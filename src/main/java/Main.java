import java.io.IOException;
import java.net.Socket;

public class Main {
    private static DatabaseThread mDatabaseThread;
    private static BufferThread mBufferThread;
    public static void main(String args[]) {
        while (isAlive()) {
            try {
                mDatabaseThread = new DatabaseThread();
                mDatabaseThread.run();
                Thread.sleep(1000);
            } catch (Exception e) {
                System.out.println(e.getLocalizedMessage());
            }
        }
        while(!isAlive()) {
            mBufferThread = new BufferThread();
            mBufferThread.run();

        }
    }

    public static boolean isAlive() {
        try {
            Socket s = new Socket(Constant.URL, Constant.PORT);
            System.out.println("Server status: online");
            return true;
        } catch (IOException ex) {
            System.out.printf("Server status: offline");
            return false;
        }
    }

}
