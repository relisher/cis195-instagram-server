/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hala.android.image.server;

import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.client.MongoCursor;
import static com.mongodb.client.model.Filters.eq;
import java.util.ArrayList;


import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import org.apache.commons.codec.binary.Base64;
/**
 *
 * @author arelin
 */
public class MongoEntity {
    
    private MongoClient _mongoClient;
    private DB _db;
    
    public MongoEntity() {
        MongoClientURI uri  = new MongoClientURI(AuthenticatedUsers.getMongoURI());
        _mongoClient = new MongoClient(uri);
        _db = _mongoClient.getDB(uri.getDatabase());
    }
    
    public byte[] getImage(String userId, String imageId) {
        DBCollection currUser = _db.getCollection("user" + userId);
        MongoCursor<DBObject> result = currUser.find(eq("imageId", imageId)).iterator();
        DBObject retrived_doc = result.next();
        result.close();
        return Base64.decodeBase64((String)retrived_doc.get("image"));
    }
    
    public String[] listImages(String userId) {
        DBCollection currUser = _db.getCollection("user" + userId);
        CompletableFuture<List<DBObject>> future = new CompletableFuture<>();
        List<DBObject> resultList = new ArrayList<>();
        DBCursor find = currUser.find();
        while(find.hasNext()) {
            resultList.add(find.next());
        }
        find.close();
        String[] results = new String[resultList.size()];
        int i = 0;
        for(DBObject d : resultList) {
            results[i] = ((String)d.get("imageId"));
            i++;
        }
        return results;
    }
    
    public String setImage(String userId, byte[] image) {
        DBCollection currUser = _db.getCollection("user" + userId);
        String docId =  UUID.randomUUID().toString();
        currUser.insert(new BasicDBObject("imageId", docId)
                .append("image", Base64.encodeBase64String(image)));
        return docId;
    }
    
    public boolean deleteImage(String userId, String imageId) {
        DBCollection currUser = _db.getCollection("user" + userId);
        MongoCursor<DBObject> result = currUser.find(eq("imageId", imageId)).iterator();
        DBObject retrieved_doc = result.next();
        result.close();
        return currUser.remove(retrieved_doc).wasAcknowledged();
    }
    
    public boolean deleteAllImages(String userId) {
        DBCollection currUser = _db.getCollection("user" + userId);
        return currUser.remove(new BasicDBObject()).wasAcknowledged();
    }
    
}