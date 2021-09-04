package view.panels;

import view.buttons.UnitButton;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class PlayerArmyPanel extends JPanel {

    private final ArrayList<UnitButton> playerUnitButtons;

    public PlayerArmyPanel(ArrayList<UnitButton> playerUnitButtons) {
        this.setBorder(BorderFactory.createLineBorder(Color.BLACK, 4));
        this.setBounds(0, 700, 1200, 300);
        this.setVisible(true);
        this.setOpaque(true);
        this.setBackground(Color.BLUE);
        this.playerUnitButtons = playerUnitButtons;
        this.setLayout(new FlowLayout());
        for (UnitButton unitButton : playerUnitButtons)
            this.add(unitButton);
        this.revalidate();
        this.repaint();
    }

    public ArrayList<UnitButton> getPlayerUnitButtons() {
        return playerUnitButtons;
    }
}
