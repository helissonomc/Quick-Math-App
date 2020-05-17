package com.example.braintrainer;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import androidx.gridlayout.widget.GridLayout;

import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    TextView timerTextView;
    TextView questionTextView;
    TextView scoreTextView;
    TextView finalScoreTextView;
    boolean isRunning = false;
    int result;
    int score=0,rounds=0;
    ArrayList<Integer> options = new ArrayList<Integer>();
    GridLayout gridLayout;
    Random r = new Random();
    String question;
    Button button;

    public void updateScore(){
        scoreTextView.setText(Integer.toString(score)+"/"+Integer.toString(rounds));
    }

    public void generateQuestion(){
        int aux = result;
        if(r.nextInt(2) == 0){
            aux =  r.nextInt(result-3)+1;
            question = Integer.toString(aux) + "+" +Integer.toString(result-aux);
        }else{
            aux =  r.nextInt(aux*2)+aux+1;
            question = Integer.toString(aux) + "-" +Integer.toString(aux-result);
        }
        questionTextView .setText(question);
    }

    public void generateOptions(){
        result = r.nextInt(181)+20;
        options.clear();
        options.add(result +10);
        options.add(result -10);
        options.add(result);
        options.add(result +1);
        System.out.println(options);
        Collections.shuffle(options);
        for(int i=0; i<gridLayout.getChildCount();i++){
            TextView child = (TextView) gridLayout.getChildAt(i);
            child.setText(Integer.toString(options.get(i)));
        }

    }

    public void optionSelected(View view){
        if(isRunning) {
            TextView answer = (TextView) view;
            Log.i("answered", answer.getText().toString());
            Log.i("right answer ", Integer.toString(result));
            if (Integer.parseInt(answer.getText().toString()) == result) {
                score = score + 1;
            }
            rounds = rounds + 1;
            updateScore();
            generateOptions();
            generateQuestion();
        }
    }

    public void startGame(View view){
        gridLayout.setVisibility(View.VISIBLE);
        button = (Button) view;
        button.setVisibility(View.INVISIBLE);
        generateOptions();
        generateQuestion();
        isRunning = true;
        finalScoreTextView.setText("");
        CountDownTimer countDownTimer = new CountDownTimer(30000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timerTextView.setText(Integer.toString((int) (millisUntilFinished/1000))+"s");
            }

            @Override
            public void onFinish() {
                button.setVisibility(View.VISIBLE);
                finalScoreTextView.setText("You got "+Integer.toString(score)+" out of "+rounds+" right!");
                score=0;
                rounds=0;
                updateScore();
            }
        }.start();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        gridLayout = findViewById(R.id.gridLayout);
        timerTextView = (TextView) findViewById(R.id.timerTextView);
        questionTextView = (TextView) findViewById(R.id.questionTextView);
        scoreTextView = (TextView) findViewById(R.id.scoreTextView);
        finalScoreTextView = (TextView) findViewById(R.id.finalScoreTextView);
        gridLayout.setVisibility(View.INVISIBLE);
    }
}
