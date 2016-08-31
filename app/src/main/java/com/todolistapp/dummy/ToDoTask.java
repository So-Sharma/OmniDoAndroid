package com.todolistapp.dummy;

public class ToDoTask {
    public final String id;
    public final String title;
    public boolean selected;

    public ToDoTask(String id, String name, boolean selected) {
        this.id = id;
        this.title = name;
        this.selected = selected;
    }

    @Override
    public String toString() {
        return title;
    }
}
