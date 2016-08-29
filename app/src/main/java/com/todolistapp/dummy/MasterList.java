package com.todolistapp.dummy;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Helper class for providing sample title for user interfaces created by
 * Android template wizards.
 * <p/>
 * TODO: Replace all uses of this class before publishing your app.
 */

public class MasterList {
    //public static final List<ToDoList> ITEMS = new ArrayList<ToDoList>();
    public Map<String, ToDoList> ITEM_MAP;

    private static final int COUNT = 0;
    public static final String MASTER_LIST = "master_list";

    private static MasterList masterList;

    public static MasterList getInstance() {
        if (masterList == null) {
            // TODO: Lock
            masterList = new MasterList();
        }

        return masterList;
    }

    public MasterList() {
        ITEM_MAP = new HashMap<String, ToDoList>();
    }

    public void addItem(ToDoList item) {
        //ITEMS.add(item);
        ITEM_MAP.put(item.id, item);
    }

    public void Save(Context context) {
        Gson gson = new Gson();
        String serializedMap = gson.toJson(this);
        SharedPreferences preferences = context.getSharedPreferences(MASTER_LIST, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(MASTER_LIST, serializedMap);
        editor.commit();
    }

    public void Load(Context context) {
        SharedPreferences pref = context.getSharedPreferences(MASTER_LIST, Context.MODE_PRIVATE);
        String serializedMap = pref.getString(MASTER_LIST, null);

        if (serializedMap == null) {
            return;
        }

        Gson gson = new Gson();
        this.ITEM_MAP = gson.fromJson(serializedMap, this.getClass()).ITEM_MAP;
    }

    private static ToDoList createDummyItem(int position) {
        List aList = new ArrayList();
        ToDoItem d1 = new ToDoItem("1", "title", true);
        ToDoItem d2 = new ToDoItem("2", "title", false);
        aList.add(d1);
        aList.add(d2);
        return new ToDoList(String.valueOf(position), "Item " + position, (ArrayList) aList);
    }

    public List<ToDoList> GetList() {

        ArrayList<ToDoList> items = new ArrayList<ToDoList>();

        int count = 0;

        for (ToDoList todoList : ITEM_MAP.values()) {
            items.add(count++, todoList);
        }

        return items;
    }
}

