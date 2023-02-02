package Model.Values;

import Model.Types.iType;

public interface iValue {

    iType getType();
    iValue copy();
}
