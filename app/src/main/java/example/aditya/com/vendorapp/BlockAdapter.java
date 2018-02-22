package example.aditya.com.vendorapp;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import java.util.ArrayList;

import static example.aditya.com.vendorapp.BlockActivity.toggleInitiator;

public class BlockAdapter extends RecyclerView.Adapter<BlockAdapter.MyViewHolder> {

    private Context context;
    private ArrayList<MenuItems> mItems;


    public BlockAdapter(Context context, ArrayList<MenuItems> mItems) {
        this.context = context;
        this.mItems = new ArrayList();
        this.mItems = mItems;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
         View mView;
         TextView available;
         Button block;
        Button unblock;
        Switch aSwitch;


        private MyViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
            this.available = itemView.findViewById(R.id.check_box);
            this.block = itemView.findViewById(R.id.block);
            this.unblock = itemView.findViewById(R.id.unblock);
            this.aSwitch = itemView.findViewById(R.id.swtch);
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_block_view, parent, false);
            return new MyViewHolder(view);

    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        holder.available.setText(mItems.get(position).getName());
        holder.unblock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toggleInitiator(mItems.get(position).getId());

            }
        });
        holder.block.setEnabled(false);
        holder.aSwitch.setText(mItems.get(position).getName());
        holder.aSwitch.setChecked(true);
        holder.aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                toggleInitiator(mItems.get(position).getId());

            }
        });
        //holder.available.(mItems.get(position).isAvailable());
    }



    @Override
    public int getItemCount() {
       if(mItems!=null) return mItems.size();
       else return 0;
    }


}
