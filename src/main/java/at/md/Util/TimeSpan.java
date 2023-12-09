package at.md.Util;

public class TimeSpan {
    private long startTime;

    public void start() {
        startTime = System.nanoTime();
    }

    public double end() {
        long endTime = System.nanoTime();
        double elapsedTime = (endTime - startTime);
        return elapsedTime / 1_000_000; // Convert nanoseconds to milliseconds
    }
}


