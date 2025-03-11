package com.example.lojadepartamentos;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
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
    private int posicaoItem = -1;
    private int posicaoCliente = -1;
    private int numPedido;
    private String[] vetAvista = {"À vista"};
    private String[] vetAprazo = {"Selecione a quantidade de parcelas", "1 vez",
            "2 vezes", "3 vezes", "4 vezes", "5 vezes", "6 vezes"};
    private String[] vetPedido = {};

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
                posicaoCliente = i;
                tvErroCliente.setVisibility(View.GONE);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {}
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

        rgPagamento.setOnCheckedChangeListener((group, checkedId) -> carregarPagamento());

        popularListaItem();
        popularListaCliente();
        atualizarListaPedido();
    }

    private void popularListaCliente(){

        clientes = Controller.getInstancia().retornarCliente();
        String[] vetorClientes = new String[clientes.size() + 1];
        vetorClientes[0] = "Selecione o cliente";

        for (int i = 0; i < clientes.size(); i++){
            Cliente cliente = clientes.get(i);
            vetorClientes[i + 1] = cliente.getNome();
        }

        ArrayAdapter<String> adapterC = new ArrayAdapter(this,
                android.R.layout.simple_dropdown_item_1line, vetorClientes);

        spListaCliente.setAdapter(adapterC);
    }

   private void popularListaItem(){

        itens = Controller.getInstancia().retornarItem();
        String[] vetorItens = new String[itens.size() + 1];
        vetorItens[0] = "Selecione o item";

        for (int i = 0; i < itens.size(); i++){
            Item item = itens.get(i);
            vetorItens[i + 1] = item.getCodigo() + " - " + item.getDescricao();
        }

        ArrayAdapter<String> adapterI = new ArrayAdapter(this,
               android.R.layout.simple_dropdown_item_1line, vetorItens);

        spListaItem.setAdapter(adapterI);
   }

   private void carregarPedido(){

        if (posicaoCliente <= 0){
            tvErroCliente.setVisibility(View.VISIBLE);
            return;
        }

        int quantidade;
        String quantidadeStr = edQuantidade.getText().toString();

        if (quantidadeStr.isEmpty()){
            edQuantidade.setError("A quantidade deve ser informada!");
            edQuantidade.requestFocus();
            return;
        } try {
            quantidade = Integer.parseInt(quantidadeStr);

            if (quantidade <= 0 ){
                edQuantidade.setError("Informe uma quantidade maior que zero!");
                edQuantidade.requestFocus();
                return;
            }
        } catch (NumberFormatException e){
            edQuantidade.setError("Número inválido!");
            edQuantidade.requestFocus();
            return;
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
        Toast.makeText(this, "Item adicionado no pedido!",
                Toast.LENGTH_LONG).show();
   }

   private void atualizarListaPedido(){

        StringBuilder texto = new StringBuilder();

       ArrayList<Pedido> listaP = Controller.getInstancia().retornarPedido();

       for (Pedido pedido : listaP){
            Item item = pedido.getItem();
            texto.append(item.getCodigo()).append(" | ")
                    .append(item.getDescricao()).append(" | ")
                    .append(item.getValorUnitario())
                    .append("\n--------------------------------------------\n");
       }
       tvListaPedido.setText(texto.toString());
   }

   private void mostrarPedido(){

       ArrayList<Pedido> listaPedidos = Controller.getInstancia().retornarPedido();
        String[] vetPedido = new String[listaPedidos.size()];

        for (int i = 0 ; i < listaPedidos.size(); i++ ){
            Pedido pedido = listaPedidos.get(i);
            vetPedido[i] = pedido.getItem().getDescricao();
        }

        ArrayAdapter<String> adapterLP = new ArrayAdapter(this,
                android.R.layout.simple_dropdown_item_1line,vetPedido);
   }

   private void carregarPagamento(){

        ArrayAdapter<String> adapterPag;

        if (rbAvista.isChecked()){
            adapterPag = new ArrayAdapter<>(this,
                    android.R.layout.simple_dropdown_item_1line, vetAvista);
        } else if (rbAprazo.isChecked()){
            adapterPag = new ArrayAdapter<>(this,
                    android.R.layout.simple_dropdown_item_1line, vetAprazo);
        } else {
            return;
        }

        spListaItem.setAdapter(adapterPag);
   }

   private void gerarNumPedido(){

        Random random = new Random();
        numPedido = random.nextInt(1000) + 1;
   }
}