package Units;

import Game.DorD_Game;
import Moving.AttackerMovement;
import Moving.DefenderMovement;
import Moving.Movement;
import Properties.*;
import Strategy.AttackStrategy;
import Strategy.IAttackStrategy;
import Strategy.LowestHealthAttackStrategy;
import Strategy.RandomAttackStrategy;
import javafx.scene.shape.Circle;

import java.util.ArrayList;
import java.util.Vector;
import java.util.concurrent.atomic.AtomicInteger;

import static java.lang.Double.parseDouble;

public class Unit implements IAttackStrategy {
    private static final AtomicInteger count = new AtomicInteger(-1);
    private final int ID;
    private boolean isAlive;
    private String unitName;
    private Vector<UnitType> canAttack=new Vector<>();
    private Movement movement;
    private UnitPosition position;
    private AttackStrategy attackStrategy;
    private Unit targetedUnit;
    private Vector<UnitDestroyObserver> unitDestroyObservers=new Vector<>();
    private UnitProperty[] unitProperties=new UnitProperty[20];
    private UnitType unitType;
    private UnitDestroyObserver destructionObservers;
    private Vector<Unit> targetUnits=new Vector<>();
    private UnitAttack unitAttack;
    public static boolean myState=true;
    private int movementCnt;
    public static boolean gameInPauseCheckedOnce=true;
    private Circle c;

    /** UnitPosition Class **/
    public class UnitPosition {
        private int centerX;
        private int centerY;
        private int previousX;
        private int previousY;
        private int radius;

        public UnitPosition(int centerX, int centerY, int radius , int previousX, int previousY) {
            this.centerX = centerX;
            this.centerY = centerY;
            this.previousX = previousX;
            this.previousY = previousY;
            this.radius = radius;
        }
        public int getCenterX() {
            return centerX;
        }
        public void setCenterX(int centerX) {
            this.centerX = centerX;
        }

        public int getCenterY() {
            return centerY;
        }
        public void setCenterY(int centerY) {
            this.centerY = centerY;
        }

        public int getPreviousX() {
            return previousX;
        }
        public void setPreviousX(int previousX) {
            this.previousX = previousX;
        }

        public int getPreviousY() {
            return previousY;
        }
        public void setPreviousY(int previousY) {
            this.previousY = previousY;
        }

        public int getRadius() {
            return radius;
        }
        public void setRadius(int radius) {
            this.radius = radius;
        }
    }

    /** Default Constructor **/
    public Unit(int x, int y, double mode, UnitDestroyObserver observer,String Line) {
        String[] data =Line.split(" ");

        this.ID = count.incrementAndGet();
        this.unitName=data[0];
        this.isAlive=true;
        this.unitProperties[0]=new HealthUnitProperty(parseDouble(data[1]));
        this.unitProperties[1]=new SpeedMovement(parseDouble(data[2]));
        this.unitProperties[2]=new AttackSpeedUnitProperty(parseDouble(data[3]));
        this.unitProperties[3]=new ArmorUnitProperty(parseDouble(data[4]));
        this.unitProperties[4]=new ModeUnitProperty(mode);
        if (mode==0) {
            this.movement= new AttackerMovement();
            this.attackStrategy= LowestHealthAttackStrategy.getInstance();

        }
        else {
            this.movement= new DefenderMovement();
            this.attackStrategy= RandomAttackStrategy.getInstance();

        }
        this.unitProperties[5]=new AttackRangeUnitProperty(parseDouble(data[5]));
        this.unitProperties[6]=new DamageUnitProperty(parseDouble(data[6]));
        this.unitProperties[7]=new CostUnitProperty(parseDouble(data[7]));
        this.position=new UnitPosition(x,y,Integer.parseInt(data[8]),x,y);
        switch (data[9]) {
            case "Tank" -> this.unitType = UnitType.Tank;
            case "Structure" -> this.unitType = UnitType.Structure;
            case "Solider" -> this.unitType = UnitType.Solider;
            case "Airplane" -> this.unitType = UnitType.Airplane;
            case "MainBase" -> this.unitType = UnitType.MainBase;
            case "River" -> this.unitType = UnitType.River;
            case "Bridge" -> this.unitType = UnitType.Bridge;
            case "Valley" -> this.unitType = UnitType.Valley;

        }
        for (int i=10;i<data.length;i++){
            switch (data[i]) {
                case "Tank" -> this.canAttack.add(UnitType.Tank);
                case "Structure" -> this.canAttack.add(UnitType.Structure);
                case "Solider" -> this.canAttack.add(UnitType.Solider);
                case "Airplane" -> this.canAttack.add(UnitType.Airplane);
                case "MainBase" -> this.canAttack.add(UnitType.MainBase);
                case "River" -> this.canAttack.add(UnitType.River);
                case "Bridge" -> this.canAttack.add(UnitType.Bridge);
                case "Valley" -> this.canAttack.add(UnitType.Valley);
            }
        }
        this.c=new Circle(this.getPosition().getRadius());
        this.destructionObservers=observer;
        this.addUnitDestroyObserver(observer);
        this.unitAttack=new UnitAttack();
        this.movementCnt=1;
    }

