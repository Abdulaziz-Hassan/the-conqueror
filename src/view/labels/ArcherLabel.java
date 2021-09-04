package view.labels;

import javax.swing.*;
import java.awt.*;

public class ArcherLabel extends JLabel {

    public ArcherLabel(int level) {
        this.setVisible(true);
        this.setPreferredSize(new Dimension(200, 135));
        switch (level) {
            case 1:
                this.setIcon(new ImageIcon("resources/images/Archer1Edited.jpg"));
                break;
            case 2:
                this.setIcon(new ImageIcon("resources/images/Archer2Edited.jpg"));
                break;
            case 3:
                this.setIcon(new ImageIcon("resources/images/Archer3Edited.jpg"));
                break;
        }
    }
}
