package com.example.gym_otomasyon_java;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.pdf.PdfDocument;
import android.os.BaseBundle;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
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
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import static androidx.viewpager.widget.PagerAdapter.POSITION_NONE;


public class UserProfile extends AppCompatActivity {

    FragmentTransaction transaction;
    FragmentManager manager;
    ImageView button_qr_scan;

    TextInputEditText gelen_sifrem;
    TextInputEditText onceki_sifrem;

    DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");

    protected  void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Window g = getWindow();
        // g.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION,WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        g.setNavigationBarColor(this.getResources().getColor(R.color.gradyan3));


        g.setStatusBarColor((this.getResources().getColor((R.color.gradyan4))));



        setContentView(R.layout.user_profile_lay);




        button_qr_scan = (ImageView)  findViewById(R.id.qr_scan_button);



        manager = getSupportFragmentManager();
         transaction = manager.beginTransaction();


        TabItem profil_sekme = findViewById(R.id.profile_tab);
        TabItem qr_sekme = findViewById(R.id.qr_okuyucu);
        TabItem ayarlar_sekme = findViewById(R.id.ayarlar_tab);






        final ViewPager2 viewPager2 = (ViewPager2) findViewById(R.id.viev_pager);



        viewPager2.setAdapter(new PagerAdapter(this, 3));
        final TabLayout tabLayout = findViewById(R.id.tabBar);
        viewPager2.setUserInputEnabled(false);


        tabLayout.addOnTabSelectedListener(new TabLayout.BaseOnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager2.setCurrentItem(tab.getPosition());


            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {


            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });



    }

    public String name_Pass() {

        Intent intent = getIntent();

        String tamisim_intent = intent.getStringExtra("isim");

        return tamisim_intent;
    }

    public String gender_Pass() {

        Intent intent = getIntent();

        String cinsiyet_intent = intent.getStringExtra("cinsiyet");

        return cinsiyet_intent;
    }


    public String age_Pass(){

        Intent intent = getIntent();

        String yas_intent = intent.getStringExtra("yas_user");

        return yas_intent;

    }


    public String height_Pass(){
        Intent intent = getIntent();

        String boy_intent = intent.getStringExtra("boy_user");

        return boy_intent;
  }


    public String weight_Pass(){
        Intent intent = getIntent();

        String kilo_intent = intent.getStringExtra("kilo_user");

        return kilo_intent;

   }


    public String fat_Pass(){
        Intent intent = getIntent();

        String yag_intent = intent.getStringExtra("yag_user");

        return yag_intent;

  }

    public String id_Pass(){
        Intent intent = getIntent();

        String id_intent = intent.getStringExtra("id");

        return id_intent;

    }


    public String sifre_Pass(){

        Intent intent = getIntent();

        String sifre_intent = intent.getStringExtra("sifre");

        return sifre_intent;

    }



    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }





    public void onClick(View view) {

        scanQr();

    }

    private void scanQr(){

        IntentIntegrator integrator = new IntentIntegrator(this);
        integrator.setCaptureActivity(cameraActionqr.class);
        integrator.setOrientationLocked(false);
        integrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
        integrator.setPrompt("QR-Code Değeri");
        integrator.initiateScan();

    }


    public  void  onActivityResult(int requestCode, int resultCode, Intent data){

        final IntentResult result = IntentIntegrator.parseActivityResult(requestCode,resultCode,data);
        if (result != null){
            if (result.getContents() != null){

                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                result.getContents();
                builder.setMessage(result.getContents());
                builder.setTitle("QR Sonucu");
                builder.setPositiveButton("TEKRAR", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        scanQr();
                    }
                }).setNegativeButton("KAPAT", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //finish();
                        TextView agirlikDegeri = (TextView) findViewById(R.id.agirlik_degeri);
                        agirlikDegeri.setText(result.getContents());
                        String idDown = id_Pass().toString();
                        reference.child(idDown).child("agirlik_user").setValue(result.getContents());
                    }
                });

               AlertDialog dialog = builder.create();
                dialog.show();



            }

            else {
                Toast.makeText(this,"Sonuc Yok", Toast.LENGTH_LONG).show();
            }


        }else {
            super.onActivityResult(requestCode,resultCode,data);
        }


    }


    public void sifreDegistir(View view) {

        gelen_sifrem =  (TextInputEditText) findViewById(R.id.new_sifre);
        onceki_sifrem = (TextInputEditText) findViewById(R.id.firs_sifre);

        final String kontrolDegeri = onceki_sifrem.getText().toString();


        final Query checkUser = reference.orderByChild("id").equalTo(id_Pass());


        checkUser.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {



                if (dataSnapshot.exists()) {


                    String passwordFromdb = dataSnapshot.child(id_Pass()).child("sifre").getValue(String.class);



                    if (passwordFromdb.equals(kontrolDegeri)) {
                        Toast.makeText(UserProfile.this, "Başarıyla Değiştirildi !",
                                Toast.LENGTH_SHORT).show();

                        reference.child(id_Pass()).child("sifre").setValue(gelen_sifrem.getText().toString());

                    }


                    else {
                        onceki_sifrem.setError("YANLIŞ ŞİFRE");
                        onceki_sifrem.requestFocus();
                    }

                }
                else {
                    gelen_sifrem.setError("Kullanici Bulunamadi !");
                    gelen_sifrem.requestFocus();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
















}
