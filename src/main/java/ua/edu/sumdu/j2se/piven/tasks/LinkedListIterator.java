package ua.edu.sumdu.j2se.piven.tasks;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class LinkedListIterator implements Iterator<Task> {
    private Node current;
    private Node lastRet;
    private LinkedTaskList list;

    public LinkedListIterator(LinkedTaskList list) {
        this.current = list.getHead();
        this.lastRet = null;
        this.list = list;
    }

    @Override
    public boolean hasNext() {
        return current != null;
    }

    @Override
    public Task next() {
        if (!this.hasNext()) {
            throw new NoSuchElementException();
        }
        lastRet = current;
        current = current.next;
        return lastRet.task;
    }

    @Override
    public void remove() {
        if (lastRet == null) {
            throw new IllegalStateException();
        } else {
            list.remove(lastRet.task);
            lastRet = null;
        }
    }
}