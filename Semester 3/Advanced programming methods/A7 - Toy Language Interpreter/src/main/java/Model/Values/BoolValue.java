package Model.Values;

import Model.Types.BoolType;
import Model.Types.iType;

public class BoolValue implements iValue {

    private final boolean __value;

    public BoolValue(boolean value) {
        __value = value;
    }

    public boolean get() {
        return __value;
    }

    @Override
    public iType getType() {
        return new BoolType();
    }

    @Override
    public iValue copy() {
        return new BoolValue(__value);
    }

    @Override
    public String toString() {
        return String.valueOf(__value);
    }
}
