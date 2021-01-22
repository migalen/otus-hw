package ru.otus;

import com.sun.management.GarbageCollectionNotificationInfo;
import javax.management.MBeanServer;
import javax.management.NotificationEmitter;
import javax.management.NotificationListener;
import javax.management.ObjectName;
import javax.management.openmbean.CompositeData;
import java.lang.management.GarbageCollectorMXBean;
import java.lang.management.ManagementFactory;
import java.util.List;

public class GcDemo {

    static long CleaningCount = 0;
    static long CleaningTime = 0;
    static long maxSweepDuration = 0;

    public static void main(String... args) throws Exception {

        System.out.println("Starting pid: " + ManagementFactory.getRuntimeMXBean().getName());

        int size = 5 * 100000;
        int loopCounter = 10000;
        long beginTime = System.currentTimeMillis();

        switchOnMonitoring();

        MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
        ObjectName objectName = new ObjectName("ru.otus.bench:type=Benchmark");

        try {
            Benchmark mbean = new Benchmark(loopCounter);
            mbs.registerMBean(mbean, objectName);
            mbean.setSize(size);
            mbean.run();
        } catch (Exception e) {
            System.out.println(e.getCause());
        } finally {
            System.out.println("Execution time:      " + (System.currentTimeMillis() - beginTime) + " (ms)");
            System.out.println("Total cleaning count:      " + CleaningCount);
            System.out.println("Average cleaning time:      " + CleaningTime / CleaningCount + " (ms)");
            System.out.println("Max cleaning time:      " + (System.currentTimeMillis() - beginTime) + " (ms)");
        }
    }

    private static void switchOnMonitoring() {
        List<GarbageCollectorMXBean> gcbeans = java.lang.management.ManagementFactory.getGarbageCollectorMXBeans();
        for (GarbageCollectorMXBean gcbean : gcbeans) {
            System.out.println("GC name:" + gcbean.getName());
            NotificationEmitter emitter = (NotificationEmitter) gcbean;
            NotificationListener listener = (notification, handback) -> {
                if (notification.getType().equals(GarbageCollectionNotificationInfo.GARBAGE_COLLECTION_NOTIFICATION)) {
                    GarbageCollectionNotificationInfo info = GarbageCollectionNotificationInfo.from((CompositeData) notification.getUserData());
                    String gcName = info.getGcName();
                    String gcAction = info.getGcAction();
                    String gcCause = info.getGcCause();

                    long startTime = info.getGcInfo().getStartTime();
                    long duration = info.getGcInfo().getDuration();
                    CleaningCount++;
                    CleaningTime += duration;
                    if (maxSweepDuration < duration) {
                        maxSweepDuration = duration;
                    }
                    System.out.println("Start time:" + startTime +
                            " Name:" + gcName + ", action:" + gcAction +
                            ", gcCause:" + gcCause + "(" + duration + " ms)");
                }
            };
            emitter.addNotificationListener(listener, null, null);
        }
    }
}
