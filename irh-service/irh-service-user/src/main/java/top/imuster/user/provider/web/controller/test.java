package top.imuster.user.provider.web.controller;

import com.sun.management.OperatingSystemMXBean;

import java.io.File;
import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.MemoryUsage;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;


/**
 * @ClassName: test
 * @Description: test
 * @author: hmr
 * @date: 2020/2/25 12:29
 */
public class test {

    public static void init() {
        Executors.newSingleThreadScheduledExecutor().scheduleAtFixedRate(() -> {
            try {
                OperatingSystemMXBean osmxb = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();
                MemoryMXBean memoryMXBean = ManagementFactory.getMemoryMXBean();
                // 椎内存使用情况
                MemoryUsage memoryUsage = memoryMXBean.getHeapMemoryUsage();

                // 初始的总内存
                long initTotalMemorySize = memoryUsage.getInit();
                // 最大可用内存
                long maxMemorySize = memoryUsage.getMax();
                // 已使用的内存
                long usedMemorySize = memoryUsage.getUsed();

                // 操作系统
                String osName = System.getProperty("os.name");
                // 总的物理内存
                String totalMemorySize = new DecimalFormat("#.##")
                        .format(osmxb.getTotalPhysicalMemorySize() / 1024.0 / 1024 / 1024) + "G";
                // 剩余的物理内存
                String freePhysicalMemorySize = new DecimalFormat("#.##")
                        .format(osmxb.getFreePhysicalMemorySize() / 1024.0 / 1024 / 1024) + "G";
                System.out.println("剩余的物理内存:" + freePhysicalMemorySize);
                // 已使用的物理内存
                String usedMemory = new DecimalFormat("#.##").format(
                        (osmxb.getTotalPhysicalMemorySize() - osmxb.getFreePhysicalMemorySize()) / 1024.0 / 1024 / 1024)
                        + "G";
                // 获得线程总数
                System.out.println("已经使用的物理内存：" + usedMemory);
                ThreadGroup parentThread;
                for (parentThread = Thread.currentThread().getThreadGroup(); parentThread
                        .getParent() != null; parentThread = parentThread.getParent()) {

                }

                int totalThread = parentThread.activeCount();

                // 磁盘使用情况
                File[] files = File.listRoots();
                for (File file : files) {
                    String total = new DecimalFormat("#.#").format(file.getTotalSpace() * 1.0 / 1024 / 1024 / 1024)
                            + "G";
                    String free = new DecimalFormat("#.#").format(file.getFreeSpace() * 1.0 / 1024 / 1024 / 1024) + "G";
                    String un = new DecimalFormat("#.#").format(file.getUsableSpace() * 1.0 / 1024 / 1024 / 1024) + "G";
                    String path = file.getPath();
                    System.err.println(path + "总:" + total + ",可用空间:" + un + ",空闲空间:" + free);
                    System.err.println("=============================================");
                }

                System.err.println("操作系统:" + osName);
                System.err.println("程序启动时间:" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
                        .format(new Date(ManagementFactory.getRuntimeMXBean().getStartTime())));
                System.err.println("cpu核数:" + Runtime.getRuntime().availableProcessors());
                //printlnCpuInfo(systemInfo);
                System.err.println("初始的总内存(JVM):"
                        + new DecimalFormat("#.#").format(initTotalMemorySize * 1.0 / 1024 / 1024) + "M");
                System.err.println(
                        "最大可用内存(JVM):" + new DecimalFormat("#.#").format(maxMemorySize * 1.0 / 1024 / 1024) + "M");
                System.err.println(
                        "已使用的内存(JVM):" + new DecimalFormat("#.#").format(usedMemorySize * 1.0 / 1024 / 1024) + "M");
                System.err.println("总的物理内存:" + totalMemorySize);
                System.err.println("===========================");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, 0, 5, TimeUnit.SECONDS);
    }

    public static void main(String[] args) {
        init();
    }

}
