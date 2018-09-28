package com.example.user.proyecto_final.fragment;


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.user.proyecto_final.R;
import com.example.user.proyecto_final.service.DenunciaService;
import com.theartofdev.edmodo.cropper.CropImage;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.app.Activity.RESULT_OK;


/**
 * A simple {@link Fragment} subclass.
 */
public class ContactoFragment extends Fragment {

    ImageView imagen;
    Spinner tipo;
    EditText comentario;

    public ContactoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_contacto, container, false);
        Button capturarButton = v.findViewById(R.id.capturar);
        capturarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CropImage.activity()
                        .setCropMenuCropButtonTitle("Ok")
                        .start(getActivity());
            }
        });

        Button enviarButton = v.findViewById(R.id.enviar);
        enviarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendDenuncia();
            }
        });

        imagen = v.findViewById(R.id.imageView);
        tipo = v.findViewById(R.id.tipo);
        comentario = v.findViewById(R.id.comentario);
        return v;
    }

    private void sendDenuncia() {
        //Inicializar retrofit para consumir el servicio web
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://aniux.dx.am/index.php/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        final DenunciaService service = retrofit.create(DenunciaService.class);

        Map<String, String> data = new HashMap<>();
        data.put("tipo", tipo.getSelectedItem().toString());
        data.put("descripcion", comentario.getText().toString());
        data.put("image", toBase64String(imagen));

        service.saveNoticia(data).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                Toast.makeText(getContext(), "Su denuncia fue enviada :)", Toast.LENGTH_SHORT).show();
                cleanForm();
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.e(ContactoFragment.class.getName(),"Error enviando denuncia :(",t);
                Toast.makeText(getContext(), "No pude mandar tu denuncia :(", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private String toBase64String(ImageView imagen) {
        if (imagen.getDrawable() instanceof ColorDrawable) return "";
        Bitmap bitmap = ((BitmapDrawable) imagen.getDrawable()).getBitmap();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageInByte = baos.toByteArray();
        return Base64.encodeToString(imageInByte, Base64.DEFAULT);
    }

    private void cleanForm() {
        comentario.setText("");
        imagen.setImageBitmap(null);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                Uri resultUri = result.getUri();
                try {
                    imagen.setImageBitmap(MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), resultUri));
                } catch (IOException e) {
                    Toast.makeText(getContext(), "Error cargando la imagen :(", Toast.LENGTH_SHORT).show();
                }
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Toast.makeText(getContext(), "Error capturando la imagen :(", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
