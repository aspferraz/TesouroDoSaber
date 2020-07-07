package com.seventech.tesourodosaber.session;

import android.app.Application;
import android.content.SharedPreferences;
import android.os.AsyncTask;

import androidx.annotation.NonNull;

import com.seventech.tesourodosaber.database.repository.EnigmasRepository;
import com.seventech.tesourodosaber.database.repository.EquipesRepository;
import com.seventech.tesourodosaber.database.repository.SessoesRepository;
import com.seventech.tesourodosaber.model.Jornada;
import com.seventech.tesourodosaber.model.Sessao;

import java.util.Date;

public final class SessionManager {

    private static final String PREF_LAST_SESSION_ID_KEY = "lastSessionIdKey";

    private SharedPreferences mPrefs;

    // Shared pref mode
    private int PRIVATE_MODE = 0;

    // Sharedpref file name
    private static final String SESSION_PREFS = "sessionPrefs";


    private static SessionManager sInstance;

    private Sessao mCurrentSession;

    private static Jornada sJornada;

    static SessoesRepository sSessoesRepository;
    static EquipesRepository sEquipesRepository;
    static EnigmasRepository sEnigmasRepository;

    private SessionManager(Application context) {
        super();
        mPrefs = context.getSharedPreferences(SESSION_PREFS, PRIVATE_MODE);
        sSessoesRepository = new SessoesRepository(context);
        sEquipesRepository = new EquipesRepository(context);
        sEnigmasRepository = new EnigmasRepository(context);
    }

    public static SessionManager with(@NonNull Application context) {
        if (sInstance == null)
            sInstance = new SessionManager(context);
        return sInstance;
    }

    public Sessao getCurrentSession() {
        return mCurrentSession;
    }

    public Sessao getLastSession() {
        return sSessoesRepository.getUltimaSessao();
    }

    public void init(Jornada jornada) {
            sJornada = jornada;
//        if (mCurrentSession == null) {
            mCurrentSession = new Sessao();
            mCurrentSession.setInicioJogo(new Date());
            new SaveSessionTask().executeOnExecutor(AsyncTask.SERIAL_EXECUTOR, mCurrentSession);
//        }
    }

    public void finish() {
        mergeCurrentSession();
        mCurrentSession.setFimJogo(new Date());

        if (mCurrentSession.getId() == null) {
            throw new IllegalStateException("Session id must be different from null. ");
        }

        new SaveSessionTask().executeOnExecutor(AsyncTask.SERIAL_EXECUTOR, mCurrentSession);
    }

    public void abort() {
        mergeCurrentSession();
        mPrefs.edit().putLong(PREF_LAST_SESSION_ID_KEY, mCurrentSession.getId()).commit();
        new SaveSessionTask().executeOnExecutor(AsyncTask.SERIAL_EXECUTOR, mCurrentSession);
    }

    private void mergeCurrentSession() {
        if (mCurrentSession != null) {
            Sessao lastSession = sSessoesRepository.getUltimaSessao();
            if (mCurrentSession.getInicioJogo().equals(lastSession.getInicioJogo())) {
                mCurrentSession.setId(lastSession.getId());
                mCurrentSession.setIdEquipe(lastSession.getIdEquipe());
                mCurrentSession.setEquipe(sEquipesRepository.getUltimaEquipe());
            }
            else {
                throw new IllegalStateException("Current session has not yet been persisted. ");
            }
        }
    }

    private static class SaveSessionTask extends AsyncTask<Sessao, Void, Void> {

        @Override
        protected Void doInBackground(Sessao... sessoes) {
            Sessao s = sessoes[0];
            if (s.getId() == null) {
                s.setIdEquipe(sEquipesRepository.getUltimaEquipe().getId());
                s.setIdUltimoEnigma(sEnigmasRepository.getEnigmasByJornada(sJornada).get(0).getId());
                sSessoesRepository.addSessao(sessoes[0]);
            }
            else
                sSessoesRepository.updateSessao(sessoes[0]);
            return null;
        }
    }
}
