package com.bigdata.akka.scala.http

import scala.io.StdIn
import akka.stream.ActorMaterializer
import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.HttpEntity
import akka.http.scaladsl.model.ContentTypes
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.unmarshalling.PredefinedFromStringUnmarshallers

object ParameterProcessMain extends App with PredefinedFromStringUnmarshallers {
	implicit val system = ActorSystem("httpActorSystem")
   implicit val materializer = ActorMaterializer()
   // needed for the future flatMap/onComplete in the end
   implicit val executionContext = system.dispatcher

   val route = 
   	// color, backgroundColor 두개의 파라미터가 필수로 있어야함. 없을시 Request is missing required query parameter 'backgroundColor' 에러
   	path("requiredParam") {
	   	get {
	   		parameters('color, 'backgroundColor) { (color, backgroundColor) =>
	      		complete(HttpEntity(ContentTypes.`text/html(UTF-8)`, s"The color is '$color' and the background is '$backgroundColor'"))
	   		}
	      }
		} ~
		// color는 필수 backgroundColor는 옵셔널
		path("optionalParam") {
	   	get {
	   		parameters('color, 'backgroundColor.?) { (color, backgroundColor) =>
	   			val backgroundStr = backgroundColor.getOrElse("undefined")
	   			
	      		complete(HttpEntity(ContentTypes.`text/html(UTF-8)`, s"The color is '$color' and the background is '$backgroundStr'"))
	   		}
	      }
		} ~
		// color는 필수 backgroundColor는 값이 없으면 기본값 설정
		path("optionalParamDefault") {
	   	get {
	   		parameters('color, 'backgroundColor ? "defaultColor") { (color, backgroundColor) =>
	      		complete(HttpEntity(ContentTypes.`text/html(UTF-8)`, s"The color is '$color' and the background is '$backgroundColor'"))
	   		}
	      }
		} ~
		// color는 필수 action 값은 "true"로 들어와야 실행됨. action 값이 없거나 "true" 가 아닌 다른 값으로 들어오면 The requested resource could not be found.
		path("fixedParam") {
	   	get {
	   		parameters('color, 'action ! "true") { (color) =>
	      		complete(HttpEntity(ContentTypes.`text/html(UTF-8)`, s"The color is '$color'."))
	   		}
	      }
		} ~
		// color는 필수 count 값은 숫자형으로 들어와야 한다. 아닐경우 The query parameter 'count' was malformed:'o' is not a valid 32-bit signed integer value
		path("deserializeIntParam") {
	   	get {
	   		parameters('color, 'count.as[Int]) { (color, count) =>
	      		complete(HttpEntity(ContentTypes.`text/html(UTF-8)`, s"The color is '$color' and you have $count of it."))
	   		}
	      }
		} ~
		// color는 필수 city 값은 안들어오거나 한건이 들어오거나 여러건이 들어올수 있다.
		path("repeatedParam") {
	   	get {
	   		parameters('color, 'city.*) { (color, cities) =>
				    cities.toList match {
				      case Nil         => complete(s"The color is '$color' and there are no cities.")
				      case city :: Nil => complete(s"The color is '$color' and the city is $city.")
				      case multiple    => complete(s"The color is '$color' and the cities are ${multiple.mkString(", ")}.")
				    }
				}
	      }
		} ~
		// color는 필수 distance 값은 숫자형으로 안들어오거나 한건이 들어오거나 여러건이 들어올수 있다.
		path("repeatedDeserializedParam") {
	   	get {
	   		parameters('color, 'distance.as[Int].*) { (color, cities) =>
			   	cities.toList match {
			      	case Nil             => complete(s"The color is '$color' and there are no distances.")
			      	case distance :: Nil => complete(s"The color is '$color' and the distance is $distance.")
			      	case multiple        => complete(s"The color is '$color' and the distances are ${multiple.mkString(", ")}.")
			    	}
			  	}
	      }
		} ~
		// names 는 필수이고 names= 는 공백, names=값, names=값,값 으로 받을수 있다. CsvSeq는 PredefinedFromStringUnmarshallers 트레잇에 선언되어 있음
		path("csvParam") {
	   	get {
	   		parameter("names".as(CsvSeq[String])) { names =>
			   	complete(s"The parameters are ${names.mkString(", ")}")
			  	}
	      }
		}

    val bindingFuture = Http().bindAndHandle(route, "0.0.0.0", 8080)

    println(s"Server online at http://localhost:8080/\nPress RETURN to stop...")
    StdIn.readLine() // let it run until user presses return
    bindingFuture
      .flatMap(_.unbind()) // trigger unbinding from the port
      .onComplete(_ => system.terminate()) // and shutdown when done
}

/*
 * http://doc.akka.io/docs/akka-http/10.0.5/scala/http/routing-dsl/directives/parameter-directives/parameters.html
 * http://doc.akka.io/docs/akka-http/10.0.5/scala/http/routing-dsl/directives/parameter-directives/index.html#which-parameter-directive
 * http://doc.akka.io/docs/akka-http/current/scala/http/routing-dsl/case-class-extraction.html#case-class-extraction
 */