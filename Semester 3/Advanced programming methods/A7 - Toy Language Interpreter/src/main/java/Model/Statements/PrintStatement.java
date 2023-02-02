package Model.Statements;

import Exceptions.DataStructureException;
import Exceptions.ExpressionException;
import Exceptions.StatementException;
import Model.DataStructures.iHashMap;
import Model.Expressions.iExpression;
import Model.ProgramState.ProgramState;
import Model.DataStructures.iList;
import Model.Types.iType;
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
        return null;
    }

    @Override
    public iHashMap<String, iType> typeCheck(iHashMap<String, iType> typeEnvironment) throws StatementException, DataStructureException, ExpressionException {
        __expression.typeCheck(typeEnvironment);
        return typeEnvironment;
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
