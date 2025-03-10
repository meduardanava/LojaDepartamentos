package com.example.lojadepartamentos;

import androidx.annotation.ArrayRes;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;

public class LancamentoPedido extends AppCompatActivity {

    private TextView tvErroCliente;
    private TextView tvErroItem;
    private TextView tvValores;
    private AutoCompleteTextView tvListaPedido;
    private EditText edQuantidade;
    private Spinner spListaCliente;
    private Spinner spListaItem;
    private RadioGroup rgPagamento;
    private RadioButton rbAvista;
    private RadioButton rbAprazo;
    private ArrayList<Cliente> clientes;
    private ArrayList<Item> itens;
    private int posicaoItem;
    private int posicaoCliente;
    private int numPedido;
    private String[]vetAvista = new String[];
    private String[]vetAprazo = new String[]{"Selecione a quantidade de parcelas", "1 vez",
            "2 vezes", "3 vezes", "4 vezes", "5 vezes", "6 vezes"};
    private String[]vetPedido = new String[];


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lancamento_pedido);

        tvErroItem = findViewById(R.id.tvErroItem);
        tvValores = findViewById(R.id.tvValores);
        edQuantidade = findViewById(R.id.edQuantidade);
        tvListaPedido = findViewById(R.id.tvListaPedido);
        spListaCliente = findViewById(R.id.spListaCliente);
        spListaItem = findViewById(R.id.spListaItem);
        rgPagamento = findViewById(R.id.rgPagamento);
        rbAvista = findViewById(R.id.rbAvista);
        rbAprazo = findViewById(R.id.rbAprazo);

        spListaCliente.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spListaItem.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int posicao, long l) {


                if (posicao > 0){
                    posicaoItem = posicao;
                    tvErroItem.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        rgPagamento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        popularListaItem();
        popularListaCliente();
        atualizarListaPedido();
    }

    private void popularListaCliente(){

        clientes = Controller.getInstancia().retornarCliente();
        String[]vetorClientes = new String[clientes.size()+1];
        vetorClientes[0] = "Selecione o cliente";

        for (int i = 0; i < clientes.size(); i++){
            Cliente cliente = clientes.get(i);
            vetorClientes[i+1] = cliente.getNome();
        }

        ArrayAdapter adapterC = new ArrayAdapter(LancamentoPedido.this,
                android.R.layout.simple_dropdown_item_1line, vetorClientes);

        spListaCliente.setAdapter(adapterC);
    }

   private void popularListaItem(){

        itens = Controller.getInstancia().retornarItem();
        String[]vetorItens = new String[itens.size()+1];
        vetorItens[0] = "Selecione o item";

        for (int i = 0; i < itens.size(); i++){
            Item item = itens.get(i);
            vetorItens[i+1] = item.getCodigo() + " - " + item.getDescricao();
        }

        ArrayAdapter adapterI = new ArrayAdapter(LancamentoPedido.this,
               android.R.layout.simple_dropdown_item_1line, vetorItens);

        spListaItem.setAdapter(adapterI);
   }

   private void carregarPedido(){

        int quantidade;

        if (posicaoCliente <=0){
            tvErroCliente.setVisibility(View.VISIBLE);
            return;
        }

        if (edQuantidade.getText().toString().isEmpty()){
            edQuantidade.setError("A quantidade deve ser informada!");
            edQuantidade.requestFocus();
            return;
        }else{
            quantidade = Integer.parseInt(edQuantidade.getText().toString());

            if (quantidade <= 0 ){
                edQuantidade.setError("Informe uma quantidade maior que zero!");
                edQuantidade.requestFocus();
                return;
            }
        }

        if (posicaoItem <= 0){
            tvErroItem.setVisibility(View.VISIBLE);
            return;
        }

        Pedido pedido = new Pedido();

        pedido.setCliente(clientes.get(posicaoCliente - 1));
        pedido.setItem(itens.get(posicaoItem - 1));
        pedido.setQuantidade(quantidade);

        Controller.getInstancia().listaPedido(pedido);
        Toast.makeText(LancamentoPedido.this,
                "Item adicionado no pedido!", Toast.LENGTH_LONG).show();
   }

   private void atualizarListaPedido(){

        String texto = " ";

       ArrayList<Pedido> listaP = Controller.getInstancia().retornarPedido();

       for (Pedido pedido : listaP){
            Item itens = pedido.getItem();
            texto += itens.getCodigo() + " \t| " + itens.getDescricao() + " \t|"
                    + itens.getValorUnitario() + "\n------------------------------------" +
                    "---------------------------------------------\n";
       }
       tvListaPedido.setText(texto);
   }

   private void mostrarPedido(){


        listaPedido = Controller.getInstancia().retornarPedido();
        String[]vetPedido =new String[listaPedidos.size()];

        for (int i = 0 ; i < listaPedido.size(); i++ ){
            Pedido pedido = listaPedido.get(i);
            vetPedido[i] = pedido.getItem();
        }
        ArrayAdapter adapterLP = new ArrayAdapter(LancamentoPedido.this,
                android.R.layout.simple_dropdown_item_1line,vetPedido);
   }

   private void carregarPagamento(){

        ArrayAdapter adapterPag = null;

        if (rbAvista.isChecked()){
            adapterPag = new ArrayAdapter<>(LancamentoPedido.this,
                    android.R.layout.simple_dropdown_item_1line, );

        }
        if (rbAprazo.isChecked()){
            adapterPag = new ArrayAdapter(LancamentoPedido.this,
                    android.R.layout.simple_dropdown_item_1line, vetAprazo);
        }
        spListaItem.setAdapter(adapterPag);

   }

   private void gerarNumPedido(){

        Random random = new Random();

        int limiteSuperior = 1000;
        int limiteInferior = 1;

        numPedido = random.nextInt(limiteSuperior - limiteInferior
                + 1) + limiteInferior;

        return;
   }
}