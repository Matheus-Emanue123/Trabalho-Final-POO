import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Aeroporto {
    private List<Pista> pistas;
    private List<FilaDeEspera> filas_aterrisagem;
    private List<FilaDeEspera> filas_decolagem;
    private List<Aeronave> aeronaves_criticas;

    public Aeroporto() {

        pistas = new ArrayList<>();
        filas_aterrisagem = new ArrayList<>();
        filas_decolagem = new ArrayList<>();
        aeronaves_criticas = new ArrayList<>();

        for (int i = 0; i < 3; i++) {
            pistas.add(new Pista());
        }

        for (int i = 0; i < 2; i++) {
            filas_aterrisagem.add(new FilaDeEspera());
        }

        for (int i = 0; i < 2; i++) {
            filas_decolagem.add(new FilaDeEspera());
        }
    }

    public void simular_minuto() {

        Random random = new Random();

        for (int i = 0; i < 3; i++) {
            int combustivel = random.nextInt(50) + 1; 
            int tempoMaximoEspera = random.nextInt(10) + 1; 

            Aeronave aeronave = new Aeronave(combustivel, tempoMaximoEspera);


            adicionar_aeronave_fila_aterrisagem(aeronave);
        }

    }

    private void adicionar_aeronave_fila_aterrisagem(Aeronave aeronave) {

        FilaDeEspera filaMinima = filas_aterrisagem.get(0);
        for (FilaDeEspera fila : filas_aterrisagem) {
            if (fila.tamanho() < filaMinima.tamanho()) {
                filaMinima = fila;
            }
        }

        filaMinima.adicionar_aeronave(aeronave);
    }

    private void verificarCombustivelCritico(Aeronave aeronave) { 

        if (aeronave.get_combustivel() < 10) { 
            aeronaves_criticas.add(aeronave);
        }
    }
}
        

