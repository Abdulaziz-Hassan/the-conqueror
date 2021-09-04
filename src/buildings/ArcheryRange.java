package buildings;

import exceptions.BuildingInCoolDownException;
import exceptions.MaxLevelException;
import exceptions.MaxRecruitedException;
import units.Archer;
import units.Unit;

public class ArcheryRange extends MilitaryBuilding {

    public ArcheryRange() {
        super(1500, 800, 400);
    }

    public void upgrade() throws BuildingInCoolDownException, MaxLevelException {
        super.upgrade();
        if (super.getLevel() == 1) {
            super.setLevel(2);
            super.setUpgradeCost(700);
            super.setRecruitmentCost(450);
        } else if (super.getLevel() == 2) {
            super.setLevel(3);
            super.setRecruitmentCost(500);
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
                unit = new Archer(super.getLevel(), 60, 0.4, 0.5, 0.6);
                break;
            case 3:
                unit = new Archer(3, 70, 0.5, 0.6, 0.7);
                break;
        }
        super.setCurrentRecruit(super.getCurrentRecruit() + 1);
        return unit;
    }
}
