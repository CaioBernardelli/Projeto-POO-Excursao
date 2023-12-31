import java.io.*;
import java.nio.file.*;
import java.util.*;

// Classe que representa uma excursão
class Excursao {
    private int codigo; // Código da excursão
    private double preco; // Preço da excursão
    private int max; // Número máximo de reservas
    private ArrayList<String> reservas; // Lista de reservas (formato: "cpf/nome")

    // Obtém o código da excursão
    public int getCodigo() {
        return codigo;
    }

    // Construtor com código, preço e número máximo de reservas
    public Excursao(int codigo, double preco, int max) throws IllegalArgumentException {
        // Validação dos argumentos
        if (codigo <= 0 || preco <= 0 || max <= 0) {
            throw new IllegalArgumentException("Código, preço e max devem ser maiores que zero.");
        }

        this.codigo = codigo;
        this.preco = preco;
        this.max = max;
        this.reservas = new ArrayList<>();

        salvar();
    }

    // Construtor com código (usa o preço e max padrão)
    public Excursao(int codigo) throws IllegalArgumentException {
        this.codigo = codigo;
        carregar(); // Chama o método carregar após definir o código e antes de criar um novo objeto
    }

    // Método para criar uma reserva
    public void criarReserva(String cpf, String nome) throws IllegalArgumentException {
        // Validação de número máximo de reservas
        if (reservas.size() >= max) {
            throw new IllegalArgumentException("Número máximo de reservas atingido.");
        }

        // Verifica se o nome já foi reservado
        for (String reserva : reservas) {
            String[] partes = reserva.split("/");
            String reservaNome = partes[1];

            if (reservaNome.equals(nome)) {
                throw new IllegalArgumentException("Esse nome já foi reservado.");
            }
        }

        // Adiciona a nova reserva
        String novaReserva = cpf + "/" + nome;
        reservas.add(novaReserva);

        salvar();
    }

    // Método para cancelar uma reserva pelo CPF e nome
    public void cancelarReserva(String cpf, String nome) throws IllegalArgumentException {
        String reserva = cpf + "/" + nome;
        if (!reservas.remove(reserva)) {
            throw new IllegalArgumentException("Essa reserva não existe.");
        }

        salvar(); // Atualiza o arquivo após a remoção da reserva
    }

    // Método para cancelar todas as reservas de um CPF
    public void cancelarReserva(String cpf) throws IllegalArgumentException {
        boolean removed = reservas.removeIf(reserva -> reserva.startsWith(cpf + "/")); //Esta linha remove elementos de uma coleção chamada reservas. Ela usa o método removeIf, que recebe um predicado 
        if (!removed) {                                                               //reserva -> reserva.startsWith(cpf + "/") verifica se cada elemento em reservas começa com o CPF fornecido seguido de uma barra ("/"). Se um elemento corresponder a essa condição, ele é removido e removed é definido como true
            throw new IllegalArgumentException("Não há reservas para esse CPF.");
        }

        salvar(); // Atualiza o arquivo após a remoção das reservas
    }

    // Método para listar as reservas por CPF
    public List<String> listarReservasPorCpf(String digitos) {
        List<String> filteredReservas = new ArrayList<>();//Isso cria uma nova lista vazia chamada filteredReservas que será usada para armazenar as reservas que correspondem ao texto de pesquisa.
        for (String reserva : reservas) {// Este é um loop que itera por todas as reservas na lista reservas.
            String cpf = reserva.split("/")[0];//código divide a reserva em duas partes usando a barra ("/") como separador e pega a segunda parte (índice 0), que deve conter o cpf. 
            if (digitos.isEmpty() || cpf.contains(digitos)) {
                filteredReservas.add(reserva);
            }
        }
        return filteredReservas;
    }

