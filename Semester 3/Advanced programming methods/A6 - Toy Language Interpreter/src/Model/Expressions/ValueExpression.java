package Model.Expressions;

import Exceptions.ExpressionException;
import Model.DataStructures.iHashMap;
import Model.DataStructures.iHeap;
import Model.Types.iType;
import Model.Values.iValue;

public class ValueExpression implements iExpression {

    iValue __value;

    public ValueExpression(iValue value) {
        __value = value;
    }
    @Override
    public iValue eval(iHashMap<String, iValue> variablesTable, iHeap memoryHeap) throws ExpressionException {
        return __value;
    }

    @Override
    public iType typeCheck(iHashMap<String, iType> typeEnvironment) throws ExpressionException {
        return __value.getType();
    }


    @Override
    public iExpression copy() {
        return new ValueExpression(__value);
    }

    @Override
    public String toString() {
        return __value.toString();
    }
}
