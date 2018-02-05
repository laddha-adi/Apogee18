package example.aditya.com.vendorapp;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> implements onMoveAndSwipedListener {

    private Context context;
    private List<String> mItems;
    private int color = 0;
    private View parentView;
    private final int TYPE_NORMAL = 1;
    private final int TYPE_FOOTER = 2;
    private final String FOOTER = "footer";

    public RecyclerViewAdapter(Context context) {
        this.context = context;
        mItems = new ArrayList();
    }

    public void setItems(List<String> data) {
          this.mItems = data;
        notifyDataSetChanged();
    }

    public void addItem(int position, String insertData) {
        mItems.add(position,insertData);
        notifyItemInserted(position);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
         View mView;
         TextView title;

        private MyViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
            this.title = itemView.findViewById(R.id.tv_recycler_item_1);

        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        parentView = parent;

            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recycler_view, parent, false);
            return new MyViewHolder(view);

    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        holder.title.setText(mItems.get(position));
        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, DetailsActivity.class);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemViewType(int position) {
        String s = mItems.get(position);
        if (s.equals(FOOTER)) {
            return TYPE_FOOTER;
        } else {
            return TYPE_NORMAL;
        }
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }


    @Override
    public boolean onItemMove(int fromPosition, int toPosition) {
        Collections.swap(mItems, fromPosition, toPosition);
        notifyItemMoved(fromPosition, toPosition);
        return true;
    }

    @Override
    public void onItemDismiss(final int position) {
        final String data = mItems.get(position);
       // Toast.makeText(context, "removed"+ data, Toast.LENGTH_SHORT).show();
        mItems.remove(position);
        notifyItemRemoved(position);

        Snackbar.make(parentView, context.getString(R.string.item_swipe_dismissed), Snackbar.LENGTH_SHORT)
                .setAction(context.getString(R.string.item_swipe_undo), new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                       // Toast.makeText(context, "adding: "+data, Toast.LENGTH_SHORT).show();
                        addItem(position,data );
                    }
                }).show();
    }





}
