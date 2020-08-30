package com.greenshopap.bel.greenshop_eca;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.Picasso;

public class jfu_ViewHolder_item extends RecyclerView.ViewHolder {

    View mview;

    public jfu_ViewHolder_item(View itemView) {
        super(itemView);
        mview=itemView;

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mClickListener.onItemClick(v, getAdapterPosition());
            }
        });
        itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                mClickListener.onItemLongClick(v, getAdapterPosition());
                return true;
            }
        });
    }
    public void setDetails(Context context, String images, String model, String price)
    {
        ImageView item_img=mview.findViewById(R.id.jfu_img_id);
        TextView item_name=mview.findViewById(R.id.jfu_model_id);
        TextView item_status=mview.findViewById(R.id.jfu_price_id);

        Picasso.get().load(images).into(item_img);
        item_name.setText(model);
        item_status.setText(price);
    }
    private jfu_ViewHolder_item.ClickListener mClickListener;

    public interface ClickListener
    {
        void onItemClick(View view,int position);
        void onItemLongClick(View view,int position);
    }

    public void setOnClickListener(jfu_ViewHolder_item.ClickListener clickListener)
    {
        mClickListener=clickListener;
    }
}