    /** Copy Constructor **/
    public Unit(Unit unit) {
        this.ID = unit.ID;
        this.isAlive = unit.isAlive;
        this.unitName = unit.unitName;
        this.canAttack = unit.canAttack;
        this.movement = unit.movement;
        this.position = unit.position;
        this.attackStrategy = unit.attackStrategy;
        this.targetedUnit = unit.targetedUnit;
        this.unitDestroyObservers = unit.unitDestroyObservers;
        this.unitProperties = unit.unitProperties;
        this.unitType = unit.unitType;
        this.destructionObservers = unit.destructionObservers;
        this.targetUnits = unit.targetUnits;
        this.unitAttack = unit.unitAttack;
        this.movementCnt = unit.movementCnt;
    }

    /** Getter & Setter **/

    public UnitAttack getUnitAttack() {
        return unitAttack;
    }
    public void setUnitAttack(UnitAttack unitAttack) {
        this.unitAttack = unitAttack;
    }

    public Circle getC() {
        return c;
    }
    public void setC(Circle c) {
        this.c = c;
    }

    public int getMovementCnt() {
        return movementCnt;
    }
    public void setMovementCnt(int cnt) {
        this.movementCnt = cnt;
    }

    public String getUnitName() {
        return unitName;
    }
    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public int getID() {
        return ID;
    }

    public boolean isAlive() {
        return isAlive;
    }
    public void setAlive(boolean alive) {
        isAlive = alive;
    }

    public Vector<UnitType> getCanAttack() {
        return canAttack;
    }
    public void setCanAttack(Vector<UnitType> canAttack) {
        this.canAttack = canAttack;
    }

    public Movement getMovement() {
        return movement;
    }
    public void setMovement(Movement movement) {
        this.movement = movement;
    }

    public UnitPosition getPosition() {
        return position;
    }
    public void setPosition(UnitPosition position) {
        this.position = position;
    }

    public AttackStrategy getAttackStrategy() {
        return attackStrategy;
    }
    public void setAttackStrategy(AttackStrategy attackStrategy) {
        this.attackStrategy = attackStrategy;
    }

    public Unit getTargetedUnit() {
        return targetedUnit;
    }
    public void setTargetedUnit(Unit targetedUnit) {
        this.targetedUnit = targetedUnit;
    }

    public Vector<UnitDestroyObserver> getUnitDestroyObservers() {
        return unitDestroyObservers;
    }
    public void setUnitDestroyObservers(Vector<UnitDestroyObserver> unitDestroyObservers) {
        this.unitDestroyObservers = unitDestroyObservers;
    }

    public UnitProperty[] getUnitProperties() {
        return unitProperties;
    }
    public void setUnitProperties(UnitProperty[] unitProperties) {
        this.unitProperties = unitProperties;
    }

    public UnitType getUnitType() {
        return unitType;
    }
    public void setUnitType(UnitType unitType) {
        this.unitType = unitType;
    }

    public UnitDestroyObserver getDestructionObservers() {
        return destructionObservers;
    }
    public void setDestructionObservers(UnitDestroyObserver destructionObservers) {
        this.destructionObservers = destructionObservers;
    }

    public Vector<Unit> getTargetUnits() {
        return targetUnits;
    }
    public void setTargetUnits(Unit unit) {
        this.targetUnits.add(unit);
    }

    /** AcceptStrategy **/
    public Unit AcceptStrategy(AttackStrategy attackStrategy){
        return attackStrategy.prioritizeUnitToAttack(targetUnits);
    }

