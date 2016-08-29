package com.todolistapp;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.design.widget.CollapsingToolbarLayout;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.todolistapp.dummy.MasterList;
import com.todolistapp.dummy.ToDoItem;
import com.todolistapp.dummy.ToDoList;

import java.util.List;

/**
 * A fragment representing a single Todo List detail screen.
 * This fragment is either contained in a {@link TodoMasterListActivity}
 * in two-pane mode (on tablets) or a {@link TodoListDetailActivity}
 * on handsets.
 */
public class TodoListDetailFragment extends Fragment {
    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
    public static final String ARG_ITEM_ID = "item_id";

    /**
     * The dummy title this fragment is presenting.
     */
    private List<ToDoItem> mItem;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public TodoListDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey(ARG_ITEM_ID)) {
            // Load the dummy title specified by the fragment
            // arguments. In a real-world scenario, use a Loader
            // to load title from a title provider.
            ToDoList mList = MasterList.getInstance().ITEM_MAP.get(getArguments().getString(ARG_ITEM_ID));
            mItem = mList.ITEMS;

            Activity activity = this.getActivity();
            CollapsingToolbarLayout appBarLayout = (CollapsingToolbarLayout) activity.findViewById(R.id.toolbar_layout);
            if (appBarLayout != null) {
                StringBuilder s = getTitles();
                appBarLayout.setTitle(s);
            }
        }
    }

    @NonNull
    private StringBuilder getTitles() {
        StringBuilder s = new StringBuilder();
        for (ToDoItem item : mItem) {
            s.append(item.title);
            s.append("\n");
        }
        return s;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.todolist_detail, container, false);

        // Show the dummy title as text in a TextView.
        if (mItem != null) {
            ((TextView) rootView.findViewById(R.id.todolist_detail)).setText(getTitles());
        }

        return rootView;
    }
}
