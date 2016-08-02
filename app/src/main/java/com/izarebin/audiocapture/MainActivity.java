package com.izarebin.audiocapture;

import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    Button btnrecord,btnstop,btnplay;
    MediaRecorder audiocapture;
    String outputfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.audio_capture);

        btnrecord = (Button) findViewById(R.id.btnrecord);
        btnstop = (Button) findViewById(R.id.btnstop);
        btnplay = (Button) findViewById(R.id.btnplay);

        btnplay.setEnabled(false);
        btnstop.setEnabled(false);
        outputfile = Environment.getExternalStorageDirectory().getAbsolutePath() + "/recording.3gp";

        audiocapture = new MediaRecorder();
        audiocapture.setAudioSource(MediaRecorder.AudioSource.MIC);
        audiocapture.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        audiocapture.setAudioEncoder(MediaRecorder.OutputFormat.AMR_NB);
        audiocapture.setOutputFile(outputfile);

        btnrecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    audiocapture.prepare();
                    audiocapture.start();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                btnplay.setEnabled(false);
                btnstop.setEnabled(true);
                Toast.makeText(MainActivity.this, "ضبط صدا آغاز شد", Toast.LENGTH_SHORT).show();
            }
        });

        btnstop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                audiocapture.stop();
                audiocapture.release();
                audiocapture  = null;

                btnstop.setEnabled(false);
                btnplay.setEnabled(true);
                Toast.makeText(MainActivity.this, "صدای ضبط شده ذخیره گردید", Toast.LENGTH_SHORT).show();
            }
        });

        btnplay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MediaPlayer md = new MediaPlayer();
                try {
                    md.setDataSource(outputfile);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    md.prepare();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                md.start();
            }
        });
    }
}
