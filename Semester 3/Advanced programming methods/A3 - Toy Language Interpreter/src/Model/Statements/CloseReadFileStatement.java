package Model.Statements;

import Exceptions.DataStructureException;
import Exceptions.ExpressionException;
import Exceptions.StatementException;
import Model.DataStructures.iHashMap;
import Model.Expressions.iExpression;
import Model.ProgramState.ProgramState;
import Model.Types.StringType;
import Model.Values.StringValue;
import Model.Values.iValue;

import java.io.BufferedReader;
import java.io.IOException;

public class CloseReadFileStatement implements iStatement {

    private final iExpression __exp;

    public CloseReadFileStatement(iExpression exp) {
        __exp = exp;
    }

    @Override
    public ProgramState execute(ProgramState state) throws ExpressionException, DataStructureException, StatementException {
        iValue expValue = __exp.eval(state.getHashmap());
        iHashMap<String, BufferedReader> fileTable = state.getFilesHashmap();
        BufferedReader reader;

        if(!expValue.getType().equals(new StringType())) {
            throw new StatementException("STMT-ERROR: Type of expression: " + __exp.toString() + " is not StringType.");
        }

        StringValue fileName = (StringValue)expValue;

        try {
            reader = fileTable.find(fileName.get());
            reader.close();
            fileTable.erase(fileName.get());
        }
        catch(DataStructureException e) {
            throw new StatementException("STMT-ERROR: File " + fileName.get() + "is not opened.");
        }
        catch(IOException e) {
            throw new StatementException("STMT-ERROR: File " + fileName.get() + "couldn't be closed");
        }
        state.setFilesHashmap(fileTable);
        return state;
    }

    @Override
    public iStatement copy() {
        return new CloseReadFileStatement(__exp);
    }

    @Override
    public String toString() {
        return "CloseReadFile(" + __exp.toString() + ")";
    }
}
