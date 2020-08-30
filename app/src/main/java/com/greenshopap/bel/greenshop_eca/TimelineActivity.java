package com.greenshopap.bel.greenshop_eca;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.ByteArrayOutputStream;

public class TimelineActivity extends AppCompatActivity {

    private FloatingActionButton fab_timeline;
    private ViewFlipper viewFlipper;
    CardView card_lap,card_mob,card_sun,card_bck,card_wtc,card_tys;
    RecyclerView jfu_recyclerView,trdn_recyclerView,mpr_recyclerView,nwp_recyclerView;

    DatabaseReference d_reff_jfu,d_reff_trdn,d_reff_mpr,d_reff_nwp;
    FirebaseDatabase f_dbs_jfu,f_dbs_trdn,f_dbs_mpr,f_dbs_nwp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);

        card_lap=findViewById(R.id.card_laptop_id);
        card_mob=findViewById(R.id.card_mobile_id);
        card_sun=findViewById(R.id.card_sunglass_id);
        card_bck=findViewById(R.id.card_backpack_id);
        card_wtc=findViewById(R.id.card_watch_id);
        card_tys=findViewById(R.id.card_toys_id);

        card_lap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(TimelineActivity.this,LaptopActivity.class));
            }
        });
        card_mob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(TimelineActivity.this,MobileActivity.class));
            }
        });
        card_sun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(TimelineActivity.this,SunglassActivity.class));
            }
        });
        card_bck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(TimelineActivity.this,BackpackActivity.class));
            }
        });
        card_wtc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(TimelineActivity.this,WatchActivity.class));
            }
        });
        card_tys.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(TimelineActivity.this,ToysActivity.class));
            }
        });


        fab_timeline=(FloatingActionButton)findViewById(R.id.fab_timeline_id);
        viewFlipper=(ViewFlipper)findViewById(R.id.slider_id);

        jfu_recyclerView=findViewById(R.id.recycler_jfu_id);
        trdn_recyclerView=findViewById(R.id.recycler_trdn_id);
        mpr_recyclerView=findViewById(R.id.recycler_mpr_id);
        nwp_recyclerView=findViewById(R.id.recycler_nwp_id);

        jfu_recyclerView.setHasFixedSize(true);
        jfu_recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,true));
        mpr_recyclerView.setHasFixedSize(true);
        mpr_recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,true));
        trdn_recyclerView.setHasFixedSize(true);
        trdn_recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,true));
        nwp_recyclerView.setHasFixedSize(true);
        nwp_recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,true));

        f_dbs_jfu=FirebaseDatabase.getInstance();
        d_reff_jfu=f_dbs_jfu.getReference("justforyou");
        f_dbs_trdn=FirebaseDatabase.getInstance();
        d_reff_trdn=f_dbs_trdn.getReference("trendingnow");
        f_dbs_mpr=FirebaseDatabase.getInstance();
        d_reff_mpr=f_dbs_mpr.getReference("mostpopular");
        f_dbs_nwp=FirebaseDatabase.getInstance();
        d_reff_nwp=f_dbs_nwp.getReference("newproduct");

        int images[]={R.drawable.mobile_banner,R.drawable.laptop_banner,
                R.drawable.watch_banner,R.drawable.football_banner,R.drawable.sunglass_banner};
        for(int img:images)
        {
            slideFlip(img);
        }
        fab_timeline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(TimelineActivity.this,MyCartActivity.class));
            }
        });
    }
    public void slideFlip(int image)
    {
        ImageView imageView=new ImageView(this);
        imageView.setBackgroundResource(image);
        viewFlipper.addView(imageView);
        viewFlipper.setFlipInterval(4000);
        viewFlipper.setAutoStart(true);
        viewFlipper.setInAnimation(this,android.R.anim.slide_in_left);
        viewFlipper.setOutAnimation(this,android.R.anim.slide_out_right);
    }

    @Override
    protected void onStart() {
        super.onStart();
        //JustForYou
        FirebaseRecyclerAdapter<jfu_model_item,jfu_ViewHolder_item> jfu_recy_adapter=
                new FirebaseRecyclerAdapter<jfu_model_item, jfu_ViewHolder_item>(
                        jfu_model_item.class,
                        R.layout.just_for_you_live,
                        jfu_ViewHolder_item.class,
                        d_reff_jfu
                ) {
                    @Override
                    protected void populateViewHolder(jfu_ViewHolder_item jfu_viewHolder_item, jfu_model_item jfu_model_item, int i) {
                        jfu_viewHolder_item.setDetails(getApplicationContext(),jfu_model_item.
                                getImages(),jfu_model_item.getModel(),jfu_model_item.getPrice());
                    }

                    @Override
                    public jfu_ViewHolder_item onCreateViewHolder(ViewGroup parent, int viewType) {
                        jfu_ViewHolder_item vh_i=super.onCreateViewHolder(parent,viewType);

                        vh_i.setOnClickListener(new jfu_ViewHolder_item.ClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {

                                TextView mItemModel=view.findViewById(R.id.jfu_model_id);
                                TextView mItemPrice=view.findViewById(R.id.jfu_price_id);
                                ImageView mItemImg=view.findViewById(R.id.jfu_img_id);

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
        jfu_recyclerView.setAdapter(jfu_recy_adapter);

        //TrendingNow
        FirebaseRecyclerAdapter<jfu_model_item,jfu_ViewHolder_item> trdn_recy_adapter=
                new FirebaseRecyclerAdapter<jfu_model_item, jfu_ViewHolder_item>(
                        jfu_model_item.class,
                        R.layout.just_for_you_live,
                        jfu_ViewHolder_item.class,
                        d_reff_trdn
                ) {
                    @Override
                    protected void populateViewHolder(jfu_ViewHolder_item jfu_viewHolder_item, jfu_model_item jfu_model_item, int i) {
                        jfu_viewHolder_item.setDetails(getApplicationContext(),jfu_model_item.
                                getImages(),jfu_model_item.getModel(),jfu_model_item.getPrice());
                    }

                    @Override
                    public jfu_ViewHolder_item onCreateViewHolder(ViewGroup parent, int viewType) {
                        jfu_ViewHolder_item vh_i=super.onCreateViewHolder(parent,viewType);

                        vh_i.setOnClickListener(new jfu_ViewHolder_item.ClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {

                                TextView mItemModel=view.findViewById(R.id.jfu_model_id);
                                TextView mItemPrice=view.findViewById(R.id.jfu_price_id);
                                ImageView mItemImg=view.findViewById(R.id.jfu_img_id);

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
        trdn_recyclerView.setAdapter(trdn_recy_adapter);

        //MostPopular
        FirebaseRecyclerAdapter<jfu_model_item,jfu_ViewHolder_item> mpr_recy_adapter=
                new FirebaseRecyclerAdapter<jfu_model_item, jfu_ViewHolder_item>(
                        jfu_model_item.class,
                        R.layout.just_for_you_live,
                        jfu_ViewHolder_item.class,
                        d_reff_mpr
                ) {
                    @Override
                    protected void populateViewHolder(jfu_ViewHolder_item jfu_viewHolder_item, jfu_model_item jfu_model_item, int i) {
                        jfu_viewHolder_item.setDetails(getApplicationContext(),jfu_model_item.
                                getImages(),jfu_model_item.getModel(),jfu_model_item.getPrice());
                    }
                    @Override
                    public jfu_ViewHolder_item onCreateViewHolder(ViewGroup parent, int viewType) {
                        jfu_ViewHolder_item vh_i=super.onCreateViewHolder(parent,viewType);

                        vh_i.setOnClickListener(new jfu_ViewHolder_item.ClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {

                                TextView mItemModel=view.findViewById(R.id.jfu_model_id);
                                TextView mItemPrice=view.findViewById(R.id.jfu_price_id);
                                ImageView mItemImg=view.findViewById(R.id.jfu_img_id);

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
        mpr_recyclerView.setAdapter(mpr_recy_adapter);

        //NewProduct
        FirebaseRecyclerAdapter<jfu_model_item,jfu_ViewHolder_item> nwp_recy_adapter=
                new FirebaseRecyclerAdapter<jfu_model_item, jfu_ViewHolder_item>(
                        jfu_model_item.class,
                        R.layout.just_for_you_live,
                        jfu_ViewHolder_item.class,
                        d_reff_nwp
                ) {
                    @Override
                    protected void populateViewHolder(jfu_ViewHolder_item jfu_viewHolder_item, jfu_model_item jfu_model_item, int i) {
                        jfu_viewHolder_item.setDetails(getApplicationContext(),jfu_model_item.
                                getImages(),jfu_model_item.getModel(),jfu_model_item.getPrice());
                    }
                    @Override
                    public jfu_ViewHolder_item onCreateViewHolder(ViewGroup parent, int viewType) {
                        jfu_ViewHolder_item vh_i=super.onCreateViewHolder(parent,viewType);

                        vh_i.setOnClickListener(new jfu_ViewHolder_item.ClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {

                                TextView mItemModel=view.findViewById(R.id.jfu_model_id);
                                TextView mItemPrice=view.findViewById(R.id.jfu_price_id);
                                ImageView mItemImg=view.findViewById(R.id.jfu_img_id);

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
        nwp_recyclerView.setAdapter(nwp_recy_adapter);
    }

}
