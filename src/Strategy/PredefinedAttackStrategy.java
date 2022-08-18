package Strategy;

import Units.Unit;
import Units.UnitType;

import java.util.Vector;

public class PredefinedAttackStrategy extends AttackStrategy{
    private Unit unit;
    private PredefinedAttackStrategy() {
    }
    public Unit getUnit() {
        return unit;
    }
    public void setUnit(Unit unit) {
        this.unit = unit;
    }
    public static AttackStrategy getInstance() {
        if(instance==null){
            synchronized (PredefinedAttackStrategy.class){
                if(instance==null){
                    instance = new PredefinedAttackStrategy();
                }
            }
        }
        return instance;
    }
    @Override
    public Unit prioritizeUnitToAttack(Vector<Unit> units){
        Vector<UnitType> targets=unit.getCanAttack();
        for(int i=0;i<targets.size();i++){
            for(int j=0;j<units.size();j++){
                if(targets.get(i)==units.get(j).getUnitType())
                    return units.get(j);
            }
        }
        return null;
    }
}