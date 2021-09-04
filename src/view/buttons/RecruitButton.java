package view.buttons;

import java.awt.*;

public class RecruitButton extends CustomButton {

    public RecruitButton() {
        this.setVisible(false);
        this.setBackground(Color.WHITE);
        this.setFont(new Font("New Roman", Font.BOLD, 18));
        this.setForeground(Color.BLACK);
        this.setText("Recruit");
    }
}
