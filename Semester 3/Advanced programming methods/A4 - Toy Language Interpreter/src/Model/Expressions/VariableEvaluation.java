package Model.Expressions;

import Exceptions.DataStructureException;
import Exceptions.ExpressionException;
import Exceptions.StatementException;
import Model.DataStructures.iHashMap;
import Model.DataStructures.iHeap;
import Model.Values.iValue;

public class VariableEvaluation implements iExpression{

    String __variable;

    public VariableEvaluation(String variable) {
        __variable = variable;
    }

    @Override
    public iValue eval(iHashMap<String, iValue> variablesTable, iHeap memoryHeap) throws ExpressionException, DataStructureException, StatementException {
        return variablesTable.find(__variable);
    }

    @Override
    public iExpression copy() {
        return new VariableEvaluation(__variable);
    }

    @Override
    public String toString() {
        return __variable;
    }
}
