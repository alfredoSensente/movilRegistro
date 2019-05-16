package com.example.movilesregistro;

import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.content.Intent;
import android.provider.MediaStore;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private List<Student> persons;
    public Uri miPath;
    ImageView photo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        persons=new ArrayList<Student>();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }



    public void inputData(View v){
        final View view = LayoutInflater.from(this).inflate(R.layout.add_data,null);

        final EditText name = view.findViewById(R.id.add_name);
        final EditText age = view.findViewById(R.id.add_age);
        final EditText carnet = view.findViewById(R.id.add_carnet);
        final EditText career = view.findViewById(R.id.add_career);
        photo=view.findViewById(R.id.add_photo);
        photo.setImageResource(R.drawable.user);
        Teclado(1,view);
        Seleccionado(name,age,carnet,career);
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setMessage("Agregar Nuevo Estudiante");
        builder.setPositiveButton("Guardar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Teclado(2,view);
                RecyclerView recycler=(RecyclerView)findViewById(R.id.RVEstudiantes);
                recycler.setLayoutManager(new LinearLayoutManager(MainActivity.this,LinearLayoutManager.VERTICAL,false));
                if (miPath!=null) {
                    addData(name.getText().toString(), age.getText().toString(), carnet.getText().toString(), career.getText().toString(), miPath);
                } else {
                    addData(name.getText().toString(), age.getText().toString(), carnet.getText().toString(), career.getText().toString(),
                            Uri.parse("android.resource://"+getPackageName()+"/"+R.drawable.user));
                }
                miPath=null;
                RVAdapter adapter=new RVAdapter(MainActivity.this,persons,MainActivity.this);
                recycler.setAdapter(adapter);
            }
        });
        builder.setView(view);
        AlertDialog notificacion=builder.create();
        notificacion.show();


    }



    public void Seleccionado(EditText name,EditText age,EditText carnet,EditText career){
        name.selectAll();
        age.selectAll();
        carnet.selectAll();
        career.selectAll();
    }

    public void Teclado(int caso, View view){
        InputMethodManager imm=(InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
        switch (caso){
            case 1:
                imm.toggleSoftInput(InputMethodManager.SHOW_FORCED,0);
                break;
            case 2:
                imm.hideSoftInputFromWindow(view.getWindowToken(),0);
        }

    }



    public void addData(String name, String age, String carnet, String career, Uri photo){
        persons.add(new Student(name,age,carnet,career,photo));
    }



    public void getUriPhoto(View v){
        Teclado(2,v);
        cargarImagen(v);
    }

    private void cargarImagen(View v) {
        Intent intent=new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/");
        startActivityForResult(Intent.createChooser(intent,"Seleccione la Aplicación"),10);


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode,@Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode==RESULT_OK){
            miPath=data.getData();
            photo.setImageURI(miPath);
        }




    }
}
