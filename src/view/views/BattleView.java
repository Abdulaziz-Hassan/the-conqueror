package view.views;

import view.buttons.UnitButton;
import view.buttons.CustomButton;
import view.tools.CustomFrame;
import view.labels.BoardLabel;
import view.panels.EnemyArmyPanel;
import view.panels.LogPanel;
import view.panels.PlayerArmyPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class BattleView extends CustomFrame implements ActionListener {

    private final ArrayList<UnitButton> defenderUnitButtons;
    private final ArrayList<UnitButton> attackerUnitButtons;
    private final BoardLabel boardLabel;
    private final PlayerArmyPanel playerArmyPanel;
    private final LogPanel logPanel;
    private final EnemyArmyPanel enemyArmyPanel;
    private boolean playerChosen = true;
    private boolean enemyChosen = true;
    private boolean whoWon;
    private final CustomButton endBattleButton = new CustomButton();

    public BattleView(String targetCityName, ArrayList<UnitButton> enemyUnitButtons, ArrayList<UnitButton> attackUnitButtons) {
        this.defenderUnitButtons = enemyUnitButtons;
        this.attackerUnitButtons = attackUnitButtons;
        boardLabel = new BoardLabel();
        this.setLayout(null);
        this.setVisible(true);

        endBattleButton.setVisible(false);
        endBattleButton.addActionListener(this);
        endBattleButton.setText("Battle Finished");
        endBattleButton.setBackground(Color.BLACK);
        endBattleButton.setOpaque(true);
        endBattleButton.setForeground(Color.WHITE);
        endBattleButton.setBounds(550, 500, 300, 200);
        endBattleButton.setFont(new Font("NEW ROMAN", Font.BOLD, 25));
        this.add(endBattleButton);

        enemyArmyPanel = new EnemyArmyPanel(enemyUnitButtons);
        playerArmyPanel = new PlayerArmyPanel(attackUnitButtons);
        logPanel = new LogPanel(targetCityName);
        this.add(enemyArmyPanel);
        this.add(playerArmyPanel);
        this.add(boardLabel);
        this.add(logPanel);

        ImageIcon image = null;
        switch (targetCityName.toLowerCase()) {
            case "cairo":
            case "sparta":
            case "rome":
                image = new ImageIcon("resources/images/SpartaBattleArenaEditedk.jpg");
        }
        boardLabel.setIcon(image);
        for (UnitButton unitButton : attackUnitButtons)
            unitButton.addActionListener(this);
        for (UnitButton unitButton : enemyUnitButtons)
            unitButton.addActionListener(this);

        logPanel.getStartBattleButton().addActionListener(this);
        this.reload();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (playerChosen) {
            for (UnitButton unitButton : attackerUnitButtons) {
                if (e.getSource() == unitButton) {
                    playerArmyPanel.remove(unitButton);
                    boardLabel.addAttacker(unitButton);
                    this.reload();
                    playerChosen = false;
                    playerArmyPanel.getPlayerUnitButtons().remove(unitButton);
                    break;
                }
            }
        }

        if (enemyChosen) {
            for (UnitButton unitButton : defenderUnitButtons) {
                if (e.getSource() == unitButton) {
                    enemyArmyPanel.remove(unitButton);
                    boardLabel.addDefender(unitButton);
                    enemyChosen = false;
                    enemyArmyPanel.getEnemyUnitButtons().remove(unitButton);
                    this.reload();
                    break;
                }
            }
        }

        if (e.getSource() == logPanel.getStartBattleButton()) {
            if (boardLabel.getAttacker() != null && boardLabel.getDefender() != null) {
                UnitButton damagedUnit = boardLabel.startBattle();
                if (boardLabel.isPlayerFlag()) {
                    playerArmyPanel.add(damagedUnit);
                    playerArmyPanel.getPlayerUnitButtons().add(damagedUnit);
                } else {
                    enemyArmyPanel.add(damagedUnit);
                    enemyArmyPanel.getEnemyUnitButtons().add(damagedUnit);
                }
                logPanel.getLogPane().setText(boardLabel.getMessage());
                boardLabel.setMessage("");
                if (enemyArmyPanel.getEnemyUnitButtons().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "you Won", "Hooray", JOptionPane.PLAIN_MESSAGE);
                    endBattleButton.setVisible(true);
                    whoWon = true;
                }
                if (playerArmyPanel.getPlayerUnitButtons().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "you lost", "cry", JOptionPane.PLAIN_MESSAGE);
                    endBattleButton.setVisible(true);
                    whoWon = false;
                }
            } else
                JOptionPane.showMessageDialog(null, "Must choose the units", "Action Denied", JOptionPane.WARNING_MESSAGE);
            playerChosen = true;
            enemyChosen = true;
        }
        if (e.getSource() == endBattleButton)
            this.dispose();
    }

    public boolean isWhoWon() {
        return whoWon;
    }
}
