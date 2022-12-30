package Model.Types;

import Model.Values.BoolValue;
import Model.Values.iValue;

public class BoolType implements iType {
    @Override
    public boolean equals(iType another) {
        return another instanceof BoolType;
    }

    @Override
    public iValue _default() {
        return new BoolValue(false);
    }

    @Override
    public iType copy() {
        return new BoolType();
    }

    @Override
    public String toString() {
        return "bool";
    }
}
