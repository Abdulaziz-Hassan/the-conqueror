package view.labels;

import view.buttons.CustomButton;

import javax.swing.*;
import java.awt.*;

public class AddUnitLabel extends JLabel {

    CustomButton addButton;

    public AddUnitLabel() {
        this.addButton = new CustomButton();
        this.setPreferredSize(new Dimension(325, 135));
        this.setVisible(true);
        this.setBackground(Color.WHITE);
        this.setText("Add Unit");
        this.setFont(new Font("New Roman", Font.BOLD, 35));
        this.setHorizontalAlignment(JLabel.CENTER);
        this.setVerticalAlignment(JLabel.TOP);
        this.setForeground(Color.red);
        this.setOpaque(true);
        this.addButton.setText("+");
        this.addButton.setBackground(Color.lightGray);
        this.addButton.setFont(new Font("New Roman", Font.BOLD, 27));
        this.addButton.setBounds(145, 65, 50, 50);
        this.addButton.setVisible(true);
        this.add(addButton);
    }
}
