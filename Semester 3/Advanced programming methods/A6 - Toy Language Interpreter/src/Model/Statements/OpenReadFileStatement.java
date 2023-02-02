package Model.Statements;

import Exceptions.DataStructureException;
import Exceptions.ExpressionException;
import Exceptions.StatementException;
import Model.DataStructures.iHashMap;
import Model.Expressions.iExpression;
import Model.ProgramState.ProgramState;
import Model.Types.StringType;
import Model.Types.iType;
import Model.Values.StringValue;
import Model.Values.iValue;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class OpenReadFileStatement implements iStatement {

    private final iExpression __exp;

    public OpenReadFileStatement(iExpression exp) {
        __exp = exp;
    }
    @Override
    public ProgramState execute(ProgramState state) throws ExpressionException, DataStructureException, StatementException {
        iValue expValue = __exp.eval(state.getHashmap(), state.getHeap());
        iHashMap<String, BufferedReader> fileTable = state.getFilesHashmap();

        if(!expValue.getType().equals(new StringType())) {
            throw new StatementException("STMT-ERROR: Type of expression: " + __exp.toString() + " is not StringType.");
        }

        StringValue fileName = (StringValue)expValue;

        try {
            fileTable.find(fileName.get());
            throw new StatementException("STMT-ERROR: File " + fileName.get() + "is already opened.");
        }
        catch(DataStructureException ignored) {}

        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader(fileName.get()));
        }
        catch(FileNotFoundException e) {
            throw new StatementException("STMT-ERROR: Couldn't open file: " + fileName.get() + ".");
        }

        fileTable.insert(fileName.get(), reader);
        state.setFilesHashmap(fileTable);

        return null;
    }

    @Override
    public iHashMap<String, iType> typeCheck(iHashMap<String, iType> typeEnvironment) throws StatementException, DataStructureException, ExpressionException {
        if(!__exp.typeCheck(typeEnvironment).equals(new StringType()))
            throw new StatementException("Opening a file requires a string which represents the path of the file.");
        return typeEnvironment;
    }

    @Override
    public iStatement copy() {
        return new OpenReadFileStatement(__exp);
    }

    @Override
    public String toString() {
        return "OpenReadFile(" + __exp.toString() + ")";
    }
}
