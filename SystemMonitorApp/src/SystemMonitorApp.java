import javax.swing.*;
import java.awt.*;
import java.lang.management.ManagementFactory;
import com.sun.management.OperatingSystemMXBean;
import java.io.File;

public class SystemMonitorApp {
    public static void main(String[] args) {
        // 1. Set up the Window (Look and Feel)
        JFrame frame = new JFrame("System Monitor Pro v1.0");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(450, 400);

        // 2. Create a Dark-Mode UI Panel
        JPanel panel = new JPanel(new GridLayout(5, 1, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panel.setBackground(new Color(30, 30, 45));

        JLabel title = new JLabel("System Health Dashboard", SwingConstants.CENTER);
        title.setFont(new Font("SansSerif", Font.BOLD, 22));
        title.setForeground(new Color(0, 255, 200));

        // 3. Labels for Dynamic Hardware Data
        JLabel cpuLabel = new JLabel("CPU Usage: Fetching...", SwingConstants.CENTER);
        JLabel memLabel = new JLabel("Memory: Fetching...", SwingConstants.CENTER);
        JLabel diskLabel = new JLabel("Disk Space: Fetching...", SwingConstants.CENTER);

        styleLabel(cpuLabel);
        styleLabel(memLabel);
        styleLabel(diskLabel);

        // 4. Refresh Button
        JButton refreshBtn = new JButton("Refresh Stats");
        refreshBtn.setFont(new Font("SansSerif", Font.BOLD, 14));
        refreshBtn.setBackground(new Color(70, 70, 90));
        refreshBtn.setForeground(Color.WHITE);
        refreshBtn.addActionListener(e -> updateStats(cpuLabel, memLabel, diskLabel));

        // 5. Add components and show
        panel.add(title);
        panel.add(cpuLabel);
        panel.add(memLabel);
        panel.add(diskLabel);
        panel.add(refreshBtn);

        frame.add(panel);
        updateStats(cpuLabel, memLabel, diskLabel); // Run once at startup
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private static void styleLabel(JLabel l) {
        l.setFont(new Font("Monospaced", Font.BOLD, 16));
        l.setForeground(Color.WHITE);
    }

    private static void updateStats(JLabel cpu, JLabel mem, JLabel disk) {
        OperatingSystemMXBean osBean = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();

        // Calculate CPU Load
        double load = osBean.getCpuLoad() * 100;
        cpu.setText(String.format("CPU Load: %.2f%%", load));

        // Calculate Memory Usage (Total vs Free)
        long totalMem = osBean.getTotalMemorySize() / (1024 * 1024 * 1024);
        long freeMem = osBean.getFreeMemorySize() / (1024 * 1024 * 1024);
        mem.setText("RAM: " + (totalMem - freeMem) + "GB / " + totalMem + "GB Used");

        // Calculate Disk Space on C:
        File cDrive = new File("C:");
        long freeDisk = cDrive.getFreeSpace() / (1024 * 1024 * 1024);
        disk.setText("Disk (C:) Free: " + freeDisk + " GB");
    }
}