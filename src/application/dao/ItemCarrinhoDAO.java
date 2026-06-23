package application.dao;

import application.entidades.Carrinho;
import application.entidades.ItemCarrinho;
import application.entidades.Pedido;
import application.entidades.Produto;
import application.services.ItemCarrinhoService;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class ItemCarrinhoDAO extends DBConnetion{
    private ItemCarrinhoService itemCarrinhoService;
    private ItemCarrinho itemCarrinho;
    private Produto produto;
    private Carrinho carrinho;
    private ArrayList<String> list;
    private Pedido pedido;

    public Pedido getPedido() {
        return pedido;
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }

    public ItemCarrinhoService getItemCarrinhoService() {
        return itemCarrinhoService;
    }

    public ItemCarrinho getItemCarrinho() {
        return itemCarrinho;
    }

    public Produto getProduto() {
        return produto;
    }

    public Carrinho getCarrinho() {
        return carrinho;
    }

    public ArrayList<String> getList() {
        return list;
    }

    public void setItemCarrinhoService(ItemCarrinhoService itemCarrinhoService) {
        this.itemCarrinhoService = itemCarrinhoService;
    }

    public void setItemCarrinho(ItemCarrinho itemCarrinho) {
        this.itemCarrinho = itemCarrinho;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public void setCarrinho(Carrinho carrinho) {
        this.carrinho = carrinho;
    }

    public void setList(ArrayList<String> list) {
        this.list = list;
    }

    public void insert(Produto produto, Carrinho carrinho){
        try{
            getCon();
            Connection ct = getConecxao();
            PreparedStatement ps = ct.prepareStatement("INSERT INTO item_carrinho VALUES (default, ?, ?, ?, ?)");
            ps.setInt(1,1);
            ps.setDouble(2, produto.getValor());
            ps.setString(3, carrinho.getIdCarrinho());
            ps.setString(4, produto.getIdProduto());

            int uptadecount = ps.executeUpdate();
            System.out.println("Qtd. de insercoes"+uptadecount);
        }catch (Exception e){
            System.out.println("Ocorreu um erro ao inserir item no carrinho.\nERRO:" + e);
        }
    }
    public void getVar(){

        try {
            getCon();

            Connection ct = getConecxao();

            PreparedStatement ps = ct.prepareStatement("SELECT * FROM item_carrinho WHERE fk_id_carrinho = ? AND fk_id_produto = ?");
            ps.setString(1,getCarrinho().getIdCarrinho());
            ps.setString(2,getProduto().getIdProduto());
            ResultSet resultSet = ps.executeQuery();
            System.out.println(getCarrinho().getIdCarrinho());
            while (resultSet.next()){
                getItemCarrinho().setIdItemCarrinho(resultSet.getString("id_item_carrinho"));
                getItemCarrinho().setQuantidade(resultSet.getInt("quantidade"));
                getItemCarrinho().setPrecoUnitario(resultSet.getDouble("preco_unitario"));
                getItemCarrinho().setFkCarrinho(resultSet.getString("fk_id_carrinho"));
                getItemCarrinho().setFkProduto(resultSet.getString("fk_id_produto"));
            }

        }catch (Exception e){
            System.out.println("Ocorreu um erro ao verificar o item do carrinho.\nERRO: " + e);

        }

    }

    public void getVar2(){

        try {
            getCon();

            Connection ct = getConecxao();

            PreparedStatement ps = ct.prepareStatement("SELECT * FROM item_carrinho WHERE fk_id_carrinho = ?");
            ps.setString(1,getCarrinho().getIdCarrinho());
            ResultSet resultSet = ps.executeQuery();
            System.out.println(getCarrinho().getIdCarrinho());
            while (resultSet.next()){
                getItemCarrinho().setIdItemCarrinho(resultSet.getString("id_item_carrinho"));
                getItemCarrinho().setQuantidade(resultSet.getInt("quantidade"));
                getItemCarrinho().setPrecoUnitario(resultSet.getDouble("preco_unitario"));
                getItemCarrinho().setFkCarrinho(resultSet.getString("fk_id_carrinho"));
                getItemCarrinho().setFkProduto(resultSet.getString("fk_id_produto"));
            }

            getItemCarrinhoService().setItemCarrinho(getItemCarrinho());
        }catch (Exception e){
            System.out.println("Ocorreu um erro ao verificar o item do carrinho.\nERRO: " + e);

        }

    }

    public void update(String sql){
        try {
            getCon();

            Connection ct = getConecxao();

            PreparedStatement ps = ct.prepareStatement(sql);
            ps.setString(1, getCarrinho().getIdCarrinho());

            ps.executeUpdate();

        }catch (Exception e){
            System.out.println("Ocorreu um erro ao atualizar o item carrinho.\nERRO: " + e);
        }
    }
    public void listItemsCarrinho(ArrayList<String> list){


        try {
            getCon();
            Connection ct = getConecxao();

            double totalPedido = 0.0;

            PreparedStatement ps = ct.prepareStatement("SELECT * FROM item_carrinho WHERE fk_id_carrinho = ?");
            ps.setString(1, getCarrinho().getIdCarrinho());
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()){
                list.add(resultSet.getString("quantidade") + "-" +
                         resultSet.getString("preco_unitario") + ";" +
                         resultSet.getString("fk_id_produto")

                );

                int quantidade = resultSet.getInt("quantidade");
                double precoUnitario = resultSet.getDouble("preco_unitario");

                double subtotal = quantidade * precoUnitario;
                totalPedido += subtotal;  // Acumula o total

                System.out.println("Subtotal item: R$ " + subtotal);
            }
            getPedido().setValorFinal(String.valueOf(totalPedido));

        }catch (Exception e){
            System.out.println(e);
        }
        getItemCarrinhoService().setList(list);
    }

    public void getqtditem(String idPrd){

        try {
            getCon();
            Connection ct = getConecxao();

            PreparedStatement ps = ct.prepareStatement("SELECT * FROM item_carrinho WHERE fk_id_produto = ? AND fk_id_carrinho = ?");
            ps.setString(1, idPrd);
            ps.setString(2, getCarrinho().getIdCarrinho());

            ResultSet resultSet = ps.executeQuery();

            while (resultSet.next()){
                getItemCarrinho().setQuantidade(resultSet.getInt("quantidade"));
                getItemCarrinho().setPrecoUnitario(resultSet.getDouble("preco_unitario"));
            }
            getItemCarrinhoService().setItemCarrinho(getItemCarrinho());

        }catch (Exception e){
            System.out.println("Ocorreu um erro ao pegar o id do item.\n ERRo:" + e);
        }

    }

    public boolean delete(String sql, String id){

        try{
            getCon();
            Connection ct = getConecxao();

            PreparedStatement ps = ct.prepareStatement(sql);
            ps.setString(1,id);
            ps.setString(2, getCarrinho().getIdCarrinho());

            ps.executeUpdate();
        }catch (Exception e){
            System.out.println("Ocorreu um erro ao deletar o item do carrinho.\nERRO: " + e);
            return false;
        }
        return true;
    }
}
