package com.fjord.podrozni;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

/**
 * Created by fjord on 31.03.2017.
 */

public class Pop extends Activity {

    TextView title, info, infoShort;
    ImageView img;
    Events event;
    Integer notifyMinsBefore = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.details);
        Intent intent = getIntent();
        event = (Events) intent.getSerializableExtra("Details");

        title =(TextView) findViewById(R.id.tvTitle);
        info = (TextView) findViewById(R.id.tvInfo);
        infoShort = (TextView) findViewById(R.id.tvInfoShort);
        img = (ImageView) findViewById(R.id.titleImage);
        title.setText(event.getTitle());
        infoShort.setText(event.getInfoShort());
        info.setText(event.getInfo());
        info.setMovementMethod(new ScrollingMovementMethod());
        String imgUrl = event.getImage();
        Picasso.with(this).load(imgUrl).into(img, new Callback() {
            @Override
            public void onSuccess() {
                findViewById(R.id.picassoPB).setVisibility(View.GONE);
            }

            @Override
            public void onError() {

            }
        });


        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int)(width*.8),(int)(height*.8));
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.dimAmount = 0.7f;
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        Button remindBtn = (Button) findViewById(R.id.remindBtn);
        Button mapBtn = (Button) findViewById(R.id.mapBtn);
        Button shareBtn = (Button) findViewById(R.id.shareBtn);

        remindBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showReminderPop();
            }
        });
        mapBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showMapPop();
            }
        });
        shareBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                String shareBody = "Pozdrowienia z #FestiwalPodRóżni. Właśnie trwa : " + event.getTitle();
                sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "#FestiwalPodRóżni");
                sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
                startActivity(Intent.createChooser(sharingIntent, "Podziel się!"));
            }
        });

    }

    private void showMapPop() {
        Dialog settingsDialog = new Dialog(this);
        settingsDialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        settingsDialog.setContentView(getLayoutInflater().inflate(R.layout.map
                , null));
        settingsDialog.show();
        ImageView map = (ImageView)  settingsDialog.findViewById(R.id.map_file);
        Picasso.with(this).load(event.getMap()).into(map);
    }

    void showReminderPop(){


        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View seekBarLayout = inflater.inflate(R.layout.reminder_dialog,null);


        builder.setTitle(R.string.dialog_title)
                .setView(seekBarLayout)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent=new Intent();

                        long remindTime =  notifyMinsBefore *1000*60;
                        intent.putExtra("TIME",String.valueOf(remindTime));
                        setResult(2,intent);
                    }
                })
                .setNegativeButton("Jednak nie", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent=new Intent();
                        intent.putExtra("TIME","0");
                        setResult(2,intent);

                    }
                });



        // 3. Get the AlertDialog from create()
        AlertDialog dialog = builder.create();
        dialog.getWindow().getAttributes().verticalMargin = 0.15F;
        dialog.getWindow().setBackgroundDrawableResource(R.drawable.rounded_background);

        SeekBar seekBar = (SeekBar) seekBarLayout.findViewById(R.id.seekBar);
        final TextView textView = (TextView) seekBarLayout.findViewById(R.id.minutesTV);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                notifyMinsBefore = progress + 5;
               textView.setText(notifyMinsBefore + " minut przed.");


            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        dialog.show();
    }




}
