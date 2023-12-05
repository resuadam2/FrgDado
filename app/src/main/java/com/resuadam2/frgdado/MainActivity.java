package com.resuadam2.frgdado;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements FrgDado.OnFragmentInteractionListener {

    private static final int NUM_DADOS = 2; // Número de dados

    EditText etNumDados, etNumCaras; // Campos de texto para el número de dados y caras
    Button btnFinPartida, btnStart; // Botones de fin de partida y empezar partida

    TextView tvTiradas; // Texto de tiradas

    FrgDado frgDado1, frgDado2; // Fragmentos de los dados


    /**
     * Método que se ejecuta cuando se crea la actividad
     * @param savedInstanceState If the activity is being re-initialized after
     *     previously being shut down then this Bundle contains the data it most
     *     recently supplied in {@link #onSaveInstanceState}.  <b><i>Note: Otherwise it is null.</i></b>
     *
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etNumDados = findViewById(R.id.etNumDados);
        etNumDados.setText(String.valueOf(NUM_DADOS));
        etNumDados.setEnabled(false);
        etNumCaras = findViewById(R.id.etNumCaras);
        btnFinPartida = findViewById(R.id.btnFinPartida);
        btnFinPartida.setEnabled(false);
        btnStart = findViewById(R.id.btnStart);
        tvTiradas = findViewById(R.id.tvTiradas);
        frgDado1 = (FrgDado) getSupportFragmentManager().findFragmentById(R.id.frgDado1);
        frgDado2 = (FrgDado) getSupportFragmentManager().findFragmentById(R.id.frgDado2);

        btnStart.setOnClickListener(v -> {
            // int numDados = Integer.parseInt(etNumDados.getText().toString());

            if (etNumDados.getText().toString().equals("") || etNumDados.getText().toString().equals("0")) {
                Toast.makeText(this, "Debes introducir un número de dados válido", Toast.LENGTH_SHORT).show();
                return;
            }

            if (Integer.parseInt(etNumDados.getText().toString()) < 2) {
                Toast.makeText(this, "El número de dados debe ser mayor que 1", Toast.LENGTH_SHORT).show();
                return;
            }

            if (etNumCaras.getText().toString().equals("") || etNumCaras.getText().toString().equals("0")) {
                Toast.makeText(this, "Debes introducir un número de caras válido", Toast.LENGTH_SHORT).show();
                return;
            }

            if (Integer.parseInt(etNumCaras.getText().toString()) < 2) {
                Toast.makeText(this, "El número de caras debe ser mayor que 1", Toast.LENGTH_SHORT).show();
                return;
            }
            int numCaras = Integer.parseInt(etNumCaras.getText().toString());
            frgDado1.setOnFragmentInteractionListener(this, numCaras, true);
            frgDado1.jugando(true);
            frgDado2.setOnFragmentInteractionListener(this, numCaras, true);
            frgDado2.jugando(true);
            btnFinPartida.setEnabled(false);
            partidaFinalizada = false;
        });

        btnFinPartida.setOnClickListener(v -> {
            partidaFinalizada = false; // Para que no se active el botón al volver a la actividad
            Intent intent = new Intent(this, StatsActivity.class);
            intent.putExtra("tiradas", tiradasTotales);
            intent.putExtra("numCaras", etNumCaras.getText().toString());
            if (frgDado1.getRacha() > frgDado2.getRacha()) {
                intent.putExtra("rachaMax", frgDado1.getRacha());
            } else {
                intent.putExtra("rachaMax", frgDado2.getRacha());
            }
            startActivity(intent);
        });
    }

    /**
     * Método que se ejecuta cuando se vuelve a la actividad
     */
    @Override
    public void onStart() {
        super.onStart();
        if (partidaFinalizada) {
            btnFinPartida.setEnabled(true);
        } else {
            btnFinPartida.setEnabled(false);
        }
    }

    int tiradasTotales = 0;
    int resultado;

    boolean partidaFinalizada = false;

    /**
     * Método que se ejecuta cuando se lanza un dado
     * @param frgDado fragmento del dado
     * @param numero número que ha salido
     * @param racha racha actual
     * @param totalLanzamiento total de lanzamientos
     * @return true
     */
    @Override
    public boolean onDadoLanzado(FrgDado frgDado, int numero, int racha, int totalLanzamiento) {
        if (frgDado == frgDado1) {
            frgDado2.setLanzado(false);
            resultado = numero;
        } else if (frgDado1.isLanzado()) {
            tiradasTotales++;
            if (numero == resultado) {
                Toast.makeText(this, "¡Has ganado!", Toast.LENGTH_SHORT).show();
                frgDado1.jugando(false);
                frgDado2.jugando(false);
                btnFinPartida.setEnabled(true);
                partidaFinalizada = true;
            }
            frgDado1.setLanzado(false);
        } else {
            Toast.makeText(this, "¡Debes lanzar primero el Dado 1!\n Esta tirada no cuenta", Toast.LENGTH_SHORT).show();
        }
        tvTiradas.setText(String.format("Tiradas: %d", tiradasTotales));
        return true;
    }
}