package engine;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import buildings.EconomicBuilding;
import buildings.Farm;
import buildings.Market;
import buildings.MilitaryBuilding;
import exceptions.FriendlyFireException;
import units.*;
import view.buttons.UnitButton;

public class Game {
    private Player player;
    private final ArrayList<City> availableCities;
    private final ArrayList<Distance> distances;
    private static final int MAX_TURN_COUNT = 50;
    private int currentTurnCount;
    private final HashMap<String,ArrayList<UnitButton>> enemyDefendingArmies;
    private boolean isVictorious;
    private boolean whoWon;

    public Game(String playerName, String playerCity) throws IOException {
        this.player = new Player(playerName);
        this.availableCities = new ArrayList<>();
        this.distances = new ArrayList<>();
        this.currentTurnCount = 1;
        this.enemyDefendingArmies = new HashMap<>();
        loadCitiesAndDistances();
        for (City city : this.availableCities) {
            if (city.getName().equalsIgnoreCase(playerCity))
                player.getControlledCities().add(city);
            else
                loadArmy(city.getName(), "resources/miscellaneous/" + city.getName().toLowerCase() + "_army.csv");
        }
    }

    private void loadCitiesAndDistances() throws IOException {
        BufferedReader br = new BufferedReader(new FileReader("resources/miscellaneous/distances.csv"));
        String currentLine = br.readLine();
        ArrayList<String> names = new ArrayList<>();
        while (currentLine != null) {
            String[] content = currentLine.split(",");
            if (!names.contains(content[0])) {
                availableCities.add(new City(content[0]));
                names.add(content[0]);
            } else if (!names.contains(content[1])) {
                availableCities.add(new City(content[1]));
                names.add(content[1]);
            }
            distances.add(new Distance(content[0], content[1], Integer.parseInt(content[2])));
            currentLine = br.readLine();
        }
        br.close();
    }

