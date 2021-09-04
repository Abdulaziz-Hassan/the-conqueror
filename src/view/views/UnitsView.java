package view.views;

import units.*;
import view.buttons.CloseButton;
import view.buttons.CustomButton;
import view.buttons.UnitButton;
import view.tools.CustomFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;

public class UnitsView extends CustomFrame implements ActionListener {

    private JLabel status = new JLabel();
    private final JPanel rightPanel = new JPanel();
    private final JPanel leftPanel = new JPanel();
    private int i = 0;
    private final JLabel numberOfUnits = new JLabel();
    private final ArrayList<UnitButton> unitButtons = new ArrayList<>();
    private ArrayList<Unit> units = new ArrayList<>();
    private CloseButton closeButton = new CloseButton();
    private boolean marchingFlag;
    private boolean besiegingFlag;
    private boolean idleFlag = true;
    private final CustomButton targetCairoButton = new CustomButton();
    private final CustomButton targetSpartaButton = new CustomButton();
    private final CustomButton targetRomeButton = new CustomButton();
    private String targetCityName;
    private final JLabel distanceLabel = new JLabel();
    private final CustomButton autoResolveButton = new CustomButton();
    private final CustomButton manualBattleButton = new CustomButton();
    private final CustomButton laySiegeButton = new CustomButton();
    private final JLabel targetCityNameLabel = new JLabel();
    private final JLabel turnsUnderSiegeLabel = new JLabel();
    private BattleView battleView;
    private HashMap<String, ArrayList<UnitButton>> enemyUnitButtonsMap;
    private int distanceLeft = 100;
    private boolean flag;
    private int turnsUnderSiege = 3;

    public UnitsView(Unit unit) {
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
        this.addUnitToArmy(unit);
        manualBattleButton.addActionListener(this);
        this.reload();
    }

    public void initializeRightPanel() {
        rightPanel.setLayout(null);
        closeButton.setBounds(140, 2, 90, 70);
        closeButton.addActionListener(this);
        rightPanel.add(closeButton);
        if (this.idleFlag) {
            targetCairoButton.setVisible(true);
            targetCairoButton.setBounds(20, 450, 200, 50);
            targetCairoButton.setForeground(Color.WHITE);
            targetCairoButton.setFont(new Font("NEW ROMAN", Font.BOLD, 25));
            targetCairoButton.setBackground(Color.BLACK);
            targetCairoButton.setText("Target Cairo");
            rightPanel.add(targetCairoButton);
            targetRomeButton.setVisible(true);
            targetRomeButton.setBounds(20, 510, 200, 50);
            targetRomeButton.setForeground(Color.WHITE);
            targetRomeButton.setFont(new Font("NEW ROMAN", Font.BOLD, 25));
            targetRomeButton.setBackground(Color.BLACK);
            targetRomeButton.setText("Target Rome");
            rightPanel.add(targetRomeButton);
            targetSpartaButton.setVisible(true);
            targetSpartaButton.setBounds(20, 570, 200, 50);
            targetSpartaButton.setForeground(Color.WHITE);
            targetSpartaButton.setFont(new Font("NEW ROMAN", Font.BOLD, 25));
            targetSpartaButton.setBackground(Color.BLACK);
            targetSpartaButton.setText("Target Sparta");
            rightPanel.add(targetSpartaButton);
        }

        JLabel logoPic = new JLabel();
        logoPic.setIcon(new ImageIcon("resources/images/THE_Conquror_Logo.Edited.jpg"));
        logoPic.setBounds(20, 100, 200, 250);
        status.setBounds(20, 250, 250, 250);
        status.setVisible(true);

        if (besiegingFlag)
            status.setText("Besieging Army");
        if (idleFlag)
            status.setText("Idle Army");
        if (marchingFlag)
            status.setText("Marching Army");
        status.setFont(new Font("NEW ROMAN", Font.BOLD, 20));
        numberOfUnits.setBounds(50, 300, 250, 250);
        numberOfUnits.setVisible(true);
        numberOfUnits.setText("Total Units :" + i);
        numberOfUnits.setFont(new Font("NEW ROMAN", Font.BOLD, 20));
        rightPanel.add(numberOfUnits);
        rightPanel.add(status);
        rightPanel.add(logoPic);
        rightPanel.repaint();
        rightPanel.revalidate();
    }


