package application.services;

import application.dao.ClienteDAO;
import application.dao.FornecerdorDAO;
import application.dao.ItemCarrinhoDAO;
import application.dao.ProdutoDAO;
import application.entidades.Carrinho;
import application.entidades.Produto;
import application.entidades.Usuario;

import java.util.ArrayList;
import java.util.Scanner;

public class ProdutoService {

    private Produto produto;
    private ProdutoDAO produtoDAO;
    private CategoriaService categoriaService;
    private ValidarLogin login;
    private Usuario user;
    private ArrayList list;
    private ClienteDAO clienteDAO;
    private FornecerdorDAO fornecerdorDAO;
    private ItemCarrinhoService itemCarrinhoService;
    private ItemCarrinhoDAO itemCarrinhoDAO;
    private Carrinho carrinho;

    public Produto getProduto() {
        return produto;
    }

    public ProdutoDAO getProdutoDAO() {
        return produtoDAO;
    }

    public CategoriaService getCategoriaService() {
        return categoriaService;
    }

    public ValidarLogin getLogin() {
        return login;
    }

    public Usuario getUser() {
        return user;
    }

    public ArrayList<String> getList() {
        return list;
    }

    public ClienteDAO getClienteDAO() {
        return clienteDAO;
    }

    public FornecerdorDAO getFornecerdorDAO() {
        return fornecerdorDAO;
    }

    public ItemCarrinhoService getItemCarrrinhoService() {
        return itemCarrinhoService;
    }

    public Carrinho getCarrinho() {
        return carrinho;
    }

