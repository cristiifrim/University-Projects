package Model.Statements;

import Exceptions.DataStructureException;
import Exceptions.ExpressionException;
import Exceptions.StatementException;
import Model.DataStructures.iHashMap;
import Model.DataStructures.iHeap;
import Model.Expressions.iExpression;
import Model.ProgramState.ProgramState;
import Model.Types.ReferenceType;
import Model.Types.iType;
import Model.Values.ReferenceValue;
import Model.Values.iValue;

public class WriteHeapStatement implements iStatement{
    private final String __name;
    private final iExpression __expression;

    public WriteHeapStatement(String varName, iExpression expression) {
        __name = varName;
        __expression = expression;
    }

    @Override
    public ProgramState execute(ProgramState state) throws DataStructureException, ExpressionException, StatementException {
        iHashMap<String, iValue> symbolsTable = state.getHashmap();
        iHeap heap = state.getHeap();

        iValue value;

        try {
            value = symbolsTable.find(__name);
        }
        catch(DataStructureException e) {
            throw new StatementException(String.format("%s is not defined", __name));
        }
        if (!(value instanceof ReferenceValue))
            throw new StatementException(String.format("%s not of ReferenceType", value));
        ReferenceValue refValue = (ReferenceValue) value;
        iValue evaluated = __expression.eval(symbolsTable, heap);
        if (!evaluated.getType().equals(refValue.getLocationType()))
            throw new StatementException(String.format("%s not of %s", evaluated, refValue.getLocationType()));
        heap.update(refValue.getAddress(), evaluated);
        state.setHeap(heap);
        return null;
    }

    @Override
    public iHashMap<String, iType> typeCheck(iHashMap<String, iType> typeEnvironment) throws StatementException, DataStructureException, ExpressionException {
        iType typeVariable = typeEnvironment.find(__name);
        iType typeExpression = __expression.typeCheck(typeEnvironment);

        if(!typeVariable.equals(new ReferenceType(typeExpression)))
            throw new StatementException("Write Heap Statement operands differ in type");

        return typeEnvironment;
    }

    @Override
    public iStatement copy() {
        return new WriteHeapStatement(__name, __expression.copy());
    }

    @Override
    public String toString() {
        return String.format("WriteHeap(%s, %s)", __name, __expression);
    }
}