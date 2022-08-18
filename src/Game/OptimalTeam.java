package Game;

import Units.UnitType;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class OptimalTeam {
    private Object[][] allUnits;
    private int numOfAllUnit;
    private Vector<String> optimalSolution; /** Units of Optimal Solution **/
    private int numberOfUnitTypeSet; /** Optimal Solution **/
    private int numberOfUnitTypeVector;
    private double finalSumDamage; /** Optimal Solution **/
    private double optimalCost;
    private double currentCost;
    private Vector<Integer> units;
    private Vector<UnitType> unitTypes;
    private double mode;

    public OptimalTeam(double mode, double point){
        this.allUnits = new Object[20][20];
        this.optimalSolution = new Vector<>();
        this.units = new Vector<>();
        this.unitTypes = new Vector<>();
        this.mode=mode;

        String file;
        if (mode == 0) {
            file="C:\\Users\\ASUS\\Documents\\FinalDorDGamef\\AttackUnitInfo";
        }
        else {
            file="C:\\Users\\ASUS\\Documents\\FinalDorDGamef\\DefendUnitInfo";
        }
        int index = 0;
        try {
            Scanner input = new Scanner(new File(file));
            while (input.hasNextLine()) {
                String[] data =input.nextLine().split(" ");
                String name=data[0];
                double damage =Double.parseDouble(data[6]);
                double cost =Double.parseDouble(data[7]);
                Vector<UnitType> tempCanAttack=new Vector<>();
                for (int i=10;i<data.length;i++){
                    switch (data[i]) {
                        case "Tank" -> tempCanAttack.add(UnitType.Tank);
                        case "Structure" -> tempCanAttack.add(UnitType.Structure);
                        case "Solider" -> tempCanAttack.add(UnitType.Solider);
                        case "Airplane" -> tempCanAttack.add(UnitType.Airplane);
                        case "MainBase" -> tempCanAttack.add(UnitType.MainBase);
                    }
                }
                allUnits[index][0]=name;
                allUnits[index][1]=damage;
                allUnits[index][2]=cost;
                allUnits[index][3]=tempCanAttack;
                index++;
            }
            input.close();
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        numOfAllUnit = index;
    }

    public Object[][] getAllUnits() {
        return allUnits;
    }
    public void setAllUnits(Object[][] allUnits) {
        this.allUnits = allUnits;
    }

    public int getNumOfAllUnit() {
        return numOfAllUnit;
    }
    public void setNumOfAllUnit(int numOfAllUnit) {
        this.numOfAllUnit = numOfAllUnit;
    }

    public Vector<String> getOptimalSolution() {
        return optimalSolution;
    }
    public void setOptimalSolution(Vector<String> optimalSolution) {
        this.optimalSolution = optimalSolution;
    }

    public int getNumberOfUnitTypeSet() {
        return numberOfUnitTypeSet;
    }
    public void setNumberOfUnitTypeSet(int numberOfUnitTypeSet) {
        this.numberOfUnitTypeSet = numberOfUnitTypeSet;
    }

    public int getNumberOfUnitTypeVector() {
        return numberOfUnitTypeVector;
    }
    public void setNumberOfUnitTypeVector(int numberOfUnitTypeVector) {
        this.numberOfUnitTypeVector = numberOfUnitTypeVector;
    }

    public double getFinalSumDamage() {
        return finalSumDamage;
    }
    public void setFinalSumDamage(double finalSumDamage) {
        this.finalSumDamage = finalSumDamage;
    }

    public double getOptimalCost() {
        return optimalCost;
    }
    public void setOptimalCost(double optimalCost) {
        this.optimalCost = optimalCost;
    }

    public double getCurrentCost() {
        return currentCost;
    }
    public void setCurrentCost(double currentCost) {
        this.currentCost = currentCost;
    }

    public Vector<Integer> getUnits() {
        return units;
    }
    public void setUnits(Vector<Integer> units) {
        this.units = units;
    }

    public Vector<UnitType> getUnitTypes() {
        return unitTypes;
    }
    public void setUnitTypes(Vector<UnitType> unitTypes) {
        this.unitTypes = unitTypes;
    }

    public double getMode() {
        return mode;
    }
    public void setMode(double mode) {
        this.mode = mode;
    }

    public void check(){
        double sumDamage = 0.0;
        for (Integer index : units) {
            sumDamage += (double)allUnits[index][1];
        }
        if(sumDamage < finalSumDamage){
            return;
        }
        Set<UnitType> set = new HashSet<>();
        for (UnitType unitType : unitTypes) {
            set.add(unitType);
        }
        int size = set.size();

        if(sumDamage > finalSumDamage){
            finalSumDamage = sumDamage;
            optimalSolution.clear();
            for(int i=0;i<units.size();i++){
                optimalSolution.add((String) allUnits[units.get(i)][0]);
            }
            numberOfUnitTypeSet = size;
            numberOfUnitTypeVector = unitTypes.size();
            optimalCost = currentCost;
            return;
        }
        if(size > numberOfUnitTypeSet){
            finalSumDamage = sumDamage;
            for(int i=0;i<numOfAllUnit;i++){
                optimalSolution.add((String) allUnits[units.get(i)][0]);
            }
            numberOfUnitTypeSet = set.size();
            numberOfUnitTypeVector = unitTypes.size();
            optimalCost = currentCost;
        }
        else if(size == numberOfUnitTypeSet){
            if(unitTypes.size() > numberOfUnitTypeVector){
                finalSumDamage = sumDamage;
                for(int i=0;i<units.size();i++){
                    optimalSolution.add((String) allUnits[units.get(i)][0]);
                }
                numberOfUnitTypeVector = unitTypes.size();
                optimalCost = currentCost;
            }
        }
    }

    public void print(){
        System.out.println("Final Units is:");
        for (String u : optimalSolution) {
            System.out.println(u);
        }
        System.out.println("End.");
        System.out.println("Optimal Cost: " + optimalCost);
    }

    public void fun2(double point){

        for(int i = 0; i < numOfAllUnit; i++){

            int numOfUnit = 0;
            while (point >= (double)allUnits[i][2]){
                point -= (double)allUnits[i][2];
                optimalCost += (double)allUnits[i][2];
                numOfUnit++;
            }

            for(int j=0;j<numOfUnit;j++) {
                optimalSolution.add((String)allUnits[i][0]);
                unitTypes.addAll((Vector<UnitType>)allUnits[i][3]);
            }
        }
    }

    public void fun(int i,double point){
        if(i == numOfAllUnit){
            check();
            return;
        }

        int numOfCopy = (int) Math.ceil((300/(double)allUnits[i][1]));
        numOfCopy = Math.max(2,numOfCopy);

        int numOfUnit = 0;
        if((double)allUnits[i][2] <= point) {
            double sumPoint = point;

            for(int j = 0; j < numOfCopy; j++){
                if(sumPoint < (double)allUnits[i][2])
                    break;
                sumPoint -= (double)allUnits[i][2];
                currentCost += (double)allUnits[i][2];
                numOfUnit++;
            }

            for(int j=0;j<numOfUnit;j++) {
                units.add(i);
                unitTypes.addAll((Vector<UnitType>)allUnits[i][3]);
            }

            fun(i + 1, sumPoint);

            units.removeAll(Collections.singleton(i));
            for(int j = 0; j < numOfUnit; j++) {
                //units.remove(i);
                for (int k = 0; k < ((Vector<UnitType>)allUnits[i][3]).size(); k++) {
                    unitTypes.remove(((Vector<UnitType>)allUnits[i][3]).get(k));
                }
            }
            currentCost -= (numOfUnit)*((double)allUnits[i][2]);
        }
        fun(i+1,point);
    }

    public void runBacktrack(double point){
        fun(0,point);
        fun2(point-optimalCost);

        print();
    }
}
