package ua.edu.sumdu.j2se.piven.tasks;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.time.LocalDateTime;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public abstract class AbstractTaskList implements Cloneable, Serializable {
    abstract int size();
    abstract Task getTask(int index);
    abstract void add(Task task);
    abstract boolean remove(Task task);

    public final AbstractTaskList incoming(LocalDateTime from, LocalDateTime to) throws NoSuchMethodException, IllegalAccessException, InstantiationException, InvocationTargetException {
        if (size() > 0) {
            AbstractTaskList taskList =  getClass().getDeclaredConstructor().newInstance();
            IntStream.range(0, size())
                    .mapToObj(this::getTask).forEach(task -> {
                        if (task.getStartTime().isAfter(from) && task.getEndTime().isBefore(to) && task.isActive()) {
                            taskList.add(task);
                    }});
            return taskList;
        }
        else {
            throw new ArrayIndexOutOfBoundsException("Size of tasks array is 0.");
        }
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        else if (o == null) {
            return false;
        }
        else if (!(o instanceof AbstractTaskList)) {
            return false;
        }
        AbstractTaskList other = (AbstractTaskList) o;
        int thisSize = this.size();
        int otherSize = other.size();
        if (thisSize == otherSize) {
            if (thisSize == 0) {
                return true;
            }
            for (int i = 0; i < thisSize; i++) {
                if (!this.getTask(i).equals(other.getTask(i))) {
                    return false;
                }
            }
            return true;
        }
        else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        int thisSize = this.size();
        int hashCode = 1;
        for (int i = 0; i < thisSize; i++) {
            hashCode = 31 * hashCode + (this.getTask(i) == null ? 0 : this.hashCode());
        }
        return hashCode;
    }

    @Override
    public String toString() {
        int thisSize = this.size();
        StringBuilder resultString = new StringBuilder(this.getClass().getName() + "with such tasks: \n");
        for (int i = 0; i < thisSize; i++) {
            resultString.append(this.getTask(i).toString());
        }
        return resultString.toString();
    }

    @Override
    public AbstractTaskList clone() throws CloneNotSupportedException {
        AbstractTaskList cloneObj = (AbstractTaskList)super.clone();
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
            objectOutputStream.writeObject(cloneObj);
            ByteArrayInputStream bais = new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
            ObjectInputStream objectInputStream = new ObjectInputStream(bais);
            return (AbstractTaskList) objectInputStream.readObject();
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public Stream<Task> getStream() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        AbstractTaskList taskList =  getClass().getDeclaredConstructor().newInstance();
        for (int i = 0; i < size(); i++) {
            Task task = getTask(i);
            taskList.add(task);
        }
        return taskList.getStream();
    }
}
