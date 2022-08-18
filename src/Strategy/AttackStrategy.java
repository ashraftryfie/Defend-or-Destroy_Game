package Strategy;


import Units.Unit;

import java.util.Vector;

public abstract class AttackStrategy{
    protected static AttackStrategy instance;
    public abstract Unit prioritizeUnitToAttack(Vector<Unit> units);
}