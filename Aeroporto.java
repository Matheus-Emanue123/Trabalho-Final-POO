import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Aeroporto {
    private Pista pista1;
    private Pista pista2;
    private Pista pista3;

    private int qtdAterrissagemEmergencial;

    private List<FilaDeEspera> filasAterrisagem;
    private List<FilaDeEspera> filasDecolagem;
    private List<Aeronave> aeronavesCriticas;

    public Aeroporto() {
        // pistas = new ArrayList<>();
        filasAterrisagem = new ArrayList<>();
        filasDecolagem = new ArrayList<>();
        aeronavesCriticas = new ArrayList<>();

        for (int i = 0; i < 3; i++) {
            // pistas.add(new Pista());
        }

        for (int i = 0; i < 2; i++) {
            filasAterrisagem.add(new FilaDeEspera());
        }

        for (int i = 0; i < 2; i++) {
            filasDecolagem.add(new FilaDeEspera());
        }
    }

    public void simularMinuto() {
    }

    public void adicionarAeronaveFilaAterrisagem(Aeronave aeronave) {

        escolherPistaAterrissagem().escolherFilaAterrissagem().offer(aeronave);

    }

    public Pista escolherPistaAterrissagem(){
        if(pista1.quantidadeAeronavesAterrissagem() > pista2.quantidadeAeronavesAterrissagem()){
            return pista1;
        }else if(pista1.quantidadeAeronavesAterrissagem() < pista2.quantidadeAeronavesAterrissagem()){
            return pista2;
        } else {
            return pista1;
        }
    }

    public Pista escolherPistaDecolagem(){
        if(pista1.quantidadeAeronavesAterrissagem() > pista2.quantidadeAeronavesAterrissagem()){
            return pista1;
        }else if(pista1.quantidadeAeronavesAterrissagem() < pista2.quantidadeAeronavesAterrissagem()){
            return pista2;
        } else {
            return pista1;
        }
    }

    public void adicionarAeronaveFilaDecolagem(Aeronave aeronave) {

        FilaDeEspera filaMinima = filasDecolagem.get(0);
        for (FilaDeEspera fila : filasDecolagem) {
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

    public void recalcularTempoMedio(){

    }
}
        

