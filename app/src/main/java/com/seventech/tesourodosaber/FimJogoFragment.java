package com.seventech.tesourodosaber;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.seventech.tesourodosaber.database.repository.SessoesRepository;
import com.seventech.tesourodosaber.model.Sessao;
import com.seventech.tesourodosaber.navigation.BackNavigable;
import com.seventech.tesourodosaber.utils.view.Messages;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class FimJogoFragment extends Fragment implements BackNavigable {


    private ImageButton mInicioButton;
    private ImageButton mRankingButton;

    private Activity mFimJogoActivity;

    private SessoesRepository mSessoesRepository;

    public static FimJogoFragment newInstance() {
        return new FimJogoFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);

        mSessoesRepository = new SessoesRepository(mFimJogoActivity.getApplication());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fim_jogo, container, false);

        mInicioButton = view.findViewById(R.id.inicio_button);
        mInicioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(MainActivity.newIntent(mFimJogoActivity));
            }
        });

        mRankingButton = view.findViewById(R.id.ver_ranking_button);
        mRankingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StringBuilder text = new StringBuilder();
                text.append("Você concluiu o jogo em '"+getDuracaoSessaoEmMinutos(App.getSessionManager().getCurrentSession())+"' minutos. Parabéns!");
                String[] topDez = getTopDez();
                if (topDez.length > 0) {
                    text.append("\n\tEsses são os melhores tempos:");
                    for (String t : topDez) {
                        text.append("\n\t").append(t);
                    }
                }
                Messages.with(mFimJogoActivity).showInfoMessage("Ranking", text.toString());
            }
        });
        return view;
    }

    private int getDuracaoSessaoEmMinutos(Sessao sessao) {
        Date earlierDate = sessao.getInicioJogo();
        Date laterDate = sessao.getFimJogo();

        return (int)((laterDate.getTime()/60000) - (earlierDate.getTime()/60000));
    }

    private String[] getTopDez() {
        List<String> result = new ArrayList<>();
        List<Sessao> l =  mSessoesRepository.getMelhoresSessoes(10);
        if (l != null && !l.isEmpty()) {
            for (int i = 0; i < l.size(); i++) {
                Sessao sessao = l.get(i);
                result.add(i+": "+ getDuracaoSessaoEmMinutos(sessao) +"minuitos. ");
            }
        }
        return result.toArray(new String[]{});
    }

    @Override
    public void onBackPressed() {

    }

    // gambiarra p/ contornar bug do android
    @Override
    public void onAttach(Activity activity) {
        mFimJogoActivity = (FimJogoActivity) activity;
        super.onAttach(activity);
    }
}
