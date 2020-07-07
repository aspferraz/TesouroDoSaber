package com.seventech.tesourodosaber.database.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.seventech.tesourodosaber.model.Jornada;

import java.util.List;

@Dao
public interface JornadaDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void addJornada(Jornada jornada);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateJornada(Jornada jornada);

    @Delete
    void removeJornada(Jornada jornada);

    @Query("delete from JORNADA")
    void removeAllJornadas();

    @Query("select * from JORNADA")
    public List<Jornada> getAllJornadas();


    @Query("select * from JORNADA where COD_JORNADA = :idJornada")
    public List<Jornada> getJornada(Long idJornada);
}
