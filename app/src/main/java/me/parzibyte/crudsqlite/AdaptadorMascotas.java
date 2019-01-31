package me.parzibyte.crudsqlite;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import me.parzibyte.crudsqlite.modelos.Mascota;

public class AdaptadorMascotas extends RecyclerView.Adapter<AdaptadorMascotas.MyViewHolder> {

    private List<Mascota> listaDeMascotas;

    public void setListaDeMascotas(List<Mascota> listaDeMascotas) {
        this.listaDeMascotas = listaDeMascotas;
    }

    public AdaptadorMascotas(List<Mascota> mascotas) {
        this.listaDeMascotas = mascotas;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View filaMascota = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.fila_mascota, viewGroup, false);
        return new MyViewHolder(filaMascota);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        // Obtener la mascota de nuestra lista gracias al índice i
        Mascota mascota = listaDeMascotas.get(i);

        // Obtener los datos de la lista
        String nombreMascota = mascota.getNombre();
        int edadMascota = mascota.getEdad();
        // Y poner a los TextView los datos con setText
        myViewHolder.nombre.setText(nombreMascota);
        myViewHolder.edad.setText(String.valueOf(edadMascota));
    }

    @Override
    public int getItemCount() {
        return listaDeMascotas.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView nombre, edad;

        MyViewHolder(View itemView) {
            super(itemView);
            this.nombre = itemView.findViewById(R.id.tvNombre);
            this.edad = itemView.findViewById(R.id.tvEdad);
        }
    }
}
