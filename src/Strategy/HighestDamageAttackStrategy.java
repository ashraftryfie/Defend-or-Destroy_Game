package Strategy;

import Units.Unit;

import java.util.Vector;

public class HighestDamageAttackStrategy extends AttackStrategy{
    private HighestDamageAttackStrategy() {
    }
    public static AttackStrategy getInstance() {
        if(instance==null){
            synchronized (HighestDamageAttackStrategy.class){
                if(instance==null){
                    instance = new HighestDamageAttackStrategy();
                }
            }
        }
        return instance;
    }
    @Override
    public Unit prioritizeUnitToAttack(Vector<Unit> units){
        if(units.size()==0)
            return null;
        Unit temp=units.get(0);
        double DamageTemp=temp.getUnitProperties()[6].getPropertyValue();
        for(int i=1;i<units.size();i++){
            if(DamageTemp < units.get(i).getUnitProperties()[6].getPropertyValue()){
                temp=units.get(i);
                DamageTemp=temp.getUnitProperties()[6].getPropertyValue();
            }
        }
        return temp;
    }
}