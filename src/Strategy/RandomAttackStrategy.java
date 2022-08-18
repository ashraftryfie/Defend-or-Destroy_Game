package Strategy;

import Units.Unit;

import java.util.Vector;

public class RandomAttackStrategy extends AttackStrategy{
    private RandomAttackStrategy() {
    }
    public static AttackStrategy getInstance() {
        if(instance==null){
            synchronized (RandomAttackStrategy.class){
                if(instance==null){
                    instance = new RandomAttackStrategy();
                }
            }
        }
        return instance;
    }
    @Override
    public Unit prioritizeUnitToAttack(Vector<Unit> units) {
        if(units.size()==0)
            return null;
        return units.get(0);
    }
}