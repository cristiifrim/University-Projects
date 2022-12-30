package Model.Types;

import Model.Values.ReferenceValue;
import Model.Values.iValue;

public class ReferenceType implements iType{
    private final iType __inner;

    public ReferenceType(iType inner) {
        __inner = inner;
    }

    iType getInner() {
        return __inner;
    }

    @Override
    public boolean equals(iType anotherType) {
        if (anotherType instanceof ReferenceType)
            return __inner.equals(((ReferenceType) anotherType).getInner());
        else
            return false;
    }

    @Override
    public iValue _default() {
        return new ReferenceValue(0, __inner);
    }

    @Override
    public iType copy() {
        return new ReferenceType(__inner.copy());
    }

    @Override
    public String toString() {
        return String.format("Ref(%s)", __inner);
    }
}