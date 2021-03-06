package com.example.orvilleclarke.testfrag.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.orvilleclarke.testfrag.R;
import com.example.orvilleclarke.testfrag.fragments.ItemFragment.OnListFragmentInteractionListener;
import com.example.orvilleclarke.testfrag.fragments.ToDoListFragment;
//import com.example.orvilleclarke.testfrag.fragments.dummy.DummyContent.DummyItem;
import com.example.orvilleclarke.testfrag.models.ToDoItem;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link ToDoItem} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyToDoListRecyclerViewAdapter extends RecyclerView.Adapter<MyToDoListRecyclerViewAdapter.ViewHolder> {

    private final List<ToDoItem> mValues;
    private final ToDoListFragment.OnToDoItemFragmentInteractionListener mListener;

    public MyToDoListRecyclerViewAdapter(List<ToDoItem> items, ToDoListFragment.OnToDoItemFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
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
//        if(holder.mItem.getToDoPriority()=="LOW"){
//            holder.mContentView.setBackgroundColor(Integer.valueOf(R.color.colorLow));
//        }
//        else if(holder.mItem.getToDoPriority()=="MED"){
//            holder.mContentView.setBackgroundColor(Integer.valueOf(R.color.colorMed));
//        }
//        else if(holder.mItem.getToDoPriority()=="HIGH"){
//            holder.mContentView.set(Integer.valueOf(R.color.colorHigh));
//        }
        holder.mIdView.setText(String.valueOf(mValues.get(position).id));
        holder.mContentView.setText(mValues.get(position).name);

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    long position = Long.parseLong(holder.mIdView.getText().toString());
                    mListener.onListFragmentInteraction(position);
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
        public ToDoItem mItem;

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
