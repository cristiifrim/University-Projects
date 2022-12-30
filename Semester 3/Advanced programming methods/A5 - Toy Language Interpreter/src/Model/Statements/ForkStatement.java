package Model.Statements;

import Exceptions.DataStructureException;
import Exceptions.ExpressionException;
import Exceptions.StatementException;
import Model.DataStructures.iHashMap;
import Model.DataStructures.iStack;
import Model.DataStructures.szHashMap;
import Model.ProgramState.ProgramState;
import Model.Values.iValue;

public class ForkStatement implements iStatement {

    private iStatement __statement;


    public ForkStatement(iStatement statement) {
        __statement = statement;
    }

    @Override
    public ProgramState execute(ProgramState state) throws ExpressionException, DataStructureException, StatementException {
        iStack<iStatement> stack = state.getStack();
        stack.push(__statement);

        iHashMap<String, iValue> hashmap = new szHashMap<>();
        state.getHashmap().getData().forEach(
                (key, value) -> hashmap.insert(key, value.copy())
        );

        return new ProgramState(stack, hashmap, state.getList(), state.getFilesHashmap(), state.getHeap());
    }

    @Override
    public iStatement copy() {
        return new ForkStatement(__statement.copy());
    }

    @Override
    public String toString() {
        return String.format("Fork(%s)", __statement.toString());
    }
}
