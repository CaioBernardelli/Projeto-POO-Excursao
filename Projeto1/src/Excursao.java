import java.io.*;
import java.nio.file.*;
import java.util.*;

// Classe que representa uma excursão
class Excursao {
    private int codigo; // Código da excursão
    private double preco; // Preço da excursão
    private int max; // Número máximo de reservas
    private List<String> reservas; // Lista de reservas (formato: "cpf/nome")

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
        boolean removed = reservas.removeIf(reserva -> reserva.startsWith(cpf + "/"));
        if (!removed) {
            throw new IllegalArgumentException("Não há reservas para esse CPF.");
        }

        salvar(); // Atualiza o arquivo após a remoção das reservas
    }

    // Método para listar as reservas por CPF
    public List<String> listarReservasPorCpf(String digitos) {
        List<String> filteredReservas = new ArrayList<>();
        for (String reserva : reservas) {
            String cpf = reserva.split("/")[0];
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
            String nome = reserva.split("/")[1];
            if (texto.isEmpty() || nome.contains(texto)) {
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
        return "Código: " + codigo + ", Preço: " + preco + ", Máximo de reservas: " + max + ", Total de reservas: " + reservas.size();
    }

    // Método para salvar os dados da excursão em um arquivo
    public void salvar() {
        criarDiretorioSeNaoExiste();

        try (PrintWriter writer = new PrintWriter(getFilePath())) {
            writer.println(this.preco);
            writer.println(this.max);
            for (String reserva : this.reservas) {
                writer.println(reserva);
            }
        } catch (IOException e) {
            System.err.println("Erro ao salvar os dados da excursão: " + e.getMessage());
        }
    }

    // Método para carregar os dados da excursão a partir de um arquivo
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

            try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
                String precoLine = reader.readLine();
                String maxLine = reader.readLine();

                if (precoLine != null && maxLine != null) {
                    this.preco = Double.parseDouble(precoLine);
                    this.max = Integer.parseInt(maxLine);
                }

                // Inicializa a lista de reservas
                this.reservas = new ArrayList<>();

                String linha;
                while ((linha = reader.readLine()) != null) {
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
