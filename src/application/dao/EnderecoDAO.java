package application.dao;

import application.entidades.Cliente;
import application.entidades.Endereco;
import application.entidades.Fornecerdor;
import application.entidades.Usuario;
import application.services.ValidarLogin;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class EnderecoDAO extends DBConnetion {

    private ClienteDAO clienteDAO;
    private FornecerdorDAO fornecerdorDAO;
    private ValidarLogin login;
    private Endereco endereco;
    private Cliente cliente;
    private Fornecerdor forncerdor;
    private Usuario user;


    private String idEndereco;
    private String resultQuery;
    private String sql;
    private boolean verify;

    public Usuario getUser() {
        return user;
    }

    public void setUser(Usuario user) {
        this.user = user;
    }

    public ClienteDAO getClienteDAO() {
        return clienteDAO;
    }

    public FornecerdorDAO getFornecerdorDAO() {
        return fornecerdorDAO;
    }

    public ValidarLogin getLogin() {
        return login;
    }

    public void setFornecerdorDAO(FornecerdorDAO fornecerdorDAO) {
        this.fornecerdorDAO = fornecerdorDAO;
    }

    public void setLogin(ValidarLogin login) {
        this.login = login;
    }

    public void setClienteDAO(ClienteDAO clienteDAO) {
        this.clienteDAO = clienteDAO;
    }

    public Endereco getEndereco() {
        return endereco;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public Fornecerdor getForncerdor() {
        return forncerdor;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public void setFornecerdor(Fornecerdor forncerdor) {
        this.forncerdor = forncerdor;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }

    public void setVerify(boolean verify) {
        this.verify = verify;
    }

    public boolean getVerify(){
        return verify;
    }

    public String getSql() {
        return sql;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }

    public void setResultQuery(String resultQuery) {
        this.resultQuery = resultQuery;
    }

    public String getResultQuery() {
        return resultQuery;
    }

    public void setIdEndereco(String idEndereco) {
        this.idEndereco = idEndereco;
    }

    public String getIdEndereco() {
        String sql, index;
        sql = "SELECT ID_endereco FROM endereco WHERE FK_id_cliente = ?";
        index = "ID_endereco";
        if (this.idEndereco == null){
            getIds(sql,index);

            setIdEndereco(getResultQuery());
        }
        return idEndereco;
    }

    public void insertEndereco(Endereco endereco){
        String idUsuario = getUser().getIdUser();
        getCon();
        Connection ct = getConecxao();
        try {

            if (getLogin().getChoose()){


                PreparedStatement ps = ct.prepareStatement("INSERT INTO endereco values (default, ?, ?, ?, ?, ?, ?, ?, ?, default)");
                ps.setString(1,endereco.getPais());
                ps.setString(2,endereco.getEstado());
                ps.setString(3,endereco.getCidade());
                ps.setString(4,endereco.getBairro());
                ps.setString(5,endereco.getNumEndereco());
                ps.setString(6,endereco.getCep());
                ps.setString(7,endereco.getInfoAux());
                ps.setString(8,idUsuario);
                ps.executeUpdate();

                getEndereco().setFkIdCli(idUsuario);
            }else {

                PreparedStatement ps = ct.prepareStatement("INSERT INTO endereco values (default, ?, ?, ?, ?, ?, ?, ?, default, ?)");
                ps.setString(1,endereco.getPais());
                ps.setString(2,endereco.getEstado());
                ps.setString(3,endereco.getCidade());
                ps.setString(4,endereco.getBairro());
                ps.setString(5,endereco.getNumEndereco());
                ps.setString(6,endereco.getCep());
                ps.setString(7,endereco.getInfoAux());
                ps.setString(8,idUsuario);
                ps.executeUpdate();

                getEndereco().setFkIDfor(idUsuario);
            }


        }catch(Exception e){
            System.out.println("Ocorreu um erro ao inserir na tabela endereco em Endereco: " + e);
        }
    }
    public void getVaribles(){
        if (getEndereco().getPais() == null || getEndereco().getEstado() == null || getEndereco().getCidade() == null || getEndereco().getBairro() == null || getEndereco().getNumEndereco() == null|| getEndereco().getCep() == null) {
            getCon();
            Connection ct = getConecxao();
            if (getLogin().getChoose()){
                try {

                    System.out.println(getLogin().getChoose());

                    PreparedStatement ps = ct.prepareStatement("SELECT * FROM endereco WHERE FK_id_cliente = ?");
                    ps.setString(1, getCliente().getIdUser());
                    ResultSet resultSet = ps.executeQuery();
                    while (resultSet.next()){
                        getEndereco().setPais(resultSet.getString("Pais"));
                        getEndereco().setEstado(resultSet.getString("Estado"));
                        getEndereco().setCidade(resultSet.getString("Cidade"));
                        getEndereco().setBairro(resultSet.getString("Bairro"));
                        getEndereco().setNumEndereco(resultSet.getString("Num_endereco"));
                        getEndereco().setCep(resultSet.getString("CEP"));
                        getEndereco().setInfoAux(resultSet.getString("info_aux"));
                        getEndereco().setFkIdCli(resultSet.getString("FK_id_cliente"));
                    }

                } catch (Exception e) {
                    System.out.println("Ocorreu uma execao ao procurar os dados do cliente: " + e);
                }
            }else {
                try {

                    System.out.println(getLogin().getChoose());

                    PreparedStatement ps = ct.prepareStatement("SELECT * FROM endereco WHERE FK_id_fornecerdor = ?");
                    ps.setString(1, getForncerdor().getIdUser());
                    ResultSet resultSet = ps.executeQuery();
                    while (resultSet.next()){
                        getEndereco().setPais(resultSet.getString("Pais"));
                        getEndereco().setEstado(resultSet.getString("Estado"));
                        getEndereco().setCidade(resultSet.getString("Cidade"));
                        getEndereco().setBairro(resultSet.getString("Bairro"));
                        getEndereco().setNumEndereco(resultSet.getString("Num_endereco"));
                        getEndereco().setCep(resultSet.getString("CEP"));
                        getEndereco().setInfoAux(resultSet.getString("info_aux"));
                        getEndereco().setFkIDfor(resultSet.getString("FK_id_fornecerdor"));
                    }

                } catch (Exception e) {
                    System.out.println("Ocorreu uma execao ao procurar os dados do cliente: " + e);
                }
            }
        }
    }
    public void getIds(String sql, String index){

        getCon();
        Connection ct = conecxao;
        try {

            PreparedStatement ps = ct.prepareStatement(sql);
            ps.setString(1,getCliente().getIdUser());
            ResultSet resultSet = ps.executeQuery();

            while (resultSet.next()){
                setResultQuery(resultSet.getString(index));
            }

        }catch (Exception e){
            System.out.println("Ocorreu um erro ao pegar o id: " + e);
        }
    }
    public void update(String sql, String idenfier){

        setVerify(false);
        try {
            getCon();
            Connection ct = getConecxao();
            PreparedStatement ps = ct.prepareStatement(sql);
            ps.setString(1, getSql());
            ps.setString(2, getCliente().getIdUser());
            int updatecount = ps.executeUpdate();
            System.out.println("Qtd. de updates: " + updatecount);
            setVerify(true);
        }catch (Exception e){
            System.out.println("Ocorreu um erro ao atualizar o "+ idenfier +" em endereco.\nErro: "+ e);
            setVerify(false);
        }

    }
}
