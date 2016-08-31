package com.todolistapp;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.todolistapp.dummy.MasterList;
import com.todolistapp.dummy.ToDoList;
import com.todolistapp.dummy.ToDoTask;

/**
 * A fragment representing a single Todo List detail screen.
 * This fragment is either contained in a {@link TodoMasterListActivity}
 * in two-pane mode (on tablets) or a {@link TodoListDetailActivity}
 * on handsets.
 */
public class TodoListDetailFragment extends Fragment {

    public static final String ARG_ITEM_ID = "item_id";

    private ToDoList toDoList;

    public TodoListDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (!getArguments().containsKey(ARG_ITEM_ID)) {
            return;
        }

        toDoList = MasterList.getInstance().toDoListMap.get(getArguments().getString(ARG_ITEM_ID));

        Activity activity = this.getActivity();
        CollapsingToolbarLayout appBarLayout = (CollapsingToolbarLayout) activity.findViewById(R.id.toolbar_layout);
        if (appBarLayout != null) {
            appBarLayout.setTitle(getTitle());
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.todo_list_detail, container, false);

        View recyclerView = rootView.findViewById(R.id.todo_list_detail);
        setupRecyclerView((RecyclerView) recyclerView);

        return rootView;
    }

    @NonNull
    private String getTitle() {
        return toDoList.title;
    }

    private void setupRecyclerView(@NonNull RecyclerView recyclerView) {
        recyclerView.setAdapter(new SimpleItemRecyclerViewAdapter(toDoList));
    }

    public class SimpleItemRecyclerViewAdapter
            extends RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder> {

        private final ToDoList toDoList;

        public SimpleItemRecyclerViewAdapter(ToDoList toDoList) {
            this.toDoList = toDoList;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.todo_list_detail_content, parent, false);

            CheckBox checkBox = (CheckBox) view.findViewById(R.id.checkBox);
            checkBox.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    final CheckBox checkbox = (CheckBox) v;

                    toDoList.GetTask((String) checkbox.getTag()).selected = checkbox.isChecked();
                    MasterList.getInstance().Save(getActivity().getApplicationContext());
                }
            });

            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, int position) {
            holder.name.setText(toDoList.GetTaskTitle(position));

            holder.checkBox.setTag(toDoList.GetTaskId(position));
            holder.checkBox.setChecked(toDoList.GetTaskChecked(position));
        }

        @Override
        public int getItemCount() {
            return toDoList.GetTaskCount();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            public final View mView;
            public final TextView name;
            public final CheckBox checkBox;

            public ToDoTask mTask;

            public ViewHolder(View view) {
                super(view);
                mView = view;
                name = (TextView) view.findViewById(R.id.name);
                checkBox = (CheckBox) view.findViewById(R.id.checkBox);
            }

            @Override
            public String toString() {
                return super.toString() + " '" + name.getText() + "'";
            }
        }
    }
}
