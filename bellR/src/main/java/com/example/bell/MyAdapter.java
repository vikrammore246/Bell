package com.example.bell;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

/**
 * Created by vikram on 20/2/18.
 */

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

    private List<ListItem> listItems;
    private Context context;

    public MyAdapter(List<ListItem> listItems, Context context) {
        this.listItems = listItems;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ListItem listItem = listItems.get(position);

        holder.textViewHead.setText(listItem.getHead());
        holder.textViewDate.setText(listItem.getDate());
        holder.textViewTime.setText(listItem.getTime());
    }

    @Override
    public int getItemCount() {
        return listItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView textViewHead,textViewDate,textViewTime;
        public LinearLayout cardLinearLayout;

        public ViewHolder(View itemView) {
            super(itemView);

            textViewHead = (TextView)itemView.findViewById(R.id.textViewHead);
            textViewDate = (TextView)itemView.findViewById(R.id.textViewDate);
            textViewTime = (TextView)itemView.findViewById(R.id.textViewTime);
            cardLinearLayout = (LinearLayout)itemView.findViewById(R.id.cardLinearLayout);

        }
    }

}
