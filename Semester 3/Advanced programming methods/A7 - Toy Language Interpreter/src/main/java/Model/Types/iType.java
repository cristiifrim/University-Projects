package Model.Types;

import Model.Values.iValue;

public interface iType {
    boolean equals(iType another);
    iValue _default();
    iType copy();
}
