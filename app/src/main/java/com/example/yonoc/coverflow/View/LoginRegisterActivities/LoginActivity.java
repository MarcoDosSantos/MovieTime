package com.example.yonoc.coverflow.View.LoginRegisterActivities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.yonoc.coverflow.Model.POJO.UserFacebook;
import com.example.yonoc.coverflow.R;
import com.example.yonoc.coverflow.Utils.ResultsListener;
import com.example.yonoc.coverflow.View.ActividadCompartir.CompartirActivity;
import com.example.yonoc.coverflow.View.MainActivityPortada.MainActivity;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Arrays;

public class LoginActivity extends AppCompatActivity {

    //constante de Facebook
    private static final String EMAIL = "email";

    //constante result Code
    private final static int RC_SIGN_IN = 2;//Para Google Login - Sign Up

    /***Atributos Firebase Buttons Email Password***/

    private EditText editTextUsername;
    private EditText editTextPassword;
    private Button olvideContraseñaButton;
    private TextInputLayout textInputLayoutUsername;
    private TextInputLayout textInputLayoutPassword;
    private Button botonLogin;
    private Button buttonCrearCuenta;
    private Button buttonLoginByFacebook;
    private AccessToken accessToken;
    private String actualToken;
    private final static int FB_SIGN_IN = 64206;
    private UserFacebook actualUserFacebook;


    /***Atributos para Firebase***/
    private FirebaseAuth mAuth;             //Para Google Login - Sign Up
    private FirebaseAuth.AuthStateListener mAuthListener;  //Para Google Login - Sign Up


    /***Atributos para Google***/
    private SignInButton botonGoogle;       //Para Google Login - Sign Up
    private GoogleApiClient mGoogleApiClient;        //Para Google Login - Sign Up

    /*** Atributos para Facebook ***/
    private CallbackManager callbackManager;


