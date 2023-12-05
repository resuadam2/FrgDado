package com.resuadam2.frgdado;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;


public class StatsActivity extends AppCompatActivity {
    DBManager dbManager;

    ListView lvPartidas;

    TextView tvDificultad, tvDatosUltimaPartida;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);

        dbManager = new DBManager(this);

        Intent intent = getIntent();
        int  tiradas = intent.getIntExtra("tiradas", 0);
        int dificultad = Integer.parseInt(intent.getStringExtra("numCaras"));
        int rachaMax = intent.getIntExtra("rachaMax", 0);

        dbManager.insertPartida(tiradas, rachaMax, dificultad);

        tvDificultad = findViewById(R.id.tvDificultad);
        tvDificultad.setText(tvDificultad.getText().toString() + " " + dificultad);

        tvDatosUltimaPartida = findViewById(R.id.tvDatosUltimaPartida);
        tvDatosUltimaPartida.setText(tvDatosUltimaPartida.getText().toString() + "\n " + tiradas + " tiradas, " + rachaMax + " racha máxima");

        lvPartidas = findViewById(R.id.lvPartidas); // Lista de partidas
        ArrayList<String> partidas = new ArrayList<>();
        Cursor cursor = dbManager.getPartidasPorDificultad(dificultad);
        while (cursor.moveToNext()) {
            partidas.add(cursor.getString(0) + " tiradas: " + cursor.getString(1) + " racha máxima: " + cursor.getString(2));
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, partidas);
        lvPartidas.setAdapter(adapter);

        Button btnBorrar = findViewById(R.id.btnBorrarHistDiff); // Botón de borrar historial
        btnBorrar.setOnClickListener(v -> {
            dbManager.deletePartidasPorDificultad(dificultad);
            Toast.makeText(this, "Partidas borradas", Toast.LENGTH_SHORT).show();
            adapter.clear();
            adapter.notifyDataSetChanged();
        });
    }


}
