package hn.uth.examen201820060151.database;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "tblubicaciones")
public class Ubicaciones implements Parcelable{
    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "id")
    private long ubicacionId;

    @NonNull
    @ColumnInfo(name = "persona")
    private String persona;

    @NonNull
    @ColumnInfo(name = "categoria")
    private String categoria;

    @ColumnInfo(name = "latitud")
    private double latitud;

    @ColumnInfo(name = "longitud")
    private double longitud;

    public Ubicaciones(@NonNull String persona, @NonNull String categoria, double latitud, double longitud) {
        this.persona = persona;
        this.categoria = categoria;
        this.latitud = latitud;
        this.longitud = longitud;
    }

    public long getUbicacionId() {
        return ubicacionId;
    }

    public void setUbicacionId(long ubicacionId) {
        this.ubicacionId = ubicacionId;
    }

    @NonNull
    public String getPersona() {
        return persona;
    }

    public void setPersona(@NonNull String persona) {
        this.persona = persona;
    }

    @NonNull
    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(@NonNull String categoria) {
        this.categoria = categoria;
    }

    public double getLatitud() {
        return latitud;
    }

    public void setLatitud(double latitud) {
        this.latitud = latitud;
    }

    public double getLongitud() {
        return longitud;
    }

    public void setLongitud(double longitud) {
        this.longitud = longitud;
    }

    protected Ubicaciones(Parcel in) {
        ubicacionId = in.readLong();
        persona = in.readString();
        categoria = in.readString();
        latitud = in.readDouble();
        longitud = in.readDouble();
    }

    public static final Parcelable.Creator<Ubicaciones> CREATOR = new Parcelable.Creator<Ubicaciones>() {
        @Override
        public Ubicaciones createFromParcel(Parcel in) {
            return new Ubicaciones(in);
        }

        @Override
        public Ubicaciones[] newArray(int size) {
            return new Ubicaciones[size];
        }
    };


    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(ubicacionId);
        dest.writeString(persona);
        dest.writeString(categoria);
        dest.writeDouble(latitud);
        dest.writeDouble(longitud);
    }


    public int describeContents() {
        return 0;
    }
}

