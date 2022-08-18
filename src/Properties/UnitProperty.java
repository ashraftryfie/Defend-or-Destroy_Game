package Properties;

public abstract class UnitProperty {
    protected double propertyValue;

    public UnitProperty(double propertyValue) {
        this.propertyValue = propertyValue;
    }
    public abstract double getPropertyValue();

    public abstract void setPropertyValue(double propertyValue);

    /*
        0 => Health
        1 => SpeedMovement
        2 => AttackSpeed
        3 => Armor
        4 => ModeUnit   1 Attack 2 Defend 3 Static
        5 => AttackRange
        6 => Damage
        7 => Cost
     */
}
