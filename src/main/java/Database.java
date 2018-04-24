import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.Block;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.IndexOptions;
import com.mongodb.client.model.Indexes;
import org.bson.Document;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

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
        this.mongoClient = Client.getInstance().getClient();
        this.database = mongoClient.getDatabase("testLocaleDatabase");
        this.data = database.getCollection("mockData");
        data.createIndex(Indexes.ascending("timeAdded"));
    }


    private void dropDb() {
        data.drop();
    }
    public void insert(Document document) {
        try {
//            dropDb();
            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            data.insertOne(document);



        } finally { }
    }

    public void getAllStamps() {
        MongoCursor<Document> cursor = data.find().iterator();
        try {
            while (cursor.hasNext()) {
                System.out.println(cursor.next().toString());
            }
        } finally {
            cursor.close();
        }
    }
}
