package com.example.app;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.app.DBHelper.ConnexionSQL;
import com.example.app.utilidades.Utilidades;

public class RegisterActivity extends AppCompatActivity {
    String sex,campoSexo;
    ImageButton btRegresar;
    EditText campoId, campoNombre,campoTelefono,campoEdad,campoEmail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        btRegresar= (ImageButton)findViewById(R.id.btRegresarRegistro);
        btRegresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2 =new Intent(RegisterActivity.this,MainActivity.class);
                startActivity(intent2);
            }
        });
        if (Build.VERSION.SDK_INT > 16) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }




        final ImageButton hombre = findViewById(R.id.btBoy);
        final ImageButton mujer = findViewById(R.id.btGirl);
        final ImageButton continuar= findViewById(R.id.btContinue);
        final ImageButton regresar = findViewById(R.id.btRegresarRegistro);



        ;
        hombre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mujer.setBackgroundResource(R.drawable.girlgray);
                hombre.setBackgroundResource(R.drawable.boy);
                sex="Hombre";
            }
        });
        mujer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hombre.setBackgroundResource(R.drawable.boygray);
                mujer.setBackgroundResource(R.drawable.girl);
                sex="Mujer";
            }
        });
        campoId=(EditText)findViewById(R.id.campoId);
         campoNombre =(EditText)findViewById(R.id.etNombre);
        campoEmail = (EditText) findViewById(R.id.etEmail);
        campoTelefono = findViewById(R.id.etCelular);
        campoEdad = findViewById(R.id.etEdad);
        campoSexo=sex;
    }
    public void onClick(View view){
        registrar();
        //registrarUsuarioSQL();
    }

    /*private void registrarUsuarioSQL() {
        ConnexionSQL conn=new ConnexionSQL(this,"db_usuario",null,1);
        SQLiteDatabase db=conn.getWritableDatabase();
        String insert="INSERT INTO "+Utilidades.TABLA_USUARIO+" ( "+Utilidades.CAMPO_ID+"," +Utilidades.CAMPO_NOMBRE+
                ","+Utilidades.CAMPO_EMAIL+","+Utilidades.CAMPO_TELEFONO+","+Utilidades.CAMPO_EDAD+","+Utilidades.CAMPO_SEXO+" )
                "VALUES("+campoId.getText().toString()+", '"+campoNombre.getText().toString()+"', '"+campoEmail.getText().toString()+
                        "',"+campoTelefono.toString()+","
                        +campoEdad.toString()+","+campoSexo.toString()+")";
        db.execSQL(insert);
        db.close();
    }*/

    public void registrar (){
        ConnexionSQL conn=new ConnexionSQL(this,"db_usuario",null,1);
        SQLiteDatabase db=conn.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put(Utilidades.CAMPO_ID,campoId.getText().toString());
        values.put(Utilidades.CAMPO_NOMBRE,campoNombre.getText().toString());
        values.put(Utilidades.CAMPO_EMAIL,campoEmail.getText().toString());
        values.put(Utilidades.CAMPO_TELEFONO,campoTelefono.getText().toString());
        values.put(Utilidades.CAMPO_EDAD,campoEdad.getText().toString());
        values.put(Utilidades.CAMPO_SEXO,campoSexo.concat(campoSexo));
        Long idResultante = db.insert(Utilidades.TABLA_USUARIO,Utilidades.CAMPO_ID,values);
        Toast.makeText(getApplicationContext(),"idRegistro"+idResultante,Toast.LENGTH_SHORT).show();
        db.close();
    }
}
