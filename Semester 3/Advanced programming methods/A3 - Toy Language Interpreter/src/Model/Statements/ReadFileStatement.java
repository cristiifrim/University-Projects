package Model.Statements;

import Exceptions.DataStructureException;
import Exceptions.ExpressionException;
import Exceptions.StatementException;
import Model.DataStructures.iHashMap;
import Model.Expressions.iExpression;
import Model.ProgramState.ProgramState;
import Model.Types.IntType;
import Model.Types.StringType;
import Model.Values.IntValue;
import Model.Values.StringValue;
import Model.Values.iValue;

import java.io.BufferedReader;
import java.io.IOException;

public class ReadFileStatement implements iStatement {

    private final iExpression __exp;
    private final String __var;

    public ReadFileStatement(iExpression exp, String var) {
        __exp = exp;
        __var = var;
    }

    @Override
    public ProgramState execute(ProgramState state) throws ExpressionException, DataStructureException, StatementException {
        iHashMap<String, iValue> variablesTable = state.getHashmap();
        iHashMap<String, BufferedReader> filesTable = state.getFilesHashmap();
        iValue variableVal;
        iValue expVal = __exp.eval(variablesTable);
        BufferedReader reader;

        try {
            variableVal = variablesTable.find(__var);

            if(!variableVal.getType().equals(new IntType())) {
                throw new StatementException("STMT-ERROR: Variable " + __var + "is not of type int.");
            }
        }
        catch(DataStructureException e) {
            throw new StatementException("STMT-ERROR: Variable " + __var + "is not defined.");
        }

        if(!expVal.getType().equals(new StringType())) {
            throw new StatementException("STMT-ERROR: Type of expression: " + __exp.toString() + " is not StringType.");
        }
        StringValue val = (StringValue) expVal;
        try {
            reader = filesTable.find(val.get());
            String line = reader.readLine();

            if(line == null)
                line = "0";

            variablesTable.update(__var, new IntValue(Integer.parseInt(line)));
        }
        catch(DataStructureException e) {
            throw new StatementException("STMT-ERROR: File " + val.get() + "is not open.");
        }
        catch(IOException e) {
            throw new StatementException("STMT-ERROR: Couldn't read from file: " + val.get() + ".");
        }
        catch(NumberFormatException e) {
            throw new StatementException("STMT-ERROR: Couldn't convert from string to int");
        }

        return state;

    }

    @Override
    public iStatement copy() {
        return new ReadFileStatement(__exp, __var);
    }

    @Override
    public String toString() {
        return "ReadFile(" + __exp.toString() + ", " + __var + ")";
    }
}
