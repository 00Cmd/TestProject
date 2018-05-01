import java.util.Scanner;

public class Main  {
    public static void main(String args[]) throws InterruptedException {
        start();
        getInput();
    }

    private static void getInput() {
        Client client = Client.getInstance();
        if (client.isAlive()) {
            new java.util.Timer().schedule(
                    new java.util.TimerTask() {
                        @Override
                        public void run() {
                            System.out.println("Type ( GET ) to get data.");
                            System.out.println("Press Enter");
                            Scanner scanner;
                            try {
                                scanner = new Scanner(System.in);
                                while (true) {
                                    if (scanner.nextLine().equalsIgnoreCase("get")) {
                                        Database.getInstance().getAllStamps();
                                        System.exit(1);
                                    } else {
                                        System.out.println("App started .");
                                    }
                                }

                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    },
                    1000
            );
        }
    }

    private static void start() {
        ProductionProcess process = new ProductionProcess();

        Runnable runnableProducer = () -> {
            while (true) {
                try {
                    process.produce();
                    Thread.sleep(1000);
                } catch (InterruptedException exc) {
                    exc.printStackTrace();
                }
            }
        };
        Runnable runnableConsumer = () -> {
            while(true) {
                try
                {
                    process.reveice();
                    Thread.sleep(1000);
                }
                catch(InterruptedException exc)
                {
                    exc.printStackTrace();
                }
            }
        };
        new Thread(runnableProducer).start();
        new Thread(runnableConsumer).start();
    }
}
