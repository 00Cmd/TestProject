import com.mongodb.gridfs.CLI;
import org.bson.Document;

import java.sql.Timestamp;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ProductionProcess {
    private final Queue QUEUE;
    private final Lock LOCK;

    public ProductionProcess(){
        this.QUEUE = new LinkedList<Document>();
        this.LOCK = new ReentrantLock();


    }

    public void produce() throws InterruptedException {
        this.LOCK.lock();
        try {
            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            Document document = new Document("timeAdded", timestamp);
            if(this.QUEUE.offer(document)) {
//                System.out.println("Added to queue: " + document);
            }
        } finally {
            this.LOCK.unlock();
        }
    }

    public void receive() throws InterruptedException {
        if (Client.isAlive()) {
            this.LOCK.lock();
            try {
                Database mDatabase = Database.getInstance();

                Document mDocument = (Document) this.QUEUE.poll();

                if (mDocument != null) {
                    mDatabase.insert(mDocument);
//                    System.out.println("Consumed from queue: " + mDocument);
                        while(this.QUEUE.size() != 0) {
                            mDocument = (Document) this.QUEUE.poll();
                            mDatabase.insert(mDocument);
                            System.out.println("Consumed " + mDocument.toString());

                        }
                    }
            } finally {
                this.LOCK.unlock();
            }
        } else {
            Client.reconnect();
        }
    }
}