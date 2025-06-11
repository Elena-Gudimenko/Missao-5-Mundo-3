package cadastroserver;

import controller.ProdutoJpaController;
import controller.UsuarioJpaController;
import controller.MovimentoJpaController;
import controller.PessoaJpaController;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;
import model.Movimento;
import model.Produto;
import model.Usuario;
import model.Pessoa;

public class CadastroThreadV2 extends Thread {

    private ProdutoJpaController ctrlProd;
    private UsuarioJpaController ctrlUsu;
    private MovimentoJpaController ctrlMov;
    private PessoaJpaController ctrlPessoa;
    private Socket s1;

    public CadastroThreadV2(
        ProdutoJpaController ctrlProd,
        UsuarioJpaController ctrlUsu,
        MovimentoJpaController ctrlMov,
        PessoaJpaController ctrlPessoa,
        Socket s1
    ) {
        this.ctrlProd = ctrlProd;
        this.ctrlUsu = ctrlUsu;
        this.ctrlMov = ctrlMov;
        this.ctrlPessoa = ctrlPessoa;
        this.s1 = s1;
    }
/**
    CadastroThreadV2(ProdutoJpaController ctrl, UsuarioJpaController ctrlUsu, Socket clienteSocket) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
**/

    @Override
    public void run() {
        ObjectOutputStream saida = null;
        ObjectInputStream entrada = null;

        try {
            saida = new ObjectOutputStream(s1.getOutputStream());
            entrada = new ObjectInputStream(s1.getInputStream());

            String login = (String) entrada.readObject();
            String senha = (String) entrada.readObject();

            Usuario usuario = ctrlUsu.findUsuario(login, senha);
            if (usuario == null) {
                saida.writeObject(null);
                s1.close();
                return;
            }

            saida.writeObject(usuario);

            while (true) {
                String comando = (String) entrada.readObject();

                if (comando.equals("L")) {
                    List<Produto> produtos = ctrlProd.findProdutoEntities();
                    saida.writeObject(produtos);

                } else if (comando.equals("E") || comando.equals("S")) {
                    Movimento mov = new Movimento();
                    mov.setTipo(comando);
                    mov.setUsuario(usuario);

                    int idPessoa = (Integer) entrada.readObject();
                    int idProduto = (Integer) entrada.readObject();
                    int quantidade = (Integer) entrada.readObject();
                    double valor = (Double) entrada.readObject();

                    Pessoa pessoa = ctrlPessoa.findPessoa(idPessoa);
                    Produto produto = ctrlProd.findProduto(idProduto);
                    

                    if (pessoa == null || produto == null) {
                        saida.writeObject("Erro: Pessoa ou Produto nao encontrado.");
                        continue;
                    }

                    int novaQuantidade = comando.equals("E") ?
                        produto.getQuantidade() + quantidade :
                        produto.getQuantidade() - quantidade;

                    if (novaQuantidade < 0) {
                        saida.writeObject("Erro: Quantidade insuficiente no estoque.");
                        continue;
                    }

                    mov.setPessoa(pessoa);
                    mov.setProduto(produto);
                    mov.setQuantidade(quantidade);
                    mov.setValorUnitario(valor);

                    ctrlMov.create(mov);

                    produto.setQuantidade(novaQuantidade);
                    ctrlProd.edit(produto);

                    saida.writeObject("Movimento registrado com sucesso.");

                } else if (comando.equals("FIM")) {
                    break;

                } else {
                    saida.writeObject("Comando desconhecido.");
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            try {
                if (saida != null) {
                    saida.writeObject("Erro interno no servidor: " + e.getMessage());
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } finally {
            try {
                if (entrada != null) entrada.close();
                if (saida != null) saida.close();
                if (s1 != null && !s1.isClosed()) s1.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
}
