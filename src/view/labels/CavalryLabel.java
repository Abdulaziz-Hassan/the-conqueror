package view.labels;

import javax.swing.*;
import java.awt.*;

public class CavalryLabel extends JLabel {

    public CavalryLabel(int level) {
        this.setVisible(true);
        this.setPreferredSize(new Dimension(200, 300));
        switch (level) {
            case 1:
                this.setIcon(new ImageIcon("resources/images/Cavalry1New.jpg"));
                break;
            case 2:
                this.setIcon(new ImageIcon("resources/images/Cavalry2EditedNew.jpg"));
                break;
            case 3:
                this.setIcon(new ImageIcon("resources/images/Cavalry3EditedNew.jpg"));
                break;
        }
    }
}
