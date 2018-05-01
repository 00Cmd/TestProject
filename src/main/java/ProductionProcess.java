import org.bson.Document;
import java.sql.Timestamp;
import java.util.LinkedList;

public class ProductionProcess {
    private LinkedList<Document> queue;

    public ProductionProcess(){
        this.queue = new LinkedList<>();
    }

    public void produce() throws InterruptedException {
        synchronized (this) {
            Document document;
            try {
                Timestamp timestamp = new Timestamp(System.currentTimeMillis());
                document = new Document("timeAdded", timestamp);
                if (this.queue.offer(document)) {
//                    System.out.println("Added to queue: " + document);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void reveice() throws InterruptedException {
            Client client = Client.getInstance();
            if (client.isAlive()) {
                synchronized (this) {
                try {
                    Database mDatabase = Database.getInstance();

                    Document mDocument = this.queue.poll();

                    if (mDocument != null) {
                        mDatabase.insert(mDocument);
//                        System.out.println("Consumed from queue: " + mDocument);
                        while (this.queue.size() != 0) {
                            mDocument = this.queue.poll();
                            mDatabase.insert(mDocument);
                            System.out.println("Consumed " + mDocument.toString());

                        }
                    }
                } catch (Exception e) {
                    e.getLocalizedMessage();
                }
            }
        } else {
                client.reconnectToServer();
            }
    }
}