    private void loadArmy(String cityName, String path) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(path));
        ArrayList<UnitButton>unitButtons=new ArrayList<>();
        String currentLine = br.readLine();
        Army resultArmy = new Army(cityName);
        while (currentLine != null) {
            String[] content = currentLine.split(",");
            String unitType = content[0].toLowerCase();
            int unitLevel = Integer.parseInt(content[1]);
            Unit unit = null;
            if (unitType.equalsIgnoreCase("archer")) {
                if (unitLevel == 1)
                    unit = (new Archer(1, 60, 0.4, 0.5, 0.6));
                else if (unitLevel == 2)
                    unit = (new Archer(2, 60, 0.4, 0.5, 0.6));
                else
                    unit = (new Archer(3, 70, 0.5, 0.6, 0.7));
            } else if (unitType.equalsIgnoreCase("infantry")) {
                if (unitLevel == 1)
                    unit = (new Infantry(1, 50, 0.5, 0.6, 0.7));
                else if (unitLevel == 2)
                    unit = (new Infantry(2, 50, 0.5, 0.6, 0.7));
                else
                    unit = (new Infantry(3, 60, 0.6, 0.7, 0.8));
            } else if (unitType.equalsIgnoreCase("cavalry")) {
                if (unitLevel == 1)
                    unit = (new Cavalry(1, 40, 0.6, 0.7, 0.75));
                else if (unitLevel == 2)
                    unit = (new Cavalry(2, 40, 0.6, 0.7, 0.75));
                else
                    unit = (new Cavalry(3, 60, 0.7, 0.8, 0.9));
            }
            if (unit != null) {
                unit.setParentArmy(resultArmy);
                resultArmy.getUnits().add(unit);
                unitButtons.add(new UnitButton(unit.getParentArmy().getCurrentStatus(),unit.getLevel(),unitType,unit.getCurrentSoldierCount(),unit.getMaxSoldierCount(),unit));
            }
            currentLine = br.readLine();
        }
        br.close();
        for (City city : this.availableCities)
            if (city.getName().equalsIgnoreCase(cityName))
                city.setDefendingArmy(resultArmy);
        enemyDefendingArmies.put(cityName.toLowerCase(),unitButtons);
    }

    public void targetCity(Army army, String targetName) {
        if (army == null)
            return;
        if (!army.getCurrentStatus().equals(Status.MARCHING) || !army.getCurrentLocation().equalsIgnoreCase("onRoad")) {
            army.setTarget(targetName);
            for (Distance distance : this.getDistances()) {
                if ((distance.getFrom().equalsIgnoreCase(army.getCurrentLocation()) && distance.getTo().equalsIgnoreCase(targetName)) ||
                        (distance.getFrom().equalsIgnoreCase(targetName) && distance.getTo().equalsIgnoreCase(army.getCurrentLocation()))) {
                    army.setDistanceToTarget(distance.getDistance());
                    break;
                }
            }
        }
    }

    public void endTurn() {
        currentTurnCount++;
        double totalUpkeep = 0;
        for (City city : player.getControlledCities()) {
            for (MilitaryBuilding militaryBuilding : city.getMilitaryBuildings()) {
                militaryBuilding.setCoolDown(false);
                militaryBuilding.setCurrentRecruit(0);
            }
            for (EconomicBuilding economicBuilding : city.getEconomicalBuildings()) {
                economicBuilding.setCoolDown(false);
                if (economicBuilding instanceof Market)
                    player.setTreasury(player.getTreasury() + economicBuilding.harvest());
                else if (economicBuilding instanceof Farm)
                    player.setFood(player.getFood() + economicBuilding.harvest());
            }
            totalUpkeep += city.getDefendingArmy().foodNeeded();
        }

        for (Army army : player.getControlledArmies()) {
            if (!army.getTarget().equals("") && army.getCurrentStatus().equals(Status.IDLE)) {
                army.setCurrentStatus(Status.MARCHING);
                army.setCurrentLocation("onRoad");
            }
            if(army.getDistanceToTarget() > 0 && !army.getTarget().equals(""))
                army.setDistanceToTarget(army.getDistanceToTarget() - 1);
            if (army.getDistanceToTarget() == 0) {
                army.setCurrentLocation(army.getTarget());
                army.setTarget("");
                army.setCurrentStatus(Status.IDLE);
            }
            totalUpkeep +=  army.foodNeeded();
        }
        if (totalUpkeep <= player.getFood())
            player.setFood(player.getFood() - totalUpkeep);
        else {
            player.setFood(0);
            for (Army army : player.getControlledArmies())
                for (Unit unit : army.getUnits())
                    unit.setCurrentSoldierCount(unit.getCurrentSoldierCount() - (int) (unit.getCurrentSoldierCount() * 0.1));
        }

        for (City city : this.availableCities) {
            if (city.isUnderSiege()) {
                if(city.getTurnsUnderSiege() < 3)
                    city.setTurnsUnderSiege(city.getTurnsUnderSiege() + 1);
                else{
                    city.setUnderSiege(false);
                    return;
                }
                for (Unit unit : city.getDefendingArmy().getUnits())
                    unit.setCurrentSoldierCount(unit.getCurrentSoldierCount() - (int) (unit.getCurrentSoldierCount() * 0.1));
            }
        }
    }

    public void occupy(Army army,String cityName){
        if(army == null)
            return;
       for(City city : this.getAvailableCities()) {
           if (city != null) {
               if (city.getName().equalsIgnoreCase(cityName)) {
                   city.setUnderSiege(false);
                   city.setTurnsUnderSiege(-1);
                   if (!this.getPlayer().getControlledCities().contains(city)) {
                       this.getPlayer().getControlledCities().add(city);
                   }
                   city.setDefendingArmy(army);
                   this.getPlayer().getControlledArmies().remove(army);
                   army.setCurrentStatus(Status.IDLE);
                   break;
               }
           }
       }
    }

    public void autoResolve(Army attacker, Army defender) throws FriendlyFireException {
        if(this.getPlayer().getControlledArmies().contains(attacker) && this.getPlayer().getControlledArmies().contains(defender))
            throw new FriendlyFireException("You can't attack a Friendly Army");
        boolean attackTurn;
        while (!attacker.getUnits().isEmpty() && !defender.getUnits().isEmpty()){
            int randomAttackerIndex = (int) (Math.random() * attacker.getUnits().size());
            while(attacker.getUnits().get(randomAttackerIndex) == null){
                attacker.getUnits().remove(randomAttackerIndex);
                randomAttackerIndex = (int) (Math.random() * attacker.getUnits().size());
            }
            int randomDefenderIndex = (int) (Math.random() * defender.getUnits().size());
            while(defender.getUnits().get(randomDefenderIndex) == null){
                defender.getUnits().remove(randomDefenderIndex);
                randomDefenderIndex = (int) (Math.random() * defender.getUnits().size());
            }
            Unit attackerUnit = attacker.getUnits().get(randomAttackerIndex);
            Unit defenderUnit = defender.getUnits().get(randomDefenderIndex);
            attackTurn = true;
            while (attackerUnit.getCurrentSoldierCount() != 0 && defenderUnit.getCurrentSoldierCount() != 0){
                if(attackTurn)
                    attackerUnit.attack(defenderUnit);
                else
                    defenderUnit.attack(attackerUnit);

                attackTurn=!attackTurn;
            }
            if (attackerUnit.getCurrentSoldierCount() <= 0)
                attacker.getUnits().remove(attackerUnit);
            else if (defenderUnit.getCurrentSoldierCount() <= 0)
                defender.getUnits().remove(defenderUnit);
        }

        if(!attacker.getUnits().isEmpty() && defender.getUnits().isEmpty()){
            occupy(attacker,defender.getCurrentLocation());
            whoWon = true;
        }
        else {
            this.getPlayer().getControlledArmies().remove(attacker);
            City correspondingCity = getCorrespondingAvailableCity(defender.getCurrentLocation());
            if (correspondingCity != null) {
                correspondingCity.setUnderSiege(false);
                correspondingCity.setTurnsUnderSiege(-1);
            }
            whoWon = false;
        }
    }

    public boolean isGameOver() {
        this.isVictorious = this.getPlayer().getControlledCities().size() == this.getAvailableCities().size();
        return this.getPlayer().getControlledCities().size() == this.getAvailableCities().size() || getCurrentTurnCount() > getMaxTurnCount();
    }

    public City getCorrespondingAvailableCity(String target) {
        for (City city : this.getAvailableCities())
            if (city != null && city.getName().equalsIgnoreCase(target))
                return city;
        return null;
    }

    public ArrayList<City> getAvailableCities() {
        return availableCities;
    }

    public ArrayList<Distance> getDistances() {
        return distances;
    }

    public int getCurrentTurnCount() {
        return currentTurnCount;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public int getMaxTurnCount() {
        return MAX_TURN_COUNT;
    }

    public void setCurrentTurnCount(int currentTurnCount) {
        this.currentTurnCount = currentTurnCount;
    }

    public HashMap<String, ArrayList<UnitButton>> getEnemyDefendingArmies() {
        return enemyDefendingArmies;
    }

    public boolean isVictorious() {
        return isVictorious;
    }

    public boolean isWhoWon() {
        return whoWon;
    }
}