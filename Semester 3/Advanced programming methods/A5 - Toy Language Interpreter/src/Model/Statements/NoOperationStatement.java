package Model.Statements;

import Exceptions.DataStructureException;
import Exceptions.ExpressionException;
import Exceptions.StatementException;
import Model.ProgramState.ProgramState;

public class NoOperationStatement implements iStatement {

    @Override
    public ProgramState execute(ProgramState state) throws ExpressionException, DataStructureException, StatementException {
        return null;
    }

    @Override
    public iStatement copy() {
        return new NoOperationStatement();
    }

    @Override
    public String toString() {
        return "No Operation Statement";
    }
}
