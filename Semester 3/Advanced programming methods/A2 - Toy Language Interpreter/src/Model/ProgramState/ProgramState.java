package Model.ProgramState;

import Model.DataStructures.iList;
import Model.DataStructures.iStack;
import Model.DataStructures.iHashMap;

import Model.Statements.iStatement;
import Model.Values.iValue;


public class ProgramState {
    private iStack<iStatement> __executionStack;
    private iHashMap<String, iValue> __variablesTable;
    private iList<iValue> __outputList;
    private iStatement __originalProgram;


    public ProgramState(iStack<iStatement> executionStack, iHashMap<String, iValue> variablesTable, iList<iValue> outputList, iStatement originalProgram) {
        __executionStack = executionStack;
        __variablesTable = variablesTable;
        __outputList = outputList;
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

    @Override
    public String toString() {
        return "Execution stack: \n" + __executionStack.toString() + "\nSymbol table: \n" + __variablesTable.toString() + "\nOutput list: \n" + __outputList.toString();
    }
}
