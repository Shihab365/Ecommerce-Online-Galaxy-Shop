package com.greenshopap.bel.greenshop_eca;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MyCartActivity extends AppCompatActivity {

    RecyclerView recyclerView_cart;
    Button conf_btn;
    FirebaseDatabase fc_dbs;
    DatabaseReference dc_rff;
    int totalCost=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_cart);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        ActionBar actionBar=getSupportActionBar();
        actionBar.setTitle("Cart List");

        conf_btn=(Button)findViewById(R.id.conf_ord_id);
        recyclerView_cart=findViewById(R.id.cart_list_id);
        recyclerView_cart.setHasFixedSize(true);
        recyclerView_cart.setLayoutManager(new LinearLayoutManager(this));

        fc_dbs=FirebaseDatabase.getInstance();
        dc_rff=fc_dbs.getReference().child("CustomerData").child("UsersInfo")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("OrderDetails");

        conf_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MyCartActivity.this,ShippingActivity.class);
                intent.putExtra("totalCosts",String.valueOf(totalCost));
                startActivity(intent);
                finish();
            }
        });
    }
    @Override
    protected void onStart() {
        super.onStart();
        FirebaseRecyclerAdapter<Cart_item,CartViewHolder> firebaseRecyclerAdp=new
                FirebaseRecyclerAdapter<Cart_item, CartViewHolder>(
                        Cart_item.class,
                        R.layout.order_placing,
                        CartViewHolder.class,
                        dc_rff
                ) {
                    @Override
                    protected void populateViewHolder(CartViewHolder cart_viewholder, Cart_item cart, int i) {
                        cart_viewholder.setDetails(getApplicationContext(),
                                cart.getModel(),cart.getPrice(),cart.getQuantity());

                        int totalProCost=((Integer.valueOf(cart.getPrice())))*Integer.valueOf(cart.getQuantity());
                        totalCost=totalCost+totalProCost;
                    }

                    @Override
                    public CartViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                        final CartViewHolder ct_i=super.onCreateViewHolder(parent,viewType);
                        ct_i.setOnClickListener(new CartViewHolder.ClickListener() {
                            @Override
                            public void onItemClick(View view, final int position) {

                                CharSequence options[]=new CharSequence[]
                                        {
                                                "Remove"
                                        };
                                AlertDialog.Builder builder=new AlertDialog.Builder(MyCartActivity.this);
                                builder.setTitle("Cart Options:");
                                builder.setItems(options, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        if(which==0)
                                        {
                                            String dd=getRef(position).getKey();
                                            DatabaseReference dffr=FirebaseDatabase.getInstance().getReference();
                                            dffr.child("CustomerData").child("UsersInfo").child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                                    .child("OrderDetails").child(dd).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    if(task.isSuccessful())
                                                    {
                                                        Toast.makeText(MyCartActivity.this, "remove success", Toast.LENGTH_SHORT).show();
                                                    }
                                                }
                                            });
                                        }
                                    }
                                });
                                builder.show();
                            }

                            @Override
                            public void onItemLongClick(View view, int position) {

                            }
                        });
                        return ct_i;
                    }
                };
        recyclerView_cart.setAdapter(firebaseRecyclerAdp);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId()==android.R.id.home)
        {
            this.finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
