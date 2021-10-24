package id.vigyan.room.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import id.vigyan.room.R;
import id.vigyan.room.database.entitas.Regis;

public class RegisAdapter extends RecyclerView.Adapter<RegisAdapter.ViewAdapter> {
    private List<Regis> list;
    private Context context;
    private Dialog dialog;

    public interface Dialog{
        void onClick(int position);
    }

    public void setDialog(Dialog dialog){
        this.dialog = dialog;
    }

    public RegisAdapter(Context context, List<Regis> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_regis, parent, false);
        return new ViewAdapter(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewAdapter holder, int position) {

        holder.nikTV.setText(list.get(position).row_nik);
        holder.namaTV.setText(list.get(position).row_nama);
        holder.jkTV.setText(list.get(position).row_jk);
        holder.keluhanTV.setText(list.get(position).row_keluhan);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewAdapter extends RecyclerView.ViewHolder{
        private TextView nikTV, namaTV, jkTV, keluhanTV;

        public ViewAdapter(@NonNull View itemView) {
            super(itemView);

            nikTV = itemView.findViewById(R.id.nikTV);
            namaTV = itemView.findViewById(R.id.namaTV);
            jkTV = itemView.findViewById(R.id.jkTV);
            keluhanTV = itemView.findViewById(R.id.keluhanTV);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(dialog!=null){
                        dialog.onClick(getLayoutPosition());
                    }

                }
            });
        }
    }
}
