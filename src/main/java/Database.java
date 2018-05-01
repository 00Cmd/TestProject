import com.mongodb.MongoClientOptions;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Indexes;
import org.bson.Document;




public class Database {
    private MongoCollection<Document> data;

    private static Database sDatabase;


    public static Database getInstance() {
        if(sDatabase == null) {
            sDatabase = new Database();
        }
        return sDatabase;
    }

    private Database() {
        int start = calculateTime(System.currentTimeMillis());


        MongoDatabase database = Client.getInstance()
                .getClient().getDatabase("testLocaleDatabase");

        MongoClientOptions.Builder options_builder = new MongoClientOptions.Builder();
        options_builder.maxConnectionIdleTime(30000).build();

        this.data = database.getCollection("mockData");
        data.createIndex(Indexes.ascending("timeAdded"));
        int end = calculateTime(System.currentTimeMillis()) - start;
        System.out.println("Db connection took " + end + " seconds");

    }

    private int calculateTime(long time) { return (int) (time / 1000) % 60; }

//    private void dropDb() { data.drop(); }

    public void insert(Document document) { data.insertOne(document); }

    public void getAllStamps() {
        try (MongoCursor<Document> cursor = data.find().iterator()) {
            while (cursor.hasNext()) {
                System.out.println(cursor.next().toString());
            }
        }
    }

}
