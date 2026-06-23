package application.dao;

import application.entidades.Carrinho;
import application.entidades.Pedido;
import application.entidades.Usuario;
import application.services.PedidoService;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class PedidoDAO extends DBConnetion {

    private Pedido pedido;
    private PedidoService pedidoService;
    private Usuario user;
    private Carrinho carrinho;
    private ArrayList<String> list;

    // Getters e Setters
    public Pedido getPedido() {
        return pedido;
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }

    public PedidoService getPedidoService() {
        return pedidoService;
    }

    public void setPedidoService(PedidoService pedidoService) {
        this.pedidoService = pedidoService;
    }

    public Usuario getUser() {
        return user;
    }

    public void setUser(Usuario user) {
        this.user = user;
    }

    public Carrinho getCarrinho() {
        return carrinho;
    }

    public void setCarrinho(Carrinho carrinho) {
        this.carrinho = carrinho;
    }

    public ArrayList<String> getList() {
        return list;
    }

    public void setList(ArrayList<String> list) {
        this.list = list;
    }

    // Inserir novo pedido
    public void insert() {
        try {
            getCon();
            Connection ct = getConecxao();

            // Baseado na sua tabela pedido:
            // ID_pedido (auto_increment), stau_ped, valor_final, md_pag, date_ped, fk_car, fk_cli
            PreparedStatement ps = ct.prepareStatement(
                    "INSERT INTO pedido (stau_ped, valor_final, md_pag, date_ped, fk_car, fk_cli) " +
                            "VALUES (?, ?, ?, ?, ?, ?)"
            );

            ps.setString(1, pedido.getStatusPedio() != null ? pedido.getStatusPedio() : "Esperando pagamento.");
            ps.setFloat(2, pedido.getValorFinal() != null ? Float.parseFloat(pedido.getValorFinal()) : 0);
            ps.setString(3, pedido.getModoDePagamaento()); // Pode ser null por enquanto
            ps.setString(4, pedido.getDataDoPedido() != null ? pedido.getDataDoPedido() : java.time.LocalDate.now().toString());
            ps.setInt(5, Integer.parseInt(pedido.getFkIdCarrinho()));
            ps.setInt(6, Integer.parseInt(pedido.getFkIdCliente()));

            int result = ps.executeUpdate();
            System.out.println("Pedido realizado com sucesso! Linhas afetadas: " + result);
            ct.close();

        } catch (Exception e) {
            System.out.println("Erro ao inserir pedido: " + e);
            e.printStackTrace();
        }
    }

    // Listar pedidos de um cliente
    public void listPedidosByCliente(String idCliente) {
        try {
            getCon();
            Connection ct = getConecxao();

            PreparedStatement ps = ct.prepareStatement(
                    "SELECT * FROM pedido WHERE fk_cli = ? ORDER BY ID_pedido DESC"
            );
            ps.setString(1, idCliente);
            ResultSet rs = ps.executeQuery();

            ArrayList<String> pedidos = new ArrayList<>();

            while (rs.next()) {
                String pedidoInfo =
                        rs.getString("ID_pedido") + ";" +
                                rs.getString("stau_ped") + ";" +
                                rs.getString("valor_final") + ";" +
                                rs.getString("md_pag") + ";" +
                                rs.getString("date_ped") + ";" +
                                rs.getString("fk_car");
                pedidos.add(pedidoInfo);
            }

            setList(pedidos);
            ct.close();

        } catch (Exception e) {
            System.out.println("Erro ao listar pedidos: " + e);
        }
    }

    // Buscar pedido por ID
    public Pedido buscarPedidoPorId(String idPedido) {
        Pedido pedidoEncontrado = null;

        try {
            getCon();
            Connection ct = getConecxao();

            PreparedStatement ps = ct.prepareStatement(
                    "SELECT * FROM pedido WHERE ID_pedido = ?"
            );
            ps.setString(1, idPedido);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                pedidoEncontrado = new Pedido();
                pedidoEncontrado.setIdPedido(rs.getString("ID_pedido"));
                pedidoEncontrado.setStatusPedio(rs.getString("stau_ped"));
                pedidoEncontrado.setValorFinal(String.valueOf(rs.getFloat("valor_final")));
                pedidoEncontrado.setModoDePagamaento(rs.getString("md_pag"));
                pedidoEncontrado.setDataDoPedido(rs.getString("date_ped"));
                pedidoEncontrado.setFkIdCarrinho(rs.getString("fk_car"));
                pedidoEncontrado.setFkIdCliente(rs.getString("fk_cli"));
            }

            ct.close();

        } catch (Exception e) {
            System.out.println("Erro ao buscar pedido: " + e);
        }

        return pedidoEncontrado;
    }

    // Atualizar status do pedido
    public void updateStatus(String idPedido, String novoStatus) {
        try {
            getCon();
            Connection ct = getConecxao();

            PreparedStatement ps = ct.prepareStatement(
                    "UPDATE pedido SET stau_ped = ? WHERE ID_pedido = ?"
            );
            ps.setString(1, novoStatus);
            ps.setString(2, idPedido);

            int result = ps.executeUpdate();
            System.out.println("Status atualizado! Linhas afetadas: " + result);
            ct.close();

        } catch (Exception e) {
            System.out.println("Erro ao atualizar status do pedido: " + e);
        }
    }

    // Atualizar método de pagamento
    public void updatePagamento(String idPedido, String metodoPagamento) {
        try {
            getCon();
            Connection ct = getConecxao();

            PreparedStatement ps = ct.prepareStatement(
                    "UPDATE pedido SET md_pag = ? WHERE ID_pedido = ?"
            );
            ps.setString(1, metodoPagamento);
            ps.setString(2, idPedido);

            int result = ps.executeUpdate();
            System.out.println("Método de pagamento atualizado! Linhas afetadas: " + result);
            ct.close();

        } catch (Exception e) {
            System.out.println("Erro ao atualizar pagamento: " + e);
        }
    }

    // Cancelar pedido (deletar)
    public void cancelarPedido(String idPedido) {
        try {
            getCon();
            Connection ct = getConecxao();

            PreparedStatement ps = ct.prepareStatement(
                    "DELETE FROM pedido WHERE ID_pedido = ?"
            );
            ps.setString(1, idPedido);

            int result = ps.executeUpdate();
            System.out.println("Pedido cancelado! Linhas afetadas: " + result);
            ct.close();

        } catch (Exception e) {
            System.out.println("Erro ao cancelar pedido: " + e);
        }
    }
}