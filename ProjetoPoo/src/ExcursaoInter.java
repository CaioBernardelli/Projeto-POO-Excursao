
import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.awt.event.ActionEvent;

public class ExcursaoInter {

	
	
	private Excursao excursao;
	private JFrame frmExcurso;
	private JTextField textFieldNome;
    private JButton btnNewButton;
    private JButton btnNewButton_1 ;
    private JButton btnNewButton_3 ;
    private JButton btnNewButton_4;
    private JLabel label;
	    
	    
	    

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ExcursaoInter window = new ExcursaoInter();
					window.frmExcurso.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public ExcursaoInter() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmExcurso = new JFrame();
		frmExcurso.setTitle("Excursão");
		frmExcurso.setBounds(100, 100, 450, 300);
		frmExcurso.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmExcurso.getContentPane().setLayout(null);
		
		
		
		
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setBounds(20, 236, 391, 14);
		frmExcurso.getContentPane().add(lblNewLabel);
		
		JTextArea textArea = new JTextArea();
		textArea.setBounds(10, 11, 165, 211);
		frmExcurso.getContentPane().add(textArea);
		
		JButton btnNewButton = new JButton("Criar  Excursão");
		btnNewButton.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		        try {
		            String codigoStr = JOptionPane.showInputDialog("Digite o código da Excursão:");
		            String precoStr = JOptionPane.showInputDialog("Digite o preço:");
		            String maxStr = JOptionPane.showInputDialog("Digite o máximo de pessoas:");

		            int codigo = Integer.parseInt(codigoStr);
		            double preco = Double.parseDouble(precoStr);
		            int max = Integer.parseInt(maxStr);

		            Excursao excursao = new Excursao(codigo, preco, max); // Cria uma nova instância de Excursao

		            label.setText("Excursão criada com sucesso");
		        } catch (NumberFormatException ex) {
		            label.setText("Código, preço e máximo de pessoas devem ser numéricos");
		        } catch (Exception ex) {
		            label.setText(ex.getMessage());
		        }
		    }
		});

		btnNewButton.setBounds(200, 12, 145, 23);
		frmExcurso.getContentPane().add(btnNewButton);
		
		
		
		JButton btnNewButton_5 = new JButton("Criar reserva");
		btnNewButton_5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				 try {
					    String cpfPri = JOptionPane.showInputDialog("Digite o cpf:");
			            String nomePri  = JOptionPane.showInputDialog("Digite o nome:");
			            excursao.criarReserva(cpfPri, nomePri);
			            label.setText("Reserva criada com sucesso");
			            excursao.salvar();
			        } catch (NumberFormatException ex) {
			            label.setText("Código, preço e máximo de pessoas devem ser numéricos");
			        } catch (Exception ex) {
			            label.setText(ex.getMessage());
			        }
			}
		});
		btnNewButton_5.setBounds(200, 46, 145, 23);
		frmExcurso.getContentPane().add(btnNewButton_5);
		
		
		
	
		JButton btnNewButton_2 = new JButton("Cancelar reserva grupo");
		btnNewButton_2.setBounds(200, 119, 145, 23);
		frmExcurso.getContentPane().add(btnNewButton_2);
		
		
		
		
	//ok
		JButton btnNewButton_3 = new JButton("Listar por CPF");
		btnNewButton_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {	
			textArea.setText("");  //limpar
			ArrayList<String> lista = excursao.listarReservasPorCpf(null);
			for(String s : lista)
				textArea.append(s + "\n");
			}
		});
		btnNewButton_3.setBounds(200, 153, 145, 23);
		frmExcurso.getContentPane().add(btnNewButton_3);
		
		
		
		
		
		
		JButton btnNewButton_4 = new JButton("Listar por Nome");
		btnNewButton_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnNewButton_4.setBounds(198, 187, 147, 23);
		frmExcurso.getContentPane().add(btnNewButton_4);
		
	
		
		
	
	}
}
