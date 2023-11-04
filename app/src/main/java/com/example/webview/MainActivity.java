package com.example.webview;

import android.view.View;
import android.webkit.JavascriptInterface;
import android.widget.EditText;
import android.widget.Toast;
import android.webkit.WebSettings;
import android.webkit.WebView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {

    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //obtenemos la referencia del ID definido en el XML
        webView = findViewById(R.id.webView);
        //obtiene las configuraciones del WebView para modificar sus propiedades
        WebSettings webSettings = webView.getSettings();
        // habilita javascript
        webSettings.setJavaScriptEnabled(true);
        // agrega una interfaz de javascript, permite comunicar el código de la app
        webView.addJavascriptInterface(new MyJavascriptInterface(), "appInterface");
    }

    public void loadURL(View view) {
        EditText editText = findViewById(R.id.editText);
        String url = editText.getText().toString();

        if (url.isEmpty()) {
            Toast.makeText(this, "Ingresa una URL válida", Toast.LENGTH_SHORT).show();
        } else {
            // Carga el video de YouTube usando la URL de inserción
            String embedUrl = "https://www.youtube.com/embed/" + extractVideoId(url); // Extrae el ID del video de la URL ingresada
            webView.loadUrl(embedUrl);
        }
    }

    // Método para extraer el ID del video de la URL de YouTube
    private String extractVideoId(String youtubeUrl) {
        String videoId = null;
        if (youtubeUrl != null && youtubeUrl.trim().length() > 0 && (youtubeUrl.contains("youtube.com") || youtubeUrl.contains("youtu.be"))) {
            String expression = "(?<=watch\\?v=|/videos/|embed\\/|youtu.be\\/)[^#\\&\\?]*";
            Pattern pattern = Pattern.compile(expression);
            Matcher matcher = pattern.matcher(youtubeUrl);
            if (matcher.find()) {
                videoId = matcher.group();
            }
        }
        return videoId;
    }
    private class MyJavascriptInterface {
        @android.webkit.JavascriptInterface
        public void showToast(String message) {
        }
    }

}