package com.seventech.tesourodosaber.database.repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.annotation.NonNull;

import com.seventech.tesourodosaber.database.AppDatabase;
import com.seventech.tesourodosaber.database.dao.AlternativaDao;
import com.seventech.tesourodosaber.model.Alternativa;
import com.seventech.tesourodosaber.model.Enigma;

import java.util.List;

public class AlternativasRepository {

    private List<Alternativa> mAllAlternativas;
    AlternativaDao mAlternativaDao;

    public AlternativasRepository(@NonNull Application application) {
        AppDatabase database = AppDatabase.getDatabase(application);

        mAlternativaDao = database.alternativaDao();
        //get all alternativas
        mAllAlternativas = mAlternativaDao.getAllAlternativas();
    }

    public List<Alternativa> getAllAlternativas() {
        return mAllAlternativas;
    }

    public List<Alternativa> getAlternativasByEnigma(Enigma enigma) {
        return mAlternativaDao.getAlternativasByIdEnigma(enigma.getId());
    }

    public void addAlternativa(Alternativa alternativa) {
        new AddAlternativaTask().execute(alternativa);
    }

    public void updateAlternativa(Alternativa alternativa) {
        new UpdateAlternativaTask().execute(alternativa);
    }

    public void removeAlternativa(Alternativa alternativa) {
        new RemoveAlternativaTask().execute(alternativa);
    }

    //Async task to add alternativa
    public class AddAlternativaTask extends AsyncTask<Alternativa, Void, Void> {
        @Override
        protected Void doInBackground(Alternativa... alternativas) {
            mAlternativaDao.addAlternativa(alternativas[0]);
            return null;
        }
    }

    //Async task to update alternativa
    public class UpdateAlternativaTask extends AsyncTask<Alternativa, Void, Void> {
        @Override
        protected Void doInBackground(Alternativa... alternativas) {
            mAlternativaDao.updateAlternativa(alternativas[0]);
            return null;
        }
    }

    //Async task to update alternativa
    public class RemoveAlternativaTask extends AsyncTask<Alternativa, Void, Void> {
        @Override
        protected Void doInBackground(Alternativa... alternativas) {
            mAlternativaDao.removeAlternativa(alternativas[0]);
            return null;
        }
    }
}
