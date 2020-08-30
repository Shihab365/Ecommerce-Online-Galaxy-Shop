package com.greenshopap.bel.greenshop_eca;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.Picasso;

public class CartViewHolder extends RecyclerView.ViewHolder {

    TextView model_text,price_text,quantity_text;
    ImageView imageView;
    View mView;

    public CartViewHolder(View itemView) {
        super(itemView);
        mView=itemView;

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
    public void setDetails(Context context, String model, String price, String quantity)
    {
        model_text=mView.findViewById(R.id.cart_name_id);
        price_text=mView.findViewById(R.id.cart_price_id);
        quantity_text=mView.findViewById(R.id.cart_qnt_id);

        model_text.setText(model);
        price_text.setText(price);
        quantity_text.setText(quantity);
    }

    private CartViewHolder.ClickListener mClickListener;

    public interface ClickListener
    {
        void onItemClick(View view,int position);
        void onItemLongClick(View view,int position);
    }

    public void setOnClickListener(CartViewHolder.ClickListener clickListener)
    {
        mClickListener=clickListener;
    }
}
