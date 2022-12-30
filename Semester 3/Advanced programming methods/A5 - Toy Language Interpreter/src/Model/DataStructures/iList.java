package Model.DataStructures;

import Exceptions.DataStructureException;

import java.util.List;

public interface iList<T> {

    void push_back(T Elem);
    T pop_back() throws DataStructureException;
    T pop_front() throws DataStructureException;
    boolean empty();
    List<T> get();

}
