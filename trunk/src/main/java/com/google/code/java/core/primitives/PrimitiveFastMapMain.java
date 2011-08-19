package com.google.code.java.core.primitives;

import javolution.util.FastMap;

import java.io.FileNotFoundException;

public class PrimitiveFastMapMain {
    private static final long START = System.currentTimeMillis();

    public static void main(String... args) throws InterruptedException, FileNotFoundException {
        int runs = 1200;
        FastMap<Integer, Integer> counters = new FastMap();
        Report out = new Report(runs);
        System.gc();
        for (int i = 0; i < runs; i++) {
            performTest(out, counters);
            Thread.sleep(100);
        }
        out.print("primitive-fastmap-report.csv");
    }

    private static void performTest(Report out, FastMap<Integer, Integer> counters) {
        counters.clear();
        long start = System.nanoTime();
        int runs = 300 * 300;
        for (int i = 0; i < runs; i++) {
            int x = i % 300;
            int y = i / 300;
            int times = x * y;
            Integer count = counters.get(times);
            if (count == null)
                counters.put(times, 1);
            else
                counters.put(times, count + 1);
        }
        long time = System.nanoTime() - start;
        out.usedMB[out.count] = (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / 1e6;
        out.timeFromStart[out.count] = (System.currentTimeMillis() - START) / 1e3;
        out.avgTime[out.count] = (double) time / runs;
        out.count++;
    }
}