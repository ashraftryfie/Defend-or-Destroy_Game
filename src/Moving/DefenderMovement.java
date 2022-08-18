package Moving;

import Arena.Grid;
import Units.Unit;

public class DefenderMovement extends Movement {


    /** Find The Movement Direction For The Unit **/
    @Override
    public int[] ShortestPath(Unit unit , Grid grid, int x,int y) {
        int xUnit = unit.getPosition().getCenterX();
        int yUnit = unit.getPosition().getCenterY();
        int xBase=x;
        int yBase=y;
        int deltaX = Math.abs(xUnit - xBase);
        int deltaY = Math.abs(yUnit - yBase);
        int distance = Math.max(deltaX,deltaY);

        int[] arr = new int[]{xUnit,yUnit };

        if(deltaX == distance && deltaY != distance){
            if(xUnit < xBase){
                if(walk){
                    int value=grid.acceptUnitMovement(unit, xUnit,yUnit + 1);
                    if(value != 0){
                        if(value == 2)
                            decreaseSpeed=true;
                        arr[1] = yUnit + 1 ;
                    }
                    else
                        walk=!walk;
                }
                else{
                    int value=grid.acceptUnitMovement(unit, xUnit,yUnit - 1);
                    if(value!=0) {
                        if (value == 2)
                            decreaseSpeed = true;
                        arr[1] = yUnit - 1;
                    }
                    else
                        walk=!walk;
                }
            }
            else{
                if(walk){
                    int value=grid.acceptUnitMovement(unit,xUnit,yUnit - 1);
                    if(value!=0) {
                        if (value == 2)
                            decreaseSpeed = true;
                        arr[1] = yUnit - 1;
                    }
                    else
                        walk=!walk;
                }
                else{
                    int value=grid.acceptUnitMovement(unit,xUnit,yUnit + 1);
                    if(value!=0) {
                        if (value == 2)
                            decreaseSpeed = true;
                        arr[1] = yUnit + 1;
                    }
                    else
                        walk=!walk;
                }
            }
        }
        else if(deltaX != distance && deltaY == distance){
            if(yUnit < yBase){
                if(walk){
                    int value=grid.acceptUnitMovement(unit,xUnit - 1,yUnit);
                    if(value!=0) {
                        if (value == 2)
                            decreaseSpeed = true;
                        arr[0] = xUnit - 1;
                    }
                    else
                        walk=!walk;
                }
                else{
                    int value=grid.acceptUnitMovement(unit,xUnit + 1,yUnit);
                    if(value!=0) {
                        if (value == 2)
                            decreaseSpeed = true;
                        arr[0] = xUnit + 1;
                    }
                    else
                        walk=!walk;
                }
            }
            else{
                if(walk){
                    int value=grid.acceptUnitMovement(unit,xUnit + 1,yUnit);
                    if(value!=0) {
                        if (value == 2)
                            decreaseSpeed = true;
                        arr[0] = xUnit + 1;
                    }
                    else
                        walk=!walk;
                }
                else{
                    int value=grid.acceptUnitMovement(unit,xUnit - 1,yUnit);
                    if(value!=0) {
                        if (value == 2)
                            decreaseSpeed = true;
                        arr[0] = xUnit - 1;
                    }
                    else
                        walk=!walk;
                }
            }
        }
        else {
            if(xUnit < xBase && yUnit > yBase){
                if(walk){
                    int value=grid.acceptUnitMovement(unit,xUnit + 1,yUnit);
                    if(value!=0) {
                        if (value == 2)
                            decreaseSpeed = true;
                        arr[0] = xUnit + 1;
                    }
                    else
                        walk=!walk;
                }
                else{
                    int value=grid.acceptUnitMovement(unit,xUnit,yUnit - 1);
                    if(value!=0) {
                        if (value == 2)
                            decreaseSpeed = true;
                        arr[1] = yUnit - 1;
                    }
                    else
                        walk=!walk;
                }
            }
            else if(xUnit > xBase && yUnit > yBase){
                if(walk){
                    int value=grid.acceptUnitMovement(unit,xUnit,yUnit - 1);
                    if(value!=0) {
                        if (value == 2)
                            decreaseSpeed = true;
                        arr[1] = yUnit - 1;
                    }
                    else
                        walk=!walk;
                }
                else{
                    int value=grid.acceptUnitMovement(unit,xUnit - 1,yUnit);
                    if(value!=0) {
                        if (value == 2)
                            decreaseSpeed = true;
                        arr[0] = xUnit - 1;
                    }
                    else
                        walk=!walk;
                }
            }
            else if(xUnit > xBase && yUnit < yBase){
                if(walk){
                    int value=grid.acceptUnitMovement(unit,xUnit - 1,yUnit);
                    if(value!=0) {
                        if (value == 2)
                            decreaseSpeed = true;
                        arr[0] = xUnit - 1;
                    }
                    else
                        walk=!walk;
                }
                else{
                    int value=grid.acceptUnitMovement(unit,xUnit,yUnit + 1);
                    if(value!=0) {
                        if (value == 2)
                            decreaseSpeed = true;
                        arr[1] = yUnit + 1;
                    }
                    else
                        walk=!walk;
                }
            }
            else{
                if(walk){
                    int value=grid.acceptUnitMovement(unit,xUnit,yUnit + 1);
                    if(value!=0) {
                        if (value == 2)
                            decreaseSpeed = true;
                        arr[1] = yUnit + 1;
                    }
                    else
                        walk=!walk;
                }
                else {
                    int value=grid.acceptUnitMovement(unit,xUnit + 1,yUnit);
                    if(value!=0) {
                        if (value == 2)
                            decreaseSpeed = true;
                        arr[0] = xUnit + 1;
                    }
                    else
                        walk=!walk;
                }
            }
        }

        return arr;
    }
}