package com.seventech.tesourodosaber.database.repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.annotation.NonNull;

import com.seventech.tesourodosaber.database.AppDatabase;
import com.seventech.tesourodosaber.database.dao.EnigmaDao;
import com.seventech.tesourodosaber.model.Enigma;
import com.seventech.tesourodosaber.model.Jornada;

import java.util.Iterator;
import java.util.List;

public class EnigmasRepository {

    private List<Enigma> mAllEnigmas;
    EnigmaDao mEnigmaDao;

    public EnigmasRepository(@NonNull Application application) {
        AppDatabase database = AppDatabase.getDatabase(application);

        mEnigmaDao = database.enigmaDao();

        mAllEnigmas = mEnigmaDao.getAllEnigmas();
    }

    public List<Enigma> getAllEnigmas() {
        return mAllEnigmas;
    }

    public List<Enigma> getEnigmasByJornada(Jornada jornada) {
        return mEnigmaDao.getEnigmasByIdJornada(jornada.getId());
    }

    public Enigma getEnigmaById(Long idEnigma) {
        List<Enigma> l = mEnigmaDao.getEnigma(idEnigma);
        if (l != null && !l.isEmpty())
            return l.iterator().next();
        return null;
    }

    public Enigma getProximoEnigmaDaJornada(Enigma enigmaAtual) {
        List<Enigma> l = mEnigmaDao.getEnigmasByIdJornada(enigmaAtual.getIdJornada());
        Iterator<Enigma> itr = l.iterator();
        while (itr.hasNext()) {
            Enigma e = itr.next();
            if (e.equals(enigmaAtual) && itr.hasNext())
                return itr.next();
        }

        return null;
    }

    public void addEnigma(Enigma enigma) {
        new AddEnigmaTask().execute(enigma);
    }

    public void updateEnigma(Enigma enigma) {
        new UpdateEnigmaTask().execute(enigma);
    }

    public void removeEnigma(Enigma enigma) {
        new RemoveEnigmaTask().execute(enigma);
    }

    //Async task to add enigma
    public class AddEnigmaTask extends AsyncTask<Enigma, Void, Void> {
        @Override
        protected Void doInBackground(Enigma... enigmas) {
            mEnigmaDao.addEnigma(enigmas[0]);
            return null;
        }
    }

    //Async task to update enigma
    public class UpdateEnigmaTask extends AsyncTask<Enigma, Void, Void> {
        @Override
        protected Void doInBackground(Enigma... enigmas) {
            mEnigmaDao.updateEnigma(enigmas[0]);
            return null;
        }
    }

    //Async task to update enigma
    public class RemoveEnigmaTask extends AsyncTask<Enigma, Void, Void> {
        @Override
        protected Void doInBackground(Enigma... enigmas) {
            mEnigmaDao.removeEnigma(enigmas[0]);
            return null;
        }
    }
}
