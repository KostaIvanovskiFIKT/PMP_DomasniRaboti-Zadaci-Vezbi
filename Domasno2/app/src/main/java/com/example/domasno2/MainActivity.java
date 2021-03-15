package com.example.domasno2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.Scanner;

public class MainActivity extends AppCompatActivity {

    public Button btnSearch, btnInsert;
    public EditText edtTxtMkd, edtTxtEng, edtTxtSearch;
    public TextView textViewMkd, textViewMkdRezult, textViewEng, textViewEngRezult;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();


        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String searchZbor=edtTxtSearch.getText().toString();
                String defn = readDefinition(searchZbor);
                TextView definition = (TextView) findViewById(R.id.definition);
                definition.setText(defn);
            }

            private String readDefinition(String zbor){
                String defaultReturn="Nema takov zbor";
                Scanner scan = null;
                try {
                    scan = new Scanner(openFileInput("recnik.txt"));
                    while (scan.hasNextLine()){
                        String line = scan.nextLine();
                        String[] pieces=line.split("/t");
                        String mak=pieces[0];
                        String ang=pieces[1];
                        if (mak.equals(zbor.trim())) {
                            textViewMkdRezult.setText(mak);
                            textViewEngRezult.setText(ang);
                            return ang;
                        }
                        if (ang.equals(zbor.trim())) {
                            textViewMkdRezult.setText(mak);
                            textViewEngRezult.setText(ang);
                            return mak;
                        }
                    }
                    scan.close();
                    return defaultReturn;
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                return defaultReturn;
            }
        });


        btnInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    PrintStream output = new PrintStream(openFileOutput("recnik.txt", MODE_PRIVATE));
                    output.println(edtTxtMkd.getText()+"/t"+edtTxtEng.getText());
                    output.close();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                edtTxtMkd.setText("");
                edtTxtEng.setText("");
            }
        });
        }


    public void initViews(){
        edtTxtEng = (EditText) findViewById(R.id.edtTxtEng);
        edtTxtMkd = (EditText) findViewById(R.id.edtTxtMkd);
        edtTxtSearch = (EditText) findViewById(R.id.edtTxtSearch);

        btnSearch = (Button) findViewById(R.id.btnSearch);
        btnInsert = (Button) findViewById(R.id.btnInsert);

        textViewMkd = (TextView) findViewById(R.id.textViewMkd);
        textViewMkdRezult = (TextView) findViewById(R.id.textViewMkdRezult);
        textViewEng = (TextView) findViewById(R.id.textViewEng);
        textViewEngRezult = (TextView) findViewById(R.id.textViewEngRezult);
    }
}