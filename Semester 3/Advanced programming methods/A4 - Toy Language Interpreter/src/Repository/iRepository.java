package Repository;

import Exceptions.DataStructureException;
import Model.Statements.iStatement;
import Model.ProgramState.ProgramState;

import java.io.IOException;
import java.util.List;


public interface iRepository {

    ProgramState getCurrentState() throws DataStructureException;
    void add(ProgramState T);
    List<ProgramState> getStates();

    void logProgramStateExecution() throws DataStructureException, IOException;

}
