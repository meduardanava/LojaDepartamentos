package com.example.lojadepartamentos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button btCadastroCliente;
    private Button btCadastroItem;
    private Button btFinalizarPedido;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btCadastroCliente = findViewById(R.id.btCadastroCliente);
        btCadastroItem = findViewById(R.id.btCadastroItem);
        btFinalizarPedido = findViewById(R.id.btFinalizarPedido);

        btCadastroCliente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                abrirActivity(CadastroCliente.class);
            }
        });

        btCadastroItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                abrirActivity(CadastroItem.class);
            }
        });

        btFinalizarPedido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                abrirActivity(LancamentoPedido.class);
            }
        });
    }

    private void abrirActivity(Class<?> activity){
        Intent intent = new Intent(MainActivity.this, activity);
        startActivity(intent);
    }
}