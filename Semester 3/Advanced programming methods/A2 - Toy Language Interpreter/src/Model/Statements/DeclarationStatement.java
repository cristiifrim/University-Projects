package Model.Statements;

import Exceptions.DataStructureException;
import Exceptions.ExpressionException;
import Exceptions.StatementException;
import Model.ProgramState.ProgramState;

import Model.Types.iType;
import Model.Values.iValue;
import Model.DataStructures.iHashMap;

public class DeclarationStatement implements iStatement {

    String __name;
    iType __type;

    public DeclarationStatement(String name, iType type) {
        __name = name;
        __type = type;
    }

    @Override
    public ProgramState execute(ProgramState state) throws ExpressionException, DataStructureException, StatementException {
        iHashMap<String, iValue> hashmap = state.getHashmap();
        try {
            iValue x = hashmap.find(__name);
            throw new StatementException("STMT-ERROR: Variable " + __name + "is already declared.");
        }
        catch(DataStructureException e) {
            hashmap.insert(__name, __type._default());
            state.setHashmap(hashmap);
            return state;
        }
    }

    @Override
    public iStatement copy() {
        return new DeclarationStatement(__name, __type);
    }

    @Override
    public String toString() {
        return __type.toString() + " " + __name;
    }
}
