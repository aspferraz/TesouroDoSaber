package com.seventech.tesourodosaber.database.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.seventech.tesourodosaber.model.Enigma;

import java.util.List;

@Dao
public interface EnigmaDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void addEnigma(Enigma enigma);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateEnigma(Enigma enigma);

    @Delete
    void removeEnigma(Enigma enigma);

    @Query("delete from ENIGMA")
    void removeAllEnigmas();

    @Query("select * from ENIGMA")
    public List<Enigma> getAllEnigmas();

    @Query("select * from ENIGMA where COD_JORNADA_FK = :idJornada order by COD_ENIGMA asc")
    public List<Enigma> getEnigmasByIdJornada(Long idJornada);

    @Query("select * from ENIGMA where COD_ENIGMA = :idEnigma")
    public List<Enigma> getEnigma(Long idEnigma);
}
