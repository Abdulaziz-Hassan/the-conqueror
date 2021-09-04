package view.tools;

import javax.swing.*;
import java.awt.*;

public class CustomTextArea extends JTextArea {

    public CustomTextArea() {
        this.setVisible(true);
        this.setBackground(new Color(1, 1, 1, 121));
        setFont(new Font("New Roman", Font.BOLD, 30));
    }
}
