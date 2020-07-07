package com.seventech.tesourodosaber.database.repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.annotation.NonNull;

import com.seventech.tesourodosaber.database.AppDatabase;
import com.seventech.tesourodosaber.database.dao.EquipeDao;
import com.seventech.tesourodosaber.model.Equipe;

import java.util.List;

public class EquipesRepository {

    private List<Equipe> mAllEquipes;
    EquipeDao mEquipeDao;

    public EquipesRepository(@NonNull Application application) {
        AppDatabase database = AppDatabase.getDatabase(application);

        mEquipeDao = database.equipeDao();

        mAllEquipes = mEquipeDao.getAllEquipes();
    }

    public List<Equipe> getAllEquipes() {
        return mAllEquipes;
    }

    public Equipe getEquipePorId(Long id) {
        List<Equipe> result = mEquipeDao.getEquipe(id);
        if (result != null && !result.isEmpty())
            return result.iterator().next();
        return null;
    }

    public Equipe getUltimaEquipe() {
        List<Equipe> result = mEquipeDao.getUltimaEquipe();
        if (result != null && !result.isEmpty())
            return result.iterator().next();
        return null;
    }

    public void addEquipe(Equipe equipe) {
        new AddEquipeTask().executeOnExecutor(AsyncTask.SERIAL_EXECUTOR, equipe);
    }

    public void updateEquipe(Equipe equipe) {
        new UpdateEquipeTask().execute(equipe);
    }

    public void removeEquipe(Equipe equipe) {
        new RemoveEquipeTask().execute(equipe);
    }

    //Async task to add equipe
    public class AddEquipeTask extends AsyncTask<Equipe, Void, Void> {
        @Override
        protected Void doInBackground(Equipe... equipes) {
            mEquipeDao.addEquipe(equipes[0]);
            return null;
        }
    }

    //Async task to update equipe
    public class UpdateEquipeTask extends AsyncTask<Equipe, Void, Void> {
        @Override
        protected Void doInBackground(Equipe... equipes) {
            mEquipeDao.updateEquipe(equipes[0]);
            return null;
        }
    }

    //Async task to update equipe
    public class RemoveEquipeTask extends AsyncTask<Equipe, Void, Void> {
        @Override
        protected Void doInBackground(Equipe... equipes) {
            mEquipeDao.removeEquipe(equipes[0]);
            return null;
        }
    }
}
