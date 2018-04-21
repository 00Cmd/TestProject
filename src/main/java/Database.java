import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

public class Database {
    private MongoClient mongoClient;
    private MongoDatabase database;
    private MongoCollection<Document> data;

    private static Database sDatabase;

    public static Database getInstance() {
        if(sDatabase == null) {
            sDatabase = new Database();
        }
        return sDatabase;
    }

    private Database() {
        this.mongoClient = new MongoClient();
        this.database = mongoClient.getDatabase("testLocaleDatabase");
        this.data = database.getCollection("mockData");
    }

    public void insert() {
        try {
//            System.out.println("Drop collection");
//            data.drop();
            System.out.println("Start inserting document");

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss");

            Timestamp timestamp = new Timestamp(System.currentTimeMillis());

//            List<Document> documentList = new ArrayList<Document>();
            Document document = new Document("timeAdded", timestamp);

            data.insertOne(document);
            System.out.println("Done inserting document");

        } finally {
            mongoClient.close();
        }
    }

    public void getAllStamps() {
        MongoCollection<Document> collection = database.getCollection("mockData");
        MongoCursor<Document> cursor = collection.find().iterator();
        try {
            while (cursor.hasNext()) {
                System.out.println(cursor.next().toString());
            }
        } finally {
            cursor.close();
        }
//        Document myDoc = collection.find().first();
    }

}
