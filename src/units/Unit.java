package units;

import exceptions.FriendlyFireException;

public abstract class Unit {

    private final int level;
    private final int maxSoldierCount;
    private int currentSoldierCount;
    private final double idleUpkeep;
    private final double marchingUpkeep;
    private final double siegeUpkeep;
    private Army parentArmy;

    public Unit(int level, int maxSoldierCount, double idleUpkeep, double marchingUpkeep, double siegeUpkeep) {
        this.level = level;
        this.maxSoldierCount = maxSoldierCount;
        this.currentSoldierCount = maxSoldierCount;
        this.idleUpkeep = idleUpkeep;
        this.marchingUpkeep = marchingUpkeep;
        this.siegeUpkeep = siegeUpkeep;
    }

    public void attack(Unit target) throws FriendlyFireException {
    }

    public int getLevel() {
        return level;
    }

    public int getMaxSoldierCount() {
        return maxSoldierCount;
    }

    public int getCurrentSoldierCount() {
        return currentSoldierCount;
    }

    public void setCurrentSoldierCount(int currentSoldierCount) {
        this.currentSoldierCount = currentSoldierCount;
        if (this.currentSoldierCount <= 0)
            this.currentSoldierCount = 0;
    }

    public double getIdleUpkeep() {
        return idleUpkeep;
    }

    public double getMarchingUpkeep() {
        return marchingUpkeep;
    }

    public double getSiegeUpkeep() {
        return siegeUpkeep;
    }

    public Army getParentArmy() {
        return parentArmy;
    }

    public void setParentArmy(Army parentArmy) {
        this.parentArmy = parentArmy;
    }
}
