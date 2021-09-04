package view.buttons;

import javax.swing.*;
import java.awt.*;

public class CustomButton extends JButton {

    public CustomButton() {
        this.setCursor(new Cursor(Cursor.HAND_CURSOR));
        this.setVisible(true);
        this.setFocusable(false);
        this.setEnabled(true);
    }
}
