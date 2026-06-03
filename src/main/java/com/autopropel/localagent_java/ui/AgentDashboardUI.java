package com.autopropel.localagent_java.ui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class AgentDashboardUI extends JFrame {

    private final String cloudUrl;
    private final String orgName;
    private final String agentId;
    private final String tokenLabel;

    private JLabel statusDot;
    private JLabel statusText;
    private JLabel lastPollLabel;
    private JLabel currentActionLabel;

    public AgentDashboardUI(String cloudUrl, String orgName, String agentId, String tokenLabel) {
        this.cloudUrl = cloudUrl;
        this.orgName = orgName;
        this.agentId = agentId;
        this.tokenLabel = tokenLabel;

        setTitle("Autopropel Agent");
        setSize(400, 350);
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE); // Hide instead of exit to keep polling active
        setLocationRelativeTo(null);
        setResizable(false);
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) {
        }

        buildUI();
        
        // When user closes the window, remind them it's still running
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                // Actually, for simplicity, let's just exit if they close the dashboard
                // otherwise it runs invisibly forever
                System.exit(0);
            }
        });
    }

    private void buildUI() {
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        mainPanel.setBackground(Color.WHITE);

        // Header
        JLabel titleLabel = new JLabel("Autopropel Agent");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 22));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel subtitleLabel = new JLabel(agentId);
        subtitleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        subtitleLabel.setForeground(Color.GRAY);
        subtitleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Status Panel
        JPanel statusPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 8, 0));
        statusPanel.setBackground(Color.WHITE);
        statusPanel.setMaximumSize(new Dimension(300, 30));
        
        statusDot = new JLabel("●");
        statusDot.setForeground(new Color(34, 197, 94)); // Green
        statusDot.setFont(new Font("Segoe UI", Font.BOLD, 18));
        
        statusText = new JLabel("Connected & Listening");
        statusText.setFont(new Font("Segoe UI", Font.BOLD, 14));
        statusText.setForeground(new Color(34, 197, 94));
        
        statusPanel.add(statusDot);
        statusPanel.add(statusText);

        // Info Card
        JPanel infoCard = new JPanel();
        infoCard.setLayout(new GridLayout(4, 1, 0, 8));
        infoCard.setBackground(new Color(249, 250, 251)); // light gray
        infoCard.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(229, 231, 235), 1, true),
                new EmptyBorder(15, 15, 15, 15)
        ));
        infoCard.setMaximumSize(new Dimension(350, 140));

        infoCard.add(createInfoRow("Organization", orgName));
        infoCard.add(createInfoRow("Token", tokenLabel));
        infoCard.add(createInfoRow("Cloud URL", cloudUrl));
        
        currentActionLabel = new JLabel("Idle - Waiting for jobs...");
        currentActionLabel.setFont(new Font("Segoe UI", Font.ITALIC, 12));
        currentActionLabel.setForeground(new Color(107, 114, 128));
        infoCard.add(createInfoRow("Status", currentActionLabel));

        // Footer
        lastPollLabel = new JLabel("Last poll: just now");
        lastPollLabel.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        lastPollLabel.setForeground(new Color(156, 163, 175));
        lastPollLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Assembly
        mainPanel.add(titleLabel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        mainPanel.add(subtitleLabel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        mainPanel.add(statusPanel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        mainPanel.add(infoCard);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        mainPanel.add(Box.createVerticalGlue());
        mainPanel.add(lastPollLabel);

        add(mainPanel);
    }

    private JPanel createInfoRow(String label, String value) {
        return createInfoRow(label, new JLabel(value));
    }

    private JPanel createInfoRow(String label, JLabel valueLabel) {
        JPanel row = new JPanel(new BorderLayout());
        row.setOpaque(false);
        
        JLabel lbl = new JLabel(label + ": ");
        lbl.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lbl.setForeground(new Color(75, 85, 99));
        
        valueLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        valueLabel.setForeground(Color.BLACK);
        
        row.add(lbl, BorderLayout.WEST);
        row.add(valueLabel, BorderLayout.CENTER);
        return row;
    }

    public void updatePollTime() {
        SwingUtilities.invokeLater(() -> {
            String time = LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"));
            lastPollLabel.setText("Last poll: " + time);
        });
    }

    public void setStatus(boolean isConnected, String message) {
        SwingUtilities.invokeLater(() -> {
            if (isConnected) {
                statusDot.setForeground(new Color(34, 197, 94));
                statusText.setForeground(new Color(34, 197, 94));
                statusText.setText("Connected");
            } else {
                statusDot.setForeground(new Color(239, 68, 68)); // Red
                statusText.setForeground(new Color(239, 68, 68));
                statusText.setText("Disconnected");
            }
            currentActionLabel.setText(message);
        });
    }

    public void showJobExecution(Long executionId, String testCaseName) {
        SwingUtilities.invokeLater(() -> {
            statusDot.setForeground(new Color(168, 85, 247)); // Purple
            statusText.setForeground(new Color(168, 85, 247));
            statusText.setText("Executing Job #" + executionId);
            currentActionLabel.setText("Running: " + testCaseName);
        });
    }
}
