package com.seventech.tesourodosaber.database.repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.annotation.NonNull;

import com.seventech.tesourodosaber.database.AppDatabase;
import com.seventech.tesourodosaber.database.dao.JornadaDao;
import com.seventech.tesourodosaber.model.Jornada;

import java.util.List;

public class JornadasRepository {

    private List<Jornada> mAllJornadas;
    JornadaDao mJornadaDao;

    public JornadasRepository(@NonNull Application application) {
        AppDatabase database = AppDatabase.getDatabase(application);

        mJornadaDao = database.jornadaDao();

        mAllJornadas = mJornadaDao.getAllJornadas();
    }

    public List<Jornada> getAllJornadas() {
        return mAllJornadas;
    }

    public void addJornada(Jornada jornada) {
        new AddJornadaTask().execute(jornada);
    }

    public void updateJornada(Jornada jornada) {
        new UpdateJornadaTask().execute(jornada);
    }

    public void removeJornada(Jornada jornada) {
        new RemoveJornadaTask().execute(jornada);
    }

    //Async task to add jornada
    public class AddJornadaTask extends AsyncTask<Jornada, Void, Void> {
        @Override
        protected Void doInBackground(Jornada... jornadas) {
            mJornadaDao.addJornada(jornadas[0]);
            return null;
        }
    }

    //Async task to update jornada
    public class UpdateJornadaTask extends AsyncTask<Jornada, Void, Void> {
        @Override
        protected Void doInBackground(Jornada... jornadas) {
            mJornadaDao.updateJornada(jornadas[0]);
            return null;
        }
    }

    //Async task to update jornada
    public class RemoveJornadaTask extends AsyncTask<Jornada, Void, Void> {
        @Override
        protected Void doInBackground(Jornada... jornadas) {
            mJornadaDao.removeJornada(jornadas[0]);
            return null;
        }
    }


}
