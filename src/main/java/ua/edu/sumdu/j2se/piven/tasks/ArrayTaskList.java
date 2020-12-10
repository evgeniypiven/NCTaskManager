package ua.edu.sumdu.j2se.piven.tasks;

import java.util.Iterator;
import java.util.stream.Stream;

public class ArrayTaskList extends AbstractTaskList implements Iterable<Task> {
    private Task[] tasks;
    private int task_size = 10;

    public ArrayTaskList() {
        this.tasks = new Task[task_size];
    }

    public Task getTask(int index) throws IndexOutOfBoundsException {
        return tasks[index];
    }

    public int size() {
        int count = 0;
        for (Task task : tasks)
            if (task != null)
                ++count;
        return count;
    }

    public void add(Task task) {
        if (size() == task_size) {
            // add size to tasks array, if it doesnt enough
            task_size = task_size + 10;
            Task[] tmp_tasks = new Task[task_size];
            System.arraycopy(tasks, 0, tmp_tasks, 0, tasks.length);
            tasks = tmp_tasks;
        }
        tasks[size()] = task;
    }

    public boolean remove(Task task) {
        for (int i = 0; i < tasks.length; i++) {
            if (tasks[i] == task) {
                tasks[i] = null;
                for (int j = i; j < tasks.length; j++) {
                    if (j == tasks.length - 1) {
                        // deduct tasks size, if it`s too much for new quantity
                        if (size() - 1 % 10 == 0) {
                            task_size = task_size - 10;
                            Task[] tmp_tasks = new Task[task_size];
                            System.arraycopy(tasks, 0, tmp_tasks, 0, tasks.length);
                            tasks = tmp_tasks;
                        }
                        return true;
                    }
                    tasks[j] = tasks[j + 1];
                }

            }
        }
        return false;
    }

    @Override
    public Stream<Task> getStream() {
        return Stream.of(tasks);
    }

    @Override
    public Iterator<Task> iterator() {
        return new ArrayListIterator(this);
    }

}
