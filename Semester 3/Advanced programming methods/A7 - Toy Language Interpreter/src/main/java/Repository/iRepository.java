package Repository;

import Exceptions.DataStructureException;
import Model.Statements.iStatement;
import Model.ProgramState.ProgramState;

import java.io.IOException;
import java.util.List;


public interface iRepository {
    void add(ProgramState T);
    List<ProgramState> getStates() throws DataStructureException;
    void setStates(List<ProgramState> states);
    void logProgramStateExecution(ProgramState state) throws DataStructureException, IOException;

}
