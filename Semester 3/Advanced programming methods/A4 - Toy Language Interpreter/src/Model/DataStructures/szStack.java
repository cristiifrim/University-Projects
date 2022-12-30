package Model.DataStructures;

import Exceptions.DataStructureException;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

public class szStack<T> implements iStack<T> {

    Stack<T> __stack;

    public szStack() {
        this.__stack = new Stack<>();
    }

    @Override
    public T pop() throws DataStructureException {
        if(__stack.empty())
            throw new DataStructureException("DS-ERROR: Stack is empty.");

        return __stack.pop();
    }

    @Override
    public void push(T elem) {
        __stack.push(elem);
    }

    @Override
    public T top() throws DataStructureException {
        if(__stack.empty())
            throw new DataStructureException("DS-ERROR: Stack is empty.");

        return __stack.peek();
    }

    @Override
    public Stack<T> getStack() {
        return __stack;
    }

    @Override
    public boolean empty() {
        return __stack.isEmpty();
    }

    @Override
    public List<T> getList() {
        List<T> list =  Arrays.asList((T[]) __stack.toArray());
        Collections.reverse(list);
        return list;
    }

    @Override
    public String toString() {
        return __stack.toString();
    }
}
