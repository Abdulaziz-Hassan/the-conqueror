package buildings;

import exceptions.BuildingInCoolDownException;
import exceptions.MaxLevelException;
import exceptions.MaxRecruitedException;
import units.Infantry;
import units.Unit;

public class Barracks extends MilitaryBuilding {

    public Barracks() {
        super(2000, 1000, 500);
    }

    public void upgrade() throws BuildingInCoolDownException, MaxLevelException {
        super.upgrade();
        if (super.getLevel() == 1) {
            super.setLevel(2);
            super.setUpgradeCost(1500);
            super.setRecruitmentCost(550);
        } else if (super.getLevel() == 2) {
            super.setLevel(3);
            super.setRecruitmentCost(600);
        }
    }

    @Override
    public Unit recruit() throws BuildingInCoolDownException, MaxRecruitedException {
        if (super.isCoolDown())
            throw new BuildingInCoolDownException("Building is cooling down, please wait for next turn");

        if (super.getCurrentRecruit() >= super.getMaxRecruit())
            throw new MaxRecruitedException("You have reached the maximum number of recruits , please wait for next turn");

        Unit unit = null;
        switch (super.getLevel()) {
            case 1:
            case 2:
                unit = new Infantry(super.getLevel(), 50, 0.5, 0.6, 0.7);
                break;
            case 3:
                unit = new Infantry(3, 60, 0.6, 0.7, 0.8);
                break;
        }
        super.setCurrentRecruit(super.getCurrentRecruit() + 1);
        return unit;
    }
}
