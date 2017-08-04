import akka.stream.scaladsl.Sink
import akka.stream.scaladsl.RunnableGraph
import akka.stream.scaladsl.Source
import scala.concurrent.Future
import akka.stream.scaladsl.Keep
import akka.actor.ActorSystem
import akka.stream.ActorMaterializer

object Test extends App {
	implicit val system = ActorSystem()
   implicit val materializer = ActorMaterializer()
    
	val source = Source(1 to 10)
	val sink = Sink.fold[Int, Int](0)(_ + _)
	
	// connect the Source to the Sink, obtaining a RunnableGraph
	val runnable: RunnableGraph[Future[Int]] = source.toMat(sink)(Keep.right)
	
	// materialize the flow and get the value of the FoldSink
	val sum: Future[Int] = runnable.run()
}