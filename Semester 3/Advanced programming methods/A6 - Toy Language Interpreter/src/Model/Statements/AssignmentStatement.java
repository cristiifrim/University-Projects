package Model.Statements;

import Exceptions.DataStructureException;
import Exceptions.ExpressionException;
import Exceptions.StatementException;
import Model.DataStructures.iHashMap;
import Model.DataStructures.iHeap;
import Model.Expressions.iExpression;
import Model.ProgramState.ProgramState;
import Model.Types.iType;
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
        iHeap heap = state.getHeap();

        try {
            iValue x = hashmap.find(__id);

            if(!x.getType().equals(__expression.eval(hashmap, heap).getType()))
                throw new StatementException("STMT-ERROR: Declared type of " + __id + " and the type of expression do not match.");

            hashmap.update(__id, __expression.eval(hashmap, heap));
            state.setHashmap(hashmap);

            return null;
        }
        catch(Exception e) {
            throw new StatementException("STMT-ERROR: Variable " + __id + "was not declared.");
        }
    }

    @Override
    public iHashMap<String, iType> typeCheck(iHashMap<String, iType> typeEnvironment) throws StatementException, DataStructureException, ExpressionException {
       iType typeVariable = typeEnvironment.find(__id);
       iType typeExpression = __expression.typeCheck(typeEnvironment);

       if(!typeVariable.equals(typeExpression))
           throw new StatementException("Assignment: Operands differ in type");
       return typeEnvironment;
    }

    @Override
    public iStatement copy() {
        return new AssignmentStatement(__id, __expression);
    }
}
