package Properties;

public class ArmorUnitProperty extends UnitProperty {
    public ArmorUnitProperty(double propertyValue) {
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
