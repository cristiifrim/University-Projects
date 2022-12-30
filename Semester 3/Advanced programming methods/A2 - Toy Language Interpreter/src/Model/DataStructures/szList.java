package Model.DataStructures;

import Exceptions.DataStructureException;

import java.util.List;
import java.util.ArrayList;

public class szList<T> implements iList<T> {

    List<T> __list;

    public szList() {
        __list = new ArrayList<>();
    }

    @Override
    public void push_back(T Elem) {
        __list.add(Elem);
    }

    @Override
    public T pop_front() throws DataStructureException {
        if(__list.isEmpty())
            throw new DataStructureException("DS-ERROR: List is empty.");

        return __list.remove(0);
    }

    @Override
    public T pop_back() throws DataStructureException {
        if(__list.isEmpty())
            throw new DataStructureException("DS-ERROR: List is empty.");

        return __list.remove(__list.size() - 1);
    }

    @Override
    public boolean empty() {
        return __list.isEmpty();
    }

    @Override
    public List<T> get() {
        return __list;
    }

    @Override
    public String toString() {
        return __list.toString();
    }
}
