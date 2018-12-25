package com.example.assignment2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;


public class ShowList extends AppCompatActivity {

    public static ArrayList<String> record = new ArrayList<String>() ;
    public static String name = "No Record To Show";
    public static String number = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_list);
        showData();
    }

    public void showData() {
        int count = 0;
        record.clear();
        try {
//            File file = new File("again12.txt");
            FileInputStream fis = openFileInput("listFile.txt");
            InputStreamReader inputStreamReader = new InputStreamReader(fis);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            StringBuffer stringBuffer = new StringBuffer();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                StringTokenizer tokens = new StringTokenizer(line,",");
                String two ;
                if(tokens.hasMoreElements()){
                    name = tokens.nextToken();
                    two = tokens.nextToken();
                    number = tokens.nextToken();
                }
                String concatenate = name + "  " + number;
                record.add(concatenate);

                count +=1;
            }
            inputStreamReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        ArrayAdapter adapter = new ArrayAdapter<String>(this,
                R.layout.activity_listview, record);

        ListView listView = (ListView) findViewById(R.id.list_view);
        listView.setAdapter(adapter);

    }
}
