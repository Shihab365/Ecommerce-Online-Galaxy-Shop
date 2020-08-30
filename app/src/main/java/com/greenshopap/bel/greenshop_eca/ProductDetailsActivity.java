package com.greenshopap.bel.greenshop_eca;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class ProductDetailsActivity extends AppCompatActivity {

    ElegantNumberButton elegantNumberButton;
    Button addCart;
    TextView detailsModel,detailsPrice;
    ImageView detailsImage;
    String images,images1,model,price,quantity,fCurrentTime,fCurrentDate;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        ActionBar actionBar=getSupportActionBar();
        actionBar.setTitle("Product Details");

        elegantNumberButton=findViewById(R.id.product_qntnum_id);
        addCart=(Button)findViewById(R.id.addtocart_btn_id);
        detailsModel=(TextView)findViewById(R.id.details_modelname_id);
        detailsPrice=(TextView)findViewById(R.id.details_price_id);
        detailsImage=(ImageView)findViewById(R.id.image_details_id);

        byte[] bytes=getIntent().getByteArrayExtra("iImage");
        final String mModel=getIntent().getStringExtra("iModel");
        final String mPrice=getIntent().getStringExtra("iPrice");
        final Bitmap bmp=BitmapFactory.decodeByteArray(bytes,0,bytes.length);

        detailsImage.setImageBitmap(bmp);
        detailsModel.setText(mModel);
        detailsPrice.setText(mPrice);

        Calendar calendar=Calendar.getInstance();
        SimpleDateFormat currentdate=new SimpleDateFormat("MMM dd,yyyy");
        fCurrentDate=currentdate.format(calendar.getTime());

        SimpleDateFormat currenttime=new SimpleDateFormat("hh:mm:ss aa");
        fCurrentTime=currenttime.format(calendar.getTime());

        firebaseAuth=FirebaseAuth.getInstance();
        databaseReference=FirebaseDatabase.getInstance().getReference("CustomerData").child("UsersInfo");

        addCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                model=detailsModel.getText().toString();
                price=detailsPrice.getText().toString();
                quantity=elegantNumberButton.getNumber();
                images1=getIntent().getStringExtra("iLink");
                images=images1;


                Store_order_data store_order_data=new Store_order_data(
                        model,
                        price,
                        quantity,
                        images
                );

                FirebaseDatabase.getInstance().getReference("CustomerData").child("UsersInfo")
                        .child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("OrderDetails").child(fCurrentDate+", "+fCurrentTime)
                        .setValue(store_order_data).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful())
                        {
                            startActivity(new Intent(ProductDetailsActivity.this,TimelineActivity.class));
                        }
                    }
                });
            }
        });
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
