package com.seventech.tesourodosaber.database.repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.annotation.NonNull;

import com.seventech.tesourodosaber.database.AppDatabase;
import com.seventech.tesourodosaber.database.dao.SessaoDao;
import com.seventech.tesourodosaber.model.Sessao;

import java.util.List;

public class SessoesRepository {

    private List<Sessao> mAllSessoes;
    SessaoDao mSessaoDao;

    public SessoesRepository(@NonNull Application application) {
        AppDatabase database = AppDatabase.getDatabase(application);

        mSessaoDao = database.sessaoDao();

        mAllSessoes = mSessaoDao.getAllSessoes();
    }

    public List<Sessao> getAllSessoes() {
        return mAllSessoes;
    }

    public Sessao getUltimaSessao() {
        Sessao ultimaSessao = null;
        List<Sessao> result = mSessaoDao.getUltimaSessao();
        if (result != null && !result.isEmpty())
            ultimaSessao = result.iterator().next();

        return ultimaSessao;
    }

    public Sessao getSessaoPorId(Long id) {
        Sessao sessao = null;
        List<Sessao> result = mSessaoDao.getSessao(id);
        if (result != null && !result.isEmpty())
            sessao = result.iterator().next();

        return sessao;
    }

    public List<Sessao> getMelhoresSessoes(int limite) {
        List<Sessao> result = mSessaoDao.getMelhoresSessoes(limite);
        if (result != null)
            return result;
        return null;
    }

    public void addSessao(Sessao sessao) {
        new AddSessaoTask().executeOnExecutor(AsyncTask.SERIAL_EXECUTOR, sessao);
    }

    public void updateSessao(Sessao sessao) {
        new UpdateSessaoTask().executeOnExecutor(AsyncTask.SERIAL_EXECUTOR, sessao);
    }

    public void removeSessao(Sessao sessao) {
        new RemoveSessaoTask().execute(sessao);
    }

    //Async task to add sessao
    public class AddSessaoTask extends AsyncTask<Sessao, Void, Void> {
        @Override
        protected Void doInBackground(Sessao... sessoes) {
            mSessaoDao.addSessao(sessoes[0]);
            return null;
        }
    }

    //Async task to update sessao
    public class UpdateSessaoTask extends AsyncTask<Sessao, Void, Void> {
        @Override
        protected Void doInBackground(Sessao... sessoes) {
            mSessaoDao.updateSessao(sessoes[0]);
            return null;
        }
    }

    //Async task to update sessao
    public class RemoveSessaoTask extends AsyncTask<Sessao, Void, Void> {
        @Override
        protected Void doInBackground(Sessao... sessoes) {
            mSessaoDao.removeSessao(sessoes[0]);
            return null;
        }
    }
}
