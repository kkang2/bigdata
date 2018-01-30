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
            //MongoDB�� �����Ѵ�.
            conn = new Mongo("221.132.71.203", 27017);
        }
        catch(Exception e) {
            e.getStackTrace();
        }

        // WriteConcern�� �����Ѵ�. �������� ù��° ������ �����Ϸ��� �����̰� �ι�° ������ ���� ���͹� �ð��̴�.
        WriteConcern w = new WriteConcern(1,2000);
        conn.setWriteConcern(w);
        
        // database�� ��ȯ�Ѵ�.
        // database�� �������� ������ MongoDB�� database�� �����Ѵ�.
        DB db = conn.getDB("mydatabase");
          
        // collection�� ��ȯ�Ѵ�.
        // collection�� �������� ������ MongoDB�� collection�� �����Ѵ�.
        DBCollection collection = db.getCollection("roadCollection");
          
        /**** Insert ****/
        // key�� value�� �̿��Ͽ� document�� �����ϰ� collection�� �����Ѵ�.
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
        // age > 40�� ���Ǹ� �̿��Ͽ� ��ȯ�Ǵ� document�� "age = 20"�� ���� ���� ���ο� document�� ��ü�Ѵ�. 
        BasicDBObject newDocument = new BasicDBObject();
        newDocument.put("age", 20);
          
        BasicDBObject updateObj = new BasicDBObject();
        updateObj.put("$set", newDocument);
          
        collection.update(query, updateObj,false,true); // ������ �Ű������� true�� �����Ͽ� ��� document�� �����Ѵ�.
          
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