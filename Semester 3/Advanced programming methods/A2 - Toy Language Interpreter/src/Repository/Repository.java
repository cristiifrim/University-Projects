package Repository;

import Exceptions.DataStructureException;
import Model.ProgramState.ProgramState;

import java.util.ArrayList;
import java.util.List;

public class Repository implements iRepository {

    List<ProgramState> __data;

    public Repository() {
        __data = new ArrayList<>();
    }

    @Override
    public ProgramState getCurrentState() throws DataStructureException {
        if(__data.isEmpty())
            throw new DataStructureException("DS-ERROR: Repository list is empty.");

        return __data.get(0);
    }

    @Override
    public void add(ProgramState T) {
        __data.add(T);
    }

    @Override
    public List<ProgramState> getStates() {
        return __data;
    }
}
