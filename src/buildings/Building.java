package buildings;

import exceptions.BuildingInCoolDownException;
import exceptions.MaxLevelException;

public abstract class Building {
    private final int cost;
    private int level;
    private int upgradeCost;
    private boolean coolDown;

    public Building(int cost, int upgradeCost) {
        this.cost = cost;
        this.level = 1;
        this.upgradeCost = upgradeCost;
        this.coolDown = true;
    }

    public void upgrade() throws BuildingInCoolDownException, MaxLevelException {
        if (this.coolDown)
            throw new BuildingInCoolDownException("Building is cooling down, please wait for next turn");
        if (this.level >= 3)
            throw new MaxLevelException("You have reached the maximum level");
        this.coolDown = true;
    }

    public int getCost() {
        return cost;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getUpgradeCost() {
        return upgradeCost;
    }

    public void setUpgradeCost(int upgradeCost) {
        this.upgradeCost = upgradeCost;
    }

    public boolean isCoolDown() {
        return coolDown;
    }

    public void setCoolDown(boolean coolDown) {
        this.coolDown = coolDown;
    }
}
