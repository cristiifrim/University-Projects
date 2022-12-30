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

public class NewAllocationStatement implements iStatement{
    private final String __name;
    private final iExpression __expression;

    public NewAllocationStatement(String varName, iExpression expression) {
        __name = varName;
       __expression = expression;
    }

    @Override
    public ProgramState execute(ProgramState state) throws DataStructureException, ExpressionException, StatementException {
        iHashMap<String, iValue> symTable = state.getHashmap();
        iHeap heap = state.getHeap();


        iValue varValue;
        try {
            varValue = symTable.find(__name);
        }
        catch(DataStructureException e) {
            throw new StatementException(String.format("%s is not defined", __name));
        }
        if (!(varValue.getType() instanceof ReferenceType))
            throw new StatementException(String.format("%s in not of RefType", __name));
        iValue evaluated = __expression.eval(symTable, heap);
        iType locationType = ((ReferenceValue)varValue).getLocationType();
        if (!locationType.equals(evaluated.getType()))
            throw new StatementException(String.format("%s not of %s", __name, evaluated.getType()));
        int newPosition = heap.add(evaluated);
        symTable.insert(__name, new ReferenceValue(newPosition, locationType));
        state.setHashmap(symTable);
        state.setHeap(heap);
        return state;
    }

    @Override
    public iStatement copy() {
        return new NewAllocationStatement(__name, __expression.copy());
    }

    @Override
    public String toString() {
        return String.format("New(%s, %s)", __name, __expression);
    }
}