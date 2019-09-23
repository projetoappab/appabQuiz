package br.com.appabquiz;


import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import static br.com.appabquiz.R.*;

public class RespostaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layout.activity_resposta);
        getSupportActionBar().hide();

        ImageView imgResposta = findViewById(id.imgResposta);
        TextView resposta = findViewById(id.resposta);
        Button btnJogarNovamente = findViewById(id.btnJogarNovamente);

        Intent intent = getIntent();
        int pontos = intent.getIntExtra("pontos", 0);

        if(intent.hasExtra("acertou")) {
            btnJogarNovamente.setVisibility(View.INVISIBLE);
            boolean acertou = intent.getBooleanExtra("acertou", false);
            if (acertou) {
                imgResposta.setImageResource(drawable.acertou);
                resposta.setText("Acertou! Pontos: " + pontos);
            } else {
                imgResposta.setImageResource(drawable.errou);
                resposta.setText("Errou! Pontos: " + pontos);
            }

            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    finish();
                }
            });
            thread.start();
        }
        else{
            btnJogarNovamente.setVisibility(View.VISIBLE);
            resposta.setText("Fez " + pontos + " pontos!");

            if(pontos >= 3)
                imgResposta.setImageResource(drawable.happy);
            else
                imgResposta.setImageResource(drawable.sad);
        }
    }

    public void btnJogarNovamenteOnClick(View v){
        Intent intent = new Intent(this, QuizActivity.class);
        startActivity(intent);
        finish();
    }
}
