import java.util.Queue;
import java.util.LinkedList;

public class Pista {
    private Queue<Aeronave> fila;

    public Pista() {
        fila = new LinkedList<>();
    }

    public void adicionar_aeronave(Aeronave aeronave) {
        fila.offer(aeronave);
    }

    public Aeronave remover_aeronave() {
        return fila.poll();
    }

    public int tamanho_fila() {
        return fila.size();
    }
}
