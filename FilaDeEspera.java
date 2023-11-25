import java.util.Queue;
import java.util.LinkedList;

public class FilaDeEspera {

    private Queue<Aeronave> fila;
    private int qtdAvioes = 0;
    private int tempoMedioDeEspera = 0;
    private int tempoDeEsperaTotal = 0;
    private String nome;

    public FilaDeEspera() {
        this.fila = new LinkedList<>();
    }

    public FilaDeEspera(String nome) {
        this.fila = new LinkedList<>();
        this.nome = nome;
    }

    public void adicionarAeronave(Aeronave aeronave) {
        fila.offer(aeronave);
        tempoDeEsperaTotal += aeronave.getTempoEspera();
    }

    public Aeronave removerAeronave() {
        return fila.poll();
    }

    public void imprimirQtd(FilaDeEspera fila) {

        qtdAvioes = fila.tamanho();
        System.out.println("O número de aviões na fila eh: " + qtdAvioes);

    }

    public void imprimirtempoMedioDeEspera(FilaDeEspera fila) {

        tempoMedioDeEspera = tempoDeEsperaTotal / qtdAvioes;
        System.out.println("O tempo médio de espera eh: " + tempoMedioDeEspera);

    }

    public int tamanho() {
        return fila.size();
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
}