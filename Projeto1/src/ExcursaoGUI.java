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

        // Adicionando uma JTextArea para listar as reservas
        listaReservasArea = new JTextArea();
        listaReservasArea.setBounds(220, 20, 220, 380);
        listaReservasArea.setEditable(false);
        panel.add(listaReservasArea);
        
        JButton criarExcursaoButton = new JButton("Criar Excursão");
        criarExcursaoButton.setBounds(10, 20, 200, 25);
        panel.add(criarExcursaoButton);

        JButton recuperarExcursaoButton = new JButton("Recuperar Excursão");
        recuperarExcursaoButton.setBounds(10, 60, 200, 25);
        panel.add(recuperarExcursaoButton);

        JButton criarReservaButton = new JButton("Criar Reserva");
        criarReservaButton.setBounds(10, 100, 200, 25);
        panel.add(criarReservaButton);

        JButton cancelarReservaIndividualButton = new JButton("Cancelar Reserva Individual");
        cancelarReservaIndividualButton.setBounds(10, 140, 200, 25);
        panel.add(cancelarReservaIndividualButton);

        JButton cancelarReservaGrupoButton = new JButton("Cancelar Reserva em Grupo");
        cancelarReservaGrupoButton.setBounds(10, 180, 200, 25);
        panel.add(cancelarReservaGrupoButton);

        JButton listarPorCpfButton = new JButton("Listar por CPF");
        listarPorCpfButton.setBounds(10, 220, 200, 25);
        panel.add(listarPorCpfButton);

        JButton listarPorNomeButton = new JButton("Listar por Nome");
        listarPorNomeButton.setBounds(10, 260, 200, 25);
        panel.add(listarPorNomeButton);

        JButton calcularTotalButton = new JButton("Calcular Total");  //Calcular Valor total Excursao
        calcularTotalButton.setBounds(10, 300, 200, 25);
        panel.add(calcularTotalButton);

        JButton salvarEmArquivoButton = new JButton("Salvar em Arquivo");
        salvarEmArquivoButton.setBounds(10, 340, 200, 25);
        panel.add(salvarEmArquivoButton);

        JButton carregarDeArquivoButton = new JButton("Carregar de Arquivo");
        carregarDeArquivoButton.setBounds(10, 380, 200, 25);
        panel.add(carregarDeArquivoButton);


		calcularTotalButton.addActionListener(new ActionListener() {// ActionListener define o comportamento que ocorre quando o botão é clicado.
			@Override
			public void actionPerformed(ActionEvent e) {
				if (excursao != null) {// Verificar se existe excursao
					double valorTotal = excursao.calcularValorTotal(); // usando o método calcularValorTotal() do objeto "excursao" , guarda o resultado em uma variável chamada "valorTotal.
					JOptionPane.showMessageDialog(null, "Valor total das reservas: R$" + valorTotal);
				} else {
					JOptionPane.showMessageDialog(null, "Nenhuma excursão para calcular o valor total.");
				}
			}
		});
        
        
        criarExcursaoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int codigo = Integer.parseInt(JOptionPane.showInputDialog("Digite o código da excursão:"));//Integer.parseInt(...): converte a entrada do usuário em um valor inteiro. .
                double preco = Double.parseDouble(JOptionPane.showInputDialog("Digite o preço da excursão:"));//Double.parseDouble(...): converte a entrada do usuário em um valor de ponto flutuante
                int max = Integer.parseInt(JOptionPane.showInputDialog("Digite o número máximo de reservas:"));
                excursao = new Excursao(codigo, preco, max);//Está criando um novo objeto da classe Excursao
                JOptionPane.showMessageDialog(null, "Excursão criada com sucesso.");
            }
        });

        recuperarExcursaoButton.addActionListener(new ActionListener() {//Recuperar Excursao
            @Override
            public void actionPerformed(ActionEvent e) {
                int codigo = Integer.parseInt(JOptionPane.showInputDialog("Digite o código da excursão:"));
                excursao = new Excursao(codigo);//Cria uma nova instância da classe Excursao com o código fornecido pelo usuário como argumento para o construtor
                excursao.carregar();//Chama o método carregar() na instância da excursão recém-criada. Isso carrega os dados da excursão a partir de um arquivo
                JOptionPane.showMessageDialog(null, "Excursão recuperada com sucesso:\n" + excursao.toString());
            }
        });

        criarReservaButton.addActionListener(new ActionListener() {// Criar Reserva 
            @Override
            public void actionPerformed(ActionEvent e) {
                String cpf = JOptionPane.showInputDialog("Digite o CPF:");
                String nome = JOptionPane.showInputDialog("Digite o nome:");
                try { // Exceções podem ocorrer se, por exemplo, o número máximo de reservas for atingido ou se o nome já estiver reservado.
                    excursao.criarReserva(cpf, nome);// Chama o método criarReserva(cpf, nome) e cria a reserva com os dados colocados pelo usuário 
                    JOptionPane.showMessageDialog(null, "Reserva criada com sucesso.");
                } catch (IllegalArgumentException ex) {   // é usado para capturar a exceção que pode ser lançada no bloco try.IllegalArgumentException:  que é lançada quando ocorre um erro durante a criação da reserva.
                    JOptionPane.showMessageDialog(null, ex.getMessage());
                }
            }
        });

        cancelarReservaIndividualButton.addActionListener(new ActionListener() {// Cancelar Reserva Individual
            @Override
            public void actionPerformed(ActionEvent e) {
                String cpf = JOptionPane.showInputDialog("Digite o CPF:");
                String nome = JOptionPane.showInputDialog("Digite o nome:");
                try {
                    excursao.cancelarReserva(cpf, nome); //Chama o método  cancelarReserva(cpf,nome) 
                    JOptionPane.showMessageDialog(null, "Reserva cancelada com sucesso.");
                } catch (IllegalArgumentException ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage());
                }
            }
        });

        cancelarReservaGrupoButton.addActionListener(new ActionListener() { //Cancelar Reserva em Grupo
            @Override
            public void actionPerformed(ActionEvent e) {
                String cpf = JOptionPane.showInputDialog("Digite o CPF:");
                try {
                    excursao.cancelarReserva(cpf);   //Chama o método  cancelarReserva(cpf) 
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
                List<String> reservas = excursao.listarReservasPorCpf(digitos); //   Chama o método listarReservasPorCpf(digitos).  // A lista de reservas resultante é armazenada na variável reservas.
                exibirReservas(reservas);    //Chama o método exibirReservas(reservas) para exibir as reservas associadas ao CPF na JTextArea da interface gráfica
            }
        });

        listarPorNomeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String texto = JOptionPane.showInputDialog("Digite o texto do nome:");
                List<String> reservas = excursao.listarReservasPorNome(texto);     //Chama o método listarReservasPorNome(texto).A lista de reservas resultante é armazenada na variável reservas.
                exibirReservas(reservas);
            }
        });

        salvarEmArquivoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (excursao != null) {  
                    excursao.salvar();//Se a variável excursao não for nula, chama o método salvar()
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
                    excursao.carregar();//Se a variável excursao não for nula, chama o método carregar()
                    JOptionPane.showMessageDialog(null, "Dados da excursão carregados de arquivo.");
                } else {
                    JOptionPane.showMessageDialog(null, "Nenhuma excursão para carregar.");
                }
            }
        });
    }

    private void exibirReservas(List<String> reservas) {
        StringBuilder reservasStr = new StringBuilder("Reservas:\n");//O método cria um objeto StringBuilder chamado reservasStr para construir a representação final das reservas que serão exibidas na JTextArea.
        for (String reserva : reservas) {// percorre a lista de reservas usando um loop for-each 
            reservasStr.append(reserva).append("\n");// adiciona cada reserva (que é uma string) seguida por uma quebra de linha (\n) ao StringBuilder
        }
        listaReservasArea.setText(reservasStr.toString());// O texto construído é definido como o conteúdo da JTextArea listaReservasArea usando o método setText. Isso atualiza a JTextArea com a lista formatada de reservas.
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(ExcursaoGUI::new);  // é usado para iniciar a interface gráfica Swing da aplicação.
    }
}
