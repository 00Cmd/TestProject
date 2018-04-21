
import com.mongodb.MongoClient;

public class Client {
    private static Client sClient;
    private MongoClient mongoClient;

    public static Client getInstance() {
        if (sClient == null) {
            sClient = new Client();
        }
        return sClient;
    }

    private Client() { this.mongoClient = new MongoClient(Constant.URL, Constant.PORT); }

    public MongoClient getClient() {
        return mongoClient;
    }


}
