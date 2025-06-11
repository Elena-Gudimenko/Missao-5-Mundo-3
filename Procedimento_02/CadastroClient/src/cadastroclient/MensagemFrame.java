package cadastroclient;

import javax.swing.*;

public class MensagemFrame extends JFrame {
    private JTextArea area;

    public MensagemFrame() {
        setTitle("Mensagens do Servidor");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        area = new JTextArea();
        area.setEditable(false);
        JScrollPane scroll = new JScrollPane(area);

        add(scroll);
        setVisible(true);
    }

    public void atualizarMensagem(String msg) {
        SwingUtilities.invokeLater(() -> {
            area.append(msg + "\n");
        });
    }
}

