package com.greenshopap.bel.greenshop_eca;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.io.ByteArrayOutputStream;

public class ToysActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    FirebaseDatabase f_dbs;
    DatabaseReference d_ref;
    Intent intent;
    FloatingActionButton fab_laptop;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_toys);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        ActionBar actionBar=getSupportActionBar();
        actionBar.setTitle("Toys");

        fab_laptop=(FloatingActionButton) findViewById(R.id.fab_toys_id);
        recyclerView=findViewById(R.id.recycler_toys_list);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        f_dbs=FirebaseDatabase.getInstance();
        d_ref=f_dbs.getReference("toys");

        fab_laptop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayItemList();
            }
        });
    }
    private void ProductSearch(String searchItem)
    {
        Query query=d_ref.orderByChild("brand").startAt(searchItem).endAt(searchItem + "\uf8ff");
        FirebaseRecyclerAdapter<Model_item,ViewHolder_item> firebaseRecyAdap=
                new FirebaseRecyclerAdapter<Model_item, ViewHolder_item>(
                        Model_item.class,R.layout.products_list_view,
                        ViewHolder_item.class,query
                )
                {
                    @Override
                    protected void populateViewHolder(ViewHolder_item viewHolder_item, Model_item model_item, int i) {
                        viewHolder_item.setDetails(getApplicationContext(),model_item.getImages(),model_item.getBrand(),model_item.getModel(),model_item.getPrice());
                    }
                    @Override
                    public ViewHolder_item onCreateViewHolder(ViewGroup parent, int viewType) {

                        ViewHolder_item vh_i=super.onCreateViewHolder(parent,viewType);

                        vh_i.setOnClickListener(new ViewHolder_item.ClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {

                                TextView mItemBrand=view.findViewById(R.id.item_brand_id);
                                TextView mItemModel=view.findViewById(R.id.item_model_id);
                                TextView mItemPrice=view.findViewById(R.id.item_price_id);
                                ImageView mItemImg=view.findViewById(R.id.item_img_id);

                                String mBrand=mItemBrand.getText().toString();
                                String mModel=mItemModel.getText().toString();
                                String mPrice=mItemPrice.getText().toString();
                                Drawable mDrawable=mItemImg.getDrawable();
                                Bitmap mBitmap=((BitmapDrawable)mDrawable).getBitmap();

                                Intent intent=new Intent(view.getContext(), ProductDetailsActivity.class);
                                ByteArrayOutputStream stream=new ByteArrayOutputStream();
                                mBitmap.compress(Bitmap.CompressFormat.PNG,100,stream);
                                byte[] bytes=stream.toByteArray();
                                intent.putExtra("iImage",bytes);
                                intent.putExtra("iBrand",mBrand);
                                intent.putExtra("iModel",mModel);
                                intent.putExtra("iPrice",mPrice);
                                startActivity(intent);
                            }

                            @Override
                            public void onItemLongClick(View view, int position) {

                            }
                        });
                        return vh_i;
                    }

                };
        recyclerView.setAdapter(firebaseRecyAdap);
    }

    protected void onStart() {
        super.onStart();
        FirebaseRecyclerAdapter<Model_item,ViewHolder_item> firebaseRecyclerAdapter=
                new FirebaseRecyclerAdapter<Model_item, ViewHolder_item>(
                        Model_item.class,
                        R.layout.products_list_view,
                        ViewHolder_item.class,
                        d_ref
                ) {
                    @Override
                    protected void populateViewHolder(final ViewHolder_item viewHolder_item, final Model_item model_item, final int i)
                    {
                        viewHolder_item.setDetails(getApplicationContext(),model_item.
                                getImages(),model_item.getBrand(),model_item.
                                getModel(),model_item.getPrice());
                    }

                    @Override
                    public ViewHolder_item onCreateViewHolder(ViewGroup parent, int viewType) {

                        ViewHolder_item vh_i=super.onCreateViewHolder(parent,viewType);

                        vh_i.setOnClickListener(new ViewHolder_item.ClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {

                                TextView mItemModel=view.findViewById(R.id.item_model_id);
                                TextView mItemPrice=view.findViewById(R.id.item_price_id);
                                ImageView mItemImg=view.findViewById(R.id.item_img_id);

                                String mModel=mItemModel.getText().toString();
                                String mPrice=mItemPrice.getText().toString();
                                Drawable mDrawable=mItemImg.getDrawable();
                                Bitmap mBitmap=((BitmapDrawable)mDrawable).getBitmap();

                                Intent intent=new Intent(view.getContext(), ProductDetailsActivity.class);
                                ByteArrayOutputStream stream=new ByteArrayOutputStream();
                                mBitmap.compress(Bitmap.CompressFormat.PNG,100,stream);
                                byte[] bytes=stream.toByteArray();
                                intent.putExtra("iImage",bytes);
                                intent.putExtra("iModel",mModel);
                                intent.putExtra("iPrice",mPrice);
                                startActivity(intent);
                            }

                            @Override
                            public void onItemLongClick(View view, int position) {

                            }
                        });
                        return vh_i;
                    }
                };
        recyclerView.setAdapter(firebaseRecyclerAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        MenuItem item=menu.findItem(R.id.action_search_id);
        SearchView searchView=(SearchView)MenuItemCompat.getActionView(item);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                ProductSearch(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                ProductSearch(newText);
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    public void displayItemList()
    {
        intent=new Intent(ToysActivity.this,MyCartActivity.class);
        startActivity(intent);
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
