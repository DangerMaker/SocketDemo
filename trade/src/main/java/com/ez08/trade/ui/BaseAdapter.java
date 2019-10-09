package com.ez08.trade.ui;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

abstract public class BaseAdapter<T> extends RecyclerView.Adapter<BaseViewHolder> {
    protected List<T> mObjects;

    protected OnItemClickListener mItemClickListener;

    public interface ItemView {
        View onCreateView(ViewGroup parent);

        void onBindView(View headerView);
    }

    private Context mContext;

    public BaseAdapter(Context context) {
        init(context, new ArrayList<T>());
    }

    /**
     * Constructor
     *
     * @param context The current context.
     * @param objects The objects to represent in the ListView.
     */
    public BaseAdapter(Context context, T[] objects) {
        init(context, Arrays.asList(objects));
    }

    /**
     * Constructor
     *
     * @param context The current context.
     * @param objects The objects to represent in the ListView.
     */
    public BaseAdapter(Context context, List<T> objects) {
        init(context, objects);
    }


    private void init(Context context, List<T> objects) {
        mContext = context;
        mObjects = new ArrayList<>(objects);
    }

    public void clear() {
        mObjects.clear();
        notifyDataSetChanged();
    }

    public void addAll(List<T> newList) {
        mObjects.addAll(newList);
        notifyDataSetChanged();
    }

    public void clearAndAddAll(List<T> newList) {
        mObjects.clear();
        mObjects.addAll(newList);
        notifyDataSetChanged();
    }

    public int getCount() {
        return mObjects.size();
    }

    public List<T> getAllData() {
        return mObjects;
    }

    public T getItem(int position) {
        return mObjects.get(position);
    }

    @Override
    public final int getItemCount() {
        return mObjects.size();
    }

    @Override
    public int getItemViewType(int position) {
        return getViewType(position);
    }

    public int getViewType(int position) {
        return 0;
    }

    @Override
    public final BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final BaseViewHolder viewHolder = OnCreateViewHolder(parent, viewType);
        if (mItemClickListener != null) {
            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mItemClickListener.onItemClick(viewHolder.getAdapterPosition());
                }
            });
        }
        return viewHolder;
    }

    abstract public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType);


    @Override
    public final void onBindViewHolder(BaseViewHolder holder, int position) {
        holder.itemView.setId(position);
        OnBindViewHolder(holder, position);
    }


    public void OnBindViewHolder(BaseViewHolder holder, final int position) {
        holder.setData(getItem(position));
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mItemClickListener = listener;
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }
}