package com.seventech.tesourodosaber.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Objects;

@Entity(tableName = "JORNADA")
public class Jornada {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "COD_JORNADA")
    private Long mId;

    @NonNull
    @ColumnInfo(name = "DS_JORNADA")
    private String mDescricao;

    public Jornada(Long id, String descricao) {
        mId = id;
        mDescricao = descricao;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Jornada jornada = (Jornada) o;
        return Objects.equals(mId, jornada.mId) &&
                Objects.equals(mDescricao, jornada.mDescricao);
    }

    @Override
    public int hashCode() {
        return Objects.hash(mId, mDescricao);
    }
}
