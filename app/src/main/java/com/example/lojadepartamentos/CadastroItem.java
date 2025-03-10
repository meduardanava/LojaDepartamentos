package com.example.lojadepartamentos;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class CadastroItem extends AppCompatActivity {

    private EditText edCodigoItem;
    private EditText edDescricaoItem;
    private EditText edValorUnitario;
    private TextView tvListaItens;
    private Button btSalvarItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_item);

        edCodigoItem = findViewById(R.id.edCodigoItem);
        edDescricaoItem = findViewById(R.id.edDescricaoItem);
        edValorUnitario = findViewById(R.id.edValorUnitario);
        tvListaItens = findViewById(R.id.tvListaItens);
        btSalvarItem = findViewById(R.id.btSalvarItem);
        btSalvarItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                salvarItem();
            }
        });
        atualizarItem();
    }

    private void salvarItem(){

        int codigo;
        double valorUnitario = 0;

        if (edCodigoItem.getText().toString().isEmpty()){
            edCodigoItem.setError("O CÓDIGO do item deve ser informado!");
            return;
        }else {
            try {
                codigo = Integer.parseInt(edCodigoItem.getText().toString());
            }catch (Exception ex){
                edCodigoItem.setError("Informe um CÓDIGO válido(somente números)!");
                return;
            }
        }

        if (edDescricaoItem.getText().toString().isEmpty()){
            edDescricaoItem.setError("A DESCRIÇÃO do item deve ser informada!");
            return;
        }

        if (edValorUnitario.getText().toString().isEmpty()){
            edValorUnitario.setError("O VALOR UNITÁRIO do item deve ser informado!");
        }else {
            valorUnitario = Double.parseDouble(edValorUnitario.getText().toString());

            if (valorUnitario <= 0){
                edValorUnitario.setError("Informe um VALOR UNITÁRIO(somente " +
                        "números e valor maior que zero)");
                return;
            }
        }

        Item item = new Item();

        item.setCodigo(codigo);
        item.setDescricao(edDescricaoItem.getText().toString());
        item.setValorUnitario(valorUnitario);

        Controller.getInstancia().salvarItem(item);
        Toast.makeText(CadastroItem.this,
                "Item cadastrado com sucesso!", Toast.LENGTH_LONG).show();

        finish();
    }

    public void atualizarItem(){

        String texto = " ";

        ArrayList<Item> lista = Controller.getInstancia().retornarItem();

        for (Item item : lista){
            texto += "Código: " + item.getCodigo() + "\nDescrição: " + item.getDescricao() +
                    "\nValor Unitário: " + item.getValorUnitario() + "\n----------------" +
                    "-----------------------------------------------------------------\n";
        }
        tvListaItens.setText(texto);
    }

}