package com.example.gym_otomasyon_java;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import  android.os.Bundle;
import androidx.appcompat.app.AppCompatDialogFragment;


public class InfoDialog extends AppCompatDialogFragment {
    @Override
    public  Dialog onCreateDialog(Bundle savedInstanceState){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setTitle("BİLGİ")
                    .setMessage("BAHTİYAR KARAKOÇ\nStaj Ödevi")

                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
        // builder.create();
        AlertDialog dialog = builder.create();
        //dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.GREEN));
        dialog.getWindow().setBackgroundDrawableResource(R.drawable.gradient_background);


    return  dialog;

    }


}