    @Override
    protected void onStart() {
        super.onStart();

        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //este es el capo de Firebase..
        mAuth = FirebaseAuth.getInstance();

        /***Para Firebase dentro del onCreate***/
        editTextUsername = findViewById(R.id.editTextUsername);
        editTextPassword = findViewById(R.id.editTextPassword);
        olvideContraseñaButton = findViewById(R.id.olvideContraseñaButton);
        botonLogin = findViewById(R.id.botonLogin);
        buttonCrearCuenta = findViewById(R.id.buttonCrearCuenta);

        //voy a traer el bundle si viene desde CompartirEntrada
        final Bundle bundle = getIntent().getExtras();

        //voy a crear un Intent para llegar a Pantalla CompartirEntrada
        final Intent intentDeBotonLoginDesdeCompartir = new Intent(this, CompartirActivity.class);



        //voy a crear un Intent para llegar a Pantalla register
        final Intent intentDeBotonSingUp = new Intent(this, RegisterActivity.class);

        accessToken = AccessToken.getCurrentAccessToken();
        Boolean isLoggedIn = accessToken != null && !accessToken.isExpired();

        /***para google***/
        //Login con Google //Para Google Login - Sign Up
        botonGoogle = findViewById(R.id.botonLoginGoogle);



        botonGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signIn();
            }
        });

        // Para Loguearme con Email y Contraseña de Firebase
        //todo: este activa el metodo LoguearUsuarioFirebaseEmailPassword
        botonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoguearUsuarioFirebaseEmailPassword();   // se verifica que el usuario este cargado en firebase

            }
        });



        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if(firebaseAuth.getCurrentUser() !=null){
                    //UNA VEZ QUE INICIA SESION PASA A LA OTRA ACTIVIDAD
                    if(bundle.getBoolean("irACompartir")){
                        Bundle bundleACompartir = new Bundle();
                        bundleACompartir.putString("cineSeleccionado", bundle.getString("cineSeleccionado"));
                        bundleACompartir.putString("tituloPelicula", bundle.getString("tituloPelicula"));

                        Intent intent = new Intent(LoginActivity.this, CompartirActivity.class);
                        intent.putExtras(bundleACompartir);

                        startActivity(intent);
                    } else {
                        startActivity(new Intent(LoginActivity.this, MainActivity.class));
                    }
                }
            }
        };

       // String serverClientId = getString(R.string.server_client_id);
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestIdToken(getString(R.string.default_web_client_id)).requestEmail().build();


        //escuchador de cuenta de Google por si algo sale mal .. //Para Google Login - Sign Up
        mGoogleApiClient = new GoogleApiClient.Builder(this).enableAutoManage(this , new GoogleApiClient.OnConnectionFailedListener() {
            @Override
            public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
                Toast.makeText(LoginActivity.this, "Parece que algo no salio bien", Toast.LENGTH_SHORT).show();

            }
        }).addApi(Auth.GOOGLE_SIGN_IN_API, gso).build();


        // LOGIN FACEBOOK

        callbackManager = CallbackManager.Factory.create();

        LoginButton loginButton = (LoginButton) findViewById(R.id.login_button_Facebook);
        loginButton.setReadPermissions(Arrays.asList(EMAIL));

        // Callback registration
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
            }

            @Override
            public void onCancel() {
                // App code
            }

            @Override
            public void onError(FacebookException exception) {
                // App code
            }
        });

        // FIN LOGIN FACEBOOK

        //Busco la variable en el Layout
        editTextUsername = findViewById(R.id.editTextUsername);
        editTextPassword = findViewById(R.id.editTextPassword);
        textInputLayoutUsername = findViewById(R.id.textImputLayoutUsername);
        textInputLayoutPassword = findViewById(R.id.textImputLayoutPassword);
        botonLogin = findViewById(R.id.botonLogin);
        buttonCrearCuenta = findViewById(R.id.buttonCrearCuenta);
        buttonLoginByFacebook = findViewById(R.id.login_button_Facebook);


       /* //OnClickListener para el boton LOGIN
        botonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(intentDeBotonLogin);
            }
        });*/

        //OnClickListener para el boton Sing Up
        buttonCrearCuenta.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                startActivity(intentDeBotonSingUp);
            }
        });


        //todo: este me lleva a la actividad para registrarme
        buttonCrearCuenta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });

        //todo: este me lleva a la actividad para recuperar contraseña
        olvideContraseñaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, ResetPasswordActivity.class));
            }
        });

        //(TAVO) Tengo que ver para que sirve esta linea todavía no logro la utilidad real
        //LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("public_profile"));
    }

    /***Para Google Login - Sign Up***/

    /*//Configuracion para google Log In
    GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build();
*/
    //Para login con Google
    private void signIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    public String getActualToken()
    {
        actualToken = accessToken.getToken();
        return actualToken;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data); //SOLO esta linea corresponde a FACEBOOK
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if (result.isSuccess()) {
                // Google Sign In fue exitoso y se pudo autenticar con Firebase
                GoogleSignInAccount account = result.getSignInAccount();
                firebaseAuthWithGoogle(account);

            } else {
                Toast.makeText(this, "La Autenticacion salio mal", Toast.LENGTH_SHORT).show();
            }
        }
        else if (requestCode == FB_SIGN_IN) {
            getFullNameAndImageFacebook();
        }
        else{
            Toast.makeText(this, "La autenticación fue incorrecta", Toast.LENGTH_SHORT).show();
        }
    }

    public void getFullNameAndImageFacebook (){
        actualToken = AccessToken.getCurrentAccessToken().getToken();
        MainActivity getDataFacebookUser = new MainActivity();
        getDataFacebookUser.cargarFacebookUserData(actualToken, new ResultsListener<UserFacebook>() {
            @Override
            public void finish(UserFacebook resultado) {
                actualUserFacebook = resultado;
                firebaseAuthWithFacebook(actualUserFacebook);
            }
        });
    }

    /* // DEVOLUCION DE FACEBOOK
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);  isLoggedIn
    }*/

    private void firebaseAuthWithGoogle(GoogleSignInAccount account) {
        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
        mAuth.signInWithCredential(credential).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d("TAG", "signInWithCredential:success");
                    FirebaseUser user = mAuth.getCurrentUser();
                    String user_id = mAuth.getCurrentUser().getUid();
                    String userName = user.getDisplayName();
                    String userImageUrl = user.getPhotoUrl().toString();

                    DatabaseReference curent_user_db = FirebaseDatabase.getInstance().getReference().child("usuariosActivos").child(user_id);
                    DatabaseReference curent_user_db_name = FirebaseDatabase.getInstance().getReference().child("usuariosActivos").child(user_id).child("nombre");
                    DatabaseReference curent_user_db_comparte = FirebaseDatabase.getInstance().getReference().child("usuariosActivos").child(user_id).child("comparte");
                    DatabaseReference curent_user_db_imagen = FirebaseDatabase.getInstance().getReference().child("usuariosActivos").child(user_id).child("imagen");

                    curent_user_db.setValue(true);
                    curent_user_db_name.setValue(userName);
                    curent_user_db_comparte.setValue(false);
                    curent_user_db_imagen.setValue(userImageUrl);

                    //updateUI(user);
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w("TAG", "signInWithCredential:failure", task.getException());
                    //Snackbar.make(findViewById(R.id.), "Authentication Failed.", Snackbar.LENGTH_SHORT).show();
                    //updateUI(null);
                }

                // ...
            }
        });
    }

    private void firebaseAuthWithFacebook(UserFacebook actualUserFacebook) {
        final String nameFacebookUser = actualUserFacebook.getName();
        final String imageFacebookUser = actualUserFacebook.getPicture().getData().getUrl();

        AuthCredential credential = FacebookAuthProvider.getCredential(actualToken);
        mAuth.signInWithCredential(credential).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {

            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    FirebaseUser user = mAuth.getCurrentUser();
                    String user_id = mAuth.getCurrentUser().getUid();
                    String userName = nameFacebookUser;
                    String userImageUrl = imageFacebookUser;

                    DatabaseReference curent_user_db = FirebaseDatabase.getInstance().getReference().child("usuariosActivos").child(user_id);
                    DatabaseReference curent_user_db_name = FirebaseDatabase.getInstance().getReference().child("usuariosActivos").child(user_id).child("nombre");
                    DatabaseReference curent_user_db_comparte = FirebaseDatabase.getInstance().getReference().child("usuariosActivos").child(user_id).child("comparte");
                    DatabaseReference curent_user_db_imagen = FirebaseDatabase.getInstance().getReference().child("usuariosActivos").child(user_id).child("imagen");

                    curent_user_db.setValue(true);
                    curent_user_db_name.setValue(userName);
                    curent_user_db_comparte.setValue(false);
                    curent_user_db_imagen.setValue(userImageUrl);
                }
            }
        });
    }


    /***Para FireBasa comun fuera del onCreate ***/
    public void LoguearUsuarioFirebaseEmailPassword() {
        String email = editTextUsername.getText().toString();
        String password = editTextPassword.getText().toString();

        if((email.isEmpty() || password.isEmpty()) || (email.isEmpty() && password.isEmpty())){
            Toast.makeText(this, "Ingrese cuenta de usuario y contraseña", Toast.LENGTH_SHORT).show();
            return;
        }

        //voy a crear un Intent para llegar a Pantalla principal
        final Intent intentDeBotonLogin = new Intent(this, MainActivity.class);

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = mAuth.getCurrentUser();
                            String user_id = mAuth.getCurrentUser().getUid();
                            String userName = user.getDisplayName();
                            String userImageUrl = user.getPhotoUrl().toString();

                            DatabaseReference curent_user_db = FirebaseDatabase.getInstance().getReference().child("usuariosActivos").child(user_id);
                            DatabaseReference curent_user_db_name = FirebaseDatabase.getInstance().getReference().child("usuariosActivos").child(user_id).child("nombre");
                            DatabaseReference curent_user_db_comparte = FirebaseDatabase.getInstance().getReference().child("usuariosActivos").child(user_id).child("comparte");
                            DatabaseReference curent_user_db_imagen = FirebaseDatabase.getInstance().getReference().child("usuariosActivos").child(user_id).child("imagen");

                            curent_user_db.setValue(true);
                            curent_user_db_name.setValue(userName);
                            curent_user_db_comparte.setValue(false);
                            curent_user_db_imagen.setValue(userImageUrl);
                            startActivity(intentDeBotonLogin);

                            // Sign in success, update UI with the signed-in user's information
                            Log.d("TAG", "createUserWithEmail:success");

                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("TAG", "createUserWithEmail:failure", task.getException());
                            Toast.makeText(LoginActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                        // ...
                    }
                });
    }

}
