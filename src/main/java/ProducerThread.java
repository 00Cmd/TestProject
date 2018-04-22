import org.bson.Document;

import java.sql.Timestamp;
import java.util.concurrent.LinkedBlockingQueue;

public class ProducerThread implements Runnable {
    private LinkedBlockingQueue<Document> queue;

    public ProducerThread(LinkedBlockingQueue<Document> queue) {
        this.queue = queue;
    }
    @Override
    public void run() {
        while(true) {
            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            Document document = new Document("timeAdded", timestamp);
            try {
                queue.put(document);
                System.out.println("Document added " + document.toString());
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
