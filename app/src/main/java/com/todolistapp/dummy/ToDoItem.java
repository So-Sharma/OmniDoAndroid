package com.todolistapp.dummy;

public class ToDoItem {
    public final String id;
    public final String title;
    public boolean selected;

    public ToDoItem(String id, String name, boolean selected) {
        this.id = id;
        this.title = name;
        //this.details = details;
        this.selected = selected;
    }

    @Override
    public String toString() {
        return title;
    }
}
