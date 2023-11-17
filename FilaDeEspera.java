import java.util.Queue;
import java.util.LinkedList;

public class FilaDeEspera {
    private Queue<Aeronave> fila;
    private int qtdAvioes;
    private int tempoMedioDeEspera;

    public FilaDeEspera() {
        fila = new LinkedList<>();
    }

    public void adicionarAeronave(Aeronave aeronave) {
        fila.offer(aeronave);
    }

    public Aeronave removerAeronave() {
        return fila.poll();
    }

    public int tamanho() {
        return fila.size();
    }
}