    public void setItemCarrinhoDAO(ItemCarrinhoDAO itemCarrinhoDAO) {
        this.itemCarrinhoDAO = itemCarrinhoDAO;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public void setProdutoDAO(ProdutoDAO produtoDAO) {
        this.produtoDAO = produtoDAO;
    }

    public void setCategoriaService(CategoriaService categoriaService) {
        this.categoriaService = categoriaService;
    }

    public void setLogin(ValidarLogin login) {
        this.login = login;
    }

    public void setUser(Usuario user) {
        this.user = user;
    }

    public void setList(ArrayList<String> list) {
        this.list = list;
    }

    public void setClienteDAO(ClienteDAO clienteDAO) {
        this.clienteDAO = clienteDAO;
    }

    public void setFornecerdorDAO(FornecerdorDAO fornecerdorDAO) {
        this.fornecerdorDAO = fornecerdorDAO;
    }

    public ItemCarrinhoDAO getItemCarrinhoDAO() {
        return itemCarrinhoDAO;
    }

    public void setCarrinho(Carrinho carrinho) {
        this.carrinho = carrinho;
    }

    public void setItemCarrinhoService(ItemCarrinhoService itemCarrinhoService) {
        this.itemCarrinhoService = itemCarrinhoService;
    }

    public void register(){
        Scanner sc = new Scanner(System.in);

        boolean verify = false;

        System.out.println("insira seu produto que deseja vender: ");

        do {
            System.out.print("*NOME: ");
            getProduto().setNome(sc.nextLine());
        }while (!(getProduto().getNome().matches("^[A-Za-zÀ-ÿ0-9 ]{3,100}$")) || getProduto().getNome().length() > 100);

        System.out.print("DESCRICAO (Opcional): ");
        getProduto().setDescricao(sc.nextLine());
        if (getProduto().getDescricao().isEmpty() || getProduto().getDescricao().length() > 230){
            getProduto().setDescricao(null);
        }

        do {
            System.out.print("*SKU: ");
            getProduto().setsKUCodigoUnico(sc.nextLine());

            if (getProduto().getsKUCodigoUnico().length() > 30){
                System.out.println("O sku tem que ser menos de 30 caracteres");
            }
            if (!(getProduto().getsKUCodigoUnico().matches("^[A-Z0-9]+(-[A-Z0-9]+)*$"))){
                System.out.println("Ex: 'PRODUTO-MODELO-TIPO-+OPCIONAL.'");
            }
        }while (!(getProduto().getsKUCodigoUnico().matches("^[A-Z0-9]+(-[A-Z0-9]+)*$"))|| getProduto().getsKUCodigoUnico().length() > 30);

        do {
            try {
                System.out.print("*VALOR: ");
                getProduto().setValor(Double.valueOf(sc.nextLine()));

                if (getProduto().getValor() > 0){
                    getProduto().setValor(getProduto().getValor());
                    verify = false;
                }
            }catch (Exception e){
                System.out.println("Porfavor digite um decimal.");
                verify = true;
            }
        }while (verify);

        do {
            try {
                System.out.print("QUANTIDADE: ");
                getProduto().setQuantidade(Integer.valueOf(sc.nextLine()));

                if (getProduto().getQuantidade() > 0){
                    getProduto().setQuantidade(getProduto().getQuantidade());
                    verify = false;
                }
            }catch (Exception e){
                System.out.println("Por favor digite um numero inteiro positivo.");
                verify = true;
            }

        }while (verify);

        getProduto().setFkIdCat(Integer.parseInt(getCategoriaService().selectCatgegoria()));

        if (getLogin().getChoose()){
            getProduto().setFkIdCli(Integer.parseInt(getUser().getIdUser()));
        }else {
            getProduto().setFkIdFor(Integer.parseInt(getUser().getIdUser()));
        }

        getProdutoDAO().insert(getProduto());
    }
    public void listProduto(){
        ArrayList<String> list = new ArrayList<>();
        setList(list);

        getProdutoDAO().listProdutos(list);

        Scanner sc = new Scanner(System.in);
        boolean verify = false;
        String select;
        int index;

        System.out.println("\nProdutos\n");
        try {

            do {
                for (int i = 0; i < getList().size(); i++) {
                    String trimer = getList().get(i);
                    int first = trimer.indexOf("*");
                    int last = trimer.indexOf("]");

                    System.out.println(i + "-" + trimer.substring(first + 1, last));

                }
                System.out.println("\nZ-Sair.");
                System.out.print("\nSelecione: ");
                select = sc.nextLine();
                if (select.equalsIgnoreCase("z")) {
                    System.out.println("Voltando");
                    verify = false;
                } else if (select.matches("^\\d+$")) {
                    index = Integer.parseInt(select);
                    if (index > getList().size()) {
                        System.out.println("INVALIDO.");
                        verify = true;
                    }else {
                        String trimer = getList().get(index);
                        int first = trimer.indexOf("-");
                        getProduto().setIdProduto(trimer.substring(0,first));

                        first = trimer.indexOf("/");
                        int last = trimer.indexOf("<");
                        String test = trimer.substring(first + 1,last);
                        System.out.println(test);

                        if(!("null".equals(test))){
                            getProduto().setFkIdFor(Integer.parseInt(test));
                        }else {
                            first = trimer.indexOf("<");
                            last = trimer.indexOf(">");
                            getProduto().setFkIdCli(Integer.parseInt(trimer.substring(first + 1,last)));
                            System.out.println(getProduto().getFkIdCli());
                        }

                        selectProduto();
                    }

                }
            }while (verify);
        }catch (Exception e){
            System.out.println("Ocorreu um erro ao listar os produtos.\nERRO:" + e);
        }


    }
    public void selectProduto(){
        String categoria = getProdutoDAO().viewProduto(getProduto().getIdProduto());
        boolean verify;
        String select, nome;
        Integer id;

        Scanner sc = new Scanner(System.in);
        if (!(getProduto().getFkIdCli() == null)) {
            id = getProduto().getFkIdCli();
            getClienteDAO().search(id);
            nome = getClienteDAO().getResultQuery();
        }else {
            id = getProduto().getFkIdFor();
            getFornecerdorDAO().search(id);
            nome = getClienteDAO().getResultQuery();
        }

        do {
            System.out.println("\nProduto\n");
            System.out.println(
                    "NOME:.......| " + getProduto().getNome() +
                            "\nDESCRICAO:..| " + getProduto().getDescricao() +
                            "\nVALOR:......| " + getProduto().getValor() +
                            "\nQUANTIDADE:.| " + getProduto().getQuantidade() +
                            "\nCATEGORIA:..| " + categoria +
                            "\nVENDEDOR:...| " + nome
            );
            if (getProduto().getQuantidade() == 0) {
                System.out.println("\nProduto fora de estoque por favor volte outro momento.");
                verify = false;

            }else {
                System.out.print("\nDeseja comprar?\n" +
                        "\n1-Sim." +
                        "\n0-Nao." +
                        "\n\nSelecione: "
                );
                select = sc.nextLine();

                switch (select) {
                    case "1" -> {


                        String identifier = "baixa produto";
                        String sql;
                        if (!(nome.equals(getUser().getNome()))) {
                            if (getItemCarrrinhoService().verify(getProduto().getIdProduto())) {
                                getProduto().setQuantidade(getProduto().getQuantidade() - 1);
                                System.out.println("Item adicionado ao carrinho!");
                                System.out.println("Para finalizar a compra va em carrinho\ne finalize a compra");

                                sql = "UPDATE produto SET Qtd_no_Estoque = Qtd_no_Estoque -1 WHERE ID_Produto = " + getProduto().getIdProduto();
                                getProdutoDAO().update(sql, identifier);
                                sql = "UPDATE item_carrinho SET quantidade = quantidade + 1 WHERE fk_id_produto = " + getProduto().getIdProduto() + " AND fk_id_carrinho =?";
                                getItemCarrinhoDAO().update(sql);
                            } else {
                                getProduto().setQuantidade(getProduto().getQuantidade() - 1);
                                System.out.println("Item adicionado ao carrinho!");
                                System.out.println("Para finalizar a compra va em carrinho\ne finalize a compra");
                                sql = "UPDATE produto SET Qtd_no_Estoque = Qtd_no_Estoque - 1 WHERE ID_Produto = " + getProduto().getIdProduto();
                                getProdutoDAO().update(sql, identifier);
                                getItemCarrinhoDAO().insert(getProduto(), getCarrinho());
                            }
                            verify = false;
                        }else {
                            System.out.println("Voce nao pode comprar seu propio produto!!");
                            verify = true;
                        }
                    }

                    case "0" -> {
                        System.out.println("Voltando...");
                        verify = false;
                    }

                    default -> {
                        System.out.println("INVALIDO.");
                        verify = true;
                    }
                }
            }
        }while (verify);
    }
}
