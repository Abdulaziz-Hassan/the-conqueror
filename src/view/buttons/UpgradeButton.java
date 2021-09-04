package view.buttons;

import java.awt.*;

public class UpgradeButton extends CustomButton {

    public UpgradeButton() {
        this.setVisible(false);
        this.setBackground(Color.WHITE);
        this.setFont(new Font("New Roman", Font.BOLD, 15));
        this.setForeground(Color.BLACK);
        this.setText("Upgrade");
    }
}
