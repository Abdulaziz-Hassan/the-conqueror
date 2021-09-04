package units;

import exceptions.FriendlyFireException;

public class Cavalry extends Unit {

    public Cavalry(int level, int maxSoldierCount, double idleUpkeep, double marchingUpkeep, double siegeUpkeep) {
        super(level, maxSoldierCount, idleUpkeep, marchingUpkeep, siegeUpkeep);
    }


    @Override
    public void attack(Unit target) throws FriendlyFireException {
        super.attack(target);
        if (target instanceof Archer) {
            switch (super.getLevel()) {
                case 1:
                    target.setCurrentSoldierCount((int) (target.getCurrentSoldierCount() - (this.getCurrentSoldierCount() * 0.5)));
                    break;
                case 2:
                    target.setCurrentSoldierCount((int) (target.getCurrentSoldierCount() - (this.getCurrentSoldierCount() * 0.6)));
                    break;
                case 3:
                    target.setCurrentSoldierCount((int) (target.getCurrentSoldierCount() - (this.getCurrentSoldierCount() * 0.7)));
                    break;
            }
        } else if (target instanceof Infantry) {
            switch (super.getLevel()) {
                case 1:
                    target.setCurrentSoldierCount((int) (target.getCurrentSoldierCount() - (this.getCurrentSoldierCount() * 0.3)));
                    break;
                case 2:
                    target.setCurrentSoldierCount((int) (target.getCurrentSoldierCount() - (this.getCurrentSoldierCount() * 0.4)));
                    break;
                case 3:
                    target.setCurrentSoldierCount((int) (target.getCurrentSoldierCount() - (this.getCurrentSoldierCount() * 0.5)));
                    break;
            }
        } else if (target instanceof Cavalry) {
            switch (super.getLevel()) {
                case 1:
                case 2:
                    target.setCurrentSoldierCount((int) (target.getCurrentSoldierCount() - (this.getCurrentSoldierCount() * 0.2)));
                    break;
                case 3:
                    target.setCurrentSoldierCount((int) (target.getCurrentSoldierCount() - (this.getCurrentSoldierCount() * 0.3)));
                    break;
            }

        }
        if (target.getCurrentSoldierCount() <= 0) {
            target.setCurrentSoldierCount(0);
            if (target.getParentArmy() != null)
                target.getParentArmy().handleAttackedUnit(target);
        }
    }
}
