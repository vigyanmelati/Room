package id.vigyan.room;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import id.vigyan.room.database.AppDatabase;
import id.vigyan.room.database.entitas.Regis;

public class TambahActivity extends AppCompatActivity {
    private EditText NIK, Nama;
    private RadioGroup listOpsiJK;
    private RadioButton radioButton, female, male;
    private CheckBox checkBoxHalu, checkBoxStress, checkBoxMakan, checkBoxTidur;
    private TextView AlNIK, AlNama, AlJK, AlKeluhan;
    private Button btnReset, btnSubmit;
    String nik,nama,jk,keluhan;
    AlertDialog.Builder dialog;
    LayoutInflater inflater;
    View dialogView;
    private AppDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah);

        database = AppDatabase.getInstance(getApplicationContext());

        NIK = (EditText) findViewById(R.id.NIK);
        Nama = (EditText) findViewById(R.id.Nama);
        checkBoxHalu = (CheckBox) findViewById(R.id.checkBoxHalu);
        checkBoxStress = (CheckBox) findViewById(R.id.checkBoxStress);
        checkBoxMakan = (CheckBox) findViewById(R.id.checkBoxMakan);
        checkBoxTidur = (CheckBox) findViewById(R.id.checkBoxTidur);
        listOpsiJK = findViewById(R.id.opsiJK);
        female = findViewById(R.id.female);
        male = findViewById(R.id.male);
        btnReset= (Button) findViewById(R.id.btnReset);
        btnReset.setOnClickListener(clickListener);
        btnSubmit= (Button) findViewById(R.id.btnSubmit);
        btnSubmit.setOnClickListener(clickListener);
    }

    public View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btnSubmit:
                    nik = NIK.getText().toString();
                    nama = Nama.getText().toString();
                    if (!checkBoxHalu.isChecked() && !checkBoxStress.isChecked() && !checkBoxMakan.isChecked() && !checkBoxTidur.isChecked()){
                        Toast.makeText(getApplicationContext(), "Tidak ada keluhan yang dipilih", Toast.LENGTH_SHORT).show();
                    } else if (nik.matches("")||nama.matches("")){
                        Toast.makeText(getApplicationContext(), "Kolom NIK dan Nama Lengkap Tidak Boleh Kosong", Toast.LENGTH_SHORT).show();
                    } else if(listOpsiJK.getCheckedRadioButtonId() == -1){
                        Toast.makeText(getApplicationContext(), "Jenis Kelamin Tidak Boleh Kosong", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        int selectedId = listOpsiJK.getCheckedRadioButtonId();
                        radioButton = (RadioButton) findViewById(selectedId);
                        jk = radioButton.getText().toString();
                        keluhan = "";
                        if(checkBoxHalu.isChecked()){
                            if(keluhan == ""){
                                keluhan = "" + checkBoxHalu.getText();
                            }else{
                                keluhan = keluhan + "," + checkBoxHalu.getText();
                            }
                        }if(checkBoxStress.isChecked()){
                            if(keluhan == ""){
                                keluhan = "" + checkBoxStress.getText();
                            }else{
                                keluhan = keluhan + "," + checkBoxStress.getText();
                            }
                        }if(checkBoxMakan.isChecked()){
                            if(keluhan == ""){
                                keluhan = "" + checkBoxMakan.getText();
                            }else{
                                keluhan = keluhan + "," + checkBoxMakan.getText();
                            }
                        }if(checkBoxTidur.isChecked()){
                            if(keluhan == ""){
                                keluhan = "" + checkBoxTidur.getText();
                            }else{
                                keluhan = keluhan + "," + checkBoxTidur.getText();
                            }
                        }

                        DialogForm();
                    }
                    break;
                case R.id.btnReset:
                    NIK.setText(null);
                    Nama.setText(null);
                    listOpsiJK.clearCheck();
                    checkBoxHalu.setChecked(false);
                    checkBoxStress.setChecked(false);
                    checkBoxMakan.setChecked(false);
                    checkBoxTidur.setChecked(false);
                    break;
            }
        }
    };

    private void DialogForm(){
        dialog = new AlertDialog.Builder(TambahActivity.this);
        inflater = getLayoutInflater();
        dialogView = inflater.inflate(R.layout.alert_dialogs, null);
        dialog.setView(dialogView);
        dialog.setCancelable(true);

        AlNIK = (TextView) dialogView.findViewById(R.id.AlNIK);
        AlNama = (TextView) dialogView.findViewById(R.id.AlNama);
        AlJK = (TextView) dialogView.findViewById(R.id.AlJK);
        AlKeluhan = (TextView) dialogView.findViewById(R.id.AlKeluhan);

        AlNIK.setText("NIK\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t: " + nik);
        AlNama.setText("Nama\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t: " + nama);
        AlJK.setText("Jenis Kelamin\t\t\t\t\t\t\t\t: " + jk);
        AlKeluhan.setText("Keluhan\t\t\t\t\t\t\t\t\t\t\t\t\t:" + keluhan);

        dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                database.regisDao().insertAll(nik,nama,jk,keluhan);

                Intent intent1 = new Intent(TambahActivity.this, HasilForm.class);
                intent1.putExtra("NIK", nik);
                intent1.putExtra("NAMA", nama);
                intent1.putExtra("JK", jk);
                intent1.putExtra("KELUHAN", keluhan);
                startActivity(intent1);
            }
        }).setNegativeButton("Batal", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        dialog.show();

    }
}