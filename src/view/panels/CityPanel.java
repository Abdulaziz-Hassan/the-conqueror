package view.panels;

import view.panels.CityButtonsPanel;

import javax.swing.*;
import java.awt.*;

public class CityPanel extends JPanel {

    private final CityButtonsPanel cityButtonsPanel;

    public CityPanel() {
        cityButtonsPanel = new CityButtonsPanel();
        this.setLayout(null);
        this.setBounds(550, 100, 400, 600);
        this.setLayout(new BorderLayout());
        cityButtonsPanel.setBounds(615, 715, 300, 100);
        this.add(cityButtonsPanel);
        this.setVisible(true);
    }

    public CityButtonsPanel getCityButtonsPanel() {
        return cityButtonsPanel;
    }
}