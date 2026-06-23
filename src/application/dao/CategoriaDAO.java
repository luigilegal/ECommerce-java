package application.dao;

import application.entidades.Categoria;
import application.services.CategoriaService;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class CategoriaDAO extends DBConnetion{

    private Categoria categoria;
    private CategoriaService categoriaService;
    private ArrayList<String> list;

    public Categoria getCategoria() {
        return categoria;
    }

    public CategoriaService getCategoriaService() {
        return categoriaService;
    }

    public ArrayList<String> getList() {
        return list;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public void setList(ArrayList<String> list) {
        this.list = list;
    }

    public void setCategoriaService(CategoriaService categoriaService) {
        this.categoriaService = categoriaService;
    }

    public void insert(Categoria categoria){
        getCon();
        Connection ct = getConecxao();
        try {

            PreparedStatement ps = ct.prepareStatement("INSERT INTO categoria VALUES (default, ?, ?)");
            ps.setString(1,getCategoria().getNome());
            ps.setString(2,getCategoria().getDescricao());

            int insertcount = ps.executeUpdate();

            System.out.println("Qtd. de insercao: " + insertcount);

        }catch (Exception e){
            System.out.println("Ocorreu um erro ao inserir uma categoria.\nERRO: " + e);
        }
    }

    public void listCategoria(ArrayList<String> list){
        String id, nome, desc;

        try {
            getCon();

            Connection ct = getConecxao();

            PreparedStatement ps = ct.prepareStatement("SELECT * FROM categoria");
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()){
               id = resultSet.getString("ID_categoria");
               nome = resultSet.getString("nome");
               desc = resultSet.getString("descricao");

               list.add(id +"-"+ nome +"-"+ desc);
            }

        } catch (Exception e) {
            System.out.println("Erro ao listar as categorias.\nErro: " + e);
        }
        getCategoriaService().setList(list);
    }
    public String getcat(Integer id){
        try {
            getCon();
            Connection ct = getConecxao();

            PreparedStatement ps = ct.prepareStatement("SELECT nome FROM categoria WHERE ID_categoria = ?");
            ps.setInt(1,id);

            ResultSet resultSet = ps.executeQuery();

            while (resultSet.next()){
                getCategoria().setNome(resultSet.getString("nome"));
            }

        }catch (Exception e){
            System.out.println(e);
        }
        return getCategoria().getNome();
    }
}