    public void reinitializeRightPanel() {
        if (besiegingFlag)
            status.setText("Besieging Army");
        if (idleFlag)
            status.setText("Idle Army");
        if (marchingFlag) {
            status.setText("Marching Army");
            targetCairoButton.setVisible(false);
            targetSpartaButton.setVisible(false);
            targetRomeButton.setVisible(false);
            rightPanel.remove(targetCairoButton);
            rightPanel.remove(targetRomeButton);
            rightPanel.remove(targetSpartaButton);
            targetCityNameLabel.setVisible(true);
            targetCityNameLabel.setBounds(20, 500, 200, 50);
            targetCityNameLabel.setForeground(Color.WHITE);
            targetCityNameLabel.setFont(new Font("NEW ROMAN", Font.BOLD, 15));
            targetCityNameLabel.setBackground(Color.BLACK);
            targetCityNameLabel.setText("Target City Name :" + targetCityName);
            targetCityNameLabel.setOpaque(true);
            rightPanel.add(targetCityNameLabel);
            distanceLabel.setVisible(true);
            distanceLabel.setBounds(20, 550 + 10, 200, 50);
            distanceLabel.setForeground(Color.WHITE);
            distanceLabel.setFont(new Font("NEW ROMAN", Font.BOLD, 15));
            distanceLabel.setBackground(Color.BLACK);
            distanceLabel.setText("Distance to Target : " + distanceLeft);
            distanceLabel.setOpaque(true);
            rightPanel.add(distanceLabel);
            for (UnitButton unitButton : unitButtons)
                unitButton.getUnitInfoLabel().getStatusLabel().setText("Marching");
        } else if (besiegingFlag) {
            targetCairoButton.setVisible(false);
            targetRomeButton.setVisible(false);
            targetSpartaButton.setVisible(false);
            rightPanel.remove(targetCairoButton);
            rightPanel.remove(targetSpartaButton);
            rightPanel.remove(targetRomeButton);
            turnsUnderSiegeLabel.setVisible(true);
            turnsUnderSiegeLabel.setBounds(20, 650 - 100, 200, 50);
            turnsUnderSiegeLabel.setForeground(Color.WHITE);
            turnsUnderSiegeLabel.setFont(new Font("NEW ROMAN", Font.BOLD, 17));
            turnsUnderSiegeLabel.setBackground(Color.BLACK);
            turnsUnderSiegeLabel.setText("Turns Under Siege : 3");
            turnsUnderSiegeLabel.setOpaque(true);
            rightPanel.add(turnsUnderSiegeLabel);
            autoResolveButton.setVisible(true);
            autoResolveButton.setBounds(20, 650, 200, 50);
            autoResolveButton.setForeground(Color.WHITE);
            autoResolveButton.setFont(new Font("NEW ROMAN", Font.BOLD, 15));
            autoResolveButton.setBackground(Color.BLACK);
            autoResolveButton.setText("Auto-Resolve ");
            autoResolveButton.setOpaque(true);
            rightPanel.add(autoResolveButton);
            manualBattleButton.setVisible(true);
            manualBattleButton.setBounds(20, 650 + 60, 200, 50);
            manualBattleButton.setForeground(Color.WHITE);
            manualBattleButton.setFont(new Font("NEW ROMAN", Font.BOLD, 15));
            manualBattleButton.setBackground(Color.BLACK);
            manualBattleButton.setText("Battle");
            manualBattleButton.setOpaque(true);
            rightPanel.add(manualBattleButton);
            for (UnitButton unitButton : unitButtons)
                unitButton.getUnitInfoLabel().getStatusLabel().setText("Besieging");
            targetCityNameLabel.setVisible(true);
            targetCityNameLabel.setBounds(20, 500, 200, 50);
            targetCityNameLabel.setForeground(Color.WHITE);
            targetCityNameLabel.setFont(new Font("NEW ROMAN", Font.BOLD, 25));
            targetCityNameLabel.setBackground(Color.BLACK);
            targetCityNameLabel.setText("Target City Name :" + targetCityName);
            rightPanel.add(targetCityNameLabel);
        }
        rightPanel.repaint();
        rightPanel.revalidate();
    }

    public void takeAction() {
        turnsUnderSiege--;
        if (turnsUnderSiege == 0)
            JOptionPane.showMessageDialog(null, "Battle or AutoResolve", "Action Denied ", JOptionPane.WARNING_MESSAGE);
        else
            turnsUnderSiegeLabel.setText("Turns UnderSiege : " + turnsUnderSiege);
    }

    public void modify() {
        if (distanceLeft == 0) {
            JOptionPane.showMessageDialog(null, "You have reached the enemy city, take an action Manual Battle, Auto Resolve or Lay Siege", "Take Action",
                    JOptionPane.INFORMATION_MESSAGE);
            distanceLabel.setVisible(false);
            rightPanel.remove(distanceLabel);
            distanceLabel.setText("Distance to Target : " + distanceLeft);
            autoResolveButton.setVisible(true);
            autoResolveButton.setBounds(20, 600 + 20, 200, 50);
            autoResolveButton.setForeground(Color.WHITE);
            autoResolveButton.setFont(new Font("NEW ROMAN", Font.BOLD, 15));
            autoResolveButton.setBackground(Color.BLACK);
            autoResolveButton.setText("Auto-Resolve ");
            autoResolveButton.setOpaque(true);
            rightPanel.add(autoResolveButton);
            laySiegeButton.setVisible(true);
            laySiegeButton.setBounds(20, 650 + 30, 200, 50);
            laySiegeButton.setForeground(Color.WHITE);
            laySiegeButton.setFont(new Font("NEW ROMAN", Font.BOLD, 15));
            laySiegeButton.setBackground(Color.BLACK);
            laySiegeButton.setText("Lay Siege");
            laySiegeButton.setOpaque(true);
            rightPanel.add(laySiegeButton);
            manualBattleButton.setVisible(true);
            manualBattleButton.setBounds(20, 650 + 50 + 40, 200, 50);
            manualBattleButton.setForeground(Color.WHITE);
            manualBattleButton.setFont(new Font("NEW ROMAN", Font.BOLD, 15));
            manualBattleButton.setBackground(Color.BLACK);
            manualBattleButton.setText("Battle");
            manualBattleButton.setOpaque(true);
            rightPanel.add(manualBattleButton);
        }
    }

