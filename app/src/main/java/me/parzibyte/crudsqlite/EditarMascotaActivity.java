package me.parzibyte.crudsqlite;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import me.parzibyte.crudsqlite.controllers.MascotasController;
import me.parzibyte.crudsqlite.modelos.Mascota;

public class EditarMascotaActivity extends AppCompatActivity {
    private EditText etEditarNombre, etEditarEdad;
    private Button btnGuardarCambios, btnCancelarEdicion;
    private Mascota mascota;//La mascota que vamos a estar editando
    private MascotasController mascotasController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_mascota);

        // Recuperar datos que enviaron
        Bundle extras = getIntent().getExtras();
        // Si no hay datos (cosa rara) salimos
        if (extras == null) {
            finish();
            return;
        }
        // Instanciar el controlador de las mascotas
        mascotasController = new MascotasController(EditarMascotaActivity.this);

        // Rearmar la mascota
        // Nota: igualmente solamente podríamos mandar el id y recuperar la mascota de la BD
        long idMascota = extras.getLong("idMascota");
        String nombreMascota = extras.getString("nombreMascota");
        int edadMascota = extras.getInt("edadMascota");
        mascota = new Mascota(nombreMascota, edadMascota, idMascota);


        // Ahora declaramos las vistas
        etEditarEdad = findViewById(R.id.etEditarEdad);
        etEditarNombre = findViewById(R.id.etEditarNombre);
        btnCancelarEdicion = findViewById(R.id.btnCancelarEdicionMascota);
        btnGuardarCambios = findViewById(R.id.btnGuardarCambiosMascota);


        // Rellenar los EditText con los datos de la mascota
        etEditarEdad.setText(String.valueOf(mascota.getEdad()));
        etEditarNombre.setText(mascota.getNombre());

        // Listener del click del botón para salir, simplemente cierra la actividad
        btnCancelarEdicion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        // Listener del click del botón que guarda cambios
        btnGuardarCambios.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Remover previos errores si existen
                etEditarNombre.setError(null);
                etEditarEdad.setError(null);
                // Crear la mascota con los nuevos cambios pero ponerle
                // el id de la anterior
                String nuevoNombre = etEditarNombre.getText().toString();
                String posibleNuevaEdad = etEditarEdad.getText().toString();
                if (nuevoNombre.isEmpty()) {
                    etEditarNombre.setError("Escribe el nombre");
                    etEditarNombre.requestFocus();
                    return;
                }
                if (posibleNuevaEdad.isEmpty()) {
                    etEditarEdad.setError("Escribe la edad");
                    etEditarEdad.requestFocus();
                    return;
                }
                // Si no es entero, igualmente marcar error
                int nuevaEdad;
                try {
                    nuevaEdad = Integer.parseInt(posibleNuevaEdad);
                } catch (NumberFormatException e) {
                    etEditarEdad.setError("Escribe un número");
                    etEditarEdad.requestFocus();
                    return;
                }
                // Si llegamos hasta aquí es porque los datos ya están validados
                Mascota mascotaConNuevosCambios = new Mascota(nuevoNombre, nuevaEdad, mascota.getId());
                int filasModificadas = mascotasController.guardarCambios(mascotaConNuevosCambios);
                if (filasModificadas != 1) {
                    // De alguna forma ocurrió un error porque se debió modificar únicamente una fila
                    Toast.makeText(EditarMascotaActivity.this, "Error guardando cambios. Intente de nuevo.", Toast.LENGTH_SHORT).show();
                } else {
                    // Si las cosas van bien, volvemos a la principal
                    // cerrando esta actividad
                    finish();
                }
            }
        });
    }
}
