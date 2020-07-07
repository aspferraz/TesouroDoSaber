package com.seventech.tesourodosaber.database.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.seventech.tesourodosaber.model.Localizacao;

import java.util.List;

@Dao
public interface LocalizacaoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void addLocalizacao(Localizacao localizacao);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateLocalizacao(Localizacao localizacao);

    @Delete
    void removeLocalizacao(Localizacao localizacao);

    @Query("delete from LOCALIZACAO")
    void removeAllLocalizacoes();

    @Query("select * from LOCALIZACAO")
    public List<Localizacao> getAllLocalizacoes();

    @Query("select l.* from LOCALIZACAO l join ENIGMA e where l.COD_ENIGMA_FK = e.COD_ENIGMA and e.COD_JORNADA_FK = :idJornada;")
    public List<Localizacao> getLocalizacoesPorIdJornada(Long idJornada);

    @Query("select * from LOCALIZACAO where COD_LOCALIZACAO = :idLocalizacao")
    public List<Localizacao> getLocalizacao(Long idLocalizacao);

    @Query("select * from LOCALIZACAO where COD_ENIGMA_FK = :idEnigma")
    public List<Localizacao> getLocalizacaoPorIdEnigma(Long idEnigma);

    @Query("select * from LOCALIZACAO where QRCODE = :qrCode")
    public List<Localizacao> getLocalizacaoPorQrCode(String qrCode);
}
