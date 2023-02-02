package Model.Values;

import Model.Types.ReferenceType;
import Model.Types.iType;

import java.util.Objects;

public class ReferenceValue implements iValue{
    private final int __address;
    private final iType __locationType;

    public ReferenceValue(int address, iType locationType) {
        __address = address;
        __locationType = locationType;
    }
    @Override
    public iType getType() {
        return new ReferenceType(__locationType);
    }

    public int getAddress() {
        return __address;
    }

    public iType getLocationType() {
        return __locationType;
    }

    @Override
    public iValue copy() {
        return new ReferenceValue(__address, __locationType.copy());
    }

    @Override
    public String toString() {
        return String.format("(%d, %s)", __address, __locationType);
    }
}