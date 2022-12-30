package Model.DataStructures;

import Exceptions.DataStructureException;

import java.util.Stack;

public interface iStack<T> {

    T pop() throws DataStructureException;
    void push(T elem);
    T top() throws DataStructureException;
    Stack<T> getStack();

    boolean empty();
}
