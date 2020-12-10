package ua.edu.sumdu.j2se.piven.tasks;

import java.io.Serializable;
import java.util.Iterator;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class LinkedTaskList extends AbstractTaskList implements Iterable<Task> {
    private int size;
    private Node head;

    public int size() {
        return size;
    }

    public Task getTask(int index) throws IndexOutOfBoundsException {
        for (Node current = head; current != null; current = current.next, index--) if (index == 0) return current.task;
        throw new IndexOutOfBoundsException();
    }

    public void add(Task task) {
        size++;
        if (head == null) {
            head = new Node(task);
            return;
        }
        tail().next = new Node(task);
    }

    public Node tail() {
        Node tail = this.head;

        while (tail.next != null) {
            tail = tail.next;
        }
        return tail;
    }

    public Node getHead()
    {
        return head;
    }

    public boolean remove(Task task) {
        if (head.task.equals(task)) {
            head = head.next;
            size--;
            return true;
        }
        for (Node current = head; current != null; current = current.next) {
            if (current.next.task.equals(task)) {
                current.next = current.next.next;
                size--;
                return true;
            }
        }
        return false;
    }

    @Override
    public Iterator<Task> iterator() {
        return new LinkedListIterator(this);
    }

    @Override
    public Stream<Task> getStream() {
        Task[] tasks = new Task[size()];
        IntStream.range(0, size())
                .forEach(index -> {
                    tasks[index] = getTask(index);
                });
        return Stream.of(tasks);
    }
}
