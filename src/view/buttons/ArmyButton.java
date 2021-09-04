package view.buttons;

import units.Status;
import view.labels.ArmyInfoLabel;

import javax.swing.*;
import java.awt.*;

public class ArmyButton extends CustomToggleButton {

    private Status status;
    private String cityName;
    private ImageIcon image;
    private final ArmyInfoLabel armyInfoLabel;

    public ArmyButton(int armyNumber, Status status, String cityName, int numberOfSoldiers) {
        JLabel pictureLabel = new JLabel();
        this.status = status;
        this.cityName = cityName;
        this.armyInfoLabel = new ArmyInfoLabel(armyNumber, status, cityName, numberOfSoldiers);
        this.setBackground(Color.DARK_GRAY);
        this.setVisible(true);
        this.setPreferredSize(new Dimension(325, 135));
        this.setLayout(new GridLayout(1, 2));
        switch (cityName.toLowerCase()) {
            case "cairo":
                image = new ImageIcon("resources/images/CairoEditedKo2.jpg");
                break;
            case "sparta":
                image = new ImageIcon("resources/images/SpartanKo2.jpg");
                break;
            case "rome":
                image = new ImageIcon("resources/images/RomeEditedlko2.jpg");
                break;
        }
        pictureLabel.setIcon(image);
        pictureLabel.setPreferredSize(new Dimension(200, 135));
        pictureLabel.setVisible(true);
        this.add(pictureLabel);
        this.add(armyInfoLabel);
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public ArmyInfoLabel getArmyInfoLabel() {
        return armyInfoLabel;
    }
}
