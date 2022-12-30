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

public class WhileStatement implements iStatement{
    private final iExpression __expression;
    private final iStatement __statement;

    public WhileStatement(iExpression expression, iStatement statement) {
        __expression = expression;
        __statement = statement;
    }

    @Override
    public ProgramState execute(ProgramState state) throws StatementException, ExpressionException, DataStructureException {
        iValue value = __expression.eval(state.getHashmap(), state.getHeap());
        iStack<iStatement> stack = state.getStack();

        if (!value.getType().equals(new BoolType()))
            throw new StatementException(String.format("%s is not of BoolType", value));

        BoolValue boolValue = (BoolValue) value;

        if (boolValue.get()) {
            stack.push(this);
            stack.push(__statement);
        }

        return null;
    }

    @Override
    public iStatement copy() {
        return new WhileStatement(__expression.copy(), __statement.copy());
    }

    @Override
    public String toString() {
        return String.format("while(%s){%s}", __expression, __statement);
    }
}