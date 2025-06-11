package cadastroclient;

import java.io.ObjectInputStream;
import javax.swing.JTextArea;

public class ThreadLeitura implements Runnable {

    private ObjectInputStream entrada;
    private JTextArea texto;

    public ThreadLeitura(ObjectInputStream entrada, JTextArea texto) {
        this.entrada = entrada;
        this.texto = texto;
    }

    @Override
    public void run() {
        try {
            while (true) {
                Object obj = entrada.readObject();
                if (obj instanceof String) {
                    String mensagem = (String) obj;
                    texto.append(mensagem + "\n");
                }
                // Можно добавить обработку других типов, если понадобится
            }
        } catch (Exception e) {
            texto.append("Erro na leitura do servidor: " + e.getMessage() + "\n");
        }
    }
}
