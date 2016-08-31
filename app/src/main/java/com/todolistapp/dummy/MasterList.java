package com.todolistapp.dummy;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MasterList {
    private static final String MasterListName = "master_list";

    private static MasterList masterList;

    private static Object lock = new Object();

    public Map<String, ToDoList> toDoListMap;

    public static MasterList getInstance() {

        synchronized (lock) {
            if (masterList == null) {
                masterList = new MasterList();
            }
        }

        return masterList;
    }

    public MasterList() {
        toDoListMap = new HashMap<>();
    }

    public void addItem(ToDoList item) {
        toDoListMap.put(item.id, item);
    }

    public void Save(Context context) {
        Gson gson = new Gson();
        String serializedMap = gson.toJson(this);
        SharedPreferences preferences = context.getSharedPreferences(MasterListName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(MasterListName, serializedMap);
        editor.commit();
    }

    public void Load(Context context) {
        SharedPreferences pref = context.getSharedPreferences(MasterListName, Context.MODE_PRIVATE);
        String serializedMap = pref.getString(MasterListName, null);

        if (serializedMap == null) {
            return;
        }

        Gson gson = new Gson();
        this.toDoListMap = gson.fromJson(serializedMap, this.getClass()).toDoListMap;
    }

    public List<ToDoList> GetLists() {
        return new ArrayList<>(toDoListMap.values());
    }
}

