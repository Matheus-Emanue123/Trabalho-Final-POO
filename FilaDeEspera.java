import java.util.Queue;
import java.util.Scanner;
import java.util.LinkedList;

public class FilaDeEspera {

    private Queue<Aeronave> fila;
    private double tempoMedioDeEspera = 0;
    private double tempoDeEsperaTotal = 0;
    private String nome;
    private int qtdAterrissagensEmergenciais;
    private int qtdAeronavesDecolaram;
    private int qtdAeronavesSairam;
    private double tempoEsperaAeronavesSairam;

    public FilaDeEspera() {
        this.fila = new LinkedList<>();
    }

    public FilaDeEspera(String nome) {
        this.fila = new LinkedList<>();
        this.nome = nome;
        if (!nome.equals("Fila de Decolagem")) {
            this.qtdAeronavesDecolaram = -1;
        }
    }

    public void adicionarAeronave(Aeronave aeronave) {
        fila.offer(aeronave);
        tempoDeEsperaTotal += aeronave.getTempoEspera();
    }

    public Queue<Aeronave> getFila() {
        return this.fila;
    }

    public String getNome() {
        return this.nome;
    }

    public int getQtdAeronavesDecolaram() {
        return this.qtdAeronavesDecolaram;
    }

    public int getQtdAeronavesSairam() {
        return this.qtdAeronavesSairam;
    }

    public int getQtdAterrissagensEmergenciais() {
        return this.qtdAterrissagensEmergenciais;
    }

    public int getMenorCombustivel() {
        int menorCombustivel = 16;

        for (Aeronave a : fila) {
            if (a.getCombustivel() < menorCombustivel) {
                menorCombustivel = a.getCombustivel();
            }
        }
        return menorCombustivel;
    }

    public double getTempoEsperaAeronavesSairam() {
        return this.tempoEsperaAeronavesSairam;
    }

    public double getTempoMedioDeEspera() {
        return this.tempoMedioDeEspera;
    }

    public void imprimir() {
        if (fila.isEmpty()) {
            System.out.println("Fila vazia!");
            return;
        }

        for (Aeronave a : fila) {
            a.imprimir();
        }
    }

    public void imprimirInformacoesFila(int n, Scanner sc) {
        System.out.println("INFORMACOES DA Fila " + n + ": ");
        System.out.println("Tempo medio de espera: " + String.format("%.2f", tempoMedioDeEsperaFila()));
        System.out.println(
                "Quantidade de aeronaves que realizaram aterrissagens emergenciais: "
                        + this.qtdAterrissagensEmergenciais);
        System.out.println("Total de aeronaves em espera: " + tamanho());

        do {
            System.out.println("Deseja ver as aeronaves desta fila? (S/N)");
            String escolha = sc.nextLine();

            if (escolha.equals("S") || escolha.equals("s")) {
                System.out.println();
                imprimir();
            } else if (escolha.equals("N") || escolha.equals("n")) {
                return;
            } else {
                System.out.println("Opcao invalida!");
            }
        } while (true);
    }

    public int qntAeronavesCombustivelCritico() {
        int qtdAeronavesCombustivelCritico = 0;
        for (Aeronave a : fila) {
            if (a.verificarCombustivelCritico()) {
                qtdAeronavesCombustivelCritico++;
            }
        }
        return qtdAeronavesCombustivelCritico;
    }

    public int qntPassagueirosEspeciais() {
        int qtdPassageirosEspeciais = 0;
        for (Aeronave a : fila) {
            if (a.getPassageiroEspecial()) {
                qtdPassageirosEspeciais++;
            }
        }
        return qtdPassageirosEspeciais;
    }

    public void removerAeronave() {
        fila.poll();
    }

    public void setFila(Queue<Aeronave> fila) {
        this.fila = fila;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setQtdAeronavesDecolaram(int qtd) {
        this.qtdAeronavesDecolaram = qtd;
    }

    public void setQtdAeronavesSairam(int qtd) {
        this.qtdAeronavesSairam = qtd;
    }

    public void setQtdAterrissagensEmergenciais(int qtd) {
        this.qtdAterrissagensEmergenciais = qtd;
    }

    public void setTempoDeEsperaTotal(double tempoDeEsperaTotal) {
        this.tempoDeEsperaTotal = tempoDeEsperaTotal;
    }

    public void setTempoEsperaAeronavesSairam(double tempo) {
        this.tempoEsperaAeronavesSairam = tempo;
    }

    public double setTempoMedioDeEspera() {
        return this.tempoMedioDeEspera;
    }

    public int tamanho() {
        return fila.size();
    }

    public double tempoDeEsperaTotal() {
        tempoDeEsperaTotal = 0;
        for (Aeronave a : fila) {
            tempoDeEsperaTotal += a.getTempoEspera();
        }

        return tempoDeEsperaTotal + tempoEsperaAeronavesSairam;
    }

    public double tempoMedioDeEsperaFila() {
        if (tempoDeEsperaTotal() + tempoEsperaAeronavesSairam == 0 || tamanho() + qtdAeronavesSairam == 0) {
            return 0;
        } else {
            return tempoMedioDeEspera = tempoDeEsperaTotal() / (tamanho() + getQtdAeronavesSairam());
        }
    }

    public void verificarCombustivelCritico() {
        int aux = 0;
        for (Aeronave a : fila) {
            boolean auxCombustivel = a.verificarCombustivelCritico();

            if (auxCombustivel) {
                System.out.println(
                        "A aeronave " + a.getId() + " está com combustível crítico (" + a.getCombustivel() + ")!");
                aux++;
            }
        }

        if (aux == 0 || fila.isEmpty()) {
            System.out.println("Nao possui nenhuma aeronave com combustível crítico!");
        }
    }
}