package Model.Statements;

import Exceptions.DataStructureException;
import Exceptions.ExpressionException;
import Exceptions.StatementException;
import Model.DataStructures.iHashMap;
import Model.Expressions.iExpression;
import Model.ProgramState.ProgramState;
import Model.Values.iValue;

public class AssignmentStatement implements iStatement {

    String __id;
    iExpression __expression;

    public AssignmentStatement(String id, iExpression expression) {
        __id = id;
        __expression = expression;
    }

    @Override
    public String toString() {
        return __id + " = " + __expression.toString();
    }

    @Override
    public ProgramState execute(ProgramState state) throws ExpressionException, DataStructureException, StatementException {
        iHashMap<String, iValue> hashmap = state.getHashmap();

        try {
            iValue x = hashmap.find(__id);

            if(!x.getType().equals(__expression.eval(hashmap).getType()))
                throw new StatementException("STMT-ERROR: Declared type of " + __id + " and the type of expression do not match.");

            hashmap.update(__id, __expression.eval(hashmap));
            state.setHashmap(hashmap);

            return state;
        }
        catch(Exception e) {
            throw new StatementException("STMT-ERROR: Variable " + __id + "was not declared.");
        }
    }

    @Override
    public iStatement copy() {
        return new AssignmentStatement(__id, __expression);
    }
}
