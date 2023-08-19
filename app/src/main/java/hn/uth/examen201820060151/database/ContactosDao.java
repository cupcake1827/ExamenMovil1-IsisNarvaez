package hn.uth.examen201820060151.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;
@Dao
public interface ContactosDao {
    @Insert
    long insert(Contactos nuevo);

    @Update
    void update(Contactos actualizar);

    @Delete
    void delete(Contactos eliminar);

    @Query("DELETE FROM tbl_contactos")
    void deleteAll();

    @Query("SELECT * FROM tbl_contactos")
    LiveData<List<Contactos>> getContactos();

    @Query("SELECT * FROM tbl_contactos WHERE " +
            "REPLACE(nombre_contacto || ' ' || apellido_contacto, ' ', '') " +
            "LIKE '%' || REPLACE(:query, ' ', '') || '%'")
    LiveData<List<Contactos>> buscarContacto(String query);

}

