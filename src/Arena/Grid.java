package Arena;

import Units.Unit;
import Units.UnitType;

import java.util.ArrayList;
import java.util.Vector;

public class Grid {
    public ArrayList<Unit> grid ;
    public ArrayList<Object[]> occupied;
    private boolean riverHere = false;
    public Grid() {
        grid=new ArrayList<>();
        occupied=new ArrayList<>();
    }

    /** Getter & Setter **/
    public ArrayList<Unit> getGrid() {
        return grid;
    }
    public ArrayList<Object[]> getOccupied() {
        return occupied;
    }
    public boolean isRiverHere() {
        return riverHere;
    }

    /** Check If Wanted Square Is Occupied Or Not **/
    public synchronized int isOccupied(Unit unit, int x, int y){
        /* 0 can move 1 out of grid 2 move unit 3 static unit 4 river */
        int minX = x - unit.getPosition().getRadius();
        int maxX = x + unit.getPosition().getRadius();
        int minY = y - unit.getPosition().getRadius();
        int maxY = y - unit.getPosition().getRadius();
        if(maxX >= 1e6 || maxY >= 1e6 || minX < 0 || minY < 0)
            return 1;
        if(unit.getUnitType() == UnitType.Airplane) {
            return 0;
        }

        int r = unit.getPosition().getRadius();

        for (int i = 0; i < occupied.size(); i++){
            boolean stat = false;

            Object[] u = occupied.get(i);

            Unit TempUnit = (Unit)u[0];

            int subX = Math.abs(TempUnit.getPosition().getCenterX() - x);
            int subY = Math.abs(TempUnit.getPosition().getCenterY() - y);
            if(subX > 0 && subY > 0){
                r = (int) Math.sqrt(2 * Math.pow(r, 2));
                stat = true;
            }

            if(TempUnit.getID() == unit.getID()) {
                continue;
            }

            int r1 = TempUnit.getPosition().getRadius();
            if(stat){
                r1 = (int) Math.sqrt(2 * Math.pow(r1, 2));
            }

            if((boolean) u[1]){
                int[] futureCoordinates = {(int)u[2], (int)u[3]};

                int distance = (int) Math.sqrt( Math.pow(x - futureCoordinates[0], 2) + Math.pow(y - futureCoordinates[1], 2));

                if(distance < (r+r1) && TempUnit.getUnitType() != UnitType.Airplane && TempUnit.getID() != unit.getID()){

                    /* It is impossible for the river to be true */
//                    if (TempUnit.getUnitType() == UnitType.River) {
//                        return 2;
//                    }
                    return 1;
                }
            }

            int[] currentCoordinates = {TempUnit.getPosition().getCenterX(),TempUnit.getPosition().getCenterY()};

            int distance = (int) Math.sqrt( Math.pow(x - currentCoordinates[0], 2) + Math.pow(y - currentCoordinates[1], 2));

            if(distance < (r+r1) && TempUnit.getUnitType() != UnitType.Airplane) {
                if(TempUnit.getUnitType() == UnitType.Bridge){
                    if(TempUnit.getUnitProperties()[0].getPropertyValue() > 0){
                        if(occupied.size() > unit.getID()){
                            Unit tempUnit = (Unit)occupied.get(unit.getID())[0];
                            Object[] tempArray = new Object[]{tempUnit,true,x,y};
                            occupied.set(tempUnit.getID(),tempArray);
                        }
                        return 0;
                    }
                    else {
                        return 1;
                    }
                }
                if (TempUnit.getUnitType() == UnitType.Valley) {
                    return 1;
                }
                if (TempUnit.getUnitType() == UnitType.River) {
                    return 2;
                }
                return 1;
            }
        }
        if(occupied.size() > unit.getID()){
            Unit tempUnit = (Unit)occupied.get(unit.getID())[0];
            Object[] tempArray = new Object[]{tempUnit,true,x,y};
            occupied.set(tempUnit.getID(),tempArray);
        }
        return 0;
    }

    /** Check If Unit Can Move Or Not **/
    public int acceptUnitMovement(Unit unit, int x, int y) {
        int result = isOccupied(unit,x,y);
        if (result == 0)
            return 1;
        if (result == 1)
            return 0;
        return 2;

    }

    /** Add New Unit To The Grid **/
    public boolean addUnit(Unit unit){
        if (isOccupied(unit,unit.getPosition().getCenterX(),unit.getPosition().getCenterY())!=0)
            return false;
        grid.add(unit);
        Unit un = new Unit(unit);
        Object[] u = new Object[]{un,false,un.getPosition().getCenterX(),un.getPosition().getCenterY()};
        occupied.add(u);
        System.out.println("Unit "+unit.getUnitName()+"#"+unit.getID()+" is added to the Grid successfully");
        return true;
    }

    /** Update An Existing Unit In The Grid **/
    public void updateUnit(Unit unit, int x, int y){
        unit.getPosition().setPreviousX(unit.getPosition().getCenterX());
        unit.getPosition().setPreviousY(unit.getPosition().getCenterY());
        unit.getPosition().setCenterX(x);
        unit.getPosition().setCenterY(y);
        grid.set(unit.getID(),unit);

        Object[] tempArray = new Object[]{unit,false,x,y};
        occupied.set(unit.getID(),tempArray);
    }

    /** Get All Units In Attack Range Of The Unit **/
    public synchronized Vector<Unit> getAllUnitsInRange(Unit unit) {
        Vector<Unit> targets=new Vector<>();
        int x = unit.getPosition().getCenterX();
        int y = unit.getPosition().getCenterY();
        int r = unit.getPosition().getRadius();
        int x1, y1, r1;

        for (int i=0;i<grid.size();i++) {
            Unit u = grid.get(i);
            x1 = u.getPosition().getCenterX();
            y1 = u.getPosition().getCenterY();
            r1 = u.getPosition().getRadius();
            int distance = (int) Math.sqrt(Math.pow(x - x1, 2) + Math.pow(y - y1, 2));
            String mode1 = String.valueOf(unit.getUnitProperties()[4].getPropertyValue());
            String mode2 = String.valueOf(u.getUnitProperties()[4].getPropertyValue());
            if (!mode1.equals(mode2)) {
                if (distance - r - r1 <= (int) unit.getUnitProperties()[5].getPropertyValue()) {
                    if(u.getUnitProperties()[4].getPropertyValue() == 2){
                        if(mode1.equals("1.0") && u.getUnitType() == UnitType.Bridge){
                            targets.add(u);
                        }
                    }
                    else if (unit.getCanAttack().contains(u.getUnitType()) && unit.getID() != u.getID() && u.isAlive()) {
                        targets.add(u);
                    }
                }
            }
        }
        return targets;
    }
}
