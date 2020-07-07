package com.seventech.tesourodosaber.database.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.seventech.tesourodosaber.model.Alternativa;

import java.util.List;

@Dao
public interface AlternativaDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void addAlternativa(Alternativa alternativa);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateAlternativa(Alternativa alternativa);

    @Delete
    void removeAlternativa(Alternativa alternativa);

    @Query("delete from ALTERNATIVA")
    void removeAllAlternativas();

    @Query("select * from ALTERNATIVA")
    public List<Alternativa> getAllAlternativas();

    @Query("select * from ALTERNATIVA where COD_ENIGMA_FK = :idEnigma")
    public List<Alternativa> getAlternativasByIdEnigma(Long idEnigma);

    @Query("select * from ALTERNATIVA where COD_ALTERNATIVA = :idAlternativa")
    public List<Alternativa> getAlternativa(Long idAlternativa);
}
