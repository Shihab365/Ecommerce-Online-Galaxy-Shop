package com.greenshopap.bel.greenshop_eca;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.*;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUpActivity extends AppCompatActivity {

    private EditText sign_name,sign_mobile,sign_email,sign_password;
    private Button sign_button,tryagain_btn,ok_btn;
    private FirebaseAuth sign_fAuth;
    private DatabaseReference sign_dbs;
    private ProgressDialog progressDialog;
    private Dialog mDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        ActionBar actionBar=getSupportActionBar();
        actionBar.setTitle("Sign Up");

        mDialog=new Dialog(this);

        sign_name=(EditText)findViewById(R.id.signup_name_id);
        sign_mobile=(EditText)findViewById(R.id.signup_mobile_id);
        sign_email=(EditText)findViewById(R.id.signup_email_id);
        sign_password=(EditText)findViewById(R.id.signup_password_id);
        sign_button=(Button)findViewById(R.id.signup_button_id);

        sign_fAuth=FirebaseAuth.getInstance();
        sign_dbs=FirebaseDatabase.getInstance().getReference("CustomerData").child("UsersInfo");

        progressDialog=new ProgressDialog(this);

        sign_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validate())
                {
                    final String user_name=sign_name.getText().toString().trim();
                    final String user_mobile=sign_mobile.getText().toString().trim();
                    final String user_email=sign_email.getText().toString().trim();
                    final String user_password=sign_password.getText().toString().trim();

                    progressDialog.setMessage("Please wait");
                    progressDialog.show();
                    sign_fAuth.createUserWithEmailAndPassword(user_email,user_password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful())
                            {
                                StoreUserInfo storeUserInfo=new StoreUserInfo(
                                        user_name,
                                        user_mobile,
                                        user_email,
                                        user_password
                                );

                                FirebaseDatabase.getInstance().getReference("CustomerData").child("UsersInfo")
                                        .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                        .setValue(storeUserInfo).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        progressDialog.dismiss();
                                        sign_name.setText("");
                                        sign_mobile.setText("");
                                        sign_email.setText("");
                                        sign_password.setText("");

                                        mDialog.setContentView(R.layout.reg_success);
                                        ok_btn=(Button)mDialog.findViewById(R.id.ok_reg_id);
                                        ok_btn.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                startActivity(new Intent(SignUpActivity.this,TimelineActivity.class));
                                            }
                                        });
                                        mDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                                        mDialog.show();
                                    }
                                });
                            }
                            else
                            {
                                progressDialog.dismiss();
                                sign_name.setText("");
                                sign_mobile.setText("");
                                sign_email.setText("");
                                sign_password.setText("");

                                mDialog.setContentView(R.layout.reg_failed);
                                tryagain_btn=(Button)mDialog.findViewById(R.id.tryagain_reg_id);
                                tryagain_btn.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        startActivity(new Intent(SignUpActivity.this,SignUpActivity.class));
                                    }
                                });
                                mDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                                mDialog.show();
                            }
                        }
                    });
                }
            }
        });
    }
    private Boolean validate()
    {
        Boolean result=false;

        String s_name=sign_name.getText().toString();
        String s_mobile=sign_mobile.getText().toString();
        String s_email=sign_email.getText().toString();
        String s_password=sign_password.getText().toString();

        if(s_name.isEmpty() || s_mobile.isEmpty() || s_email.isEmpty() || s_password.isEmpty())
        {
            LayoutInflater inflater=getLayoutInflater();
            View customView=inflater.inflate(R.layout.sign_blank, (ViewGroup) findViewById(R.id.sign_text_blank_id));
            Toast toast=new Toast(getApplicationContext());
            toast.setDuration(Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER,0,50);
            toast.setView(customView);
            toast.show();
        }
        else
        {
            result=true;
        }
        return  result;
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
