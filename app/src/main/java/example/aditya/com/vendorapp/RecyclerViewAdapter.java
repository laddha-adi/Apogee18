package example.aditya.com.vendorapp;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> implements onMoveAndSwipedListener {

    private Context context;
    private ArrayList<Order> mItems;
     private View parentView;


    public RecyclerViewAdapter(Context context,ArrayList<Order> mItems) {
        this.context = context;
        this.mItems = new ArrayList();
        this.mItems = mItems;
    }

    public void setItems(ArrayList<Order> data) {
          this.mItems = data;
        notifyDataSetChanged();
    }

    public void addItem(int position, Order insertData) {
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
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        holder.title.setText(mItems.get(position).getUniqueId());
        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, DetailsActivity.class);

                intent.putExtra("order_num",position);

                context.startActivity(intent);
            }
        });
    }



    @Override
    public int getItemCount() {
       if(mItems!=null) return mItems.size();
       else return 0;
    }


    @Override
    public boolean onItemMove(int fromPosition, int toPosition) {
        Collections.swap(mItems, fromPosition, toPosition);
        notifyItemMoved(fromPosition, toPosition);
        return true;
    }

    @Override
    public void onItemDismiss(final int position) {
        final Order data = mItems.get(position);

        mItems.remove(position);
        notifyItemRemoved(position);

        Snackbar.make(parentView, context.getString(R.string.item_swipe_dismissed), Snackbar.LENGTH_SHORT)
                .setAction(context.getString(R.string.item_swipe_undo), new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(context, "adding: "+data, Toast.LENGTH_SHORT).show();
                        addItem(position,data);
                    }
                }).show();
    }

}
