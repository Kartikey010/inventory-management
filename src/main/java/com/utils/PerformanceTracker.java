package com.utils;

public class PerformanceTracker {

    public void logRetrievalTime(String source, long time) {
        System.out.println("Time to retrieve from " + source + ": " + time + " ns");
    }
}
