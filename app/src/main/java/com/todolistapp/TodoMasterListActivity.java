package com.todolistapp;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.todolistapp.dummy.MasterList;
import com.todolistapp.dummy.ToDoList;

import java.util.ArrayList;
import java.util.List;

/**
 * An activity representing a list of Todo Lists. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a {@link TodoListDetailActivity} representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */
public class TodoMasterListActivity extends AppCompatActivity {
    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private boolean mTwoPane;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo_master_list);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Dialog box to add a new List
                final EditText taskEditText = new EditText(TodoMasterListActivity.this);

                AlertDialog dialog = new AlertDialog.Builder(TodoMasterListActivity.this)
                        .setTitle("Add a new to-do list")
                        .setView(taskEditText)
                        .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String listName = String.valueOf(taskEditText.getText());
                                ToDoList item = new ToDoList(java.util.UUID.randomUUID().toString(), listName, new ArrayList());

                                MasterList.getInstance().addItem(item);
                                MasterList.getInstance().Save(getApplicationContext());

                                View recyclerView = findViewById(R.id.todo_master_list);
                                setupRecyclerView((RecyclerView) recyclerView);
                            }
                        })
                        .setNegativeButton("Cancel", null)
                        .create();
                dialog.show();
            }
        });

        // Load data
        MasterList.getInstance().Load(getApplicationContext());

        View recyclerView = findViewById(R.id.todo_master_list);
        setupRecyclerView((RecyclerView) recyclerView);

        if (findViewById(R.id.todolist_detail_container) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-w900dp).
            // If this view is present, then the
            // activity should be in two-pane mode.
            mTwoPane = true;
        }
    }

    private void setupRecyclerView(@NonNull RecyclerView recyclerView) {
        recyclerView.setAdapter(new SimpleItemRecyclerViewAdapter(MasterList.getInstance().GetLists()));
    }

    public class SimpleItemRecyclerViewAdapter
            extends RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder> {

        private final List<ToDoList> toDoLists;

        public SimpleItemRecyclerViewAdapter(List<ToDoList> items) {
            toDoLists = items;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.todo_master_list_content, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, int position) {
            holder.mItem = toDoLists.get(position);
            holder.mContentView.setText(toDoLists.get(position).title);

            String description;

            int completedTaskCount = toDoLists.get(position).GetCompletedTaskCount();
            int totalTaskCount = toDoLists.get(position).GetTaskCount();

            if (totalTaskCount == 0) {
                description = String.format("No tasks found!");
            } else if (completedTaskCount == totalTaskCount) {
                description = String.format("Yay! All tasks (%1s) completed.", totalTaskCount);
            } else {
                description = String.format(
                        "%1$s/%2$s tasks completed.",
                        completedTaskCount,
                        totalTaskCount);
            }

            holder.descriptionView.setText(description);
            holder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mTwoPane) {
                        Bundle arguments = new Bundle();
                        arguments.putString(TodoListDetailFragment.ARG_ITEM_ID, holder.mItem.id);
                        TodoListDetailFragment fragment = new TodoListDetailFragment();
                        fragment.setArguments(arguments);
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.todolist_detail_container, fragment)
                                .commit();
                    } else {
                        Context context = v.getContext();
                        Intent intent = new Intent(context, TodoListDetailActivity.class);
                        intent.putExtra(TodoListDetailFragment.ARG_ITEM_ID, holder.mItem.id);

                        context.startActivity(intent);
                    }
                }
            });
        }

        @Override
        public int getItemCount() {
            return toDoLists.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            public final View mView;
            public final TextView mContentView;
            public final TextView descriptionView;

            public ToDoList mItem;

            public ViewHolder(View view) {
                super(view);
                mView = view;
                mContentView = (TextView) view.findViewById(R.id.name);
                descriptionView = (TextView) view.findViewById(R.id.description);
            }

            @Override
            public String toString() {
                return super.toString() + " '" + mContentView.getText() + "'";
            }
        }
    }
}
