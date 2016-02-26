package com.vb.aptiquiz;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.beardedhen.androidbootstrap.BootstrapButton;

public class Check_Question extends AppCompatActivity implements View.OnClickListener {

    BootstrapButton b;
    TextView tv;
    int u[] = new int[30];
    String s, t = "";
    char ch;
    TextView urAns, corAns;
    String uAns, cAns;
    TextView corAnslabel;
    final static String KEY_TABLE_NAME_VB = "verbaltest";
    final static String KEY_NO = "no.";
    final static String KEY_U = "u";
    final static String KEY_PARA = "paragraph";
    final static String KEY_TEST = "test";
    final static String KEY_TABLE_NAME_APTI = "aptitest";
    final static String KEY_DB = "project";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);
        b = (BootstrapButton) findViewById(R.id.button1);
        b.setOnClickListener(this);

        tv = (TextView) findViewById(R.id.textView1);
        urAns = (TextView) findViewById(R.id.urans);
        corAns = (TextView) findViewById(R.id.corans);
        corAnslabel = (TextView) findViewById(R.id.corAnsText);

        Intent in = getIntent();
        Bundle b = in.getExtras();
        ch = b.getChar(KEY_TEST);
        if (ch == 'a')
            loadQuestion(b.getInt(KEY_NO), b, KEY_TABLE_NAME_APTI);

        else
            loadQuestion(b.getInt(KEY_NO), b, KEY_TABLE_NAME_VB);
    }


    public void loadQuestion(int n, Bundle b, String nm) {

        SQLiteDatabase db;
        db = openOrCreateDatabase(KEY_DB, SQLiteDatabase.CREATE_IF_NECESSARY, null);
        Cursor c = db.query(nm, null, null, null, null, null, null);

        if (b.getIntArray(KEY_U) != null) {
            if (b.getChar(KEY_TEST) == 'a')
                s = b.getString(KEY_PARA);

            u = b.getIntArray(KEY_U);
        }

        if (c.moveToPosition(n - 1)) {
            String s1 = c.getString(1);
            String s2 = c.getString(2);
            String s3 = c.getString(3);
            String s4 = c.getString(4);
            String s5 = c.getString(5);
            String s6 = c.getString(6);
            String s7 = c.getString(7);

            if ((n == 26 || n == 27 || n == 28 || n == 29 || n == 30) && b.getChar(KEY_TEST) == 'a') {
                t = "Paragraph :\n" + s + "\n\n" + "Question :  " + s1;
            } else {
                t = "" + s1;
            }

            if (u[n - 1] == 1) {
                uAns = "" + s2;
            } else if (u[n - 1] == 2) {
                uAns = "" + s3;
            } else if (u[n - 1] == 3) {
                uAns = "" + s4;
            } else if (u[n - 1] == 4) {
                uAns = "" + s5;
            } else if (u[n - 1] == 5) {
                uAns = "" + s6;
            } else {
                uAns = "-";
                corAns.setVisibility(View.GONE);
                corAnslabel.setVisibility(View.GONE);
            }

            Integer i = Integer.parseInt(s7);
            if (i == 1) {
                cAns = "" + s2;
            } else if (i == 2) {
                cAns = "" + s3;
            } else if (i == 3) {
                cAns = "" + s4;
            } else if (i == 4) {
                cAns = "" + s5;
            } else if (i == 5) {
                cAns = "" + s6;
            }
            tv.setMovementMethod(new ScrollingMovementMethod());
            tv.setText(t);
            urAns.setText(uAns);
            corAns.setText(cAns);

        }

        c.close();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            this.finish();
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        this.finish();
    }

    @Override
    public void onClick(View v) {
        AlertDialog.Builder ad = new AlertDialog.Builder(this);
        ad.setIcon(R.drawable.ic_warning_black_24dp);
        ad.setTitle(getString(R.string.confirm_ad));
        ad.setMessage(getString(R.string.alert_quit));
        ad.setIcon(android.R.drawable.stat_notify_error);
        ad.setNegativeButton("Ok", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                SQLiteDatabase db = openOrCreateDatabase("project", 0, null);
                db.execSQL("drop table if exists " + KEY_TABLE_NAME_APTI);
                db.execSQL("drop table if exists " + KEY_TABLE_NAME_VB);
                db.close();
                finishAffinity();
            }
        });

        ad.setPositiveButton("Cancel", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        ad.show();
    }
}
