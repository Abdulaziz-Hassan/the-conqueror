package view.tools;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class CustomFrame extends JFrame {

    public CustomFrame(){
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setVisible(true);
        this.setBounds(-10,-5,1600,1000);
        ImageIcon image =new ImageIcon("resources/images/THE_Conquror_Logo.jpeg");
        this.setIconImage(image.getImage());
        this.setTitle("THE CONQUEROR");
        this.setResizable(true);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent we)
            {
                String[] ObjButtons = {"Yes","No"};
                int PromptResult = JOptionPane.showOptionDialog(null,"Are you sure you want to exit?","Exit",JOptionPane.DEFAULT_OPTION,JOptionPane.WARNING_MESSAGE,null,ObjButtons,ObjButtons[1]);
                if(PromptResult==JOptionPane.YES_OPTION)
                {
                    System.exit(0);
                }
                else
                    setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
            }
        });
    }

    public void reload() {
        this.revalidate();
        this.repaint();
    }
}
