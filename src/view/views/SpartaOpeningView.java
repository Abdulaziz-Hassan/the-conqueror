package view.views;

import view.buttons.CustomButton;
import view.labels.CustomLabel;
import view.panels.CityPanel;
import view.tools.CustomFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SpartaOpeningView extends CustomFrame implements ActionListener {

    private final CityPanel cityPanel = new CityPanel();
    private final CustomButton closeButton;
    private final SpartaCityView spartaCityView;
    private final DefendingArmySpartaView defendingArmySpartaView = new DefendingArmySpartaView();

    public SpartaOpeningView(boolean isMale, String namePlayer, double playerTreasury, double playerFood) {
        cityPanel.getCityButtonsPanel().getDefendingArmyButton().addActionListener(this);
        spartaCityView = new SpartaCityView(isMale, namePlayer, playerTreasury, playerFood);
        spartaCityView.setVisible(false);
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
        ImageIcon spartaLogo = new ImageIcon("resources/images/Spartan.jpeg");
        CustomLabel spartaLogoLabel = new CustomLabel();
        spartaLogoLabel.setOpaque(false);
        spartaLogoLabel.setIcon(spartaLogo);
        spartaLogoLabel.setVerticalAlignment(JLabel.CENTER);
        spartaLogoLabel.setHorizontalAlignment(JLabel.CENTER);
        mapLabel.add(cityPanel.getCityButtonsPanel());
        spartaLogoLabel.setBounds(500, 200, 500, 500);
        mapLabel.add(spartaLogoLabel);
        cityPanel.setLayout(new BorderLayout());
        spartaLogoLabel.setVisible(true);
        mapLabel.add(spartaLogoLabel);
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
            spartaCityView.setVisible(true);
            this.dispose();
        }
        if (e.getSource() == cityPanel.getCityButtonsPanel().getDefendingArmyButton()) {
            defendingArmySpartaView.setVisible(true);
            this.dispose();
        }
    }

    public SpartaCityView getSpartaCityView() {
        return spartaCityView;
    }

    public DefendingArmySpartaView getDefendingArmySpartaView() {
        return defendingArmySpartaView;
    }
}
