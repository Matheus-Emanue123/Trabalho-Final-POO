import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Aeroporto {
    private List<Pista> pistas;
    private List<FilaDeEspera> filasAterrisagem;
    private List<FilaDeEspera> filasDecolagem;
    private List<Aeronave> aeronavesCriticas;

    public Aeroporto() {
        pistas = new ArrayList<>();
        filasAterrisagem = new ArrayList<>();
        filasDecolagem = new ArrayList<>();
        aeronavesCriticas = new ArrayList<>();

        for (int i = 0; i < 3; i++) {
            pistas.add(new Pista());
        }

        for (int i = 0; i < 2; i++) {
            filasAterrisagem.add(new FilaDeEspera());
        }

        for (int i = 0; i < 2; i++) {
            filasDecolagem.add(new FilaDeEspera());
        }
    }

    public void simularMinuto() {

        Random random = new Random();

        for (int i = 0; i < 3; i++) {
            int combustivel = random.nextInt(50) + 1; 
            int tempoMaximoEspera = random.nextInt(10) + 1; 

            Aeronave aeronave = new Aeronave(combustivel, tempoMaximoEspera);


            adicionarAeronaveFilaAterrisagem(aeronave);
        }

    }

    private void adicionarAeronaveFilaAterrisagem(Aeronave aeronave) {

        FilaDeEspera filaMinima = filasAterrisagem.get(0);
        for (FilaDeEspera fila : filasAterrisagem) {
            if (fila.tamanho() < filaMinima.tamanho()) {
                filaMinima = fila;
            }
        }

        filaMinima.adicionarAeronave(aeronave);
    }

    private void verificarCombustivelCritico(Aeronave aeronave) { 
        if (aeronave.getCombustivel() < 10) { 
            aeronavesCriticas.add(aeronave);
        }
    }
}
        

