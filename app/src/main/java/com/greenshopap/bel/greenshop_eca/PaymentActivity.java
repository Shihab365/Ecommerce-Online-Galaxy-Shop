package com.greenshopap.bel.greenshop_eca;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class PaymentActivity extends AppCompatActivity {

    TextView textView;
    CardView cardBkash,cardRocket,cardNexus,cardPaypal;
    Button delButton;
    Dialog xdialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        ActionBar actionBar=getSupportActionBar();
        actionBar.setTitle("Payment");

        setContentView(R.layout.activity_payment);

        xdialog=new Dialog(this);

        textView=(TextView)findViewById(R.id.totalbill_id);
        cardBkash=(CardView) findViewById(R.id.bkash_id);
        cardRocket=(CardView) findViewById(R.id.rocket_id);
        cardNexus=(CardView) findViewById(R.id.nexus_id);
        cardPaypal=(CardView) findViewById(R.id.paypal_id);

        Bundle b=getIntent().getExtras();
        if(b!=null)
        {
            String payCost=(String)b.get("totalrcv");
            textView.setText(payCost + " $ ");
        }

        cardBkash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                xdialog.setContentView(R.layout.delivery_ui);
                delButton=xdialog.findViewById(R.id.ok_del_id);
                delButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        xdialog.dismiss();
                        startActivity(new Intent(PaymentActivity.this,TimelineActivity.class));
                    }
                });
                xdialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                xdialog.show();
            }
        });
        cardRocket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                xdialog.setContentView(R.layout.delivery_ui);
                delButton=xdialog.findViewById(R.id.ok_del_id);
                delButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        xdialog.dismiss();
                        startActivity(new Intent(PaymentActivity.this,TimelineActivity.class));
                    }
                });
                xdialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                xdialog.show();
            }
        });
        cardNexus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                xdialog.setContentView(R.layout.delivery_ui);
                delButton=xdialog.findViewById(R.id.ok_del_id);
                delButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        xdialog.dismiss();
                        startActivity(new Intent(PaymentActivity.this,TimelineActivity.class));
                    }
                });
                xdialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                xdialog.show();
            }
        });
        cardPaypal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                xdialog.setContentView(R.layout.delivery_ui);
                delButton=xdialog.findViewById(R.id.ok_del_id);
                delButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        xdialog.dismiss();
                        startActivity(new Intent(PaymentActivity.this,TimelineActivity.class));
                    }
                });
                xdialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                xdialog.show();
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
