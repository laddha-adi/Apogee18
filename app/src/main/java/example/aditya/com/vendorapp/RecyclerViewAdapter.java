package example.aditya.com.vendorapp;

import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder>  {

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
         TextView items;
        TextView otp;
        TextView status;
        ImageView imageView;
        RecyclerView list;
        CardView card;
        TextView total;
        TextView time;

        private MyViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
            this.title = itemView.findViewById(R.id.tv_recycler_item_1);
            this.items = itemView.findViewById(R.id.tv_recycler_item_3);
            this.otp = itemView.findViewById(R.id.tv_recycler_item_4);
             this.imageView = itemView.findViewById(R.id.imageView2);
            this.status = itemView.findViewById(R.id.status_tv);
            this.list = itemView.findViewById(R.id.list);
            this.card = itemView.findViewById(R.id.card_view_item_recycler_view);
            this.total = itemView.findViewById(R.id.order_total);
            this.time = itemView.findViewById(R.id.tv_time);
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
        holder.title.setText(mItems.get(position).getOrderId());
        holder.otp.setText(mItems.get(position).getUniqueId());

        String items = "";
        for(int i=0;i<mItems.get(position).getItemArrayList().size();i++){
            if(i!=0){ items = items+"\n";}

            items = items+" "+mItems.get(position).getItemArrayList().get(i).getQuantity()+"          "+ mItems.get(position).getItemArrayList().get(i).getName();
        }
        holder.items.setText(items);
        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, DetailsActivity.class);
                intent.putExtra("order_num",position);
                context.startActivity(intent);
            }
        });

        holder.card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, DetailsActivity.class);
                intent.putExtra("order_num",position);
                context.startActivity(intent);
            }
        });
        if(mItems.get(position).isReady()){
            holder.imageView.setImageResource(R.drawable.tick);
            holder.status.setText("Ready");
          //  holder.status.setTextColor(ContextCompat.getColor(context, R.color.green));
          //  holder.imageView.setImageTintList(ColorStateList.valueOf(ContextCompat.getColor(context, R.color.green)));
            holder.status.setTextColor(ContextCompat.getColor(context, R.color.blue));
            holder.imageView.setImageResource(R.drawable.ic_do_not_disturb_on_black_24dp);
            holder.imageView.setImageTintList(ColorStateList.valueOf(ContextCompat.getColor(context, R.color.blue)));
        }
        else{
          //  holder.imageView.setImageResource(R.drawable.dash);
            holder.status.setText("Pending");
            holder.status.setTextColor(ContextCompat.getColor(context, R.color.yellow));
            holder.imageView.setImageResource(R.drawable.ic_do_not_disturb_on_black_24dp);
            holder.imageView.setImageTintList(ColorStateList.valueOf(ContextCompat.getColor(context, R.color.yellow)));

        }
        holder.list.setLayoutManager(new LinearLayoutManager(context));
        holder.list.setAdapter(new DetailsListAdapter(context,mItems.get(position).getItemArrayList()));
        DividerItemDecoration mDividerItemDecoration = new DividerItemDecoration(
                holder.list.getContext(),
                DividerItemDecoration.VERTICAL
        );
        holder.list.addItemDecoration(mDividerItemDecoration);
        holder.total.setText(String.valueOf(mItems.get(position).getTotal()));
        holder.time.setText(mItems.get(position).getTime());

    }



    @Override
    public int getItemCount() {
       if(mItems!=null) return mItems.size();
       else return 0;
    }



}
