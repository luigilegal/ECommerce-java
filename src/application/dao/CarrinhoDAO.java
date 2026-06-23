package application.dao;

import application.entidades.Carrinho;
import application.entidades.Cliente;
import application.entidades.Usuario;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class CarrinhoDAO extends DBConnetion{

    private Carrinho carrinho;
    private Usuario user;

    public Carrinho getCarrinho() {
        return carrinho;
    }

    public Usuario getUser() {
        return user;
    }

    public void setCarrinho(Carrinho carrinho) {
        this.carrinho = carrinho;
    }

    public void setUser(Usuario user) {
        this.user = user;
    }

    public void insert(){
        try {
            getCon();
            Connection ct = getConecxao();

            PreparedStatement ps = ct.prepareStatement("INSERT INTO carrinho VALUES (default, ?)");
            ps.setString(1,getUser().getIdUser());

            ps.executeUpdate();
        }catch (Exception e){
            System.out.println("Ocorreu um erro ao inserir um carrinho ao cliente.\nERRO: " + e);
        }
    }
    public void getId(){
        try {
            getCon();
            Connection ct = getConecxao();

            PreparedStatement ps = ct.prepareStatement("SELECT id_carrinho FROM carrinho WHERE FK_cli = ?");
            ps.setString(1, getUser().getIdUser());
            ResultSet resultSet = ps.executeQuery();

            while (resultSet.next()){
                getCarrinho().setIdCarrinho(resultSet.getString("ID_carrinho"));
            }
            System.out.println(getCarrinho().getIdCarrinho());
        }catch (Exception e){
            System.out.println("Ocorreu um erro ao tentar pegar o id carrinho do usuario.\nERRO:" + e);
        }
    }
}
