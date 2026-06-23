package application.dao;

import application.entidades.Produto;
import application.services.ProdutoService;
import application.services.ValidarLogin;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ProdutoDAO extends DBConnetion {
    private String sql;
    private String resultQuery;

    private Produto produto;
    private ProdutoService produtoService;
    private ValidarLogin login;
    private CategoriaDAO categoriaDAO;

    public ValidarLogin getLogin() {
        return login;
    }

    public Produto getProduto() {
        return produto;
    }

    public String getSql() {
        return sql;
    }

    public String getResultQuery() {
        return resultQuery;
    }

    public ProdutoService getProdutoService() {
        return produtoService;
    }

    public CategoriaDAO getCategoriaDAO() {
        return categoriaDAO;
    }

    public void setLogin(ValidarLogin login) {
        this.login = login;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }

    public void setResultQuery(String resultQuery) {
        this.resultQuery = resultQuery;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public void setProdutoService(ProdutoService produtoService) {
        this.produtoService = produtoService;
    }

    public void setCategoriaDAO(CategoriaDAO categoriaDAO) {
        this.categoriaDAO = categoriaDAO;
    }

    public void insert(Produto produto){
        setProduto(produto);
        getCon();
        Connection ct = getConecxao();
        int insertcount;
        try {
            if (getLogin().getChoose()) {
                PreparedStatement ps = ct.prepareStatement("INSERT INTO produto VALUES (default, ?, ?, ?, ?, ?, default, ?, ?)");
                ps.setString(1, getProduto().getsKUCodigoUnico());
                ps.setString(2, getProduto().getNome());
                ps.setString(3, getProduto().getDescricao());
                ps.setDouble(4, getProduto().getValor());
                ps.setInt(5, getProduto().getQuantidade());
                ps.setInt(6, getProduto().getFkIdCli());
                ps.setInt(7, getProduto().getFkIdCat());

                insertcount = ps.executeUpdate();

                System.out.println("Qtd. de insercoes feitas: " + insertcount);
            }else {
                PreparedStatement ps = ct.prepareStatement("INSERT INTO produto VALUES (default, ?, ?, ?, ?, ?, ?, default, ?)");
                ps.setString(1, getProduto().getsKUCodigoUnico());
                ps.setString(2, getProduto().getNome());
                ps.setString(3, getProduto().getDescricao());
                ps.setDouble(4, getProduto().getValor());
                ps.setInt(5, getProduto().getQuantidade());
                ps.setInt(6, getProduto().getFkIdFor());
                ps.setInt(7, getProduto().getFkIdCat());

                insertcount = ps.executeUpdate();

                System.out.println("Qtd. de insercoes feitas: " + insertcount);
            }
        }catch (Exception e){
            System.out.println("Ocorreu um erro ao inserir o produto.\nERRO: " + e);
        };

    }

    public void listProdutos(ArrayList<String> list){

        getCon();
        Connection ct = getConecxao();
        try{

            PreparedStatement ps = ct.prepareStatement("SELECT * FROM produto");
            ResultSet resultSet = ps.executeQuery();

            while (resultSet.next()){
                list.add(
                         resultSet.getString("ID_produto") + "-" +
                         resultSet.getString("SKU") + "*" +
                         resultSet.getString("Nome") + "]" +
                         resultSet.getString("Descricao") + "|" +
                         resultSet.getString("Valor") + "[" +
                         resultSet.getString("Qtd_no_Estoque") + "/" +
                         resultSet.getString("FK_id_fornecerdor") + "<" +
                         resultSet.getString("FK_id_cliente") + ">" +
                         resultSet.getString("FK_id_categoria")
                );
            }

            getProdutoService().setList(list);
        }catch (Exception e){
            System.out.println("Ocorreu um erro ao listar os produtos.\nErro: " + e);
        }
    }

    public String viewProduto(String id){
        getCon();
        Connection ct = getConecxao();
        try {
            if (getLogin().getChoose()) {
                PreparedStatement ps = ct.prepareStatement("SELECT * FROM produto WHERE ID_Produto = ?");
                ps.setString(1,id);
                ResultSet resultSet = ps.executeQuery();
                while (resultSet.next()){
                    getProduto().setIdProduto(resultSet.getString("ID_produto"));
                    getProduto().setNome(resultSet.getString("Nome"));
                    getProduto().setDescricao(resultSet.getString("descricao"));
                    getProduto().setValor(resultSet.getDouble("Valor"));
                    getProduto().setQuantidade(resultSet.getInt("Qtd_no_estoque"));
                    getProduto().setFkIdCli(resultSet.getInt("FK_id_cliente"));
                    getProduto().setFkIdFor(resultSet.getInt("FK_id_fornecerdor"));
                    getProduto().setFkIdCat(resultSet.getInt("FK_id_categoria"));
                }
            }else {

            }
        }catch (Exception e){
            System.out.println("Ocorreu um erro ao inspecionar o produto.\nERRO: " + e);
        }
        return getCategoriaDAO().getcat(getProduto().getFkIdCat());
    }
    public void update(String sql, String identifier){
        try {
            getCon();
            Connection ct = getConecxao();
            PreparedStatement ps = ct.prepareStatement(sql);

            int updatecount = ps.executeUpdate();
        }catch (Exception e){
            System.out.println("Ocorreu um erro ao atualizar o/a " + identifier + " em Produto.\nERRO: " + e);
        }
    }

    public ArrayList<String> itemCarrinhoProduto(String sql, ArrayList<String> list){
        getCon();
        Connection ct = getConecxao();

        try {
            PreparedStatement ps = ct.prepareStatement("SELECT * FROM produto WHERE ID_produto = " + sql);
            ResultSet resultSet = ps.executeQuery();

            while (resultSet.next()){
                list.add(resultSet.getString("ID_produto") + "-" +
                         resultSet.getString("nome") +";" +
                         resultSet.getString("valor")
                );
            }

        } catch (Exception e) {
            System.out.println("Ocorreu um erro ao listar os produtos do carrinho.\nERRO: " + e);
        }
        return list;
    }
    public void search(String id){
        try {
            getCon();
            Connection ct =getConecxao();

            PreparedStatement ps = ct.prepareStatement("SELECT * FROM produto WHERE ID_produto = ?");
            ps.setString(1,id);
            ResultSet resultSet = ps.executeQuery();

            while (resultSet.next()){
                getProduto().setNome(resultSet.getString("Nome"));
            }

        }catch (Exception e){
            System.out.println("Ocorreu um erro ao procurar o produto.\nERRO" + e);
        }

    }
    public void searchCat(ArrayList<String>list){
        try {
            getCon();

            Connection ct = getConecxao();

            PreparedStatement ps = ct.prepareStatement("SELECT * FROM produto WHERE FK_id_categoria");
            ResultSet resultSet = ps.executeQuery();

            while (resultSet.next()){
                list.add(resultSet.getString("ID_produto") + "-" +
                        resultSet.getString("nome") +";" +
                        resultSet.getString("valor")
                );
            }

        }catch (Exception e){
            System.out.println("Ocorreu um erro ao procurar o proudto pela categoria.\nERRO: " + e);
        }
    }
}
