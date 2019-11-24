package com.example.app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.media.Image;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.example.app.DBEntity.DBUser;
import com.example.app.DBHelper.ConnexionSQL;
import com.example.app.utilidades.Utilidades;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ListView listViewPersonas;
    ArrayList<String> listainformacion;
    ArrayList<DBUser> listaUsuarios;
    String campoSexo;
    EditText campoId, campoNombre,campoTelefono,campoEdad,campoEmail;
    ConnexionSQL conn;
ImageButton btRegistro;
Button btActualizar, btEliminar;

   @Override
   protected void onCreate(Bundle savedInstanceState) {
       super.onCreate(savedInstanceState);
       setContentView(R.layout.activity_main);
       btRegistro = findViewById(R.id.btRegistro);

        conn=new ConnexionSQL(this,"bd_user",null,1);
        listViewPersonas=(ListView)findViewById(R.id.listViewPersonas);
        consultarLista();
       ArrayAdapter adaptador = new ArrayAdapter(this,android.R.layout.simple_expandable_list_item_1,listainformacion);
       listViewPersonas.setAdapter(adaptador);
       listViewPersonas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
           @Override
           public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
               String informacion="id:"+listaUsuarios.get(i)+"\n";
               informacion+="Nombre: "+listaUsuarios.get(i).getNombre()+"\n";
               informacion+="Email: "+listaUsuarios.get(i).getEmail()+"\n";
               informacion+="Telefono: "+listaUsuarios.get(i).getTelefono()+"\n";
               informacion+="Edad: "+listaUsuarios.get(i).getEdad()+"\n";
               informacion+="Sexo: "+listaUsuarios.get(i).getSexo()+"\n";
               Toast.makeText(getApplicationContext(),informacion,Toast.LENGTH_LONG).show();

           }
       });


       btRegistro.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               Intent intent =new Intent(MainActivity.this,RegisterActivity.class);
               startActivity(intent);
           }
       });
       if (Build.VERSION.SDK_INT > 16) {
           getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                   WindowManager.LayoutParams.FLAG_FULLSCREEN);
       }


   }

    private void consultarLista() {
       SQLiteDatabase db=conn.getReadableDatabase();
       DBUser usuario=null;
       Cursor cursor=db.rawQuery("SELECT * FROM "+Utilidades.TABLA_USUARIO,null);
       while (cursor.moveToNext()){
           usuario=new DBUser();
           usuario.setId(cursor.getInt(0));
           usuario.setNombre(cursor.getString(1));
           usuario.setEmail(cursor.getString(1));
           usuario.setTelefono(cursor.getString(1));
           usuario.setEdad(cursor.getString(1));
           usuario.setSexo(cursor.getString(1));
       }
       obtenerLista();
    }

    private void obtenerLista() {
       listainformacion=new ArrayList<String>();
       for (int i=0; i<listaUsuarios.size();i++){
           listainformacion.add(listaUsuarios.get(i).getId()+"-"+listaUsuarios.get(i).getNombre());
       }
    }

    public void onClick(View view){
       switch (view.getId()){
           case R.id.btConsultar: consultarUsuario();
               break;
           case R.id.btActulizar: actualizarUsuario();
               break;
           case R.id.btEliminar: eliminarUsuario();
               break;
       }
   }

    private void eliminarUsuario() {
        SQLiteDatabase db=conn.getReadableDatabase();
        String[] parametros={campoId.getText().toString()};
        db.delete(Utilidades.TABLA_USUARIO,Utilidades.CAMPO_ID+"=?",parametros);
        Toast.makeText(getApplicationContext(),"El Usuario se elimino",Toast.LENGTH_LONG).show();
        db.close();
    }

    private void consultarUsuario() {
       SQLiteDatabase db=conn.getReadableDatabase();
       String[] parametros={campoId.getText().toString()};
       String[] campos ={Utilidades.CAMPO_NOMBRE,Utilidades.CAMPO_EMAIL,Utilidades.CAMPO_TELEFONO,Utilidades.CAMPO_EDAD,Utilidades.CAMPO_SEXO};
        try {
            Cursor cursor =db.query(Utilidades.TABLA_USUARIO,campos,Utilidades.CAMPO_ID+"=?",parametros,null,null,null);
            cursor.moveToFirst();
            campoNombre.setText(cursor.getString(0));
            campoEmail.setText(cursor.getString(1));
            campoTelefono.setText(cursor.getString(2));
            campoEdad.setText(cursor.getString(3));
            campoSexo.concat(cursor.getString(4));
            cursor.close();
        }catch (Exception e){
            Toast.makeText(getApplicationContext(),"El Usuario no existe",Toast.LENGTH_LONG).show();
            limpiar();
        }
    }
    private void limpiar() {
       campoNombre.setText("");
        campoEmail.setText("");
        campoTelefono.setText("");
        campoEdad.setText("");
        campoSexo.concat("");
    }
    private void actualizarUsuario() {
        SQLiteDatabase db=conn.getReadableDatabase();
        String[] parametros={campoId.getText().toString()};
        ContentValues values = new ContentValues();
        values.put(Utilidades.CAMPO_NOMBRE,campoNombre.getText().toString());
        values.put(Utilidades.CAMPO_EMAIL,campoEmail.getText().toString());
        values.put(Utilidades.CAMPO_TELEFONO,campoTelefono.getText().toString());
        values.put(Utilidades.CAMPO_EDAD,campoEdad.getText().toString());
        values.put(Utilidades.CAMPO_SEXO,campoSexo.concat(campoSexo));
        db.update(Utilidades.TABLA_USUARIO,values,Utilidades.CAMPO_ID+"=?",parametros);
        Toast.makeText(getApplicationContext(),"El Usuario se actualizo",Toast.LENGTH_LONG).show();
        db.close();


    }
}
