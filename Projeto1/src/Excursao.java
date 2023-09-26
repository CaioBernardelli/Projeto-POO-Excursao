import java.io.*;
import java.util.ArrayList;
import java.util.List;

class Excursao {
    private int codigo;
    private double preco;
    private int max;
    private List<String> reservas;

    // Construtor com código, preço e max
    public Excursao(int codigo, double preco, int max) throws IllegalArgumentException {
        if (codigo <= 0 || preco <= 0 || max <= 0) {
            throw new IllegalArgumentException("Código, preço e max devem ser maiores que zero.");
        }

        this.codigo = codigo;
        this.preco = preco;
        this.max = max;
        this.reservas = new ArrayList<>();
    }

    // Construtor com código (usa o preço e max padrão)
    public Excursao(int codigo) throws IllegalArgumentException {
        this(codigo, 0.0, 0);
    }

    public void criarReserva(String cpf, String nome) throws IllegalArgumentException {
        if (reservas.size() >= max) {
            throw new IllegalArgumentException("Número máximo de reservas atingido.");
        }

        for (String reserva : reservas) {
            String[] partes = reserva.split("/");
            String reservaNome = partes[1];

            if (reservaNome.equals(nome)) {
                throw new IllegalArgumentException("Esse nome já foi reservado.");
            }
        }

        String novaReserva = cpf + "/" + nome;
        reservas.add(novaReserva);
    }


    public void cancelarReserva(String cpf, String nome) throws IllegalArgumentException {
        String reserva = cpf + "/" + nome;
        if (!reservas.remove(reserva)) {
            throw new IllegalArgumentException("Essa reserva não existe.");
        }
    }

    public void cancelarReserva(String cpf) throws IllegalArgumentException {
        boolean removed = reservas.removeIf(reserva -> reserva.startsWith(cpf + "/"));
        if (!removed) {
            throw new IllegalArgumentException("Não há reservas para esse CPF.");
        }
    }

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

    public double calcularValorTotal() {
        return preco * reservas.size();
    }

    @Override
    public String toString() {
        return "Código: " + codigo + ", Preço: " + preco + ", Máximo de reservas: " + max + ", Total de reservas: " + reservas.size();
    }

    public void salvar() {
        try (PrintWriter writer = new PrintWriter(codigo + ".txt")) {
            writer.println(preco);
            writer.println(max);
            for (String reserva : reservas) {
                writer.println(reserva);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void carregar() {
        try (BufferedReader reader = new BufferedReader(new FileReader(codigo + ".txt"))) {
            preco = Double.parseDouble(reader.readLine());
            max = Integer.parseInt(reader.readLine());
            reservas.clear();
            String line;
            while ((line = reader.readLine()) != null) {
                reservas.add(line);
            }
        } catch (IOException | NumberFormatException e) {
            e.printStackTrace();
        }
    }
}
