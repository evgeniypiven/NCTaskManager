package ua.edu.sumdu.j2se.piven.tasks;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class ArrayListIterator implements Iterator<Task> {
    private ArrayTaskList array;
    private int index;
    private int lastRet;

    public ArrayListIterator(ArrayTaskList array)
    {
        setArray(array);
    }

    @Override
    public boolean hasNext() {
        return (index < array.size());
    }

    @Override
    public Task next() {
        if (!hasNext()) {
            throw new NoSuchElementException();
        }
        lastRet = index;
        return array.getTask(index++);
    }

    private void setArray(ArrayTaskList array) {
        this.array = array;
        this.index = 0;
        this.lastRet = -1;
    }

    public void remove()
    {
        if (lastRet < 0) {
            throw new IllegalStateException();
        } else {
            array.remove(array.getTask(lastRet));
            index = lastRet;
            lastRet = -1;
        }
    }
}
