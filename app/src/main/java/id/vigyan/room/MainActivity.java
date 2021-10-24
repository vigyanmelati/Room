package id.vigyan.room;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

import id.vigyan.room.adapter.RegisAdapter;
import id.vigyan.room.database.AppDatabase;
import id.vigyan.room.database.entitas.Regis;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerviewregis;
    private Button btn_plus;
    private AppDatabase database;
    private RegisAdapter regisAdapter;
    private List<Regis> list = new ArrayList<>();
    private AlertDialog.Builder dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        database = AppDatabase.getInstance(getApplicationContext());
        list.clear();
        list.addAll(database.regisDao().getAll());
        regisAdapter = new RegisAdapter(getApplicationContext(), list);
        regisAdapter.setDialog(new RegisAdapter.Dialog() {
            @Override
            public void onClick(int position) {
                final CharSequence[] dialogitem = {"Hapus", "Batal"};
                dialog = new AlertDialog.Builder(MainActivity.this);
                dialog.setItems(dialogitem, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        switch (i){
                            case 0:
                                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                                builder.setTitle("Konfirmasi");
                                builder.setMessage("Apakah Anda yakin ingin menghapus data ?");
                                builder.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Regis regis = list.get(position);
                                        database.regisDao().delete(regis);
                                        onStart();
                                    }
                                }).setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                });
                                AlertDialog popupKonfirmasi = builder.create();
                                popupKonfirmasi.show();
                                break;
                            case 1:
                                dialog.dismiss();
                                break;
                        }
                    }
                });
                dialog.show();

            }
        });
        recyclerviewregis = findViewById(R.id.recyclerviewregis);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(), RecyclerView.VERTICAL, false);
        recyclerviewregis.setLayoutManager(layoutManager);
        recyclerviewregis.setAdapter(regisAdapter);

        btn_plus = (Button) findViewById(R.id.btn_plus);
        btn_plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentFormAdd = new Intent(getApplicationContext(), TambahActivity.class);
                startActivity(intentFormAdd);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        list.clear();
        list.addAll(database.regisDao().getAll());
        regisAdapter.notifyDataSetChanged();
    }
}