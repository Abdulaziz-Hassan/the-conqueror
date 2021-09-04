package buildings;

import exceptions.BuildingInCoolDownException;
import exceptions.MaxLevelException;
import exceptions.MaxRecruitedException;
import units.Cavalry;
import units.Unit;

public class Stable extends MilitaryBuilding {

    public Stable() {
        super(2500, 1500, 600);
    }

    public void upgrade() throws BuildingInCoolDownException, MaxLevelException {
        super.upgrade();
        if (super.getLevel() == 1) {
            super.setLevel(2);
            super.setUpgradeCost(2000);
            super.setRecruitmentCost(650);
        } else if (super.getLevel() == 2) {
            super.setLevel(3);
            super.setRecruitmentCost(700);
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
                unit = new Cavalry(super.getLevel(), 40, 0.6, 0.7, 0.75);
                break;
            case 3:
                unit = new Cavalry(3, 60, 0.7, 0.8, 0.9);
                break;
        }
        super.setCurrentRecruit(super.getCurrentRecruit() + 1);
        return unit;
    }
}
