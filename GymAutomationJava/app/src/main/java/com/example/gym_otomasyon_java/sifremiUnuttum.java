package com.example.gym_otomasyon_java;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.nio.charset.Charset;
import java.util.Random;
import java.util.concurrent.TimeUnit;


public class sifremiUnuttum extends AppCompatActivity {

    TextInputEditText mailBoxNew, IdBoxNew;
    ImageView sendMailFree;

    DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");

    protected  void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Window g = getWindow();
        // g.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION,WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        g.setNavigationBarColor(this.getResources().getColor(R.color.gradyan3));


        g.setStatusBarColor((this.getResources().getColor((R.color.gradyan4))));


        setContentView(R.layout.sifremi_unuttum);

      //  sendMailFree = (ImageView) findViewById(R.id.sendMail);


    }


    public void sendMail(View view) {

        mailBoxNew = (TextInputEditText) findViewById(R.id.mailBox);
        IdBoxNew = (TextInputEditText) findViewById(R.id.idBox_pass);


        final  String userEnteredMail = mailBoxNew.getText().toString();

        final  String userEnteredId = IdBoxNew.getText().toString();




        if (userEnteredId.isEmpty() || userEnteredMail.isEmpty()) {

            Toast.makeText(sifremiUnuttum.this, "LÜTFEN TÜM ALANLARI DOLDURUNUZ !",
                    Toast.LENGTH_SHORT).show();
        }

        else{




            final Query checkUser = reference.orderByChild("id").equalTo(userEnteredId);


            checkUser.addListenerForSingleValueEvent(new ValueEventListener() {

                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {



                    if (dataSnapshot.exists()) {


                        String mailFromDb = dataSnapshot.child(userEnteredId).child("mail_user").getValue(String.class);
                        String idFromDb = dataSnapshot.child(userEnteredId).child("id").getValue(String.class);


                        if (idFromDb.equals(userEnteredId)&&mailFromDb.equals(userEnteredMail)) {



                                Toast.makeText(sifremiUnuttum.this, "YENİ ŞİFRE MAIL GONDERİLDİ", Toast.LENGTH_SHORT).show();
                                reference.child(userEnteredId).child("sifre").setValue("1234");
                                JavaMailAPI javaMailAPI = new JavaMailAPI(sifremiUnuttum.this,mailFromDb,"YENİ ŞİFRE",randomSifre(5));
                                javaMailAPI.execute();



                            try {
                                TimeUnit.SECONDS.sleep(1);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            Toast.makeText(sifremiUnuttum.this, "YENİ ŞİFRE 1234", Toast.LENGTH_SHORT).show();
                            finish();

                        }



                        else {
                            IdBoxNew.setError("Kullanıcı Bulunamadı !\n Şifre Yada Mail Yanlış");
                            IdBoxNew.requestFocus();
                        }

                    }
                    else {
                        mailBoxNew.setError("Kullanıcı Bulunamadı !\n Şifre Yada Mail Yanlış");
                        mailBoxNew.requestFocus();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });







        }



    }

    public String randomSifre(int length){


        byte[] array = new byte[length];
        new Random().nextBytes(array);
        String generatedString = new String(array, Charset.forName("UTF-8"));
        return  generatedString;

    }




}
