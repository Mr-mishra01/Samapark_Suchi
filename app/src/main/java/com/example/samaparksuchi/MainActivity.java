package com.example.samaparksuchi;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    RecyclerView recyclerView;

    FirebaseAuth auth;
    FirebaseUser user;
    RecyclerContactAdapter adapter;
    FloatingActionButton dialog_button;
    AppCompatButton logout;
    ArrayList <ContactModel> arrContacts = new ArrayList<>();
    private static final int PICK_IMAGE_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        auth = FirebaseAuth.getInstance();
        recyclerView = findViewById(R.id.recycler_contacts);
        dialog_button = findViewById(R.id.dialog_button);
        logout= findViewById(R.id.logout_btn);
        user = auth.getCurrentUser();

        if (user == null){
            Intent intent = new Intent(getApplicationContext(),Login_activity.class);
            startActivity(intent);
            finish();

        }





        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(getApplicationContext(),Login_activity.class);
                startActivity(intent);
                finish();
            }
        });




        dialog_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog = new Dialog(MainActivity.this);
                dialog.setContentView(R.layout.add_contatct);
                Dialog save_dialog = new Dialog(MainActivity.this);
                save_dialog.setContentView(R.layout.save_dialog);

                EditText contName = dialog.findViewById(R.id.add_name);
                EditText contNumb = dialog.findViewById(R.id.add_num);
                ImageView addImg = findViewById(R.id.add_img);
                Button btn = dialog.findViewById(R.id.action_btn);



                btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        String name = "" , numb = "";

                        if (!contName.getText().toString().equals("")){
                                name = contName.getText().toString();
                        }else {
                            Toast.makeText(MainActivity.this, "Enter name ", Toast.LENGTH_SHORT).show();
                        }

                        if (!contNumb.getText().toString().equals("")){
                              numb = contNumb.getText().toString();
                        }
                        else {
                            Toast.makeText(MainActivity.this, "Enter number", Toast.LENGTH_SHORT).show();
                        }
                        addImg.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                                startActivityForResult(i,3);

                            }
                        });



                        arrContacts.add(new ContactModel(R.drawable.boy2,name,numb));

                        adapter.notifyItemInserted(arrContacts.size()-1);

                        recyclerView.scrollToPosition(arrContacts.size()-1);

                        dialog.dismiss();
                        save_dialog.show();
                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {

                                save_dialog.dismiss();
                            }
                        },2000);
                        save_dialog.setCanceledOnTouchOutside(true);

                    }
                });

                dialog.show();


            }
        });






        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        arrContacts.add(new ContactModel(R.drawable.boy,"A","9784793839"));
        arrContacts.add(new ContactModel(R.drawable.boy1,"B","9784793839"));
        arrContacts.add(new ContactModel(R.drawable.boy2,"C","9784793839"));
        arrContacts.add(new ContactModel(R.drawable.girl0,"D","9784793839"));
        arrContacts.add(new ContactModel(R.drawable.girl01,"E","9784793839"));
        arrContacts.add(new ContactModel(R.drawable.boy3,"F","9784793839"));
        arrContacts.add(new ContactModel(R.drawable.boy5,"G","9784793839"));
        arrContacts.add(new ContactModel(R.drawable.womwn1,"H","9784793839"));
        arrContacts.add(new ContactModel(R.drawable.woman0,"I","9784793839"));
        arrContacts.add(new ContactModel(R.drawable.boy,"A","9784793839"));
        arrContacts.add(new ContactModel(R.drawable.boy1,"B","9784793839"));
        arrContacts.add(new ContactModel(R.drawable.boy2,"C","9784793839"));
        arrContacts.add(new ContactModel(R.drawable.girl0,"D","9784793839"));
        arrContacts.add(new ContactModel(R.drawable.girl01,"E","9784793839"));
        arrContacts.add(new ContactModel(R.drawable.boy3,"F","9784793839"));
        arrContacts.add(new ContactModel(R.drawable.boy5,"G","9784793839"));
        arrContacts.add(new ContactModel(R.drawable.womwn1,"H","9784793839"));
        arrContacts.add(new ContactModel(R.drawable.woman0,"I","9784793839"));
        arrContacts.add(new ContactModel(R.drawable.boy,"A","9784793839"));
        arrContacts.add(new ContactModel(R.drawable.boy1,"B","9784793839"));
        arrContacts.add(new ContactModel(R.drawable.boy2,"C","9784793839"));
        arrContacts.add(new ContactModel(R.drawable.girl0,"D","9784793839"));
        arrContacts.add(new ContactModel(R.drawable.girl01,"E","9784793839"));
        arrContacts.add(new ContactModel(R.drawable.boy3,"F","9784793839"));
        arrContacts.add(new ContactModel(R.drawable.boy5,"G","9784793839"));
        arrContacts.add(new ContactModel(R.drawable.womwn1,"H","9784793839"));
        arrContacts.add(new ContactModel(R.drawable.woman0,"I","9784793839"));



        adapter = new RecyclerContactAdapter(this,arrContacts);
        recyclerView.setAdapter(adapter);

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && data != null){
            Uri selectedImage = data.getData();

        }
    }
}