    /** Call Notify for The Observer **/
    public void onDestroy() {
        this.destructionObservers.OnUnitDestroy(this);
    }

    /** Check If Unit Is Alive Or Not **/
    public static void isAliveCheck(Unit unit) throws Exception{
        if(!unit.isAlive()){
            throw new Exception("Unit "+unit.getUnitName()+"#"+unit.getID()+" Is Already Dead, Can't Perform Attack.");
        }
    }

    /** Add Observer To Unit **/
    public void addUnitDestroyObserver(UnitDestroyObserver unitDestroyObserver) {
        unitDestroyObservers.add(unitDestroyObserver);
    }


    /** Movement & Attack Thread **/
    public class ActionThread extends Thread {
        DorD_Game DorD;

        /*** Action Thread Constructor **/
        public ActionThread(DorD_Game D)
        {
            this.DorD=D;
        }

        /** Airplane Special Case Attack Method **/
        public void runAir(Unit unit) throws InterruptedException {

            ArrayList<int[]> arrayList = DorD.getAirplanesCoordinates();
            int i ;
            for(i=0;i<arrayList.size();i++){
                if(arrayList.get(i)[0] == unit.getID()){
                    break;
                }
            }
            if(arrayList.get(i)[3] == 3){
                Unit.this.movement.move(Unit.this, DorD.getGrid(), 700,700);
                //System.out.println(unit.getPosition().centerX+"  "+unit.getPosition().centerY);
                if(unit.getPosition().getCenterX() == 700 && unit.getPosition().getCenterY() == 700){
                    Thread.sleep(2000);

                    arrayList.get(i)[3] = 0;
                    DorD.setAirplanesCoordinates(arrayList);
                }
            }
            else if(arrayList.get(i)[3] == 0){
                int xUnit = unit.getPosition().getCenterX();
                int yUnit = unit.getPosition().getCenterY();
                int rUnit = unit.getPosition().getRadius();
                int xBase=DorD.getMainBase().getPosition().getCenterX();
                int yBase=DorD.getMainBase().getPosition().getCenterY();
                int rBase=DorD.getMainBase().getPosition().getRadius();

                int distance = (int) Math.sqrt(Math.pow(xUnit - xBase, 2) + Math.pow(yUnit - yBase, 2));
                if (distance - rUnit - rBase <= (int) unit.getUnitProperties()[5].getPropertyValue()) {
                    Unit.this.unitAttack.AttackUnit(DorD.getMainBase(),Unit.this);
                    arrayList.get(i)[3] = 1;
                    DorD.setAirplanesCoordinates(arrayList);
                }
                else {
                    Unit.this.movement.move(Unit.this, DorD.getGrid(), xBase,yBase);
                }
            }
            else if(arrayList.get(i)[3] == 1){
                Unit.this.movement.move(Unit.this, DorD.getGrid(), arrayList.get(i)[1],arrayList.get(i)[2]);
                if(unit.getPosition().getCenterX() ==  arrayList.get(i)[1] && unit.getPosition().getCenterY() == arrayList.get(i)[2]){
                    arrayList.get(i)[3] = 0;
                    DorD.setAirplanesCoordinates(arrayList);
                }
            }
        }
        public void run()
        {
            if(Unit.this.getUnitType()== UnitType.Airplane && Unit.this.getUnitProperties()[0].getPropertyValue() > 0) {
                try {
                    runAir(Unit.this);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return;
            }
            else if(Unit.this.unitType != UnitType.MainBase && Unit.this.getUnitProperties()[0].getPropertyValue() > 0) {
                Unit.this.targetUnits=DorD.getGrid().getAllUnitsInRange(Unit.this);
                try {
                    Unit.this.unitAttack.performAttack(Unit.this);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            if(Unit.this.getUnitProperties()[0].getPropertyValue() > 0 && Unit.this.unitType!=UnitType.Structure  && Unit.this.unitType!=UnitType.Bridge  && Unit.this.unitType!=UnitType.River  && Unit.this.unitType!=UnitType.Valley  && myState) {
                try {

                    Unit.this.movement.move(Unit.this, DorD.getGrid(), DorD.getMainBase().getPosition().getCenterX(),DorD.getMainBase().getPosition().getCenterY());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

    }

}
