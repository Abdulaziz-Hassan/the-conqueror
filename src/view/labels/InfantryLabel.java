package view.labels;

import javax.swing.*;
import java.awt.*;

public class InfantryLabel extends JLabel {

    public InfantryLabel(int level) {
        this.setVisible(true);
        this.setPreferredSize(new Dimension(200, 300));
        switch (level) {
            case 1:
                this.setIcon(new ImageIcon("resources/images/Infantry1Editednew.jpg"));
                break;
            case 2:
                this.setIcon(new ImageIcon("resources/images/Infantry2EditedNew.jpg"));
                break;
            case 3:
                this.setIcon(new ImageIcon("resources/images/Infantry3EditedNew.jpg"));
                break;
        }
    }
}
