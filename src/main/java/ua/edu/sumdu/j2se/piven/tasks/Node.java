package ua.edu.sumdu.j2se.piven.tasks;

import java.io.Serializable;

public class Node implements Serializable {
    public Task task;
    public Node next;
    public Node(Task task) {
        this.task = task;
    }
}
