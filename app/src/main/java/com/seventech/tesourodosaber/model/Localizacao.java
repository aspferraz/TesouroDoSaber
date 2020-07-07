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

@Entity(tableName = "LOCALIZACAO",
        foreignKeys = {
                @ForeignKey(
                        entity = Enigma.class,
                        parentColumns = "COD_ENIGMA",
                        childColumns = "COD_ENIGMA_FK",
                        onDelete = ForeignKey.CASCADE,
                        onUpdate = ForeignKey.CASCADE
                )}
)
public class Localizacao implements Parcelable, Serializable {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "COD_LOCALIZACAO")
    private Long mId;

    @ColumnInfo(name = "QRCODE")
    private String mQrCode;

    @ColumnInfo(name = "IMG_MAPA")
    private String mCaminhoArquivoMapa;

    @NonNull
    @ColumnInfo(name = "COD_ENIGMA_FK")
    private Long mIdEnigma;

    @Ignore
    private Enigma mEnigma;

    public Localizacao(Long id, String qrCode, String caminhoArquivoMapa, Long idEnigma) {
        mId = id;
        mQrCode = qrCode;
        mCaminhoArquivoMapa = caminhoArquivoMapa;
        mIdEnigma = idEnigma;
    }

    protected Localizacao(Parcel in) {
        if (in.readByte() == 0) {
            mId = null;
        } else {
            mId = in.readLong();
        }
        mQrCode = in.readString();
        mCaminhoArquivoMapa = in.readString();
        if (in.readByte() == 0) {
            mIdEnigma = null;
        } else {
            mIdEnigma = in.readLong();
        }
        mEnigma = in.readParcelable(Enigma.class.getClassLoader());
    }

    public static final Creator<Localizacao> CREATOR = new Creator<Localizacao>() {
        @Override
        public Localizacao createFromParcel(Parcel in) {
            return new Localizacao(in);
        }

        @Override
        public Localizacao[] newArray(int size) {
            return new Localizacao[size];
        }
    };

    public Long getId() {
        return mId;
    }

    public void setId(Long id) {
        mId = id;
    }

    public String getQrCode() {
        return mQrCode;
    }

    public void setQrCode(String qrCode) {
        mQrCode = qrCode;
    }

    public String getCaminhoArquivoMapa() {
        return mCaminhoArquivoMapa;
    }

    public void setCaminhoArquivoMapa(String caminhoArquivoMapa) {
        mCaminhoArquivoMapa = caminhoArquivoMapa;
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
        Localizacao that = (Localizacao) o;
        return mId.equals(that.mId) &&
                mQrCode.equals(that.mQrCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(mId, mQrCode);
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
        dest.writeString(mQrCode);
        dest.writeString(mCaminhoArquivoMapa);
        if (mIdEnigma == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeLong(mIdEnigma);
        }
        dest.writeParcelable(mEnigma, flags);
    }
}
