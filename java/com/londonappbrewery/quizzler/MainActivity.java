package com.londonappbrewery.quizzler;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

    // TODO: Declare constants here


    // TODO: Declare member variables here:
     Button mtrueButton ;
     Button mfalsButton;
     TextView mQuestionTextView;
     ProgressBar mProgressBar;
     TextView mScoreText;
     int mIndex;
     int mScore;

    // TODO: Uncomment to create question bank
    private TrueFalse[] mQuestionBank = new TrueFalse[] {
            new TrueFalse(R.string.question_1, true),
            new TrueFalse(R.string.question_2, true),
            new TrueFalse(R.string.question_3, true),
            new TrueFalse(R.string.question_4, true),
            new TrueFalse(R.string.question_5, true),
            new TrueFalse(R.string.question_6, false),
            new TrueFalse(R.string.question_7, true),
            new TrueFalse(R.string.question_8, false),
            new TrueFalse(R.string.question_9, true),
            new TrueFalse(R.string.question_10, true),
            new TrueFalse(R.string.question_11, false),
            new TrueFalse(R.string.question_12, false),
            new TrueFalse(R.string.question_13,true)
    };

    final int PROGRESS_BAR_INCREMENT=(int) Math.ceil(100/(mQuestionBank.length-1));

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

       mtrueButton= (Button)findViewById(R.id.true_button);
       mfalsButton= (Button)findViewById(R.id.false_button);
       mQuestionTextView= (TextView)findViewById(R.id.question_text_view);
       mProgressBar=(ProgressBar)findViewById(R.id.progress_bar);
       mScoreText=(TextView)findViewById(R.id.score);

        if (savedInstanceState!=null){
            mScore=savedInstanceState.getInt("ScoreKey");
            mIndex=savedInstanceState.getInt("IndexKey");
            mScoreText.setText("Score " + mScore + "/" + mQuestionBank.length);
        }
        else {
            mScore=0;
            mIndex=0;
        }

        int mQuestion = mQuestionBank[mIndex].getQuestionID();
        mQuestionTextView.setText(mQuestion);


       mtrueButton.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               CheckAnswer(true);
               updateQuestion();
           }
       });

       mfalsButton.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               CheckAnswer(false);
               updateQuestion();
           }
       });





    }
    private void updateQuestion(){
        mIndex=(mIndex+1)%mQuestionBank.length;

        if (mIndex==0){
            AlertDialog.Builder alert =new AlertDialog.Builder(this);
            alert.setTitle("Game Over");
            alert.setCancelable(false);
            alert.setMessage("your Score is : "+mScore);
            alert.setPositiveButton("close Applaction", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    finish();
                }
            });
            alert.show();
        }

        int mQuestion = mQuestionBank[mIndex].getQuestionID();
        mQuestionTextView.setText(mQuestion);
        mProgressBar.incrementProgressBy(PROGRESS_BAR_INCREMENT);
        mScoreText.setText("Score "+mScore+"/"+mQuestionBank.length);
    }

    private void CheckAnswer(boolean SelectedAnswer){
        boolean correctAnswer=mQuestionBank[mIndex].isAnswer();
        if (SelectedAnswer==correctAnswer){
            Toast.makeText(this, R.string.correct_toast, Toast.LENGTH_SHORT).show();
            mScore++;
        }
        else {
            Toast.makeText(this,R.string.incorrect_toast, Toast.LENGTH_SHORT).show();
        }
    }

    //to store date before app crash
    @Override
    public void onSaveInstanceState(Bundle outState){
        super.onSaveInstanceState(outState);
        outState.putInt("ScoreKey",mScore);
        outState.putInt("IndexKey",mIndex);
    }
}
