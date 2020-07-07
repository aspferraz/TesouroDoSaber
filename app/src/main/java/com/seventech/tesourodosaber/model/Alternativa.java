package com.seventech.tesourodosaber.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.Objects;

@Entity(tableName = "ALTERNATIVA",
        foreignKeys = {
            @ForeignKey(
                entity = Enigma.class,
                parentColumns = "COD_ENIGMA",
                childColumns = "COD_ENIGMA_FK",
                onDelete = ForeignKey.CASCADE,
                onUpdate = ForeignKey.CASCADE
        )}
)
public class Alternativa {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "COD_ALTERNATIVA")
    private Long mId;

    @NonNull
    @ColumnInfo(name = "DS_ALTERNATIVA")
    private String mDescricao;

    @NonNull
    @ColumnInfo(name = "IS_CORRETO")
    private Boolean mIsCorreta;

    @NonNull
    @ColumnInfo(name = "COD_ENIGMA_FK")
    private Long mIdEnigma;

    @Ignore
    private Enigma mEnigma;

    public Alternativa(Long id, String descricao, Boolean isCorreta, Long idEnigma) {
        mId = id;
        mDescricao = descricao;
        mIsCorreta = isCorreta;
        mIdEnigma = idEnigma;
    }

    public Long getId() {
        return mId;
    }

    public void setId(Long id) {
        mId = id;
    }

    public String getDescricao() {
        return mDescricao;
    }

    public void setDescricao(String descricao) {
        mDescricao = descricao;
    }

    public Boolean isCorreta() {
        return mIsCorreta;
    }

    public void setCorreta(Boolean correta) {
        mIsCorreta = correta;
    }

    public Long getIdEnigma() {
        return mIdEnigma;
    }

    public void setIdEnigma(Long idEnigma) {
        mIdEnigma = idEnigma;
    }

    public Enigma getEnigma() {
        return mEnigma;
    }

    public void setEnigma(Enigma enigma) {
        mEnigma = enigma;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Alternativa that = (Alternativa) o;
        return mId.equals(that.mId) &&
                mDescricao.equals(that.mDescricao);
    }

    @Override
    public int hashCode() {
        return Objects.hash(mId, mDescricao);
    }
}
