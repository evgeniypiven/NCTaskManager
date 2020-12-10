package ua.edu.sumdu.j2se.piven.tasks;

public abstract class AbstractTaskList {
    abstract int size();
    abstract Task getTask(int index);
    abstract void add(Task task);
    abstract boolean remove(Task task);

    public AbstractTaskList incoming(int from, int to) {
        if (size() > 0) {
            LinkedTaskList linkedTaskList =  new LinkedTaskList();
            for (int i = 0; i < size(); i++) {
                Task task = getTask(i);
                if (task.getStartTime() > from && task.getEndTime() < to && task.isActive()) {
                    linkedTaskList.add(task);
                }
            }
            return linkedTaskList;
        }
        else {
            throw new ArrayIndexOutOfBoundsException("Size of tasks array is 0.");
        }
    }
}
