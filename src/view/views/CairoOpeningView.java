package view.views;

import view.panels.CityPanel;
import view.buttons.CustomButton;
import view.tools.CustomFrame;
import view.labels.CustomLabel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CairoOpeningView extends CustomFrame implements ActionListener {

    private final CityPanel cityPanel = new CityPanel();
    private final CustomButton closeButton;
    private final CairoCityView cairoCityView;
    private final DefendingArmyCairoView defendingArmyCairoView = new DefendingArmyCairoView();

    public CairoOpeningView(boolean isMale, String namePlayer, double playerTreasury, double playerFood) {
        cityPanel.getCityButtonsPanel().getDefendingArmyButton().addActionListener(this);
        cairoCityView = new CairoCityView(isMale, namePlayer, playerTreasury, playerFood);
        cityPanel.getCityButtonsPanel().getCityViewButton().addActionListener(this);
        cairoCityView.setVisible(false);
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
        ImageIcon cairoLogo = new ImageIcon("resources/images/CairoEdited.jpg");
        CustomLabel cairoLogoLabel = new CustomLabel();
        cairoLogoLabel.setOpaque(false);
        cairoLogoLabel.setIcon(cairoLogo);
        cairoLogoLabel.setVerticalAlignment(JLabel.CENTER);
        cairoLogoLabel.setHorizontalAlignment(JLabel.CENTER);
        mapLabel.add(cityPanel.getCityButtonsPanel());
        cairoLogoLabel.setBounds(500, 200, 500, 500);
        mapLabel.add(cairoLogoLabel);
        cityPanel.setLayout(new BorderLayout());
        cairoLogoLabel.setVisible(true);
        mapLabel.add(cairoLogoLabel);
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
            cairoCityView.setVisible(true);
            this.dispose();
        }
        if (e.getSource() == cityPanel.getCityButtonsPanel().getDefendingArmyButton()) {
            defendingArmyCairoView.setVisible(true);
            this.dispose();
        }
    }

    public CairoCityView getCairoCityView() {
        return cairoCityView;
    }

    public DefendingArmyCairoView getDefendingArmyCairoView() {
        return defendingArmyCairoView;
    }
}
