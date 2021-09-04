package units;

import exceptions.MaxCapacityException;

import java.util.ArrayList;

public class Army {

    private Status currentStatus;
    private ArrayList<Unit> units;
    private int distanceToTarget;
    private String target;
    private String currentLocation;
    private static final int MAX_TO_HOLD = 10;

    public Army(String currentLocation) {
        this.currentStatus = Status.IDLE;
        this.units = new ArrayList<>();
        this.distanceToTarget = -1;
        this.currentLocation = currentLocation;
    }

    public void relocateUnit(Unit unit) throws MaxCapacityException {
        if (unit == null)
            return;
        if (this.units.size() >= MAX_TO_HOLD)
            throw new MaxCapacityException("Maximum Capacity reached");
        if (unit.getParentArmy() != null)
            unit.getParentArmy().getUnits().remove(unit);
        unit.setParentArmy(this);
        this.units.add(unit);
    }

    public void handleAttackedUnit(Unit unit) {
        if (unit == null)
            return;
        if (unit.getCurrentSoldierCount() <= 0) {
            unit.setCurrentSoldierCount(0);
           this.getUnits().remove(unit);
           unit.setParentArmy(null);
        }
    }

    public double foodNeeded() {
        double totalNeededFood = 0;
        for (Unit unit : this.units) {
            switch (this.currentStatus) {
                case IDLE:
                    totalNeededFood += (unit.getIdleUpkeep() * unit.getCurrentSoldierCount());
                    break;
                case MARCHING:
                    totalNeededFood += (unit.getMarchingUpkeep() * unit.getCurrentSoldierCount());
                    break;
                case BESIEGING:
                    totalNeededFood += (unit.getSiegeUpkeep() * unit.getCurrentSoldierCount());
                    break;
            }
        }
        return totalNeededFood;
    }

    public Status getCurrentStatus() {
        return currentStatus;
    }

    public void setCurrentStatus(Status currentStatus) {
        this.currentStatus = currentStatus;
    }

    public ArrayList<Unit> getUnits() {
        return units;
    }

    public void setUnits(ArrayList<Unit> units) {
        this.units = units;
    }

    public int getDistanceToTarget() {
        return distanceToTarget;
    }

    public void setDistanceToTarget(int distanceToTarget) {
        this.distanceToTarget = distanceToTarget;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public String getCurrentLocation() {
        return currentLocation;
    }

    public void setCurrentLocation(String currentLocation) {
        this.currentLocation = currentLocation;
    }

    public int getMaxToHold() {
        return MAX_TO_HOLD;
    }
}
