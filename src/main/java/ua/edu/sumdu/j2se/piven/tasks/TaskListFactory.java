package ua.edu.sumdu.j2se.piven.tasks;


public final class TaskListFactory {
    public static Object createTaskList(ListTypes.types type) {
        switch (type) {
            case ARRAY:
                return new ArrayTaskList();
            case LINKED:
                return new LinkedTaskList();
        }
        return null;
    }
}
