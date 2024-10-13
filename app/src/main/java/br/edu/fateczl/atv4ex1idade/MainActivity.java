/*
@author:<Matheus Augusto Marti>
 */

package br.edu.fateczl.atv4ex1idade;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    private EditText etDia;
    private EditText etMes;
    private EditText etAno;
    private TextView tvIdade;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        etDia = findViewById(R.id.etDia);
        etMes = findViewById(R.id.etMes);
        etAno = findViewById(R.id.etAno);
        tvIdade = findViewById(R.id.tvIdade);
        Button btnCalcular = findViewById(R.id.btnCalcular);
        btnCalcular.setOnClickListener(op -> calcIdade());
    }

    private void calcIdade() {
        tvIdade.setText("");
        int diaNascimento = Integer.parseInt(etDia.getText().toString());
        int mesNascimento = Integer.parseInt(etMes.getText().toString());
        int anoNascimento = Integer.parseInt(etAno.getText().toString());

        Calendar dataAtual = Calendar.getInstance();
        int diaAtual = dataAtual.get(Calendar.DAY_OF_MONTH);
        int mesAtual = dataAtual.get(Calendar.MONTH) + 1;
        int anoAtual = dataAtual.get(Calendar.YEAR);

        int anos = anoAtual - anoNascimento;
        int meses = mesAtual - mesNascimento;
        int dias = diaAtual - diaNascimento;

        if (meses < 0) {
            anos--;
            meses += 12;
        }

        if (dias < 0) {
            meses--;
            dias += diasNoMes(mesAtual == 1 ? 12 : mesAtual - 1, anoAtual);
        }

        int anosBissextos = contarAnosBissextos(anoNascimento, anoAtual);

        dias += anosBissextos;

        while (dias > diasNoMes(mesAtual, anoAtual)) {
            dias -= diasNoMes(mesAtual, anoAtual);
            meses++;
            if (meses > 12) {
                meses = 1;
                anos++;
            }
        }

        String idade = "Você tem " + anos + " anos, " + meses + " meses e " + dias + " dias.";
        tvIdade.setText(idade);
    }

    private int diasNoMes(int mes, int ano) {
        int[] diasPorMes = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};

        if (mes == 2 && isBissexto(ano)) {
            return 29;
        }

        return diasPorMes[mes - 1];
    }

    private boolean isBissexto(int ano) {
            return (ano % 400 == 0) || (ano % 4 == 0) && (ano % 100 != 0);
    }

    private int contarAnosBissextos(int anoInicio, int anoFim) {
        int count = 0;
        for (int i = anoInicio; i <= anoFim; i++) {
            if (isBissexto(i)) {
                count++;
            }
        }
        return count;
    }
}