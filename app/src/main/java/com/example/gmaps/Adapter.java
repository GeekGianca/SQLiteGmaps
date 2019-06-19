package com.example.gmaps;

import android.content.Context;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

class AdapterViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener {
    TextView nombre;
    TextView direccion;
    TextView latitud;
    TextView longitud;
    TextView idpersona;

    public AdapterViewHolder(@NonNull View itemView) {
        super(itemView);
        nombre = itemView.findViewById(R.id.nombre);
        direccion = itemView.findViewById(R.id.direccion);
        latitud = itemView.findViewById(R.id.latitud);
        longitud = itemView.findViewById(R.id.longitud);
        idpersona = itemView.findViewById(R.id.idpersona);
        itemView.setOnCreateContextMenuListener(this);
    }

    @Override
    public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {
        contextMenu.setHeaderTitle("Realizar accion");
        contextMenu.add(0,0, getAdapterPosition(), "Mostrar en mapa");
        contextMenu.add(0,1, getAdapterPosition(), "Eliminar");
        contextMenu.add(0,2, getAdapterPosition(), "Actualizar");
    }
}

public class Adapter extends RecyclerView.Adapter<AdapterViewHolder>{
    private List<Person> personas;
    private Context context;

    public Adapter(List<Person> personas, Context context) {
        this.personas = personas;
        this.context = context;
    }

    @NonNull
    @Override
    public AdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View itemView = inflater.inflate(R.layout.item_recycler, parent, false);
        return new AdapterViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterViewHolder holder, int position) {
        holder.direccion.setText(personas.get(position).getDireccion());
        holder.nombre.setText(personas.get(position).getNombre());
        holder.latitud.setText(personas.get(position).getLat()+"");
        holder.longitud.setText(personas.get(position).getLng()+"");
        holder.idpersona.setText(personas.get(position).getId()+"");
    }

    @Override
    public int getItemCount() {
        return personas.size();
    }
}
