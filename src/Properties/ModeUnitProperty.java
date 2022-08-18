package Properties;

public class ModeUnitProperty extends UnitProperty {
    public ModeUnitProperty(double propertyValue) {
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
