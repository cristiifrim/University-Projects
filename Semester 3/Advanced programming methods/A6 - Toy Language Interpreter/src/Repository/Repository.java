package Repository;

import Exceptions.DataStructureException;
import Model.ProgramState.ProgramState;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class Repository implements iRepository {

    List<ProgramState> __data;
    String __logFilePath;

    public Repository(String logFilePath) {
        __data = new ArrayList<>();
        __logFilePath = logFilePath;
    }

    @Override
    public void add(ProgramState T) {
        __data.add(T);
    }

    @Override
    public List<ProgramState> getStates() throws DataStructureException {
        if(__data.isEmpty())
            throw new DataStructureException("DS-ERROR: Repository list is empty.");
        return __data;
    }

    @Override
    public void setStates(List<ProgramState> states) {
        __data = states;
    }

    @Override
    public void logProgramStateExecution(ProgramState state) throws DataStructureException, IOException {
        PrintWriter logFile = new PrintWriter(
                              new BufferedWriter(
                              new FileWriter(__logFilePath, true)));

        logFile.println(state._toString());
        logFile.close();
    }
}
