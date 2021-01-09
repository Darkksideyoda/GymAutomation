package com.example.gym_otomasyon_java;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {




    ImageView giris_button;
    ImageView bilgi_button;

    long animationDuration = 1000;

    AnimatorSet animatorSet1;

    ImageView sifre_unutum_button;

    AnimatorSet animatorSet2;
   // TextInputLayout

    TextInputEditText id_box,sifre_box;
    FirebaseDatabase rootNode;
    DatabaseReference reference;






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Window g = getWindow();
       // g.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION,WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        g.setNavigationBarColor(this.getResources().getColor(R.color.gradyan2));


        g.setStatusBarColor((this.getResources().getColor((R.color.gradyan1))));
        setContentView(R.layout.activity_main);
        giris_button = (ImageView)findViewById(R.id.log_image);
        bilgi_button = (ImageView)findViewById(R.id.info_viev);
        sifre_unutum_button= (ImageView)findViewById(R.id.sifre_button);
        id_box = findViewById(R.id.id_real);
        sifre_box = findViewById(R.id.pass_real);
        reference = FirebaseDatabase.getInstance().getReference().child("Users");


        ////////////////////////////////////butongirisanimasyon///////////////////
        animatorSet1 = new AnimatorSet() ;
        ObjectAnimator fadeout = ObjectAnimator.ofFloat(giris_button,"alpha",1f,0.1f);
        fadeout.setDuration(1000);
        ObjectAnimator fadein = ObjectAnimator.ofFloat(giris_button,"alpha",0.1f,1f);
        animatorSet1.play(fadein).after(fadeout);
        animatorSet1.addListener(new AnimatorListenerAdapter() {

        @Override
            public void onAnimationEnd(Animator animation){
            super.onAnimationEnd(animation);

        animatorSet1.start();
        }


        });

        animatorSet1.start();
        ///////////////////////////////////////////////////////////////////////////////
        /////////////////buton sifre unuttum animasyon////////////////////////////////

        animatorSet2 = new AnimatorSet() ;
        ObjectAnimator fadeout1 = ObjectAnimator.ofFloat(sifre_unutum_button,"alpha",1f,0.1f);
        fadeout1.setDuration(1000);
        ObjectAnimator fadein1 = ObjectAnimator.ofFloat(sifre_unutum_button,"alpha",0.1f,1f);
        animatorSet2.play(fadein1).after(fadeout1);
        animatorSet2.addListener(new AnimatorListenerAdapter() {

            @Override
            public void onAnimationEnd(Animator animation){
                super.onAnimationEnd(animation);

                animatorSet2.start();
            }


        });

        animatorSet2.start();







        ///////////////////////////////////////////////////////////////////////////////////////////
    }




    public void onClick(View view) {


       /* ObjectAnimator animatorY = ObjectAnimator.ofFloat(giris_button,"y",300f);
        animatorY.setDuration(animationDuration);
        ObjectAnimator alpha = ObjectAnimator.ofFloat(giris_button,View.ALPHA,1.0f,0.0f);
        alpha.setDuration(animationDuration);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(animatorY,alpha);
        animatorSet.start();*/
       // Kullanici_kontrol();

       /* finish();
        System.exit(0);*/


        Kullanici_kontrol();







    }

    public  void Show_Info(){
        InfoDialog info = new InfoDialog();
        info.show(getSupportFragmentManager(),"exaple");
    }

    public void onClick1(View view) {
           Show_Info();
    }


    public void Kullanici_kontrol(){




          final  String userEnteredUsername = id_box.getText().toString().trim();

          final  String userEnteredPassword = sifre_box.getText().toString().trim();




       if (userEnteredPassword.isEmpty() || userEnteredUsername.isEmpty()) {

            Toast.makeText(MainActivity.this, "LÜTFEN TÜM ALANLARI DOLDURUNUZ !",
                    Toast.LENGTH_SHORT).show();
        }

       else{


        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");

        final Query checkUser = reference.orderByChild("id").equalTo(userEnteredUsername);


        checkUser.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {



                if (dataSnapshot.exists()) {


                    String passwordFromdb = dataSnapshot.child(userEnteredUsername).child("sifre").getValue(String.class);



                    if (passwordFromdb.equals(userEnteredPassword)) {
                        Toast.makeText(MainActivity.this, "GIRIS YAPILDI",
                                Toast.LENGTH_SHORT).show();


                        String nameFromDb = dataSnapshot.child(userEnteredUsername).child("isim").getValue(String.class);
                        String genderFromDb = dataSnapshot.child(userEnteredUsername).child("cinsiyet").getValue(String.class);
                        String yasFromDb = dataSnapshot.child(userEnteredUsername).child("yas_user").getValue(String.class);
                        String boyFromDb = dataSnapshot.child(userEnteredUsername).child("boy_user").getValue(String.class);
                        String kiloFromDb = dataSnapshot.child(userEnteredUsername).child("kilo_user").getValue(String.class);
                        String yag_FromDb = dataSnapshot.child(userEnteredUsername).child("yag_user").getValue(String.class);
                        String id_FromDb = dataSnapshot.child(userEnteredUsername).child("id").getValue(String.class);
                        String sifreFromDb = dataSnapshot.child(userEnteredUsername).child("sifre").getValue(String.class);



                        Intent intent = new Intent(MainActivity.this,UserProfile.class);



                        intent.putExtra("isim",nameFromDb);
                        intent.putExtra("cinsiyet",genderFromDb);
                        intent.putExtra("yas_user",yasFromDb);
                        intent.putExtra("boy_user",boyFromDb);
                        intent.putExtra("kilo_user",kiloFromDb);
                        intent.putExtra("yag_user",yag_FromDb);
                        intent.putExtra("id",id_FromDb);
                        intent.putExtra("sifre",nameFromDb);


                       startActivity(intent);


                    }


                    else {
                        sifre_box.setError("YANLIŞ ŞİFRE");
                        sifre_box.requestFocus();
                    }

                }
                else {
                    id_box.setError("Kullanici Bulunamadi !");
                    id_box.requestFocus();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });







    }



    }


    public void sifremiUnuttum(View view) {

        Intent intent = new Intent(MainActivity.this,sifremiUnuttum.class);

        startActivity(intent);


    }
}