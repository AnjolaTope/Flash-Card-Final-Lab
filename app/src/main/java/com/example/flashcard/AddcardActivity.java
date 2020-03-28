package com.example.flashcard;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class AddcardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addcard);


        findViewById(R.id.myBtn2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(AddcardActivity.this, MainActivity.class);
                AddcardActivity.this.startActivity(intent);
            }
        });

        findViewById(R.id.save).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent data = new Intent();
                data.putExtra("string1", ((EditText) findViewById(R.id.que)).getText().toString());
                data.putExtra("string2",  ((EditText) findViewById(R.id.ans)).getText().toString());
                setResult(RESULT_OK, data);
                finish();
            }
        });

    }


}
