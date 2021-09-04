package view.buttons;

import units.*;
import view.labels.ArcherLabel;
import view.labels.CavalryLabel;
import view.labels.InfantryLabel;
import view.labels.UnitInfoLabel;

import javax.swing.*;
import java.awt.*;

public class UnitButton extends CustomToggleButton {

    private final int level;
    private JLabel pictureLabel;
    private final Status status;
    private final int maxSoldierCount;
    private final Unit unit;
    private final UnitInfoLabel unitInfoLabel;


    public UnitButton(Status status, int level, String type, double soldierCount, int maxSoldierCount, Unit unit) {
        this.level = level;
        this.status = status;
        this.maxSoldierCount = maxSoldierCount;
        this.unit = unit;
        this.setBackground(Color.DARK_GRAY);
        this.setVisible(true);
        this.setPreferredSize(new Dimension(325, 135));
        this.setLayout(new GridLayout(1, 2));

        switch (type.toLowerCase()) {
            case "archer":
                pictureLabel = new ArcherLabel(level);
                break;
            case "infantry":
                pictureLabel = new InfantryLabel(level);
                break;
            case "cavalry":
                pictureLabel = new CavalryLabel(level);
                break;
        }

        this.add(pictureLabel);
        unitInfoLabel = new UnitInfoLabel(status, level, type, soldierCount, maxSoldierCount);
        this.add(unitInfoLabel);
    }

    public UnitInfoLabel getUnitInfoLabel() {
        return unitInfoLabel;
    }

    public Status getStatus() {
        return status;
    }

    public int getLevel() {
        return level;
    }

    public int getMaxSoldierCount() {
        return maxSoldierCount;
    }

    public Unit getUnit() {
        return unit;
    }
}
