package com.todolistapp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.todolistapp.dummy.MasterList;
import com.todolistapp.dummy.ToDoItem;

/**
 * An activity representing a single Todo List detail screen. This
 * activity is only used narrow width devices. On tablet-size devices,
 * item details are presented side-by-side with a list of items
 * in a {@link TodoMasterListActivity}.
 */
public class TodoListDetailActivity extends AppCompatActivity {

    //TodoListDetailFragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todolist_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.detail_toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Snackbar.make(view, "Replace with your own detail action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();*/

                //Dialog box to add a new List
                final EditText taskEditText = new EditText(TodoListDetailActivity.this);
                AlertDialog dialog = new AlertDialog.Builder(TodoListDetailActivity.this)
                        .setTitle("Add a new item")
                        .setView(taskEditText)
                        .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String listItem = String.valueOf(taskEditText.getText());

                                // TODO: Saurabh
                                Bundle arguments = new Bundle();
                                String key = getIntent().getStringExtra(TodoListDetailFragment.ARG_ITEM_ID);

                                MasterList.getInstance().ITEM_MAP.get(key).ITEMS.add(new ToDoItem("0", listItem, false));
                                MasterList.getInstance().Save(getApplicationContext());

                                arguments.putString(TodoListDetailFragment.ARG_ITEM_ID, key);

                                TodoListDetailFragment fragment = new TodoListDetailFragment();

                                fragment.setArguments(arguments);

                                getSupportFragmentManager().beginTransaction()
                                        .replace(R.id.todolist_detail_container, fragment)
                                        .commit();

                                /*View recyclerView = findViewById(R.id.todolist_list);
                                assert recyclerView != null;
                                setupRecyclerView((RecyclerView) recyclerView);*/
                            }
                        })
                        .setNegativeButton("Cancel", null)
                        .create();
                dialog.show();
            }
        });

        // Show the Up button in the action bar.
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        // savedInstanceState is non-null when there is fragment state
        // saved from previous configurations of this activity
        // (e.g. when rotating the screen from portrait to landscape).
        // In this case, the fragment will automatically be re-added
        // to its container so we don't need to manually add it.
        // For more information, see the Fragments API guide at:
        //
        // http://developer.android.com/guide/components/fragments.html
        //
        if (savedInstanceState == null) {
            // Create the detail fragment and add it to the activity
            // using a fragment transaction.
            Bundle arguments = new Bundle();
            arguments.putString(TodoListDetailFragment.ARG_ITEM_ID,
                    getIntent().getStringExtra(TodoListDetailFragment.ARG_ITEM_ID));
            TodoListDetailFragment fragment = new TodoListDetailFragment();

            fragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.todolist_detail_container, fragment)
                    .commit();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            // This ID represents the Home or Up button. In the case of this
            // activity, the Up button is shown. Use NavUtils to allow users
            // to navigate up one level in the application structure. For
            // more details, see the Navigation pattern on Android Design:
            //
            // http://developer.android.com/design/patterns/navigation.html#up-vs-back
            //
            NavUtils.navigateUpTo(this, new Intent(this, TodoMasterListActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /*private void setupRecyclerView(@NonNull RecyclerView recyclerView) {
        recyclerView.setAdapter(new SimpleItemRecyclerViewAdapter(MasterList.ITEMS));
    }

    public class SimpleItemRecyclerViewAdapter
            extends RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder> {

        private final List<MasterList.ToDoItem> mValues;

        public SimpleItemRecyclerViewAdapter(List<MasterList.ToDoItem> items) {
            mValues = items;
        }
        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.todolist_list_content, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, int position) {
            holder.mItem = mValues.get(position);
            holder.mIdView.setText(mValues.get(position).id);
            holder.mContentView.setText(mValues.get(position).title);
        }
        @Override
        public int getItemCount() {
            return mValues.size();
        }
        public class ViewHolder extends RecyclerView.ViewHolder {
            public final View mView;
            public final TextView mIdView;
            public final TextView mContentView;
            public MasterList.ToDoItem mItem;

            public ViewHolder(View view) {
                super(view);
                mView = view;
                mIdView = (TextView) view.findViewById(R.id.id);
                mContentView = (TextView) view.findViewById(R.id.title);
            }

            @Override
            public String toString() {
                return super.toString() + " '" + mContentView.getText() + "'";
            }
        }

    }*/


}