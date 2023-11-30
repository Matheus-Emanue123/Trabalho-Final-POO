import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Aeroporto {
    private Scanner sc = new Scanner(System.in);

    private Pista pista1;
    private Pista pista2;
    private Pista pista3;

    private String clima; // variavel para armazenar o clima atual

    private int qtdAterrissagemEmergencial; // variavel para armazenar a quantidade de aeronaves que fizeram
                                            // aterrissagem emergencial
    private int minutosSimulados; // variavel para armazenar a quantidade de minutos simulados

    private boolean estadoCritico; // variavel para armazenar o estado critico do aeroporto

    private List<Aeronave> aeronavesCriticas; // lista para armazenar as aeronaves que estao em estado critico

    public static int idsAeronavesAterrissagem = 1; // variavel para armazenar o id da proxima aeronave que vai
                                                    // aterrissar
    public static int idsAeronavesDecolagem = 2; // variavel para armazenar o id da proxima aeronave que vai decolar

    private static int qtdAeronavesAterrissagemPorMinuto; // variavel para armazenar a quantidade de aeronaves que
                                                          // chegaram para aterrissagem no minuto atual
    private static int qtdAeronavesDecolagemPorMinuto; // variavel para armazenar a quantidade de aeronaves que chegaram
                                                       // para decolagem no minuto atual

    private static boolean aterrissagem1 = false; // variavel para armazenar se a pista 1 esta em uso para aterrissagem
    private static boolean aterrissagem2 = false; // variavel para armazenar se a pista 2 esta em uso para aterrissagem
    private static boolean aterrissagem3 = false; // variavel para armazenar se a pista 3 esta em uso para aterrissagem

    private static int auxQntAeronavesDecolagem = 0; // variavel para armazenar a quantidade de aeronaves que decolaram
                                                     // na pista 3

    private static double tempoEsperaTotal1 = 0; // variavel para armazenar o tempo medio de espera da pista 1
    private static double tempoEsperaTotal2 = 0; // variavel para armazenar o tempo medio de espera da pista 2
    private static double tempoEsperaTotal3 = 0; // variavel para armazenar o tempo medio de espera da pista 3
    private static double tempoEsperaTotal = 0; // variavel para armazenar o tempo medio de espera total

    private static double qntTotalAeronavesSairam = 0; // variavel para armazenar a quantidade de aeronaves que sairam
                                                       // do aeroporto
    private static double tempoEsperaTotalTodasAeronavesSairam = 0; // variavel para armazenar o tempo total de espera
                                                                    // de todas as aeronaves que sairam do aeroporto
    private static double qntTotalAeronavesAtuais = 0; // variavel para armazenar a quantidade de aeronaves que estao no
                                                       // aeroporto
    private static double tempoEsperaTotalTodasAeronavesAtuais = 0; // variavel para armazenar o tempo total de espera
                                                                    // de todas as aeronaves que estao no aeroporto
    private static double qntTotalAeronaves = 0; // variavel para armazenar a quantidade de aeronaves que estao no
                                                 // aeroporto
    private static double tempoEsperaTotalTodasAeronaves = 0; // variavel para armazenar o tempo total de espera de
                                                              // todas as aeronaves que estao no aeroporto

    static enum CompanhiaAerea { // enum para armazenar as companhias aereas
        GOL,
        LATAM,
        AZUL,
        AmericanAirlines
    }

    static enum Clima { // enum para armazenar os climas
        Sol,
        ChuvaLeve,
        Neve,
        VentoForte,
        Tempestade,
        Nublado
    }

    public Aeroporto() { // construtor da classe Aeroporto
        pista1 = new Pista("Pista 1", true);
        pista2 = new Pista("Pista 2", true);
        pista3 = new Pista("Pista 3", false);

        aeronavesCriticas = new ArrayList<>();
        this.minutosSimulados = 0;
        this.estadoCritico = false;
        clima = Clima.Sol.toString();
    }

    public void simularMinuto() { // funcao para simular um minuto
        tempoEsperaTotal = 0;
        tempoEsperaTotal1 = 0;
        tempoEsperaTotal2 = 0;
        tempoEsperaTotal3 = 0;
        auxQntAeronavesDecolagem = 0;
        aterrissagem1 = false;
        aterrissagem2 = false;
        aterrissagem3 = false;

        qtdAeronavesAterrissagemPorMinuto = 0;
        qtdAeronavesDecolagemPorMinuto = 0;

        System.out.println("Simulando minuto...");
        this.minutosSimulados++;
        gerarAeronaves();

        if (minutosSimulados % 10 == 0) {
            mudarClima();
        }

        imprimirInformacoes();

        aterrissagem();
        decolagem();

        sc.nextLine();
        clearConsole();

        somarTempoEspera();
        atualizarCombustivel();

        imprimirInformacoes();
    }

    public void mudarClima() {
        Random random = new Random();
        int valor = random.nextInt(200);

        if (valor < 10) {
            clima = Clima.ChuvaLeve.toString();
        } else if (valor < 20) {
            clima = Clima.Neve.toString();
        } else if (valor < 30) {
            clima = Clima.VentoForte.toString();
        } else if (valor < 40) {
            clima = Clima.Tempestade.toString();
        } else if (valor < 50) {
            clima = Clima.Nublado.toString();
        } else {
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

    public Pista escolherPistaAterrissagem() {
        if (pista1.quantidadeAeronavesAterrissagem() < pista2.quantidadeAeronavesAterrissagem()) {
            return pista1;
        } else if (pista1.quantidadeAeronavesAterrissagem() > pista2.quantidadeAeronavesAterrissagem()) {
            return pista2;
        } else {
            // Tem que fazer a lógica ainda
            return pista1;
        }
    }

    public void adicionarAeronaveFilaDecolagem(Aeronave aeronave) {
        Pista pistaEscolhida = escolherPistaDecolagem();
        System.out.println("Pista escolhida: " + pistaEscolhida.getNome());
        FilaDeEspera filaEscolhida = pistaEscolhida.escolherFilaDecolagem();
        System.out.println("Fila escolhida: " + filaEscolhida.getNome());
        filaEscolhida.adicionarAeronave(aeronave);
    }

    public Pista escolherPistaDecolagem() {
        if (auxQntAeronavesDecolagem < 3) {
            auxQntAeronavesDecolagem++;
            return pista3;
        } else {
            if (pista1.quantidadeAeronavesDecolagem() < pista2.quantidadeAeronavesDecolagem()) {
                return pista1;
            } else if (pista1.quantidadeAeronavesDecolagem() > pista2.quantidadeAeronavesDecolagem()) {
                return pista2;
            } else {
                // Tem que fazer a lógica ainda
                return pista1;
            }
        }
    }

    public int calcularAeronavesEmEspera() {
        return pista1.quantidadeAeronaves() + pista2.quantidadeAeronaves() + pista3.quantidadeAeronaves3();
    }

    public int calcularAeronavesEmEsperaAterrissagem() {
        return pista1.quantidadeAeronavesAterrissagem() + pista2.quantidadeAeronavesAterrissagem()
                + pista3.quantidadeAeronavesAterrissagem3();
    }

    public int calcularAeronavesEmEsperaDecolagem() {
        return pista1.quantidadeAeronavesDecolagem() + pista2.quantidadeAeronavesDecolagem()
                + pista3.quantidadeAeronavesDecolagem();
    }

    private boolean verificarCombustivelCritico(Aeronave aeronave) {
        return aeronave.getCombustivel() < 5;
    }

    public void gerarAeronaves() {
        Random random = new Random();

        clearConsole();

        System.out.println("Gerando aeronaves...");

        int aeronavesAterrissagem = random.nextInt(13);

        System.out.println("\nATERRISSAGEM : \nChegando " + aeronavesAterrissagem + " aeronaves para aterrissagem...");

        for (int i = 0; i < aeronavesAterrissagem; i++) {
            System.out.println("\nAviao " + (1 + i) + " de aterrissagem.");
            int numPassageiros = random.nextInt(380) + 1;
            int combustivel = random.nextInt(15);
            ;

            String companhiaAerea = CompanhiaAerea.values()[random.nextInt(CompanhiaAerea.values().length)].toString();

            boolean passageiroEspecial = (random.nextInt(10) + 1 > 8 ? true : false);

            Aeronave aeronave = new Aeronave(numPassageiros, 0, combustivel, companhiaAerea, passageiroEspecial);
            aeronave.setIdAterrissagem(idsAeronavesAterrissagem);
            idsAeronavesAterrissagem += 2;
            qtdAeronavesAterrissagemPorMinuto++;

            adicionarAeronaveFilaAterrisagem(aeronave);
        }

        System.out.print("-------------");
        sc.nextLine();

        int aeronavesDecolagem = random.nextInt(9);
        System.out.println("\nDECOLAGEM : \n" + "Chegando " + aeronavesDecolagem + " aeronaves para decolagem...");
        for (int i = 0; i < aeronavesDecolagem; i++) {
            System.out.println("\nAviao " + (1 + i) + " de decolagem.");
            int numPassageiros = random.nextInt(380) + 1;
            int combustivel = 15;

            String companhiaAerea = CompanhiaAerea.values()[random.nextInt(CompanhiaAerea.values().length)].toString();

            boolean passageiroEspecial = (random.nextInt(10) + 1 > 8 ? true : false);

            Aeronave aeronave = new Aeronave(numPassageiros, 0, combustivel, companhiaAerea, passageiroEspecial);
            aeronave.setIdDecolagem(idsAeronavesDecolagem);
            idsAeronavesDecolagem += 2;
            qtdAeronavesDecolagemPorMinuto++;

            adicionarAeronaveFilaDecolagem(aeronave);
        }

        System.out.println("Aeronaves geradas com sucesso!");
        System.out.println("-------------");
        sc.nextLine();
        clearConsole();
    }

    public void aterrissagem() {
        if (pista1.getFilaAterrissagem1().tamanho() > pista1.getFilaAterrissagem2().tamanho()) {
            if (pista1.getFilaAterrissagem1().getFila().isEmpty()) {
                System.out.println("Não há aeronaves na fila de aterrissagem 1.");
            } else {
                System.out.println(
                        "Aeronave " + pista1.getFilaAterrissagem1().getFila().peek().getId()
                                + " aterrissando na pista 1 da fila 1.");
                somarTempoEsperaTotalTodasAeronavesSairam(
                        pista1.getFilaAterrissagem1().getFila().peek().getTempoEspera());
                pista1.getFilaAterrissagem1().removerAeronave();
                aterrissagem1 = true;
                qntTotalAeronavesSairam++;
            }
        } else {
            if (pista1.getFilaAterrissagem2().getFila().isEmpty()) {
                System.out.println("Não há aeronaves na fila de aterrissagem 2.");
            } else {
                System.out.println(
                        "Aeronave " + pista1.getFilaAterrissagem2().getFila().peek().getId()
                                + " aterrissando na pista 1 da fila 2.");
                somarTempoEsperaTotalTodasAeronavesSairam(
                        pista1.getFilaAterrissagem2().getFila().peek().getTempoEspera());
                pista1.getFilaAterrissagem2().removerAeronave();
                aterrissagem1 = true;
                qntTotalAeronavesSairam++;
            }
        }

        if (pista2.getFilaAterrissagem1().tamanho() > pista2.getFilaAterrissagem2().tamanho()) {
            if (pista2.getFilaAterrissagem1().getFila().isEmpty()) {
                System.out.println("Não há aeronaves na fila de aterrissagem 1.");
            } else {
                System.out.println(
                        "Aeronave " + pista2.getFilaAterrissagem1().getFila().peek().getId()
                                + " aterrissando na pista 2 da fila 1.");
                somarTempoEsperaTotalTodasAeronavesSairam(
                        pista2.getFilaAterrissagem1().getFila().peek().getTempoEspera());
                pista2.getFilaAterrissagem1().removerAeronave();
                aterrissagem2 = true;
                qntTotalAeronavesSairam++;
            }
        } else {
            if (pista2.getFilaAterrissagem2().getFila().isEmpty()) {
                System.out.println("Não há aeronaves na fila de aterrissagem 2.");
            } else {
                System.out.println(
                        "Aeronave " + pista2.getFilaAterrissagem2().getFila().peek().getId()
                                + " aterrissando na pista 2 da fila 2.");
                somarTempoEsperaTotalTodasAeronavesSairam(
                        pista2.getFilaAterrissagem2().getFila().peek().getTempoEspera());
                pista2.getFilaAterrissagem2().removerAeronave();
                aterrissagem2 = true;
                qntTotalAeronavesSairam++;
            }
        }

        if (pista3.getFilaAterrissagem1().getFila().isEmpty()) {
            System.out.println("Não há aeronaves na fila de aterrissagem 1.");
        } else {
            System.out.println(
                    "Aeronave " + pista3.getFilaAterrissagem1().getFila().peek().getId() + " aterrissando na pista 3.");
            somarTempoEsperaTotalTodasAeronavesSairam(pista3.getFilaAterrissagem1().getFila().peek().getTempoEspera());
            pista3.getFilaAterrissagem1().removerAeronave();
            aterrissagem3 = true;
            qntTotalAeronavesSairam++;
        }
    }

    public void decolagem() {
        if (aterrissagem1) {
            System.out.println("Nao e possivel fazer decolagem, a pista 1 esta em uso para aterrissagem.");
        } else {
            if (pista1.getFilaDecolagem().getFila().isEmpty()) {
                System.out.println("Não há aeronaves na fila de decolagem.");
            } else {
                System.out.println(
                        "Aeronave " + pista1.getFilaDecolagem().getFila().peek().getId() + " decolando na pista 1.");
                somarTempoEsperaTotalTodasAeronavesSairam(pista1.getFilaDecolagem().getFila().peek().getTempoEspera());
                pista1.getFilaDecolagem().removerAeronave();
                qntTotalAeronavesSairam++;
            }
        }

        if (aterrissagem2) {
            System.out.println("Nao e possivel fazer decolagem, a pista 2 esta em uso para aterrissagem.");
        } else {
            if (pista2.getFilaDecolagem().getFila().isEmpty()) {
                System.out.println("Não há aeronaves na fila de decolagem.");
            } else {
                System.out.println(
                        "Aeronave " + pista2.getFilaDecolagem().getFila().peek().getId() + " decolando na pista 2.");
                somarTempoEsperaTotalTodasAeronavesSairam(pista2.getFilaDecolagem().getFila().peek().getTempoEspera());
                pista2.getFilaDecolagem().removerAeronave();
                qntTotalAeronavesSairam++;
            }
        }

        if (aterrissagem3) {
            System.out.println("Nao e possivel fazer decolagem, a pista 3 esta em uso para aterrissagem.");
        } else {
            if (pista3.getFilaDecolagem().getFila().isEmpty()) {
                System.out.println("Não há aeronaves na fila de decolagem.");
            } else {
                System.out.println(
                        "Aeronave " + pista3.getFilaDecolagem().getFila().peek().getId() + " decolando na pista 3.");
                somarTempoEsperaTotalTodasAeronavesSairam(pista3.getFilaDecolagem().getFila().peek().getTempoEspera());
                pista3.getFilaDecolagem().removerAeronave();
                qntTotalAeronavesSairam++;
            }
        }

    }

    public void imprimirPistas() {
        pista1.imprimir();
        System.out.println("-------------");
        sc.nextLine();
        pista2.imprimir();
        System.out.println("-------------");
        sc.nextLine();
        pista3.imprimir();
        System.out.println("-------------");
        sc.nextLine();
    }

    public void clearConsole() {
        try {
            new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void imprimirTempoMedioDeEspera() {
        System.out.println("Tempo medio pista 1: " + pista1.recalcularTempoMedioEspera());
        System.out.println("Tempo medio da fila 1: " + pista1.getFilaAterrissagem1().tempoMedioDeEsperaFila());
        System.out.println("Tempo medio da fila 2: " + pista1.getFilaAterrissagem2().tempoMedioDeEsperaFila());
        System.out.println("Tempo medio da fila 3: " + pista1.getFilaDecolagem().tempoMedioDeEsperaFila());

        System.out.println("\nTempo medio pista 2: " + pista2.recalcularTempoMedioEspera());
        System.out.println("Tempo medio da fila 1: " + pista2.getFilaAterrissagem1().tempoMedioDeEsperaFila());
        System.out.println("Tempo medio da fila 2: " + pista2.getFilaAterrissagem2().tempoMedioDeEsperaFila());
        System.out.println("Tempo medio da fila 3: " + pista2.getFilaDecolagem().tempoMedioDeEsperaFila());

        System.out.println("\nTempo medio pista 3: " + pista3.recalcularTempoMedioEspera());
        System.out.println("Tempo medio da fila 1: " + pista3.getFilaAterrissagem1().tempoMedioDeEsperaFila());
        System.out.println("Tempo medio da fila 3: " + pista3.getFilaDecolagem().tempoMedioDeEsperaFila());

        qntTotalAeronaves();
        somarTempoEsperaTotalTodasAeronaves();

        if (tempoEsperaTotalTodasAeronaves == 0 || qntTotalAeronavesSairam == 0) {
            System.out.println("\nTempo total de espera de todas as aeronaves: 0");
            System.out.println("Total de naves: 0");
            System.out.println("Tempo medio de espera de todas as aeronaves: 0");
        } else {
            System.out.println("\nTempo total de espera de todas as aeronaves: " + tempoEsperaTotalTodasAeronaves);
            System.out.println("Total de naves: " + qntTotalAeronaves);
            System.out.println("Tempo medio de espera de todas as aeronaves: "
                    + tempoEsperaTotalTodasAeronaves / qntTotalAeronaves);
        }

    }

    public void imprimirInformacoes() {
        System.out.println("INFORMACOES: ");
        System.out.println("Aeronaves em espera: " + calcularAeronavesEmEspera());
        System.out.println("Aeronaves em espera para aterrissagem: " + calcularAeronavesEmEsperaAterrissagem());
        System.out.println("Aeronaves em espera para decolagem: " + calcularAeronavesEmEsperaDecolagem());
        System.out.println("Aeronaves que realizaram aterrissagem emergencial: " + qtdAterrissagemEmergencial);
        System.out.println("Minutos simulados: " + minutosSimulados);
        System.out.println("Clima: " + clima);

        System.out.println("\nTempo médio de espera...");
        imprimirTempoMedioDeEspera();

        System.out.println("-------------");

        System.out.println("INFORMACOES DAS PISTAS: ");
        imprimirPistas();

        sc.nextLine();
        clearConsole();
    }

    public void somarTempoEspera() {
        pista1.somarTempoEspera();
        pista2.somarTempoEspera();
        pista3.somarTempoEspera();
    }

    public void atualizarCombustivel() {
        pista1.atualizarCombustivel();
        pista2.atualizarCombustivel();
    }

    public void somarTempoEsperaTotalTodasAeronavesSairam(int tempoEspera) {
        tempoEsperaTotalTodasAeronavesSairam += tempoEspera;
    }

    public void somarTempoEsperaTotalTodasAeronavesAtuais() {
        tempoEsperaTotalTodasAeronavesAtuais = 0;
        for (Aeronave a : pista1.getFilaAterrissagem1().getFila()) {
            tempoEsperaTotalTodasAeronavesAtuais += a.getTempoEspera();
        }
    }

    public void somarTempoEsperaTotalTodasAeronaves() {
        somarTempoEsperaTotalTodasAeronavesAtuais();
        tempoEsperaTotalTodasAeronaves = tempoEsperaTotalTodasAeronavesAtuais + tempoEsperaTotalTodasAeronavesSairam;
    }

    public void qntTotalAeronavesAtuais() {
        qntTotalAeronavesAtuais = 0;
        qntTotalAeronavesAtuais += pista1.quantidadeAeronaves();
        qntTotalAeronavesAtuais += pista2.quantidadeAeronaves();
        qntTotalAeronavesAtuais += pista3.quantidadeAeronaves3();
    }

    public void qntTotalAeronaves() {
        qntTotalAeronavesAtuais();
        qntTotalAeronaves = qntTotalAeronavesAtuais + qntTotalAeronavesSairam;
    }
}