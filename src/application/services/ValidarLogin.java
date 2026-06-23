package application.services;

import application.App;
import application.dao.CarrinhoDAO;
import application.dao.ClienteDAO;
import application.dao.DBConnetion;
import application.dao.FornecerdorDAO;
import application.entidades.Cliente;
import application.entidades.Fornecerdor;
import application.entidades.Usuario;

import java.sql.*;
import java.util.Scanner;

public class ValidarLogin extends DBConnetion {

    private String idCliente;
    private String email;
    private String cpf;
    private String senha;
    private boolean verify;
    private boolean choose;

    private Usuario cliente;
    private ClienteDAO clienteDAO;
    private FornecerdorDAO fornecerdorDAO;
    private CarrinhoDAO carrinhoDAO;

    public void setCliente(Usuario cliente) {
        this.cliente = cliente;
    }

    public void setClienteDAO(ClienteDAO clienteDAO) {
        this.clienteDAO = clienteDAO;
    }

    public void setFornecerdorDAO(FornecerdorDAO fornecerdorDAO) {
        this.fornecerdorDAO = fornecerdorDAO;
    }

    public void setIdCliente(String idCliente) {
        this.idCliente = idCliente;
    }

    public void setCarrinhoDAO(CarrinhoDAO carrinhoDAO) {
        this.carrinhoDAO = carrinhoDAO;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public void setVerify(boolean verify) {
        this.verify = verify;
    }

    public void setChoose(boolean choose) {
        this.choose = choose;
    }

    public Usuario getCliente() {
        return cliente;
    }

    public ClienteDAO getClienteDAO() {
        return clienteDAO;
    }

    public FornecerdorDAO getFornecerdorDAO() {
        return fornecerdorDAO;
    }

    public String getIdCliente() {
        return idCliente;
    }

    public String getEmail() {
        return email;
    }

    public String getCpf() {
        return cpf;
    }

    public String getSenha() {
        return senha;
    }

    public  boolean getVerify(){
        return verify;
    }

    public boolean getChoose(){
        return choose;
    }

    public CarrinhoDAO getCarrinhoDAO() {
        return carrinhoDAO;
    }

    public ValidarLogin(){

    }

    public ValidarLogin(String idCliente,String email, String cpf, String senha){
        this.idCliente = idCliente;
        this.email = email;
        this.cpf = cpf;
        this.senha = senha;
    }
    public void getLogin(Usuario usuario){
        Scanner sc = new Scanner(System.in);
        String emailOuCPF;

        System.out.println(getChoose());
        if (getChoose()) {
            do {
                System.out.print("Digite seu Email ou CPF cadastrados: ");
                emailOuCPF = sc.nextLine();
                if (emailOuCPF.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")) {
                    usuario.setEmail(emailOuCPF);
                    setEmail(emailOuCPF);
                    setVerify(true);
                } else {
                    setVerify(false);
                    ((Cliente) usuario).setCpf(emailOuCPF);
                    setCpf(emailOuCPF);
                }
            } while (emailOuCPF.isEmpty());

            do {
                System.out.print("Senha: ");
                setSenha(sc.nextLine());
                usuario.setSenha(getSenha());
            } while (getSenha().length() < 8);
            validar(usuario);
        }else {
            do {
                System.out.print("Digite seu Email ou CNPJ cadastrados: ");
                emailOuCPF = sc.nextLine();
                if (emailOuCPF.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")) {
                    usuario.setEmail(emailOuCPF);
                    setEmail(emailOuCPF);
                    setVerify(true);
                } else {
                    setVerify(false);
                    ((Fornecerdor) usuario).setCnjp(emailOuCPF);
                    setCpf(emailOuCPF);
                }
            } while (emailOuCPF.isBlank());

            do {
                System.out.print("Senha: ");
                setSenha(sc.nextLine());
                usuario.setSenha(getSenha());
            } while (getSenha().length() < 8);
            validar(usuario);
        }
    }
    public void validar(Usuario cliente){
        if (getChoose()){
            try {
                getCon();
                Connection ct = getConecxao();



                if (getVerify()){

                    setEmail(cliente.getEmail());
                    PreparedStatement ps = ct.prepareStatement("SELECT * FROM cliente WHERE email = ?");
                    ps.setString(1,cliente.getEmail());
                    System.out.println(cliente.getEmail());
                    ResultSet resultSet = ps.executeQuery();

                    while (resultSet.next()){
                        String emailtest = resultSet.getString("email");
                        String senhatest = resultSet.getString("senha");
                        setIdCliente(resultSet.getString("ID_cliente"));

                        cliente.setIdUser(resultSet.getString("ID_cliente"));
                        cliente.setNome(resultSet.getString("nome"));
                        ((Cliente) cliente).setCpf(resultSet.getString("CPF"));
                        ((Cliente) cliente).setSexo(resultSet.getString("Sexo"));
                        cliente.setTelefone(resultSet.getString("telefone"));
                        ((Cliente) cliente).setDataNacimento(resultSet.getString("data_nascimento"));
                        ((Cliente) cliente).setDataReg(resultSet.getString("data_de_registro"));
                        ((Cliente) cliente).setIdPerm(resultSet.getString("ID_perm"));


                        if (((Cliente) cliente).getIdPerm().equals("1") && emailtest.equalsIgnoreCase(cliente.getEmail()) && senhatest.equals(cliente.getSenha())) {
                            System.out.println("Seja bem vindo ADMINISTRADOR\n" +
                                    "REDIRECIONANDO PARA O PAINEL ADMINISTRADOR...."
                            );
                            App.mainframeADMIN();
                        }else if(emailtest.equalsIgnoreCase(getEmail()) && senhatest.equals(getSenha())){
                            System.out.println("LOGADO COM SUCESSO!\nRedirecionado para a pagina principal...");

                            getClienteDAO().setCliente(((Cliente)cliente));
                            getCarrinhoDAO().setUser(cliente);
                            getCarrinhoDAO().getId();
                            App.mainframeClient();

                        }else {
                            System.out.println("Email ou senha invalidos\n");
                        }
                    }


                }else {
                    setCpf(((Cliente)cliente).getCpf());
                    PreparedStatement ps = ct.prepareStatement("SELECT * FROM cliente WHERE CPF = ?");
                    ps.setString(1,getCpf());
                    ResultSet resultSet = ps.executeQuery();

                    while (resultSet.next()){
                        String cpftest = resultSet.getString("CPF");
                        String senhatest = resultSet.getString("senha");
                        setIdCliente(resultSet.getString("ID_cliente"));

                        cliente.setIdUser(resultSet.getString("ID_cliente"));
                        cliente.setNome(resultSet.getString("nome"));
                        cliente.setEmail(resultSet.getString("email"));
                        ((Cliente) cliente).setSexo(resultSet.getString("Sexo"));
                        cliente.setTelefone(resultSet.getString("telefone"));
                        ((Cliente) cliente).setDataNacimento(resultSet.getString("data_nascimento"));
                        ((Cliente) cliente).setDataReg(resultSet.getString("data_de_registro"));
                        ((Cliente) cliente).setIdPerm(resultSet.getString("ID_perm"));

                        if (((Cliente) cliente).getIdPerm().equals("1") && ((Cliente) cliente).getCpf().equals(cpftest) && cliente.getSenha().equals(senhatest)) {
                            System.out.println("Seja bem vindo ADMINISTRADOR\n" +
                                    "REDIRECIONANDO PARA O PAINEL ADMINISTRADOR....\n\n"
                            );
                            App.mainframeADMIN();
                        }else if (cpftest.equals(getCpf()) && senhatest.equals(getSenha())) {
                            System.out.println("LOGADO COM SUCESSO!\nRedirecionado para a pagina principal...");

                            getClienteDAO().setCliente(((Cliente)cliente));
                            getCarrinhoDAO().setUser(cliente);
                            getCarrinhoDAO().getId();
                            App.mainframeClient();

                        }else {
                            System.out.println("Cpf ou senha invalidos\n");
                        }
                    }
                }
                ct.close();
            }catch (SQLException e){
               System.out.println("erro ao validar o login: " + e);
            }
        }else {
            try {
                getCon();
                Connection ct = conecxao;

                if (getVerify()){

                    setEmail(cliente.getEmail());
                    PreparedStatement ps = ct.prepareStatement("SELECT * FROM fornecerdor WHERE email = ?");
                    ps.setString(1,cliente.getEmail());
                    ResultSet resultSet = ps.executeQuery();

                    while (resultSet.next()){
                        String emailtest = resultSet.getString("email");
                        String senhatest = resultSet.getString("senha");
                        cliente.setIdUser(resultSet.getString("id_fornecerdor"));
                        cliente.setNome(resultSet.getString("nome"));
                        ((Fornecerdor) cliente).setCnjp(resultSet.getString("CNPJ"));
                        cliente.setTelefone(resultSet.getString("Telefone"));
                        ((Fornecerdor) cliente).setDataReg(resultSet.getString("data_reg"));

                        if(emailtest.equalsIgnoreCase(getEmail()) && senhatest.equals(getSenha())){
                            System.out.println("LOGADO COM SUCESSO!\nRedirecionado para a pagina principal...");

                            getFornecerdorDAO().setFornecerdor(((Fornecerdor)cliente));
                            App.mainframeFornecerdor();

                        }else {
                            System.out.println("Email ou senha invalidos\n");
                        }
                    }


                }else {
                    setCpf(((Fornecerdor)cliente).getCnjp());
                    PreparedStatement ps = ct.prepareStatement("SELECT * FROM fornecerdor WHERE CNPJ = ?");
                    ps.setString(1,((Fornecerdor)cliente).getCnjp());
                    ResultSet resultSet = ps.executeQuery();

                    while (resultSet.next()){
                        String cpftest = resultSet.getString("CNPJ");
                        String senhatest = resultSet.getString("senha");
                        cliente.setIdUser(resultSet.getString("id_fornecerdor"));
                        cliente.setNome(resultSet.getString("nome"));
                        cliente.setEmail(resultSet.getString("Email"));
                        cliente.setTelefone(resultSet.getString("Telefone"));
                        ((Fornecerdor)cliente).setDataReg(resultSet.getString("data_reg"));

                        if (cpftest.equals(getCpf()) && senhatest.equals(getSenha())) {
                            System.out.println("LOGADO COM SUCESSO!\nRedirecionado para a pagina principal...");

                            getFornecerdorDAO().setFornecerdor((Fornecerdor) cliente);
                            App.mainframeFornecerdor();

                        }else {
                            System.out.println("CNPJ ou senha invalidos\n");
                        }
                    }
                }
                ct.close();
            }catch (SQLException e){
                System.out.println("erro ao validar o login: " + e);
            }
        }
    }

}
