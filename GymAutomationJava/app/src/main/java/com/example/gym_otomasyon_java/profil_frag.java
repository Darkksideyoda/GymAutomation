package com.example.gym_otomasyon_java;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.content.Intent;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import org.w3c.dom.Text;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link profil_frag#newInstance} factory method to
 * create an instance of this fragment.
 */
public class profil_frag extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public profil_frag() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment profil_frag.
     */
    // TODO: Rename and change types and number of parameters
    public static profil_frag newInstance(String param1, String param2) {
        profil_frag fragment = new profil_frag();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);


        }



    }

    TextView names, gender, age,heigy,weight,fat;
    TextView id_hander;
    ImageView qr_id;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.profil_fragment, container, false);

        View view = inflater.inflate(R.layout.profil_fragment, container, false);
/*
        String strtext = getArguments().getString("ad");
        textView = (TextView) view.findViewById(R.id.user_show_name);
        textView.setText(strtext);
*/


        UserProfile activity = (UserProfile) getActivity();


        String isim = activity.name_Pass();
        String cinsiyet = activity.gender_Pass();
        String yas = activity.age_Pass();
        String boy = activity.height_Pass();
        String kilo = activity.weight_Pass();
        String yag = activity.fat_Pass();
        String id = activity.id_Pass();


        names = (TextView)  view.findViewById(R.id.user_show_name);
        gender = (TextView) view.findViewById(R.id.user_show_yas);
        age = (TextView)  view.findViewById(R.id.user_show_cinsiyet);
        heigy = (TextView)  view.findViewById(R.id.user_show_boy);
        weight = (TextView)  view.findViewById(R.id.user_show_kilo);
        fat = (TextView)  view.findViewById(R.id.user_show_yag);
        id_hander = (TextView) view.findViewById(R.id.id_tutucu);
        qr_id = (ImageView) view.findViewById(R.id.qr_shower);



        names.setText(isim);
        gender.setText(cinsiyet);
        age.setText(yas);
        heigy.setText(boy);
        weight.setText(kilo);
        fat.setText(yag);


        MultiFormatWriter multiFormatWriter = new MultiFormatWriter();

        try {
            BitMatrix bitMatrix = multiFormatWriter.encode(id, BarcodeFormat.QR_CODE,90,90);
            BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
            Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
            qr_id.setImageBitmap(bitmap);

        }catch (Exception e){
            e.printStackTrace();
        }


        return view;






    }


}