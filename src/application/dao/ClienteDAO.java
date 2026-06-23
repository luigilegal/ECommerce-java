package application.dao;

import application.App;
import application.entidades.Cliente;
import application.entidades.Endereco;
import application.services.ClienteService;
import application.services.EnderecoService;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;


public class ClienteDAO extends DBConnetion{

    private String sql;
    private String resultQuery;
    private boolean verify;

    private EnderecoService enderecoService;
    private Endereco endereco;
    private Cliente cliente;
    private CarrinhoDAO carrinhoDAO;

    public String getSql() {
        return sql;
    }

    public String getResultQuery() {
        return resultQuery;
    }

    public boolean getVerify() {
        return verify;
    }

    public EnderecoService getEnderecoService() {
        return enderecoService;
    }

    public Endereco getEndereco() {
        return endereco;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public CarrinhoDAO getCarrinhoDAO() {
        return carrinhoDAO;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }

    public void setSql(String ano, String mes, String dia){
        this.sql = ano+ "-" + mes + "-" + dia;
    }

    public void setResultQuery(String resultQuery) {
        this.resultQuery = resultQuery;
    }

    public void setVerify(boolean verify) {
        this.verify = verify;
    }

    public void setEnderecoService(EnderecoService enderecoService) {
        this.enderecoService = enderecoService;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public void setCarrinhoDAO(CarrinhoDAO carrinhoDAO) {
        this.carrinhoDAO = carrinhoDAO;
    }

    public void insertion(Cliente cliente) {
        getCon();
        Connection ct = getConecxao();
        ClienteService cService = new ClienteService();
        try {
            PreparedStatement ps = ct.prepareStatement("INSERT INTO cliente VALUES(default, ?, ?, ?, ?, ?, ?, ?, ?, default)");
            ps.setString(1, cliente.getNome());
            ps.setString(2, cliente.getCpf());
            ps.setString(3, cliente.getEmail());
            ps.setString(4, cliente.getSenha());
            ps.setString(5, cliente.getSexo());
            ps.setString(6, cliente.getTelefone());
            ps.setString(7, cliente.getDataNacimento());
            ps.setString(8, cliente.getDataReg());
            int insertCount = ps.executeUpdate();
            System.out.println("Qtd.Insercoes: " + insertCount);
            System.out.println("Usuario cadastrado com sucesso!");

            setCliente(cliente);
            cService.setCliente(cliente);

            getidCli();
            getCarrinhoDAO().insert();
            getEnderecoService().cadastrarEndereco(getEndereco());


        } catch (Exception exception) {
            System.out.println("erro ao registrar usuario: " + exception);

        }
    }

    public Cliente verGetVar() {
        DBConnetion connetion = new DBConnetion();

        if (getCliente().getIdUser() == null || getCliente().getNome() == null || getCliente().getDataNacimento() == null ||
                getCliente().getEmail() == null || getCliente().getCpf() == null) { // verifico se as variaveis da classe esta vazia
            try {
                // caso ela esteja procuro no banco de dados as credencias do cliente que logou no sistema

                connetion.getCon();
                Connection ct = connetion.getConecxao();

                PreparedStatement ps = ct.prepareStatement("SELECT * FROM cliente WHERE email =? or CPF = ?");
                ps.setString(1, getCliente().getEmail());
                ps.setString(2, getCliente().getCpf());
                ResultSet resultSet = ps.executeQuery();

                while (resultSet.next()) {
                    getCliente().setNome(resultSet.getString("nome"));
                    getCliente().setEmail(resultSet.getString("email"));
                    getCliente().setCpf(resultSet.getString("CPF"));
                    getCliente().setSexo(resultSet.getString("Sexo"));
                    getCliente().setTelefone(resultSet.getString("telefone"));
                    getCliente().setDataNacimento(resultSet.getString("data_nascimento"));
                    getCliente().setDataReg(resultSet.getString("data_de_registro"));
                    getCliente().setIdUser(resultSet.getString("ID_cliente"));

                }


            } catch (Exception e) {
                System.out.println("Ocorreu um erro: " + e);
            }
        }
        return getCliente();
    }

    public void update(String sql, String identifier){

        setVerify(false);
        try {
            getCon();
            Connection ct = getConecxao();

            PreparedStatement ps = ct.prepareStatement(sql);
            ps.setString(1,getSql());
            ps.setString(2,getCliente().getIdUser());
            int updatecount =ps.executeUpdate();
            System.out.println("Qtd. de updates: " + updatecount);
            setVerify(true);

        }catch (Exception e){
            System.out.println("Ocorreu um erro ao atualizar o "+identifier+" em ClienteDAO: "+ e);
            setVerify(false);
        }
    }
    public void delete(String sql, String Identifier){
        getCon();
        Connection ct = conecxao;

        try {

            PreparedStatement ps = ct.prepareStatement(sql);
            ps.setString(1, getCliente().getIdUser());
            int deletecount = ps.executeUpdate();

            System.out.println("Qtd. de item deletado: " + deletecount);
            App.callerMain();
        }catch (Exception e){
            System.out.println("Ocorreu um erro ao tentar deletar sua/seu " + Identifier + " erro: " + e);
        }
    }

    public void getidCli(){
        try{

            Connection ct = getConecxao();
            PreparedStatement ps;

            ps = ct.prepareStatement("SELECT ID_cliente FROM cliente WHERE email = ?");
            ps.setString(1, getCliente().getEmail());
            ResultSet resultSet = ps.executeQuery();

            while (resultSet.next()){
                getCliente().setIdUser(resultSet.getString("ID_cliente"));
            }
        getCarrinhoDAO().setUser(getCliente());
        }catch (Exception e){
            System.out.println("ocorreu um erro ao pegar o id do cliente.\nERRO: " + e);
        }
    }

    public void search(Integer id){

        try {
            getCon();
            Connection ct = getConecxao();

            PreparedStatement ps = ct.prepareStatement("SELECT * FROM cliente WHERE ID_cliente = ?");
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
