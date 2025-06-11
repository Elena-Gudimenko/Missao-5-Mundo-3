package cadastroclient;

import javax.swing.*;

public class SaidaFrame extends JDialog {
    public JTextArea texto;

    public SaidaFrame() {
        super((JFrame) null, "Mensagens do Servidor", false);
        setBounds(100, 100, 400, 300);

        texto = new JTextArea();
        texto.setEditable(false);
        texto.setLineWrap(true);

        JScrollPane scroll = new JScrollPane(texto);
        getContentPane().add(scroll);

        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
    }
}
