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

/**
 * Clase FrgDado que extiende de Fragment y que representa un dado en la aplicación
 * El dado está implementado como un componente reutilizable que se puede añadir a la actividad
 * @version 1.0
 * @see Fragment
 */
public class FrgDado extends Fragment {

    private Button btnLanzar; // Botón de lanzar
    private Spinner spnLanzar; // Selector de lanzar

    private TextView tvResult; // Texto de resultado

    private int numCaras = 10; // Número de caras

    private boolean debug = true, lanzado = false; // Modo debug y si se ha lanzado

    private OnFragmentInteractionListener mListener; // Listener de interacción

    private int racha = 0; // Racha de tiradas

    private ArrayList<Integer> tiradas = new ArrayList<>(); // Lista de tiradas


    /**
     * Interfaz de interacción con la actividad
     */
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

    /**
     * Método que se ejecuta cuando se lanza el dado
     * @param result resultado del lanzamiento
     */
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
                tvResult.setText(getString(R.string.resultado) + String.format("\nDado: %d (%d)", result, tiradas.size()));
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

    /**
     * Método que se ejecuta cuando se crea el fragmento
     * @param mListener listener de interacción
     * @param numCaras número de caras
     * @param debug modo debug
     */
    public void setOnFragmentInteractionListener(OnFragmentInteractionListener mListener,
                                                 int numCaras, boolean debug) {
        this.mListener = mListener;
        this.numCaras = numCaras;
        this.tiradas.clear();
        setSpnLanzar(numCaras);
        tvResult.setText(R.string.resultado);
        this.debug = debug;
    }

    /**
     * Método que cambia el estado del dado para activarlo o desactivarlo según se esté jugando
     * @param jugando si se está jugando
     */
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

    /**
     * Setter del selector del spinner de lanzar, crea el spinner con tantas opciones como
     * caras haya, además de añadir la opción vacía a la que se autoajusta por defecto tras
     * lanzar el dado
     * @param numCaras número de caras
     */
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

    /**
     * Método que se ejecuta cuando se crea el fragmento
     * @param inflater The LayoutInflater object that can be used to inflate
     * any views in the fragment,
     * @param container If non-null, this is the parent view that the fragment's
     * UI should be attached to.  The fragment should not add the view itself,
     * but this can be used to generate the LayoutParams of the view.
     * @param savedInstanceState If non-null, this fragment is being re-constructed
     * from a previous saved state as given here.
     *
     * @return View vista del fragmento
     */
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

    /**
     * Método que devuelve el item seleccionado en el spinner
     * Nota*: Está de una implementación anterior, actualmente es innecesario, podría
     * sustituirse en dónde se usa por la simple devolución del item
     * @param item item seleccionado
     * @return int item seleccionado
     */
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
