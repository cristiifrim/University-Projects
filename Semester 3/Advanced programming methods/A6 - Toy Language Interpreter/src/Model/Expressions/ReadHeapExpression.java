package Model.Expressions;

import Exceptions.DataStructureException;
import Exceptions.ExpressionException;
import Exceptions.StatementException;
import Model.DataStructures.iHashMap;
import Model.DataStructures.iHeap;
import Model.Types.ReferenceType;
import Model.Types.iType;
import Model.Values.ReferenceValue;
import Model.Values.iValue;

public class ReadHeapExpression implements iExpression{
    private final iExpression __expression;

    public ReadHeapExpression(iExpression expression) {
        __expression = expression;
    }

    @Override
    public iValue eval(iHashMap<String, iValue> symTable, iHeap heap) throws DataStructureException, ExpressionException, StatementException {
        iValue value = __expression.eval(symTable, heap);
        if (!(value instanceof ReferenceValue))
            throw new ExpressionException(String.format("%s not of RefType", value));
        ReferenceValue refValue = (ReferenceValue) value;
        return heap.get(refValue.getAddress());
    }

    @Override
    public iType typeCheck(iHashMap<String, iType> typeEnvironment) throws ExpressionException, DataStructureException {
        iType type = __expression.typeCheck(typeEnvironment);

        if(!(type instanceof ReferenceType referenceType))
            throw new ExpressionException("The ReadHead arguments is not of ReferenceType");

        return referenceType.getInner();
    }

    @Override
    public iExpression copy() {
        return new ReadHeapExpression(__expression.copy());
    }

    @Override
    public String toString() {
        return String.format("ReadHeap(%s)", __expression);
    }
}