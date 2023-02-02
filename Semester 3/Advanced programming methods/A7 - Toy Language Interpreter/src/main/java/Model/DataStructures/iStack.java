package Model.DataStructures;

import Exceptions.DataStructureException;
import Model.Statements.iStatement;

import java.util.List;
import java.util.Stack;

public interface iStack<T> {

    T pop() throws DataStructureException;
    void push(T elem);
    T top() throws DataStructureException;
    Stack<T> getStack();

    List<T> getList();

    boolean empty();
}
