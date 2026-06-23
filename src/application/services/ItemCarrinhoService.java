package application.services;

import application.dao.DBConnetion;
import application.dao.ItemCarrinhoDAO;
import application.dao.PedidoDAO;
import application.dao.ProdutoDAO;
import application.entidades.ItemCarrinho;
import application.entidades.Pedido;
import application.entidades.Produto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Scanner;

public class ItemCarrinhoService extends DBConnetion {
    private ItemCarrinho itemCarrinho;
    private ItemCarrinhoDAO itemCarrinhoDAO;
    private ProdutoDAO produtoDAO;
    private Produto produto;
    private ArrayList<String> list;
    private Pedido pedido;

    public Pedido getPedido() {
        return pedido;
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }

    public ItemCarrinho getItemCarrinho() {
        return itemCarrinho;
    }

    public ProdutoDAO getProdutoDAO() {
        return produtoDAO;
    }

    public Produto getProduto() {
        return produto;
    }

    public ItemCarrinhoDAO getItemCarrinhoDAO() {
        return itemCarrinhoDAO;
    }

    public ArrayList<String> getList() {
        return list;
    }

    public void setItemCarrinho(ItemCarrinho itemCarrinho) {
        this.itemCarrinho = itemCarrinho;
    }

    public void setItemCarrinhoDAO(ItemCarrinhoDAO itemCarrinhoDAO) {
        this.itemCarrinhoDAO = itemCarrinhoDAO;
    }

