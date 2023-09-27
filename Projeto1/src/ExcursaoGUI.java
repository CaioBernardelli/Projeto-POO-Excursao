import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class ExcursaoGUI {
    private Excursao excursao;
    private JTextArea listaReservasArea;

    public ExcursaoGUI() {
        JFrame frame = new JFrame("Sistema de Excursões");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 500);

        JPanel panel = new JPanel();
        frame.add(panel);
        placeComponents(panel);

        frame.setVisible(true);
    }
    
    private void placeComponents(JPanel panel) {
        panel.setLayout(null);

        JButton criarExcursaoButton = new JButton("Criar Excursão");
        criarExcursaoButton.setBounds(10, 20, 150, 25);
        panel.add(criarExcursaoButton);

        JButton recuperarExcursaoButton = new JButton("Recuperar Excursão");
        recuperarExcursaoButton.setBounds(10, 60, 150, 25);
        panel.add(recuperarExcursaoButton);

        JButton criarReservaButton = new JButton("Criar Reserva");
        criarReservaButton.setBounds(10, 100, 150, 25);
        panel.add(criarReservaButton);

        JButton cancelarReservaIndividualButton = new JButton("Cancelar Reserva Individual");
        cancelarReservaIndividualButton.setBounds(10, 140, 200, 25);
        panel.add(cancelarReservaIndividualButton);

        JButton cancelarReservaGrupoButton = new JButton("Cancelar Reserva em Grupo");
        cancelarReservaGrupoButton.setBounds(10, 180, 200, 25);
        panel.add(cancelarReservaGrupoButton);

        JButton listarPorCpfButton = new JButton("Listar por CPF");
        listarPorCpfButton.setBounds(10, 220, 150, 25);
        panel.add(listarPorCpfButton);

        JButton listarPorNomeButton = new JButton("Listar por Nome");
        listarPorNomeButton.setBounds(10, 260, 150, 25);
        panel.add(listarPorNomeButton);

        // Adicionando uma JTextArea para listar as reservas
        listaReservasArea = new JTextArea();
        listaReservasArea.setBounds(220, 20, 220, 380);
        listaReservasArea.setEditable(false);
        panel.add(listaReservasArea);

        JButton calcularTotalButton = new JButton("Calcular Total");
        calcularTotalButton.setBounds(10, 300, 150, 25);
        panel.add(calcularTotalButton);

        JButton salvarEmArquivoButton = new JButton("Salvar em Arquivo");
        salvarEmArquivoButton.setBounds(10, 340, 150, 25);
        panel.add(salvarEmArquivoButton);

        JButton carregarDeArquivoButton = new JButton("Carregar de Arquivo");
        carregarDeArquivoButton.setBounds(10, 380, 150, 25);
        panel.add(carregarDeArquivoButton);

        // ... outros códigos dos botões

        criarExcursaoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int codigo = Integer.parseInt(JOptionPane.showInputDialog("Digite o código da excursão:"));
                double preco = Double.parseDouble(JOptionPane.showInputDialog("Digite o preço da excursão:"));
                int max = Integer.parseInt(JOptionPane.showInputDialog("Digite o número máximo de reservas:"));
                excursao = new Excursao(codigo, preco, max);
                JOptionPane.showMessageDialog(null, "Excursão criada com sucesso.");
            }
        });

        recuperarExcursaoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int codigo = Integer.parseInt(JOptionPane.showInputDialog("Digite o código da excursão:"));
                excursao = new Excursao(codigo);
                excursao.carregar();
                JOptionPane.showMessageDialog(null, "Excursão recuperada com sucesso:\n" + excursao.toString());
            }
        });

        criarReservaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String cpf = JOptionPane.showInputDialog("Digite o CPF:");
                String nome = JOptionPane.showInputDialog("Digite o nome:");
                try {
                    excursao.criarReserva(cpf, nome);
                    JOptionPane.showMessageDialog(null, "Reserva criada com sucesso.");
                } catch (IllegalArgumentException ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage());
                }
            }
        });

        cancelarReservaIndividualButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String cpf = JOptionPane.showInputDialog("Digite o CPF:");
                String nome = JOptionPane.showInputDialog("Digite o nome:");
                try {
                    excursao.cancelarReserva(cpf, nome);
                    JOptionPane.showMessageDialog(null, "Reserva cancelada com sucesso.");
                } catch (IllegalArgumentException ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage());
                }
            }
        });

        cancelarReservaGrupoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String cpf = JOptionPane.showInputDialog("Digite o CPF:");
                try {
                    excursao.cancelarReserva(cpf);
                    JOptionPane.showMessageDialog(null, "Todas as reservas do CPF foram canceladas com sucesso.");
                } catch (IllegalArgumentException ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage());
                }
            }
        });

        listarPorCpfButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String digitos = JOptionPane.showInputDialog("Digite os dígitos do CPF:");
                List<String> reservas = excursao.listarReservasPorCpf(digitos);
                exibirReservas(reservas);
            }
        });

        listarPorNomeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String texto = JOptionPane.showInputDialog("Digite o texto do nome:");
                List<String> reservas = excursao.listarReservasPorNome(texto);
                exibirReservas(reservas);
            }
        });

        salvarEmArquivoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (excursao != null) {
                    excursao.salvar();
                    JOptionPane.showMessageDialog(null, "Dados da excursão salvos em arquivo.");
                } else {
                    JOptionPane.showMessageDialog(null, "Nenhuma excursão para salvar.");
                }
            }
        });

        carregarDeArquivoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (excursao != null) {
                    excursao.carregar();
                    JOptionPane.showMessageDialog(null, "Dados da excursão carregados de arquivo.");
                } else {
                    JOptionPane.showMessageDialog(null, "Nenhuma excursão para carregar.");
                }
            }
        });
    }

    private void exibirReservas(List<String> reservas) {
        StringBuilder reservasStr = new StringBuilder("Reservas:\n");
        for (String reserva : reservas) {
            reservasStr.append(reserva).append("\n");
        }
        listaReservasArea.setText(reservasStr.toString());
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(ExcursaoGUI::new);
    }
}