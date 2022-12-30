package Model.ProgramState;

import Exceptions.DataStructureException;
import Model.DataStructures.iList;
import Model.DataStructures.iStack;
import Model.DataStructures.iHashMap;

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


    public ProgramState(iStack<iStatement> executionStack, iHashMap<String, iValue> variablesTable, iList<iValue> outputList,
                        iHashMap<String, BufferedReader> filesTable, iStatement originalProgram) {
        __executionStack = executionStack;
        __variablesTable = variablesTable;
        __outputList = outputList;
        __filesTable = filesTable;
        __originalProgram = originalProgram.copy();
        __executionStack.push(originalProgram);
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

    @Override
    public String toString() {
        return "Execution stack: \n" + __executionStack.toString() + "\nSymbol table: \n" + __variablesTable.toString() + "\nOutput list: \n" + __outputList.toString() + "\nFiles table: \n" + __filesTable.toString();
    }

    public String _toString() throws DataStructureException {
        return "Execution stack: \n" + executionStackString() + "\nSymbol table: \n" + variablesTableString() + "\nOutput list: \n" + outputListString() + "\nFiles table: \n" + fileTableToString();
    }
}
