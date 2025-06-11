package cadastroserver;

import controller.MovimentoJpaController;
import controller.PessoaJpaController;
import controller.ProdutoJpaController;
import controller.UsuarioJpaController;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class CadastroServidor {

    public static void main(String[] args) {
        try {
            // 1. Criar EntityManagerFactory
            EntityManagerFactory emf = Persistence.createEntityManagerFactory("CadastroServerPU");

            // 2. Criar controladores
            ProdutoJpaController ctrl = new ProdutoJpaController(emf);
            UsuarioJpaController ctrlUsu = new UsuarioJpaController(emf);
            MovimentoJpaController ctrlMov = new MovimentoJpaController(emf);
            PessoaJpaController ctrlPes = new PessoaJpaController(emf);

            // 3. Criar ServerSocket na porta 4321
            ServerSocket serverSocket = new ServerSocket(4321);
            System.out.println("Servidor escutando na porta 4321...");

            // 4. Loop infinito para aceitar conex?es
            while (true) {
                Socket clienteSocket = serverSocket.accept(); // espera conex?o
                System.out.println("Novo cliente conectado: " + clienteSocket.getInetAddress());

                // Criar nova thread para atender o cliente
                CadastroThreadV2 thread = new CadastroThreadV2(ctrl, ctrlUsu, ctrlMov, ctrlPes, clienteSocket);
                thread.start(); // iniciar o processamento
            }

        } catch (IOException e) {
            System.err.println("Erro no servidor: " + e.getMessage());
        }
    }
}
