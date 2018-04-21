public class DatabaseThread implements Runnable {
    private Database database;

    @Override
    public void run() {
        getDbInstance();
        if(database != null) {
            database.insert();
        }
    }

    private void getDbInstance() {
        database = Database.getInstance();
    }
}
