package Properties;

public class HealthUnitProperty extends UnitProperty {
    public HealthUnitProperty(double propertyValue) {
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
