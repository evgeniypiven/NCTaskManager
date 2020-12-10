package ua.edu.sumdu.j2se.piven.tasks;

import java.io.*;
import java.util.Objects;

public class Task implements Cloneable, Serializable {
    private String title;
    private int time;
    private int start = -1;
    private int end = -1;
    private int interval = -1;
    private boolean active;
    private boolean repeated;

    /**
     *
     * @param title
     * @param time
     * @throws IllegalArgumentException
     */
    public Task(String title, int time) throws IllegalArgumentException {
        if (title.length() == 0) {
            throw new IllegalArgumentException("Title length has to be more than 0 characters.");
        }
        else if (time < 0) {
            throw new IllegalArgumentException("Time has to be positive number.");
        }
        this.title = title;
        this.time = time;
    }

    /**
     *
     * @param title
     * @param start
     * @param end
     * @param interval
     * @throws IllegalArgumentException
     */
    public Task(String title,  int start, int end, int interval) throws IllegalArgumentException {
        if (start > end) {
            throw new IllegalArgumentException("Start time is bigger than end.");
        }
        else if (start == end) {
            throw new IllegalArgumentException("Task doesn`t able to start.");
        }
        else if (interval <= 0) {
            throw new IllegalArgumentException("Interval has to be bigger than 0.");
        }
        this.title = title;
        this.repeated = true;
        setTime(start, end, interval);
    }

    /**
     *
     * @return task title
     */
    public String getTitle() {
        return title;
    }

    /**
     *
     * @param title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     *
     * @return active status
     */
    public boolean isActive() {
        return active;
    }

    /**
     *
     * @param active
     */
    public void setActive(boolean active) {
        this.active = active;
    }

    /**
     *
     * @return time, if task is not repeated. Else, task start execution time
     */
    public int getTime() {
        if (this.interval > 0) {
            return this.start;
        }
        else {
            return this.time;
        }

    }

    /**
     *
     * @param time
     */
    public void setTime(int time) {
        this.time = time;
        this.repeated = false;
        if (this.interval > 0) {
            this.interval = -1;
        }
    }

    /**
     *
     * @return task execution start time
     */
    public int getStartTime() {
        if (this.interval > 0) {
            return this.start;
        }
        else {
            return this.time;
        }
    }

    /**
     *
     * @return task end execution time
     */
    public int getEndTime() {
        if (this.interval > 0) {
            return this.end;
        }
        else {
            return this.time;
        }
    }

    /**
     *
     * @return repeat interval, if interval more than 0, else 0
     */
    public int getRepeatInterval() {
        return Math.max(this.interval, 0);
    }

    /**
     *
     * @return repeated
     */
    public boolean isRepeated() {
        return repeated;
    }

    /**
     *
     * @param start
     * @param end
     * @param interval
     */
    public void setTime(int start, int end, int interval) {
        this.time = 0;
        this.start = start;
        this.end = end;
        this.interval = interval;
        this.repeated = true;
    }

    /**
     *
     * @param current
     * @return next execution time after current time
     */
    public int nextTimeAfter(int current) {
        if (!this.active) {
            return -1;
        }
        else if (this.time > 0) {
            if (current >= this.time) {
                return -1;
            }
            else {
                return this.time;
            }
        }
        else {
            if (current < this.start) {
                return this.start;
            }
            else if (current > this.end) {
                return -1;
            }
            else {
                int prevTime = this.start;
                for (int i = this.start; i < this.end; i += this.interval) {
                    if(prevTime <= current && i > current) {
                        return i;
                    }
                    prevTime = i;
                }
            }
            return -1;
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
        else if (!(o instanceof Task)) {
            return false;
        }
        Task other = (Task) o;
        if (this.getRepeatInterval() > 0 && other.getRepeatInterval() > 0) {
            boolean titleEquals = this.getTitle().equals(other.getTitle());
            boolean activeEquals = this.isActive() == other.isActive();
            boolean startEquals = this.getStartTime() == other.getStartTime();
            boolean endEquals = this.getEndTime() == other.getEndTime();
            boolean intervalEquals = this.getRepeatInterval() == other.getRepeatInterval();
            return titleEquals && activeEquals && startEquals && endEquals && intervalEquals;
        }
        else if (this.getRepeatInterval() <= 0 && other.getRepeatInterval() <= 0) {
            boolean titleEquals = this.getTitle().equals(other.getTitle());
            boolean activeEquals = this.isActive() == other.isActive();
            boolean timeEquals = this.getTime() == other.getTime();
            return titleEquals && activeEquals && timeEquals;
        }
        else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        if (this.getRepeatInterval() > 0) {
            return Objects.hash(active, start, end, interval);
        }
        else {
            return Objects.hash(active, time);
        }
    }

    public String toString() {
        if (this.getRepeatInterval() > 0) {
            return "The " + (active ? "active" : "non-active") +
                    " repeatable task - '" + title + "' with start time "
                    + String.valueOf(start) + ", end time "
                    + String.valueOf(end) + " and interval "
                    + String.valueOf(interval) + "." + "\n";
        }
        else {
            return "The " + (active ? "active" : "non-active") +
                    " non-repeatable task - '" + title + "' with time "
                    + String.valueOf(time) + "." + "\n";
        }
    }

    public Task clone() throws CloneNotSupportedException {
        return (Task)super.clone();
    }
}
