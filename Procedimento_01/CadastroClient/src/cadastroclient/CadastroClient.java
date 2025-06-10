package cadastroclient;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;
import java.util.Scanner;
import model.Produto;

public class CadastroClient {

    public static void main(String[] args) {
        Socket socket = null;
        ObjectOutputStream out = null;
        ObjectInputStream in = null;

        try {
            // 1. Conectar ao servidor (localhost:4321)
            socket = new Socket("localhost", 4321);
            System.out.println("Conectado ao servidor.");

            // 2. Criar streams de entrada/sa?da
            out = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());

            // 3. Escrever login e senha (de acordo com o banco: por ex., op1/op1)
            out.writeUTF("op1");
            out.writeUTF("op1");
            out.flush();

            // 4. Enviar comando "L" (listar produtos)
            out.writeUTF("L");
            out.flush();

            // 5. Receber lista de produtos
            Object obj = in.readObject();
            if (obj instanceof List<?>) {
                List<?> lista = (List<?>) obj;
                System.out.println("\nProdutos recebidos do servidor:");

                for (Object item : lista) {
                    if (item instanceof Produto) {
                        Produto p = (Produto) item;
                        System.out.println(" - " + p.getNome());
                    }
                }
            }

        } catch (Exception e) {
            System.err.println("Erro no cliente: " + e.getMessage());
            e.printStackTrace();
        } finally {
            // 6. Fechar conex?es
            try {
                if (in != null) in.close();
                if (out != null) out.close();
                if (socket != null) socket.close();
                System.out.println("\nConex?o encerrada.");
            } catch (Exception e) {
                System.err.println("Erro ao fechar conex?o: " + e.getMessage());
            }
        }
    }
}
