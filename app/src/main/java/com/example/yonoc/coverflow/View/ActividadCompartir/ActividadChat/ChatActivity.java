package com.example.yonoc.coverflow.View.ActividadCompartir.ActividadChat;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yonoc.coverflow.R;
import com.google.firebase.auth.FirebaseAuth;
import com.squareup.picasso.Picasso;

import jp.wasabeef.picasso.transformations.CropCircleTransformation;

public class ChatActivity extends AppCompatActivity {

    private String nombreUsuario, urlFotoUser;
    private ImageView backArrow, userFoto;
    private TextView userName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        Bundle bundle = getIntent().getExtras();
        nombreUsuario = bundle.getString("nombreUsuario");
        urlFotoUser = bundle.getString("url");

        backArrow = findViewById(R.id.arrowBackChat);
        userFoto = findViewById(R.id.userFotoChat);
        userName = findViewById(R.id.userNameChat);

        userName.setText(nombreUsuario);

        Picasso.with(this).load(urlFotoUser).transform(new CropCircleTransformation()).into(userFoto);

        ChatFragment chatFragment = new ChatFragment();
        chatFragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction().replace(R.id.contenedorDeFragmentChat, chatFragment).commit();

        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


    }
}
