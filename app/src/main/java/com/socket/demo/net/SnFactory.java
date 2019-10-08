package com.socket.demo.net;

import java.util.concurrent.atomic.AtomicInteger;

public class SnFactory {

    private static AtomicInteger snClient = new AtomicInteger(100);

    public static int getSnClient() {
        return snClient.getAndIncrement();
    }
}
