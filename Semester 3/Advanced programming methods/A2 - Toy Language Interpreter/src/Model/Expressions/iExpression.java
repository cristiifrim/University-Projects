package Model.Expressions;

import Exceptions.DataStructureException;
import Exceptions.ExpressionException;
import Exceptions.StatementException;
import Model.DataStructures.iHashMap;
import Model.Values.iValue;

public interface iExpression {

    iValue eval(iHashMap<String, iValue> variablesTable) throws ExpressionException, DataStructureException, StatementException;
    iExpression copy();

}
