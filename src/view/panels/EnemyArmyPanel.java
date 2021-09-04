package view.panels;

import view.buttons.UnitButton;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class EnemyArmyPanel extends JPanel {

    private final ArrayList<UnitButton> EnemyUnitButtons;

    public EnemyArmyPanel(ArrayList<UnitButton> EnemyUnitButtons) {
        this.setBorder(BorderFactory.createLineBorder(Color.BLACK, 4));
        this.setBounds(0, 0, 1200, 300);
        this.setVisible(true);
        this.setOpaque(true);
        this.setBackground(Color.RED);
        this.EnemyUnitButtons = EnemyUnitButtons;
        this.setLayout(new FlowLayout());
        for (UnitButton unitButton : EnemyUnitButtons)
            this.add(unitButton);
        this.revalidate();
        this.repaint();
    }

    public ArrayList<UnitButton> getEnemyUnitButtons() {
        return EnemyUnitButtons;
    }
}
