package se.antoneliasson.kf2monitor;

import se.antoneliasson.kf2monitor.messages.GameDataContainer;

import java.util.concurrent.BlockingQueue;

public class ProducerThread extends Thread {
    private final BlockingQueue<GameDataContainer> messages;
    private final WebAdminClient client;

    public ProducerThread(BlockingQueue<GameDataContainer> messages, String url, String user, String password) {
        this.messages = messages;
        client = new WebAdminClient(url, user, password);
    }

    @Override
    public void run() {
        long t = System.currentTimeMillis();
        int period = 1000;
        try {
            while (!interrupted()) {
                GameDataContainer c = client.update();
                messages.put(c);

                t += period;
                long diff = t - System.currentTimeMillis();
                if (diff > 0) {
                    sleep(diff);
                }
            }
        } catch (InterruptedException e) {}
        System.out.println("ProducerThread shutting down");
    }
}
