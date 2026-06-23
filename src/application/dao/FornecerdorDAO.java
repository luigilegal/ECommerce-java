package application.dao;

import application.entidades.Fornecerdor;

import application.services.EnderecoService;
import application.services.FornecerdorService;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class FornecerdorDAO extends DBConnetion {

    private String sql;
    private String resultQuery;
    private boolean verify;

    private FornecerdorService fornecerdorService;
    private Fornecerdor fornecerdor;
    private EnderecoService enderecoService;

    public FornecerdorDAO (){

    }

    public FornecerdorDAO(String sql, String resultQuery){
        this.sql = sql;
        this.resultQuery = resultQuery;
    }

    public String getSql() {
        return sql;
    }

    public String getResultQuery() {
        return resultQuery;
    }

    public boolean getVerify(){
        return verify;
    }

    public FornecerdorService getFornecerdorService() {
        return fornecerdorService;
    }

    public Fornecerdor getFornecerdor() {
        return fornecerdor;
    }

    public EnderecoService getEnderecoService() {
        return enderecoService;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }

    public void setResultQuery(String resultQuery) {
        this.resultQuery = resultQuery;
    }

    public void setVerify(boolean verify) {
        this.verify = verify;
    }

    public void setFornecerdorService(FornecerdorService fornecerdorService) {
        this.fornecerdorService = fornecerdorService;
    }

    public void setFornecerdor(Fornecerdor fornecerdor) {
        this.fornecerdor = fornecerdor;
    }

    public void setEnderecoService(EnderecoService enderecoService) {
        this.enderecoService = enderecoService;
    }

    public void insert(Fornecerdor fornecerdor){
        try {
            getCon();
            Connection ct = getConecxao();
            PreparedStatement ps = ct.prepareStatement("INSERT INTO fornecerdor VALUES (default, ?, ?, ?, ?, ?, ?,default)");
            ps.setString(1,fornecerdor.getCnjp());
            ps.setString(2,fornecerdor.getNome());
            ps.setString(3,fornecerdor.getEmail());
            ps.setString(4,fornecerdor.getSenha());
            ps.setString(5,fornecerdor.getTelefone());
            ps.setString(6,getFornecerdorService().dataRegNow());
            int insertCount = ps.executeUpdate();

            System.out.println("Qtd. de insercoes: " + insertCount);

            getEnderecoService().chooseEndereco();

        }catch (Exception e){
            System.out.println("Ocorreu um erro ao inserir o fornecerodr: " + e);
        }
    }
    public void update(String sql, String identifier){
        try {
            getCon();
            Connection ct = getConecxao();
            PreparedStatement ps = ct.prepareStatement(sql);
            ps.setString(1,getSql());
            ps.setString(2, getFornecerdor().getIdUser());
            int updatecount = ps.executeUpdate();
            setVerify(true);
            System.out.println("Qtd. de updates: " + updatecount);
        }catch (Exception e){
            System.out.println("Ocorreu um erro ao atualizar o " + identifier + "em FonecerdorDAO\nErro: "+ e);
            setVerify(false);
        }
    }

    public void delete(String sql, String identifier){

        getCon();
        Connection ct = getConecxao();
        try {

            PreparedStatement ps = ct.prepareStatement(sql);
            ps.setString(1,getFornecerdor().getIdUser());
            int deletecount = ps.executeUpdate();

            System.out.println("Qtd. de item deletados: " + deletecount);
            setVerify(true);
        }catch (Exception e){
            setVerify(false);
            System.out.println("Ocorreu um erro ao deletar o " + identifier + "em Fornecerdor DAO\nErro: " + e);
        }

    }
    public void search(Integer id){

        try {
            getCon();
            Connection ct = getConecxao();

            PreparedStatement ps = ct.prepareStatement("SELECT nome FROM fornecerdor WHERE ID_fornecerdor = ?");
            ps.setInt(1,id);
            ResultSet resultSet = ps.executeQuery();

            while (resultSet.next()){
                setResultQuery(resultSet.getString("nome"));
            }

        }catch (Exception e){
            System.out.println(e);
        }

    }
}