    public void setProdutoDAO(ProdutoDAO produtoDAO) {
        this.produtoDAO = produtoDAO;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public void setList(ArrayList<String> list) {
        this.list = list;
    }

    public Boolean verify(String produto){

        if(
                getItemCarrinho().getFkCarrinho() == null ||
                getItemCarrinho().getIdItemCarrinho() == null ||
                getItemCarrinho().getFkProduto()== null ||
                getItemCarrinho().getQuantidade() == null ||
                getItemCarrinho().getPrecoUnitario() == null
        ){
            getItemCarrinhoDAO().getVar();
        }


        if (!(getItemCarrinho().getFkCarrinho() == null)){
            System.out.println("false");
            if (getItemCarrinho().getFkProduto().equals(produto)){
                System.out.println("true");
                return true;

            }else System.out.println("false"); return false;
        }else System.out.println("false"); return false;


    }

    public boolean verifyifnull(){
        getItemCarrinhoDAO().getVar2();
        if(
                getItemCarrinho().getFkCarrinho() == null ||
                        getItemCarrinho().getIdItemCarrinho() == null ||
                        getItemCarrinho().getFkProduto()== null ||
                        getItemCarrinho().getQuantidade() == null ||
                        getItemCarrinho().getPrecoUnitario() == null
        ){
            System.out.println(getItemCarrinho().getFkCarrinho());
            System.out.println(getItemCarrinho().getIdItemCarrinho());
            System.out.println(getItemCarrinho().getFkProduto());
            System.out.println(getItemCarrinho().getQuantidade());
            System.out.println(getItemCarrinho().getPrecoUnitario());
            System.out.println("Voce nao tem nada ainda no carrinho.\n\nAdicione algo!!");
            return false;
        }else return true;

    }

    public void listItems(){
        Scanner sc = new Scanner(System.in);
        String select;
        boolean verify =false;

        if (verifyifnull()) {
            ArrayList<String> list = new ArrayList<>();
            ArrayList<String> list1 = new ArrayList<>();
            ArrayList<String> list3 = new ArrayList<>();
            ArrayList<ArrayList<String>> list2 = new ArrayList<>();

            getItemCarrinhoDAO().listItemsCarrinho(list);

            for (int i = 0; getList().size() > i; i++) {
                String trimer = getList().get(i);

                int first = trimer.indexOf(";");
                list1.add(trimer.substring(first + 1) + " or ID_produto =");
            }
            int index = list1.size() - 1;
            String orRemover = list1.get(index);

            list1.remove(index);
            String put = orRemover.substring(0, orRemover.lastIndexOf(" or ID_produto ="));
            System.out.println(put);
            list1.add(put);
            list2.add(list1);
            String test = list2.get(0).toString();
            String replace;
            String replace2;
            String finalsub;
            replace = test.replace("[", "");
            replace2 = replace.replace("]", "");
            finalsub = replace2.replace(",", "");
            System.out.println(finalsub);

            System.out.println(list);
            list3 = getProdutoDAO().itemCarrinhoProduto(finalsub, list3);
            System.out.println(list3);
            System.out.println(list);
            do {


                for (int i = 0; list3.size() > i; i++) {
                    String trim = list3.get(i);
                    String trim2 = list.get(i);
                    int first = trim.indexOf("-");
                    int last = trim.indexOf(";");
                    int last2 = trim.indexOf(";");
                    System.out.println(i + "-" + trim.substring(first + 1, last) + " QUANTIDADE: " + trim.substring(0, first));
                }
                System.out.println("N-Remover | S-Finalizar Compra | Z-Voltar");
                select = sc.nextLine();

                if (select.matches("^[A-Za-z]+$")) {
                    if (select.equalsIgnoreCase("N")) {
                        switch (select) {
                            case "N" -> {
                                do {
                                    System.out.print("\nDeseja Remover quantos?\n" +
                                            "\n1-Um ou mais" +
                                            "\n2-Tudo" +
                                            "\n0-Nenhum" +
                                            "\n\nSelecione: "
                                    );
                                    select = sc.nextLine();
                                    switch (select) {
                                        case "1" -> {
                                            do {
                                                System.out.println("Qual deseja remover?");
                                                try {

                                                    System.out.print("|:");
                                                    int check = Integer.parseInt(sc.nextLine());
                                                    if ((check >= list3.size() || check < 0)) {
                                                        System.out.println("Porfavor digite um numero disponivel");
                                                        verify = true;
                                                    } else {
                                                        switchItemCarrinho(check, list3, list);
                                                        verify = false;
                                                    }
                                                } catch (Exception e) {
                                                    System.out.println("INVALIDO.");
                                                    verify = true;
                                                }
                                            } while (verify);

                                        }

                                        case "2" -> {

                                            do {
                                                System.out.println("Qual deseja remover?");
                                                try {

                                                    System.out.print("|: ");
                                                    int check = Integer.parseInt(sc.nextLine());
                                                    if ((check >= list3.size() || check < 0)) {
                                                        System.out.println("Porfavor digite um numero disponivel");
                                                        verify = true;
                                                    } else {
                                                        remove(check, list3);
                                                        verify = false;
                                                    }
                                                    verify = false;
                                                } catch (Exception e) {
                                                    System.out.println("INVALIDO.");
                                                    verify = true;
                                                }
                                            } while (verify);


                                        }

                                        case "0" -> {
                                            System.out.println("Voltando...");
                                            verify = false;
                                        }
                                        default -> {
                                            System.out.println("INVALIDO");
                                            verify = true;
                                        }
                                    }

                                } while (verify);


                            }

                            case "S" -> {



                                System.out.println("Seu pedido foi feito!");
                                System.out.println("Falta apenas confirmar!");
                                System.out.println("va em menu -> pedidos e confirme!");


                            }

                            case "Z" -> {
                                System.out.println("Voltando...");
                            }

                            default -> System.out.println("INVALIDO.");
                        }
                    }
                } else if (select.equalsIgnoreCase("Z")) {
                    System.out.println("Voltando...");
                    verify = false;
                } else if (select.equalsIgnoreCase("S")) {
                    System.out.println("Finzaliando compra..");
                    System.out.println("Olhe no Pedido para finalizar a compra");
                    verify = false;
                } else {
                    System.out.println("INVALIDO.");
                    verify = true;
                }
            } while (verify);
        }
    }

    public void switchItemCarrinho(Integer option, ArrayList<String> list, ArrayList<String> list2){
        Scanner sc = new Scanner(System.in);

        boolean verify = false;
        do {
            try {
                String sql;
                String string = list.get(option);
                System.out.println(string);
                int doc = string.indexOf("-");
                String id = string.substring(0,doc);
                System.out.println(id);
                System.out.println(getItemCarrinhoDAO().getCarrinho().getIdCarrinho());
                getProduto().setIdProduto(id);
                getItemCarrinhoDAO().getqtditem(id);
                getProdutoDAO().search(id);
                for (int i = 0; list2.size() > i;i++ ){
                    string = list.get(i);
                    int first = string.indexOf("-");
                    String test = string.substring(0,first);
                    if (getProduto().getIdProduto().equals(test)){
                        System.out.println("Produto: " +
                                "\nNome:.......| " + getProduto().getNome() +
                                "\nquantidade:.| " + getItemCarrinho().getQuantidade()
                        );
                        break;
                    }
                }



                System.out.println("Digite quantos quer remover.");
                System.out.print(": ");
                int qtd = Integer.parseInt(sc.nextLine());
                if(qtd > getItemCarrinho().getQuantidade() || qtd < 0) {
                    System.out.println("Porfavor digite um numero que nao seja acima da qtd ou abaixo.");
                    verify = true;
                }else if (0 == qtd - getItemCarrinho().getQuantidade()){
                    sql ="DELETE FROM item_carrinho WHERE fk_id_produto =? AND fk_id_carrinho =?";
                    if(getItemCarrinhoDAO().delete(sql,id)){
                        sql = "UPDATE produto SET Qtd_no_Estoque = Qtd_no_estoque +"+ qtd +" WHERE ID_produto = "+getProduto().getIdProduto();
                        String sql2 = sql;
                        System.out.println(sql2);
                        String identifier = "Remocao de item do carrinho total.";
                        getProdutoDAO().update(sql2,identifier);
                    }
                }else {
                    sql = "UPDATE item_carrinho SET quantidade = quantidade -"+ qtd +" WHERE fk_id_produto = "+getProduto().getIdProduto()+" AND fk_id_carrinho =?";
                    getItemCarrinhoDAO().update(sql);
                }
            } catch (Exception e) {
                System.out.println("Digite um numero inteiro.");

            }
        }while (verify);
    }
    public void remove(Integer check, ArrayList<String> list){
        String sql = "DELETE FROM item_carrinho WHERE fk_id_produto =? AND fk_id_carrinho =?";
        String string = list.get(check);
        int doc = string.indexOf(";");
        String id = string.substring(doc +1);
        getProduto().setIdProduto(id);


        getItemCarrinhoDAO().delete(sql,id);
    }
    public void finalizarCompra() {
        try {
            double total = Double.parseDouble(getPedido().getValorFinal());

            pedido.setValorFinal(String.valueOf(total));
            pedido.setStatusPedio("Esperando pagamento.");
            pedido.setDataDoPedido(java.time.LocalDate.now().toString());
            pedido.setFkIdCarrinho(itemCarrinhoDAO.getCarrinho().getIdCarrinho());

            // Pega o ID do cliente do carrinho
            if (itemCarrinhoDAO.getCarrinho() != null) {
                // Buscar o cliente pelo carrinho
                Connection ct = getConecxao();
                PreparedStatement ps = ct.prepareStatement(
                        "SELECT FK_cli FROM carrinho WHERE ID_carrinho = ?"
                );
                ps.setString(1, itemCarrinhoDAO.getCarrinho().getIdCarrinho());
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    pedido.setFkIdCliente(rs.getString("FK_cli"));
                }
                ct.close();
            }

            PedidoDAO pedidoDAO = new PedidoDAO();
            pedidoDAO.setPedido(pedido);
            pedidoDAO.insert();

            System.out.println("Total do pedido: R$ " + String.format("%.2f", total));

        } catch (Exception e) {
            System.out.println("Erro ao finalizar compra: " + e);
            e.printStackTrace();
        }
    }
}
