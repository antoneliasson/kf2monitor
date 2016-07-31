package se.antoneliasson.kf2monitor;

import java.util.concurrent.BlockingQueue;

public class ConsumerThread extends Thread {
    private final BlockingQueue<GameDataContainer> messages;

    public ConsumerThread(BlockingQueue<GameDataContainer> messages) {
        this.messages = messages;
    }

    @Override
    public void run() {
        try {
            while (!interrupted()) {
                System.out.println("Consumer: " + messages.take());
            }
        } catch (InterruptedException e) {}
        System.out.println("ConsumerThread shutting down");
    }
}
