import org.apache.commons.collections4.queue.CircularFifoQueue;
import org.bson.Document;

import java.sql.Timestamp;

public class BufferThread implements Runnable {
    @Override
    public void run() {
        CircularFifoQueue<Document> buffer = new CircularFifoQueue<>();
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        Document document = new Document("timeAdded", timestamp);
        buffer.add(document);
    }
}
