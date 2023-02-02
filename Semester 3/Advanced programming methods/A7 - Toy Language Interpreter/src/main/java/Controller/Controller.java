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
import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Controller {

    iRepository __repo;
    boolean __display;

    private ExecutorService __executor;

    public Controller(iRepository repo) {
        __repo = repo;
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

    public void conservativeGarbageCollector(List<ProgramState> programStates) {
        List<Integer> variablesTableAddresses = Objects.requireNonNull(programStates.stream()
                        .map(p -> getAddressFromVariablesTable(p.getHashmap().values()))
                        .map(Collection::stream)
                        .reduce(Stream::concat).orElse(null))
                .collect(Collectors.toList());
        programStates.forEach(p -> {
            p.getHeap().setData((HashMap<Integer, iValue>) safeGarbageCollector(variablesTableAddresses, getAddressFromMemoryHeap(p.getHeap().getData().values()), p.getHeap().getData()));
        });
    }

    public List<ProgramState> removeCompletedStates(List<ProgramState> inProgramList) {
        return inProgramList.stream().filter(prg -> !prg.isCompleted()).collect(Collectors.toList());
    }


    public boolean getDisplay() {
        return __display;
    }

    public void setDisplay(boolean T) {
        __display = T;
    }

    private void runProgramStatesSequentially(List<ProgramState> programStates) throws DataStructureException, StatementException, ExpressionException, IOException, InterruptedException {
        programStates.forEach(
                prg -> {
                    try {
                        __repo.logProgramStateExecution(prg);
                        display(prg);
                    } catch (DataStructureException | IOException e) {
                        System.out.println(e.getMessage());
                    }
                }
        );

        List<Callable<ProgramState>> callList = programStates.stream().map(
                (ProgramState p) -> (Callable<ProgramState>) (
                        p::runProgramSequentially)).toList();

        List<ProgramState> newProgramStates = __executor.invokeAll(callList).stream().map(
                future -> {
                    try {
                        return future.get();
                    } catch (ExecutionException | InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
        ).filter(Objects::nonNull).toList();

        programStates.addAll(newProgramStates);

        programStates.forEach(
                prg -> {
                    try {
                        __repo.logProgramStateExecution(prg);
                        display(prg);
                    } catch (DataStructureException | IOException e) {
                        System.out.println(e.getMessage());
                    }
                }
        );

        __repo.setStates(programStates);

    }

    public void runProgramSequentially() throws DataStructureException, StatementException, ExpressionException, IOException, InterruptedException {
        __executor = Executors.newFixedThreadPool(2);

        List<ProgramState> programList = removeCompletedStates(__repo.getStates());

        runProgramStatesSequentially(programList);
        conservativeGarbageCollector(programList);

        __executor.shutdownNow();
        __repo.setStates(programList);
    }

    public void runProgram() throws DataStructureException, StatementException, ExpressionException, IOException, InterruptedException {
        __executor = Executors.newFixedThreadPool(2);

        List<ProgramState> programList = removeCompletedStates(__repo.getStates());

        while(!programList.isEmpty()) {
            conservativeGarbageCollector(programList);
            runProgramStatesSequentially(programList);
            programList = removeCompletedStates(__repo.getStates());
        }

        __executor.shutdownNow();
        __repo.setStates(programList);
    }

    public void display(ProgramState state) {
        if(__display) {
            System.out.println(state.toString());
        }
    }

    public List<ProgramState> getStates() throws DataStructureException {
        return __repo.getStates();
    }

    public void setStates(List<ProgramState> states) {
        __repo.setStates(states);
    }



}
