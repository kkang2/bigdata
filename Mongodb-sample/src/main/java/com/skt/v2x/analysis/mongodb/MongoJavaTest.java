package com.skt.v2x.analysis.mongodb;

import java.util.Date;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.Mongo;
import com.mongodb.WriteConcern;

public class MongoJavaTest {

    static String array_names[] = {"John","Tim","Brit","Robin","Smith","Lora","Jennifer","Lyla","Victor","Adam"};
    
    static String array_address[][] ={
        {"US", "FL", " Miami"},
        {"US", "FL", " Orlando"},
        {"US", "CA", "San Diego"},
        {"US", "FL", " Orlando"},
        {"US", "FL", " Orlando"},
        {"US", "NY", "New York"},
        {"US", "NY", "Buffalo"},
        {"US", "TX", " Houston"},
        {"US", "CA", "San Diego"},
        {"US", "TX", " Houston"}
    };
    
    static int array_age[] = {40, 29, 51, 37, 42, 31, 36, 58, 26, 46};

    public static void main(String[] args)  {
        // TODO Auto-generated method stub
        Mongo conn = null;
        
        try {
            //MongoDB와 연결한다.
            conn = new Mongo("221.132.71.203", 27017);
        }
        catch(Exception e) {
            e.getStackTrace();
        }

        // WriteConcern을 지정한다. 생성자의 첫번째 변수는 복제하려는 갯수이고 두번째 변수는 복제 인터벌 시간이다.
        WriteConcern w = new WriteConcern(1,2000);
        conn.setWriteConcern(w);
        
        // database를 반환한다.
        // database가 존재하지 않으면 MongoDB는 database를 생성한다.
        DB db = conn.getDB("mydatabase");
          
        // collection을 반환한다.
        // collection이 존재하지 않으면 MongoDB는 collection을 생성한다.
        DBCollection collection = db.getCollection("roadCollection");
          
        /**** Insert ****/
        // key와 value를 이용하여 document를 생성하고 collection에 삽입한다.
        BasicDBObject document;
        
        for(int i = 0 ; i < array_names.length ; i++){
            document = new BasicDBObject();
            //value -> String
            document.append("name", array_names[i]);
            // value -> int
            document.append("age", array_age[i]);
            // value -> date
            document.append("join", new Date());
            // value --> document
            document.append("address", 
                    new BasicDBObject("country",array_address[i][0]).append("state", array_address[i][1])
                    .append("city", array_address[i][2]));
         
            collection.insert(document);
         
        }
             
        // get count
        System.out.println("All Persons: "+collection.getCount());
        //------------------------------------
        // get all document
        DBCursor cursor = collection.find();
        try {
            while(cursor.hasNext()) {
                System.out.println(cursor.next());
            }
        } finally {
            cursor.close();
        }
        //------------------------------------
         
        // get documents by query
        BasicDBObject query = new BasicDBObject("age", new BasicDBObject("$gt", 40));
         
        cursor = collection.find(query);
        System.out.println("Person with age > 40 --> "+cursor.count());
             
          
        /**** Update ****/
        // age > 40의 질의를 이용하여 반환되는 document를 "age = 20"의 값을 갖는 새로운 document로 대체한다. 
        BasicDBObject newDocument = new BasicDBObject();
        newDocument.put("age", 20);
          
        BasicDBObject updateObj = new BasicDBObject();
        updateObj.put("$set", newDocument);
          
        collection.update(query, updateObj,false,true); // 마지막 매개변수를 true로 설정하여 모든 document에 적용한다.
          
        /**** Find and display ****/
        cursor = collection.find(query);
        System.out.println("Person with age > 40 after update --> "+cursor.count());
             
             
        //get all again
        cursor = collection.find();
        try {
            while(cursor.hasNext()) {
                System.out.println(cursor.next());
            }
        } finally {
            cursor.close();
        }
        
        /**** Database Remove ****/
        conn.dropDatabase("HR_DB");
          
        /**** Done ****/
        System.out.println("Done");
        
    }
}