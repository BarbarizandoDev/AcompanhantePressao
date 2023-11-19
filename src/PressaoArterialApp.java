import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class PressaoArterialApp extends JFrame {

    private JTextField txtData, txtHora, txtSistolica, txtDiastolica;
    private JCheckBox chkEstresse;
    private JButton btnSalvar;
    private JTable tblHistorico;
    private DefaultTableModel tableModel;
    private JTextArea txtAreaHistorico;

    public PressaoArterialApp() {
        initComponents();
    }

    private void initComponents() {
        setTitle("Registro de Pressão Arterial");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);

        // Labels
        JLabel lblData = new JLabel("Data:");
        lblData.setBounds(10, 10, 80, 25);
        add(lblData);

        JLabel lblHora = new JLabel("Hora:");
        lblHora.setBounds(10, 40, 80, 25);
        add(lblHora);

        JLabel lblSistolica = new JLabel("Pressão Sistólica:");
        lblSistolica.setBounds(10, 70, 120, 25);
        add(lblSistolica);

        JLabel lblDiastolica = new JLabel("Pressão Diastólica:");
        lblDiastolica.setBounds(10, 100, 120, 25);
        add(lblDiastolica);

        // TextFields
        txtData = new JTextField();
        txtData.setBounds(150, 10, 150, 25);
        add(txtData);

        txtHora = new JTextField();
        txtHora.setBounds(150, 40, 150, 25);
        add(txtHora);

        txtSistolica = new JTextField();
        txtSistolica.setBounds(150, 70, 150, 25);
        add(txtSistolica);

        txtDiastolica = new JTextField();
        txtDiastolica.setBounds(150, 100, 150, 25);
        add(txtDiastolica);

        // CheckBox
        chkEstresse = new JCheckBox("Estresse");
        chkEstresse.setBounds(150, 130, 100, 25);
        add(chkEstresse);

        // Button
        btnSalvar = new JButton("Salvar");
        btnSalvar.setBounds(10, 160, 80, 25);
        btnSalvar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                salvarMedicao();
            }
        });
        add(btnSalvar);

        // Table
        tableModel = new DefaultTableModel();
        tableModel.addColumn("Data");
        tableModel.addColumn("Hora");
        tableModel.addColumn("Sistólica");
        tableModel.addColumn("Diastólica");
        tableModel.addColumn("Estresse");

        tblHistorico = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(tblHistorico);
        scrollPane.setBounds(10, 200, 500, 150);
        add(scrollPane);

        // TextArea
        txtAreaHistorico = new JTextArea();
        txtAreaHistorico.setBounds(10, 360, 500, 150);
        add(txtAreaHistorico);

        // Set focus traversal keys
        setFocusTraversalKeysEnabled(false);
        setFocusTraversalKeys(
                java.awt.KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS, java.util.Collections.EMPTY_SET);
        setFocusTraversalKeys(
                java.awt.KeyboardFocusManager.BACKWARD_TRAVERSAL_KEYS, java.util.Collections.EMPTY_SET);

        // Set initial focus
        txtData.requestFocus();
    }

    private void salvarMedicao() {
        try {
            // Validar dados
            String data = txtData.getText();
            String hora = txtHora.getText();
            int sistolica = Integer.parseInt(txtSistolica.getText());
            int diastolica = Integer.parseInt(txtDiastolica.getText());
            boolean estresse = chkEstresse.isSelected();

            // Adicionar dados à tabela
            tableModel.addRow(new Object[]{data, hora, sistolica, diastolica, estresse});

            // Adicionar dados à área de texto
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            String medicaoStr = String.format("%s %s - Sistólica: %d, Diastólica: %d, Estresse: %b\n",
                    data, hora, sistolica, diastolica, estresse);
            txtAreaHistorico.append(medicaoStr);

            // Salvar em arquivo (opcional)
            salvarEmArquivo(data, hora, sistolica, diastolica, estresse);

            // Limpar campos
            limparCampos();
        } catch (NumberFormatException ex) {
            exibirMensagemErro("Os campos de pressão devem conter apenas números inteiros.");
        } catch (Exception ex) {
            ex.printStackTrace();
            exibirMensagemErro("Ocorreu um erro ao salvar a medição.");
        }
    }

    private void salvarEmArquivo(String data, String hora, int sistolica, int diastolica, boolean estresse) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("historico_pressao.txt", true))) {
            writer.write(String.format("%s %s %d %d %b%n", data, hora, sistolica, diastolica, estresse));
        } catch (IOException e) {
            e.printStackTrace();
            exibirMensagemErro("Erro ao salvar no arquivo.");
        }
    }

    private void limparCampos() {
        txtData.setText("");
        txtHora.setText("");
        txtSistolica.setText("");
        txtDiastolica.setText("");
        chkEstresse.setSelected(false);
    }

    private void exibirMensagemErro(String mensagem) {
        // Implemente a lógica para exibir mensagens de erro de forma amigável
        System.out.println("Erro: " + mensagem);
    }

    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(() -> {
            new PressaoArterialApp().setVisible(true);
        });
    }
}
