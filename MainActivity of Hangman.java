package com.google.hangman;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    Random r = new Random();
    String[] word = new String[5];
    int score=0;
    int cnt=0;
    int summa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //here i have only 5 words
        final int wno = r.nextInt(4);  //to select any 1 of the 5 words
        word[0]="dizzying";
        word[1]="espionage";
        word[2]="bagpiper";
        word[3]="numbskull";
        word[4]="insensitive";

        final TextView tv1 = findViewById(R.id.textView1);
        final TextView tv2 = findViewById(R.id.textView2);
        tv2.setText("Score: 0");
        final TextView tv3 = findViewById(R.id.textView3);
        tv1.setText("Highscore: -");
        final EditText et = findViewById(R.id.editText);
        Button b = findViewById(R.id.button);

        tv1.setText("_");

        for (int i=1; i<word[wno].length() ; i++)
          tv1.append(" _");

        tv1.setTextSize(30);

        b.setOnClickListener(new View.OnClickListener() {

            int s;  //to check whether the typed word is there or not in the original word
            @Override

            public void onClick(View view) {
                char c = et.getText().charAt(0);
                StringBuilder tvs = new StringBuilder(tv1.getText().toString());

                //for removing the underscores incase the character we typed is in the original word
                s=0;
                for(int i=0; i<word[wno].length() ; i++)
                    if (c == word[wno].charAt(i))
                    {
                        s=1;
                         if (i==0)
                          tvs.setCharAt(0,c);
                         else
                             tvs.setCharAt(i+i,c);
                    }


                if (s==0)
                {
                    score++;
                    ImageView im = findViewById(R.id.imageView);
                    Toast.makeText(MainActivity.this, "Try again!!!", Toast.LENGTH_SHORT).show();
                    s=0;

                    //for displaying the image of the hangman everytime we guess the word wrong
                    if (score==1)
                       im.setImageResource(R.drawable.hman2);
                    if (score==2)
                        im.setImageResource(R.drawable.hman3);
                    if (score==3)
                        im.setImageResource(R.drawable.hman4);
                    if (score==4)
                        im.setImageResource(R.drawable.hman5);
                    if (score==5)
                        im.setImageResource(R.drawable.hman6);
                    if (score==6)
                    {
                        Toast.makeText(MainActivity.this, "The man is DEAD !!!", Toast.LENGTH_SHORT).show();
                        im.setImageResource(R.drawable.hmanfull);
                    }

                }



                tv1.setText(tvs);
                tv2.setText("Score:");
                tv2.append(" "+score);
                String tvs2 = tvs.toString();

                int summa=0;

                for (int d=0; d<tvs2.length(); d++)
                    if (tvs2.charAt(d)=='_')
                        summa++;
                if (summa==0)
                {
                    cnt++;
                    //for saving the highscore
                    SharedPreferences sp = getSharedPreferences("Saveinfo", Context.MODE_PRIVATE);
                    SharedPreferences.Editor e = sp.edit();
                    if(cnt==1)
                        e.putInt("score",score);
                    if(cnt>1)
                        summa = sp.getInt("score",0);
                    if(cnt>1 && summa>score)
                        e.putInt("score",score);
                    e.apply();
                    Toast.makeText(MainActivity.this, "CONGRATULATIONS !!!", Toast.LENGTH_LONG).show();
                }



            }

        });




    }

    @Override
    protected void onResume() {
        super.onResume();

        //for retreiving the highscore
        SharedPreferences sp = getSharedPreferences("Saveinfo",Context.MODE_PRIVATE);
        int hscore;
        hscore = sp.getInt("score",0);

        TextView tv3 = findViewById(R.id.textView3);
        tv3.setText("Highscore:");
        tv3.append(" "+hscore);


    }



}
