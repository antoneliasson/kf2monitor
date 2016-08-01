package se.antoneliasson.kf2monitor;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import static java.lang.System.exit;

public class App 
{
    static final String URL = "http://kf2server:8080/ServerAdmin/current/info";
    static final String USERNAME = "admin";

    public App(String username, String password, String host, int port) {
        BlockingQueue<GameDataContainer> messages = new LinkedBlockingQueue<>();
        ProducerThread p = new ProducerThread(messages, URL, username, password);
        Display display = new Display(host, port);
        ConsumerThread c = new ConsumerThread(messages, display);

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

        p.start();
        c.start();
    }

    public static void main( String[] args )
    {
        if (args.length < 2) {
            System.err.println("Usage: java App <webadmin password> <lcdproc host> [lcdproc port]");
            exit(1);
        }
        String password = args[0];
        String host = args[1];
        int port;
        if (args.length > 2) {
            port = Integer.parseInt(args[2]);
        } else {
            port = 13666;
        }
        new App(USERNAME, password, host, port);
    }
}
