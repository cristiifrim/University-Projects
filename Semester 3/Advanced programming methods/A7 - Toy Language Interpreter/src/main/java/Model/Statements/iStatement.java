package Model.Statements;

import Exceptions.DataStructureException;
import Exceptions.ExpressionException;
import Exceptions.StatementException;
import Model.DataStructures.iHashMap;
import Model.ProgramState.ProgramState;
import Model.Types.iType;

public interface iStatement {

    ProgramState execute(ProgramState state) throws ExpressionException, DataStructureException, StatementException;
    iHashMap<String, iType> typeCheck(iHashMap<String, iType> typeEnvironment) throws StatementException, DataStructureException, ExpressionException;
    iStatement copy();
}
