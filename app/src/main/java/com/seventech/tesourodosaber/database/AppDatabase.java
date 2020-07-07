package com.seventech.tesourodosaber.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.seventech.tesourodosaber.database.dao.AlternativaDao;
import com.seventech.tesourodosaber.database.dao.DicaDao;
import com.seventech.tesourodosaber.database.dao.EnigmaDao;
import com.seventech.tesourodosaber.database.dao.EquipeDao;
import com.seventech.tesourodosaber.database.dao.JornadaDao;
import com.seventech.tesourodosaber.database.dao.LocalizacaoDao;
import com.seventech.tesourodosaber.database.dao.SessaoDao;
import com.seventech.tesourodosaber.model.Alternativa;
import com.seventech.tesourodosaber.model.Dica;
import com.seventech.tesourodosaber.model.Enigma;
import com.seventech.tesourodosaber.model.Equipe;
import com.seventech.tesourodosaber.model.Jornada;
import com.seventech.tesourodosaber.model.Localizacao;
import com.seventech.tesourodosaber.model.Sessao;

@Database(entities = {Alternativa.class, Dica.class, Enigma.class, Equipe.class, Jornada.class, Localizacao.class, Sessao.class},
        version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase mInstance;

    //Dao's definition
    public abstract AlternativaDao alternativaDao();
    public abstract DicaDao dicaDao();
    public abstract EnigmaDao enigmaDao();
    public abstract EquipeDao equipeDao();
    public abstract JornadaDao jornadaDao();
    public abstract LocalizacaoDao localizacaoDao();
    public abstract SessaoDao sessaoDao();


    public static AppDatabase getDatabase(Context context) {
        if (mInstance == null) {
            mInstance =
                    Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, "internal")
//Room.inMemoryDatabaseBuilder(context.getApplicationContext(), AppDatabase.class)
                            // To simplify the exercise, allow queries on the main thread.
                            // Don't do this on a real app!
                            .allowMainThreadQueries()
                            // recreate the database if necessary
//                            .fallbackToDestructiveMigration()
                            .createFromAsset("database/internal.db")
                            .build();
        }
        return mInstance;
    }

    public static void destroyInstance() {
        mInstance = null;
    }
}
