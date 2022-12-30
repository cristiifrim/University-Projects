package Model.Statements;

import Exceptions.DataStructureException;
import Exceptions.ExpressionException;
import Exceptions.StatementException;
import Model.DataStructures.iStack;
import Model.Expressions.iExpression;
import Model.ProgramState.ProgramState;
import Model.Types.BoolType;
import Model.Values.BoolValue;
import Model.Values.iValue;

public class IfElseStatement implements iStatement {

    private iExpression __expression;
    private iStatement __first;
    private iStatement __second;

    public IfElseStatement(iExpression expression, iStatement first, iStatement second) {
        __expression = expression;
        __first = first;
        __second = second;
    }


    @Override
    public ProgramState execute(ProgramState state) throws ExpressionException, DataStructureException, StatementException {
        iValue expressionResult = __expression.eval(state.getHashmap());
        iStatement resultedStatement;

        BoolType checker = new BoolType();

        if(!expressionResult.getType().equals(checker))
            throw new ExpressionException("EXP-ERROR: Expression is not of type bool.");

        BoolValue result = (BoolValue)expressionResult;

        if(result.get()) {
            resultedStatement = __first;
        }
        else {
            resultedStatement = __second;
        }

        iStack<iStatement> stack = state.getStack();
        stack.push(resultedStatement);
        state.setStack(stack);

        return state;

    }

    @Override
    public iStatement copy() {
        return null;
    }

    @Override
    public String toString() {
        return "if (" + __expression.toString() + ") then " + __first.toString() + " else " + __second.toString();
    }
}
