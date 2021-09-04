package view.labels;

import view.buttons.CustomButton;

import javax.swing.*;
import java.awt.*;

public class PlayerLabel extends JLabel {

    private JLabel turnsLabel;
    private final String playerName;
    private JLabel treasuryLabel = new JLabel();
    private JLabel foodLabel = new JLabel();
    private final double playerTreasury;
    private final double playerFood;
    private final boolean isMale;
    private final CustomButton endTurn = new CustomButton();

    public PlayerLabel(boolean isMale, String playerName, double playerTreasury, double playerFood) {
        this.playerTreasury = playerTreasury;
        this.playerFood = playerFood;
        this.isMale = isMale;
        this.playerName = playerName;
        this.setVisible(true);
        this.setOpaque(false);
        this.setLayout(null);
        initializeTurnsLabel();
        initializeEndTurnButton();
        initializePlayerLabel();
        initializeTreasuryLabel();
        initializeFoodLabel();
        this.repaint();
        this.revalidate();
    }

    public void initializeEndTurnButton() {
        endTurn.setVisible(true);
        endTurn.setBounds(1330, 220, 200, 90);
        endTurn.setForeground(Color.WHITE);
        endTurn.setBackground(Color.BLACK);
        endTurn.setFont(new Font("New Roman", Font.BOLD, 35));
        endTurn.setText("End Turn");
        this.add(endTurn);
    }

    public void initializePlayerLabel() {
        JLabel playerLabel = new JLabel();
        playerLabel.setVisible(true);
        playerLabel.setText("Player Name: " + playerName);
        playerLabel.setBounds(400, 70, 400, 100);
        playerLabel.setOpaque(false);
        playerLabel.setFont(new Font("New Roman", Font.BOLD, 25));
        playerLabel.setForeground(Color.BLACK);
        this.add(playerLabel);
    }

    public void initializeTreasuryLabel() {
        treasuryLabel = new JLabel();
        treasuryLabel.setVisible(true);
        treasuryLabel.setText("Treasury :  \uD83D\uDCB0" + playerTreasury);
        treasuryLabel.setBounds(400, 120, 400, 100);
        treasuryLabel.setOpaque(false);
        treasuryLabel.setForeground(Color.BLACK);
        treasuryLabel.setFont(new Font("New Roman", Font.BOLD, 25));
        this.add(treasuryLabel);
    }

    public void initializeFoodLabel() {
        foodLabel = new JLabel();
        foodLabel.setVisible(true);
        foodLabel.setText("Food :" + playerFood);
        foodLabel.setBounds(400, 170, 400, 100);
        foodLabel.setOpaque(false);
        foodLabel.setForeground(Color.BLACK);
        foodLabel.setFont(new Font("New Roman", Font.BOLD, 25));
        foodLabel.setForeground(Color.BLACK);
        this.add(foodLabel);
    }

    public void initializeTurnsLabel() {
        turnsLabel = new JLabel();
        turnsLabel.setVisible(true);
        turnsLabel.setBounds(400, 250, 120, 35);
        turnsLabel.setForeground(Color.BLACK);
        turnsLabel.setFont(new Font("New Roman", Font.BOLD, 25));
        turnsLabel.setText(" 1 Turns");
        this.initializeCharacterLabel();
        this.add(turnsLabel);
    }

    public void initializeCharacterLabel() {
        JLabel character = new JLabel();
        character.setVisible(true);
        character.setOpaque(false);
        if (isMale) {
            ImageIcon image2 = new ImageIcon("resources/images/Kingedited.jpg");
            character.setIcon(image2);
        } else {
            ImageIcon image3 = new ImageIcon("resources/images/QueenEdited.jpg");
            character.setIcon(image3);
        }
        character.setBounds(70, 90, 300, 200);//imp
        character.setVerticalAlignment(JButton.CENTER);
        this.add(character);
    }

    public JLabel getTurnsLabel() {
        return turnsLabel;
    }

    public JLabel getTreasuryLabel() {
        return treasuryLabel;
    }

    public JLabel getFoodLabel() {
        return foodLabel;
    }

    public CustomButton getEndTurn() {
        return endTurn;
    }
}
