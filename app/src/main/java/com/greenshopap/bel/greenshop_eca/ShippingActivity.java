package com.greenshopap.bel.greenshop_eca;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class ShippingActivity extends AppCompatActivity {

    EditText editFirst,editLast,editAddress1,editAddress2,editCity,editZip,editPhone;
    String shipFirst,shipLast,shipAddress1,shipAddress2,shipCity,shipZip,shipPhone;
    Button billingButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shipping);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        ActionBar actionBar=getSupportActionBar();
        actionBar.setTitle("Shipping Info");

        editFirst=(EditText)findViewById(R.id.ship_firstname_id);
        editLast=(EditText)findViewById(R.id.ship_lastname_id);
        editAddress1=(EditText)findViewById(R.id.ship_address1_id);
        editAddress2=(EditText)findViewById(R.id.ship_address2_id);
        editCity=(EditText)findViewById(R.id.ship_city_id);
        editZip=(EditText)findViewById(R.id.ship_zip_id);
        editPhone=(EditText)findViewById(R.id.ship_phone_id);
        billingButton=(Button)findViewById(R.id.billing_btn_id);

        billingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EmptyCheck();
            }
        });
    }

    public void EmptyCheck() {
        if (TextUtils.isEmpty(editFirst.getText().toString())) {
            Toast.makeText(this, "Provide first name", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(editLast.getText().toString())) {
            Toast.makeText(this, "Provide last name", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(editAddress1.getText().toString())) {
            Toast.makeText(this, "Provide address 1", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(editCity.getText().toString())) {
            Toast.makeText(this, "Provide city name", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(editZip.getText().toString())){
            Toast.makeText(this, "Provide zip number", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(editPhone.getText().toString())){
            Toast.makeText(this, "Provide phone number", Toast.LENGTH_SHORT).show();
        }
        else
        {
            ConfirmOrder();
        }
    }
    private void ConfirmOrder() {

        final String fCurrentDate, fCurrentTime;
        shipFirst = editFirst.getText().toString();
        shipLast = editLast.getText().toString();
        shipAddress1 = editAddress1.getText().toString();
        shipAddress2 = editAddress2.getText().toString();
        shipCity = editCity.getText().toString();
        shipZip = editZip.getText().toString();
        shipPhone = editPhone.getText().toString();

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat currentdate = new SimpleDateFormat("MMM dd,yyyy");
        fCurrentDate = currentdate.format(calendar.getTime());

        SimpleDateFormat currenttime = new SimpleDateFormat("hh:mm:ss aa");
        fCurrentTime = currenttime.format(calendar.getTime());

        Shipping_order_item shipping_order_item = new Shipping_order_item(
                shipFirst,shipLast,shipAddress1,shipAddress2,shipCity,shipZip,shipPhone
        );

        FirebaseDatabase.getInstance().getReference("ConfirmOrderList")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid()).
                child(fCurrentDate + ", " + fCurrentTime)
                .setValue(shipping_order_item).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    FirebaseDatabase.getInstance().getReference().child("CustomerData").child("UsersInfo")
                            .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                            .removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful())
                            {
                                String rcvCost=getIntent().getStringExtra("totalCosts");
                                Intent intent=new Intent(ShippingActivity.this,PaymentActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                intent.putExtra("totalrcv",rcvCost);
                                startActivity(intent);
                                finish();
                            }
                        }
                    });
                }
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
