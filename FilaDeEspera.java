import java.util.Queue;
import java.util.LinkedList;

public class FilaDeEspera {
    private Queue<Aeronave> fila;

    public FilaDeEspera() {
        fila = new LinkedList<>();
    }

    public void adicionar_aeronave(Aeronave aeronave) {
        fila.offer(aeronave);
    }

    public Aeronave remover_aeronave() {
        return fila.poll();
    }

    public int tamanho() {
        return fila.size();
    }
}
