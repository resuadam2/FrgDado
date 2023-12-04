package com.resuadam2.frgdado;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import java.util.ArrayList;

public class FrgDado extends Fragment {

    private Button btnLanzar;
    private Spinner spnLanzar;

    private TextView tvResult;

    private int numCaras = 10;

    private boolean debug = true, lanzado = false;

    private OnFragmentInteractionListener mListener;

    private int racha = 0;

    private ArrayList<Integer> tiradas = new ArrayList<>();


    public interface OnFragmentInteractionListener {
        boolean onDadoLanzado(FrgDado frgDado, int numero, int racha, int totalLanzamiento);

    }

    public void setDebug(boolean debug) {
        this.debug = debug;
    }

    public void setNumCaras(int numCaras) {
        this.numCaras = numCaras;
    }

    public boolean isLanzado() {
        return lanzado;
    }

    public void lanzamiento(Integer result) {
        if (mListener != null) {
            if (tiradas.size() > 0) {
                if (result == tiradas.get(tiradas.size() - 1)) {
                    racha++;
                    tiradas.add(result);
                } else {
                    racha = 0;
                    tiradas.add(result);
                }
            } else {
                tiradas.add(result);
            }
            if (debug) {
                tvResult.setText(R.string.resultado + String.format("\nDado: %d (%d)", result, tiradas.size())); //TODO: recurso está devolviendo el ID
                if (racha > 0) {
                    tvResult.append(String.format("\n Racha: %d", racha));
                }
            }
            mListener.onDadoLanzado(this, result, racha, tiradas.size());
        }
    }

    public int getRacha() {
        return racha;
    }

    public ArrayList<Integer> getTiradas() {
        return tiradas;
    }

    public void setOnFragmentInteractionListener(OnFragmentInteractionListener mListener,
                                                 int numCaras, boolean debug) {
        this.mListener = mListener;
        this.numCaras = numCaras;
        this.tiradas.clear();
        setSpnLanzar(numCaras);
        tvResult.setText(R.string.resultado);
        this.debug = debug;
    }

    public void jugando(boolean jugando) {
        if (!jugando) {
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

    private void setSpnLanzar(int numCaras) {
        ArrayAdapter<String> adaptador = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item);
        adaptador.add("");
        for (int i = 1; i <= numCaras; i++) {
            adaptador.add(i + "");
        }
        adaptador.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnLanzar.setAdapter(adaptador);


        spnLanzar.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(android.widget.AdapterView<?> parent, View view, int position, long id) {
                if (mListener != null) {
                    if(!adaptador.getItem(position).equals("")){
                        lanzamiento(lanzarDesdeSpinner(Integer.parseInt(adaptador.getItem(position))));
                        setLanzado(true);
                        spnLanzar.setSelection(0);
                    }
                }
            }

            @Override
            public void onNothingSelected(android.widget.AdapterView<?> parent) {
                Toast.makeText(getActivity(), getString(R.string.nothingSelected), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        View v = inflater.inflate(R.layout.frg_dado, container, false);

        btnLanzar = (Button) v.findViewById(R.id.btnLanzar);
        spnLanzar = (Spinner) v.findViewById(R.id.spnLanzar);
        if (debug) {
            tvResult = (TextView) v.findViewById(R.id.tvResult);
        }

        btnLanzar.setOnClickListener(v1 -> {
            if (mListener != null) {
                lanzamiento(this.lanzarDesdeBoton());
                setLanzado(true);
            }
        });

        setSpnLanzar(numCaras);

        return v;

    }

    private Integer lanzarDesdeSpinner(Integer item) {
        return item;
    }

    /**
     * Método que devuelve un número aleatorio entre 1 y el número de caras
     *
     * @return int número aleatorio
     */
    private Integer lanzarDesdeBoton() {
        return (int) (Math.random() * numCaras) + 1;
    }
}
