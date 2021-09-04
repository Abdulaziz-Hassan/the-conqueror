package view.labels;

import units.Status;

import javax.swing.*;
import java.awt.*;

public class ArmyInfoLabel extends JLabel {

    private Status status;
    private String cityName;
    private final int armyNumber;
    private int distanceToTarget;
    private final JLabel targetCityLabel;
    private final JLabel distanceToTargetLabel;
    private final JLabel statusLabel;
    private final JLabel cityNameLabel;
    private final JLabel armyNumberLabel;

    public ArmyInfoLabel(int armyNumber, Status status, String cityName, int numberOfSoldiers) {
        this.status = status;
        this.cityName = cityName;
        this.armyNumber = armyNumber;
        this.targetCityLabel = new JLabel();
        this.distanceToTargetLabel = new JLabel();
        this.statusLabel = new JLabel();
        this.cityNameLabel = new JLabel();
        this.armyNumberLabel = new JLabel();
        this.setVisible(true);
        this.setPreferredSize(new Dimension(125, 135));
        this.setBackground(Color.WHITE);
        this.setVisible(true);
        this.setOpaque(true);
        this.setEnabled(false);
        initialize();
        this.repaint();
        this.revalidate();
    }

    public void initialize() {
        armyNumberLabel.setBounds(0, 0, 120, 20);
        armyNumberLabel.setText("Army Number : " + armyNumber);
        armyNumberLabel.setVisible(true);
        this.add(armyNumberLabel);

        cityNameLabel.setBounds(0, 44 - 22, 120, 20);
        cityNameLabel.setText("City Name : " + cityName);
        cityNameLabel.setVisible(true);
        this.add(cityNameLabel);


        statusLabel.setBounds(0, 66 - 22, 120, 20);
        statusLabel.setText("Statues : " + status);
        statusLabel.setVisible(true);
        this.add(statusLabel);

        distanceToTargetLabel.setBounds(0, 88 - 22, 120, 20);
        distanceToTargetLabel.setText("distance target : " + distanceToTarget);
        distanceToTargetLabel.setVisible(false);
        this.add(distanceToTargetLabel);

        targetCityLabel.setBounds(0, 88, 120, 20);
        targetCityLabel.setText("target City: ");
        targetCityLabel.setVisible(false);
        this.add(targetCityLabel);
    }

    public JLabel getArmyNumberLabel() {
        return armyNumberLabel;
    }

    public Status getStatus() {
        return status;
    }

    public String getCityName() {
        return cityName;
    }

    public int getArmyNumber() {
        return armyNumber;
    }

    public JLabel getTargetCityLabel() {
        return targetCityLabel;
    }

    public JLabel getDistanceToTargetLabel() {
        return distanceToTargetLabel;
    }

    public JLabel getStatusLabel() {
        return statusLabel;
    }

    public JLabel getCityNameLabel() {
        return cityNameLabel;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }
}

