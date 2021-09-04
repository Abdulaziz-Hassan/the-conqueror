package view.panels;

import view.buttons.CustomButton;

import javax.swing.*;
import java.awt.*;

public class CityButtonsPanel extends JPanel {

    private final CustomButton idleButton = new CustomButton();
    private final CustomButton defendingArmyButton = new CustomButton();
    private final CustomButton cityViewButton = new CustomButton();
    private final ImageIcon idleButtonIcon = new ImageIcon("resources/images/idleIconEdited.jpg");
    private final ImageIcon defendingArmyButtonIcon = new ImageIcon("resources/images/shieldIconEdited.jpg");
    private final ImageIcon cityViewButtonIcon = new ImageIcon("resources/images/BuildingIconEdited.jpg");
    private CustomButton closeButton = new CustomButton();

    public CityButtonsPanel() {
        closeButton.setText("❌");
        closeButton.setBackground(Color.white);
        closeButton.setBounds(100, 10, 50, 50);
        this.add(closeButton);
        this.setVisible(true);
        this.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
        this.setSize(100, 100);
        this.setOpaque(false);
        cityViewButton.setPreferredSize(new Dimension(50, 50));
        idleButton.setPreferredSize(new Dimension(50, 50));
        defendingArmyButton.setPreferredSize(new Dimension(50, 50));
        initializeImages();
        this.add(cityViewButton);
        this.add(idleButton);
        this.add(defendingArmyButton);
    }

    public void initializeImages() {
        idleButton.setIcon(idleButtonIcon);
        defendingArmyButton.setIcon(defendingArmyButtonIcon);
        cityViewButton.setIcon(cityViewButtonIcon);
    }

    public CustomButton getCityViewButton() {
        return cityViewButton;
    }

    public CustomButton getCloseButton() {
        return closeButton;
    }

    public void setCloseButton(CustomButton closeButton) {
        this.closeButton = closeButton;
    }

    public CustomButton getDefendingArmyButton() {
        return defendingArmyButton;
    }
}
