package se.antoneliasson.kf2monitor;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import static java.lang.System.exit;

public class App 
{
    static final String URL = "http://kf2server:8080/ServerAdmin/current/info";

    public App(String username, String password, String host, int port) {
        BlockingQueue<GameDataContainer> messages = new LinkedBlockingQueue<>();
        ProducerThread p = new ProducerThread(messages, URL, username, password);
        Display display = new Display(host, port);
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
        if (args.length < 3) {
            System.err.println("Usage: java App <webadmin username> <webadmin password> <lcdproc host> [lcdproc port]");
            exit(1);
        }
        String username = args[0];
        String password = args[1];
        String host = args[2];
        int port;
        if (args.length > 3) {
            port = Integer.parseInt(args[3]);
        } else {
            port = 13666;
        }
        new App(username, password, host, port);
    }
}
