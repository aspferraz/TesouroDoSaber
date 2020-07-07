package com.seventech.tesourodosaber.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.util.Objects;

@Entity(tableName = "EQUIPE")
public class Equipe implements Parcelable, Serializable {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "COD_EQUIPE")
    private Long mId;

    @ColumnInfo(name = "DS_EQUIPE")
    private String mDescricao;

    @ColumnInfo(name = "DS_COLEGIO")
    private String mNomeEscola;

    @ColumnInfo(name = "QTDE_PARTICIPANTES")
    private Integer mQuantidadeParticipantes;

    public Equipe() {
        super();
    }

    public Equipe(Long id, String descricao, String nomeEscola, Integer quantidadeParticipantes) {
        mId = id;
        mDescricao = descricao;
        mNomeEscola = nomeEscola;
        mQuantidadeParticipantes = quantidadeParticipantes;
    }

    protected Equipe(Parcel in) {
        if (in.readByte() == 0) {
            mId = null;
        } else {
            mId = in.readLong();
        }
        mDescricao = in.readString();
        mNomeEscola = in.readString();
        if (in.readByte() == 0) {
            mQuantidadeParticipantes = null;
        } else {
            mQuantidadeParticipantes = in.readInt();
        }
    }

    public static final Creator<Equipe> CREATOR = new Creator<Equipe>() {
        @Override
        public Equipe createFromParcel(Parcel in) {
            return new Equipe(in);
        }

        @Override
        public Equipe[] newArray(int size) {
            return new Equipe[size];
        }
    };

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

    public String getNomeEscola() {
        return mNomeEscola;
    }

    public void setNomeEscola(String nomeEscola) {
        mNomeEscola = nomeEscola;
    }

    public Integer getQuantidadeParticipantes() {
        return mQuantidadeParticipantes;
    }

    public void setQuantidadeParticipantes(Integer quantidadeParticipantes) {
        mQuantidadeParticipantes = quantidadeParticipantes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Equipe equipe = (Equipe) o;
        return mId.equals(equipe.mId) &&
                mDescricao.equals(equipe.mDescricao);
    }

    @Override
    public int hashCode() {
        return Objects.hash(mId, mDescricao);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (mId == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeLong(mId);
        }
        dest.writeString(mDescricao);
        dest.writeString(mNomeEscola);
        if (mQuantidadeParticipantes == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(mQuantidadeParticipantes);
        }
    }
}
