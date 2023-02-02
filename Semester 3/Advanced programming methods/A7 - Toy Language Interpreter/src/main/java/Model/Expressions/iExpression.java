package Model.Expressions;

import Exceptions.DataStructureException;
import Exceptions.ExpressionException;
import Exceptions.StatementException;
import Model.DataStructures.iHashMap;
import Model.DataStructures.iHeap;
import Model.Values.iValue;
import Model.Types.iType;

public interface iExpression {

    iValue eval(iHashMap<String, iValue> variablesTable, iHeap memoryHeap) throws ExpressionException, DataStructureException, StatementException;
    iType typeCheck(iHashMap<String, iType> typeEnvironment) throws ExpressionException, DataStructureException;
    iExpression copy();


}
