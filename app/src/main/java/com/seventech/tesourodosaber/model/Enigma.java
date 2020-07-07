package com.seventech.tesourodosaber.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.util.Objects;

@Entity(tableName = "ENIGMA",
        foreignKeys = {
                @ForeignKey(
                        entity = Jornada.class,
                        parentColumns = "COD_JORNADA",
                        childColumns = "COD_JORNADA_FK",
                        onDelete = ForeignKey.CASCADE,
                        onUpdate = ForeignKey.CASCADE
                )}
)
public class Enigma implements Parcelable, Serializable {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "COD_ENIGMA")
    private Long mId;

    @NonNull
    @ColumnInfo(name = "DS_ENIGMA")
    private String mDescricao;

    @NonNull
    @ColumnInfo(name = "COD_JORNADA_FK")
    private Long mIdJornada;

    @Ignore
    private Jornada mJornada;

    public Enigma(Long mId, String mDescricao, Long mIdJornada) {
        this.mId = mId;
        this.mDescricao = mDescricao;
        this.mIdJornada = mIdJornada;
    }

    protected Enigma(Parcel in) {
        if (in.readByte() == 0) {
            mId = null;
        } else {
            mId = in.readLong();
        }
        mDescricao = in.readString();
        if (in.readByte() == 0) {
            mIdJornada = null;
        } else {
            mIdJornada = in.readLong();
        }
    }

    public static final Creator<Enigma> CREATOR = new Creator<Enigma>() {
        @Override
        public Enigma createFromParcel(Parcel in) {
            return new Enigma(in);
        }

        @Override
        public Enigma[] newArray(int size) {
            return new Enigma[size];
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

    public Long getIdJornada() {
        return mIdJornada;
    }

    public void setIdJornada(Long idJornada) {
        mIdJornada = idJornada;
    }

    public Jornada getJornada() {
        return mJornada;
    }

    public void setJornada(Jornada jornada) {
        mJornada = jornada;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Enigma enigma = (Enigma) o;
        return mId.equals(enigma.mId) &&
                mDescricao.equals(enigma.mDescricao);
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
        if (mIdJornada == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeLong(mIdJornada);
        }
    }
}
