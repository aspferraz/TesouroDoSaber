package com.seventech.tesourodosaber.database.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.seventech.tesourodosaber.model.Equipe;

import java.util.List;

@Dao
public interface EquipeDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void addEquipe(Equipe equipe);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateEquipe(Equipe equipe);

    @Delete
    void removeEquipe(Equipe equipe);

    @Query("delete from EQUIPE")
    void removeAllEquipes();

    @Query("select * from EQUIPE")
    public List<Equipe> getAllEquipes();

    @Query("select * from EQUIPE order by COD_EQUIPE desc limit 1")
    public List<Equipe> getUltimaEquipe();


    @Query("select * from EQUIPE where COD_EQUIPE = :idEquipe")
    public List<Equipe> getEquipe(Long idEquipe);
}
