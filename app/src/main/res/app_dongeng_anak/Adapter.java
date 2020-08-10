package com.example.namasteh.app_dongeng_anak;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.MyViewHolder> {

    private List<Users> users;
    private Context context;

    public Adapter(List<Users> users, Context context) {
        this.users = users;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_cari_cerita, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.id.setText(users.get(position).getId());
        holder.id_posting.setText(users.get(position).getId_posting());
        holder.tipe_cerita.setText(users.get(position).getTipe_cerita());
        holder.judul.setText(users.get(position).getJudul());
        holder.tgl_posting.setText(users.get(position).getTgl_posting());
//        Date date = users.get(position).getTgl_posting();
//        android.text.format.DateFormat df = new android.text.format.DateFormat();
//        holder.tgl_posting.setText(df.format("dd/MM/yyyy hh:mm:ss", date).toString());
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView judul,id,id_posting,tipe_cerita,tgl_posting;

        public MyViewHolder(View itemView){
            super(itemView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(v.getContext(),menu_baca_cerita.class);
                    i.putExtra("id",id_posting.getText().toString());
                    v.getContext().startActivity(i);
                }
            });

            id =  itemView.findViewById(R.id.id);
            judul =  itemView.findViewById(R.id.judul);
            tipe_cerita =  itemView.findViewById(R.id.tipe_cerita);
            id_posting =  itemView.findViewById(R.id.id_posting);
            tgl_posting =  itemView.findViewById(R.id.tgl_posting);

        }
    }
}
