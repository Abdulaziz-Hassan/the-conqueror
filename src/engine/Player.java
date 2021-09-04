package engine;

import buildings.*;
import exceptions.*;
import units.Army;
import units.Status;
import units.Unit;

import java.util.ArrayList;

public class Player {

    private final String name;
    private final ArrayList<City> controlledCities;
    private final ArrayList<Army> controlledArmies;
    private double treasury;
    private double food;

    public Player(String name) {
        this.name = name;
        this.controlledCities = new ArrayList<>();
        this.controlledArmies = new ArrayList<>();
        this.treasury = 5000.0;
    }

    public Unit recruitUnit(String type, String cityName) throws BuildingInCoolDownException, MaxRecruitedException, NotEnoughGoldException {
        City correspondingCity = getCorrespondingCity(cityName);
        if (correspondingCity == null)
            return null;
        MilitaryBuilding correspondingMilitaryBuilding = getCorrespondingMilitaryBuilding(type,correspondingCity);
        if (correspondingMilitaryBuilding == null)
            return null;
        if(correspondingMilitaryBuilding.getRecruitmentCost() > this.treasury)
            throw new NotEnoughGoldException("Not enough gold to recruit");

        Unit recruitedUnit = correspondingMilitaryBuilding.recruit();
        correspondingCity.getDefendingArmy().getUnits().add(recruitedUnit);
        recruitedUnit.setParentArmy(correspondingCity.getDefendingArmy());
        this.setTreasury(this.treasury - correspondingMilitaryBuilding.getRecruitmentCost());
        return recruitedUnit;
    }

    public void build(String type, String cityName) throws NotEnoughGoldException {
        City correspondingCity = getCorrespondingCity(cityName);
        if (correspondingCity == null)
            return;
        Building doesExist = getCorrespondingBuilding(correspondingCity, type);
        if (doesExist == null) {
            Building building = createBuilding(type);
            if (building == null)
                return;
            if (building.getCost() > this.treasury)
                throw new NotEnoughGoldException("Not enough Gold to build");
            if (building instanceof EconomicBuilding)
                correspondingCity.getEconomicalBuildings().add((EconomicBuilding) building);
            if (building instanceof MilitaryBuilding)
                correspondingCity.getMilitaryBuildings().add((MilitaryBuilding) building);
            this.setTreasury(this.treasury - building.getCost());
            building.setCoolDown(true);
        }
    }

    public void upgradeBuilding(Building building) throws NotEnoughGoldException, BuildingInCoolDownException, MaxLevelException {
        if(building==null)
            return;
        if (building.getUpgradeCost() > this.treasury)
            throw new NotEnoughGoldException("Not enough Gold to upgrade this building");
        int initialUpgradeCost = building.getUpgradeCost();
        building.upgrade();
        this.treasury -= initialUpgradeCost;
    }

    public Army initiateArmy(City city, Unit unit)  {
        if(unit == null || city == null)
            return null;
        Army newArmy = new Army(city.getName());
        city.getDefendingArmy().getUnits().remove(unit);
        newArmy.getUnits().add(unit);
        unit.setParentArmy(newArmy);
        this.getControlledArmies().add(newArmy);
        return newArmy;
    }

    public void laySiege(Army army, City city) throws TargetNotReachedException, FriendlyCityException {
        if (army == null || city == null)
            return;
        if (this.getControlledCities().contains(city))
            throw new FriendlyCityException("You can't lay siege on a friendly city");
        if (!army.getCurrentLocation().equalsIgnoreCase(city.getName()))
            throw new TargetNotReachedException("Target not reached");
        army.setCurrentStatus(Status.BESIEGING);
        city.setUnderSiege(true);
        city.setTurnsUnderSiege(0);
    }

    public City getCorrespondingCity(String cityName) {
        for (City city : this.getControlledCities())
            if (city != null && city.getName().equalsIgnoreCase(cityName))
                return city;
        return null;
    }

    public MilitaryBuilding getCorrespondingMilitaryBuilding(String type, City city) {
        for (MilitaryBuilding militaryBuilding : city.getMilitaryBuildings()) {
            if (militaryBuilding instanceof ArcheryRange && (type.equalsIgnoreCase("archer") || type.equalsIgnoreCase("archeryrange")))
                return militaryBuilding;
            else if (militaryBuilding instanceof Barracks && (type.equalsIgnoreCase("infantry") || type.equalsIgnoreCase("barracks")))
                return militaryBuilding;
            else if (militaryBuilding instanceof Stable && (type.equalsIgnoreCase("cavalry") || type.equalsIgnoreCase("stable")))
                return militaryBuilding;
        }
        return null;
    }

    public Building getCorrespondingBuilding(City city, String type) {
        MilitaryBuilding militaryBuilding = getCorrespondingMilitaryBuilding(type,city);
        if (militaryBuilding != null)
            return militaryBuilding;
        return getCorrespondingEconomicBuilding(city, type);
    }

    public EconomicBuilding getCorrespondingEconomicBuilding(City city, String type) {
        for (EconomicBuilding economicBuilding : city.getEconomicalBuildings()) {
            if (economicBuilding instanceof Farm && type.equalsIgnoreCase("farm"))
                return economicBuilding;
            else if (economicBuilding instanceof Market && type.equalsIgnoreCase("market"))
                return economicBuilding;
        }
        return null;
    }

    public Building createBuilding(String type) {
        Building building = null;
        switch (type.toLowerCase()) {
            case "farm":
                building = new Farm();
                break;
            case "market":
                building = new Market();
                break;
            case "archeryrange":
                building = new ArcheryRange();
                break;
            case "barracks":
                building = new Barracks();
                break;
            case "stable":
                building = new Stable();
                break;
        }
        return building;
    }

    public String getName() {
        return name;
    }

    public ArrayList<City> getControlledCities() {
        return controlledCities;
    }

    public ArrayList<Army> getControlledArmies() {
        return controlledArmies;
    }

    public double getTreasury() {
        return treasury;
    }

    public void setTreasury(double treasury) {
        this.treasury = treasury;
    }

    public double getFood() {
        return food;
    }

    public void setFood(double food) {
        this.food = food;
    }


}
