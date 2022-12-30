package Model.Values;

import Model.Types.StringType;
import Model.Types.iType;

public class StringValue implements iValue {

    private final String __value;

    public StringValue(String value) {
        __value = value;
    }

    public String get() {
        return __value;
    }

    @Override
    public iType getType() {
        return new StringType();
    }

    @Override
    public iValue copy() {
        return new StringValue(__value);
    }

    @Override
    public String toString() {
        return __value;
    }
}
