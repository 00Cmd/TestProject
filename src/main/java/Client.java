
import com.mongodb.MongoClient;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;

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


    public static boolean isAlive() {
        try {
            Socket s = new Socket(Constant.URL, Constant.PORT);
            return true;
        } catch (IOException ex) {
            System.out.println("Server status: offline");
            return false;
        }
    }

    public static boolean reconnectToServer() {
      SocketAddress sa = new InetSocketAddress(Constant.URL, Constant.PORT);
        while (!isAlive()) {
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    return false;
                }
            }
        return true;
    }


}
