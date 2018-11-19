package com.zcpure.foreign.trade.log;

import java.util.ArrayDeque;
import java.util.Deque;


public class TraceStack<T> {
    
    private final ThreadLocal<Deque<T>> stack = new ThreadLocal<>();

    public void push(T entry) {
        Deque<T> deque = stack.get();
        if(deque == null) {
            deque = new ArrayDeque<>();
            stack.set(deque);
        }
        deque.add(entry);
    }
    
    public T pop() {
        Deque<T> deque = stack.get();
        if(deque == null || deque.isEmpty()) {
            return null;
        }
        T entry = deque.pollLast();
        return entry;
    }
    
    public T last() {
        Deque<T> deque = stack.get();
        return deque == null || deque.isEmpty() ? null : deque.getLast();
    }
    
    public int size() {
        Deque<T> deque = stack.get();
        return deque == null ? 0 : deque.size();
    }
    
    public boolean isEmpty() {
        Deque<T> deque = stack.get();
        return deque == null || deque.isEmpty();
    }

    public T first() {
        Deque<T> deque = stack.get();
        return deque == null || deque.isEmpty() ? null : deque.getFirst();
    }

}
