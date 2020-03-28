package com.example.flashcard;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.TextView;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    FlashcardDatabase flashcardDatabase;
    List<Flashcard> allFlashcards;
    int currentCardDisplayedIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        flashcardDatabase = new FlashcardDatabase(this);
        allFlashcards = flashcardDatabase.getAllCards();

        findViewById(R.id.question).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                findViewById(R.id.question).setVisibility(View.INVISIBLE);
                findViewById(R.id.answer).setVisibility(View.VISIBLE);

                View answerSideView = findViewById(R.id.answer);

                // get the center for the clipping circle
                int cx = answerSideView.getWidth() / 2;
                int cy = answerSideView.getHeight() / 2;

                // get the final radius for the clipping circle
                float finalRadius = (float) Math.hypot(cx, cy);

                // create the animator for this view (the start radius is zero)
                Animator anim = ViewAnimationUtils.createCircularReveal(answerSideView, cx, cy, 0f, finalRadius);

                // hide the question and show the answer to prepare for playing the animation!
                findViewById(R.id.question).setVisibility(View.INVISIBLE);
                 findViewById(R.id.answer).setVisibility(View.VISIBLE);


                anim.setDuration(3000);
                anim.start();
            }
        });

        findViewById(R.id.answer).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                findViewById(R.id.question).setVisibility(View.VISIBLE);
                findViewById(R.id.answer).setVisibility(View.INVISIBLE);
            }
        });

        findViewById(R.id.myBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this, AddcardActivity.class);
                MainActivity.this.startActivityForResult(intent,100);
                overridePendingTransition(R.anim.right_in, R.anim.left_in);
            }
        });

        findViewById(R.id.next).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // advance our pointer index so we can show the next card
                currentCardDisplayedIndex++;

                // make sure we don't get an IndexOutOfBoundsError if we are viewing the last indexed card in our list
                if (currentCardDisplayedIndex > allFlashcards.size() - 1) {
                    currentCardDisplayedIndex = 0;
                }

                // set the question and answer TextViews with data from the database
                ((TextView) findViewById(R.id.question)).setText(allFlashcards.get(currentCardDisplayedIndex).getQuestion());
                ((TextView) findViewById(R.id.answer)).setText(allFlashcards.get(currentCardDisplayedIndex).getAnswer());
                final Animation leftOutAnim = AnimationUtils.loadAnimation(v.getContext(), R.anim.left_in);
                final Animation rightInAnim = AnimationUtils.loadAnimation(v.getContext(), R.anim.right_in);
                leftOutAnim.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {
                        // this method is called when the animation first starts
                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        // this method is called when the animation is finished playing
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {
                        // we don't need to worry about this method
                    }
                });
                findViewById(R.id.question).startAnimation(leftOutAnim);
                findViewById(R.id.question).startAnimation(rightInAnim);
            }
        });

        findViewById(R.id.trash).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flashcardDatabase.deleteCard(((TextView) findViewById(R.id.question)).getText().toString());
                allFlashcards = flashcardDatabase.getAllCards();

                if(currentCardDisplayedIndex > 0 ) {
                    currentCardDisplayedIndex--;
                }


                if(currentCardDisplayedIndex == 0 ) {
                    currentCardDisplayedIndex=0;
                }


                if (allFlashcards == null ) {
                    ((TextView) findViewById(R.id.question)).setText("Enter a Question");
                    ((TextView) findViewById(R.id.answer)).setText("Ener an answer");
                }
                // set the question and answer TextViews with data from the database
                ((TextView) findViewById(R.id.question)).setText(allFlashcards.get(currentCardDisplayedIndex).getQuestion());
                ((TextView) findViewById(R.id.answer)).setText(allFlashcards.get(currentCardDisplayedIndex).getAnswer());


            }
        });


        if (allFlashcards != null && allFlashcards.size() > 0) {
            ((TextView) findViewById(R.id.question)).setText(allFlashcards.get(0).getQuestion());
            ((TextView) findViewById(R.id.answer)).setText(allFlashcards.get(0).getAnswer());
        }

    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 100) {
            String string1 = data.getExtras().getString("string1");
            String string2 = data.getExtras().getString("string2");

            ((TextView) findViewById(R.id.question)).setText(string1);
            ((TextView) findViewById(R.id.answer)).setText(string2);

            flashcardDatabase.insertCard(new Flashcard(string1, string2));
            allFlashcards = flashcardDatabase.getAllCards();
        }
    }
}
