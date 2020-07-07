package com.seventech.tesourodosaber.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.seventech.tesourodosaber.utils.DateConverter;

import java.util.Date;
import java.util.Objects;

@Entity(tableName = "SESSAO",
        foreignKeys = {
                @ForeignKey(
                        entity = Enigma.class,
                        parentColumns = "COD_ENIGMA",
                        childColumns = "COD_ULTIMO_ENIGMA_FK",
                        onDelete = ForeignKey.CASCADE,
                        onUpdate = ForeignKey.CASCADE
                ),
                @ForeignKey(
                        entity = Equipe.class,
                        parentColumns = "COD_EQUIPE",
                        childColumns = "COD_EQUIPE_FK",
                        onDelete = ForeignKey.CASCADE,
                        onUpdate = ForeignKey.CASCADE
                )
    }
)
public class Sessao {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "COD_SESSAO")
    private Long mId;

    @ColumnInfo(name = "INICIO_JOGO")
    @TypeConverters(DateConverter.class)
    private Date mInicioJogo;

    @ColumnInfo(name = "FIM_JOGO")
    @TypeConverters(DateConverter.class)
    private Date mFimJogo;

    @NonNull
    @ColumnInfo(name = "COD_ULTIMO_ENIGMA_FK")
    private Long mIdUltimoEnigma;

    @Ignore
    private Enigma mUltimoEnigma;

    @NonNull
    @ColumnInfo(name = "COD_EQUIPE_FK")
    private Long mIdEquipe;

    @Ignore
    private Equipe mEquipe;

    public Sessao() {
        super();
    }

    public Sessao(Long id, Date inicioJogo, Date fimJogo, Long idUltimoEnigma, Long idEquipe) {
        mId = id;
        mInicioJogo = inicioJogo;
        mFimJogo = fimJogo;
        mIdUltimoEnigma = idUltimoEnigma;
        mIdEquipe = idEquipe;
    }

    public Long getId() {
        return mId;
    }

    public void setId(Long id) {
        mId = id;
    }

    public Date getInicioJogo() {
        return mInicioJogo;
    }

    public void setInicioJogo(Date inicioJogo) {
        mInicioJogo = inicioJogo;
    }

    public Date getFimJogo() {
        return mFimJogo;
    }

    public void setFimJogo(Date fimJogo) {
        mFimJogo = fimJogo;
    }

    public Long getIdUltimoEnigma() {
        return mIdUltimoEnigma;
    }

    public void setIdUltimoEnigma(Long idUltimoEnigma) {
        mIdUltimoEnigma = idUltimoEnigma;
    }

    public Enigma getUltimoEnigma() {
        return mUltimoEnigma;
    }

    public void setUltimoEnigma(Enigma ultimoEnigma) {
        mUltimoEnigma = ultimoEnigma;
    }

    public Long getIdEquipe() {
        return mIdEquipe;
    }

    public void setIdEquipe(Long idEquipe) {
        mIdEquipe = idEquipe;
    }

    public Equipe getEquipe() {
        return mEquipe;
    }

    public void setEquipe(Equipe equipe) {
        mEquipe = equipe;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Sessao sessao = (Sessao) o;
        return mId.equals(sessao.mId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(mId);
    }
}
