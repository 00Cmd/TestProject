
import com.mongodb.MongoClient;

import java.io.IOException;
import java.net.Socket;


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

    public  MongoClient getClient() {
        return this.mongoClient;
    }

    public boolean isAlive() {
        try (Socket ignored =  new Socket(Constant.URL, Constant.PORT)) {
            return true;
        } catch (IOException ex) {
            System.out.println("Server status: offline");
            return false;
        }
    }

    public void reconnectToServer() {
        while (!isAlive()) {
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    return;
                }
            }
    }


}
