package com.example.myapplication;

import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.*;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    Button but;
    Switch sw1;
    Switch sw2;
    ScrollView derou;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        int SDK_INT = android.os.Build.VERSION.SDK_INT;
        if (SDK_INT > 8)
        {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                    .permitAll().build();
            StrictMode.setThreadPolicy(policy);
            //your codes here

        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.v("Lancemen", "reussi");
        but = (Button) findViewById(R.id.button);
        sw1 = (Switch) findViewById(R.id.switch1);
        sw2 = (Switch) findViewById(R.id.switch2);
        derou = (ScrollView) findViewById(R.id.deroule);
        but.setEnabled(false);
        sw1.setChecked(false);
        sw1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Log.v("Switch State=", ""+isChecked);
                but.setEnabled(true);
                Log.v("Bouton=", ""+isChecked);
            }
        });
        but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(sw1.isChecked()){
                    String test = "https://www.offi.fr/cinema/filmotheque-quartier-latin-2323.html";
                    Log.v("cine 1", "check");
                    ArrayList<String> dates = new ArrayList();
                    dates.add("22/05");
                    dates.add("23/05");
                    getSoup(test,"22/05");
                }else if(sw2.isChecked()){
                    String test = "https://www.offi.fr/cinema/filmotheque-quartier-latin-2323.html";
                    Log.v("cine 2"," check");
                    getSoup(test);                }
            }
        });
    }



    private void getSoup(String a) {
        Document doc = null;
        StringBuilder sb = new StringBuilder();
        TextView tv = new TextView(this);
derou.removeAllViews();

        try {
            doc = Jsoup.connect(a).get();
            Elements li = doc.select("div.progArrondissement");
            Elements el = doc.select("div.progArrondissement ~ div.progTabBlock");

            for( Element e : li) {
                int v = li.indexOf(e);

                    sb.append("---------------");
                    sb.append("\nTitre : " + e.text() + "\n");


                    for (Element e1 : el.eq(v)) {

                        for (int i = 0; i < e1.select("a").size(); i++) {
                                sb.append("\n" + e1.select("a").get(i).text() + "  ");
                                sb.append(e1.select("p").get(i).text() + "\n");
                                sb.append("\n");

                        }
                    }
                    sb.append("---------------");
                }


        } catch (IOException e) {
            e.printStackTrace();
        }
        tv.setText(sb);
        derou.addView(tv);
    }

    private void getSoup(String a, String b) {
        Document doc = null;
        StringBuilder sb = new StringBuilder();
        TextView tv = new TextView(this);
        derou.removeAllViews();

        try {
            doc = Jsoup.connect(a).get();
            Elements li = doc.select("div.progArrondissement");
            Elements el = doc.select("div.progArrondissement ~ div.progTabBlock");

            for( Element e : li) {
                int v = li.indexOf(e);

                if (el.get(v).select("a").text().contains(b)) {
                    Log.v("b is : ", b.toString());
                    sb.append("---------------");
                    sb.append("\nTitre : " + e.text() + "\n");


                    for (Element e1 : el.eq(v)) {

                        for (int i = 0; i < e1.select("a").size(); i++) {
                            if (e1.select("a").get(i).text().contains(b)){
                                sb.append("\n" + e1.select("a").get(i).text() + "  ");
                            sb.append(e1.select("p").get(i).text() + "\n");
                            sb.append("\n");
                        }
                        }
                    }
                    sb.append("---------------");
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        tv.setText(sb);
        derou.addView(tv);
    }
}