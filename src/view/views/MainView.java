package view.views;

import view.buttons.CustomButton;
import view.buttons.CustomToggleButton;
import view.labels.SelectLabel;
import view.tools.CustomFrame;
import view.labels.CustomLabel;
import view.tools.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainView extends CustomFrame implements ActionListener {

    private TextField nameTextField;
    private String playerName;
    private String cityName;
    private final CustomLabel mainLabel;
    private final JPanel mainPanel;
    private JRadioButton spartaRadioButton;
    private JRadioButton cairoRadioButton;
    private JRadioButton romeRadioButton;
    private final SelectLabel selectLabel;
    private CustomToggleButton kingToggleButton;
    private CustomToggleButton queenToggleButton;
    private CustomButton continueButton;
    private boolean isMale;
    private WorldMapView worldMapView;

    public MainView() {
        ImageIcon image = new ImageIcon("resources/images/THE_Conquror_Logo.jpeg");
        this.setIconImage(image.getImage());
        this.mainLabel = new CustomLabel();
        this.mainLabel.setHorizontalAlignment(JLabel.CENTER);
        this.mainPanel = new JPanel();
        this.selectLabel = new SelectLabel();
        this.mainPanel.setBounds(320, 380, 1100, 500);
        this.mainPanel.setVisible(true);
        this.mainPanel.setLayout(null);
        this.mainPanel.setOpaque(false);
        this.add(mainLabel);
        this.mainLabel.setIcon(image);
        this.initializeTextField();
        this.initializeCharacter();
        this.initializeBackground();
        this.mainLabel.add(mainPanel);
        this.initializeRadioButtons();
        this.initializeContinueButton();
        this.reload();
    }

    public void initializeContinueButton() {
        continueButton = new CustomButton();
        continueButton.setText("Start Game");
        continueButton.setFont(new Font("NEW ROMAN", Font.BOLD, 35));
        continueButton.setBackground(Color.BLACK);
        continueButton.setBounds(350, 320, 250, 40);
        mainPanel.add(continueButton);
    }

    public void initializeRadioButtons() {
        cairoRadioButton = new JRadioButton("Cairo");
        cairoRadioButton.setPreferredSize(new Dimension(100, 40));
        cairoRadioButton.setFont(new Font("NEW ROMAN", Font.BOLD, 20));
        cairoRadioButton.addActionListener(this);
        romeRadioButton = new JRadioButton("Rome");
        romeRadioButton.addActionListener(this);
        romeRadioButton.setPreferredSize(new Dimension(100, 40));
        romeRadioButton.setFont(new Font("NEW ROMAN", Font.BOLD, 20));
        spartaRadioButton = new JRadioButton("Sparta");
        spartaRadioButton.addActionListener(this);
        spartaRadioButton.setPreferredSize(new Dimension(100, 40));
        spartaRadioButton.setFont(new Font("NEW ROMAN", Font.BOLD, 20));
        ButtonGroup group = new ButtonGroup();
        group.add(cairoRadioButton);
        group.add(romeRadioButton);
        group.add(spartaRadioButton);
        JPanel tempPanel = new JPanel();
        tempPanel.setBounds(700, 0, 350, 40);
        tempPanel.setVisible(true);
        tempPanel.setOpaque(false);
        tempPanel.add(cairoRadioButton);
        tempPanel.add(romeRadioButton);
        tempPanel.add(spartaRadioButton);
        mainPanel.add(tempPanel);
    }

    public void initializeBackground() {
        CustomLabel tempLabel = new CustomLabel();
        tempLabel.setText("The Conqueror");
        tempLabel.setForeground(new Color(0xA9FDFBFB, true)); // to color the Text
        tempLabel.setFont(new Font("New Roman", Font.BOLD, 130));
        tempLabel.setOpaque(false);
        tempLabel.setVerticalTextPosition(JLabel.CENTER);
        tempLabel.setBounds(300, 150, 1300, 200);
        mainLabel.add(tempLabel);
    }

    public void initializeCharacter() {
        kingToggleButton = new CustomToggleButton();
        kingToggleButton.setVisible(true);
        kingToggleButton.addActionListener(this);
        ImageIcon image2 = new ImageIcon("resources/images/Kingedited.jpg");
        kingToggleButton.setIcon(image2);
        kingToggleButton.setBounds(70, 100, 300, 200);//imp
        kingToggleButton.setVerticalAlignment(JButton.CENTER);
        kingToggleButton.setEnabled(true);
        kingToggleButton.setLayout(null);
        queenToggleButton = new CustomToggleButton();
        queenToggleButton.addActionListener(this);
        queenToggleButton.setVisible(true);
        ImageIcon image3 = new ImageIcon("resources/images/QueenEdited.jpg");
        queenToggleButton.setIcon(image3);
        queenToggleButton.setBounds(470, 100, 300, 200);//imp
        queenToggleButton.setVerticalAlignment(JButton.CENTER);
        queenToggleButton.setHorizontalAlignment(JButton.CENTER);
        queenToggleButton.setLayout(null);
        mainPanel.add(kingToggleButton);
        mainPanel.add(queenToggleButton);
    }

    public void initializeTextField() {
        nameTextField = new TextField();
        nameTextField.setVisible(true);
        nameTextField.setText("Please Enter Your Name");
        nameTextField.setFont(new Font("New Roman", Font.BOLD, 30));
        CustomTextArea playerName = new CustomTextArea();
        playerName.setEditable(false);
        playerName.setVisible(true);
        playerName.setText("Player Name");
        playerName.setEditable(false);
        playerName.setForeground(Color.WHITE);
        playerName.setBorder(null);
        playerName.setBounds(0, 0, 190, 40);
        nameTextField.setBounds(200, 0, 500, 40);
        mainPanel.add(playerName);
        mainPanel.add(nameTextField);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == kingToggleButton) {
            kingToggleButton.add(selectLabel);
            queenToggleButton.remove(selectLabel);
            this.reload();
        } else if (e.getSource() == queenToggleButton) {
            queenToggleButton.add(selectLabel);
            kingToggleButton.remove(selectLabel);
            this.reload();

        }
    }

    public CustomButton getContinueButton() {
        return continueButton;
    }

    public WorldMapView getWorldMapView() {
        return worldMapView;
    }

    public String getPlayerName() {
        return playerName;
    }

    public String getCityName() {
        return cityName;
    }

    public TextField getNameTextField() {
        return nameTextField;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public JRadioButton getSpartaRadioButton() {
        return spartaRadioButton;
    }

    public JRadioButton getCairoRadioButton() {
        return cairoRadioButton;
    }

    public JRadioButton getRomeRadioButton() {
        return romeRadioButton;
    }

    public CustomToggleButton getKingToggleButton() {
        return kingToggleButton;
    }

    public CustomToggleButton getQueenToggleButton() {
        return queenToggleButton;
    }
    public boolean isMale() {
        return isMale;
    }

    public void setMale(boolean male) {
        isMale = male;
    }

    public void setWorldMapView(WorldMapView worldMapView) {
        this.worldMapView = worldMapView;
    }
}
