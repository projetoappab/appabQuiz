package br.com.appabquiz;

import android.content.Intent;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class QuizActivity extends AppCompatActivity {

    TextView pergunta;
    RadioButton rbResposta1, rbResposta2, rbResposta3, rbResposta4;
    int respostaCerta = R.id.rbResposta1;
    RadioGroup rgRespostas;
    int acertos;

    List<Questao> questoes = new ArrayList<Questao>(){
        {
            add(new Questao("Quem descobriu o Brasilzão?", R.id.rbResposta1, "Pedro Álvares Cabral", "Cristóvão Colombo", "Donald Trump", "Pabllo Vittar"));
            add(new Questao("Qual a cidade mais quente desse Brazilzão?", R.id.rbResposta2, "Palmas", "Goianinha", "São luiz", "Inferno"));
            add(new Questao("Quem vai pagar o café depois da Reunião?", R.id.rbResposta3, "Wallas", "Wenderson", "Pabllão sensação", "Sla"));
            add(new Questao("Qual a melhor plataforma mobile?", R.id.rbResposta4, "Symbian", "BlackBerry", "iOS", "Android <<<"));
        }
    };

    public QuizActivity() {
        acertos = 0;
    }

    private void carregarQuestao(){
        if(questoes.size() > 0) {
            Questao q = questoes.remove(0);
            pergunta.setText(q.getPergunta());
            List<String> resposta = q.getRespostas();
            rbResposta1.setText(resposta.get(0));
            rbResposta2.setText(resposta.get(1));
            rbResposta3.setText(resposta.get(2));
            rbResposta4.setText(resposta.get(3));
            respostaCerta = q.getRespostaCerta();
            rgRespostas.setSelected(false);
        }
        else{ //acabaram as questões
            Intent intent = new Intent(this, RespostaActivity.class);
            intent.putExtra("pontos", acertos);
            startActivity(intent);
            finish();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        Objects.requireNonNull(getSupportActionBar()).hide();

        pergunta = findViewById(R.id.pergunta);
        rbResposta1 = findViewById(R.id.rbResposta1);
        rbResposta2 = findViewById(R.id.rbResposta2);
        rbResposta3 = findViewById(R.id.rbResposta3);
        rbResposta4 = findViewById(R.id.rbResposta4);
        rgRespostas = findViewById(R.id.rgRespostas);
        carregarQuestao();
    }

    @Override
    protected void onRestart(){
        super.onRestart();
        carregarQuestao();
    }

    public void btnResponderOnClick(View v){
        RadioButton rb = (RadioButton)findViewById(rgRespostas.getCheckedRadioButtonId());
        Intent intent = new Intent(this, RespostaActivity.class);
        if(rgRespostas.getCheckedRadioButtonId() == respostaCerta) {
            intent.putExtra("acertou", true);
            acertos++;
        }
        else intent.putExtra("acertou", false);
        intent.putExtra("pontos", this.acertos);
        startActivity(intent);
        rb.setChecked(false);
    }
}

