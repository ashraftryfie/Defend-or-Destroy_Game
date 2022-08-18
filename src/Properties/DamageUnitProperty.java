package Properties;

public class DamageUnitProperty extends UnitProperty {
    public DamageUnitProperty(double propertyValue) {
        super(propertyValue);
    }

    @Override
    public double getPropertyValue() {
        return this.propertyValue;
    }

    @Override
    public void setPropertyValue(double propertyValue) {
        this.propertyValue = propertyValue;
    }
}
