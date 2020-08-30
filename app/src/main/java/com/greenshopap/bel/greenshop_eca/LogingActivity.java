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
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LogingActivity extends AppCompatActivity {

    private TextView signup_text;
    private Button login_btn,tryagain_log;
    private EditText log_email,log_password;
    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;
    private Dialog mDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loging);

        View decorV=getWindow().getDecorView();
        int uiOption=View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorV.setSystemUiVisibility(uiOption);

        ActionBar actionBar=getSupportActionBar();
        actionBar.hide();

        progressDialog=new ProgressDialog(this);
        mDialog=new Dialog(this);

        signup_text=(TextView)findViewById(R.id.signup_text_id);
        login_btn=(Button) findViewById(R.id.login_button_id);
        log_email=(EditText)findViewById(R.id.log_email_id);
        log_password=(EditText)findViewById(R.id.log_password_id);

        firebaseAuth=FirebaseAuth.getInstance();

        FirebaseUser user=firebaseAuth.getCurrentUser();
        if(user!=null)
        {
            finish();
            startActivity(new Intent(LogingActivity.this,TimelineActivity.class));
        }
        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (log_email.getText().toString().isEmpty() || log_password.getText().toString().isEmpty())
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
                    validate(log_email.getText().toString(),log_password.getText().toString());
                }
            }
        });

        signup_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LogingActivity.this,SignUpActivity.class));
            }
        });
    }

    private void validate(String userEmail,String userPassword)
    {
        progressDialog.setMessage("Please wait");
        progressDialog.show();
        firebaseAuth.signInWithEmailAndPassword(userEmail,userPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful())
                {
                    startActivity(new Intent(LogingActivity.this,TimelineActivity.class));
                }
                else
                {
                    progressDialog.dismiss();
                    log_email.setText("");
                    log_password.setText("");

                    mDialog.setContentView(R.layout.login_failed);
                    tryagain_log=(Button)mDialog.findViewById(R.id.tryagain_log_id);
                    tryagain_log.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            startActivity(new Intent(LogingActivity.this,LogingActivity.class));
                        }
                    });
                    mDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    mDialog.show();
                }
            }
        });
    }
}