    public void updateDistance() {
        distanceLeft--;
        this.modify();
    }


    public void addUnitToArmy(Unit unit) {
        UnitButton tempButton;
        int x = 9;
        if (i > x)
            return;
        units.add(unit);
        if (unit instanceof Archer) {
            tempButton = new UnitButton(Status.IDLE, unit.getLevel(), "Archer", unit.getCurrentSoldierCount(), unit.getMaxSoldierCount(), unit);
            if (!unitButtons.contains(tempButton)) {
                leftPanel.add(tempButton);
                unitButtons.add(tempButton);
            }
        } else if (unit instanceof Infantry) {
            tempButton = new UnitButton(Status.IDLE, unit.getLevel(), "Infantry", unit.getCurrentSoldierCount(), unit.getMaxSoldierCount(), unit);
            if (!unitButtons.contains(tempButton)) {
                leftPanel.add(tempButton);
                unitButtons.add(tempButton);
            }
        } else if (unit instanceof Cavalry) {
            tempButton = new UnitButton(Status.IDLE, unit.getLevel(), "Cavalry", unit.getCurrentSoldierCount(), unit.getMaxSoldierCount(), unit);
            if (!unitButtons.contains(tempButton)) {
                leftPanel.add(tempButton);
                unitButtons.add(tempButton);
            }
        }
        i++;
        if (i != x)
            numberOfUnits.setText("Total Units :" + i);
        leftPanel.revalidate();
        leftPanel.repaint();
        rightPanel.repaint();
        rightPanel.revalidate();
        this.reload();
    }


    public void updateToMarchingFrame() {
        this.marchingFlag = true;
        this.besiegingFlag = false;
        this.idleFlag = false;
        this.reinitializeRightPanel();
        this.reload();
    }

    public void resetTargetButtons() {
        targetRomeButton.setVisible(true);
        targetSpartaButton.setVisible(true);
        targetRomeButton.setVisible(true);
    }

    public void updateToBesiegingFrame() {
        this.marchingFlag = false;
        this.besiegingFlag = true;
        this.idleFlag = false;
        this.reinitializeRightPanel();
        this.reload();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == closeButton)
            this.dispose();
        if (e.getSource() == manualBattleButton) {
            if (enemyUnitButtonsMap != null) {
                battleView = new BattleView(targetCityName.toLowerCase(), enemyUnitButtonsMap.get(targetCityName.toLowerCase()), unitButtons);
                flag = true;
            }
        }
    }

    public JLabel getStatus() {
        return status;
    }

    public void setStatus(JLabel status) {
        this.status = status;
    }

    public ArrayList<UnitButton> getUnitButtons() {
        return unitButtons;
    }

    public ArrayList<Unit> getUnits() {
        return units;
    }

    public void setUnits(ArrayList<Unit> units) {
        this.units = units;
    }

    public CloseButton getCloseButton() {
        return closeButton;
    }

    public void setCloseButton(CloseButton closeButton) {
        this.closeButton = closeButton;
    }

    public CustomButton getTargetCairoButton() {
        return targetCairoButton;
    }

    public CustomButton getTargetSpartaButton() {
        return targetSpartaButton;
    }

    public CustomButton getTargetRomeButton() {
        return targetRomeButton;
    }


    public String getTargetCityName() {
        return targetCityName;
    }

    public void setTargetCityName(String targetCityName) {
        this.targetCityName = targetCityName;
    }


    public CustomButton getAutoResolveButton() {
        return autoResolveButton;
    }

    public CustomButton getLaySiegeButton() {
        return laySiegeButton;
    }

    public JLabel getTargetCityNameLabel() {
        return targetCityNameLabel;
    }

    public BattleView getBattleView() {
        return battleView;
    }

    public void setEnemyUnitButtonsMap(HashMap<String, ArrayList<UnitButton>> enemyUnitButtonsMap) {
        this.enemyUnitButtonsMap = enemyUnitButtonsMap;
    }

    public void setDistanceLeft(int distanceLeft) {
        this.distanceLeft = distanceLeft;
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public int getI() {
        return i;
    }
}
