package Model.Statements;

import Exceptions.DataStructureException;
import Exceptions.ExpressionException;
import Exceptions.StatementException;
import Model.ProgramState.ProgramState;
import Model.DataStructures.iStack;

public class CompoundStatement implements iStatement {

    private iStatement __first;
    private iStatement __second;

    public CompoundStatement(iStatement first, iStatement second) {
        __first = first;
        __second = second;
    }

    @Override
    public String toString() {
        return __first.toString() + ";" + __second.toString();
    }

    @Override
    public ProgramState execute(ProgramState state) throws ExpressionException, DataStructureException, StatementException {
        iStack<iStatement> stack = state.getStack();
        stack.push(__second);
        stack.push(__first);
        state.setStack(stack);
        return null;
    }

    @Override
    public iStatement copy() {
        return new CompoundStatement(__first.copy(), __second.copy());
    }

}
