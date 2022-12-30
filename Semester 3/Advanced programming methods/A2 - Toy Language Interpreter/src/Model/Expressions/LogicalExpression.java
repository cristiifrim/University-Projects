package Model.Expressions;

import Exceptions.DataStructureException;
import Exceptions.ExpressionException;
import Exceptions.StatementException;
import Model.DataStructures.iHashMap;
import Model.Types.BoolType;
import Model.Values.BoolValue;
import Model.Values.iValue;

public class LogicalExpression implements iExpression {

    iExpression __first;
    iExpression __second;

    String __operator;

    public LogicalExpression(iExpression first, iExpression second, String operator) {
        __first = first;
        __second = second;
        __operator = operator;
    }

    @Override
    public iValue eval(iHashMap<String, iValue> variablesTable) throws ExpressionException, DataStructureException, StatementException {
        iValue firstResult = __first.eval(variablesTable);
        iValue secondResult = __second.eval(variablesTable);

        BoolType checker = new BoolType();

        /* TODO:
            ADD CUSTOM EXCEPTIONS
         */

        if(!firstResult.getType().equals(checker))
            throw new ExpressionException("EXP-ERROR: First operand is not of type bool.");

        if(!secondResult.getType().equals(checker))
            throw new ExpressionException("EXP-ERROR: Second operand is not of type bool.");

        BoolValue first = (BoolValue)firstResult;
        BoolValue second = (BoolValue)secondResult;

        return switch(__operator) {
            case "and" -> new BoolValue(first.get() && second.get());
            case "or" -> new BoolValue(first.get() || second.get());
            default -> null;
        };
    }

    @Override
    public iExpression copy() {
        return new LogicalExpression(__first, __second, __operator);
    }

    @Override
    public String toString() {
        return __first.toString() + " " + __operator + " " + __second.toString();
    }
}
