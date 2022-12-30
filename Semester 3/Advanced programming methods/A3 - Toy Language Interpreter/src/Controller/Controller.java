package Controller;

import Exceptions.DataStructureException;
import Exceptions.ExpressionException;
import Exceptions.StatementException;
import Model.DataStructures.iStack;
import Model.ProgramState.ProgramState;
import Model.Statements.iStatement;
import Repository.iRepository;

import java.io.IOException;

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
        }
    }

    public void display(ProgramState state) {
        if(__display) {
            System.out.println(state.toString());
        }
    }



}
