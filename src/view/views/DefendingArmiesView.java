package view.views;

import view.buttons.CloseButton;
import view.buttons.CustomButton;
import view.tools.CustomFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DefendingArmiesView extends CustomFrame implements ActionListener {

    private final boolean cairoFlag;
    private final boolean romeFlag;
    private final boolean spartaFlag;
    private final CustomButton spartaButton = new CustomButton();
    private final CustomButton cairoButton = new CustomButton();
    private final CustomButton romeButton = new CustomButton();
    private final CloseButton closeButton = new CloseButton();
    private final JLabel defendingLabel = new JLabel("Choose your Defending Army");

    public DefendingArmiesView(boolean cairoFlag, boolean spartaFlag, boolean romeFlag) {
        this.setVisible(false);
        this.getContentPane().setBackground(new Color(229, 162, 56));
        this.setLayout(null);
        this.setBounds(300, 200, 1000, 500);
        this.initializeDefendingLabel();
        closeButton.addActionListener(this);
        closeButton.setBounds(900, 0, 100, 70);
        this.add(closeButton);
        spartaButton.setVisible(true);
        cairoButton.setVisible(true);
        romeButton.setVisible(true);
        spartaButton.setEnabled(false);
        cairoButton.setEnabled(false);
        romeButton.setEnabled(false);
        ImageIcon cairoLogo = new ImageIcon("resources/images/CairoEdited.jpg");
        cairoButton.setIcon(cairoLogo);
        ImageIcon romeLogo = new ImageIcon("resources/images/RomeEditedlk.jpg");
        romeButton.setIcon(romeLogo);
        ImageIcon spartaLogo = new ImageIcon("resources/images/Spartan.jpeg");
        spartaButton.setIcon(spartaLogo);
        romeButton.setBounds(20, 150, 300, 300);
        cairoButton.setBounds(340, 150, 300, 300);
        spartaButton.setBounds(660, 150, 300, 300);
        this.add(spartaButton);
        this.add(cairoButton);
        this.add(romeButton);
        this.cairoFlag = cairoFlag;
        this.spartaFlag = spartaFlag;
        this.romeFlag = romeFlag;
        updateButtons();
    }

    public void initializeDefendingLabel() {
        defendingLabel.setBounds(300, -100, 500, 300);
        defendingLabel.setFont(new Font("NEW ROMAN", Font.BOLD, 30));
        defendingLabel.setForeground(Color.BLACK);
        this.add(defendingLabel);
    }

    public void updateButtons() {
        if (cairoFlag)
            cairoButton.setEnabled(true);
        if (spartaFlag)
            spartaButton.setEnabled(true);
        if (romeFlag)
            romeButton.setEnabled(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == closeButton)
            this.dispose();
    }

    public CustomButton getSpartaButton() {
        return spartaButton;
    }

    public CustomButton getCairoButton() {
        return cairoButton;
    }

    public CustomButton getRomeButton() {
        return romeButton;
    }
}
