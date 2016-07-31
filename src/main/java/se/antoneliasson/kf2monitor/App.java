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
        Display display = new Display("192.168.1.242", 13666);
        ConsumerThread c = new ConsumerThread(messages, display);
        p.start();
        c.start();

        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                p.interrupt();
                c.interrupt();
                // Worker threads can shut down in any order as long as they terminate before the JVM does
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
