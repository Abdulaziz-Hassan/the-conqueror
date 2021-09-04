package view.labels;

import exceptions.FriendlyFireException;
import units.Unit;
import view.buttons.UnitButton;

import javax.swing.*;
import java.awt.*;

public class BoardLabel extends JLabel {

    private UnitButton attackerButton;
    private UnitButton defenderButton;
    private Unit attacker;
    private Unit defender;
    private String message;
    private boolean playerFlag;

    public BoardLabel() {
        this.setVisible(true);
        this.setOpaque(true);
        this.setBackground(Color.WHITE);
        this.setBounds(0, 300, 1200, 400);
    }

    public void addAttacker(UnitButton unitButton) {
        this.setBorder(BorderFactory.createLineBorder(Color.BLACK, 4));
        attackerButton = unitButton;
        attacker = attackerButton.getUnit();
        attackerButton.setVisible(true);
        attackerButton.setBounds(400, 250, 400, 150);
        this.add(unitButton);
        this.repaint();
        this.revalidate();
    }

    public void removeDeadAttacker() {
        this.remove(attackerButton);
    }

    public void updateAttackPlayerUnit(int oldSoldierCount) {
        message += "Enemy Unit attacked Attack Unit \n Attack Unit lost " +
                (oldSoldierCount - attacker.getCurrentSoldierCount()) + "\n";
        if (attacker.getCurrentSoldierCount() <= 0)
            removeDeadAttacker();
    }

    public void addDefender(UnitButton unitButton) {
        defenderButton = unitButton;
        defender = defenderButton.getUnit();
        defenderButton.setVisible(true);
        defenderButton.setBounds(400, 0, 400, 150);
        this.add(defenderButton);
        this.repaint();
        this.revalidate();
    }

    public void removeDefender() {
        this.remove(defenderButton);
    }

    public UnitButton startBattle() {
        boolean attackTurn = true;
        int old;
        while (defender.getCurrentSoldierCount() > 0 && attacker.getCurrentSoldierCount() > 0) {
            if (attackTurn) {
                try {
                    old = defender.getCurrentSoldierCount();
                    attacker.attack(defender);
                    defenderButton.getUnitInfoLabel().getSoldierCountLabel().setText("Health :" + defender.getCurrentSoldierCount());
                    updateDefender(old);
                } catch (FriendlyFireException e) {
                    JOptionPane.showMessageDialog(null, e.getMessage(), "Action Denied", JOptionPane.WARNING_MESSAGE);
                }
            } else {
                try {
                    old = attacker.getCurrentSoldierCount();
                    defender.attack(attacker);
                    updateAttackPlayerUnit(old);
                    attackerButton.getUnitInfoLabel().getSoldierCountLabel().setText("Health :" + attacker.getCurrentSoldierCount());
                } catch (FriendlyFireException e) {
                    JOptionPane.showMessageDialog(null, e.getMessage(), "Action Denied", JOptionPane.WARNING_MESSAGE);
                }
            }
            attackTurn = !attackTurn;
        }
        this.revalidate();
        this.repaint();
        if (attacker.getCurrentSoldierCount() == 0) {
            playerFlag = false;
            UnitButton enButton = defenderButton;
            defender = null;
            attacker = null;
            attackerButton = null;
            defenderButton = null;
            return enButton;
        } else {
            playerFlag = true;
            UnitButton enButton = attackerButton;
            defender = null;
            attacker = null;
            attackerButton = null;
            defenderButton = null;
            return enButton;
        }
    }

    public void updateDefender(int oldSoldierCount) {
        message += " Attack Unit attacked Enemy Unit \n Enemy Unit lost " + (oldSoldierCount - defender.getCurrentSoldierCount()) + " \n";
        if (defender.getCurrentSoldierCount() <= 0)
            removeDefender();
    }

    public Unit getAttacker() {
        return attacker;
    }

    public Unit getDefender() {
        return defender;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isPlayerFlag() {
        return playerFlag;
    }
}