    // Método para listar as reservas por nome
    public List<String> listarReservasPorNome(String texto) {
        List<String> filteredReservas = new ArrayList<>();
        for (String reserva : reservas) {
            String nome = reserva.split("/")[1];//código divide a reserva em duas partes usando a barra ("/") como separador e pega a segunda parte (índice 1), que deve conter o nome. 
            if (texto.isEmpty() || nome.contains(texto)) {// código verifica se o texto de pesquisa (texto) está vazio ou se o nome da reserva (obtido na etapa anterior) contém o texto de pesquisa.
                filteredReservas.add(reserva);
            }
        }
        return filteredReservas;
    }

    // Método para calcular o valor total da excursão
    public double calcularValorTotal() {
        return preco * reservas.size();
    }

    // Método para obter uma representação da excursão em formato de string
    @Override
    public String toString() {
        return "Codigo: " + codigo + ", Preco: " + preco + ", Maximo de reservas: " + max + ", Total de reservas: " + reservas.size();
    }

    // Método para salvar os dados da excursão em um arquivo
    public void salvar() {
        criarDiretorioSeNaoExiste();

        try (PrintWriter writer = new PrintWriter(getFilePath())) {//Ele abre um arquivo para escrita usando PrintWriter e garante que o arquivo seja fechado automaticamente após o bloco try ser concluído. getFilePath() retorna o caminho do arquivo onde os dados da excursão serão salvos.
            writer.println(this.preco);
            writer.println(this.max);
            for (String reserva : this.reservas) {
                writer.println(reserva);
            }
        } catch (IOException e) {
            System.err.println("Erro ao salvar os dados da excursão: " + e.getMessage());
        }
    }

    // Método para carregar os dados da excursão a partir de um arquivo,lê os dados de uma excursão a partir de um arquivo de texto e preenche os campos da classe com esses dados.
    public void carregar() {
        // Caminho absoluto para o diretório "excursao_data"
        String directoryPath = Paths.get("excursao_data").toAbsolutePath().toString();
        String filePath = directoryPath + "/" + this.getCodigo() + ".txt";

        try {
            // Verifica se o arquivo existe
            if (!Files.exists(Paths.get(filePath))) {
                System.err.println("O arquivo não existe.");
                return;
            }

            try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {// O código abre o arquivo para leitura usando um BufferedReader, que permite ler o arquivo linha por linha.
                String precoLine = reader.readLine();
                String maxLine = reader.readLine();//Essas linhas leem as duas primeiras linhas do arquivo, que devem conter o preço e o número máximo de reservas. Se essas linhas não forem nulas, o código converte essas strings em valores numéricos (um double e um inteiro) e os armazena nas variáveis this.preco e this.max.

                if (precoLine != null && maxLine != null) {
                    this.preco = Double.parseDouble(precoLine);
                    this.max = Integer.parseInt(maxLine);
                }

                // Inicializa a lista de reservas
                this.reservas = new ArrayList<>();

                String linha;
                while ((linha = reader.readLine()) != null) {//Este loop lê todas as linhas restantes do arquivo e as adiciona à lista reservas. Cada linha no arquivo representa uma reserva no formato "cpf/nome".
                    this.reservas.add(linha);
                }
            } catch (IOException e) {
                System.err.println("Erro ao ler o arquivo: " + e.getMessage());
            } catch (NumberFormatException e) {
                System.err.println("Erro ao converter um número: " + e.getMessage());
            }
        } catch (InvalidPathException e) {
            System.err.println("Caminho do arquivo inválido: " + e.getMessage());
        }
    }

    // Método para criar o diretório se não existir
    private void criarDiretorioSeNaoExiste() {
        String directoryPath = Paths.get("excursao_data").toAbsolutePath().toString();
        File directory = new File(directoryPath);
        if (!directory.exists()) {
            directory.mkdirs();
        }
    }

    // Método para obter o caminho do arquivo
    private String getFilePath() {
        String directoryPath = Paths.get("excursao_data").toAbsolutePath().toString();
        return directoryPath + "/" + this.getCodigo() + ".txt";
    }
}
