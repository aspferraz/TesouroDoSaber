package com.seventech.tesourodosaber.database.repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.annotation.NonNull;

import com.seventech.tesourodosaber.database.AppDatabase;
import com.seventech.tesourodosaber.database.dao.DicaDao;
import com.seventech.tesourodosaber.model.Dica;
import com.seventech.tesourodosaber.model.Enigma;

import java.util.List;

public class DicasRepository {

    private List<Dica> mAllDicas;
    DicaDao mDicaDao;

    public DicasRepository(@NonNull Application application) {
        AppDatabase database = AppDatabase.getDatabase(application);

        mDicaDao = database.dicaDao();

        mAllDicas = mDicaDao.getAllDicas();
    }

    public List<Dica> getAllDicas() {
        return mAllDicas;
    }

    public List<Dica> getDicasByEnigma(Enigma enigma) {
        return mDicaDao.getDicasByIdEnigma(enigma.getId());
    }

    public void addDica(Dica dica) {
        new AddDicaTask().execute(dica);
    }

    public void updateDica(Dica dica) {
        new UpdateDicaTask().execute(dica);
    }

    public void removeDica(Dica dica) {
        new RemoveDicaTask().execute(dica);
    }

    //Async task to add dica
    public class AddDicaTask extends AsyncTask<Dica, Void, Void> {
        @Override
        protected Void doInBackground(Dica... dicas) {
            mDicaDao.addDica(dicas[0]);
            return null;
        }
    }

    //Async task to update dica
    public class UpdateDicaTask extends AsyncTask<Dica, Void, Void> {
        @Override
        protected Void doInBackground(Dica... dicas) {
            mDicaDao.updateDica(dicas[0]);
            return null;
        }
    }

    //Async task to update dica
    public class RemoveDicaTask extends AsyncTask<Dica, Void, Void> {
        @Override
        protected Void doInBackground(Dica... dicas) {
            mDicaDao.removeDica(dicas[0]);
            return null;
        }
    }
}
