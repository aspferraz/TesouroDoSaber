package com.seventech.tesourodosaber;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.seventech.tesourodosaber.database.repository.EquipesRepository;
import com.seventech.tesourodosaber.database.repository.JornadasRepository;
import com.seventech.tesourodosaber.database.repository.LocalizacoesRepository;
import com.seventech.tesourodosaber.database.repository.SessoesRepository;
import com.seventech.tesourodosaber.model.Equipe;
import com.seventech.tesourodosaber.model.Jornada;
import com.seventech.tesourodosaber.model.Localizacao;
import com.seventech.tesourodosaber.navigation.BackNavigable;

import java.util.List;

public class MainFragment extends Fragment implements BackNavigable {

    private static final int REQUEST_CADASTRO_EQUIPE = 0;
    private static final String DIALOG_EQUIPE = "dialog_equipe";

    private Activity mMainActivity;
    private ImageButton mAstronomiaButton;
    private ImageButton mBiodiversidadeButton;
    private ImageButton mOrigemEvolucaoButton;
    private ImageButton mFecharButton;

    private List<Jornada> mAllJornadas;
    private List<Localizacao> mLocalizacoes;

    JornadasRepository mJornadasRepository;
    LocalizacoesRepository mLocalizacoesRepository;
    EquipesRepository mEquipesRepository;
    SessoesRepository mSessoesRepository;

    private Equipe mEquipe;

    private Jornada mJornada;

    public static MainFragment newInstance() {
        return new MainFragment();
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);

        mJornadasRepository = new JornadasRepository(mMainActivity.getApplication());
        mLocalizacoesRepository = new LocalizacoesRepository(mMainActivity.getApplication());
        mEquipesRepository = new EquipesRepository(mMainActivity.getApplication());
        mSessoesRepository = new SessoesRepository(mMainActivity.getApplication());

        mAllJornadas = mJornadasRepository.getAllJornadas();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View view = inflater.inflate(R.layout.fragment_main, container, false);

        mAstronomiaButton = view.findViewById(R.id.astronomia_button);
        mAstronomiaButton.setOnClickListener(new BotaoJornadaOnClickListener(1L));

        mBiodiversidadeButton = view.findViewById(R.id.biodiversidade_button);
        mBiodiversidadeButton.setOnClickListener(new BotaoJornadaOnClickListener(2L));


        mOrigemEvolucaoButton = view.findViewById(R.id.origemevolucao_button);
        mOrigemEvolucaoButton.setOnClickListener(new BotaoJornadaOnClickListener(3L));

        mFecharButton = view.findViewById(R.id.fechar_button);
        mFecharButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMainActivity.moveTaskToBack(true);
                mMainActivity.finish();
            }
        });

        return view;
    }

    class BotaoJornadaOnClickListener implements View.OnClickListener {

        Jornada _jornada;

        public BotaoJornadaOnClickListener(Long idJornada) {
            for (Jornada jornada : mAllJornadas) {
                if (jornada.getId().equals(idJornada)) {
                    _jornada = jornada;
                    break;
                }
            }
        }

        @Override
        public void onClick(View v) {
            if (_jornada != null) {
                mJornada = _jornada;
                mLocalizacoes = mLocalizacoesRepository.getLocalizacoesPorJornada(_jornada);
                if (mLocalizacoes != null) {

                    EquipeDialogFragment dialog = EquipeDialogFragment.newInstance();
                    dialog.setTargetFragment(MainFragment.this, REQUEST_CADASTRO_EQUIPE);
                    dialog.show(getActivity().getSupportFragmentManager(), DIALOG_EQUIPE);

//                    startActivity(LocalizacaoActivity.newIntent(mMainActivity, mLocalizacoes.get(0)));
                }
                else
                    Toast.makeText(getContext(), "Erro(1): Houve uma falha ao iniciar a view. Revise o banco de dados!", Toast.LENGTH_LONG).show();
            }
            else {
                Toast.makeText(getContext(), "Erro(2): Houve uma falha ao iniciar a view. Revise o banco de dados!", Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case REQUEST_CADASTRO_EQUIPE:

                if (resultCode == Activity.RESULT_OK) {

                    mEquipe = data.getParcelableExtra(EquipeDialogFragment.EXTRA_EQUIPE_BEAN);
                    if (mEquipe != null && mEquipe.getDescricao() != null && !mEquipe.getDescricao().trim().isEmpty()) {

                        mEquipesRepository.addEquipe(mEquipe);

                        App.getSessionManager().init(mJornada);

                        startActivity(LocalizacaoActivity.newIntent(mMainActivity, mLocalizacoes.get(0)));
                    }
                }

                break;
        }
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void onBackPressed() {
        mMainActivity.finish();

    }

    // gambiarra p/ contornar bug do android
    @Override
    public void onAttach(Activity activity) {
        mMainActivity = (MainActivity) activity;
        super.onAttach(activity);
    }
}
