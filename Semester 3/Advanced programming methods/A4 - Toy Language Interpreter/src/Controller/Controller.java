package Controller;

import Exceptions.DataStructureException;
import Exceptions.ExpressionException;
import Exceptions.StatementException;
import Model.DataStructures.iStack;
import Model.ProgramState.ProgramState;
import Model.Statements.iStatement;
import Model.Values.ReferenceValue;
import Model.Values.iValue;
import Repository.iRepository;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Controller {

    iRepository __repo;
    boolean __display;

    public Controller(iRepository repo) {
        __repo = repo;
    }

    public ProgramState runProgramSequentially(ProgramState state) throws StatementException, ExpressionException {

        iStack<iStatement> stack = state.getStack();

        try {
            iStatement statement = stack.pop();
            return statement.execute(state);
        }
        catch(DataStructureException e) {
            throw new StatementException("STMT-ERROR: Couldn't execute the statement because there were no statements");
        }

    }

    public List<Integer> getAddressFromVariablesTable(Collection<iValue> symTableValues) {
        return symTableValues.stream()
                .filter(v -> v instanceof ReferenceValue)
                .map(v -> {ReferenceValue v1 = (ReferenceValue) v; return v1.getAddress();})
                .collect(Collectors.toList());
    }

    public List<Integer> getAddressFromMemoryHeap(Collection<iValue> heapValues) {
        return heapValues.stream()
                .filter(v -> v instanceof ReferenceValue)
                .map(v -> {ReferenceValue v1 = (ReferenceValue) v; return v1.getAddress();})
                .collect(Collectors.toList());
    }


    public Map<Integer, iValue> safeGarbageCollector(List<Integer> symTableAddr, List<Integer> heapAddr, Map<Integer, iValue> heap) {
        return heap.entrySet().stream()
                .filter(e -> ( symTableAddr.contains(e.getKey()) || heapAddr.contains(e.getKey())))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }


    public boolean getDisplay() {
        return __display;
    }

    public void setDisplay(boolean T) {
        __display = T;
    }

    public void runProgram() throws DataStructureException, StatementException, ExpressionException, IOException {
        ProgramState state = __repo.getCurrentState();
        __repo.logProgramStateExecution();
        display(state);
        while(!state.getStack().empty()) {
            runProgramSequentially(state);
            display(state);
            __repo.logProgramStateExecution();

            state.getHeap().setData((HashMap<Integer, iValue>) safeGarbageCollector(
                    getAddressFromVariablesTable(state.getHashmap().values()),
                    getAddressFromMemoryHeap(state.getHeap().getData().values()),
                    state.getHeap().getData()));

            __repo.logProgramStateExecution();
        }
    }

    public void display(ProgramState state) {
        if(__display) {
            System.out.println(state.toString());
        }
    }



}
