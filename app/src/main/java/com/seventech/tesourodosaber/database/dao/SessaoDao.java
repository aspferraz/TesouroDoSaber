package com.seventech.tesourodosaber.database.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.TypeConverters;
import androidx.room.Update;

import com.seventech.tesourodosaber.model.Sessao;
import com.seventech.tesourodosaber.utils.DateConverter;

import java.util.List;

@Dao
@TypeConverters(DateConverter.class)
public interface SessaoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void addSessao(Sessao sessao);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateSessao(Sessao sessao);

    @Delete
    void removeSessao(Sessao sessao);

    @Query("delete from SESSAO")
    void removeAllSessoes();

    @Query("select * from SESSAO")
    public List<Sessao> getAllSessoes();

    @Query("select * from SESSAO order by COD_SESSAO desc limit 1")
    public List<Sessao> getUltimaSessao();

    @Query("select * from SESSAO s where s.FIM_JOGO is not NULL order by (s.FIM_JOGO - s.INICIO_JOGO) asc limit :limite")
    public List<Sessao> getMelhoresSessoes(int limite);

    @Query("select * from SESSAO where COD_SESSAO = :idSessao")
    public List<Sessao> getSessao(Long idSessao);

}
