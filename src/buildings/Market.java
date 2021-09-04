package buildings;

import exceptions.BuildingInCoolDownException;
import exceptions.MaxLevelException;

public class Market extends EconomicBuilding {

    public Market() {
        super(1500, 700);
    }


    public void upgrade() throws BuildingInCoolDownException, MaxLevelException {
        super.upgrade();
        if (super.getLevel() == 1) {
            super.setUpgradeCost(1000);
            super.setLevel(2);
        } else if (super.getLevel() == 2)
            super.setLevel(3);
    }

    @Override
    public int harvest() {
        int totalGold = 0;
        switch (super.getLevel()) {
            case 1:
                totalGold = 1000;
                break;
            case 2:
                totalGold = 1500;
                break;
            case 3:
                totalGold = 2000;
                break;
        }
        return totalGold;
    }
}
