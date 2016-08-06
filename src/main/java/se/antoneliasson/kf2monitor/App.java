package se.antoneliasson.kf2monitor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import se.antoneliasson.kf2monitor.messages.Message;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class App 
{
    private static final String USERNAME = "admin";

    private final Logger logger = LoggerFactory.getLogger(App.class);

    public App(String webAdminHost, String username, String password, String host, int port) {
        String webAdminURL = "http://" + webAdminHost + "/ServerAdmin/current/info";
        BlockingQueue<Message> messages = new LinkedBlockingQueue<>();
        ProducerThread p = new ProducerThread(messages, webAdminURL, username, password);
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

        logger.info("KF2monitor started");
    }

    public static void main( String[] args )
    {
        String webAdminHost;
        String webAdminPassword;
        String lcdHost;
        int lcdPort;
        if (args.length > 3) {
            lcdPort = Integer.parseInt(args[3]);
        } else {
            lcdPort = 13666;
        }
        if (args.length > 2) {
            lcdHost = args[2];
        } else {
            lcdHost = "localhost";
        }
        if (args.length > 1) {
            webAdminHost = args[0];
            webAdminPassword = args[1];

            new App(webAdminHost, USERNAME, webAdminPassword, lcdHost, lcdPort);
        } else {
            System.err.println("Usage: java App <webadmin host[:port]> <webadmin password> [lcdproc host [lcdproc port]]");
        }
    }
}
