package Units;


import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class UnitFactory {
    public Unit createUnit(String unitName, int x, int y, double mode, UnitDestroyObserver observer){
        String file1;
        String file2;
        if(mode == 0){
            file1 = "AttackUnitNames";
            file2 = "AttackUnitInfo";
        }
        else if(mode == 1){
            file1 = "DefendUnitNames";
            file2 = "DefendUnitInfo";
        }
        else {
            file1 = "C:\\Users\\cmoka\\OneDrive\\Desktop\\Final Project\\FinalDorDGame_13_12\\src\\StaticUnitNames";
            file2 = "C:\\Users\\cmoka\\OneDrive\\Desktop\\Final Project\\FinalDorDGame_13_12\\src\\StaticUnitInfo";
        }
        Unit unit = null;
        int index = 0;
        try {
            Scanner input = new Scanner(new File(file1));
            while (input.hasNextLine()) {
                if (input.next().equalsIgnoreCase(unitName)) {
                    break;
                }
                index++;
                input.nextLine();
            }
            input.close();
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            Scanner input = new Scanner(new File(file2));
            while (input.hasNextLine()) {
                if (index==0) {
                    unit = new Unit(x, y, mode, observer, input.nextLine());
                    break;
                }
                index--;
                input.nextLine();
            }
            input.close();
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return unit;
    }
}
