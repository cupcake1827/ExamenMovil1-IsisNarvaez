package hn.uth.examen201820060151.database;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;


@Entity(tableName = "tbl_contactos")
public class Contactos implements Parcelable {
    @NonNull
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "contacto_id")
    private long contactoId;

    @NonNull
    @ColumnInfo(name = "nombre_contacto")
    private String nombre;

    @NonNull
    @ColumnInfo(name = "apellido_contacto")
    private String apellido;

    @NonNull
    @ColumnInfo(name = "correo_contacto")
    private String correo;

    @NonNull
    @ColumnInfo(name = "telefono_contacto")
    private String telefono;

    @ColumnInfo(name = "fecha_cumple")
    private String fechaCumple;

    @ColumnInfo(name = "ubicacion_id")
    private long ubicacionId;

    @ColumnInfo(name = "latitud_preferencia")
    private double latitudpreferencia;

    @ColumnInfo(name = "longitud_preferencia")
    private double longitudpreferencia;

    @ColumnInfo(name = "latitud_zonacompartida")
    private double latitudzonacompartida;

    @ColumnInfo(name = "longitud_zonacompartida")
    private double longitudzonacompartida;

    // Constructor, getters y setters


    public Contactos(@NonNull String nombre, @NonNull String apellido, @NonNull String correo, @NonNull String telefono, String fechaCumple, long ubicacionId, double latitudpreferencia, double longitudpreferencia, double latitudzonacompartida, double longitudzonacompartida) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.correo = correo;
        this.telefono = telefono;
        this.fechaCumple = fechaCumple;
        this.ubicacionId = ubicacionId;
        this.latitudpreferencia = latitudpreferencia;
        this.longitudpreferencia = longitudpreferencia;
        this.latitudzonacompartida = latitudzonacompartida;
        this.longitudzonacompartida = longitudzonacompartida;
    }

    public long getContactoId() {
        return contactoId;
    }

    public void setContactoId(long contactoId) {
        this.contactoId = contactoId;
    }

    @NonNull
    public String getNombre() {
        return nombre;
    }

    public void setNombre(@NonNull String nombre) {
        this.nombre = nombre;
    }

    @NonNull
    public String getApellido() {
        return apellido;
    }

    public void setApellido(@NonNull String apellido) {
        this.apellido = apellido;
    }

    @NonNull
    public String getCorreo() {
        return correo;
    }

    public void setCorreo(@NonNull String correo) {
        this.correo = correo;
    }

    @NonNull
    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(@NonNull String telefono) {
        this.telefono = telefono;
    }

    public String getFechaCumple() {
        return fechaCumple;
    }

    public void setFechaCumple(String fechaCumple) {
        this.fechaCumple = fechaCumple;
    }

    public long getUbicacionId() {
        return ubicacionId;
    }

    public void setUbicacionId(long ubicacionId) {
        this.ubicacionId = ubicacionId;
    }

    public double getLatitudpreferencia() {
        return latitudpreferencia;
    }

    public void setLatitudpreferencia(double latitudpreferencia) {
        this.latitudpreferencia = latitudpreferencia;
    }

    public double getLongitudpreferencia() {
        return longitudpreferencia;
    }

    public void setLongitudpreferencia(double longitudpreferencia) {
        this.longitudpreferencia = longitudpreferencia;
    }

    public double getLatitudzonacompartida() {
        return latitudzonacompartida;
    }

    public void setLatitudzonacompartida(double latitudzonacompartida) {
        this.latitudzonacompartida = latitudzonacompartida;
    }

    public double getLongitudzonacompartida() {
        return longitudzonacompartida;
    }

    public void setLongitudzonacompartida(double longitudzonacompartida) {
        this.longitudzonacompartida = longitudzonacompartida;
    }

    protected Contactos(Parcel in) {
        contactoId = in.readLong();
        nombre = in.readString();
        apellido = in.readString();
        correo = in.readString();
        telefono = in.readString();
        fechaCumple = in.readString();
        ubicacionId = in.readLong();
        latitudpreferencia = in.readDouble();
        longitudpreferencia = in.readDouble();
        latitudzonacompartida = in.readDouble();
        longitudzonacompartida = in.readDouble();
    }

    public static final Parcelable.Creator<Contactos> CREATOR = new Parcelable.Creator<Contactos>() {
        @Override
        public Contactos createFromParcel(Parcel in) {
            return new Contactos(in);
        }

        @Override
        public Contactos[] newArray(int size) {
            return new Contactos[size];
        }
    };


    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(contactoId);
        dest.writeString(nombre);
        dest.writeString(apellido);
        dest.writeString(correo);
        dest.writeString(telefono);
        dest.writeString(fechaCumple);
        dest.writeLong(ubicacionId);
        dest.writeDouble(latitudpreferencia);
        dest.writeDouble(longitudpreferencia);
        dest.writeDouble(latitudzonacompartida);
        dest.writeDouble(longitudzonacompartida);
    }


    public int describeContents() {
        return 0;
    }

}


