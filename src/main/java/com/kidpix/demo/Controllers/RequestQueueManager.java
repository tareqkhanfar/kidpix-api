package com.kidpix.demo.Controllers;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

public class RequestQueueManager {

    private ConcurrentLinkedQueue<Supplier<Object>> queue = new ConcurrentLinkedQueue<>();
    private volatile boolean isProcessing = false;

    public void enqueueRequest(Supplier<Object> request) {
        queue.offer(request);
        if (!isProcessing) {
            processQueue();
        }
    }

    private void processQueue() {
        isProcessing = true;
        CompletableFuture.supplyAsync(queue.poll())
                .thenAccept(result -> {

                    if (!queue.isEmpty()) {
                        processQueue();
                    } else {
                        isProcessing = false;
                    }
                });
    }
}
