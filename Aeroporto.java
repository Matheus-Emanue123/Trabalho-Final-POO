import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Aeroporto {
    private Pista pista1;
    private Pista pista2;
    private Pista pista3;

    private String clima;
    
    private int qtdAterrissagemEmergencial;
    private int minutosSimulados;

    private boolean estadoCritico;

    // private List<FilaDeEspera> filasAterrissagem;
    // private List<FilaDeEspera> filasDecolagem;
    private List<Aeronave> aeronavesCriticas;

    
    public static int idsAeronavesAterrissagem = 1;
    public static int idsAeronavesDecolagem = 2;

    static enum CompanhiaAerea {
        GOL,
        LATAM,
        AZUL,
        AmericanAirlines
    }

    static enum Clima{
        Sol,
        ChuvaLeve,
        Neve,
        VentoForte,
        Tempestade,
        Nublado
    }

    public Aeroporto() {
        pista1 = new Pista("Pista 1", true);
        pista2 = new Pista("Pista 2", true);
        pista3 = new Pista("Pista 3", false);
    
        // filasAterrissagem = new ArrayList<>();
        // filasDecolagem = new ArrayList<>();
        aeronavesCriticas = new ArrayList<>();
        this.minutosSimulados = 0;
        this.estadoCritico = false;
        clima = Clima.Sol.toString();
    }

    public void simularMinuto() {
        System.out.println("Simulando minuto...");
        this.minutosSimulados++;
        gerarAeronaves();
        recalcularTempoMedio();

        if(minutosSimulados % 10 == 0){
            mudarClima();
        }
        
        System.out.println("Aeronaves em espera: " + calcularAeronavesEmEspera());
        System.out.println("Aeronaves em espera para decolagem: " + calcularAeronavesEmEsperaDecolagem());
        System.out.println("Aeronaves em espera para aterrissagem: " + calcularAeronavesEmEsperaAterrissagem());
        System.out.println("Aeronaves que realizaram aterrissagem emergencial: " + qtdAterrissagemEmergencial);

        
    }

    public void mudarClima(){
        Random random = new Random();
        int valor = random.nextInt(100);

        if(valor < 10){
            clima = Clima.ChuvaLeve.toString();
        } else if(valor < 20){
            clima = Clima.Neve.toString();
        } else if(valor < 30){
            clima = Clima.VentoForte.toString();
        } else if(valor < 40){
            clima = Clima.Tempestade.toString();
        } else if(valor < 50){
            clima = Clima.Nublado.toString();
        } else{
            clima = Clima.Sol.toString();
        }
    }

    public void adicionarAeronaveFilaAterrisagem(Aeronave aeronave) {

        Pista pistaEscolhida = escolherPistaAterrissagem();
        System.out.println("Pista escolhida: " + pistaEscolhida.getNome());
        FilaDeEspera filaEscolhida = pistaEscolhida.escolherFilaAterrissagem();
        System.out.println("Fila escolhida: " + filaEscolhida.getNome());
        filaEscolhida.adicionarAeronave(aeronave);

    }

    public Pista escolherPistaAterrissagem(){
        if(pista1.quantidadeAeronavesAterrissagem() > pista2.quantidadeAeronavesAterrissagem()){
            return pista1;
        }else if(pista1.quantidadeAeronavesAterrissagem() < pista2.quantidadeAeronavesAterrissagem()){
            return pista2;
        } else {
            //Tem que fazer a lógica ainda
            return pista1;
        }
    }

    public Pista escolherPistaDecolagem(){

        //Tem que fazer a lógica ainda
        return estadoCritico ? pista1 : pista3;
    }

    public void adicionarAeronaveFilaDecolagem(Aeronave aeronave) {

        Pista pistaEscolhida = escolherPistaDecolagem();
        System.out.println("Pista escolhida: " + pistaEscolhida.getNome());
        FilaDeEspera filaEscolhida = pistaEscolhida.escolherFilaDecolagem();
        System.out.println("Fila escolhida: " + filaEscolhida.getNome());
        filaEscolhida.adicionarAeronave(aeronave);

    }

    public int calcularAeronavesEmEspera() {
        return pista1.quantidadeAeronaves() + pista2.quantidadeAeronaves() + pista3.quantidadeAeronaves();
    }

    public int calcularAeronavesEmEsperaAterrissagem() {
        return pista1.quantidadeAeronavesAterrissagem() + pista2.quantidadeAeronavesAterrissagem() + pista3.quantidadeAeronavesAterrissagem();
    }

    public int calcularAeronavesEmEsperaDecolagem() {
        return pista1.quantidadeAeronavesDecolagem() + pista2.quantidadeAeronavesDecolagem() + pista3.quantidadeAeronavesDecolagem();
    }

    private boolean verificarCombustivelCritico(Aeronave aeronave) { 
        return aeronave.getCombustivel() < 5;
    }

    public void recalcularTempoMedio(){
        //Tem que fazer a lógica ainda
    }

    public boolean intervaloCombustível(){
        //Aumenta a chance de aeronaves serem geradas sem combustível crítico
        Random random = new Random();
        int valor = random.nextInt(100);

        return valor < 10;
    }

    public void gerarAeronaves(){
        Random random = new Random();
    
        System.out.println("Gerando aeronaves...");

        int aeronavesAterrissagem = random.nextInt(4);

        System.out.println("Chegando " + aeronavesAterrissagem + " aeronaves para aterrissagem...");

        for(int i = 0; i < aeronavesAterrissagem; i++){
            boolean combustivelCritico = intervaloCombustível();
            int numPassageiros = random.nextInt(380) + 1;
            int combustivel = combustivelCritico ? (random.nextInt(3) + 1) : random.nextInt(12) + 4;        

            String companhiaAerea = CompanhiaAerea.values()[random.nextInt(CompanhiaAerea.values().length)].toString();

            boolean passageiroEspecial = (random.nextInt(10) + 1 > 8 ? true : false);

            Aeronave aeronave = new Aeronave(numPassageiros, 0, combustivel, companhiaAerea, passageiroEspecial);
            aeronave.setIdAterrissagem(idsAeronavesAterrissagem);
            idsAeronavesAterrissagem += 2;

            adicionarAeronaveFilaAterrisagem(aeronave);
        }

        int aeronavesDecolagem = random.nextInt(4);

        System.out.println("Chegando " + aeronavesDecolagem + " aeronaves para decolagem...");

        for(int i = 0; i < aeronavesDecolagem; i++){
            int numPassageiros = random.nextInt(380) + 1;
            int combustivel = 15;

            String companhiaAerea = CompanhiaAerea.values()[random.nextInt(CompanhiaAerea.values().length)].toString();

            boolean passageiroEspecial = (random.nextInt(10) + 1 > 8 ? true : false);
            
            Aeronave aeronave = new Aeronave(numPassageiros, 0, combustivel, companhiaAerea, passageiroEspecial);
            aeronave.setIdDecolagem(idsAeronavesDecolagem);
            idsAeronavesDecolagem += 2;

            adicionarAeronaveFilaDecolagem(aeronave);
        }
    }

}
        