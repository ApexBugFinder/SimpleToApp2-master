package com.example.orvilleclarke.testfrag.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.orvilleclarke.testfrag.R;
import com.example.orvilleclarke.testfrag.fragments.TodoListsFragment;
import com.example.orvilleclarke.testfrag.fragments.TodoListsFragment.OnListTodoListFragmentInteractionListener;
import com.example.orvilleclarke.testfrag.models.ToDoList;

import java.util.List;

//import com.example.orvilleclarke.testfrag.fragments.dummy.DummyContent.DummyItem;

/**
 * {@link RecyclerView.Adapter} that can display a {@link ToDoList} and makes a call to the
 * specified {@link OnListTodoListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyTodoListsRecyclerViewAdapter extends RecyclerView.Adapter<MyTodoListsRecyclerViewAdapter.ViewHolder> {

    private final List<ToDoList> mValues;
    private final OnListTodoListFragmentInteractionListener mListener;

    public MyTodoListsRecyclerViewAdapter(List<ToDoList> items, TodoListsFragment.OnListTodoListFragmentInteractionListener listener) {
        mValues = items;

        mListener =  listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {


        holder.mItem = mValues.get(position);
        holder.mIdView.setText(String.valueOf(mValues.get(position).id));
        holder.mContentView.setText(mValues.get(position).title);

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    String t  =String.valueOf(holder.mItem.id);

                    mListener.onListTodoListFragmentInteraction(holder.mItem.id);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mIdView;
        public final TextView mContentView;
        public ToDoList mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mIdView = (TextView) view.findViewById(R.id.id);
            mContentView = (TextView) view.findViewById(R.id.content);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }
}
