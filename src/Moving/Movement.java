package Moving;

import Arena.Grid;
import Units.Unit;

import static java.lang.Thread.sleep;

public abstract class Movement {
    protected boolean walk;
    protected boolean decreaseSpeed;
    public abstract int[] ShortestPath(Unit unit, Grid grid, int x, int y);

    public boolean isWalk() {
        return walk;
    }
    public void setWalk(boolean walk) {
        this.walk = walk;
    }

    public void checkException (Unit unit , int [] arr) throws Exception{
        if(arr[0] == unit.getPosition().getCenterX() && arr[1] == unit.getPosition().getCenterY()){
            throw new Exception("Illegal Movement, Can't Move Unit "+unit.getUnitName()+"#"+unit.getID()+".");
        }
    }

    /** Movement Method **/
    public void move(Unit unit, Grid grid, int targetX,int targetY) throws InterruptedException {
        int x = unit.getPosition().getCenterX();
        int y = unit.getPosition().getCenterY();

        int[] arr = new int[]{x,y};

        if(unit.getUnitProperties()[4].getPropertyValue() == 0){
            arr = unit.getMovement().ShortestPath(unit,grid,targetX,targetY);
        }
        else if (unit.getUnitProperties()[4].getPropertyValue() == 1) {
            arr = unit.getMovement().ShortestPath(unit,grid,targetX,targetY);
        }
        if (!Unit.myState) {
            return;
        }
        try{
            checkException(unit,arr);
        }catch (Exception e){
            System.out.println(e.getMessage());
            return;
        }

        if(unit.getUnitProperties()[4].getPropertyValue() == 0){
            //System.out.println("Unit " + unit.getUnitName() + "#" + unit.getID() + " Team(Attacker) moved to: (" + arr[0] + "," + arr[1] + ") at: " + java.time.LocalTime.now());

            if(unit.getUnitProperties()[1].getPropertyValue()<=unit.getMovementCnt()) {
                System.out.println("Unit " + unit.getUnitName() + "#" + unit.getID() + " Team(Attacker) moved to: (" + arr[0] + "," + arr[1] + ") at: " + java.time.LocalTime.now());
                unit.setMovementCnt(1);
            }
            else {
                unit.setMovementCnt(unit.getMovementCnt()+1);
            }
        }
        else if(unit.getUnitProperties()[4].getPropertyValue() == 1){
            //System.out.println("Unit " + unit.getUnitName() + "#" + unit.getID() + " Team(Defend) moved to: (" + arr[0] + "," + arr[1] + ") at: " + java.time.LocalTime.now());

            if(unit.getUnitProperties()[1].getPropertyValue()<=unit.getMovementCnt()) {
                System.out.println("Unit " + unit.getUnitName() + "#" + unit.getID() + " Team(Defend) moved to: (" + arr[0] + "," + arr[1] + ") at: " + java.time.LocalTime.now());
                unit.setMovementCnt(1);
            }
            else {
                unit.setMovementCnt(unit.getMovementCnt()+1);
            }
        }
        grid.updateUnit(unit,arr[0],arr[1]);
        double speed = 1;
        if(decreaseSpeed)
            speed = 10;
        double movementSpeed=((double)1/unit.getUnitProperties()[1].getPropertyValue())*(1000*speed);
        //System.out.println("Sleep move: " + movementSpeed);
        sleep((long) (movementSpeed));
        decreaseSpeed = false;
    }
}
