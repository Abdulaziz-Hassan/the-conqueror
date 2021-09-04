package view.views;

import view.buttons.CustomButton;
import view.buttons.UnitButton;
import view.labels.PlayerLabel;
import view.tools.CustomFrame;

import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class WorldMapView extends CustomFrame implements ActionListener {

    private final String firstSelectedCityName;
    private final JLabel mapLabel;
    private final CustomButton cairoButton;
    private final CustomButton romeButton;
    private final CustomButton spartaButton;
    private final CairoOpeningView cairoOpeningView;
    private final SpartaOpeningView spartaOpeningView;
    private final RomeOpeningView romeOpeningView;
    private final CustomButton cairoAttackButton;
    private final CustomButton romeAttackButton;
    private final CustomButton spartaAttackButton;
    private final DefendingArmiesView defendingArmiesView;
    private final ControlledArmiesView controlledArmiesView;
    private final BesiegingArmiesView besiegingArmiesView;
    private final MarchingArmiesView marchingArmiesView;
    private final PlayerLabel playerLabel;
    private final CustomButton myControlledArmiesButton = new CustomButton();
    private final CustomButton myDefendingArmiesButton = new CustomButton();
    private final CustomButton myMarchingArmiesButton = new CustomButton();
    private final CustomButton myBesiegingArmiesButton = new CustomButton();
    private boolean isCairo;
    private boolean isRome;
    private boolean isSparta;

    public WorldMapView(String firstCountrySelected, boolean Gender, String namePlayer, double playerTreasury, double playerFood,
                        HashMap<String, ArrayList<UnitButton>> enemyDefendingArmy) {
        this.firstSelectedCityName = firstCountrySelected;
        myControlledArmiesButton.addActionListener(this);
        myBesiegingArmiesButton.addActionListener(this);
        myMarchingArmiesButton.addActionListener(this);
        try {
            File file = new File("resources/miscellaneous/Opening Credits _ Game of Thrones _ Season 8 (HBO).wav");
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(file);
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.loop(Clip.LOOP_CONTINUOUSLY);
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            System.out.println(e.getMessage());
        }
        playerLabel = new PlayerLabel(Gender, namePlayer, playerTreasury, playerFood);
        playerLabel.setBounds(6, 500, 1530, 450);
        mapLabel = new JLabel();
        ImageIcon image = new ImageIcon("resources/images/newMapEdited.jpg");
        mapLabel.setIcon(image);
        mapLabel.setOpaque(false);
        mapLabel.setVisible(true);
        mapLabel.setVerticalAlignment(JLabel.CENTER);
        mapLabel.setHorizontalAlignment(JLabel.CENTER);
        mapLabel.setBounds(6, -5, 1530, 850);
        mapLabel.setLayout(null);
        cairoOpeningView = new CairoOpeningView(Gender, namePlayer, playerTreasury, playerFood);
        spartaOpeningView = new SpartaOpeningView(Gender, namePlayer, playerTreasury, playerFood);
        romeOpeningView = new RomeOpeningView(Gender, namePlayer, playerTreasury, playerFood);
        cairoButton = new CustomButton();
        cairoButton.setText("Cairo");
        cairoButton.setBackground(Color.BLACK);
        cairoButton.setForeground(new Color(255, 230, 200));
        cairoButton.setFont(new Font("NEW ROMAN", Font.BOLD, 35));
        cairoButton.addActionListener(this);
        cairoAttackButton = new CustomButton();
        cairoAttackButton.setFont(new Font("NEW ROMAN", Font.BOLD, 35));
        cairoAttackButton.setBackground(Color.BLACK);
        cairoAttackButton.addActionListener(this);
        romeButton = new CustomButton();
        romeButton.setText("Rome");
        romeButton.setFont(new Font("NEW ROMAN", Font.BOLD, 35));
        romeButton.setBackground(Color.BLACK);
        romeButton.setForeground(new Color(255, 230, 200));
        romeButton.addActionListener(this);
        romeAttackButton = new CustomButton();
        romeAttackButton.setBackground(Color.BLACK);
        romeAttackButton.addActionListener(this);
        romeAttackButton.setFont(new Font("NEW ROMAN", Font.BOLD, 35));
        spartaButton = new CustomButton();
        spartaButton.setText("Sparta");
        spartaButton.setFont(new Font("NEW ROMAN", Font.BOLD, 35));
        spartaButton.setBackground(Color.BLACK);
        spartaButton.setForeground(new Color(255, 230, 200));
        spartaButton.addActionListener(this);
        spartaAttackButton = new CustomButton();
        spartaAttackButton.setBackground(Color.BLACK);
        spartaAttackButton.addActionListener(this);
        spartaAttackButton.setFont(new Font("NEW ROMAN", Font.BOLD, 35));
        cairoButton.setBounds(600, 390, 145, 50);
        romeButton.setBounds(100, 390, 145, 50);
        spartaButton.setBounds(1170, 390, 145, 50);
        cairoAttackButton.setBounds(750, 390, 145, 50);
        romeAttackButton.setBounds(250, 390, 145, 50);
        spartaAttackButton.setBounds(1320, 390, 145, 50);
        mapLabel.add(cairoButton);
        mapLabel.add(romeButton);
        mapLabel.add(spartaButton);
        this.initializeButtons();
        mapLabel.add(cairoAttackButton);
        mapLabel.add(romeAttackButton);
        mapLabel.add(spartaAttackButton);
        mapLabel.add(playerLabel);
        this.initializeArmyButtons();
        controlledArmiesView = new ControlledArmiesView();
        controlledArmiesView.setVisible(false);
        for (UnitsView controlledArmiesUnitsFrame : controlledArmiesView.getControlledArmiesUnitsView())
            controlledArmiesUnitsFrame.setEnemyUnitButtonsMap(enemyDefendingArmy);
        marchingArmiesView = new MarchingArmiesView();
        marchingArmiesView.setVisible(false);
        besiegingArmiesView = new BesiegingArmiesView();
        besiegingArmiesView.setVisible(false);
        this.add(mapLabel);
        defendingArmiesView = new DefendingArmiesView(isCairo, isSparta, isRome);
        defendingArmiesView.getSpartaButton().addActionListener(this);
        defendingArmiesView.getCairoButton().addActionListener(this);
        defendingArmiesView.getRomeButton().addActionListener(this);
        defendingArmiesView.setVisible(false);
        this.reload();
    }

    public void initializeArmyButtons() {
        myControlledArmiesButton.setText("My Controlled Armies");
        myControlledArmiesButton.setFont(new Font("NEW ROMAN", Font.BOLD, 19));
        myControlledArmiesButton.setBackground(Color.BLACK);
        myControlledArmiesButton.setForeground(new Color(255, 230, 200));
        myControlledArmiesButton.addActionListener(this);
        myControlledArmiesButton.setBounds(10, 0, 250, 80);
        mapLabel.add(myControlledArmiesButton);
        myDefendingArmiesButton.setText("My Defending Armies");
        myDefendingArmiesButton.setFont(new Font("NEW ROMAN", Font.BOLD, 19));
        myDefendingArmiesButton.setBackground(Color.BLACK);
        myDefendingArmiesButton.setForeground(new Color(255, 230, 200));
        myDefendingArmiesButton.addActionListener(this);
        myDefendingArmiesButton.setBounds(10 + 420, 0, 250, 80);
        mapLabel.add(myDefendingArmiesButton);
        myMarchingArmiesButton.setText("My Marching Armies");
        myMarchingArmiesButton.setFont(new Font("NEW ROMAN", Font.BOLD, 19));
        myMarchingArmiesButton.setBackground(Color.BLACK);
        myMarchingArmiesButton.setForeground(new Color(255, 230, 200));
        myMarchingArmiesButton.addActionListener(this);
        myMarchingArmiesButton.setBounds(10 + 420 * 2, 0, 250, 80);
        mapLabel.add(myMarchingArmiesButton);
        myBesiegingArmiesButton.setText("My Besieging Armies");
        myBesiegingArmiesButton.setFont(new Font("NEW ROMAN", Font.BOLD, 19));
        myBesiegingArmiesButton.setBackground(Color.BLACK);
        myBesiegingArmiesButton.setForeground(new Color(255, 230, 200));
        myBesiegingArmiesButton.addActionListener(this);
        myBesiegingArmiesButton.setBounds(10 + 420 * 3, 0, 250, 80);
        mapLabel.add(myBesiegingArmiesButton);
    }

    public void initializeButtons() {
        if (!isCairo) {
            cairoAttackButton.setForeground(new Color(230, 25, 25));
            cairoAttackButton.setText("\uD83D\uDD13");
            cairoButton.setEnabled(false);
        }

        if (firstSelectedCityName.equalsIgnoreCase("Cairo") || isCairo) {
            cairoAttackButton.setForeground(new Color(25, 230, 28));
            cairoAttackButton.setText("✔");
            cairoButton.setEnabled(true);
            isCairo = true;
        }

        if (!isRome) {
            romeAttackButton.setForeground(new Color(230, 25, 25));
            romeAttackButton.setText("\uD83D\uDD13");
            romeButton.setEnabled(false);
        }

        if (firstSelectedCityName.equalsIgnoreCase("Rome") || isRome) {
            romeAttackButton.setForeground(new Color(25, 230, 28));
            romeAttackButton.setText("✔");
            romeButton.setEnabled(true);
            isRome = true;
        }

        if (!isSparta) {
            spartaAttackButton.setForeground(new Color(230, 25, 25));
            spartaAttackButton.setText("\uD83D\uDD13");
            spartaButton.setEnabled(false);
        }

        if (firstSelectedCityName.equalsIgnoreCase("sparta") || isSparta) {
            spartaAttackButton.setForeground(new Color(25, 230, 28));
            spartaAttackButton.setText("✔");
            spartaButton.setEnabled(true);
            isSparta = true;
        }
        this.reload();
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == cairoButton) {
            cairoOpeningView.setVisible(true);
            romeOpeningView.setVisible(false);
            spartaOpeningView.setVisible(false);
            this.reload();
        } else if (e.getSource() == romeButton) {
            cairoOpeningView.setVisible(false);
            romeOpeningView.setVisible(true);
            spartaOpeningView.setVisible(false);
            this.reload();
        } else if (e.getSource() == spartaButton) {
            spartaOpeningView.setVisible(true);
            romeOpeningView.setVisible(false);
            cairoOpeningView.setVisible(false);
            this.reload();
        }
        if (e.getSource() == myDefendingArmiesButton) {
            defendingArmiesView.setVisible(true);
            this.reload();
        }
        if (e.getSource() == defendingArmiesView.getCairoButton())
            getCairoOpeningView().getDefendingArmyCairoView().setVisible(true);

        if (e.getSource() == defendingArmiesView.getRomeButton())
            getRomeOpeningView().getDefendingArmyRomeView().setVisible(true);

        if (e.getSource() == defendingArmiesView.getSpartaButton())
            getSpartaOpeningView().getDefendingArmySpartaView().setVisible(true);

        if (e.getSource() == myControlledArmiesButton)
            controlledArmiesView.setVisible(true);

        if (e.getSource() == myBesiegingArmiesButton)
            besiegingArmiesView.setVisible(true);

        if (e.getSource() == myMarchingArmiesButton)
            marchingArmiesView.setVisible(true);
    }

    public CairoOpeningView getCairoOpeningView() {
        return cairoOpeningView;
    }

    public SpartaOpeningView getSpartaOpeningView() {
        return spartaOpeningView;
    }

    public RomeOpeningView getRomeOpeningView() {
        return romeOpeningView;
    }

    public PlayerLabel getPlayerLabel() {
        return playerLabel;
    }

    public ControlledArmiesView getControlledArmiesView() {
        return controlledArmiesView;
    }

    public boolean isCairo() {
        return isCairo;
    }

    public boolean isRome() {
        return isRome;
    }

    public boolean isSparta() {
        return isSparta;
    }

    public BesiegingArmiesView getBesiegingArmiesView() {
        return besiegingArmiesView;
    }

    public MarchingArmiesView getMarchingArmiesView() {
        return marchingArmiesView;
    }

    public void setCairo(boolean cairo) {
        this.isCairo = cairo;
    }

    public void setRome(boolean rome) {
        this.isRome = rome;
    }

    public void setSparta(boolean sparta) {
        this.isSparta = sparta;
    }
}