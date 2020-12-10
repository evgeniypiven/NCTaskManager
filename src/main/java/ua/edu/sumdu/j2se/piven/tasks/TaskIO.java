package ua.edu.sumdu.j2se.piven.tasks;

import com.google.gson.Gson;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.TimeZone;

public class TaskIO {
    public static void write(AbstractTaskList tasks, OutputStream out) throws IOException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        DataOutputStream outStream = new DataOutputStream(out);
        outStream.writeInt(tasks.size());
        tasks.getStream().forEach(task -> {
            String title = task.getTitle();
            try {
                outStream.writeInt(title.length());
                outStream.writeUTF(title);
                outStream.writeBoolean(task.isActive());
                outStream.writeInt(task.getRepeatInterval());
                if (task.isRepeated()) {
                    outStream.writeLong(task.getStartTime().atZone(ZoneId.systemDefault())
                            .toInstant().toEpochMilli());
                    outStream.writeLong(task.getEndTime().atZone(ZoneId.systemDefault())
                            .toInstant().toEpochMilli());
                }
                else {
                    outStream.writeLong(task.getTime().atZone(ZoneId.systemDefault())
                            .toInstant().toEpochMilli());
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        outStream.flush();
    }

    public static void read(AbstractTaskList tasks, InputStream in) throws IOException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        DataInputStream inStream = new DataInputStream(in);
        int taskCount = inStream.readInt();
        for (int i = 0; i < taskCount; i++) {
            try {
                Task task;
                int titleLength = inStream.readInt();
                String title = inStream.readUTF();
                boolean isActive = inStream.readBoolean();
                int interval = inStream.readInt();
                if (interval > 0) {
                    LocalDateTime startTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(inStream.readLong()),
                            TimeZone.getDefault().toZoneId());
                    LocalDateTime endTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(inStream.readLong()),
                            TimeZone.getDefault().toZoneId());
                    task = new Task(title, startTime, endTime, interval);
                    task.setActive(isActive);
                }
                else {
                    LocalDateTime time = LocalDateTime.ofInstant(Instant.ofEpochMilli(inStream.readLong()),
                            TimeZone.getDefault().toZoneId());
                    task = new Task(title, time);
                    task.setActive(isActive);
                }
                tasks.add(task);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    public static void writeBinary(AbstractTaskList tasks, File file) throws IOException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        FileOutputStream fos = new FileOutputStream(file);
        write(tasks, fos);
        fos.flush();
    }

    public static void readBinary(AbstractTaskList tasks, File file) throws IOException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        FileInputStream fos = new FileInputStream(file);
        read(tasks, fos);
        fos.close();
    }

    public static void write(AbstractTaskList tasks, Writer out) throws IOException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        Gson gson = new Gson();
        ArrayTaskList tasksArray = new ArrayTaskList();
        tasks.getStream().forEach(tasksArray::add);
        gson.toJson(tasksArray, out);
        out.flush();
        out.close();
    }

    public static void read(AbstractTaskList tasks, Reader in) throws IOException {
        Gson gson = new Gson();
        ArrayTaskList taskArray = gson.fromJson(in, ArrayTaskList.class);
        for (Task task: taskArray) {
            tasks.add(task);
        }
    }

    public static void writeText(AbstractTaskList tasks, File file) throws IOException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        Gson gson = new Gson();
        ArrayTaskList tasksArray = new ArrayTaskList();
        tasks.getStream().forEach(tasksArray::add);
        gson.toJson(tasksArray, new FileWriter(file));
    }

    public static void readText(AbstractTaskList tasks, File file) throws IOException {
        Gson gson = new Gson();
        ArrayTaskList taskArray = gson.fromJson(new FileReader(file), ArrayTaskList.class);
        for (Task task: taskArray) {
            tasks.add(task);
        }
    }
}
