package buildings;

import exceptions.BuildingInCoolDownException;
import exceptions.MaxLevelException;

public class Farm extends EconomicBuilding {

    public Farm() {
        super(1000, 500);
    }

    public void upgrade() throws BuildingInCoolDownException, MaxLevelException {
        super.upgrade();
        if (super.getLevel() == 1) {
            super.setUpgradeCost(700);
            super.setLevel(2);
        } else if (super.getLevel() == 2)
            super.setLevel(3);
    }

    @Override
    public int harvest() {
        int totalFood = 0;
        switch (super.getLevel()) {
            case 1:
                totalFood = 500;
                break;
            case 2:
                totalFood = 700;
                break;
            case 3:
                totalFood = 1000;
                break;
        }
        return totalFood;
    }
}
