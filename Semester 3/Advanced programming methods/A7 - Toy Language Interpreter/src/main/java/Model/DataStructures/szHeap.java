package Model.DataStructures;

import Exceptions.DataStructureException;
import Model.Values.iValue;

import java.util.HashMap;
import java.util.Set;

public class szHeap implements iHeap {

    private int __emptyMemoryAddress;
    private HashMap<Integer, iValue> __heap;


    private void updateMemoryAddress() {
        __emptyMemoryAddress = 1;
        while (__heap.containsKey(__emptyMemoryAddress))
            __emptyMemoryAddress += 1;
    }


    public szHeap() {
        __emptyMemoryAddress = 1;
        __heap = new HashMap<>();
    }

    @Override
    public int newValue() {
        return __emptyMemoryAddress;
    }

    @Override
    public HashMap<Integer, iValue> getData() {
        return __heap;
    }

    @Override
    public void setData(HashMap<Integer, iValue> newMap) {
        __heap = newMap;
    }

    @Override
    public int add(iValue value) {
        __heap.put(__emptyMemoryAddress, value);
        int  reservedAdress = __emptyMemoryAddress;
        updateMemoryAddress();
        return reservedAdress;
    }

    @Override
    public void update(Integer position, iValue value) throws DataStructureException {
        if(!__heap.containsKey(position))
            throw new DataStructureException(String.format("Address %d was not allocated", position));
        __heap.put(position, value);
    }

    @Override
    public iValue get(Integer position) throws DataStructureException {
        if(!__heap.containsKey(position))
            throw new DataStructureException(String.format("Address %d was not allocated", position));
        return __heap.get(position);
    }

    @Override
    public boolean keyExists(Integer position) {
        return __heap.containsKey(position);
    }

    @Override
    public void remove(Integer position) throws DataStructureException {
        if(!__heap.containsKey(position))
            throw new DataStructureException(String.format("Address %d was not allocated", position));

        __emptyMemoryAddress = position;
        __heap.remove(position);
    }

    @Override
    public Set<Integer> keySet() {
        return __heap.keySet();
    }

    @Override
    public String toString() {
        return __heap.toString();
    }
}
