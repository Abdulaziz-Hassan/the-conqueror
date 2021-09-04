package view.views;

import units.Army;
import units.Status;
import view.buttons.ArmyButton;
import view.buttons.CloseButton;
import view.buttons.UnitButton;
import view.labels.AddUnitLabel;
import view.tools.CustomFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class ControlledArmiesView extends CustomFrame implements ActionListener {

    private final AddUnitLabel addUnitLabel = new AddUnitLabel();
    private final JPanel rightPanel = new JPanel();
    private final JPanel leftPanel = new JPanel();
    private int numberOfArmies;
    private final JLabel numberOfArmiesLabel = new JLabel();
    private final ArrayList<ArmyButton> armyButtons = new ArrayList<>();
    private final ArrayList<Army> allControlledArmies = new ArrayList<>();
    private CloseButton closeButton = new CloseButton();
    private final ArrayList<UnitsView> controlledArmiesUnitsView = new ArrayList<>();
    private final JLabel playerNameLabel = new JLabel();
    private final JLabel treasuryLabel = new JLabel();
    private final JLabel foodLabel = new JLabel();

    public ControlledArmiesView() {
        this.setLayout(null);
        this.setVisible(true);
        rightPanel.setBounds(1301, 0, 300, 1000);
        rightPanel.setBackground(Color.WHITE);
        leftPanel.setBounds(0, 0, 1300, 1000);
        leftPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
        rightPanel.setVisible(true);
        leftPanel.setVisible(true);
        this.initializeRightPanel();
        addUnitLabel.setText("Add Army");
        leftPanel.add(addUnitLabel);
        this.add(rightPanel);
        this.add(leftPanel);
        this.reload();
    }

    public void initializeRightPanel() {
        rightPanel.setLayout(null);
        closeButton.setBounds(140, 2, 90, 70);
        closeButton.addActionListener(this);
        rightPanel.add(closeButton);
        leftPanel.setVisible(true);
        JLabel controlPic = new JLabel();
        controlPic.setIcon(new ImageIcon("resources/images/THE_Conquror_Logo.Edited.jpg"));
        controlPic.setBounds(20, 100, 200, 250);
        JLabel cairoName = new JLabel();
        cairoName.setBounds(20, 250 + 100, 250, 70);
        cairoName.setVisible(true);
        cairoName.setText("Controlled Armies");
        cairoName.setFont(new Font("NEW ROMAN", Font.BOLD, 20));
        numberOfArmiesLabel.setBounds(50, 250 + 170, 250, 70);
        numberOfArmiesLabel.setVisible(true);
        numberOfArmiesLabel.setText("Total Armies :" + numberOfArmies);
        numberOfArmiesLabel.setFont(new Font("NEW ROMAN", Font.BOLD, 20));
        playerNameLabel.setBounds(50, 250 + 270, 400, 70);
        playerNameLabel.setVisible(true);
        playerNameLabel.setText("Player Name:");
        playerNameLabel.setFont(new Font("NEW ROMAN", Font.BOLD, 20));
        treasuryLabel.setBounds(50, 250 + 370, 400, 70);
        treasuryLabel.setVisible(true);
        treasuryLabel.setText("Treasury :");
        treasuryLabel.setFont(new Font("NEW ROMAN", Font.BOLD, 20));
        foodLabel.setBounds(50, 250 + 470, 400, 70);
        foodLabel.setVisible(true);
        foodLabel.setText("Food :");
        foodLabel.setFont(new Font("NEW ROMAN", Font.BOLD, 20));
        rightPanel.add(treasuryLabel);
        rightPanel.add(foodLabel);
        rightPanel.add(playerNameLabel);
        rightPanel.add(numberOfArmiesLabel);
        rightPanel.add(cairoName);
        rightPanel.add(controlPic);
    }

    public void addArmyToView(Army army, String cityName) {
        ArmyButton tempButton;
        leftPanel.remove(addUnitLabel);
        int maxToHold = 24;
        if (numberOfArmies > maxToHold)
            return;
        tempButton = new ArmyButton(numberOfArmies + 1, army.getCurrentStatus(), cityName, army.getUnits().size());
        tempButton.getArmyInfoLabel().getArmyNumberLabel().setText("Army : " + (numberOfArmies + 1));
        tempButton.getArmyInfoLabel().getStatusLabel().setText("Status " + Status.IDLE);
        tempButton.getArmyInfoLabel().getCityNameLabel().setText("City : " + cityName);
        controlledArmiesUnitsView.add(new UnitsView(army.getUnits().get(0)));
        if (!allControlledArmies.contains(army)) {
            leftPanel.add(tempButton);
            armyButtons.add(tempButton);
        }
        allControlledArmies.add(army);
        numberOfArmies++;
        if (numberOfArmies < 24)
            leftPanel.add(addUnitLabel);
        if (numberOfArmies != 25)
            numberOfArmiesLabel.setText("Total Armies :" + numberOfArmies);
        leftPanel.repaint();
        leftPanel.revalidate();
        this.reload();
    }

    public void relocateUnitToUnitView(UnitButton unitButton, int index) {
        String cityName = unitButton.getUnit().getParentArmy().getCurrentLocation();
        for (ArmyButton armyButton : armyButtons)
            armyButton.setEnabled(cityName.equalsIgnoreCase(armyButton.getArmyInfoLabel().getCityName()));
        this.setVisible(true);
        this.getControlledArmiesUnitsView().get(index).addUnitToArmy(unitButton.getUnit());
        this.reload();
    }

    public void addToMarchingView(Army army) {
        if (!allControlledArmies.contains(army))
            return;
        int index = allControlledArmies.indexOf(army);
        ArmyButton armyButton = armyButtons.get(index);
        armyButtons.remove(index);
        allControlledArmies.remove(index);
        numberOfArmies--;
        numberOfArmiesLabel.setText("Total Units :" + numberOfArmies);
        leftPanel.remove(armyButton);
        this.reload();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == closeButton) {
            for (UnitsView frame : this.controlledArmiesUnitsView)
                frame.resetTargetButtons();
            this.dispose();
        }
    }

    public ArrayList<ArmyButton> getArmyButtons() {
        return armyButtons;
    }

    public ArrayList<Army> getAllControlledArmies() {
        return allControlledArmies;
    }

    public CloseButton getCloseButton() {
        return closeButton;
    }

    public ArrayList<UnitsView> getControlledArmiesUnitsView() {
        return controlledArmiesUnitsView;
    }

    public void setCloseButton(CloseButton closeButton) {
        this.closeButton = closeButton;
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

