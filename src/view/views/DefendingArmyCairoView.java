package view.views;

import units.*;
import view.buttons.UnitButton;
import view.buttons.CloseButton;
import view.buttons.CustomButton;
import view.tools.CustomFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class DefendingArmyCairoView extends CustomFrame implements ActionListener {

    private final JPanel rightPanel = new JPanel();
    private final JPanel leftPanel = new JPanel();
    private int i = 0;
    private final CustomButton relocateUnitButton = new CustomButton();
    private final CustomButton initiateArmyButton = new CustomButton();
    private final JLabel numberOfUnits = new JLabel();
    private final ArrayList<UnitButton> unitButtons = new ArrayList<>();
    private final JLabel playerNameLabel = new JLabel();
    private final JLabel treasuryLabel = new JLabel();
    private final JLabel foodLabel = new JLabel();
    private final ArrayList<Unit> correspondingDefendingArmy = new ArrayList<>();
    private final CloseButton closeButton = new CloseButton();

    public DefendingArmyCairoView() {
        this.setLayout(null);
        this.setVisible(false);
        rightPanel.setBounds(1301, 0, 300, 1000);
        rightPanel.setBackground(Color.WHITE);
        leftPanel.setBounds(0, 0, 1300, 1000);
        leftPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
        rightPanel.setVisible(true);
        leftPanel.setVisible(true);
        initializeRightPanel();
        this.add(rightPanel);
        this.add(leftPanel);
        this.reload();
    }

    public void initializeRightPanel() {
        closeButton.setBounds(140, 2, 90, 70);
        closeButton.addActionListener(this);
        rightPanel.add(closeButton);
        rightPanel.setLayout(null);
        relocateUnitButton.setVisible(true);
        relocateUnitButton.setBounds(20, 450, 200, 50);
        relocateUnitButton.setForeground(Color.WHITE);
        relocateUnitButton.setFont(new Font("NEW ROMAN", Font.BOLD, 25));
        relocateUnitButton.setBackground(Color.BLACK);
        relocateUnitButton.setText("Relocate Unit");
        rightPanel.add(relocateUnitButton);
        initiateArmyButton.setVisible(true);
        initiateArmyButton.setEnabled(false);
        initiateArmyButton.setBounds(20, 550, 200, 50);
        initiateArmyButton.setForeground(Color.WHITE);
        initiateArmyButton.setFont(new Font("NEW ROMAN", Font.BOLD, 25));
        initiateArmyButton.setBackground(Color.BLACK);
        initiateArmyButton.setText("Initiate Army");
        rightPanel.add(initiateArmyButton);
        JLabel cairoPic = new JLabel();
        cairoPic.setIcon(new ImageIcon("resources/images/CairoEditedKo.jpg"));
        cairoPic.setBounds(20, 100, 200, 250);
        cairoPic.setText("Cairo Defending Army");
        JLabel cairoName = new JLabel();
        cairoName.setBounds(20, 250, 250, 250);
        cairoName.setVisible(true);
        cairoName.setText("Cairo Defending Army");
        cairoName.setFont(new Font("NEW ROMAN", Font.BOLD, 20));
        numberOfUnits.setBounds(50, 300, 250, 250);
        numberOfUnits.setVisible(true);
        numberOfUnits.setText("Total Units :" + i);
        numberOfUnits.setFont(new Font("NEW ROMAN", Font.BOLD, 20));
        rightPanel.add(numberOfUnits);
        rightPanel.add(cairoName);
        rightPanel.add(cairoPic);
        playerNameLabel.setBounds(50, 550 + 50 + 40, 400, 40);
        playerNameLabel.setVisible(true);
        playerNameLabel.setText("Player Name:");
        playerNameLabel.setFont(new Font("NEW ROMAN", Font.BOLD, 20));
        treasuryLabel.setBounds(50, 550 + 50 + 40 + 50, 400, 40);
        treasuryLabel.setVisible(true);
        treasuryLabel.setText("Treasury :");
        treasuryLabel.setFont(new Font("NEW ROMAN", Font.BOLD, 20));
        foodLabel.setBounds(50, 550 + 50 + 40 + 50 + 50, 400, 40);
        foodLabel.setVisible(true);
        foodLabel.setText("Food :");
        foodLabel.setFont(new Font("NEW ROMAN", Font.BOLD, 20));
        rightPanel.add(treasuryLabel);
        rightPanel.add(foodLabel);
        rightPanel.add(playerNameLabel);
    }


    public void addToView(Unit unit) {
        if (unit == null || unit.getCurrentSoldierCount() == 0)
            return;
        UnitButton tempButton;
        int x = 24;
        if (i > x)
            return;
        if (unit instanceof Archer) {
            tempButton = new UnitButton(Status.IDLE, unit.getLevel(), "Archer", unit.getCurrentSoldierCount(), unit.getMaxSoldierCount(), unit);
            if (!unitButtons.contains(tempButton)) {
                leftPanel.add(tempButton);
                unitButtons.add(tempButton);
                correspondingDefendingArmy.add(unit);
            }
        } else if (unit instanceof Infantry) {
            tempButton = new UnitButton(Status.IDLE, unit.getLevel(), "Infantry", unit.getCurrentSoldierCount(), unit.getMaxSoldierCount(), unit);
            if (!unitButtons.contains(tempButton)) {
                leftPanel.add(tempButton);
                unitButtons.add(tempButton);
                correspondingDefendingArmy.add(unit);
            }
        } else if (unit instanceof Cavalry) {
            tempButton = new UnitButton(Status.IDLE, unit.getLevel(), "Cavalry", unit.getCurrentSoldierCount(), unit.getMaxSoldierCount(), unit);
            if (!unitButtons.contains(tempButton)) {
                leftPanel.add(tempButton);
                correspondingDefendingArmy.add(unit);
                unitButtons.add(tempButton);
            }
        }
        i++;
        if (i != 25)
            numberOfUnits.setText("Total Units :" + i);
        this.reload();
    }

    public UnitButton moveToControllingArmiesView(Unit unit) {
        int index = correspondingDefendingArmy.indexOf(unit);
        UnitButton unitButton = unitButtons.get(index);
        unitButtons.remove(index);
        System.out.println(unitButton);
        i--;
        numberOfUnits.setText("Total Units :" + i);
        correspondingDefendingArmy.remove(index);
        leftPanel.remove(unitButton);
        leftPanel.revalidate();
        leftPanel.repaint();
        this.reload();
        return unitButton;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == closeButton)
            this.dispose();
    }

    public CustomButton getRelocateUnitButton() {
        return relocateUnitButton;
    }

    public CustomButton getInitiateArmyButton() {
        return initiateArmyButton;
    }

    public ArrayList<UnitButton> getUnitButtons() {
        return unitButtons;
    }

    public ArrayList<Unit> getCorrespondingDefendingArmy() {
        return correspondingDefendingArmy;
    }

    public CloseButton getCloseButton() {
        return closeButton;
    }

    public JLabel getPlayerNameLabel() {
        return playerNameLabel;
    }

    public JLabel getTreasuryLabel() {
        return treasuryLabel;
    }

    public JLabel getFoodLabel() {
        return foodLabel;
    }
}
