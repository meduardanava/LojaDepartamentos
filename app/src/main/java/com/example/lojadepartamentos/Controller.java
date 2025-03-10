package com.example.lojadepartamentos;

import java.util.ArrayList;

public class Controller {

    private static Controller instancia;

    private ArrayList<Cliente> listaClientes;
    private ArrayList<Item> listaItens;
    private ArrayList<Pedido> listaPedidos;

    public static Controller getInstancia(){
        if(instancia == null){
            return instancia = new Controller();
        }else{
            return instancia;
        }
    }

    private Controller(){
        listaClientes = new ArrayList<>();
        listaItens = new ArrayList<>();
        listaPedidos = new ArrayList<>();
    }

    public void salvarCliente(Cliente cliente){
        listaClientes.add(cliente);
    }

    public ArrayList<Cliente> retornarCliente(){
        return listaClientes;
    }

    public void salvarItem(Item item){
        listaItens.add(item);
    }

    public ArrayList<Item> retornarItem(){
        return listaItens;
    }

    public void listaPedido(Pedido pedido){
        listaPedidos.add(pedido);
    }

    public ArrayList<Pedido> retornarPedido(){
        return listaPedidos;
    }

}
