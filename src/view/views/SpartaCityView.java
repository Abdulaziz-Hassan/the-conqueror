package view.views;

import view.buttons.BuildingButtons;
import view.tools.CustomFrame;
import view.labels.PlayerLabel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SpartaCityView extends CustomFrame implements ActionListener {

    private final JLabel spartaCityLabel = new JLabel();
    private final BuildingButtons buildingButtons = new BuildingButtons();
    private final PlayerLabel playerLabel;

    public SpartaCityView(boolean isMale, String namePlayer, double playerTreasury, double playerFood) {
        playerLabel = new PlayerLabel(isMale, namePlayer, playerTreasury, playerFood);
        playerLabel.setBounds(-20, 500, 1600, 500);
        spartaCityLabel.add(playerLabel);
        buildingButtons.getCloseButton().setBounds(50, 50, 100, 70);
        spartaCityLabel.add(buildingButtons.getCloseButton());
        buildingButtons.getCloseButton().addActionListener(this);
        ImageIcon spartaTown = new ImageIcon("resources/images/SpartaTown1Edited.jpg");
        spartaCityLabel.setIcon(spartaTown);
        spartaCityLabel.setOpaque(false);
        spartaCityLabel.setVisible(true);
        spartaCityLabel.setVerticalAlignment(JLabel.CENTER);
        spartaCityLabel.setHorizontalAlignment(JLabel.CENTER);
        spartaCityLabel.setBounds(-60, 0, 1530, 850);
        spartaCityLabel.setLayout(null);
        this.initializeBuyButtons();
        this.initializeUpgradeButtons();
        this.add(spartaCityLabel);
        this.reload();
    }

    public void initializeBuyButtons() {
        buildingButtons.getArcheryBuyButton().setBounds(350, 250, 100, 70);
        spartaCityLabel.add(buildingButtons.getArcheryBuyButton());
        buildingButtons.getArcheryBuyButton().setToolTipText("Cost : 1500");
        buildingButtons.getBarracksBuyButton().setBounds(610, 350, 100, 70);
        spartaCityLabel.add(buildingButtons.getBarracksBuyButton());
        buildingButtons.getBarracksBuyButton().setToolTipText("Cost : 2000");
        buildingButtons.getStableBuyButton().setBounds(890, 450, 100, 70);
        spartaCityLabel.add(buildingButtons.getStableBuyButton());
        buildingButtons.getStableBuyButton().setToolTipText("Cost : 2500");
        buildingButtons.getFarmBuyButton().setBounds(1110, 250, 100, 70);
        spartaCityLabel.add(buildingButtons.getFarmBuyButton());
        buildingButtons.getFarmBuyButton().setToolTipText("Cost : 1000");
        buildingButtons.getMarketBuyButton().setBounds(840, 150, 100, 70);
        spartaCityLabel.add(buildingButtons.getMarketBuyButton());
        buildingButtons.getMarketBuyButton().setToolTipText("Cost : 1500");
    }

    public void initializeUpgradeButtons() {
        buildingButtons.getArcheryUpgradeButton().setBounds(200, 330, 100, 70);
        spartaCityLabel.add(buildingButtons.getArcheryUpgradeButton());
        buildingButtons.getArcheryUpgradeButton().setToolTipText("Upgrade : ↗ Level 2  800");
        buildingButtons.getBarracksUpgradeButton().setBounds(460, 430, 100, 70);
        spartaCityLabel.add(buildingButtons.getBarracksUpgradeButton());
        buildingButtons.getBarracksUpgradeButton().setToolTipText("Upgrade : ↗ Level 2  1000");
        buildingButtons.getStableUpgradeButton().setBounds(740, 530, 100, 70);
        spartaCityLabel.add(buildingButtons.getStableUpgradeButton());
        buildingButtons.getStableUpgradeButton().setToolTipText("Upgrade : ↗ Level 2  1500");
        buildingButtons.getFarmUpgradeButton().setBounds(960, 330, 100, 70);
        spartaCityLabel.add(buildingButtons.getFarmUpgradeButton());
        buildingButtons.getFarmUpgradeButton().setToolTipText("Upgrade : ↗ Level 2  500");
        buildingButtons.getMarketUpgradeButton().setBounds(670, 170, 100, 70);
        spartaCityLabel.add(buildingButtons.getMarketUpgradeButton());
        buildingButtons.getMarketUpgradeButton().setToolTipText("Upgrade : ↗ Level 2  700");
        buildingButtons.getArcheryRecruitButton().setBounds(350, 350, 100, 70);
        spartaCityLabel.add(buildingButtons.getArcheryRecruitButton());
        buildingButtons.getArcheryRecruitButton().setToolTipText("Recruitment Cost : 400");
        buildingButtons.getBarracksRecruitButton().setBounds(610, 450, 100, 70);
        spartaCityLabel.add(buildingButtons.getBarracksRecruitButton());
        buildingButtons.getBarracksRecruitButton().setToolTipText("Recruitment Cost : 500");
        buildingButtons.getStableRecruitButton().setBounds(890, 550, 100, 70);
        spartaCityLabel.add(buildingButtons.getStableRecruitButton());
        buildingButtons.getStableRecruitButton().setToolTipText("Recruitment Cost : 600");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == buildingButtons.getCloseButton())
            this.dispose();
    }

    public PlayerLabel getPlayerLabel() {
        return playerLabel;
    }

    public BuildingButtons getBuildingButtons() {
        return buildingButtons;
    }
}
