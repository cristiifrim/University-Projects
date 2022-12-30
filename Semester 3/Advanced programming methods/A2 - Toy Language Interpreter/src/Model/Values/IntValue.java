package Model.Values;

import Model.Types.IntType;
import Model.Types.iType;

public class IntValue implements iValue {

    private final int __value;

    public IntValue(int value) {
        __value = value;
    }

    public int get() {
        return __value;
    }

    @Override
    public iType getType() {
        return new IntType();
    }

    @Override
    public iValue copy() {
        return new IntValue(__value);
    }

    @Override
    public String toString() {
        return String.valueOf(__value);
    }
}
