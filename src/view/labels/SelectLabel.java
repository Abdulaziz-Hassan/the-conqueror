package view.labels;

import javax.swing.*;
import java.awt.*;

public class SelectLabel extends JLabel {

   public SelectLabel(){
       this.setForeground(new Color(59, 215, 24));
       this.setText("Selected ✔");
       this.setFont(new Font("New Roman",Font.BOLD,35));
        this.setOpaque(false);
       this.setBounds(20,50,200,200);
       this.setVisible(true);
   }
}
