package Properties;

public class CostUnitProperty extends UnitProperty {
    public CostUnitProperty(double propertyValue) {
        super(propertyValue);
    }

    @Override
    public double getPropertyValue() {
        return this.propertyValue;
    }

    @Override
    public void setPropertyValue(double propertyValue) {

    }
}
