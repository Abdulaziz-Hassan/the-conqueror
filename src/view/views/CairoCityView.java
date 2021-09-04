package view.views;

import view.buttons.BuildingButtons;
import view.tools.CustomFrame;
import view.labels.PlayerLabel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CairoCityView extends CustomFrame implements ActionListener {

    private final JLabel cairoCityLabel = new JLabel();
    private final BuildingButtons buildingButtons = new BuildingButtons();
    private final PlayerLabel playerLabel;

    public CairoCityView(boolean isMale, String namePlayer, double playerTreasury, double playerFood) {
        this.playerLabel = new PlayerLabel(isMale, namePlayer, playerTreasury, playerFood);
        playerLabel.setBounds(-20, 500, 1600, 500);
        cairoCityLabel.add(playerLabel);
        buildingButtons.getCloseButton().setBounds(50, 50, 100, 70);
        cairoCityLabel.add(buildingButtons.getCloseButton());
        buildingButtons.getCloseButton().addActionListener(this);
        ImageIcon cairoTown = new ImageIcon("resources/images/CairoTown9Edited.jpg");
        cairoCityLabel.setIcon(cairoTown);
        cairoCityLabel.setOpaque(false);
        cairoCityLabel.setVisible(true);
        cairoCityLabel.setVerticalAlignment(JLabel.CENTER);
        cairoCityLabel.setHorizontalAlignment(JLabel.CENTER);
        cairoCityLabel.setBounds(-60, 0, 1530, 850);
        cairoCityLabel.setLayout(null);
        this.initializeBuyButtons();
        this.initializeUpgradeButtons();
        this.add(cairoCityLabel);
        this.reload();
    }


    public void initializeBuyButtons() {
        buildingButtons.getArcheryBuyButton().setBounds(350, 250, 100, 70);
        cairoCityLabel.add(buildingButtons.getArcheryBuyButton());
        buildingButtons.getArcheryBuyButton().setToolTipText("Cost : 1500");
        buildingButtons.getBarracksBuyButton().setBounds(610, 350, 100, 70);
        cairoCityLabel.add(buildingButtons.getBarracksBuyButton());
        buildingButtons.getBarracksBuyButton().setToolTipText("Cost : 2000");
        buildingButtons.getStableBuyButton().setBounds(890, 450, 100, 70);
        cairoCityLabel.add(buildingButtons.getStableBuyButton());
        buildingButtons.getStableBuyButton().setToolTipText("Cost : 2500");
        buildingButtons.getFarmBuyButton().setBounds(1110, 250, 100, 70);
        cairoCityLabel.add(buildingButtons.getFarmBuyButton());
        buildingButtons.getFarmBuyButton().setToolTipText("Cost : 1000");
        buildingButtons.getMarketBuyButton().setBounds(840, 150, 100, 70);
        cairoCityLabel.add(buildingButtons.getMarketBuyButton());
        buildingButtons.getMarketBuyButton().setToolTipText("Cost : 1500");
    }

    public void initializeUpgradeButtons() {
        buildingButtons.getArcheryUpgradeButton().setBounds(200, 330, 100, 70);
        cairoCityLabel.add(buildingButtons.getArcheryUpgradeButton());
        buildingButtons.getArcheryUpgradeButton().setToolTipText("Upgrade Cost : ↗ Level 2  800");
        buildingButtons.getBarracksUpgradeButton().setBounds(460, 430, 100, 70);
        cairoCityLabel.add(buildingButtons.getBarracksUpgradeButton());
        buildingButtons.getBarracksUpgradeButton().setToolTipText("Upgrade Cost : ↗ Level 2  1000");
        buildingButtons.getStableUpgradeButton().setBounds(740, 530, 100, 70);
        cairoCityLabel.add(buildingButtons.getStableUpgradeButton());
        buildingButtons.getStableUpgradeButton().setToolTipText("Upgrade Cost : ↗ Level 2  1500");
        buildingButtons.getFarmUpgradeButton().setBounds(960, 330, 100, 70);
        cairoCityLabel.add(buildingButtons.getFarmUpgradeButton());
        buildingButtons.getFarmUpgradeButton().setToolTipText("Upgrade Cost : ↗ Level 2  500");
        buildingButtons.getMarketUpgradeButton().setBounds(670, 170, 100, 70);
        cairoCityLabel.add(buildingButtons.getMarketUpgradeButton());
        buildingButtons.getMarketUpgradeButton().setToolTipText("Upgrade Cost : ↗ Level 2  700");
        buildingButtons.getArcheryRecruitButton().setBounds(350, 350, 100, 70);
        cairoCityLabel.add(buildingButtons.getArcheryRecruitButton());
        buildingButtons.getArcheryRecruitButton().setToolTipText("Recruitment Cost : 400");
        buildingButtons.getBarracksRecruitButton().setBounds(610, 450, 100, 70);
        cairoCityLabel.add(buildingButtons.getBarracksRecruitButton());
        buildingButtons.getBarracksRecruitButton().setToolTipText("Recruitment Cost : 500");
        buildingButtons.getStableRecruitButton().setBounds(890, 550, 100, 70);
        cairoCityLabel.add(buildingButtons.getStableRecruitButton());
        buildingButtons.getStableRecruitButton().setToolTipText("Recruitment COst : 600");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == buildingButtons.getCloseButton())
            this.dispose();
    }

    public BuildingButtons getBuildingButtons() {
        return buildingButtons;
    }

    public PlayerLabel getPlayerLabel() {
        return playerLabel;
    }
}
