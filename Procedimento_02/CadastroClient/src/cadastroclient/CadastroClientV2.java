package cadastroclient;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;
import javax.swing.SwingUtilities;
import model.Usuario;

public class CadastroClientV2 {

    public static void main(String[] args) {
        try (Scanner teclado = new Scanner(System.in)) {

            Socket s1 = new Socket("localhost", 4321);
            ObjectOutputStream saida = new ObjectOutputStream(s1.getOutputStream());
            ObjectInputStream entrada = new ObjectInputStream(s1.getInputStream());

            // Mostrar janela de sa?da
            SaidaFrame saidaFrame = new SaidaFrame();
            SwingUtilities.invokeLater(() -> saidaFrame.setVisible(true));

            // Iniciar thread de leitura
            ThreadClient thread = new ThreadClient(entrada, saidaFrame.texto);
            thread.start();

            // Autentica??o
            System.out.print("Login: ");
            String login = teclado.nextLine();
            saida.writeObject(login);

            System.out.print("Senha: ");
            String senha = teclado.nextLine();
            saida.writeObject(senha);

            // OBS: agora ждем объект do tipo String com resposta
            // ThreadClient покажет результат (login v?lido ou n?o)

            while (true) {
                System.out.print("\nComando (L=listar, E=entrada, S=saida, X=sair): ");
                String comando = teclado.nextLine().trim().toUpperCase();

                if (comando.isEmpty()) {
                    System.out.println("Comando nao pode ser vazio.");
                    continue;
                }

                saida.writeObject(comando);

                if (comando.equals("E") || comando.equals("S")) {
                    System.out.print("ID da pessoa: ");
                    saida.writeObject(Integer.parseInt(teclado.nextLine()));

                    System.out.print("ID do produto: ");
                    saida.writeObject(Integer.parseInt(teclado.nextLine()));

                    System.out.print("Quantidade: ");
                    saida.writeObject(Integer.parseInt(teclado.nextLine()));

                    System.out.print("Valor unitario: ");
                    saida.writeObject(Double.parseDouble(teclado.nextLine()));
                } else if (comando.equals("X")) {
                    System.out.println("Encerrando conexao...");
                    break;
                }
                // resposta теперь всегда будет exibаться на JTextArea!
            }

            s1.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
