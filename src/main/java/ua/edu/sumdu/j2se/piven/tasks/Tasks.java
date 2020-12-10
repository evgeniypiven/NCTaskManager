package ua.edu.sumdu.j2se.piven.tasks;

import java.time.LocalDateTime;
import java.util.*;

public class Tasks {
    public static Iterable<Task> incoming(Iterable<Task> tasks, LocalDateTime start, LocalDateTime end) {

        ArrayTaskList arrayTaskList = new ArrayTaskList();

        for (Task task : tasks) {
            if (task.getStartTime().isAfter(start) && (task.getEndTime().isBefore(end) || task.getEndTime().isEqual(end)) && task.isActive()) {
                arrayTaskList.add(task);
            }
            else {
                if (task.isRepeated() && task.isActive()) {
                    LocalDateTime prevTime = task.getStartTime();
                    for (LocalDateTime i = task.getStartTime(); prevTime.isBefore(task.getEndTime()) || prevTime.isEqual(task.getEndTime()); i = i.plusSeconds(task.getRepeatInterval())) {
                        if(((prevTime.isAfter(start) && prevTime.isBefore(end)) || (prevTime.isEqual(end)) ) && i.isAfter(start)) {
                            arrayTaskList.add(task);
                            break;
                        }
                        prevTime = i;
                    }
                }
            }

        }

        return arrayTaskList;
    }

    public static SortedMap<LocalDateTime, Set<Task>> calendar(Iterable<Task> tasks, LocalDateTime start, LocalDateTime end) {
        SortedMap<LocalDateTime, Set<Task>> map = new TreeMap<LocalDateTime, Set<Task>>();

        for (Task task : tasks) {
            if (task.isRepeated() && task.isActive()) {
                LocalDateTime prevTime = task.getStartTime();
                for (LocalDateTime i = task.getStartTime(); prevTime.isBefore(task.getEndTime()) || prevTime.isEqual(task.getEndTime()); i = i.plusSeconds(task.getRepeatInterval())) {
                    if ((prevTime.isAfter(start) && prevTime.isBefore(end)) || prevTime.isEqual(end)) {
                        if (map.containsKey(prevTime)) {
                            Set<Task> initSet = map.get(prevTime);
                            initSet.add(task);
                            map.put(prevTime, initSet);
                        }
                        else {
                            Set<Task> initSet = new HashSet<>(Collections.singletonList(task));
                            map.put(prevTime, initSet);
                        }
                    }
                    prevTime = i;
                }
            }
            else {
                if (task.isActive()) {
                    if (map.containsKey(task.getStartTime())) {
                        Set<Task> initSet = new HashSet<Task>();
                        initSet.add(task);
                        map.put(task.getTime(), initSet);
                    }
                    else {
                        Set<Task> initSet = map.get(task.getStartTime());
                        initSet.add(task);
                        map.put(task.getStartTime(), initSet);
                    }
                }
            }
        }

        return map;
    }
}
