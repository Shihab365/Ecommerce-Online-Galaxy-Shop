package com.greenshopap.bel.greenshop_eca;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.Picasso;

public class ViewHolder_item extends RecyclerView.ViewHolder {

    View mview;
    public ViewHolder_item(View itemView) {
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
    public void setDetails(Context context, String images, String brand, String model, String price)
    {
        ImageView item_img=mview.findViewById(R.id.item_img_id);
        TextView item_brand=mview.findViewById(R.id.item_brand_id);
        TextView item_model=mview.findViewById(R.id.item_model_id);
        TextView item_price=mview.findViewById(R.id.item_price_id);

        Picasso.get().load(images).into(item_img);
        item_brand.setText(brand);
        item_model.setText(model);
        item_price.setText(price);
    }

    private ViewHolder_item.ClickListener mClickListener;

    public interface ClickListener
    {
        void onItemClick(View view,int position);
        void onItemLongClick(View view,int position);
    }

    public void setOnClickListener(ViewHolder_item.ClickListener clickListener)
    {
        mClickListener=clickListener;
    }
}
