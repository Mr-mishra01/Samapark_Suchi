package com.example.samaparksuchi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.samaparksuchi.models.UserProfile;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class sign_up_activity extends AppCompatActivity {

    private FirebaseAuth mAuth ;
    EditText name_field , username_field , password_field;
    AppCompatButton Save_btn;
    ProgressBar progressBar;
    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            Intent intent = new Intent(getApplicationContext(),MainActivity.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        name_field = findViewById(R.id.name_field);
        username_field = findViewById(R.id.sign_up_username);
        password_field = findViewById(R.id.sign_up_password);
        Save_btn = findViewById(R.id.save_detail_btn);
        progressBar = findViewById(R.id.progress_bar);
        mAuth = FirebaseAuth.getInstance();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference rootRef = database.getReference();

        Save_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                String name ,usernameFiled, passwordFiled;
                name = name_field.getText().toString();
                usernameFiled = username_field.getText().toString();
                passwordFiled = password_field.getText().toString();

                if (TextUtils.isEmpty(usernameFiled)){
                    Toast.makeText(sign_up_activity.this, "Enter Username!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(passwordFiled)){
                    Toast.makeText(sign_up_activity.this, "Enter Password!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(name)){
                    Toast.makeText(sign_up_activity.this, "Enter Name!", Toast.LENGTH_SHORT).show();
                    return;
                }
                mAuth.createUserWithEmailAndPassword(usernameFiled, passwordFiled)
                        .addOnCompleteListener( new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                progressBar.setVisibility(View.GONE);

                                if (task.isSuccessful()) {
                                        Intent intent = new Intent(getApplicationContext(),Login_activity.class);
                                        startActivity(intent);
                                        finish();
                                    // Sign in success, update UI with the signed-in user's information
                                    Toast.makeText(sign_up_activity.this, "Account created", Toast.LENGTH_SHORT).show();
//                                    FirebaseUser user = mAuth.getCurrentUser();
                                } else {
                                    // If sign in fails, display a message to the user.
                                    Toast.makeText(sign_up_activity.this, "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        });


            }
        });


    }}