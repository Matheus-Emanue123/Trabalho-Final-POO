import java.util.Queue;
import java.util.LinkedList;

public class FilaDeEspera {

    private Queue<Aeronave> fila;
    private int qtdAvioes = 0;
    private double tempoMedioDeEspera = 0;
    private double tempoDeEsperaTotal = 0;
    private String nome;

    public FilaDeEspera() {
        this.fila = new LinkedList<>();
    }

    public FilaDeEspera(String nome) {
        this.fila = new LinkedList<>();
        this.nome = nome;
    }

    public Queue<Aeronave> getFila() {
        return this.fila;
    }

    public void setFila(Queue<Aeronave> fila) {
        this.fila = fila;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return this.nome;
    }

    public double getTempoMedioDeEspera() {
        return this.tempoMedioDeEspera;
    }

    public double setTempoMedioDeEspera() {
        return this.tempoMedioDeEspera;
    }

    public void setTempoDeEsperaTotal(double tempoDeEsperaTotal) {
        this.tempoDeEsperaTotal = tempoDeEsperaTotal;
    }

    public void adicionarAeronave(Aeronave aeronave) {
        fila.offer(aeronave);
        tempoDeEsperaTotal += aeronave.getTempoEspera();
    }

    public Aeronave removerAeronave() {
        return fila.poll();
    }

    public int tamanho() {
        return fila.size();
    }

    public double tempoMedioDeEsperaFila() {
        if (tempoDeEsperaTotal() == 0 || tamanho() == 0) {
            return 0;
        } else {
            return tempoMedioDeEspera = tempoDeEsperaTotal() / tamanho();
        }
    }

    public double tempoDeEsperaTotal() {
        tempoDeEsperaTotal = 0;
        for (Aeronave a : fila) {
            tempoDeEsperaTotal += a.getTempoEspera();
        }

        if (tempoDeEsperaTotal == 0) {
            return 0;
        } else {
            return tempoDeEsperaTotal;
        }
    }

    public void verificarCombustivelCritico() {
        for (Aeronave a : fila) {
            boolean auxCombustivel = a.verificarCombustivelCritico();

            if (auxCombustivel) {
                System.out.println("A aeronave " + a.getId() + " está com combustível crítico!");
            }
        }
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

    public int qntAeronavesCombustivelCritico() {
        int qtdAeronavesCombustivelCritico = 0;
        for (Aeronave a : fila) {
            if (a.verificarCombustivelCritico()) {
                qtdAeronavesCombustivelCritico++;
            }
        }
        return qtdAeronavesCombustivelCritico;
    }

    public void imprimirFila() {
        System.out.println(this.nome + ": " + fila.size());
        System.out.println("O tempo médio de espera desta " + this.nome + " eh: " + tempoMedioDeEsperaFila());
        for (Aeronave a : fila) {
            a.imprimirAeronave();
        }
        System.out.println();
    }

    public void imprimirQtd() {
        qtdAvioes = fila.size();
        System.out.println("O número de aviões na fila eh: " + qtdAvioes);
    }

}