package com.seventech.tesourodosaber;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.seventech.tesourodosaber.database.repository.AlternativasRepository;
import com.seventech.tesourodosaber.database.repository.DicasRepository;
import com.seventech.tesourodosaber.database.repository.EnigmasRepository;
import com.seventech.tesourodosaber.database.repository.LocalizacoesRepository;
import com.seventech.tesourodosaber.model.Alternativa;
import com.seventech.tesourodosaber.model.Dica;
import com.seventech.tesourodosaber.model.Enigma;
import com.seventech.tesourodosaber.model.Localizacao;
import com.seventech.tesourodosaber.navigation.BackNavigable;
import com.seventech.tesourodosaber.utils.Assert;
import com.seventech.tesourodosaber.utils.view.Messages;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class EnigmaFragment extends Fragment implements BackNavigable {

    private static final int REQUEST_DICA = 0;
    private static final String DIALOG_DICA = "dialog_dica";

    private RelativeLayout mContainerLayout;

    private ImageView mJornadaImageView;
    private TextView mTituloEnigmaTextView;
    ArrayList<RadioButton> mListOfRadioButtons = new ArrayList<RadioButton>();

    private ImageButton mResponderImageButton;

    private EnigmaActivity mEnigmaActivity;

    private List<Alternativa> mAlternativas;
    private Alternativa mAlternativa;
    AlternativasRepository mAlternativasRepository;

    private ArrayList<Dica> mDicas;
    private Dica mUltimaDica;
    DicasRepository mDicasRepository;

    LocalizacoesRepository mLocalizacoesRepository;
    EnigmasRepository mEnigmasRepository;

    private Enigma mEnigma;

    private static final String ARG_ENIGMA_BEAN = "enigma_bean";

    public static EnigmaFragment newInstance(Enigma enigma) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_ENIGMA_BEAN, enigma);
        EnigmaFragment fragment = new EnigmaFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);

        mEnigma = (Enigma) getArguments().getSerializable(ARG_ENIGMA_BEAN);

        mAlternativasRepository = new AlternativasRepository(mEnigmaActivity.getApplication());
        mAlternativas = mAlternativasRepository.getAlternativasByEnigma(mEnigma);

        mDicasRepository = new DicasRepository(mEnigmaActivity.getApplication());
        mDicas = new ArrayList<>(mDicasRepository.getDicasByEnigma(mEnigma));

        mLocalizacoesRepository = new LocalizacoesRepository(mEnigmaActivity.getApplication());
        mEnigmasRepository = new EnigmasRepository((mEnigmaActivity.getApplication()));
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View view = inflater.inflate(R.layout.fragment_enigma, container, false);

        mContainerLayout = view.findViewById(R.id.jornada_container_layout);

        mJornadaImageView = view.findViewById(R.id.jornada_imageview);

