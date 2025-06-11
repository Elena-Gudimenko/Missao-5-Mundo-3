package cadastroclient;

import javax.swing.*;
import java.io.ObjectInputStream;
import java.util.List;
import model.Produto;

public class ThreadClient extends Thread {
    private ObjectInputStream entrada;
    private JTextArea textArea;

    public ThreadClient(ObjectInputStream entrada, JTextArea textArea) {
        this.entrada = entrada;
        this.textArea = textArea;
    }

    @Override
    public void run() {
        try {
            while (true) {
                Object obj = entrada.readObject();

                if (obj instanceof String) {
                    String mensagem = (String) obj;
                    SwingUtilities.invokeLater(() -> textArea.append("Servidor: " + mensagem + "\n"));

                } else if (obj instanceof List<?>) {
                    List<?> lista = (List<?>) obj;

                    // Проверка: пустая ли коллекция
                    if (!lista.isEmpty() && lista.get(0) instanceof Produto) {
                        SwingUtilities.invokeLater(() -> {
                            textArea.append("Lista de produtos:\n");
                            for (Object item : lista) {
                                Produto p = (Produto) item;
                                textArea.append(" - " + p.getId() + " | " + p.getNome()
                                        + " | Quantidade: " + p.getQuantidade() + "\n");
                            }
                        });
                    }
                }
            }
        } catch (Exception e) {
            SwingUtilities.invokeLater(() -> textArea.append("Erro na leitura do servidor: " + e.getMessage() + "\n"));
        }
    }
}
