package Strategy;

import Units.Unit;

import java.util.Vector;

public class LowestHealthAttackStrategy extends AttackStrategy{
    private LowestHealthAttackStrategy() {
    }
    public static AttackStrategy getInstance() {
        if(instance==null){
            synchronized (LowestHealthAttackStrategy.class){
                if(instance==null){
                    instance = new LowestHealthAttackStrategy();
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
        double DamageTemp=temp.getUnitProperties()[0].getPropertyValue();
        for(int i=1;i<units.size();i++){
            if(DamageTemp > units.get(i).getUnitProperties()[0].getPropertyValue()){
                temp=units.get(i);
                DamageTemp=temp.getUnitProperties()[0].getPropertyValue();
            }
        }
        return temp;
    }
}