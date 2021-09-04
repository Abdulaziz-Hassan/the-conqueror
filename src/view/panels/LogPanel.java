package view.panels;

import view.buttons.CustomButton;

import javax.swing.*;
import java.awt.*;

public class LogPanel extends JPanel {

    private final JTextPane logPane;
    private String message;
    private final CustomButton startBattleButton;

    public LogPanel(String targetCityName) {
        JLabel logLabel = new JLabel();
        this.logPane = new JTextPane();
        JLabel targetCityNameLabel = new JLabel();
        this.startBattleButton = new CustomButton();
        this.setBorder(BorderFactory.createLineBorder(Color.BLACK,4));
        this.setBackground(Color.WHITE);
        this.setBounds(1201,0,400,1000);
        this.setLayout(null);
        this.setVisible(true);
        this.setOpaque(true);

        targetCityNameLabel.setBounds(50,50-30,200,100);
        targetCityNameLabel.setText(targetCityName.toUpperCase());
        targetCityNameLabel.setVisible(true);
        targetCityNameLabel.setFont(new Font("NEW ROMAN",Font.BOLD,35));

        logLabel.setBounds(70,160-30-40,200,100);
        logLabel.setText("LOG");
        logLabel.setVisible(true);
        logLabel.setFont(new Font("NEW ROMAN",Font.BOLD,35));

        logPane.setBounds(10,160+100+10-30-40,300,500);
        logPane.setVisible(true);
        logPane.setOpaque(true);
        logPane.setBackground(Color.lightGray);
        logPane.setText(message);


        startBattleButton.setBounds(10,160+100+10+500-30-40+10,300,100);
        startBattleButton.setText("Battle Begins");
        startBattleButton.setVisible(true);
        startBattleButton.setFont(new Font("NEW ROMAN",Font.BOLD,35));
        startBattleButton.setForeground(Color.WHITE);
        startBattleButton.setBackground(Color.BLACK);

        logPane.setEditable(false);
        logPane.setFont(new Font("NEW ROMAN",Font.BOLD, 15));
        logPane.setForeground(Color.BLACK);

        this.add(startBattleButton);
        this.add(targetCityNameLabel);
        this.add(logLabel);
        this.add(logPane);
        this.revalidate();
        this.repaint();
    }

    public JTextPane getLogPane() {
        return logPane;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public CustomButton getStartBattleButton() {
        return startBattleButton;
    }
}
