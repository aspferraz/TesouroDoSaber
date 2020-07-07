package com.seventech.tesourodosaber.database.repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.annotation.NonNull;

import com.seventech.tesourodosaber.database.AppDatabase;
import com.seventech.tesourodosaber.database.dao.LocalizacaoDao;
import com.seventech.tesourodosaber.model.Jornada;
import com.seventech.tesourodosaber.model.Localizacao;

import java.util.List;

public class LocalizacoesRepository {

    private List<Localizacao> mAllLocalizacoes;
    LocalizacaoDao mLocalizacaoDao;

    public LocalizacoesRepository(@NonNull Application application) {
        AppDatabase database = AppDatabase.getDatabase(application);

        mLocalizacaoDao = database.localizacaoDao();

        mAllLocalizacoes = mLocalizacaoDao.getAllLocalizacoes();
    }


    public List<Localizacao> getAllLocalizacoes() {
        return mAllLocalizacoes;
    }

    public List<Localizacao> getLocalizacoesPorJornada (Jornada jornada) {
        return mLocalizacaoDao.getLocalizacoesPorIdJornada(jornada.getId());
    }

    public Localizacao getLocalizacaoPorIdEnigma(Long idEnigma) {
        List<Localizacao> l = mLocalizacaoDao.getLocalizacaoPorIdEnigma(idEnigma);
        if (l!= null && !l.isEmpty())
            return l.iterator().next();
        return null;
    }

    public Localizacao getLocalizacaoPorQrCode(String qrCode) {
        List<Localizacao> l = mLocalizacaoDao.getLocalizacaoPorQrCode(qrCode);
        if (l!= null && !l.isEmpty())
            return l.iterator().next();
        return null;
    }

    public void addLocalizacao(Localizacao localizacao) {
        new AddLocalizacaoTask().execute(localizacao);
    }

    public void updateLocalizacao(Localizacao localizacao) {
        new UpdateLocalizacaoTask().execute(localizacao);
    }

    public void removeLocalizacao(Localizacao localizacao) {
        new RemoveLocalizacaoTask().execute(localizacao);
    }

    //Async task to add localizacao
    public class AddLocalizacaoTask extends AsyncTask<Localizacao, Void, Void> {
        @Override
        protected Void doInBackground(Localizacao... localizacoes) {
            mLocalizacaoDao.addLocalizacao(localizacoes[0]);
            return null;
        }
    }

    //Async task to update localizacao
    public class UpdateLocalizacaoTask extends AsyncTask<Localizacao, Void, Void> {
        @Override
        protected Void doInBackground(Localizacao... localizacoes) {
            mLocalizacaoDao.updateLocalizacao(localizacoes[0]);
            return null;
        }
    }

    //Async task to update localizacao
    public class RemoveLocalizacaoTask extends AsyncTask<Localizacao, Void, Void> {
        @Override
        protected Void doInBackground(Localizacao... localizacoes) {
            mLocalizacaoDao.removeLocalizacao(localizacoes[0]);
            return null;
        }
    }

}
