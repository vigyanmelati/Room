package id.vigyan.room.database.entitas;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Regis {
    @PrimaryKey
    public int row_id;

    @ColumnInfo(name = "NIK")
    public String row_nik;

    @ColumnInfo(name = "Nama")
    public String row_nama;

    @ColumnInfo(name = "Jenis_Kelamin")
    public String row_jk;

    @ColumnInfo(name = "Keluhan")
    public String row_keluhan;


}
