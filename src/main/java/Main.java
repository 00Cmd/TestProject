import org.bson.Document;

import java.util.concurrent.LinkedBlockingQueue;

public class Main {
    public static void main(String args[]) throws InterruptedException {

        LinkedBlockingQueue<Document> queue = new LinkedBlockingQueue<>();
        ProducerThread mProducer = new ProducerThread(queue);
        ConsumerThread mConsumer = new ConsumerThread(queue);
        new Thread(mProducer).start();
        new Thread(mConsumer).start();




    }



}
