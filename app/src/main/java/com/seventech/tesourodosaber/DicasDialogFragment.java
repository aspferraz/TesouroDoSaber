package com.seventech.tesourodosaber;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Parcelable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.DialogFragment;

import com.seventech.tesourodosaber.model.Dica;

import java.util.ArrayList;
import java.util.List;

public class DicasDialogFragment extends DialogFragment {

    private static final String ARG_DICAS_LIST = "dicas_list";
    public static final String EXTRA_ULTIMA_DICA_BEAN = "com.seventech.tesourodosaber.ultimaDicaBean";

    private CardView mDicaCardView;

    private Button mTentarResponderButton;
    private Button mVerOutraDicaButton;
    private Button mCancelarButton;

    private TextView mDicaTextView;
    private TextView mContadorTextView;

    private List<Dica> mDicas = new ArrayList<>();
    private Dica mDica;

    CountDownTimer mDownTimer;

    public static DicasDialogFragment newInstance(ArrayList<? extends Dica> dicas) {
        Bundle args = new Bundle();
        args.putParcelableArrayList(ARG_DICAS_LIST, dicas);
        DicasDialogFragment fragment = new DicasDialogFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mDicas = getArguments().getParcelableArrayList(ARG_DICAS_LIST);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_mostrar_dicas_dialog, container, false);

        rootView.getContext().getTheme().applyStyle(R.style.MyAlertDialog, true);

        getDialog().setTitle(R.string.dica_dialog_title);


        mDicaCardView = rootView.findViewById(R.id.mostrar_dicas_dlg_cardview);

        mContadorTextView = rootView.findViewById(R.id.mostrar_dicas_dlg_contador_textview);

        mDicaTextView = rootView.findViewById(R.id.mostrar_dicas_dlg_dica_textview);

        mCancelarButton = rootView.findViewById(R.id.mostrar_dicas_dlg_cancelar_button);
        mCancelarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancelar();
            }
        });

        mTentarResponderButton = rootView.findViewById(R.id.mostrar_dicas_dlg_tentar_responder_button);
        mTentarResponderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tentarResponder();
            }
        });


        mVerOutraDicaButton = rootView.findViewById(R.id.mostrar_dicas_dlg_ver_outra_dica_button);
        mVerOutraDicaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mostrarProximaDica();
            }
        });

//        mDownTimer = new CountDownTimer(30000, 1000) {
//
//            public void onTick(long millisUntilFinished) {
//                mContadorTextView.setText(new Long(millisUntilFinished / 1000).toString());
//            }
//
//            public void onFinish() {
//                mContadorTextView.setText("");
//                mContadorTextView.setVisibility(View.INVISIBLE);
//                exibirDica();
//            }
//
//        }.start();
        mostrarProximaDica();

        return rootView;
    }

    @Override
    public void onDestroy() {
        if (mDownTimer != null)
            mDownTimer.cancel();
        super.onDestroy();
    }

    private Dica obterProximaDica() {
        if (!mDicas.isEmpty()) {
            if (mDica == null) {
                return mDicas.get(0);
            } else {
                for (int i = 0; i < mDicas.size(); i++) {
                    if (mDicas.get(i).equals(mDica)) {
                        return mDicas.get((i + 1) < mDicas.size() ? i + 1 : mDicas.size() - 1);
                    }
                }
            }
        }
        return null;
    }

    private void exibirDica() {
        Dica proximaDica = obterProximaDica();
        if (proximaDica != null && !proximaDica.equals(mDica)){
            mDica = proximaDica;
            mDicaTextView.setText(mDica.getDescricao());
            mDicaTextView.setVisibility(View.VISIBLE);

            mCancelarButton.setVisibility(View.INVISIBLE);
            mTentarResponderButton.setVisibility(View.VISIBLE);
            mVerOutraDicaButton.setVisibility(View.VISIBLE);
        }
        else {
            mostrarAusenciaDeDicas();
        }
    }

    private void mostrarAusenciaDeDicas() {
        mDicaCardView.setCardBackgroundColor(getResources().getColor(android.R.color.holo_red_dark));
        mDicaTextView.setText("Sinto muito, todas as dicas já foram reveladas.");
        mDicaTextView.setVisibility(View.VISIBLE);

        mCancelarButton.setVisibility(View.INVISIBLE);
        mTentarResponderButton.setVisibility(View.VISIBLE);
        mVerOutraDicaButton.setVisibility(View.INVISIBLE);
    }

    private void mostrarProximaDica() {

        Dica proximaDica = obterProximaDica();
        if (proximaDica == null || proximaDica.equals(mDica)){
            mostrarAusenciaDeDicas();
            return;
        }

        mDicaTextView.setText("");
        mDicaTextView.setVisibility(View.INVISIBLE);
        mContadorTextView.setVisibility(View.VISIBLE);

        mCancelarButton.setVisibility(View.VISIBLE);
        mTentarResponderButton.setVisibility(View.INVISIBLE);
        mVerOutraDicaButton.setVisibility(View.INVISIBLE);


        mDownTimer = new CountDownTimer(30000, 1000) {

            public void onTick(long millisUntilFinished) {
                mContadorTextView.setText(new Long(millisUntilFinished / 1000).toString());
            }

            public void onFinish() {
                mContadorTextView.setText("");
                mContadorTextView.setVisibility(View.INVISIBLE);
                exibirDica();
            }

        }.start();
    }

    private void tentarResponder() {
        sendResult(Activity.RESULT_OK, mDica);
        dismiss();
    }

    private void cancelar() {

        if (mDownTimer != null) {
            Toast toast = Toast.makeText(getActivity().getApplicationContext(), "Não é possível cancelar enquanto o contador estiver ativo. Por favor, aguarde.", Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
            toast.show();
            return;

//            mDownTimer.cancel();
        }


        if (mDica != null)
            sendResult(Activity.RESULT_OK, mDica);
        dismiss();
    }

    private void sendResult(int resultCode, Dica dica) {
        if (getTargetFragment() == null) {
            return;
        }

        Intent intent = new Intent();
        intent.putExtra(EXTRA_ULTIMA_DICA_BEAN, (Parcelable) dica);

        getTargetFragment().onActivityResult(getTargetRequestCode(), resultCode, intent);
    }
}
