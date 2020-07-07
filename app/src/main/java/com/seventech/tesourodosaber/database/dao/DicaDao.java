package com.seventech.tesourodosaber.database.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.seventech.tesourodosaber.model.Dica;

import java.util.List;

@Dao
public interface DicaDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void addDica(Dica dica);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateDica(Dica dica);

    @Delete
    void removeDica(Dica dica);

    @Query("delete from DICA")
    void removeAllDicas();

    @Query("select * from DICA")
    public List<Dica> getAllDicas();

    @Query("select * from DICA where COD_ENIGMA_FK = :idEnigma")
    public List<Dica> getDicasByIdEnigma(Long idEnigma);


    @Query("select * from DICA where COD_DICA = :idDica")
    public List<Dica> getDica(Long idDica);
}
