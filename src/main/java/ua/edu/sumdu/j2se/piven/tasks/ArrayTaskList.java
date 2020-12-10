package ua.edu.sumdu.j2se.piven.tasks;

public class ArrayTaskList {
    private Task[] tasks;
    private int task_size = 10;

    public ArrayTaskList() {
        this.tasks = new Task[task_size];
    }

    public Task getTask(int index) {
        return this.tasks[index];
    }

    public int size() {
        int count = 0;
        for (Task task : this.tasks)
            if (task != null)
                ++count;
        return count;
    }

    public void add(Task task) {
        if (this.size() == this.task_size) {
            // add size to tasks array, if it doesnt enough
            this.task_size = this.task_size + 10;
            Task[] tmp_tasks = new Task[task_size];
            System.arraycopy(this.tasks, 0, tmp_tasks, 0, this.tasks.length);
            this.tasks = tmp_tasks;
        }
        this.tasks[this.size()] = task;
    }

    public boolean remove(Task task) {
        for (int i = 0; i < this.tasks.length; i++) {
            if (this.tasks[i] == task) {
                this.tasks[i] = null;
                for (int j = i; j < this.tasks.length; j++) {
                    if (j == this.tasks.length - 1) {
                        // deduct tasks size, if it`s too much for new quantity
                        if (this.size() - 1 % 10 == 0) {
                            this.task_size = this.task_size - 10;
                            Task[] tmp_tasks = new Task[task_size];
                            System.arraycopy(this.tasks, 0, tmp_tasks, 0, this.tasks.length);
                            this.tasks = tmp_tasks;
                        }
                        return true;
                    }
                    this.tasks[j] = this.tasks[j + 1];
                }

            }
        }
        return false;
    }

    public ArrayTaskList incoming(int from, int to) throws ArrayIndexOutOfBoundsException {
        if (this.size() > 0) {
            Task[] tmp_tasks = new Task[this.task_size];
            int counter = 0;
            for (int i = 0; i < this.size(); i++) {
                if (this.tasks[i].getStartTime() > from && this.tasks[i].getEndTime() < to && this.tasks[i].isActive()) {
                    tmp_tasks[counter] = this.tasks[i];
                    counter++;
                }
            }
            ArrayTaskList temp_arr_task_list = new ArrayTaskList();
            temp_arr_task_list.tasks = tmp_tasks;
            return temp_arr_task_list;
        }
        else {
            throw new ArrayIndexOutOfBoundsException("Size of tasks array is 0.");
        }
    }
}
