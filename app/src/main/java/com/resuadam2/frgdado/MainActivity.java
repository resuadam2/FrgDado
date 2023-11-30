package com.resuadam2.frgdado;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements FrgDado.OnFragmentInteractionListener {

    private static final int NUM_DADOS = 2;

    EditText etNumDados, etNumCaras;
    Button btnFinPartida, btnStart;

    TextView tvTiradas;

    FrgDado frgDado1, frgDado2;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etNumDados = findViewById(R.id.etNumDados);
        etNumCaras = findViewById(R.id.etNumCaras);
        btnFinPartida = findViewById(R.id.btnFinPartida);
        btnStart = findViewById(R.id.btnStart);
        tvTiradas = findViewById(R.id.tvTiradas);
        frgDado1 = (FrgDado) getSupportFragmentManager().findFragmentById(R.id.frgDado1);
        frgDado2 = (FrgDado) getSupportFragmentManager().findFragmentById(R.id.frgDado2);

        btnStart.setOnClickListener(v -> {
            // int numDados = Integer.parseInt(etNumDados.getText().toString());
            int numCaras = Integer.parseInt(etNumCaras.getText().toString());
           frgDado1.setOnFragmentInteractionListener(this, numCaras, true);
           frgDado1.jugando(true);
           frgDado2.setOnFragmentInteractionListener(this, numCaras, true);
           frgDado2.jugando(true);
        });

    }

    /**
     * Método que genera un número aleatorio desde 1 a numCaras
     * @return int número aleatorio
     */
    @Override
    public int lanzarDesdeBoton() {
        return (int) (Math.random() * Integer.parseInt(etNumCaras.getText().toString())) + 1;
    }

    @Override
    public int lanzarDesdeSpinner(int resultado) {
        return resultado;
    }

    @Override
    public int lanzarExterno() {
        return lanzarDesdeBoton();
    }
}