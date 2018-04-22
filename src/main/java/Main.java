import org.bson.Document;

import java.util.Scanner;
import java.util.concurrent.LinkedBlockingQueue;

public class Main {
    public static void main(String args[]) throws InterruptedException {
        start();
        getInput();

    }

    private static void getInput() {
        Scanner reader = new Scanner(System.in);
        System.out.println("Press any key to get data from db and close the app .");
        String n = reader.next();
        Database.getInstance().getAllStamps();
        reader.close();
    }

    private static void start() {
        ProductionProcess process = new ProductionProcess();

        Runnable runnableProducer = new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        process.produce();
                        Thread.sleep(1000);
                    } catch (InterruptedException exc) {
                        exc.printStackTrace();
                    }
                }
            }
        };
        Runnable runnableConsumer = new Runnable() {
            @Override public void run()
            {
                while(true)
                {
                    try
                    {
                        process.receive();
                        Thread.sleep(1000);
                    }
                    catch(InterruptedException exc)
                    {
                        exc.printStackTrace();
                    }
                }
            }
        };
        new Thread(runnableProducer).start();
        new Thread(runnableConsumer).start();
    }
}
