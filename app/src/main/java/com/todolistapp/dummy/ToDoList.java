package com.todolistapp.dummy;

import java.util.ArrayList;
import java.util.List;

public class ToDoList {
    public String id;
    public String title;
    private List<ToDoTask> toDoTasks = new ArrayList<>();

    public ToDoList(String id, String title, ArrayList items) {
        this.id = id;
        this.title = title;
        this.toDoTasks = items;
    }

    public void AddTask(String title) {
        toDoTasks.add(new ToDoTask("0", title, false));
    }

    public String GetTaskTitle(int position) {
        return toDoTasks.get(position).title;
    }

    public int GetTaskCount() {
        return toDoTasks.size();
    }

    public int GetCompletedTaskCount() {
        int count = 0;

        for (ToDoTask task : toDoTasks) {
            if (task.selected) {
                count++;
            }
        }

        return count;
    }

    public boolean GetTaskChecked(int position) {
        return toDoTasks.get(position).selected;
    }
}
