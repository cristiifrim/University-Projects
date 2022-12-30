package Model.Statements;

import Exceptions.DataStructureException;
import Exceptions.ExpressionException;
import Exceptions.StatementException;
import Model.Expressions.iExpression;
import Model.ProgramState.ProgramState;
import Model.DataStructures.iList;
import Model.Values.iValue;

public class PrintStatement implements iStatement {

    iExpression __expression;

    public PrintStatement(iExpression expression) {
        __expression = expression;
    }

    @Override
    public ProgramState execute(ProgramState state) throws ExpressionException, DataStructureException, StatementException {

        iList<iValue> list = state.getList();
        list.push_back(__expression.eval(state.getHashmap(), state.getHeap()));
        state.setList(list);
        return state;
    }

    @Override
    public iStatement copy() {
        return new PrintStatement(__expression);
    }

    @Override
    public String toString() {
        return "print(" + __expression.toString() + ")";
    }
}
