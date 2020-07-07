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

@Entity(tableName = "DICA",
        foreignKeys = {
                @ForeignKey(
                        entity = Enigma.class,
                        parentColumns = "COD_ENIGMA",
                        childColumns = "COD_ENIGMA_FK",
                        onDelete = ForeignKey.CASCADE,
                        onUpdate = ForeignKey.CASCADE
                )}
)
public class Dica implements Parcelable, Serializable {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "COD_DICA")
    private Long mId;

    @ColumnInfo(name = "DS_DICA")
    private String mDescricao;

    @ColumnInfo(name = "COD_ENIGMA_FK")
    private Long mIdEnigma;

    @Ignore
    private Enigma mEnigma;

    public Dica(Long id, String descricao, Long idEnigma) {
        mId = id;
        mDescricao = descricao;
        mIdEnigma = idEnigma;
    }

    protected Dica(Parcel in) {
        if (in.readByte() == 0) {
            mId = null;
        } else {
            mId = in.readLong();
        }
        mDescricao = in.readString();
        if (in.readByte() == 0) {
            mIdEnigma = null;
        } else {
            mIdEnigma = in.readLong();
        }
        mEnigma = in.readParcelable(Enigma.class.getClassLoader());
    }

    public static final Creator<Dica> CREATOR = new Creator<Dica>() {
        @Override
        public Dica createFromParcel(Parcel in) {
            return new Dica(in);
        }

        @Override
        public Dica[] newArray(int size) {
            return new Dica[size];
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
        Dica dica = (Dica) o;
        return mId.equals(dica.mId) &&
                mDescricao.equals(dica.mDescricao);
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
        if (mIdEnigma == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeLong(mIdEnigma);
        }
        dest.writeParcelable(mEnigma, flags);
    }
}
