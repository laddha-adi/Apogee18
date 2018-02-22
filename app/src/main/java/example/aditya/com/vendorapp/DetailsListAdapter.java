package example.aditya.com.vendorapp;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by aditya on 2/8/2018.
 */

public class DetailsListAdapter extends RecyclerView.Adapter<DetailsListAdapter.MyViewHolder> {

    private Context context;
    private ArrayList<Item> mItems;
     private View parentView;

    public DetailsListAdapter(Context context,ArrayList<Item> mItems) {
        this.context = context;
        this.mItems = new ArrayList();
        this.mItems = mItems;
    }

   public class MyViewHolder extends RecyclerView.ViewHolder {
        View mView;
        TextView name;
        TextView quan;
        TextView price;

        private MyViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
            this.name = itemView.findViewById(R.id.tv_name);
            this.quan = itemView.findViewById(R.id.tv_quantity);
            this.price = itemView.findViewById(R.id.tv_price);

        }
    }

    @Override
    public DetailsListAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        parentView = parent;

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_detail_view, parent, false);
        return new DetailsListAdapter.MyViewHolder(view);



    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.name.setText(mItems.get(position).getName());
        holder.quan.setText(String.valueOf(mItems.get(position).getQuantity()));
        holder.price.setText(String.valueOf(mItems.get(position).getPrice()));

    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

}
