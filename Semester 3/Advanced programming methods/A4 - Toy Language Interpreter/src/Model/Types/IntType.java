package Model.Types;

import Model.Values.iValue;
import Model.Values.IntValue;

public class IntType implements iType {

    @Override
    public boolean equals(iType another) {
        return another instanceof IntType;
    }

    @Override
    public iValue _default() {
        return new IntValue(0);
    }

    @Override
    public iType copy() {
        return new IntType();
    }

    public String toString() {
        return "int";
    }
}
