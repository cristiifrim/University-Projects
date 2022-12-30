package Model.Types;

import Model.Values.iValue;
import Model.Values.StringValue;

public class StringType implements iType {

    @Override
    public boolean equals(iType another) {
        return another instanceof StringType;
    }

    @Override
    public iValue _default() {
        return new StringValue("");
    }

    @Override
    public iType copy() {
        return new StringType();
    }

    public String toString() {
        return "string";
    }
}
