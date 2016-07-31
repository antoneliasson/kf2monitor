package se.antoneliasson.kf2monitor;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class App 
{
    static final String URL = "http://kf2server:8080/ServerAdmin/current/info";
    static final String USER = "admin";
    static final String PASSWORD = "pw";

    public App() {
        BlockingQueue<GameDataContainer> messages = new LinkedBlockingQueue<>();
        ProducerThread p = new ProducerThread(messages, URL, USER, PASSWORD);
        ConsumerThread c = new ConsumerThread(messages);
        p.start();
        c.start();

        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                p.interrupt();
                c.interrupt();
                try {
                    p.join();
                    c.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public static void main( String[] args )
    {
        new App();
    }
}
