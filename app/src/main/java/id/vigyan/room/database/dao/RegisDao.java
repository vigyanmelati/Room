package id.vigyan.room.database.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import id.vigyan.room.database.entitas.Regis;

@Dao
public interface RegisDao {
    @Query("SELECT * FROM regis")
    List<Regis> getAll();

    @Query("INSERT INTO regis (NIK,Nama,Jenis_Kelamin,Keluhan) VALUES(:NIK,:Nama,:Jenis_Kelamin,:Keluhan)")
    void insertAll(String NIK, String Nama, String Jenis_Kelamin, String Keluhan);

//    @Insert
//    void insertAll(Regis... regiss);

    @Delete
    void delete(Regis regis);

}
