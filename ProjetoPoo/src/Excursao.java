import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class Excursao {  // Construtor Excursao  codigo,preco, max
    private int codigo;
    private double preco;
    private int max;
    private ArrayList<String> reservas;

    public Excursao(int codigo, double preco, int max) throws Exception {
        if (codigo <= 0 || preco <= 0 || max <= 0) {
            throw new Exception("Código, preço e max devem ser maiores que zero.");
        }

        this.codigo = codigo;
        this.preco = preco;
        this.max = max;
        this.reservas = new ArrayList<>();
    }

    public Excursao(int codigo) {   ///Construtor Excursao  codigo
        this.codigo = codigo;
        this.preco = 0.0;
        this.max = 0;
        this.reservas = new ArrayList<>();
    }


public void criarReserva(String cpf, String nome) throws Exception {
    if (reservas.size() >= max) { //Verifica se o tamanho atual da lista de reservas (reservas) é maior ou igual ao número máximo permitido de reservas (max). Se o limite for atingido, lança uma exceção
        throw new Exception("Limite máximo de reservas atingido."); 
    }

    String reserva = cpf + "/" + nome;  //: Cria uma string reserva concatenando o CPF e o nome, separados por uma barra ("/")
    if (reservas.contains(reserva)) {  // Verifica se a lista de reservas (reservas) já contém a reserva criada //contains() é  utilizado com coleções para realizar verificações de pertencimento.
        throw new Exception("Esta reserva já existe.");
    }

    reservas.add(reserva); //reserva é adicionada à lista reservas.
 // ----------------------------------------------------------------------------------------------------
 		// gravar dados (data,vaga,placa) no arquivo historico.csv
 		// ----------------------------------------------------------------------------------------------------
 		File f = new File(new File(".\\controle.csv").getCanonicalPath()); // pasta do projeto
 		FileWriter arq = new FileWriter(f, true); // abrir arquivo para adição de dados
 		String dataFormatada = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy - hh:mm:ss"));
 		arq.write(dataFormatada + reserva + " - Saida \n");
 		arq.close();
 		// ----------------------------
}


public void cancelarReserva(String cpf, String nome) throws Exception {
    String reserva = cpf + "/" + nome;
    if (!reservas.contains(reserva)) {
        throw new Exception("Reserva não encontrada.");
    }

    reservas.remove(reserva);
}



public void cancelarReserva(String cpf) throws Exception {
    ArrayList<String> reservasParaRemover = new ArrayList<>();
    for (String reserva : reservas) {
        if (reserva.startsWith(cpf + "/")) {   // Verifica se a reserva atual começa com o CPF fornecido, seguido de uma barra ("/"). Isso é feito usando o método startsWith()
            reservasParaRemover.add(reserva);
        }
    }

    if (reservasParaRemover.isEmpty()) {//o código verifica se reservasParaRemover está vazio
        throw new Exception("Nenhuma reserva encontrada para este CPF.");
    }

    reservas.removeAll(reservasParaRemover);  //(removeAll) ele remove todos os elementos que são comuns entre as duas listas.
}


public ArrayList<String> listarReservasPorCpf(String digitos) {
    ArrayList<String> reservasPorCpf = new ArrayList<>();
    for (String reserva : reservas) {
        String cpf = reserva.split("/")[0];
        if (cpf.contains(digitos)) {
            reservasPorCpf.add(reserva);
        }
    }
    return reservasPorCpf;
}




public ArrayList<String> listarReservasPorNome(String texto) {
    ArrayList<String> reservasPorNome = new ArrayList<>();
    for (String reserva : reservas) {
        String nome = reserva.split("/")[1];
        if (nome.contains(texto)) {
            reservasPorNome.add(reserva);
        }
    }
    return reservasPorNome;
}






public double valorTotal() {
    return preco * reservas.size();
}






public void salvar() throws IOException {
    try (BufferedWriter writer = new BufferedWriter(new FileWriter(".//codigo.txt"))) {
        writer.write("cpf;nome\n"); // Cabeçalho do arquivo

        for (String reserva : reservas) {
            String[] parts = reserva.split("/");
            String cpf = parts[0];
            String nome = parts[1];
            writer.write(cpf + ";" + nome + "\n");
        }
    }
}




}