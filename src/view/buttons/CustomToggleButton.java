package view.buttons;

import javax.swing.*;
import java.awt.*;

public class CustomToggleButton extends JToggleButton {

    public CustomToggleButton() {
        this.setCursor(new Cursor(Cursor.HAND_CURSOR));
        this.setVisible(true);
        this.setFocusable(false);
        this.setEnabled(true);
    }
}