//        ViewGroup.MarginLayoutParams marginParams = (ViewGroup.MarginLayoutParams) mJornadaImageView.getLayoutParams();
//        marginParams.setMargins(0, 0, 0, getStatusBarHeight());
//        mJornadaImageView.setPadding(0,0,0, getStatusBarHeight());
        mJornadaImageView.setImageBitmap(obterImagemDeFundoPorIdJornada(mEnigma.getIdJornada()));

        mTituloEnigmaTextView = view.findViewById(R.id.titulo_enigma_textview);
        mTituloEnigmaTextView.setText(mEnigma.getDescricao());

        mResponderImageButton = view.findViewById(R.id.responder_button);
        mResponderImageButton.setOnClickListener(new ResponderImageButtonListener());

        RadioGroup alternativasRadioGroup = view .findViewById(R.id.alternativas_rgroup);

        alternativasRadioGroup.setOnCheckedChangeListener(new AlternativaRadioButtonListener());

        int count = alternativasRadioGroup.getChildCount();

        for (int i = 0; i < count; i++) {
            View o = alternativasRadioGroup.getChildAt(i);
            if (o instanceof RadioButton) {
                if (mAlternativas.size() == count)
                    ((RadioButton)o).setText(mAlternativas.get(i).getDescricao());
                else
                    Toast.makeText(getContext(), "Erro(3): Houve uma falha ao iniciar a view. Revise o banco de dados!", Toast.LENGTH_LONG).show();
                mListOfRadioButtons.add((RadioButton)o);
            }
        }

        decorarBotoes(mResponderImageButton);

        return view;
    }

    class ResponderImageButtonListener implements ImageButton.OnClickListener {

        @Override
        public void onClick(View v) {
            if (mAlternativa == null) {
                Toast.makeText(getContext(), "Você deve selecionar uma opção para prosseguir. ", Toast.LENGTH_LONG).show();
                return;
            }
            if (mAlternativa.isCorreta()) {

                Localizacao proximaLocalizacao = obterProximaLocalizacao();

                if (proximaLocalizacao != null) {

                    Messages.with(getContext()).showInfoMessage("Parabéns, ", "Você acertou! A seguir, localize no mapa o proximo desafio.", "OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {


                            startActivity(LocalizacaoActivity.newIntent(mEnigmaActivity, proximaLocalizacao));
                        }
                    });
                }
                else {

                    App.getSessionManager().finish();

                    startActivity(FimJogoActivity.newIntent(mEnigmaActivity));
                }
            }
            else {
                if (!mDicas.isEmpty()) {
                    DicasDialogFragment dialog = DicasDialogFragment.newInstance(mDicas);
                    dialog.setTargetFragment(EnigmaFragment.this, REQUEST_DICA);
                    dialog.show(getActivity().getSupportFragmentManager(), DIALOG_DICA);
                }
                else {
                    Messages.with(getContext()).showInfoMessage(":-(", "Infelizmente, você errou de novo. Não há como prosseguir até resolver o enigma.");
                }
            }
        }
    }

    private int getStatusBarHeight() {
        int height = 48;

        Resources resources = mEnigmaActivity.getResources();
        int resourceId = resources.getIdentifier("navigation_bar_height", "dimen", "android");
        if (resourceId > 0) {
            height =  resources.getDimensionPixelSize(resourceId);
        }

        height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                (int) (height * 0.75 + 0.5), getResources().getDisplayMetrics());

        return height;
    }

    private void decorarBotoes(ImageButton... botoes) {
        for (ImageButton botao : botoes) {
            if (botao.getId() == R.id.responder_button) {
                int idJornada = mEnigma.getIdJornada().intValue();
                botao.setBackground(ContextCompat.getDrawable(mEnigmaActivity, R.drawable.border_colored));
                switch (idJornada) {
                    case 1:
                        botao.setImageDrawable(ContextCompat.getDrawable(mEnigmaActivity, R.drawable.btn_responder_1));
                        break;
                    case 2:
                        botao.setImageDrawable(ContextCompat.getDrawable(mEnigmaActivity, R.drawable.btn_responder_2));
                        break;
                    case 3:
                        botao.setImageDrawable(ContextCompat.getDrawable(mEnigmaActivity, R.drawable.btn_responder_3));
                        break;
                }
            }
        }
    }

    private Localizacao obterProximaLocalizacao() {
        Enigma proximoEnigma = mEnigmasRepository.getProximoEnigmaDaJornada(mEnigma);
        if (proximoEnigma != null) {
            return mLocalizacoesRepository.getLocalizacaoPorIdEnigma(proximoEnigma.getId());
        }
        return  null;
    }

    class AlternativaRadioButtonListener implements RadioGroup.OnCheckedChangeListener {

        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {

            switch(checkedId) {
                case R.id.alternativa_1_rbutton:
                    mAlternativa = mAlternativas.get(0);
                    break;

                case R.id.alternativa_2_rbutton:
                    mAlternativa = mAlternativas.get(1);
                    break;

                case R.id.alternativa_3_rbutton:
                    mAlternativa = mAlternativas.get(2);
                    break;

                case R.id.alternativa_4_rbutton:
                    mAlternativa = mAlternativas.get(3);
                    break;

                case R.id.alternativa_5_rbutton:
                    mAlternativa = mAlternativas.get(4);
                    break;
            }
        }
    }

    private Bitmap obterImagemDeFundoPorIdJornada(Long idJornada) {

        Assert.that(idJornada > 0 && idJornada < 4, "Argumento 'idJornada' deve ser entre 1 e 3. ");

        Bitmap backgroud = null;

        switch (idJornada.intValue()) {
            case 1 :
                backgroud = BitmapFactory.decodeResource(this.getResources(), R.drawable.astronomia_background);
                break;

            case 2 :
                backgroud = BitmapFactory.decodeResource(this.getResources(), R.drawable.biodiversidade_background) ;
                break;

            case 3 :
                backgroud = BitmapFactory.decodeResource(this.getResources(), R.drawable.origem_evolucao_background) ;
                break;
        }

        return backgroud;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQUEST_DICA:

                if (resultCode == Activity.RESULT_OK) {

                    mUltimaDica = data.getParcelableExtra(DicasDialogFragment.EXTRA_ULTIMA_DICA_BEAN);

                    Iterator<Dica> itr = mDicas.iterator();

                    //Remove todas as dicas até chegar na última
                    while (itr.hasNext()) {
                        Dica dica = itr.next();
                        if (dica.equals(mUltimaDica)) {
                            itr.remove();
                            break;
                        }
                        itr.remove();
                    }
                }

                break;
        }
    }

    @Override
    public void onBackPressed() {

    }

    // gambiarra p/ contornar bug do android
    @Override
    public void onAttach(Activity activity) {
        mEnigmaActivity = (EnigmaActivity) activity;
        super.onAttach(activity);
    }
}
