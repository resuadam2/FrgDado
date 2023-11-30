package com.resuadam2.frgdado;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

public class FrgDado extends Fragment {

    private Button btnLanzar;
    private Spinner spnLanzar;

    private TextView tvResult;

    private int numCaras = 6;

    private boolean debug = true, lanzado = false;

    private OnFragmentInteractionListener mListener;

    private int racha = 0, anterior= 0;


    public interface OnFragmentInteractionListener {
        int lanzarDesdeBoton();

        int lanzarDesdeSpinner(int resultado);

        int lanzarExterno();

    }

    public void setDebug(boolean debug) {
        this.debug = debug;
    }

    public void setNumCaras(int numCaras) {
        this.numCaras = numCaras;
    }

    public void setTvResult(int result) {
        tvResult.setText(getString(R.string.resultado) + " " + String.valueOf(result));
        if (debug) {
            if (result == anterior) {
                racha++;
                tvResult.append("\n" + getString(R.string.racha) + " " + String.valueOf(racha));
            } else {
                racha = 0;
            }
            anterior = result;
        }
    }

    public int getRacha() {
        return racha;
    }

    public int getAnterior() {
        return anterior;
    }

    public void setOnFragmentInteractionListener(OnFragmentInteractionListener mListener, int numCaras, boolean debug) {
        jugando(false);
        this.mListener = mListener;
        this.numCaras = numCaras;
        this.debug = debug;
    }

    public void jugando(boolean jugando) {
        if (jugando) {
            btnLanzar.setEnabled(false);
            spnLanzar.setEnabled(false);
        } else {
            btnLanzar.setEnabled(true);
            spnLanzar.setEnabled(true);
        }
    }

    public void setLanzado(boolean lanzado) {
        this.lanzado = lanzado;
    }

    @Override
    public void onViewCreated(View v, Bundle savedInstanceState) {
        super.onViewCreated(v, savedInstanceState);

        btnLanzar = (Button) v.findViewById(R.id.btnLanzar);
        spnLanzar = (Spinner) v.findViewById(R.id.spnLanzar);

        ArrayAdapter<Integer> adaptador = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item);
        for (int i = 1; i <= numCaras; i++) {
            adaptador.add(i);
        }
        adaptador.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnLanzar.setAdapter(adaptador);

        btnLanzar.setOnClickListener(v1 -> {
            if (mListener != null) {
                setTvResult(mListener.lanzarDesdeBoton());
                setLanzado(true);
            }
        });

        spnLanzar.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(android.widget.AdapterView<?> parent, View view, int position, long id) {
                if (mListener != null) {
                    setTvResult(mListener.lanzarDesdeSpinner(adaptador.getItem(position)));
                    setLanzado(true);
                }
            }

            @Override
            public void onNothingSelected(android.widget.AdapterView<?> parent) {
                Toast.makeText(getActivity(), getString(R.string.nothingSelected), Toast.LENGTH_SHORT).show();
            }
        });

    }
}
