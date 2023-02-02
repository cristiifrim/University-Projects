package Model.Expressions;

import Exceptions.DataStructureException;
import Exceptions.ExpressionException;
import Exceptions.StatementException;
import Model.DataStructures.iHashMap;
import Model.DataStructures.iHeap;
import Model.Types.IntType;
import Model.Types.iType;
import Model.Values.IntValue;
import Model.Values.iValue;

public class ArithmeticExpression implements iExpression {

    iExpression __first;
    iExpression __second;
    char __operator;

    public ArithmeticExpression(iExpression first, iExpression second, char operator) {
        __first = first;
        __second = second;

        __operator = operator;

    }

    @Override
    public iValue eval(iHashMap<String, iValue> variablesTable, iHeap memoryHeap) throws ExpressionException, DataStructureException, StatementException {
        iValue firstResult = __first.eval(variablesTable, memoryHeap);
        iValue secondResult = __second.eval(variablesTable, memoryHeap);
        IntType checker = new IntType();

        if(!firstResult.getType().equals(checker))
            throw new ExpressionException("EXP-ERROR: First operand is not of type int.");

        if(!secondResult.getType().equals(checker))
            throw new ExpressionException("EXP-ERROR: Second operand is not of type int.");

        IntValue first = (IntValue) firstResult;
        IntValue second = (IntValue) secondResult;

        if(__operator == '/') {
            if(second.get() == 0)
                throw new ExpressionException("EXP-ERROR: Division by zero.");

            return new IntValue(first.get() / second.get());
        }

        return switch (__operator) {
            case '+' -> new IntValue(first.get() + second.get());
            case '-' -> new IntValue(first.get() - second.get());
            case '*' -> new IntValue(first.get() * second.get());
            default -> null;
        };

    }

    @Override
    public iType typeCheck(iHashMap<String, iType> typeEnvironment) throws ExpressionException, DataStructureException {
        iType type1, type2;
        type1 = __first.typeCheck(typeEnvironment);
        type2 = __second.typeCheck(typeEnvironment);

        if(!type1.equals(new IntType()))
            throw new ExpressionException("First operand is not an integer");
        if(!type2.equals(new IntType()))
            throw new ExpressionException("Second operand is not an integer");

        return new IntType();
    }

    @Override
    public iExpression copy() {
        return new ArithmeticExpression(__first, __second, __operator);
    }

    @Override
    public String toString() {
        return __first.toString() + " " + __operator + " " + __second.toString();
    }
}
