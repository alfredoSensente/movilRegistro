package com.example.movilesregistro;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class RVAdapter extends RecyclerView.Adapter<RVAdapter.PersonViewHolder> {
    List<Student> persons;
    Context context;
    MainActivity activity;

    public RVAdapter(MainActivity activity,List<Student> persons, Context context) {
        this.persons = persons;
        this.context = context;
        this.activity = activity;
    }

    @NonNull
    @Override
    public RVAdapter.PersonViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_list,null,false);
        PersonViewHolder pvh= new PersonViewHolder(view);
        pvh.getAdapterPosition();
        return pvh;
    }

    @Override
    public void onBindViewHolder(@NonNull final RVAdapter.PersonViewHolder personViewHolder, final int i) {
        final int itemSelecionado=personViewHolder.getAdapterPosition();
        final MainActivity ma=new MainActivity();
        personViewHolder.name.setText(persons.get(i).nombre);
        personViewHolder.age.setText(persons.get(i).edad);
        personViewHolder.carnet.setText(persons.get(i).carnet);
        personViewHolder.career.setText(persons.get(i).carrera);
        personViewHolder.photo.setImageURI(persons.get(i).foto);
        personViewHolder.delete.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder=new AlertDialog.Builder(context);
                builder.setMessage("¿Estas Seguro de que quieres eliminar a "+persons.get(i).nombre+" de la lista?");
                builder.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        persons.remove(itemSelecionado);
                        notifyItemRemoved(itemSelecionado);
                    }
                });
                builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                AlertDialog notificacion=builder.create();
                notificacion.show();
            }
        });
        personViewHolder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                final View view = LayoutInflater.from(context).inflate(R.layout.add_data,null);
                final EditText name = view.findViewById(R.id.add_name);
                final EditText age = view.findViewById(R.id.add_age);
                final EditText carnet = view.findViewById(R.id.add_carnet);
                final EditText career = view.findViewById(R.id.add_career);
                activity.photo= view.findViewById(R.id.add_photo);

                name.setText(personViewHolder.name.getText(),EditText.BufferType.EDITABLE);
                age.setText(personViewHolder.age.getText(),EditText.BufferType.EDITABLE);
                carnet.setText(personViewHolder.carnet.getText(),EditText.BufferType.EDITABLE);
                career.setText(personViewHolder.career.getText(),EditText.BufferType.EDITABLE);
                activity.photo.setImageURI(persons.get(i).foto);
                activity.Teclado(1,view);
                activity.Seleccionado(name,age,carnet,career);

                AlertDialog.Builder builder=new AlertDialog.Builder(context);
                builder.setMessage("Editar Estudiante");
                builder.setPositiveButton("Guardar", new DialogInterface.OnClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        activity.Teclado(2,view);
                        if (activity.miPath!=null){
                            Student s=new Student(name.getText().toString(),age.getText().toString(),carnet.getText().toString()
                                    ,career.getText().toString(),activity.miPath);
                            persons.set(itemSelecionado,s);
                            activity.miPath=null;
                        } else {
                            Student s=new Student(name.getText().toString(),age.getText().toString(),carnet.getText().toString()
                                    ,career.getText().toString(),persons.get(i).foto);
                            persons.set(itemSelecionado,s);
                        }

                        notifyItemChanged(itemSelecionado);
                    }
                });
                builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });
                        builder.setView(view);
                AlertDialog notificacion=builder.create();
                notificacion.show();

            }
        });
    }

    @Override
    public int getItemCount() {
        return persons.size();
    }

    public class PersonViewHolder extends RecyclerView.ViewHolder {
        CardView cv;
        TextView name;
        TextView age;
        TextView carnet;
        TextView career;
        ImageView photo;
        Button  edit;
        Button  delete;

        public PersonViewHolder(@NonNull View itemView) {
            super(itemView);
            cv=(CardView)itemView.findViewById(R.id.cv);
            name=(TextView)itemView.findViewById(R.id.name_item);
            age=(TextView)itemView.findViewById(R.id.age_item);
            carnet=(TextView)itemView.findViewById(R.id.carnet_item);
            career=(TextView)itemView.findViewById(R.id.career_item);
            photo=(ImageView)itemView.findViewById(R.id.photo_item);
            edit=(Button)itemView.findViewById(R.id.btnEdit_item);
            delete=(Button)itemView.findViewById(R.id.btnDelete_item);
        }
    }

}
