import org.bson.Document;

import java.util.concurrent.LinkedBlockingQueue;

public class ConsumerThread implements Runnable {
    private LinkedBlockingQueue<Document> queue;

    public ConsumerThread(LinkedBlockingQueue<Document> queue) {
        this.queue = queue;
    }


    @Override
    public void run() {
            try {
                Document doc;
                if (Client.isAlive()) {
                    while (queue.take() != null) {
                        Database.getInstance().insert(queue.take());
                        Thread.sleep(10);
                        System.out.println("Consumed " + queue.take().toString());
                        if (!Client.isAlive()) {
                            wait();
                            Client.reconnect();
                        }
                    }
                } else {
                    wait();
                }
            } catch(InterruptedException | IllegalMonitorStateException e){
                e.printStackTrace();
            }
    }
}
