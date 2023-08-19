package hn.uth.examen201820060151.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;
@Dao
public interface UbicacionesDao {
    @Insert
    long insert(Ubicaciones nuevo);

    @Update
    void update(Ubicaciones actualizar);

    @Delete
    void delete(Ubicaciones eliminar);
    @Query("DELETE FROM tblubicaciones")
    void deleteAll();

    @Query("SELECT * FROM tblubicaciones")
    LiveData<List<Ubicaciones>> getUbicaciones();

    @Query("SELECT * FROM tblubicaciones WHERE " +
            "REPLACE(persona || ' ' || categoria, ' ', '') " +
            "LIKE '%' || REPLACE(:query, ' ', '') || '%'")
    LiveData<List<Ubicaciones>> buscarUbicacion(String query);
}

