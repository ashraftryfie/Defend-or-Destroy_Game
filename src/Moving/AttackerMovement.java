package Moving;

import Arena.Grid;
import Units.Unit;

public class AttackerMovement extends Movement {


    /** Find The Movement Direction For The Unit **/
    public int[] ShortestPath(Unit unit , Grid grid , int x,int y) {
        int[][] check = new int[5][5];
        int posX = unit.getPosition().getCenterX();
        int posY = unit.getPosition().getCenterY();
        int prevX = unit.getPosition().getPreviousX();
        int prevY = unit.getPosition().getPreviousY();
        check[prevX - posX + 1][prevY - posY + 1] = 2;
        int baseX = x;
        int baseY = y;
        int[] arr=new int[] {posX,posY};
        //System.out.println("Previou"+posX+ " "+posY);
        if(posX < baseX){
            if(posY < baseY){
                int value;
                if(prevX != posX + 1 || prevY != posY + 1){
                    value = grid.acceptUnitMovement(unit,posX + 1, posY + 1);
                    if(value != 0){
                        if(value == 2)
                            this.decreaseSpeed = true;
                        arr[0] = posX + 1;
                        arr[1] = posY + 1;
                        return arr;
                    }
                    check[2][2] = 1;
                }
                if(prevX != posX + 1 || prevY != posY) {
                    value = grid.acceptUnitMovement(unit, posX + 1, posY);
                    if (value != 0) {
                        if (value == 2)
                            this.decreaseSpeed = true;
                        arr[0] = posX + 1;
                        return arr;
                    }
                    check[2][1] = 1;
                }
                if(prevX != posX || prevY != posY + 1){
                    value = grid.acceptUnitMovement(unit,posX , posY + 1);
                    if(value != 0){
                        if(value == 2)
                            this.decreaseSpeed = true;
                        arr[1] = posY + 1;
                        return arr;
                    }
                    check[1][2] = 1;
                }
            }
            else if (posY > baseY){
                int value;
                if(prevX != posX + 1 || prevY != posY - 1){
                    value = grid.acceptUnitMovement(unit,posX + 1, posY - 1);
                    if(value != 0){
                        if(value == 2)
                            this.decreaseSpeed = true;
                        arr[0] = posX + 1;
                        arr[1] = posY - 1;
                        return arr;
                    }
                    check[2][0] = 1;
                }

                if(prevX != posX + 1 || prevY != posY){
                    value = grid.acceptUnitMovement(unit,posX + 1, posY);
                    if(value != 0){
                        if(value == 2)
                            this.decreaseSpeed = true;
                        arr[0] = posX + 1;
                        return arr;
                    }
                    check[2][1] = 1;
                }
                if(prevX != posX || prevY != posY - 1){
                    value = grid.acceptUnitMovement(unit,posX , posY - 1);
                    if(value != 0){
                        if(value == 2)
                            this.decreaseSpeed = true;
                        arr[1] = posY - 1;
                        return arr;
                    }
                    check[1][0] = 1;
                }
            }
            else {
                int value;
                if(prevX != posX + 1 || prevY != posY){
                    value = grid.acceptUnitMovement(unit,posX + 1, posY);
                    if(value != 0){
                        if(value == 2)
                            this.decreaseSpeed = true;
                        arr[0] = posX + 1;
                        return arr;
                    }
                    check[2][1] = 1;
                }
            }
        }
        else if(posX > baseX){
            if(posY < baseY){
                int value;
                if(prevX != posX - 1 || prevY != posY + 1){
                    value = grid.acceptUnitMovement(unit,posX - 1, posY + 1);
                    if(value != 0){
                        if(value == 2)
                            this.decreaseSpeed = true;
                        arr[0] = posX - 1;
                        arr[1] = posY + 1;
                        return arr;
                    }
                    check[0][2] = 1;
                }
                if(prevX != posX - 1 || prevY != posY){
                    value = grid.acceptUnitMovement(unit,posX - 1, posY);
                    if(value != 0){
                        if(value == 2)
                            this.decreaseSpeed = true;
                        arr[0] = posX - 1;
                        return arr;
                    }
                    check[0][1] = 1;
                }
                if(prevX != posX || prevY != posY + 1){
                    value = grid.acceptUnitMovement(unit,posX , posY + 1);
                    if(value != 0){
                        if(value == 2)
                            this.decreaseSpeed = true;
                        arr[1] = posY +1;
                        return arr;
                    }
                    check[1][2] = 1;
                }
            }
            else if (posY > baseY){
                int value;
                if(prevX != posX - 1 || prevY != posY - 1){
                    value = grid.acceptUnitMovement(unit,posX - 1, posY - 1);
                    if(value != 0){
                        if(value == 2)
                            this.decreaseSpeed = true;
                        arr[0] = posX - 1;
                        arr[1] = posY - 1;
                        return arr;
                    }
                    check[0][0] = 1;
                }
                if(prevX != posX - 1 || prevY != posY){
                    value = grid.acceptUnitMovement(unit,posX - 1, posY);
                    if(value != 0){
                        if(value == 2)
                            this.decreaseSpeed = true;
                        arr[0] = posX - 1;
                        return arr;
                    }
                    check[0][1] = 1;
                }
                if(prevX != posX || prevY != posY - 1){
                    value = grid.acceptUnitMovement(unit,posX , posY - 1);
                    if(value != 0){
                        if(value == 2)
                            this.decreaseSpeed = true;
                        arr[1] = posY - 1;
                        return arr;
                    }
                    check[1][0] = 1;
                }
            }
            else {
                int value;
                if(prevX != posX - 1 || prevY != posY){
                    value = grid.acceptUnitMovement(unit,posX - 1, posY);
                    if(value != 0){
                        if(value == 2)
                            this.decreaseSpeed = true;
                        arr[0] = posX - 1;
                        return arr;
                    }
                    check[0][1] = 1;
                }
            }
        }
        else {
            if(posY < baseY){
                int value;
                if(prevX != posX || prevY != posY + 1){
                    value = grid.acceptUnitMovement(unit, posX, posY + 1);
                    if(value != 0){
                        if(value == 2)
                            this.decreaseSpeed = true;
                        arr[1] = posY + 1;
                        return arr;
                    }
                    check[1][2] = 1;
                }
            }
            else if (posY > baseY){
                int value;
                if(prevX != posX || prevY != posY - 1){
                    value = grid.acceptUnitMovement(unit, posX, posY - 1 );
                    if(value != 0){
                        if(value == 2)
                            this.decreaseSpeed = true;
                        arr[1] = posY - 1;
                        return arr;
                    }
                    check[1][0] = 1;
                }
            }
        }

        /*System.out.println("************************");
        for(int i=0;i<3;i++){
            for(int j=0;j<3;j++){
                System.out.print(check[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println("************************");*/
        if(arr[0] == posX && arr[1] == posY){
            arr = Check(check,unit,grid);
        }
        return arr;
    }

    public int[] Check(int[][] check ,Unit unit , Grid grid){
        int posX = unit.getPosition().getCenterX();
        int posY = unit.getPosition().getCenterY();
        int prevX = unit.getPosition().getPreviousX();
        int prevY = unit.getPosition().getPreviousY();

        int[] arr = new int[]{prevX,prevY};
        for(int i=0;i<3;i++){
            for(int j=0;j<3;j++){
                if((i == 1 && j == 1) || check[i][j] != 0){
                    continue;
                }
                int value = grid.acceptUnitMovement(unit,posX + i - 1, posY + j - 1);
                if(value != 0){
                    if(value == 2)
                        this.decreaseSpeed = true;
                    arr[0] = posX + i - 1;
                    arr[1] = posY + j - 1;
                    return arr;
                }
            }
        }
        return arr;
    }
}