package com.example.lojadepartamentos;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class CadastroCliente extends AppCompatActivity {

    private EditText edNomeCliente;
    private EditText edCpfCliente;
    private TextView tvListaClientes;
    private Button btSalvarCliente;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_cliente);

        edNomeCliente = findViewById(R.id.edNomeCliente);
        edCpfCliente = findViewById(R.id.edCpfCliente);
        tvListaClientes = findViewById(R.id.tvListaClientes);
        btSalvarCliente = findViewById(R.id.btSalvarCliente);
        btSalvarCliente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                salvarCliente();
            }
        });

        atualizarCliente();
    }

    private void salvarCliente(){

        if (edNomeCliente.getText().toString().isEmpty()){
            edNomeCliente.setError("O NOME do cliente deve ser informado!");
            return;
        }

        if (edCpfCliente.getText().toString().isEmpty()){
            edCpfCliente.setError("O CPF do cliente deve ser informado!");
            return;
        }

        Cliente cliente = new Cliente();

        cliente.setNome(edNomeCliente.getText().toString());
        cliente.setCpf(edCpfCliente.getText().toString());

        Controller.getInstancia().salvarCliente(cliente);
        Toast.makeText(CadastroCliente.this,
                "Cliente cadastrado com sucesso", Toast.LENGTH_LONG).show();

        finish();
    }

    public void atualizarCliente(){

        String texto = " ";

        ArrayList<Cliente> lista = Controller.getInstancia().retornarCliente();

        for (Cliente cliente : lista){
            texto += "Nome: " + cliente.getNome() + "\nCPF: " + cliente.getCpf()
                    + "\n--------------------------------------------------" +
                    "-------------------------------\n";
        }
        tvListaClientes.setText(texto);
    }
}