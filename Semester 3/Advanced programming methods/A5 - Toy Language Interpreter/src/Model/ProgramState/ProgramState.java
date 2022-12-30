package Model.ProgramState;

import Exceptions.DataStructureException;
import Exceptions.ExpressionException;
import Exceptions.StatementException;
import Model.DataStructures.iList;
import Model.DataStructures.iStack;
import Model.DataStructures.iHashMap;
import Model.DataStructures.iHeap;

import Model.Statements.iStatement;
import Model.Values.iValue;

import java.io.BufferedReader;
import java.util.List;


public class ProgramState {
    private iStack<iStatement> __executionStack;
    private iHashMap<String, iValue> __variablesTable;
    private iList<iValue> __outputList;
    private iStatement __originalProgram;
    private iHashMap<String, BufferedReader> __filesTable;

    private int __stateID;

    private static int statesCount = 0;

    private iHeap __memoryHeap;


    public ProgramState(iStack<iStatement> executionStack, iHashMap<String, iValue> variablesTable, iList<iValue> outputList,
                        iHashMap<String, BufferedReader> filesTable, iHeap memoryHeap, iStatement originalProgram) {
        __executionStack = executionStack;
        __variablesTable = variablesTable;
        __outputList = outputList;
        __filesTable = filesTable;
        __memoryHeap = memoryHeap;
        __originalProgram = originalProgram.copy();
        __executionStack.push(originalProgram);
        __stateID = setStateID();
    }

    public ProgramState(iStack<iStatement> executionStack, iHashMap<String, iValue> variablesTable, iList<iValue> outputList,
                               iHashMap<String, BufferedReader> filesTable, iHeap memoryHeap) {
        __executionStack = executionStack;
        __variablesTable = variablesTable;
        __outputList = outputList;
        __filesTable = filesTable;
        __memoryHeap = memoryHeap;
        __stateID = setStateID();
    }

    public iStack<iStatement> getStack() {
        return __executionStack;
    }

    public iHashMap<String, iValue> getHashmap() {
        return __variablesTable;
    }

    public iList<iValue> getList() {
        return __outputList;
    }

    public iStatement getProgram() {
        return __originalProgram;
    }

    public void setStack(iStack<iStatement> T) {
        __executionStack = T;
    }

    public void setHashmap(iHashMap<String, iValue> T) {
        __variablesTable = T;
    }

    public iHeap getHeap() {
        return __memoryHeap;
    }

    public void setHeap(iHeap memoryHeap) {
        __memoryHeap = memoryHeap;
    }

    public void setList(iList<iValue> T) {
        __outputList = T;
    }

    public void setProgram(iStatement T) {
        __originalProgram = T;
    }

    public iHashMap<String, BufferedReader> getFilesHashmap() {
        return __filesTable;
    }

    public void setFilesHashmap(iHashMap<String, BufferedReader> filesTable) {
        __filesTable = filesTable;
    }

    public boolean isCompleted() {
        return __executionStack.empty();
    }

    private static synchronized int setStateID() {
        return ++statesCount;
    }

    public int getStateID() {
        return __stateID;
    }

    public ProgramState runProgramSequentially() throws StatementException, ExpressionException {
        try {
            iStatement statement = __executionStack.pop();
            return statement.execute(this);
        }
        catch(DataStructureException e) {
            throw new StatementException("STMT-ERROR: Couldn't execute the statement because there were no statements");
        }

    }


    public String executionStackString() {
        StringBuilder string = new StringBuilder();
        List<iStatement> stack = __executionStack.getList();
        for (iStatement statement: stack) {
            string.append(statement.toString()).append("\n");
        }
        return string.toString();
    }

    public String variablesTableString() throws DataStructureException {
        StringBuilder string = new StringBuilder();
        for (String key: __variablesTable.getKeys()) {
            string.append(String.format("%s -> %s\n", key, __variablesTable.find(key).toString()));
        }
        return string.toString();
    }

    public String outputListString() {
        StringBuilder string = new StringBuilder();
        for (iValue elem: __outputList.get()) {
            string.append(String.format("%s\n", elem.toString()));
        }
        return string.toString();
    }

    public String fileTableToString() {
        StringBuilder string = new StringBuilder();
        for (String key: __filesTable.getKeys()) {
            string.append(String.format("%s\n", key));
        }
        return string.toString();
    }

    public String memoryHeapToString() throws DataStructureException {
        StringBuilder string = new StringBuilder();
        for (int key: __memoryHeap.keySet()) {
            string.append(String.format("%d -> %s\n", key, __memoryHeap.get(key)));
        }
        return string.toString();
    }

    @Override
    public String toString() {
        return "State #" + __stateID + "\nExecution stack: \n" + __executionStack.toString() + "\nSymbol table: \n" + __variablesTable.toString() + "\nOutput list: \n" + __outputList.toString() + "\nFiles table: \n" + __filesTable.toString()
                +"\nHeap memory: \n" + __memoryHeap.toString();
    }

    public String _toString() throws DataStructureException {
        return "State #" + __stateID + "\nExecution stack: \n" + executionStackString() + "\nSymbol table: \n" + variablesTableString() + "\nOutput list: \n" + outputListString() + "\nFiles table: \n" + fileTableToString()
                +"\nHeap memory: \n" + memoryHeapToString();
    }
}
