package Model.Statements;

import Exceptions.DataStructureException;
import Exceptions.ExpressionException;
import Exceptions.StatementException;
import Model.ProgramState.ProgramState;

public interface iStatement {

    ProgramState execute(ProgramState state) throws ExpressionException, DataStructureException, StatementException;
    iStatement copy();
}
