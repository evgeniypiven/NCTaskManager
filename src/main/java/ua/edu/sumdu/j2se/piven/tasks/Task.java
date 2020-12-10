package ua.edu.sumdu.j2se.piven.tasks;

public class Task {
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
        else if (time <= 0) {
            throw new IllegalArgumentException("Time has to be bigger than 0.");
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
        if (this.interval <= 0) {
            return this.time;
        }
        else {
            return this.start;
        }
    }

    /**
     *
     * @return task execution end time
     */
    public int getEndTime() {
        if (this.interval <= 0) {
            return this.time;
        }
        else {
            return this.end;
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
}
