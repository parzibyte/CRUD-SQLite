package me.parzibyte.crudsqlite;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import me.parzibyte.crudsqlite.controllers.MascotasController;
import me.parzibyte.crudsqlite.modelos.Mascota;

public class AgregarMascotaActivity extends AppCompatActivity {
    private Button btnAgregarMascota, btnCancelarNuevaMascota;
    private EditText etNombre, etEdad;
    private MascotasController mascotasController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_mascota);

        // Instanciar vistas
        etNombre = findViewById(R.id.etNombre);
        etEdad = findViewById(R.id.etEdad);
        btnAgregarMascota = findViewById(R.id.btnAgregarMascota);
        btnCancelarNuevaMascota = findViewById(R.id.btnCancelarNuevaMascota);
        // Crear el controlador
        mascotasController = new MascotasController(AgregarMascotaActivity.this);

        // Agregar listener del botón de guardar
        btnAgregarMascota.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Resetear errores a ambos
                etNombre.setError(null);
                etEdad.setError(null);
                String nombre = etNombre.getText().toString(),
                        edadComoCadena = etEdad.getText().toString();
                if ("".equals(nombre)) {
                    etNombre.setError("Escribe el nombre de la mascota");
                    etNombre.requestFocus();
                    return;
                }
                if ("".equals(edadComoCadena)) {
                    etEdad.setError("Escribe la edad de la mascota");
                    etEdad.requestFocus();
                    return;
                }

                // Ver si es un entero
                int edad;
                try {
                    edad = Integer.parseInt(etEdad.getText().toString());
                } catch (NumberFormatException e) {
                    etEdad.setError("Escribe un número");
                    etEdad.requestFocus();
                    return;
                }
                // Ya pasó la validación
                Mascota nuevaMascota = new Mascota(nombre, edad);
                long id = mascotasController.nuevaMascota(nuevaMascota);
                if (id == -1) {
                    // De alguna manera ocurrió un error
                    Toast.makeText(AgregarMascotaActivity.this, "Error al guardar. Intenta de nuevo", Toast.LENGTH_SHORT).show();
                } else {
                    // Terminar
                    finish();
                }
            }
        });

        // El de cancelar simplemente cierra la actividad
        btnCancelarNuevaMascota.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
