package com.todolistapp.dummy;

import java.util.ArrayList;
import java.util.List;

public class ToDoList {
    public String id;
    public String title;
    public List<ToDoItem> ITEMS = new ArrayList<ToDoItem>();
    //public static  Map<String, ToDoItem> ITEM_MAP = new HashMap<String, ToDoItem>();

    public ToDoList(String id, String title, ArrayList items) {
        this.id = id;
        this.title = title;
        this.ITEMS = items;
        //this.ITEM_MAP = item_map;
    }
}
