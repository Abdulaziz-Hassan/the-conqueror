package controller;

import engine.City;
import engine.Game;
import exceptions.*;
import units.Army;
import units.Status;
import units.Unit;
import view.buttons.ArmyButton;
import view.buttons.BuildingButtons;
import view.buttons.BuyButton;
import view.buttons.RecruitButton;
import view.buttons.UpgradeButton;
import view.views.UnitsView;
import view.buttons.UnitButton;
import view.views.MainView;
import view.buttons.CustomButton;
import view.views.WorldMapView;

import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class Controller implements ActionListener {

    private final MainView mainView;
    private Game game;
    private boolean worldMapFlag;
    private Unit unit;
    private Army selectedMarchingArmy;
    private int indexOfSelectedMarchingArmy;
    private Army selectedBesiegingArmy;
    private int indexOfSelectedBesiegingArmy;
    private boolean flag;
    private boolean autoResolveFlag;
    private HashMap<String, ArrayList<UnitButton>> enemyUnitButtonsMap;

    public Controller() {
        this.mainView = new MainView();
        this.mainView.getContinueButton().addActionListener(this);
    }

    public void addInitialActionListeners() {
        if (worldMapFlag) {
            for (BuyButton button : this.mainView.getWorldMapView().getCairoOpeningView().getCairoCityView().getBuildingButtons().getBuyButtons())
                button.addActionListener(this);
            for (UpgradeButton button : this.mainView.getWorldMapView().getCairoOpeningView().getCairoCityView().getBuildingButtons().getUpgradeButtons())
                button.addActionListener(this);
            for (RecruitButton button : this.mainView.getWorldMapView().getCairoOpeningView().getCairoCityView().getBuildingButtons().getRecruitButtons())
                button.addActionListener(this);
            for (BuyButton button : this.mainView.getWorldMapView().getRomeOpeningView().getRomeCityView().getBuildingButtons().getBuyButtons())
                button.addActionListener(this);
            for (UpgradeButton button : this.mainView.getWorldMapView().getRomeOpeningView().getRomeCityView().getBuildingButtons().getUpgradeButtons())
                button.addActionListener(this);
            for (RecruitButton button : this.mainView.getWorldMapView().getRomeOpeningView().getRomeCityView().getBuildingButtons().getRecruitButtons())
                button.addActionListener(this);
            for (BuyButton button : this.mainView.getWorldMapView().getSpartaOpeningView().getSpartaCityView().getBuildingButtons().getBuyButtons())
                button.addActionListener(this);
            for (UpgradeButton button : this.mainView.getWorldMapView().getSpartaOpeningView().getSpartaCityView().getBuildingButtons().getUpgradeButtons())
                button.addActionListener(this);
            for (RecruitButton button : this.mainView.getWorldMapView().getSpartaOpeningView().getSpartaCityView().getBuildingButtons().getRecruitButtons())
                button.addActionListener(this);
            this.mainView.getWorldMapView().getPlayerLabel().getEndTurn().addActionListener(this);
            this.mainView.getWorldMapView().getCairoOpeningView().getCairoCityView().getPlayerLabel().getEndTurn().addActionListener(this);
            this.mainView.getWorldMapView().getSpartaOpeningView().getSpartaCityView().getPlayerLabel().getEndTurn().addActionListener(this);
            this.mainView.getWorldMapView().getRomeOpeningView().getRomeCityView().getPlayerLabel().getEndTurn().addActionListener(this);
        }
    }

    public void addLaySiegeActionListeners() {
        for (UnitsView frame : this.mainView.getWorldMapView().getMarchingArmiesView().getControlledArmiesUnitsView())
            frame.getLaySiegeButton().addActionListener(this);
    }

    public void addInitiateArmyButtonsActionListeners() {
        this.mainView.getWorldMapView().getCairoOpeningView().getDefendingArmyCairoView().getInitiateArmyButton().addActionListener(this);
        this.mainView.getWorldMapView().getRomeOpeningView().getDefendingArmyRomeView().getInitiateArmyButton().addActionListener(this);
        this.mainView.getWorldMapView().getSpartaOpeningView().getDefendingArmySpartaView().getInitiateArmyButton().addActionListener(this);
    }

    public void addRelocateUnitButtonActionListeners() {
        this.mainView.getWorldMapView().getCairoOpeningView().getDefendingArmyCairoView().getRelocateUnitButton().addActionListener(this);
        this.mainView.getWorldMapView().getSpartaOpeningView().getDefendingArmySpartaView().getRelocateUnitButton().addActionListener(this);
        this.mainView.getWorldMapView().getRomeOpeningView().getDefendingArmyRomeView().getRelocateUnitButton().addActionListener(this);
    }

    public void addTargetCityButtonsActionListeners() {
        for (UnitsView frame : this.mainView.getWorldMapView().getControlledArmiesView().getControlledArmiesUnitsView()) {
            frame.getTargetCairoButton().addActionListener(this);
            frame.getTargetRomeButton().addActionListener(this);
            frame.getTargetSpartaButton().addActionListener(this);
        }
    }

    public void addUnitButtonsActionListeners() {
        for (UnitButton button : this.mainView.getWorldMapView().getCairoOpeningView().getDefendingArmyCairoView().getUnitButtons())
            button.addActionListener(this);
        for (UnitButton button : this.mainView.getWorldMapView().getSpartaOpeningView().getDefendingArmySpartaView().getUnitButtons())
            button.addActionListener(this);
        for (UnitButton button : this.mainView.getWorldMapView().getRomeOpeningView().getDefendingArmyRomeView().getUnitButtons())
            button.addActionListener(this);
    }

    public void addArmyButtonsActionListeners() {
        for (ArmyButton button : this.mainView.getWorldMapView().getControlledArmiesView().getArmyButtons())
            button.addActionListener(this);
        for (ArmyButton button : this.mainView.getWorldMapView().getMarchingArmiesView().getArmyButtons())
            button.addActionListener(this);
        for (ArmyButton button : this.mainView.getWorldMapView().getBesiegingArmiesView().getArmyButtons())
            button.addActionListener(this);
    }

    public void addAutoResolveActionListeners() {
        for (UnitsView frame : this.mainView.getWorldMapView().getMarchingArmiesView().getControlledArmiesUnitsView()) {
            frame.getAutoResolveButton().addActionListener(this);
            frame.getLaySiegeButton().addActionListener(this);
        }
    }

    public void handleContinueButton(ActionEvent e) throws IOException {
        CustomButton button = this.mainView.getContinueButton();
        if (e.getSource() == button) {
            if ((this.mainView.getNameTextField().getText().equals("Please Enter Your Name") || this.mainView.getNameTextField().getText().equals(""))) {
                JOptionPane.showMessageDialog(null, "Please enter your Username", "Info missing", JOptionPane.WARNING_MESSAGE);
            } else if (!this.mainView.getCairoRadioButton().isSelected() && !this.mainView.getRomeRadioButton().isSelected() && !this.mainView.getSpartaRadioButton().isSelected()) {
                JOptionPane.showMessageDialog(null, "Please select a City", "Info missing", JOptionPane.WARNING_MESSAGE);
            } else if (!this.mainView.getKingToggleButton().isSelected() && !this.mainView.getQueenToggleButton().isSelected()) {
                JOptionPane.showMessageDialog(null, "Please select a character", "Info missing", JOptionPane.WARNING_MESSAGE);
            } else {
                this.mainView.setPlayerName(this.mainView.getNameTextField().getText());
                if (this.mainView.getRomeRadioButton().isSelected()) {
                    this.mainView.setCityName("rome");

                } else if (this.mainView.getCairoRadioButton().isSelected()) {
                    this.mainView.setCityName("cairo");
                } else if (this.mainView.getSpartaRadioButton().isSelected()) {
                    this.mainView.setCityName("sparta");
                }
                if (this.mainView.getKingToggleButton().isSelected()) {
                    this.mainView.setMale(true);
                } else if (this.mainView.getQueenToggleButton().isSelected())
                    this.mainView.setMale(false);
                this.game = new Game(this.mainView.getPlayerName(), this.mainView.getCityName());
                this.mainView.setWorldMapView(new WorldMapView(this.mainView.getCityName(), this.mainView.isMale(), this.mainView.getPlayerName(),
                        this.game.getPlayer().getTreasury(), this.game.getPlayer().getFood(), this.game.getEnemyDefendingArmies()));
                worldMapFlag = true;
                addInitialActionListeners();
                WorldMapView worldMapView = this.mainView.getWorldMapView();
                worldMapView.getControlledArmiesView().getPlayerNameLabel().setText("PLayer Name : " + this.mainView.getPlayerName());
                worldMapView.getMarchingArmiesView().getPlayerNameLabel().setText("Player Name : " + this.mainView.getPlayerName());
                worldMapView.getBesiegingArmiesView().getPlayerNameLabel().setText("Player Name : " + this.mainView.getPlayerName());
                worldMapView.getCairoOpeningView().getDefendingArmyCairoView().getPlayerNameLabel().setText("Player Name : " + this.mainView.getPlayerName());
                worldMapView.getRomeOpeningView().getDefendingArmyRomeView().getPlayerNameLabel().setText("Player Name : " + this.mainView.getPlayerName());
                worldMapView.getSpartaOpeningView().getDefendingArmySpartaView().getPlayerNameLabel().setText("Player Name : " + this.mainView.getPlayerName());
                worldMapView.getControlledArmiesView().getFoodLabel().setText("Food : " + this.game.getPlayer().getFood());
                worldMapView.getMarchingArmiesView().getFoodLabel().setText("Food : " + this.game.getPlayer().getFood());
                worldMapView.getBesiegingArmiesView().getFoodLabel().setText("Food : " + this.game.getPlayer().getFood());
                worldMapView.getCairoOpeningView().getDefendingArmyCairoView().getFoodLabel().setText("Food : " + this.game.getPlayer().getFood());
                worldMapView.getSpartaOpeningView().getDefendingArmySpartaView().getFoodLabel().setText("Food : " + this.game.getPlayer().getFood());
                worldMapView.getRomeOpeningView().getDefendingArmyRomeView().getFoodLabel().setText("Food : " + this.game.getPlayer().getFood());
                worldMapView.getControlledArmiesView().getTreasuryLabel().setText("Treasury : " + this.game.getPlayer().getTreasury());
                worldMapView.getMarchingArmiesView().getTreasuryLabel().setText("Treasury : " + this.game.getPlayer().getTreasury());
                worldMapView.getBesiegingArmiesView().getTreasuryLabel().setText("Treasury : " + this.game.getPlayer().getTreasury());
                worldMapView.getRomeOpeningView().getDefendingArmyRomeView().getTreasuryLabel().setText("Treasury : " + this.game.getPlayer().getTreasury());
                worldMapView.getSpartaOpeningView().getDefendingArmySpartaView().getTreasuryLabel().setText("Treasury : " + this.game.getPlayer().getTreasury());
                worldMapView.getCairoOpeningView().getDefendingArmyCairoView().getTreasuryLabel().setText("Treasury : " + this.game.getPlayer().getTreasury());
                enemyUnitButtonsMap = this.game.getEnemyDefendingArmies();
                this.mainView.dispose();
            }
        }
    }

    public void handleCairoButtons(ActionEvent e) {
        BuildingButtons buildingButtonsCairo = this.mainView.getWorldMapView().getCairoOpeningView().getCairoCityView().getBuildingButtons();
        if (e.getSource() == buildingButtonsCairo.getArcheryBuyButton()) {
            try {
                this.game.getPlayer().build("ArcheryRange", "Cairo");
                buildingButtonsCairo.getArcheryBuyButton().setVisible(false);
                buildingButtonsCairo.getArcheryUpgradeButton().setVisible(true);
                buildingButtonsCairo.getArcheryRecruitButton().setVisible(true);
                playSound("resources/miscellaneous/Cash Register (Kaching) - Sound Effect (HD).wav");
                updatePlayerLabel();
            } catch (NotEnoughGoldException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "Action Denied", JOptionPane.WARNING_MESSAGE);
            }
        } else if (e.getSource() == buildingButtonsCairo.getBarracksBuyButton()) {
            try {
                this.game.getPlayer().build("Barracks", "Cairo");
                buildingButtonsCairo.getBarracksBuyButton().setVisible(false);
                buildingButtonsCairo.getBarracksUpgradeButton().setVisible(true);
                buildingButtonsCairo.getBarracksRecruitButton().setVisible(true);
                playSound("resources/miscellaneous/Cash Register (Kaching) - Sound Effect (HD).wav");
                updatePlayerLabel();
            } catch (NotEnoughGoldException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "Action Denied", JOptionPane.WARNING_MESSAGE);
            }
        } else if (e.getSource() == buildingButtonsCairo.getStableBuyButton()) {
            try {
                this.game.getPlayer().build("Stable", "Cairo");
                buildingButtonsCairo.getStableBuyButton().setVisible(false);
                buildingButtonsCairo.getStableUpgradeButton().setVisible(true);
                buildingButtonsCairo.getStableRecruitButton().setVisible(true);
                playSound("resources/miscellaneous/Cash Register (Kaching) - Sound Effect (HD).wav");
                updatePlayerLabel();
            } catch (NotEnoughGoldException ex) {
                JOptionPane.showMessageDialog(null, "Not Enough Gold to build", "Action Denied", JOptionPane.WARNING_MESSAGE);
            }
        } else if (e.getSource() == buildingButtonsCairo.getMarketBuyButton()) {
            try {
                this.game.getPlayer().build("Market", "Cairo");
                buildingButtonsCairo.getMarketBuyButton().setVisible(false);
                buildingButtonsCairo.getMarketUpgradeButton().setVisible(true);
                playSound("resources/miscellaneous/Cash Register (Kaching) - Sound Effect (HD).wav");
                updatePlayerLabel();
            } catch (NotEnoughGoldException ex) {
                JOptionPane.showMessageDialog(null, "Not Enough Gold to build", "Action Denied", JOptionPane.WARNING_MESSAGE);
            }
        } else if (e.getSource() == buildingButtonsCairo.getFarmBuyButton()) {
            try {
                this.game.getPlayer().build("Farm", "Cairo");
                buildingButtonsCairo.getFarmBuyButton().setVisible(false);
                buildingButtonsCairo.getFarmUpgradeButton().setVisible(true);
                playSound("resources/miscellaneous/Cash Register (Kaching) - Sound Effect (HD).wav");
                updatePlayerLabel();
            } catch (NotEnoughGoldException ex) {
                JOptionPane.showMessageDialog(null, "Not Enough Gold to build", "Action Denied", JOptionPane.WARNING_MESSAGE);
            }
        } else if (e.getSource() == buildingButtonsCairo.getArcheryUpgradeButton()) {
            try {
                this.game.getPlayer().upgradeBuilding(this.game.getPlayer().
                        getCorrespondingMilitaryBuilding("ArcheryRange", this.game.getPlayer().getCorrespondingCity("Cairo")));
                switch (this.game.getPlayer().getCorrespondingMilitaryBuilding("ArcheryRange", this.game.getPlayer().
                        getCorrespondingCity("Cairo")).getLevel()) {
                    case 2:
                        buildingButtonsCairo.getArcheryUpgradeButton().setToolTipText("Upgrade : ↗ Level 3  700");
                        buildingButtonsCairo.getArcheryRecruitButton().setToolTipText("Recruitment Cost : 450");
                        break;
                    case 3:
                        buildingButtonsCairo.getArcheryUpgradeButton().setToolTipText(null);
                        buildingButtonsCairo.getArcheryRecruitButton().setToolTipText("Recruitment Cost : 500");
                        break;
                }
                playSound("resources/miscellaneous/Cash Register (Kaching) - Sound Effect (HD).wav");
                updatePlayerLabel();
            } catch (NotEnoughGoldException | BuildingInCoolDownException | MaxLevelException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "Action Denied", JOptionPane.WARNING_MESSAGE);
            }
        } else if (e.getSource() == buildingButtonsCairo.getBarracksUpgradeButton()) {
            try {
                this.game.getPlayer().upgradeBuilding(this.game.getPlayer().
                        getCorrespondingMilitaryBuilding("Barracks", this.game.getPlayer().getCorrespondingCity("Cairo")));
                switch (this.game.getPlayer().getCorrespondingMilitaryBuilding("Barracks", this.game.getPlayer().
                        getCorrespondingCity("Cairo")).getLevel()) {
                    case 2:
                        buildingButtonsCairo.getBarracksUpgradeButton().setToolTipText("Upgrade : ↗ Level 3  1500");
                        buildingButtonsCairo.getBarracksRecruitButton().setToolTipText("Recruitment Cost : 550");
                        break;
                    case 3:
                        buildingButtonsCairo.getBarracksUpgradeButton().setToolTipText(null);
                        buildingButtonsCairo.getBarracksRecruitButton().setToolTipText("Recruitment Cost : 600");
                        break;
                }
                playSound("resources/miscellaneous/Cash Register (Kaching) - Sound Effect (HD).wav");
                updatePlayerLabel();
            } catch (NotEnoughGoldException | BuildingInCoolDownException | MaxLevelException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "Action Denied", JOptionPane.WARNING_MESSAGE);
            }
        } else if (e.getSource() == buildingButtonsCairo.getStableUpgradeButton()) {
            try {
                this.game.getPlayer().upgradeBuilding(this.game.getPlayer().
                        getCorrespondingMilitaryBuilding("Stable", this.game.getPlayer().getCorrespondingCity("Cairo")));
                switch (this.game.getPlayer().getCorrespondingMilitaryBuilding("Stable", this.game.getPlayer().
                        getCorrespondingCity("Cairo")).getLevel()) {
                    case 2:
                        buildingButtonsCairo.getStableUpgradeButton().setToolTipText("Upgrade : ↗ Level 3  2000");
                        buildingButtonsCairo.getStableRecruitButton().setToolTipText("Recruitment Cost : 650");
                        break;
                    case 3:
                        buildingButtonsCairo.getStableUpgradeButton().setToolTipText(null);
                        buildingButtonsCairo.getStableRecruitButton().setToolTipText("Recruitment Cost : 700");
                        break;
                }
                playSound("resources/miscellaneous/Cash Register (Kaching) - Sound Effect (HD).wav");
                updatePlayerLabel();
            } catch (NotEnoughGoldException | BuildingInCoolDownException | MaxLevelException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "Action Denied", JOptionPane.WARNING_MESSAGE);
            }
        } else if (e.getSource() == buildingButtonsCairo.getFarmUpgradeButton()) {
            try {
                this.game.getPlayer().upgradeBuilding(this.game.getPlayer().
                        getCorrespondingEconomicBuilding(this.game.getPlayer().getCorrespondingCity("Cairo"), "Farm"));
                buildingButtonsCairo.getFarmUpgradeButton().setToolTipText("Upgrade : ↗ Level 3  700");
                playSound("resources/miscellaneous/Cash Register (Kaching) - Sound Effect (HD).wav");
                updatePlayerLabel();
            } catch (NotEnoughGoldException | BuildingInCoolDownException | MaxLevelException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "Action Denied", JOptionPane.WARNING_MESSAGE);
            }
        } else if (e.getSource() == buildingButtonsCairo.getMarketUpgradeButton()) {
            try {
                this.game.getPlayer().upgradeBuilding(this.game.getPlayer().
                        getCorrespondingEconomicBuilding(this.game.getPlayer().getCorrespondingCity("Cairo"), "Market"));
                buildingButtonsCairo.getMarketUpgradeButton().setToolTipText("Upgrade : ↗ Level 3  1000");
                playSound("resources/miscellaneous/Cash Register (Kaching) - Sound Effect (HD).wav");
                updatePlayerLabel();
            } catch (NotEnoughGoldException | BuildingInCoolDownException | MaxLevelException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "Action Denied", JOptionPane.WARNING_MESSAGE);
            }
        } else if (e.getSource() == buildingButtonsCairo.getArcheryRecruitButton()) {
            try {
                Unit recruitedUnit = this.game.getPlayer().recruitUnit("Archer", "Cairo");
                this.mainView.getWorldMapView().getCairoOpeningView().getDefendingArmyCairoView().addToView(recruitedUnit);
                this.addUnitButtonsActionListeners();
                updatePlayerLabel();
            } catch (BuildingInCoolDownException | MaxRecruitedException | NotEnoughGoldException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "Action Denied", JOptionPane.WARNING_MESSAGE);
            }

        } else if (e.getSource() == buildingButtonsCairo.getBarracksRecruitButton()) {
            try {
                Unit recruitedUnit = this.game.getPlayer().recruitUnit("Infantry", "Cairo");

                this.mainView.getWorldMapView().getCairoOpeningView().getDefendingArmyCairoView().addToView(recruitedUnit);
                this.addUnitButtonsActionListeners();
                updatePlayerLabel();
            } catch (BuildingInCoolDownException | MaxRecruitedException | NotEnoughGoldException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "Action Denied", JOptionPane.WARNING_MESSAGE);
            }
        } else if (e.getSource() == buildingButtonsCairo.getStableRecruitButton()) {
            try {
                Unit recruitedUnit = this.game.getPlayer().recruitUnit("Cavalry", "Cairo");

                this.mainView.getWorldMapView().getCairoOpeningView().getDefendingArmyCairoView().addToView(recruitedUnit);
                this.addUnitButtonsActionListeners();
                updatePlayerLabel();
            } catch (BuildingInCoolDownException | MaxRecruitedException | NotEnoughGoldException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "Action Denied", JOptionPane.WARNING_MESSAGE);
            }

        }
    }

    public void handleSpartaButtons(ActionEvent e) {
        BuildingButtons buildingButtonsSparta = this.mainView.getWorldMapView().getSpartaOpeningView().getSpartaCityView().getBuildingButtons();
        if (e.getSource() == buildingButtonsSparta.getArcheryBuyButton()) {
            try {
                this.game.getPlayer().build("ArcheryRange", "Sparta");
                buildingButtonsSparta.getArcheryBuyButton().setVisible(false);
                buildingButtonsSparta.getArcheryUpgradeButton().setVisible(true);
                buildingButtonsSparta.getArcheryRecruitButton().setVisible(true);
                playSound("resources/miscellaneous/Cash Register (Kaching) - Sound Effect (HD).wav");
                updatePlayerLabel();
            } catch (NotEnoughGoldException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "Action Denied", JOptionPane.WARNING_MESSAGE);
            }
        } else if (e.getSource() == buildingButtonsSparta.getBarracksBuyButton()) {
            try {
                this.game.getPlayer().build("Barracks", "Sparta");
                buildingButtonsSparta.getBarracksBuyButton().setVisible(false);
                buildingButtonsSparta.getBarracksUpgradeButton().setVisible(true);
                buildingButtonsSparta.getBarracksRecruitButton().setVisible(true);
                playSound("resources/miscellaneous/Cash Register (Kaching) - Sound Effect (HD).wav");
                updatePlayerLabel();
            } catch (NotEnoughGoldException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "Action Denied", JOptionPane.WARNING_MESSAGE);
            }
        } else if (e.getSource() == buildingButtonsSparta.getStableBuyButton()) {
            try {
                this.game.getPlayer().build("Stable", "Sparta");
                buildingButtonsSparta.getStableBuyButton().setVisible(false);
                buildingButtonsSparta.getStableUpgradeButton().setVisible(true);
                buildingButtonsSparta.getStableRecruitButton().setVisible(true);
                playSound("resources/miscellaneous/Cash Register (Kaching) - Sound Effect (HD).wav");
                updatePlayerLabel();
            } catch (NotEnoughGoldException ex) {
                JOptionPane.showMessageDialog(null, "Not Enough Gold to build", "Action Denied", JOptionPane.WARNING_MESSAGE);
            }
        } else if (e.getSource() == buildingButtonsSparta.getMarketBuyButton()) {
            try {
                this.game.getPlayer().build("Market", "Sparta");
                buildingButtonsSparta.getMarketBuyButton().setVisible(false);
                buildingButtonsSparta.getMarketUpgradeButton().setVisible(true);
                playSound("resources/miscellaneous/Cash Register (Kaching) - Sound Effect (HD).wav");
                updatePlayerLabel();
            } catch (NotEnoughGoldException ex) {
                JOptionPane.showMessageDialog(null, "Not Enough Gold to build", "Action Denied", JOptionPane.WARNING_MESSAGE);
            }
        } else if (e.getSource() == buildingButtonsSparta.getFarmBuyButton()) {
            try {
                this.game.getPlayer().build("Farm", "Sparta");
                buildingButtonsSparta.getFarmBuyButton().setVisible(false);
                buildingButtonsSparta.getFarmUpgradeButton().setVisible(true);
                playSound("resources/miscellaneous/Cash Register (Kaching) - Sound Effect (HD).wav");
                updatePlayerLabel();
            } catch (NotEnoughGoldException ex) {
                JOptionPane.showMessageDialog(null, "Not Enough Gold to build", "Action Denied", JOptionPane.WARNING_MESSAGE);
            }
        } else if (e.getSource() == buildingButtonsSparta.getArcheryUpgradeButton()) {
            try {
                this.game.getPlayer().upgradeBuilding(this.game.getPlayer().
                        getCorrespondingMilitaryBuilding("ArcheryRange", this.game.getPlayer().getCorrespondingCity("Sparta")));
                switch (this.game.getPlayer().getCorrespondingMilitaryBuilding("ArcheryRange", this.game.getPlayer().
                        getCorrespondingCity("Sparta")).getLevel()) {
                    case 2:
                        buildingButtonsSparta.getArcheryUpgradeButton().setToolTipText("Upgrade : ↗ Level 3  700");
                        buildingButtonsSparta.getArcheryRecruitButton().setToolTipText("Recruitment Cost : 450");
                        break;
                    case 3:
                        buildingButtonsSparta.getArcheryUpgradeButton().setToolTipText(null);
                        buildingButtonsSparta.getArcheryRecruitButton().setToolTipText("Recruitment Cost : 500");
                        break;
                }
                playSound("resources/miscellaneous/Cash Register (Kaching) - Sound Effect (HD).wav");
                updatePlayerLabel();
            } catch (NotEnoughGoldException | BuildingInCoolDownException | MaxLevelException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "Action Denied", JOptionPane.WARNING_MESSAGE);
            }
        } else if (e.getSource() == buildingButtonsSparta.getBarracksUpgradeButton()) {
            try {
                this.game.getPlayer().upgradeBuilding(this.game.getPlayer().
                        getCorrespondingMilitaryBuilding("Barracks", this.game.getPlayer().getCorrespondingCity("Sparta")));
                switch (this.game.getPlayer().getCorrespondingMilitaryBuilding("Barracks", this.game.getPlayer().
                        getCorrespondingCity("Sparta")).getLevel()) {
                    case 2:
                        buildingButtonsSparta.getBarracksUpgradeButton().setToolTipText("Upgrade : ↗ Level 3  1500");
                        buildingButtonsSparta.getBarracksRecruitButton().setToolTipText("Recruitment Cost : 550");
                        break;
                    case 3:
                        buildingButtonsSparta.getBarracksUpgradeButton().setToolTipText(null);
                        buildingButtonsSparta.getBarracksRecruitButton().setToolTipText("Recruitment Cost : 600");
                        break;
                }
                playSound("resources/miscellaneous/Cash Register (Kaching) - Sound Effect (HD).wav");
                updatePlayerLabel();
            } catch (NotEnoughGoldException | BuildingInCoolDownException | MaxLevelException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "Action Denied", JOptionPane.WARNING_MESSAGE);
            }
        } else if (e.getSource() == buildingButtonsSparta.getStableUpgradeButton()) {
            try {
                this.game.getPlayer().upgradeBuilding(this.game.getPlayer().
                        getCorrespondingMilitaryBuilding("Stable", this.game.getPlayer().getCorrespondingCity("Sparta")));
                switch (this.game.getPlayer().getCorrespondingMilitaryBuilding("Stable", this.game.getPlayer().
                        getCorrespondingCity("Sparta")).getLevel()) {
                    case 2:
                        buildingButtonsSparta.getStableUpgradeButton().setToolTipText("Upgrade : ↗ Level 3  2000");
                        buildingButtonsSparta.getStableRecruitButton().setToolTipText("Recruitment Cost : 650");
                        break;
                    case 3:
                        buildingButtonsSparta.getStableUpgradeButton().setToolTipText(null);
                        buildingButtonsSparta.getStableRecruitButton().setToolTipText("Recruitment Cost : 700");
                        break;
                }
                playSound("resources/miscellaneous/Cash Register (Kaching) - Sound Effect (HD).wav");
                updatePlayerLabel();
            } catch (NotEnoughGoldException | BuildingInCoolDownException | MaxLevelException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "Action Denied", JOptionPane.WARNING_MESSAGE);
            }
        } else if (e.getSource() == buildingButtonsSparta.getFarmUpgradeButton()) {
            try {
                this.game.getPlayer().upgradeBuilding(this.game.getPlayer().
                        getCorrespondingEconomicBuilding(this.game.getPlayer().getCorrespondingCity("Sparta"), "Farm"));
                buildingButtonsSparta.getFarmUpgradeButton().setToolTipText("Upgrade : ↗ Level 3  700");
                playSound("resources/miscellaneous/Cash Register (Kaching) - Sound Effect (HD).wav");
                updatePlayerLabel();
            } catch (NotEnoughGoldException | BuildingInCoolDownException | MaxLevelException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "Action Denied", JOptionPane.WARNING_MESSAGE);
            }
        } else if (e.getSource() == buildingButtonsSparta.getMarketUpgradeButton()) {
            try {
                this.game.getPlayer().upgradeBuilding(this.game.getPlayer().
                        getCorrespondingEconomicBuilding(this.game.getPlayer().getCorrespondingCity("Sparta"), "Market"));
                buildingButtonsSparta.getMarketUpgradeButton().setToolTipText("Upgrade : ↗ Level 3  1000");
                playSound("resources/miscellaneous/Cash Register (Kaching) - Sound Effect (HD).wav");
                updatePlayerLabel();
            } catch (NotEnoughGoldException | BuildingInCoolDownException | MaxLevelException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "Action Denied", JOptionPane.WARNING_MESSAGE);
            }
        } else if (e.getSource() == buildingButtonsSparta.getArcheryRecruitButton()) {
            try {
                Unit recruitedUnit = this.game.getPlayer().recruitUnit("Archer", "Sparta");
                this.mainView.getWorldMapView().getSpartaOpeningView().getDefendingArmySpartaView().addToView(recruitedUnit);
                this.addUnitButtonsActionListeners();
                updatePlayerLabel();
            } catch (BuildingInCoolDownException | MaxRecruitedException | NotEnoughGoldException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "Action Denied", JOptionPane.WARNING_MESSAGE);
            }
        } else if (e.getSource() == buildingButtonsSparta.getBarracksRecruitButton()) {
            try {
                Unit recruitedUnit = this.game.getPlayer().recruitUnit("Infantry", "Sparta");
                this.mainView.getWorldMapView().getSpartaOpeningView().getDefendingArmySpartaView().addToView(recruitedUnit);
                this.addUnitButtonsActionListeners();
                updatePlayerLabel();
            } catch (BuildingInCoolDownException | MaxRecruitedException | NotEnoughGoldException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "Action Denied", JOptionPane.WARNING_MESSAGE);
            }
        } else if (e.getSource() == buildingButtonsSparta.getStableRecruitButton()) {
            try {
                Unit recruitedUnit = this.game.getPlayer().recruitUnit("Cavalry", "Sparta");
                this.mainView.getWorldMapView().getSpartaOpeningView().getDefendingArmySpartaView().addToView(recruitedUnit);
                this.addUnitButtonsActionListeners();
                updatePlayerLabel();
            } catch (BuildingInCoolDownException | MaxRecruitedException | NotEnoughGoldException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "Action Denied", JOptionPane.WARNING_MESSAGE);
            }
        }
    }

    public void handleRomeButtons(ActionEvent e) {
        BuildingButtons buildingButtonsRome = this.mainView.getWorldMapView().getRomeOpeningView().getRomeCityView().getBuildingButtons();
        if (e.getSource() == buildingButtonsRome.getArcheryBuyButton()) {
            try {
                this.game.getPlayer().build("ArcheryRange", "Rome");
                buildingButtonsRome.getArcheryBuyButton().setVisible(false);
                buildingButtonsRome.getArcheryUpgradeButton().setVisible(true);
                buildingButtonsRome.getArcheryRecruitButton().setVisible(true);
                playSound("resources/miscellaneous/Cash Register (Kaching) - Sound Effect (HD).wav");
                updatePlayerLabel();
            } catch (NotEnoughGoldException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "Action Denied", JOptionPane.WARNING_MESSAGE);
            }
        } else if (e.getSource() == buildingButtonsRome.getBarracksBuyButton()) {
            try {
                this.game.getPlayer().build("Barracks", "Rome");
                buildingButtonsRome.getBarracksBuyButton().setVisible(false);
                buildingButtonsRome.getBarracksUpgradeButton().setVisible(true);
                buildingButtonsRome.getBarracksRecruitButton().setVisible(true);
                playSound("resources/miscellaneous/Cash Register (Kaching) - Sound Effect (HD).wav");
                updatePlayerLabel();
            } catch (NotEnoughGoldException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "Action Denied", JOptionPane.WARNING_MESSAGE);
            }
        } else if (e.getSource() == buildingButtonsRome.getStableBuyButton()) {
            try {
                this.game.getPlayer().build("Stable", "Rome");
                buildingButtonsRome.getStableBuyButton().setVisible(false);
                buildingButtonsRome.getStableUpgradeButton().setVisible(true);
                buildingButtonsRome.getStableRecruitButton().setVisible(true);
                playSound("resources/miscellaneous/Cash Register (Kaching) - Sound Effect (HD).wav");
                updatePlayerLabel();
            } catch (NotEnoughGoldException ex) {
                JOptionPane.showMessageDialog(null, "Not Enough Gold to build", "Action Denied", JOptionPane.WARNING_MESSAGE);
            }
        } else if (e.getSource() == buildingButtonsRome.getMarketBuyButton()) {
            try {
                this.game.getPlayer().build("Market", "Rome");
                buildingButtonsRome.getMarketBuyButton().setVisible(false);
                buildingButtonsRome.getMarketUpgradeButton().setVisible(true);
                playSound("resources/miscellaneous/Cash Register (Kaching) - Sound Effect (HD).wav");
                updatePlayerLabel();
            } catch (NotEnoughGoldException ex) {
                JOptionPane.showMessageDialog(null, "Not Enough Gold to build", "Action Denied", JOptionPane.WARNING_MESSAGE);
            }
        } else if (e.getSource() == buildingButtonsRome.getFarmBuyButton()) {
            try {
                this.game.getPlayer().build("Farm", "Rome");
                buildingButtonsRome.getFarmBuyButton().setVisible(false);
                buildingButtonsRome.getFarmUpgradeButton().setVisible(true);
                playSound("resources/miscellaneous/Cash Register (Kaching) - Sound Effect (HD).wav");
                updatePlayerLabel();
            } catch (NotEnoughGoldException ex) {
                JOptionPane.showMessageDialog(null, "Not Enough Gold to build", "Action Denied", JOptionPane.WARNING_MESSAGE);
            }
        } else if (e.getSource() == buildingButtonsRome.getArcheryUpgradeButton()) {
            try {
                this.game.getPlayer().upgradeBuilding(this.game.getPlayer().
                        getCorrespondingMilitaryBuilding("ArcheryRange", this.game.getPlayer().getCorrespondingCity("Rome")));
                switch (this.game.getPlayer().getCorrespondingMilitaryBuilding("ArcheryRange", this.game.getPlayer().
                        getCorrespondingCity("Rome")).getLevel()) {
                    case 2:
                        buildingButtonsRome.getArcheryUpgradeButton().setToolTipText("Upgrade : ↗ Level 3  700");
                        buildingButtonsRome.getArcheryRecruitButton().setToolTipText("Recruitment Cost : 450");
                        break;
                    case 3:
                        buildingButtonsRome.getArcheryUpgradeButton().setToolTipText(null);
                        buildingButtonsRome.getArcheryRecruitButton().setToolTipText("Recruitment Cost : 500");
                        break;
                }
                playSound("resources/miscellaneous/Cash Register (Kaching) - Sound Effect (HD).wav");
                updatePlayerLabel();
            } catch (NotEnoughGoldException | BuildingInCoolDownException | MaxLevelException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "Action Denied", JOptionPane.WARNING_MESSAGE);
            }
        } else if (e.getSource() == buildingButtonsRome.getBarracksUpgradeButton()) {
            try {
                this.game.getPlayer().upgradeBuilding(this.game.getPlayer().
                        getCorrespondingMilitaryBuilding("Barracks", this.game.getPlayer().getCorrespondingCity("Rome")));
                switch (this.game.getPlayer().getCorrespondingMilitaryBuilding("Barracks", this.game.getPlayer().
                        getCorrespondingCity("Rome")).getLevel()) {
                    case 2:
                        buildingButtonsRome.getBarracksUpgradeButton().setToolTipText("Upgrade : ↗ Level 3  1500");
                        buildingButtonsRome.getBarracksRecruitButton().setToolTipText("Recruitment Cost : 550");
                        break;
                    case 3:
                        buildingButtonsRome.getBarracksUpgradeButton().setToolTipText(null);
                        buildingButtonsRome.getBarracksRecruitButton().setToolTipText("Recruitment Cost : 600");
                        break;
                }
                playSound("resources/miscellaneous/Cash Register (Kaching) - Sound Effect (HD).wav");
                updatePlayerLabel();
            } catch (NotEnoughGoldException | BuildingInCoolDownException | MaxLevelException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "Action Denied", JOptionPane.WARNING_MESSAGE);
            }
        } else if (e.getSource() == buildingButtonsRome.getStableUpgradeButton()) {
            try {
                this.game.getPlayer().upgradeBuilding(this.game.getPlayer().
                        getCorrespondingMilitaryBuilding("Stable", this.game.getPlayer().getCorrespondingCity("Rome")));
                switch (this.game.getPlayer().getCorrespondingMilitaryBuilding("Stable", this.game.getPlayer().
                        getCorrespondingCity("Rome")).getLevel()) {
                    case 2:
                        buildingButtonsRome.getStableUpgradeButton().setToolTipText("Upgrade : ↗ Level 3  2000");
                        buildingButtonsRome.getStableRecruitButton().setToolTipText("Recruitment Cost : 650");
                        break;
                    case 3:
                        buildingButtonsRome.getStableUpgradeButton().setToolTipText(null);
                        buildingButtonsRome.getStableRecruitButton().setToolTipText("Recruitment Cost : 700");
                        break;
                }
                playSound("resources/miscellaneous/Cash Register (Kaching) - Sound Effect (HD).wav");
                updatePlayerLabel();
            } catch (NotEnoughGoldException | BuildingInCoolDownException | MaxLevelException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "Action Denied", JOptionPane.WARNING_MESSAGE);
            }
        } else if (e.getSource() == buildingButtonsRome.getFarmUpgradeButton()) {
            try {
                this.game.getPlayer().upgradeBuilding(this.game.getPlayer().
                        getCorrespondingEconomicBuilding(this.game.getPlayer().getCorrespondingCity("Rome"), "Farm"));
                buildingButtonsRome.getFarmUpgradeButton().setToolTipText("Upgrade : ↗ Level 3  700");
                playSound("resources/miscellaneous/Cash Register (Kaching) - Sound Effect (HD).wav");
                updatePlayerLabel();
            } catch (NotEnoughGoldException | BuildingInCoolDownException | MaxLevelException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "Action Denied", JOptionPane.WARNING_MESSAGE);
            }
        } else if (e.getSource() == buildingButtonsRome.getMarketUpgradeButton()) {
            try {
                this.game.getPlayer().upgradeBuilding(this.game.getPlayer().
                        getCorrespondingEconomicBuilding(this.game.getPlayer().getCorrespondingCity("Rome"), "Market"));
                buildingButtonsRome.getMarketUpgradeButton().setToolTipText("Upgrade : ↗ Level 3  1000");
                playSound("resources/miscellaneous/Cash Register (Kaching) - Sound Effect (HD).wav");
                updatePlayerLabel();
            } catch (NotEnoughGoldException | BuildingInCoolDownException | MaxLevelException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "Action Denied", JOptionPane.WARNING_MESSAGE);
            }
        } else if (e.getSource() == buildingButtonsRome.getArcheryRecruitButton()) {
            try {
                Unit recruitedUnit = this.game.getPlayer().recruitUnit("Archer", "Rome");
                this.mainView.getWorldMapView().getRomeOpeningView().getDefendingArmyRomeView().addToView(recruitedUnit);
                this.addUnitButtonsActionListeners();
                updatePlayerLabel();
            } catch (BuildingInCoolDownException | MaxRecruitedException | NotEnoughGoldException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "Action Denied", JOptionPane.WARNING_MESSAGE);
            }
        } else if (e.getSource() == buildingButtonsRome.getBarracksRecruitButton()) {
            try {
                Unit recruitedUnit = this.game.getPlayer().recruitUnit("Infantry", "Rome");
                this.mainView.getWorldMapView().getRomeOpeningView().getDefendingArmyRomeView().addToView(recruitedUnit);
                this.addUnitButtonsActionListeners();
                updatePlayerLabel();
            } catch (BuildingInCoolDownException | MaxRecruitedException | NotEnoughGoldException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "Action Denied", JOptionPane.WARNING_MESSAGE);
            }
        } else if (e.getSource() == buildingButtonsRome.getStableRecruitButton()) {
            try {
                Unit recruitedUnit = this.game.getPlayer().recruitUnit("Cavalry", "Rome");
                this.mainView.getWorldMapView().getRomeOpeningView().getDefendingArmyRomeView().addToView(recruitedUnit);
                this.addUnitButtonsActionListeners();
                updatePlayerLabel();
            } catch (BuildingInCoolDownException | MaxRecruitedException | NotEnoughGoldException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "Action Denied", JOptionPane.WARNING_MESSAGE);
            }
        }
    }

    public void handleUnitButtons(ActionEvent e) {
        for (UnitButton button : this.mainView.getWorldMapView().getCairoOpeningView().getDefendingArmyCairoView().getUnitButtons()) {
            if (e.getSource() == button) {
                disableRemainingButtons(button);
                this.mainView.getWorldMapView().getCairoOpeningView().getDefendingArmyCairoView().getInitiateArmyButton().setEnabled(true);
                this.mainView.getWorldMapView().getCairoOpeningView().getDefendingArmyCairoView().reload();
                this.addInitiateArmyButtonsActionListeners();
                this.addRelocateUnitButtonActionListeners();
                this.mainView.getWorldMapView().getCairoOpeningView().getDefendingArmyCairoView().reload();
                unit = button.getUnit();
                break;
            }
        }

        for (UnitButton button : this.mainView.getWorldMapView().getRomeOpeningView().getDefendingArmyRomeView().getUnitButtons()) {
            if (e.getSource() == button) {
                disableRemainingButtons(button);
                this.mainView.getWorldMapView().getRomeOpeningView().getDefendingArmyRomeView().getInitiateArmyButton().setEnabled(true);
                this.mainView.getWorldMapView().getRomeOpeningView().getDefendingArmyRomeView().reload();
                this.addInitiateArmyButtonsActionListeners();
                this.addRelocateUnitButtonActionListeners();
                this.mainView.getWorldMapView().getRomeOpeningView().getDefendingArmyRomeView().reload();
                unit = button.getUnit();
                break;
            }
        }

        for (UnitButton button : this.mainView.getWorldMapView().getSpartaOpeningView().getDefendingArmySpartaView().getUnitButtons()) {
            if (e.getSource() == button) {
                disableRemainingButtons(button);
                this.mainView.getWorldMapView().getSpartaOpeningView().getDefendingArmySpartaView().getInitiateArmyButton().setEnabled(true);
                this.mainView.getWorldMapView().getSpartaOpeningView().getDefendingArmySpartaView().reload();
                this.addInitiateArmyButtonsActionListeners();
                this.addRelocateUnitButtonActionListeners();
                this.mainView.getWorldMapView().getSpartaOpeningView().getDefendingArmySpartaView().reload();
                unit = button.getUnit();
                break;
            }
        }
    }

    public void handleArmyButtons(ActionEvent e) {
        for (ArmyButton button : this.mainView.getWorldMapView().getControlledArmiesView().getArmyButtons()) {
            if (e.getSource() == button) {
                this.mainView.getWorldMapView().getControlledArmiesView().getControlledArmiesUnitsView().get(this.mainView.
                        getWorldMapView().getControlledArmiesView().getArmyButtons().indexOf(button)).setVisible(true);
                this.mainView.getWorldMapView().getControlledArmiesView().dispose();
                indexOfSelectedMarchingArmy = this.mainView.getWorldMapView().getControlledArmiesView().getArmyButtons().indexOf(button);
                if (indexOfSelectedMarchingArmy == -1)
                    return;
                selectedMarchingArmy = this.mainView.getWorldMapView().getControlledArmiesView().getAllControlledArmies().get(indexOfSelectedMarchingArmy);
                this.addTargetCityButtonsActionListeners();
                disableCorrespondingCityButton();
                break;
            }
        }

        for (ArmyButton button : this.mainView.getWorldMapView().getMarchingArmiesView().getArmyButtons()) {
            if (e.getSource() == button) {
                this.mainView.getWorldMapView().getMarchingArmiesView().getControlledArmiesUnitsView().get(this.mainView.getWorldMapView().
                        getMarchingArmiesView().getArmyButtons().indexOf(button)).setVisible(true);

                this.mainView.getWorldMapView().getMarchingArmiesView().dispose();
                indexOfSelectedBesiegingArmy = this.mainView.getWorldMapView().getMarchingArmiesView().getArmyButtons().indexOf(button);
                if (indexOfSelectedBesiegingArmy == -1)
                    return;
                selectedBesiegingArmy = this.mainView.getWorldMapView().getMarchingArmiesView().getAllControlledArmies().get(indexOfSelectedBesiegingArmy);
                this.addLaySiegeActionListeners();
                break;
            }
        }
    }

    public void handleInitiateArmyButton(ActionEvent e) {
        if (e.getSource() == this.mainView.getWorldMapView().getCairoOpeningView().getDefendingArmyCairoView().getInitiateArmyButton()) {
            if (unit == null || !this.mainView.getWorldMapView().getCairoOpeningView().getDefendingArmyCairoView().getCorrespondingDefendingArmy().contains(unit))
                return;
            Army newArmy = this.game.getPlayer().initiateArmy(this.game.getPlayer().getCorrespondingCity("Cairo"), unit);
            this.mainView.getWorldMapView().getControlledArmiesView().addArmyToView(newArmy, "Cairo");
            reEnableAllButtons();
            disableInitiateArmyButtons();
            this.mainView.getWorldMapView().getCairoOpeningView().getDefendingArmyCairoView().moveToControllingArmiesView(unit);
            resetTargetButtons();
            this.mainView.getWorldMapView().getCairoOpeningView().getDefendingArmyCairoView().reload();
            this.mainView.getWorldMapView().getControlledArmiesView().reload();
            this.mainView.getWorldMapView().getCairoOpeningView().getDefendingArmyCairoView().getRelocateUnitButton().setEnabled(true);
        }

        if (e.getSource() == this.mainView.getWorldMapView().getRomeOpeningView().getDefendingArmyRomeView().getInitiateArmyButton()) {
            if (unit == null || !this.mainView.getWorldMapView().getRomeOpeningView().getDefendingArmyRomeView().getCorrespondingDefendingArmy().contains(unit))
                return;
            Army newArmy = this.game.getPlayer().initiateArmy(this.game.getPlayer().getCorrespondingCity("Rome"), unit);
            this.mainView.getWorldMapView().getControlledArmiesView().addArmyToView(newArmy, "Rome");
            reEnableAllButtons();
            disableInitiateArmyButtons();
            this.mainView.getWorldMapView().getRomeOpeningView().getDefendingArmyRomeView().moveToControllingArmiesView(unit);
            resetTargetButtons();
            this.mainView.getWorldMapView().getRomeOpeningView().getDefendingArmyRomeView().reload();
            this.mainView.getWorldMapView().getControlledArmiesView().reload();
            this.mainView.getWorldMapView().getRomeOpeningView().getDefendingArmyRomeView().getRelocateUnitButton().setEnabled(true);

        }

        if (e.getSource() == this.mainView.getWorldMapView().getSpartaOpeningView().getDefendingArmySpartaView().getInitiateArmyButton()) {
            if (unit == null || !this.mainView.getWorldMapView().getSpartaOpeningView().getDefendingArmySpartaView().getCorrespondingDefendingArmy().contains(unit))
                return;
            Army newArmy = this.game.getPlayer().initiateArmy(this.game.getPlayer().getCorrespondingCity("Sparta"), unit);
            this.mainView.getWorldMapView().getControlledArmiesView().addArmyToView(newArmy, "Sparta");
            reEnableAllButtons();
            disableInitiateArmyButtons();
            this.mainView.getWorldMapView().getSpartaOpeningView().getDefendingArmySpartaView().moveToControllingArmiesView(unit);
            resetTargetButtons();
            this.mainView.getWorldMapView().getSpartaOpeningView().getDefendingArmySpartaView().reload();
            this.mainView.getWorldMapView().getControlledArmiesView().reload();
            this.mainView.getWorldMapView().getSpartaOpeningView().getDefendingArmySpartaView().getRelocateUnitButton().setEnabled(true);
        }
        this.addArmyButtonsActionListeners();
    }

    public void handleRelocateUnitButton(ActionEvent e) {
        if (e.getSource() == this.mainView.getWorldMapView().getCairoOpeningView().getDefendingArmyCairoView().getRelocateUnitButton()) {
            try {
                if (!this.mainView.getWorldMapView().getCairoOpeningView().getDefendingArmyCairoView().getCorrespondingDefendingArmy().contains(unit))
                    return;
                int index = -1;
                for (UnitsView controlledArmiesUnitsFrame : this.mainView.getWorldMapView().getControlledArmiesView().getControlledArmiesUnitsView()) {
                    int counter = 0;
                    for (ArmyButton armyButton : this.mainView.getWorldMapView().getControlledArmiesView().getArmyButtons()) {
                        if (armyButton.getCityName().equalsIgnoreCase(unit.getParentArmy().getCurrentLocation()) && controlledArmiesUnitsFrame.getI() < 9)
                            index = counter;
                        counter++;
                    }
                }
                if (index == -1) {
                    JOptionPane.showMessageDialog(null, "You cant Relocate", "Action Denied", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                this.mainView.getWorldMapView().getControlledArmiesView().getAllControlledArmies().get(index).relocateUnit(unit);
                UnitButton unitButton = this.mainView.getWorldMapView().getCairoOpeningView().getDefendingArmyCairoView().moveToControllingArmiesView(unit);
                this.mainView.getWorldMapView().getControlledArmiesView().relocateUnitToUnitView(unitButton, index);
            } catch (MaxCapacityException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "Action Denied", JOptionPane.WARNING_MESSAGE);
                this.mainView.getWorldMapView().getCairoOpeningView().getDefendingArmyCairoView().dispose();
                this.mainView.getWorldMapView().getCairoOpeningView().getDefendingArmyCairoView().getRelocateUnitButton().setEnabled(false);
            }
        }
        if (e.getSource() == this.mainView.getWorldMapView().getSpartaOpeningView().getDefendingArmySpartaView().getRelocateUnitButton()) {
            try {
                if (!this.mainView.getWorldMapView().getSpartaOpeningView().getDefendingArmySpartaView().getCorrespondingDefendingArmy().contains(unit))
                    return;
                int index = -1;
                for (UnitsView controlledArmiesUnitsFrame : this.mainView.getWorldMapView().getControlledArmiesView().getControlledArmiesUnitsView()) {
                    int counter = 0;
                    for (ArmyButton armyButton : this.mainView.getWorldMapView().getControlledArmiesView().getArmyButtons()) {
                        if (armyButton.getCityName().equalsIgnoreCase(unit.getParentArmy().getCurrentLocation()) && controlledArmiesUnitsFrame.getI() < 9)
                            index = counter;
                        counter++;
                    }
                }
                if (index == -1) {
                    JOptionPane.showMessageDialog(null, "You cant Relocate", "Action Denied", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                this.mainView.getWorldMapView().getControlledArmiesView().getAllControlledArmies().get(index).relocateUnit(unit);
                UnitButton unitButton = this.mainView.getWorldMapView().getSpartaOpeningView().getDefendingArmySpartaView().moveToControllingArmiesView(unit);
                this.mainView.getWorldMapView().getControlledArmiesView().relocateUnitToUnitView(unitButton, index);
            } catch (MaxCapacityException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "Action Denied", JOptionPane.WARNING_MESSAGE);
                this.mainView.getWorldMapView().getSpartaOpeningView().getDefendingArmySpartaView().getRelocateUnitButton().setEnabled(false);
            }
        }
        if (e.getSource() == this.mainView.getWorldMapView().getRomeOpeningView().getDefendingArmyRomeView().getRelocateUnitButton()) {
            try {
                if (!this.mainView.getWorldMapView().getRomeOpeningView().getDefendingArmyRomeView().getCorrespondingDefendingArmy().contains(unit))
                    return;
                int index = -1;
                for (UnitsView controlledArmiesUnitsFrame : this.mainView.getWorldMapView().getControlledArmiesView().getControlledArmiesUnitsView()) {
                    int counter = 0;
                    for (ArmyButton armyButton : this.mainView.getWorldMapView().getControlledArmiesView().getArmyButtons()) {
                        if (armyButton.getCityName().equalsIgnoreCase(unit.getParentArmy().getCurrentLocation()) && controlledArmiesUnitsFrame.getI() < 9)
                            index = counter;
                        counter++;
                    }
                }
                if (index == -1) {
                    JOptionPane.showMessageDialog(null, "You cant Relocate", "Action Denied", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                this.mainView.getWorldMapView().getControlledArmiesView().getAllControlledArmies().get(index).relocateUnit(unit);
                UnitButton unitButton = this.mainView.getWorldMapView().getRomeOpeningView().getDefendingArmyRomeView().moveToControllingArmiesView(unit);
                this.mainView.getWorldMapView().getControlledArmiesView().relocateUnitToUnitView(unitButton, index);
            } catch (MaxCapacityException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "Action Denied", JOptionPane.WARNING_MESSAGE);
                this.mainView.getWorldMapView().getRomeOpeningView().getDefendingArmyRomeView().getRelocateUnitButton().setEnabled(false);
            }
        }
    }

    public void handleLaySiegeButton(ActionEvent e) {  // TODO laySiege bug
        for (UnitsView frame : this.mainView.getWorldMapView().getMarchingArmiesView().getControlledArmiesUnitsView()) {
            if (e.getSource() == frame.getLaySiegeButton()) {
                try {
                    this.game.getPlayer().laySiege(selectedBesiegingArmy, this.game.getCorrespondingAvailableCity(selectedBesiegingArmy.getTarget().toLowerCase()));
                    this.mainView.getWorldMapView().getMarchingArmiesView().addToBesiegingView(selectedBesiegingArmy);
                    ArmyButton button = this.mainView.getWorldMapView().getBesiegingArmiesView().addToBesiegingArmyView(selectedBesiegingArmy, selectedBesiegingArmy.getCurrentLocation().toLowerCase());
                    if (this.mainView.getWorldMapView().getBesiegingArmiesView().getControlledArmiesUnitsView().isEmpty())
                        return;
                    this.mainView.getWorldMapView().getBesiegingArmiesView().getControlledArmiesUnitsView().get(this.mainView.getWorldMapView().getBesiegingArmiesView().getControlledArmiesUnitsView().size() - 1).updateToBesiegingFrame();
                    indexOfSelectedBesiegingArmy = this.mainView.getWorldMapView().getBesiegingArmiesView()
                            .getAllControlledArmies().indexOf(selectedBesiegingArmy);
                    if (indexOfSelectedBesiegingArmy == -1 || this.mainView.getWorldMapView().getBesiegingArmiesView().getControlledArmiesUnitsView().isEmpty())
                        return;
                    this.mainView.getWorldMapView().getBesiegingArmiesView().getControlledArmiesUnitsView().get(indexOfSelectedBesiegingArmy).setTargetCityName(selectedBesiegingArmy.getCurrentLocation().toLowerCase());
                    this.mainView.getWorldMapView().getBesiegingArmiesView().getControlledArmiesUnitsView().get(indexOfSelectedBesiegingArmy).getTargetCityNameLabel().setText(selectedBesiegingArmy.getCurrentLocation().toLowerCase());
                    this.mainView.getWorldMapView().getBesiegingArmiesView().getControlledArmiesUnitsView().get(indexOfSelectedBesiegingArmy).getTargetCityNameLabel().setVisible(true);
                    if (button == null)
                        return;
                    button.getArmyInfoLabel().getStatusLabel().setVisible(true);
                    button.getArmyInfoLabel().getArmyNumberLabel().setText("Army Number :" + button.getArmyInfoLabel().getArmyNumber() + 1);
                    this.mainView.getWorldMapView().getBesiegingArmiesView().getControlledArmiesUnitsView().get(indexOfSelectedBesiegingArmy).getAutoResolveButton().addActionListener(this);
                    button.getArmyInfoLabel().getStatusLabel().setText("Status :" + Status.BESIEGING);
                    this.mainView.getWorldMapView().getBesiegingArmiesView().getControlledArmiesUnitsView().get(indexOfSelectedBesiegingArmy).updateToBesiegingFrame();
                    this.mainView.getWorldMapView().getBesiegingArmiesView().getControlledArmiesUnitsView().get(indexOfSelectedBesiegingArmy).setEnemyUnitButtonsMap(enemyUnitButtonsMap);
                    this.mainView.getWorldMapView().getMarchingArmiesView().reload();
                    this.mainView.getWorldMapView().getBesiegingArmiesView().reload();
                    this.mainView.getWorldMapView().getControlledArmiesView().reload();
                    this.mainView.getWorldMapView().reload();
                    break;
                } catch (TargetNotReachedException | FriendlyCityException ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage(), "Action Denied", JOptionPane.WARNING_MESSAGE);
                }
                selectedBesiegingArmy = null;
                indexOfSelectedBesiegingArmy = -1;
            }
        }
    }

    public void handleTargetCityButton(ActionEvent e) {
        if (indexOfSelectedMarchingArmy >= this.mainView.getWorldMapView().getControlledArmiesView().getControlledArmiesUnitsView().size())
            return;
        if (e.getSource() == this.mainView.getWorldMapView().getControlledArmiesView().getControlledArmiesUnitsView().get(indexOfSelectedMarchingArmy).getTargetRomeButton()) {
            this.game.targetCity(selectedMarchingArmy, "rome");
            this.mainView.getWorldMapView().getControlledArmiesView().addToMarchingView(selectedMarchingArmy);
            ArmyButton button = this.mainView.getWorldMapView().getMarchingArmiesView().addToMarchingArmyView(selectedMarchingArmy, "onRoad");
            this.mainView.getWorldMapView().getControlledArmiesView().getControlledArmiesUnitsView().get(indexOfSelectedMarchingArmy).dispose();
            indexOfSelectedMarchingArmy = this.mainView.getWorldMapView().getMarchingArmiesView().getAllControlledArmies().indexOf(selectedMarchingArmy);
            if (indexOfSelectedMarchingArmy == -1)
                return;
            this.mainView.getWorldMapView().getMarchingArmiesView().getControlledArmiesUnitsView().get(indexOfSelectedMarchingArmy).setTargetCityName("rome");
            this.mainView.getWorldMapView().getMarchingArmiesView().getControlledArmiesUnitsView().get(indexOfSelectedMarchingArmy).setDistanceLeft(selectedMarchingArmy.getDistanceToTarget());
            if (button == null)
                return;
            button.getArmyInfoLabel().getTargetCityLabel().setVisible(true);
            button.getArmyInfoLabel().getDistanceToTargetLabel().setVisible(true);
            button.getArmyInfoLabel().getStatusLabel().setVisible(true);
            button.getArmyInfoLabel().getTargetCityLabel().setText("Target : Rome");

            button.getArmyInfoLabel().getDistanceToTargetLabel().setText("DistanceToTarget : " + selectedMarchingArmy.getDistanceToTarget());
            button.getArmyInfoLabel().getStatusLabel().setText("Status : " + Status.MARCHING);
            this.mainView.getWorldMapView().getMarchingArmiesView().getControlledArmiesUnitsView().get(indexOfSelectedMarchingArmy).updateToMarchingFrame();
            this.mainView.getWorldMapView().getMarchingArmiesView().reload();
            this.mainView.getWorldMapView().getControlledArmiesView().reload();
            this.mainView.getWorldMapView().reload();
        }
        if (e.getSource() == this.mainView.getWorldMapView().getControlledArmiesView().getControlledArmiesUnitsView().get(indexOfSelectedMarchingArmy).getTargetSpartaButton()) {
            this.game.targetCity(selectedMarchingArmy, "sparta");
            this.mainView.getWorldMapView().getControlledArmiesView().addToMarchingView(selectedMarchingArmy);
            ArmyButton button = this.mainView.getWorldMapView().getMarchingArmiesView().addToMarchingArmyView(selectedMarchingArmy, "onRoad");
            this.mainView.getWorldMapView().getControlledArmiesView().getControlledArmiesUnitsView().get(indexOfSelectedMarchingArmy).dispose();
            indexOfSelectedMarchingArmy = this.mainView.getWorldMapView().getMarchingArmiesView().getAllControlledArmies().indexOf(selectedMarchingArmy);
            if (indexOfSelectedMarchingArmy == -1)
                return;
            this.mainView.getWorldMapView().getMarchingArmiesView().getControlledArmiesUnitsView().get(indexOfSelectedMarchingArmy).setTargetCityName("sparta");
            this.mainView.getWorldMapView().getMarchingArmiesView().getControlledArmiesUnitsView().get(indexOfSelectedMarchingArmy).setDistanceLeft(selectedMarchingArmy.getDistanceToTarget());
            if (button == null)
                return;
            button.getArmyInfoLabel().getTargetCityLabel().setVisible(true);
            button.getArmyInfoLabel().getDistanceToTargetLabel().setVisible(true);
            button.getArmyInfoLabel().getStatusLabel().setVisible(true);
            button.getArmyInfoLabel().getTargetCityLabel().setText("Target : Sparta");
            button.getArmyInfoLabel().getDistanceToTargetLabel().setText("DistanceToTarget : " + selectedMarchingArmy.getDistanceToTarget());
            button.getArmyInfoLabel().getStatusLabel().setText("Status : " + Status.MARCHING);
            this.mainView.getWorldMapView().getMarchingArmiesView().getControlledArmiesUnitsView().get(indexOfSelectedMarchingArmy).updateToMarchingFrame();
            this.mainView.getWorldMapView().getMarchingArmiesView().reload();
            this.mainView.getWorldMapView().getControlledArmiesView().reload();
            this.mainView.getWorldMapView().reload();
        }

        if (e.getSource() == this.mainView.getWorldMapView().getControlledArmiesView().getControlledArmiesUnitsView().get(indexOfSelectedMarchingArmy).getTargetCairoButton()) {
            this.game.targetCity(selectedMarchingArmy, "cairo");
            this.mainView.getWorldMapView().getControlledArmiesView().addToMarchingView(selectedMarchingArmy);
            ArmyButton button = this.mainView.getWorldMapView().getMarchingArmiesView().addToMarchingArmyView(selectedMarchingArmy, "onRoad");
            this.mainView.getWorldMapView().getControlledArmiesView().getControlledArmiesUnitsView().get(indexOfSelectedMarchingArmy).dispose();
            indexOfSelectedMarchingArmy = this.mainView.getWorldMapView().getMarchingArmiesView().getAllControlledArmies().indexOf(selectedMarchingArmy);
            if (indexOfSelectedMarchingArmy == -1)
                return;
            this.mainView.getWorldMapView().getMarchingArmiesView().getControlledArmiesUnitsView().get(indexOfSelectedMarchingArmy).setTargetCityName("cairo");
            this.mainView.getWorldMapView().getMarchingArmiesView().getControlledArmiesUnitsView().get(indexOfSelectedMarchingArmy).setDistanceLeft(selectedMarchingArmy.getDistanceToTarget());
            if (button == null)
                return;
            button.getArmyInfoLabel().getTargetCityLabel().setVisible(true);
            button.getArmyInfoLabel().getDistanceToTargetLabel().setVisible(true);
            button.getArmyInfoLabel().getStatusLabel().setVisible(true);
            button.getArmyInfoLabel().getTargetCityLabel().setText("Target : Cairo");
            button.getArmyInfoLabel().getDistanceToTargetLabel().setText("DistanceToTarget : " + selectedMarchingArmy.getDistanceToTarget());
            button.getArmyInfoLabel().getStatusLabel().setText("Status : " + Status.MARCHING);
            this.mainView.getWorldMapView().getMarchingArmiesView().getControlledArmiesUnitsView().get(indexOfSelectedMarchingArmy).updateToMarchingFrame();
            this.mainView.getWorldMapView().getMarchingArmiesView().reload();
            this.mainView.getWorldMapView().getControlledArmiesView().reload();
            this.mainView.getWorldMapView().reload();
            selectedMarchingArmy = null;
            indexOfSelectedMarchingArmy = -1;
        }
        this.addAutoResolveActionListeners();
        this.initializeControlledArmiesUnitFramesMap();
    }

    public void handleEndTurnButton(ActionEvent e) {
        CustomButton buttonMap = this.mainView.getWorldMapView().getPlayerLabel().getEndTurn();
        CustomButton buttonCairo = this.mainView.getWorldMapView().getCairoOpeningView().getCairoCityView().getPlayerLabel().getEndTurn();
        CustomButton buttonSparta = this.mainView.getWorldMapView().getSpartaOpeningView().getSpartaCityView().getPlayerLabel().getEndTurn();
        CustomButton buttonRome = this.mainView.getWorldMapView().getRomeOpeningView().getRomeCityView().getPlayerLabel().getEndTurn();
        if (e.getSource() == buttonMap || e.getSource() == buttonCairo || e.getSource() == buttonSparta || e.getSource() == buttonRome) {
            this.game.endTurn();
            handleGameOver();
            this.mainView.getWorldMapView().getMarchingArmiesView().updateDistance();
            for (UnitsView controlledArmiesUnitsFrame : this.mainView.getWorldMapView().getMarchingArmiesView().getControlledArmiesUnitsView()) {
                controlledArmiesUnitsFrame.updateDistance();
                if (this.autoResolveFlag) {
                    this.autoResolveFlag = false;
                    int index = this.mainView.getWorldMapView().getMarchingArmiesView().getControlledArmiesUnitsView().indexOf(controlledArmiesUnitsFrame);
                    this.mainView.getWorldMapView().getMarchingArmiesView().deleteArmy(this.mainView.getWorldMapView().
                            getMarchingArmiesView().getAllControlledArmies().get(index));
                }
            }
            for (UnitsView controlledArmiesUnitsFrame : this.mainView.getWorldMapView().getBesiegingArmiesView().getControlledArmiesUnitsView()) {
                if (controlledArmiesUnitsFrame.isFlag() || this.autoResolveFlag) {
                    controlledArmiesUnitsFrame.setFlag(false);
                    controlledArmiesUnitsFrame.takeAction();
                    this.autoResolveFlag = false;
                    int index = this.mainView.getWorldMapView().getBesiegingArmiesView().getControlledArmiesUnitsView().indexOf(controlledArmiesUnitsFrame);
                    this.mainView.getWorldMapView().getBesiegingArmiesView().removeArmy(this.mainView.getWorldMapView().
                            getBesiegingArmiesView().getAllControlledArmies().get(index));
                }
            }
            WorldMapView worldMapView = this.mainView.getWorldMapView();
            worldMapView.getControlledArmiesView().getFoodLabel().setText("Food : " + this.game.getPlayer().getFood());
            worldMapView.getMarchingArmiesView().getFoodLabel().setText("Food : " + this.game.getPlayer().getFood());
            worldMapView.getBesiegingArmiesView().getFoodLabel().setText("Food : " + this.game.getPlayer().getFood());
            worldMapView.getCairoOpeningView().getDefendingArmyCairoView().getFoodLabel().setText("Food : " + this.game.getPlayer().getFood());
            worldMapView.getRomeOpeningView().getDefendingArmyRomeView().getFoodLabel().setText("Food : " + this.game.getPlayer().getFood());
            worldMapView.getSpartaOpeningView().getDefendingArmySpartaView().getFoodLabel().setText("Food : " + this.game.getPlayer().getFood());
            worldMapView.getControlledArmiesView().getTreasuryLabel().setText("Treasury : " + this.game.getPlayer().getTreasury());
            worldMapView.getMarchingArmiesView().getTreasuryLabel().setText("Treasury : " + this.game.getPlayer().getTreasury());
            worldMapView.getBesiegingArmiesView().getTreasuryLabel().setText("Treasury : " + this.game.getPlayer().getTreasury());
            worldMapView.getSpartaOpeningView().getDefendingArmySpartaView().getTreasuryLabel().setText("Treasury : " + this.game.getPlayer().getTreasury());
            worldMapView.getCairoOpeningView().getDefendingArmyCairoView().getTreasuryLabel().setText("Treasury : " + this.game.getPlayer().getTreasury());
            worldMapView.getRomeOpeningView().getDefendingArmyRomeView().getTreasuryLabel().setText("Treasury : " + this.game.getPlayer().getTreasury());
            updatePlayerLabel();
        }
    }

    public void handleGameOver() {
        if (this.game.isGameOver()) {
            ImageIcon imageIcon;
            if (this.game.isVictorious()) {
                imageIcon = new ImageIcon("resources/images/victory.jpg");
                JOptionPane.showMessageDialog(null, "YOU ARE VICTORIOUS", "GAME OVER", JOptionPane.INFORMATION_MESSAGE, imageIcon);
            } else {
                imageIcon = new ImageIcon("resources/images/defeat.jpg");
                JOptionPane.showMessageDialog(null, "YOU ARE DEFEATED", "GAME OVER", JOptionPane.INFORMATION_MESSAGE, imageIcon);
            }
            System.exit(0);
        }
    }

    public void handleManualBattleAndAutoResolve(ActionEvent e) {  // TODO autoResolve bug
        for (UnitsView frame : this.mainView.getWorldMapView().getMarchingArmiesView().getControlledArmiesUnitsView()) {
            UnitButton button = null;
            for (UnitButton unitButton : frame.getUnitButtons()) {
                if (unitButton != null) {
                    button = unitButton;
                    break;
                }
            }
            if (frame.isFlag()) {
                if (frame.getBattleView().isWhoWon()) {
                    if (button != null) {
                        this.game.occupy(button.getUnit().getParentArmy(), frame.getTargetCityName().toLowerCase());
                        if (frame.getTargetCityName().equalsIgnoreCase("Rome")) {
                            this.mainView.getWorldMapView().setRome(true);
                            for (Unit unit : button.getUnit().getParentArmy().getUnits()) {
                                this.mainView.getWorldMapView().getRomeOpeningView().getDefendingArmyRomeView().addToView(unit);
                            }
                            this.mainView.getWorldMapView().initializeButtons();
                        } else if (frame.getTargetCityName().equalsIgnoreCase("Cairo")) {
                            this.mainView.getWorldMapView().setCairo(true);
                            for (Unit unit : button.getUnit().getParentArmy().getUnits()) {
                                this.mainView.getWorldMapView().getCairoOpeningView().getDefendingArmyCairoView().addToView(unit);
                            }
                            this.mainView.getWorldMapView().initializeButtons();
                        } else if (frame.getTargetCityName().equalsIgnoreCase("Sparta")) {
                            this.mainView.getWorldMapView().setSparta(true);
                            for (Unit unit : button.getUnit().getParentArmy().getUnits()) {
                                this.mainView.getWorldMapView().getSpartaOpeningView().getDefendingArmySpartaView().addToView(unit);
                            }
                            this.mainView.getWorldMapView().initializeButtons();
                        }
                        this.mainView.getWorldMapView().reload();
                    }
                    break;
                } else if (!frame.getBattleView().isWhoWon() && button != null) {
                    this.game.getPlayer().getControlledArmies().remove(button.getUnit().getParentArmy());
                    City correspondingCity = this.game.getCorrespondingAvailableCity(frame.getTargetCityName().toLowerCase());
                    if (correspondingCity != null) {
                        correspondingCity.setUnderSiege(false);
                        correspondingCity.setTurnsUnderSiege(-1);
                    }
                    break;
                }
                frame.setFlag(false);
                this.mainView.getWorldMapView().reload();
            } else if (e.getSource() == frame.getAutoResolveButton() && button != null && button.getUnit().getParentArmy() != null) {
                try {
                    this.autoResolveFlag = true;
                    Army defender = null;
                    switch (frame.getTargetCityName().toLowerCase()) {
                        case "rome":
                            defender = this.game.getCorrespondingAvailableCity("rome").getDefendingArmy();
                            break;
                        case "cairo":
                            defender = this.game.getCorrespondingAvailableCity("cairo").getDefendingArmy();
                            break;
                        case "sparta":
                            defender = this.game.getCorrespondingAvailableCity("sparta").getDefendingArmy();
                            break;
                    }
                    if (defender != null) {
                        this.game.autoResolve(button.getUnit().getParentArmy(), defender);
                        if (this.game.isWhoWon()) {
                            if (frame.getTargetCityName().equalsIgnoreCase("Rome")) {
                                this.mainView.getWorldMapView().setRome(true);
                                for (Unit unit : button.getUnit().getParentArmy().getUnits()) {
                                    this.mainView.getWorldMapView().getRomeOpeningView().getDefendingArmyRomeView().addToView(unit);
                                }
                                this.mainView.getWorldMapView().initializeButtons();
                            } else if (frame.getTargetCityName().equalsIgnoreCase("Cairo")) {
                                this.mainView.getWorldMapView().setCairo(true);
                                for (Unit unit : button.getUnit().getParentArmy().getUnits()) {
                                    this.mainView.getWorldMapView().getCairoOpeningView().getDefendingArmyCairoView().addToView(unit);
                                }
                                this.mainView.getWorldMapView().initializeButtons();
                            } else if (frame.getTargetCityName().equalsIgnoreCase("Sparta")) {
                                this.mainView.getWorldMapView().setSparta(true);
                                for (Unit unit : button.getUnit().getParentArmy().getUnits()) {
                                    this.mainView.getWorldMapView().getSpartaOpeningView().getDefendingArmySpartaView().addToView(unit);
                                }
                                this.mainView.getWorldMapView().initializeButtons();
                            }
                            this.mainView.getWorldMapView().reload();
                            JOptionPane.showMessageDialog(null, "YOU WON THE BATTLE", "Battle Over", JOptionPane.INFORMATION_MESSAGE);
                        } else {
                            this.autoResolveFlag = true;
                            if (!flag) {
                                JOptionPane.showMessageDialog(null, "YOU LOST THE BATTLE", "Battle Over", JOptionPane.INFORMATION_MESSAGE);
                                flag = true;
                            }
                        }
                        break;
                    }
                    this.autoResolveFlag = true;
                    frame.dispose();
                    break;
                } catch (FriendlyFireException ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage(), "Action Denied", JOptionPane.WARNING_MESSAGE);
                }
                this.autoResolveFlag = true;
                break;
            }
        }
    }

    public void handleSiegeBattle(ActionEvent e) {
        for (UnitsView frame : this.mainView.getWorldMapView().getBesiegingArmiesView().getControlledArmiesUnitsView()) {
            UnitButton button = null;
            for (UnitButton unitButton : frame.getUnitButtons()) {
                if (unitButton != null) {
                    button = unitButton;
                    break;
                }
            }
            if (frame.isFlag()) {
                if (frame.getBattleView().isWhoWon()) {
                    if (button != null) {
                        this.game.occupy(button.getUnit().getParentArmy(), frame.getTargetCityName().toLowerCase());
                        if (frame.getTargetCityName().equalsIgnoreCase("Rome")) {
                            this.mainView.getWorldMapView().setRome(true);
                            for (Unit unit : button.getUnit().getParentArmy().getUnits()) {
                                this.mainView.getWorldMapView().getRomeOpeningView().getDefendingArmyRomeView().addToView(unit);
                            }
                            this.mainView.getWorldMapView().initializeButtons();
                        } else if (frame.getTargetCityName().equalsIgnoreCase("Cairo")) {
                            this.mainView.getWorldMapView().setCairo(true);
                            for (Unit unit : button.getUnit().getParentArmy().getUnits()) {
                                this.mainView.getWorldMapView().getCairoOpeningView().getDefendingArmyCairoView().addToView(unit);
                            }
                            this.mainView.getWorldMapView().initializeButtons();
                        } else if (frame.getTargetCityName().equalsIgnoreCase("Sparta")) {
                            this.mainView.getWorldMapView().setSparta(true);
                            for (Unit unit : button.getUnit().getParentArmy().getUnits()) {
                                this.mainView.getWorldMapView().getSpartaOpeningView().getDefendingArmySpartaView().addToView(unit);
                            }
                            this.mainView.getWorldMapView().initializeButtons();
                        }
                        this.mainView.getWorldMapView().reload();
                    }
                    break;
                } else if (!frame.getBattleView().isWhoWon() && button != null) {
                    this.game.getPlayer().getControlledArmies().remove(button.getUnit().getParentArmy());
                    City correspondingCity = this.game.getCorrespondingAvailableCity(frame.getTargetCityName().toLowerCase());
                    if (correspondingCity != null) {
                        correspondingCity.setUnderSiege(false);
                        correspondingCity.setTurnsUnderSiege(-1);
                    }
                    break;
                }
                frame.setFlag(false);
                this.mainView.getWorldMapView().reload();
            } else if (e.getSource() == frame.getAutoResolveButton() && button != null && button.getUnit().getParentArmy() != null) {
                try {
                    Army defender = null;
                    this.autoResolveFlag = true;
                    switch (frame.getTargetCityName().toLowerCase()) {
                        case "rome":
                            defender = this.game.getCorrespondingAvailableCity("rome").getDefendingArmy();
                            break;
                        case "cairo":
                            defender = this.game.getCorrespondingAvailableCity("cairo").getDefendingArmy();
                            break;
                        case "sparta":
                            defender = this.game.getCorrespondingAvailableCity("sparta").getDefendingArmy();
                            break;
                    }
                    if (defender != null) {
                        this.game.autoResolve(button.getUnit().getParentArmy(), defender);
                        if (this.game.isWhoWon()) {
                            if (frame.getTargetCityName().equalsIgnoreCase("Rome")) {
                                this.mainView.getWorldMapView().setRome(true);
                                for (Unit unit : button.getUnit().getParentArmy().getUnits()) {
                                    this.mainView.getWorldMapView().getRomeOpeningView().getDefendingArmyRomeView().addToView(unit);
                                }
                                this.mainView.getWorldMapView().initializeButtons();
                            } else if (frame.getTargetCityName().equalsIgnoreCase("Cairo")) {
                                this.mainView.getWorldMapView().setCairo(true);
                                for (Unit unit : button.getUnit().getParentArmy().getUnits()) {
                                    this.mainView.getWorldMapView().getCairoOpeningView().getDefendingArmyCairoView().addToView(unit);
                                }
                                this.mainView.getWorldMapView().initializeButtons();
                            } else if (frame.getTargetCityName().equalsIgnoreCase("Sparta")) {
                                this.mainView.getWorldMapView().setSparta(true);
                                for (Unit unit : button.getUnit().getParentArmy().getUnits()) {
                                    this.mainView.getWorldMapView().getSpartaOpeningView().getDefendingArmySpartaView().addToView(unit);
                                }
                                this.mainView.getWorldMapView().initializeButtons();
                            }
                            this.mainView.getWorldMapView().reload();
                            JOptionPane.showMessageDialog(null, "YOU WON THE BATTLE", "Battle Over", JOptionPane.INFORMATION_MESSAGE);
                        } else {
                            this.autoResolveFlag = true;
                            if (!flag) {
                                JOptionPane.showMessageDialog(null, "YOU LOST THE BATTLE", "Battle Over", JOptionPane.INFORMATION_MESSAGE);
                                flag = true;
                            }
                        }
                        break;
                    }
                    this.autoResolveFlag = true;
                    frame.dispose();
                    break;
                } catch (FriendlyFireException ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage(), "Action Denied", JOptionPane.WARNING_MESSAGE);
                }
                this.autoResolveFlag = true;
                break;
            }
        }
    }

    public void disableCorrespondingCityButton() {
        if (selectedMarchingArmy.getCurrentLocation().equalsIgnoreCase("cairo")) {
            this.mainView.getWorldMapView().getControlledArmiesView().getControlledArmiesUnitsView().get(indexOfSelectedMarchingArmy).getTargetRomeButton().setEnabled(true);
            this.mainView.getWorldMapView().getControlledArmiesView().getControlledArmiesUnitsView().get(indexOfSelectedMarchingArmy).getTargetSpartaButton().setEnabled(true);
        } else if (selectedMarchingArmy.getCurrentLocation().equalsIgnoreCase("sparta")) {
            this.mainView.getWorldMapView().getControlledArmiesView().getControlledArmiesUnitsView().get(indexOfSelectedMarchingArmy).getTargetRomeButton().setEnabled(true);
            this.mainView.getWorldMapView().getControlledArmiesView().getControlledArmiesUnitsView().get(indexOfSelectedMarchingArmy).getTargetCairoButton().setEnabled(true);
        } else if (selectedMarchingArmy.getCurrentLocation().equalsIgnoreCase("rome")) {
            this.mainView.getWorldMapView().getControlledArmiesView().getControlledArmiesUnitsView().get(indexOfSelectedMarchingArmy).getTargetCairoButton().setEnabled(true);
            this.mainView.getWorldMapView().getControlledArmiesView().getControlledArmiesUnitsView().get(indexOfSelectedMarchingArmy).getTargetSpartaButton().setEnabled(true);
        }
    }

    public void disableRemainingButtons(UnitButton unitButton) {
        for (UnitButton button : this.mainView.getWorldMapView().getCairoOpeningView().getDefendingArmyCairoView().getUnitButtons()) {
            if (!button.equals(unitButton))
                button.setEnabled(false);
        }

        for (UnitButton button : this.mainView.getWorldMapView().getRomeOpeningView().getDefendingArmyRomeView().getUnitButtons()) {
            if (!button.equals(unitButton))
                button.setEnabled(false);
        }

        for (UnitButton button : this.mainView.getWorldMapView().getSpartaOpeningView().getDefendingArmySpartaView().getUnitButtons()) {
            if (!button.equals(unitButton))
                button.setEnabled(false);
        }
    }

    public void reEnableAllButtons() {
        for (UnitButton button : this.mainView.getWorldMapView().getCairoOpeningView().getDefendingArmyCairoView().getUnitButtons())
            button.setEnabled(true);
        for (UnitButton button : this.mainView.getWorldMapView().getRomeOpeningView().getDefendingArmyRomeView().getUnitButtons())
            button.setEnabled(true);
        for (UnitButton button : this.mainView.getWorldMapView().getSpartaOpeningView().getDefendingArmySpartaView().getUnitButtons())
            button.setEnabled(true);
    }

    public void disableInitiateArmyButtons() {
        this.mainView.getWorldMapView().getCairoOpeningView().getDefendingArmyCairoView().getInitiateArmyButton().setEnabled(false);
        this.mainView.getWorldMapView().getRomeOpeningView().getDefendingArmyRomeView().getInitiateArmyButton().setEnabled(false);
        this.mainView.getWorldMapView().getSpartaOpeningView().getDefendingArmySpartaView().getInitiateArmyButton().setEnabled(false);
    }

    public void updatePlayerLabel() {
        this.mainView.getWorldMapView().getPlayerLabel().getFoodLabel().setText("Food: " + this.game.getPlayer().getFood());
        this.mainView.getWorldMapView().getPlayerLabel().getTreasuryLabel().setText("Treasury: \uD83D\uDCB0 " + this.game.getPlayer().getTreasury());
        this.mainView.getWorldMapView().getPlayerLabel().getTurnsLabel().setText(this.game.getCurrentTurnCount() + " Turns");
        this.mainView.getWorldMapView().getPlayerLabel().repaint();
        this.mainView.getWorldMapView().getPlayerLabel().revalidate();
        this.mainView.getWorldMapView().getCairoOpeningView().getCairoCityView().getPlayerLabel().getTreasuryLabel().setText(
                "Treasury: \uD83D\uDCB0 " + this.game.getPlayer().getTreasury());
        this.mainView.getWorldMapView().getCairoOpeningView().getCairoCityView().getPlayerLabel().getFoodLabel().setText(
                "Food: " + this.game.getPlayer().getFood()
        );
        this.mainView.getWorldMapView().getCairoOpeningView().getCairoCityView().getPlayerLabel().getTurnsLabel().setText(this.game.getCurrentTurnCount() + " Turns");
        this.mainView.getWorldMapView().getCairoOpeningView().getCairoCityView().getPlayerLabel().repaint();
        this.mainView.getWorldMapView().getCairoOpeningView().getCairoCityView().getPlayerLabel().revalidate();

        this.mainView.getWorldMapView().getRomeOpeningView().getRomeCityView().getPlayerLabel().getTreasuryLabel().setText(
                "Treasury: \uD83D\uDCB0 " + this.game.getPlayer().getTreasury());
        this.mainView.getWorldMapView().getRomeOpeningView().getRomeCityView().getPlayerLabel().getFoodLabel().setText(
                "Food: " + this.game.getPlayer().getFood()
        );
        this.mainView.getWorldMapView().getRomeOpeningView().getRomeCityView().getPlayerLabel().getTurnsLabel().setText(this.game.getCurrentTurnCount() + " Turns");
        this.mainView.getWorldMapView().getRomeOpeningView().getRomeCityView().getPlayerLabel().repaint();
        this.mainView.getWorldMapView().getRomeOpeningView().getRomeCityView().getPlayerLabel().revalidate();

        this.mainView.getWorldMapView().getSpartaOpeningView().getSpartaCityView().getPlayerLabel().getTreasuryLabel().setText(
                "Treasury: \uD83D\uDCB0 " + this.game.getPlayer().getTreasury());
        this.mainView.getWorldMapView().getSpartaOpeningView().getSpartaCityView().getPlayerLabel().getFoodLabel().setText(
                "Food: " + this.game.getPlayer().getFood()
        );
        this.mainView.getWorldMapView().getSpartaOpeningView().getSpartaCityView().getPlayerLabel().getTurnsLabel().setText(this.game.getCurrentTurnCount() + " Turns");
        this.mainView.getWorldMapView().getSpartaOpeningView().getSpartaCityView().getPlayerLabel().repaint();
        this.mainView.getWorldMapView().getSpartaOpeningView().getSpartaCityView().getPlayerLabel().revalidate();
    }

    public void resetTargetButtons() {
        for (UnitsView frame : this.mainView.getWorldMapView().getControlledArmiesView().getControlledArmiesUnitsView())
            frame.resetTargetButtons();
    }

    public void initializeControlledArmiesUnitFramesMap() {
        if (enemyUnitButtonsMap == null)
            return;
        for (UnitsView frame : this.mainView.getWorldMapView().getMarchingArmiesView().getControlledArmiesUnitsView())
            frame.setEnemyUnitButtonsMap(enemyUnitButtonsMap);
        for (UnitsView frame : this.mainView.getWorldMapView().getBesiegingArmiesView().getControlledArmiesUnitsView())
            frame.setEnemyUnitButtonsMap(enemyUnitButtonsMap);
    }

    public void playSound(String path) {
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(path).getAbsoluteFile());
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.start();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            handleContinueButton(e);
        } catch (IOException ioException) {
            System.out.println(ioException.getMessage());
        }
        if (worldMapFlag) {
            handleCairoButtons(e);
            handleSpartaButtons(e);
            handleRomeButtons(e);
            handleEndTurnButton(e);
            handleUnitButtons(e);

            if (unit != null) {
                handleInitiateArmyButton(e);
                handleRelocateUnitButton(e);
                reEnableAllButtons();
            }

            handleArmyButtons(e);

            if (selectedMarchingArmy != null && indexOfSelectedMarchingArmy != -1)
                handleTargetCityButton(e);

            if (selectedBesiegingArmy != null && indexOfSelectedBesiegingArmy != -1)
                handleLaySiegeButton(e);

            handleManualBattleAndAutoResolve(e);
            handleSiegeBattle(e);
        }
    }
}