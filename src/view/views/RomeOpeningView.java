package view.views;

import view.buttons.CustomButton;
import view.labels.CustomLabel;
import view.panels.CityPanel;
import view.tools.CustomFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RomeOpeningView extends CustomFrame implements ActionListener {

    private final CityPanel cityPanel = new CityPanel();
    private final CustomButton closeButton;
    private final RomeCityView romeCityView;
    private final DefendingArmyRomeView defendingArmyRomeView = new DefendingArmyRomeView();

    public RomeOpeningView(boolean isMale, String namePlayer, double playerTreasury, double playerFood) {
        cityPanel.getCityButtonsPanel().getDefendingArmyButton().addActionListener(this);
        romeCityView = new RomeCityView(isMale, namePlayer, playerTreasury, playerFood);
        romeCityView.setVisible(false);
        cityPanel.getCityButtonsPanel().getCityViewButton().addActionListener(this);
        closeButton = cityPanel.getCityButtonsPanel().getCloseButton();
        closeButton.addActionListener(this);
        cityPanel.setVisible(true);
        this.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
        JLabel mapLabel = new JLabel();
        ImageIcon image = new ImageIcon("resources/images/newMapEdited.jpg");
        mapLabel.setIcon(image);
        mapLabel.setOpaque(false);
        mapLabel.setVisible(true);
        mapLabel.setVerticalAlignment(JLabel.CENTER);
        mapLabel.setHorizontalAlignment(JLabel.CENTER);
        mapLabel.setBounds(6, -5, 1530, 850);
        mapLabel.setLayout(null);
        ImageIcon romeLogo = new ImageIcon("resources/images/RomeEdited9.jpg");
        CustomLabel romeLogoLabel = new CustomLabel();
        romeLogoLabel.setOpaque(false);
        romeLogoLabel.setIcon(romeLogo);
        romeLogoLabel.setVerticalAlignment(JLabel.CENTER);
        romeLogoLabel.setHorizontalAlignment(JLabel.CENTER);
        mapLabel.add(cityPanel.getCityButtonsPanel());
        romeLogoLabel.setBounds(500, 200, 500, 500);
        mapLabel.add(romeLogoLabel);
        cityPanel.setLayout(new BorderLayout());
        romeLogoLabel.setVisible(true);
        mapLabel.add(romeLogoLabel);
        this.setVisible(false);
        this.add(mapLabel);
        this.add(cityPanel);
        this.reload();
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == closeButton)
            this.dispose();
        if (e.getSource() == cityPanel.getCityButtonsPanel().getCityViewButton()) {
            romeCityView.setVisible(true);
            this.dispose();

        }
        if (e.getSource() == cityPanel.getCityButtonsPanel().getDefendingArmyButton()) {
            defendingArmyRomeView.setVisible(true);
            this.dispose();
        }
    }

    public RomeCityView getRomeCityView() {
        return romeCityView;
    }

    public DefendingArmyRomeView getDefendingArmyRomeView() {
        return defendingArmyRomeView;
    }
}
