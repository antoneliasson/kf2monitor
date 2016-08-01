package se.antoneliasson.kf2monitor;

import se.antoneliasson.kf2monitor.messages.Message;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import static java.lang.System.exit;

public class App 
{
    private static final String USERNAME = "admin";

    public App(String webAdminHost, String username, String password, String host, int port) {
        String webAdminURL = "http://" + webAdminHost + "/ServerAdmin/current/info";
        BlockingQueue<Message> messages = new LinkedBlockingQueue<>();
        ProducerThread p = new ProducerThread(messages, webAdminHost, username, password);
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
        if (args.length < 3) {
            System.err.println("Usage: java App <webadmin host[:port]> <webadmin password> <lcdproc host> [lcdproc port]");
            exit(1);
        }
        String webAdminHost = args[0];
        String password = args[1];
        String lcdHost = args[2];
        int port;
        if (args.length > 3) {
            port = Integer.parseInt(args[3]);
        } else {
            port = 13666;
        }
        new App(webAdminHost, USERNAME, password, lcdHost, port);
    }
}
