package view.labels;

import units.Status;

import javax.swing.*;
import java.awt.*;

public class UnitInfoLabel extends JLabel {

    private final Status status;
    private final int level;
    private final String type;
    private final double soldierCount;
    private final int maxSoldierCount;
    private final JLabel statusLabel;
    private final JLabel soldierCountLabel;

    public UnitInfoLabel(Status status,int level,String type,double soldierCount,int maxSoldierCount){
      this.status = status;
      this.level = level;
      this.type = type;
      this.soldierCount = soldierCount;
      this.maxSoldierCount = maxSoldierCount;
      this.statusLabel = new JLabel();
      this.soldierCountLabel = new JLabel();
      this.setVisible(true);
      this.setPreferredSize(new Dimension(125,135));
      this.setBackground(Color.WHITE);
      this.setOpaque(true);
      this.setEnabled(false);
      initialize();
  }

  public void initialize(){
      JLabel typeLabel = new JLabel();
      typeLabel.setBounds(0,0,120,20);
      typeLabel.setText("Unit Type : "+type);
      typeLabel.setVisible(true);
      this.add(typeLabel);

      JLabel levelLabel = new JLabel();
      levelLabel.setBounds(0,22,120,20);
      levelLabel.setText("Unit level : "+level);
      levelLabel.setVisible(true);
      this.add(levelLabel);

      statusLabel.setBounds(0,44,120,20);
      statusLabel.setText("Statues : "+status);
      statusLabel.setVisible(true);
      this.add(statusLabel);

      soldierCountLabel.setBounds(0,66,120,20);
      soldierCountLabel.setText("Health :"+soldierCount);
      soldierCountLabel.setVisible(true);
      this.add(soldierCountLabel);

      JLabel maxSoldierCountLabel = new JLabel();
      maxSoldierCountLabel.setBounds(0,88,120,20);
      maxSoldierCountLabel.setText("Max Health: "+maxSoldierCount);
      maxSoldierCountLabel.setVisible(true);
      this.add(maxSoldierCountLabel);
  }

    public Status getStatus() {
        return status;
    }

    public int getLevel() {
        return level;
    }

    public int getMaxSoldierCount() {
        return maxSoldierCount;
    }

    public JLabel getStatusLabel() {
        return statusLabel;
    }

    public JLabel getSoldierCountLabel() {
        return soldierCountLabel;
    }
}
