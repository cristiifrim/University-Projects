package Model.DataStructures;

import Exceptions.DataStructureException;
import Model.Values.iValue;

import java.util.HashMap;
import java.util.Set;

public interface iHeap {
    int newValue();
    HashMap<Integer, iValue> getData();
    void setData(HashMap<Integer, iValue> newMap);
    int add(iValue value);
    void update(Integer position, iValue value) throws DataStructureException;
    iValue get(Integer position) throws DataStructureException;
    boolean keyExists(Integer position);
    void remove(Integer key) throws DataStructureException;
    Set<Integer> keySet();
}