package ua.edu.sumdu.j2se.piven.tasks;

public class LinkedTaskList extends AbstractTaskList {
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

    private Node tail() {
        Node tail = this.head;

        while (tail.next != null) {
            tail = tail.next;
        }
        return tail;
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

    //public LinkedTaskList incoming(int from, int to) throws ArrayIndexOutOfBoundsException {
    //if (size() > 0) {
            //LinkedTaskList linkedTaskList =  new LinkedTaskList();
            //for (int i = 0; i < size(); i++) {
            //Task task = getTask(i);
            //if (task.getStartTime() > from && task.getEndTime() < to) {
            // linkedTaskList.add(task);
            //}
            //}
            //return linkedTaskList;
            //}
            //else {
            //throw new ArrayIndexOutOfBoundsException("Size of tasks array is 0.");
            //}
            //}

    private class Node {
        public Task task;
        public Node next;
        public Node(Task task) {
            this.task = task;
        }
    }
}
