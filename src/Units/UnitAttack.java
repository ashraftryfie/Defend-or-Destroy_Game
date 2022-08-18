package Units;

import static Units.Unit.gameInPauseCheckedOnce;
import static Units.Unit.myState;

/** Inner Class UnitAttack **/
public class UnitAttack {

    /** UnitAttack Constructor **/


    public static synchronized void gameInPause() throws Exception {
        if(!myState && !gameInPauseCheckedOnce){
            gameInPauseCheckedOnce=true;
            throw new Exception("Game Is In (Pause), Can't Perform Attack.");
        }

    }

    /** Main Method to Do Attack **/
    public void performAttack(Unit unit) throws InterruptedException {
        while (!unit.getTargetUnits().isEmpty() && unit.getUnitProperties()[0].getPropertyValue() > 0 && myState) {
            unit.setTargetedUnit(unit.AcceptStrategy(unit.getAttackStrategy())) ;

            while (unit.getUnitProperties()[0].getPropertyValue() > 0 && unit.getTargetedUnit().getUnitProperties()[0].getPropertyValue() > 0 ) {
                /** Exception When Game Is Paused **/

                    try {
                        gameInPause();
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                    if(!myState)
                        return;

                /** Exception When Attacking Dead Unit **/
                try {
                    Unit.isAliveCheck(unit.getTargetedUnit());
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                    return;
                }

                Thread.sleep(1000);
                AttackUnit(unit.getTargetedUnit(),unit);

            }
            if(!myState)
                return;
            if (unit.getUnitProperties()[0].getPropertyValue() > 0) {
                unit.getTargetUnits().remove(unit.getTargetedUnit());
                unit.getTargetedUnit().onDestroy();
            }
        }
    }

    /** AttackUnit **/
    public void AttackUnit(Unit targetUnit,Unit Unit) throws InterruptedException {
        if (Unit.getUnitProperties()[4].getPropertyValue() == 0) {
            System.out.println("Unit " + targetUnit.getUnitName() + "#" + targetUnit.getID() + " Team(Defender) is being attacked by: unit " + Unit.getUnitName() + "#" + Unit.getID()+" at: " + java.time.LocalTime.now());
        } else{
            if(targetUnit.getUnitProperties()[4].getPropertyValue()==2){
                System.out.println("Unit " + targetUnit.getUnitName() + "#" + targetUnit.getID() + " is being attacked by: unit " + Unit.getUnitName() + "#" + Unit.getID()+" at: " + java.time.LocalTime.now());
            }
            else {
                System.out.println("Unit " + targetUnit.getUnitName() + "#" + targetUnit.getID() + " Team(Attacker) is being attacked by: unit " + Unit.getUnitName() + "#" + Unit.getID()+" at: " + java.time.LocalTime.now());
            }
        }
        targetUnit.getUnitAttack().AcceptDamage(Unit , targetUnit);
    }

    /** Accept Damage **/
    public void AcceptDamage(Unit unit , Unit targetUnit) {
        double totalDamage;
        if (unit.getUnitType() == UnitType.Airplane){
            totalDamage = unit.getUnitProperties()[6].getPropertyValue();
        }
        else {
            totalDamage = unit.getUnitProperties()[6].getPropertyValue() - (unit.getUnitProperties()[6].getPropertyValue() * targetUnit.getUnitProperties()[3].getPropertyValue());
            totalDamage *= unit.getUnitProperties()[2].getPropertyValue();
        }
        targetUnit.getUnitProperties()[0].setPropertyValue(targetUnit.getUnitProperties()[0].getPropertyValue() - totalDamage);
        if(targetUnit.getUnitProperties()[0].getPropertyValue()<=0) {
            targetUnit.onDestroy();
            return;
        }
        if (targetUnit.getUnitProperties()[4].getPropertyValue() == 0) {
            System.out.println("Unit " + targetUnit.getUnitName() + "#" + targetUnit.getID() + " Team(Attacker) health is: " + targetUnit.getUnitProperties()[0].getPropertyValue());
        } else {
            System.out.println("Unit " + targetUnit.getUnitName() + "#" + targetUnit.getID() + " Team(Defender) health is: " + targetUnit.getUnitProperties()[0].getPropertyValue());
        }
    }


}

