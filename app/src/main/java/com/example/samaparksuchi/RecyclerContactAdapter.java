package com.example.samaparksuchi;

import static androidx.core.app.ActivityCompat.startActivityForResult;
import static androidx.core.content.ContextCompat.startActivity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;


public class RecyclerContactAdapter extends RecyclerView.Adapter<RecyclerContactAdapter.ViewHolder> {
    Context context;
    private int lastPosition = -1;

    private static final  int REQUEST_CODE_SELECT_IMAGE = 1;
    ArrayList <ContactModel> arrContact;
    Bitmap imgdata;


    RecyclerContactAdapter(Context context , ArrayList<ContactModel> arrContact){
        this.context = context;
        this.arrContact = arrContact;

    }




    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

     View view =   LayoutInflater.from(context).inflate(R.layout.contact_row,parent,false);
      ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.imgContact.setImageResource(arrContact.get(position).img);
        holder.txtName.setText(arrContact.get(position).name);
        holder.txtNumb.setText(arrContact.get(position).numb);
        setAnimation(holder.itemView,position);

        holder.llRow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog = new Dialog(context);
                dialog.setContentView(R.layout.add_contatct);
                Dialog save_dialog = new Dialog(context);
                save_dialog.setContentView(R.layout.save_dialog);
                TextView add_update_dialog_txt = save_dialog.findViewById(R.id.save_update_dialog_txt);
                add_update_dialog_txt.setText("Conatct Updated");


                TextView addContLogo = dialog.findViewById(R.id.add_cont_logo); //Add Contacts in dialog
                EditText contName = dialog.findViewById(R.id.add_name);
                EditText contNumb = dialog.findViewById(R.id.add_num);
                ImageView contImg = dialog.findViewById(R.id.add_img);

                contImg.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Imagepick();
                        contImg.setImageBitmap(imgdata);
                    }
                });

                Button btn = dialog.findViewById(R.id.action_btn);
                btn.setText("Update");
                addContLogo.setText("Update Contact");

                contName.setText(arrContact.get(position).name);
                contNumb.setText(arrContact.get(position).numb);

                btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        String name = "" , numb = "";

                        if (!contName.getText().toString().equals("")){
                            name = contName.getText().toString();
                        }else {
                            Toast.makeText(context, "Enter name ", Toast.LENGTH_SHORT).show();
                        }

                        if (!contNumb.getText().toString().equals("")){
                            numb = contNumb.getText().toString();
                        }
                        else {
                            Toast.makeText(context, "Enter number", Toast.LENGTH_SHORT).show();
                        }
                            arrContact.set(position,new ContactModel(arrContact.get(position).img,name,numb));
                        notifyItemChanged(position);
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

        holder.llRow.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(context)
                        .setTitle("Delete Contact")
                        .setMessage("Are you sure you want to delete")
                        .setIcon(R.drawable.baseline_delete_24)
                        .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            arrContact.remove(position);
                            notifyItemRemoved(position);
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });

                    builder.show();
                return true;
            }
        });



    }

    @Override
    public int getItemCount() {
        return arrContact.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtName , txtNumb;
        ImageView imgContact;

        LinearLayout llRow;

        public ViewHolder(@NonNull View itemView) {

            super(itemView);

            txtName = itemView.findViewById(R.id.Contact_name);
            txtNumb = itemView.findViewById(R.id.Contact_number);
            imgContact = itemView.findViewById(R.id.Contact_Img);
            llRow = itemView.findViewById(R.id.llRow);




        }
    }

    private void setAnimation (View viewToSetAnimation , int Position ){
        if (Position > lastPosition) {

            Animation slideIn = AnimationUtils.loadAnimation(context, R.anim.set_anim);
            viewToSetAnimation.startAnimation(slideIn);
            lastPosition = Position;

        }
    }
    private void Imagepick(){

        Intent intent = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        intent.setType("image/*");
        intent.putExtra("crop", "true");
        intent.putExtra("scale", true);
        intent.putExtra("outputX", 256);
        intent.putExtra("outputY", 256);
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("return-data", true);
//        startActivityForResult(intent, 1);

    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//        if (resultCode != RESULT_OK) {
//            return;
//        }
//        if (requestCode == 1) {
//            final Bundle extras = data.getExtras();
//            if (extras != null) {
//                //Get image
//                imgdata = extras.getParcelable("data");
//
//            }
//        }
//    }










}
