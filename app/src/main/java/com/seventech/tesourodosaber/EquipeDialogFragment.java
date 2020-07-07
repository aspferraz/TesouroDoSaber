package com.seventech.tesourodosaber;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.seventech.tesourodosaber.model.Equipe;

public class EquipeDialogFragment extends DialogFragment {

    public static final String EXTRA_EQUIPE_BEAN = "com.seventech.tesourodosaber.equipeBean";

    private EditText mNomeEquipeEditText;
    private Button okButton;
    private Button cancelButton;

    private Equipe mEquipe = new Equipe();

    public static EquipeDialogFragment newInstance() {
        return new EquipeDialogFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_equipe_dialog, container, false);
        rootView.getContext().getTheme().applyStyle(R.style.MyAlertDialog, true);

        getDialog().setTitle(R.string.equipe_dialog_title);

        mNomeEquipeEditText = (EditText) rootView.findViewById(R.id.nome_equipe_edit_text);

        mNomeEquipeEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mEquipe.setDescricao(s.toString());
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void afterTextChanged(Editable s) {
            }
        });


        okButton = (Button) rootView.findViewById(R.id.equipe_dialog_ok_button);
        okButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                confirm();
            }
        });

        cancelButton = (Button) rootView.findViewById(R.id.equipe_dialog_cancel_button);
        cancelButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                dismiss();
            }
        });


        return rootView;
    }

    private void confirm() {
        if (mEquipe.getDescricao() != null && !mEquipe.getDescricao().trim().isEmpty()) {
            sendResult(Activity.RESULT_OK, mEquipe);
            dismiss();
        } else {
            Toast toast = Toast.makeText(App.getContext(), "Atenção! Você deve informar um nome para a equipe antes de prosseguir.", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
            toast.show();
        }
    }

    private void sendResult(int resultCode, Equipe equipe) {
        if (getTargetFragment() == null) {
            return;
        }

        Intent intent = new Intent();
        intent.putExtra(EXTRA_EQUIPE_BEAN, (Parcelable) equipe);

        getTargetFragment().onActivityResult(getTargetRequestCode(), resultCode, intent);
    }
}
