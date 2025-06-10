package cadastroclient;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;
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

            // 3. Enviar login e senha (como objetos)
            out.writeObject("op1");
            out.writeObject("op1");
            out.flush();
            
            // 3.1.
            Object respostaLogin = in.readObject();
            if (respostaLogin == null) {
                System.out.println("Login ou senha inv?lidos. Encerrando.");
             return;
            }
            System.out.println("Login aceito.");

            // 4. Enviar comando "L"
            out.writeObject("L");
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
                    } else {
                        System.out.println("Objeto inv?lido na lista.");
                    }
                }
            } else {
                System.out.println("Resposta n?o ? uma lista.");
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
