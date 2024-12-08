package com.example.manttoprev.Presentador;

import android.net.Uri;
import android.os.Environment;
import androidx.annotation.NonNull;
import com.example.manttoprev.Modelo.Aislamiento;
import com.example.manttoprev.Modelo.Alertas;
import com.example.manttoprev.Vista.AislamientoAdmin;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.itextpdf.io.font.PdfEncodings;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.TimeZone;


public class AislamientoPresenter implements AislamientoContract.Presenter{

    private static final String AISLAMIENTO = "aislamiento";
    private static final String DESCRIPCION = "descripcion";
    private static final String AREA = "Área";
    private static final String SECCION = "Sección";
    private static final String EQUIPO = "Equipo";
    private static final String MAQUINA = "Maquina";
    private static final String MOTOR = "Motor";

    private final AislamientoAdmin view;
    private final FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    public AislamientoPresenter(AislamientoAdmin view) {
        this.view = view;
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    public void obtenerAreas() {
        List<String> nombresAreas = new ArrayList<>();
        nombresAreas.add(AREA);

        mDatabase = FirebaseDatabase.getInstance().getReference().child("areas");
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                nombresAreas.clear(); // Limpiar la lista antes de agregar las nuevas áreas
                nombresAreas.add(AREA); // Agregar la opción "Seleccionar" nuevamente
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String nombreArea = snapshot.child(DESCRIPCION).getValue(String.class);
                    if (nombreArea != null) {
                        nombresAreas.add(nombreArea);
                    }
                }
                view.mostrarAreas(nombresAreas);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Manejar el error si es necesario
            }
        });
    }


    @Override
    public void obtenerSecciones(final String areaSeleccionada) {
        final List<String> nombresSecciones = new ArrayList<>();
        nombresSecciones.add(SECCION);

        mDatabase = FirebaseDatabase.getInstance().getReference().child("secciones");
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                nombresSecciones.clear(); // Limpiar la lista antes de agregar los nuevos equipos
                nombresSecciones.add(SECCION); // Agregar la opción "Seleccionar" nuevamente

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String nombreSeccion = snapshot.child(DESCRIPCION).getValue(String.class);
                    String areaSeccion = snapshot.child("area").getValue(String.class);

                    if (nombreSeccion != null && areaSeccion != null && areaSeccion.equals(areaSeleccionada)) {
                        nombresSecciones.add(nombreSeccion);
                    }
                }
                view.mostrarSecciones(nombresSecciones);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Manejar el error si es necesario
            }
        });
    }

    @Override
    public void obtenerEquipos( String equipoSeleccionado) {
        final List<String> nombresEquipos = new ArrayList<>();
        nombresEquipos.add(EQUIPO);

        mDatabase = FirebaseDatabase.getInstance().getReference().child("equipos");
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                nombresEquipos.clear(); // Limpiar la lista antes de agregar los nuevos equipos
                nombresEquipos.add(EQUIPO); // Agregar la opción "Seleccionar" nuevamente

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String nombreEquipo = snapshot.child(DESCRIPCION).getValue(String.class);
                    String seccionEquipo = snapshot.child("seccion").getValue(String.class);

                    if (nombreEquipo != null && seccionEquipo != null && seccionEquipo.equals(equipoSeleccionado)) {
                        nombresEquipos.add(nombreEquipo);
                    }
                }
                view.mostrarEquipos(nombresEquipos);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Manejar el error si es necesario
            }
        });
    }
    @Override
    public void obtenerMaquinas(String maquinaSeleccionada) {
        final List<String> nombresMaquinas = new ArrayList<>();
        nombresMaquinas.add(EQUIPO);

        mDatabase = FirebaseDatabase.getInstance().getReference().child("maquinas");
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                nombresMaquinas.clear(); // Limpiar la lista antes de agregar los nuevos equipos
                nombresMaquinas.add(MAQUINA); // Agregar la opción "Seleccionar" nuevamente

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String nombreMaquina = snapshot.child(DESCRIPCION).getValue(String.class);
                    String equipoMaquina = snapshot.child("equipo").getValue(String.class);

                    if (nombreMaquina != null && equipoMaquina != null && equipoMaquina.equals(maquinaSeleccionada)) {
                        nombresMaquinas.add(nombreMaquina);
                    }
                }
                view.mostrarMaquinas(nombresMaquinas);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Manejar el error si es necesario
            }
        });
    }

    @Override
    public void obtenerMotores(String motorSeleccionado) {
        final List<String> nombresMotores = new ArrayList<>();
        nombresMotores.add(MOTOR);

        mDatabase = FirebaseDatabase.getInstance().getReference().child("motores");
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                nombresMotores.clear(); // Limpiar la lista antes de agregar los nuevos equipos
                nombresMotores.add(MOTOR); // Agregar la opción "Seleccionar" nuevamente

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String nombreMotor = snapshot.child(DESCRIPCION).getValue(String.class);
                    String equipoMotor = snapshot.child("maquina").getValue(String.class);

                    if (nombreMotor != null && equipoMotor != null && equipoMotor.equals(motorSeleccionado)) {
                        nombresMotores.add(nombreMotor);
                    }
                }
                view.mostrarMotores(nombresMotores);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Manejar el error si es necesario
            }
        });
    }

    @Override
    public void guardarAislamiento(String area, String seccion, String equipo, String maquina, String motor,
                                   Double megadou, Double megadov, Double megadow,
                                   Double resistenciau, Double resistenciav, Double resistenciaw,
                                   Double amperajeu, Double amperajev, Double amperajew) {
        // Validar los datos (puedes agregar más validaciones según tus necesidades)
        if (area.isEmpty() || seccion.isEmpty() || equipo.isEmpty() || maquina.isEmpty() || motor.isEmpty() || megadou==null
        || megadov==null || megadow==null || resistenciau==null || resistenciav==null || resistenciaw==null ||
        amperajeu==null || amperajev==null || amperajew==null) {
            view.showErrorMessage("Todos los campos son obligatorios");
            return;
        }
        // Verificar si algún valor de megado es menor a 5
        // Configurar la fecha y hora en la zona horaria peruana
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.US);
        dateFormat.setTimeZone(TimeZone.getTimeZone("America/Lima"));

        if (megadou < 5 || megadov < 5 || megadow < 5) {
            // Generar una alerta
            String fechaPeru = dateFormat.format(new Date());
            String mensajeAlerta = "Bajo Aislamiento!";

            // Enviar la alerta a la vista para que esta se encargue de mostrarla
            guardarAlerta(fechaPeru, mensajeAlerta, motor, maquina);
        }

            mDatabase = FirebaseDatabase.getInstance().getReference().child(AISLAMIENTO);
            // Crear un objeto de la máquina
            Aislamiento aislamiento = new Aislamiento(area, seccion, equipo, maquina, motor,
                    megadou, megadov, megadow,
                    resistenciau, resistenciav, resistenciaw,
                    amperajeu, amperajev, amperajew, new Date());

            // Agregar la máquina directamente a la base de datos con una clave única
            mDatabase.push().setValue(aislamiento);

            try {
                generarPDF(area, seccion, equipo, maquina, motor,
                        megadou, megadov, megadow,
                        resistenciau, resistenciav, resistenciaw,
                        amperajeu, amperajev, amperajew);
                view.showSuccessMessage("Datos guardados y PDF generado exitosamente.");
            } catch (IOException e) {
                view.showErrorMessage("Error al generar el PDF: " + e.getMessage());
            }
        }


    private void guardarAlerta(String fechaPeru, String mensajeAlerta, String motor, String maquina) {
        mDatabase = FirebaseDatabase.getInstance().getReference().child("alertas");

        // Crear el objeto de la alertas
        Alertas alertas = new Alertas(fechaPeru, mensajeAlerta, motor, maquina);
        // Guardar la alertas en Firebase
        mDatabase.push().setValue(alertas)
                .addOnSuccessListener(aVoid -> view.showSuccessMessage("Alertas registrada exitosamente."))
                .addOnFailureListener(e -> view.showErrorMessage("Error al registrar la alertas: " + e.getMessage()));
    }

    private void generarPDF(String area, String seccion, String equipo, String maquina, String motor,
                            Double megadou, Double megadov, Double megadow,
                            Double resistenciau, Double resistenciav, Double resistenciaw,
                            Double amperajeu, Double amperajev, Double amperajew) throws IOException {

        mDatabase = FirebaseDatabase.getInstance().getReference().child("Usuarios");
        String uid = Objects.requireNonNull(mAuth.getCurrentUser()).getUid();

        String pdfFilePath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/Aislamientos";
        PdfDocument pdfDocument = new PdfDocument(new PdfWriter(pdfFilePath));
        Document document = new Document(pdfDocument);

        // Ruta de la fuente
        String fontPath = "assets/fonts/arial.ttf"; // Cambia según tu fuente
        PdfFont font = PdfFontFactory.createFont(fontPath, PdfEncodings.IDENTITY_H, true);
        document.setFont(font);

        // Configurar la fecha y hora en la zona horaria peruana
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.US);
        dateFormat.setTimeZone(TimeZone.getTimeZone("America/Lima"));
        String fechaPeru = dateFormat.format(new Date());



        // Agrega contenido al PDF
        document.add(new Paragraph("Reporte de Aislamiento del Motor").setBold().setFontSize(20));
        document.add(new Paragraph("Fecha: " + fechaPeru).setFontSize(12));
        document.add(new Paragraph("\nInformación del Motor:").setBold());
        document.add(new Paragraph("Área: " + area));
        document.add(new Paragraph("Sección: " + seccion));
        document.add(new Paragraph("Equipo: " + equipo));
        document.add(new Paragraph("Máquina: " + maquina));
        document.add(new Paragraph("Motor: " + motor));

        document.add(new Paragraph("\nMegado :").setBold());
        document.add(new Paragraph("Megado U: " + megadou + " MΩ"));
        document.add(new Paragraph("Megado V: " + megadov + " MΩ"));
        document.add(new Paragraph("Megado W: " + megadow + " MΩ"));

        document.add(new Paragraph("\nResistencia:").setBold());
        document.add(new Paragraph("Resistencia U: " + resistenciau + " Ω"));
        document.add(new Paragraph("Resistencia V: " + resistenciav + " Ω"));
        document.add(new Paragraph("Resistencia W: " + resistenciaw + " Ω"));

        document.add(new Paragraph("\nAmperaje:").setBold());
        document.add(new Paragraph("Amperaje U: " + amperajeu + " A"));
        document.add(new Paragraph("Amperaje V: " + amperajev + " A"));
        document.add(new Paragraph("Amperaje W: " + amperajew + " A"));


        // Obtén el nombre del usuario
        mDatabase.child(uid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String nombreUsuario = dataSnapshot.child("nombre").getValue(String.class);
                document.add(new Paragraph("\n\nTécnico: " + nombreUsuario).setBold());

                document.close(); // Cierra el documento después de agregar el usuario
                subirPDFaFirebase(new File(pdfFilePath), equipo, maquina, motor, fechaPeru); // Pasa los datos al subir
                abrirPDF(pdfFilePath);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                view.showErrorMessage("Error al obtener los datos del usuario: " + databaseError.getMessage());
            }
        });
    }

    private void subirPDFaFirebase(File pdfFile, String equipo, String maquina, String motor, String fecha) {
        // Crear un nombre descriptivo para el PDF
        String pdfFileName = equipo.replaceAll("\\s+", "_") + "_" +
                maquina.replaceAll("\\s+", "_") + "_" +
                motor.replaceAll("\\s+", "_") + "_" +
                fecha.replaceAll("[^a-zA-Z0-9_]", "") + ".pdf";

        StorageReference storageRef = FirebaseStorage.getInstance().getReference().child("pdfs/" + pdfFileName);

        storageRef.putFile(Uri.fromFile(pdfFile))
                .addOnSuccessListener(taskSnapshot -> view.showSuccessMessage("PDF subido correctamente a Firebase"))
                .addOnFailureListener(e -> view.showErrorMessage("Error al subir el PDF: " + e.getMessage()));
    }

    public void abrirPDF(String rutaPDF) {
        File pdfFile = new File(rutaPDF);

        if (pdfFile.exists()) {
            view.mostrarPDF(rutaPDF); // Llama a la vista para abrir el PDF
        } else {
            view.showErrorMessage("El archivo PDF no existe en la ruta especificada.");
        }
    }
}
