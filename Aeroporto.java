import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Random;
import java.util.Scanner;

public class Aeroporto {
    private Scanner sc = new Scanner(System.in);

    public static Queue<Aeronave> filaAeronavesDecolagemArquivo = new LinkedList<Aeronave>();
    public static Queue<Aeronave> filaAeronavesAterrissagemArquivo = new LinkedList<Aeronave>();

    private Pista pista1;
    private Pista pista2;
    private Pista pista3;

    private String clima;

    private int qtdAterrissagemEmergencial;
    private int minutosSimulados;

    public static int idsAeronavesAterrissagem = 1;
    public static int idsAeronavesDecolagem = 2;

    private static boolean aterrissagem1 = false;
    private static boolean aterrissagem2 = false;
    private static boolean aterrissagem3 = false;

    private static int auxContQntAeronavesFilaDecolagem = 0;

    private static int qntTotalAeronavesSairam = 0;

    private static double tempoEsperaTotalTodasAeronavesSairam = 0;

    private static List<Aeronave> aeronavesCairam = new ArrayList<Aeronave>();
    public static List<Aeronave> aeronavesEmEmergencia = new ArrayList<>();

    public static int chegaramAterrissagem = 0;
    public static int chegaramDecolagem = 0;

    public int qntAterrissaram;
    public int qntDecolaram;

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_RED = "\u001B[31m";

    static enum CompanhiaAerea {
        GOL,
        LATAM,
        AZUL,
        AmericanAirlines
    }

    static enum Clima {
        Sol,
        Chuva,
        Neve,
        Tempestade,
        Nublado
    }

    public Aeroporto() {
        pista1 = new Pista("Pista 1", true);
        pista2 = new Pista("Pista 2", true);
        pista3 = new Pista("Pista 3", false);

        this.minutosSimulados = 0;
        clima = Clima.Sol.toString();
    }

    public Aeronave acharAeronaveComPassageiroEspecial(FilaDeEspera fila) {
        List<Aeronave> filaPassageirosEspeciais = new LinkedList<>();
        for (Aeronave a : fila.getFila()) {
            if (a.getPassageiroEspecial()) {
                filaPassageirosEspeciais.add(a);
            }
        }

        if (filaPassageirosEspeciais.isEmpty())
            return null;

        return filaPassageirosEspeciais.getFirst();
    }

    public Aeronave acharAeronaveMenorCombustivel(FilaDeEspera fila) {
        List<Aeronave> filaPoucoCombustivel = new LinkedList<>();
        for (Aeronave a : fila.getFila()) {
            if (a.getCombustivel() < 6) {
                filaPoucoCombustivel.add(a);
            }
        }

        if (filaPoucoCombustivel.isEmpty())
            return null;

        filaPoucoCombustivel.sort((a1, a2) -> a1.getCombustivel() - a2.getCombustivel());

        return filaPoucoCombustivel.getFirst();
    }

    public void adicionarAeronaveFilaAterrisagem(Aeronave aeronave) {
        escolherPistaAterrissagem().escolherFilaAterrissagem().adicionarAeronave(aeronave);
    }

    public void adicionarAeronaveFilaDecolagem(Aeronave aeronave) {
        escolherPistaDecolagem().getFilaDecolagem().adicionarAeronave(aeronave);
    }

    public void aterrissagem() {
        boolean passageiroEspecial = false;

        if (!pista1.getFilaAterrissagem1().getFila().isEmpty() || !pista1.getFilaAterrissagem2().getFila().isEmpty()) {
            if (escolherFilaParaAterrissar(pista1.getFilaAterrissagem1(), pista1.getFilaAterrissagem2())) {
                Aeronave a = null;

                if (!clima.equals(Clima.Sol.toString())) {
                    a = acharAeronaveComPassageiroEspecial(pista1.getFilaAterrissagem1());
                }

                if (a == null || (a.getCombustivel() > 1 && pista1.getFilaAterrissagem1().getMenorCombustivel() == 1)) {
                    a = acharAeronaveMenorCombustivel(pista1.getFilaAterrissagem1());
                } else {
                    passageiroEspecial = true;
                }

                if (a != null) {
                    pista1.getFilaAterrissagem1().getFila().remove(a);
                    pista1.setQtdAterrissagensEmergenciais(pista1.getQtdAterrissagensEmergenciais() + 1);
                    pista1.getFilaAterrissagem1().setQtdAterrissagensEmergenciais(
                            pista1.getFilaAterrissagem1().getQtdAterrissagensEmergenciais() + 1);
                    setQntAterrissagemEmergencial(qtdAterrissagemEmergencial + 1);
                    somarTempoEsperaTotalTodasAeronavesSairam(a.getTempoEspera());
                    pista1.getFilaAterrissagem1().setTempoEsperaAeronavesSairam(
                            pista1.getFilaAterrissagem1().getTempoEsperaAeronavesSairam() + a.getTempoEspera());

                    System.out.print(
                            "Aeronave " + a.getId() + " aterrissando na pista 1 da fila 1, com emergencia por ");
                    if (passageiroEspecial) {
                        System.out.println("possuir passageiro com necessidades especiais.");
                    } else {
                        System.out.println("estar com combustível baixo.");
                    }
                } else {
                    somarTempoEsperaTotalTodasAeronavesSairam(
                            pista1.getFilaAterrissagem1().getFila().peek().getTempoEspera());
                    pista1.getFilaAterrissagem1()
                            .setTempoEsperaAeronavesSairam(pista1.getFilaAterrissagem1().getTempoEsperaAeronavesSairam()
                                    + pista1.getFilaAterrissagem1().getFila().peek().getTempoEspera());
                    System.out.println("Aeronave " + pista1.getFilaAterrissagem1().getFila().peek().getId()
                            + " aterrissando na pista 1 da fila 1.");
                    pista1.getFilaAterrissagem1().removerAeronave();
                }

                pista1.getFilaAterrissagem1()
                        .setQtdAeronavesSairam(pista1.getFilaAterrissagem1().getQtdAeronavesSairam() + 1);
                aterrissagem1 = true;
                qntTotalAeronavesSairam++;
                setQntAterrissaram(qntAterrissaram + 1);
            } else {
                Aeronave a = null;

                if (!clima.equals(Clima.Sol.toString())) {
                    a = acharAeronaveComPassageiroEspecial(pista1.getFilaAterrissagem2());
                }

                if (a == null || (a.getCombustivel() > 1 && pista1.getFilaAterrissagem2().getMenorCombustivel() == 1)) {
                    a = acharAeronaveMenorCombustivel(pista1.getFilaAterrissagem2());
                } else {
                    passageiroEspecial = true;
                }

                if (a != null) {
                    pista1.getFilaAterrissagem2().getFila().remove(a);
                    pista1.setQtdAterrissagensEmergenciais(pista1.getQtdAterrissagensEmergenciais() + 1);
                    pista1.getFilaAterrissagem2().setQtdAterrissagensEmergenciais(
                            pista1.getFilaAterrissagem2().getQtdAterrissagensEmergenciais() + 1);
                    setQntAterrissagemEmergencial(qtdAterrissagemEmergencial + 1);
                    somarTempoEsperaTotalTodasAeronavesSairam(a.getTempoEspera());
                    pista1.getFilaAterrissagem2().setTempoEsperaAeronavesSairam(
                            pista1.getFilaAterrissagem2().getTempoEsperaAeronavesSairam() + a.getTempoEspera());
                    System.out.print(
                            "Aeronave " + a.getId() + " aterrissando na pista 1 da fila 2, com emergencia por ");
                    if (passageiroEspecial) {
                        System.out.println("possuir passageiro com necessidades especiais.");
                    } else {
                        System.out.println("estar com combustível baixo.");
                    }
                } else {
                    somarTempoEsperaTotalTodasAeronavesSairam(
                            pista1.getFilaAterrissagem2().getFila().peek().getTempoEspera());
                    pista1.getFilaAterrissagem2()
                            .setTempoEsperaAeronavesSairam(pista1.getFilaAterrissagem2().getTempoEsperaAeronavesSairam()
                                    + pista1.getFilaAterrissagem2().getFila().peek().getTempoEspera());
                    System.out.println("Aeronave " + pista1.getFilaAterrissagem2().getFila().peek().getId()
                            + " aterrissando na pista 1 da fila 2.");
                    pista1.getFilaAterrissagem2().removerAeronave();
                }

                pista1.getFilaAterrissagem2()
                        .setQtdAeronavesSairam(pista1.getFilaAterrissagem2().getQtdAeronavesSairam() + 1);
                aterrissagem1 = true;
                qntTotalAeronavesSairam++;
                setQntAterrissaram(qntAterrissaram + 1);
            }
        }

        passageiroEspecial = false;
        if (!pista2.getFilaAterrissagem1().getFila().isEmpty() || !pista2.getFilaAterrissagem2().getFila().isEmpty()) {
            if (escolherFilaParaAterrissar(pista2.getFilaAterrissagem1(), pista2.getFilaAterrissagem2())) {
                Aeronave a = null;

                if (!clima.equals(Clima.Sol.toString())) {
                    a = acharAeronaveComPassageiroEspecial(pista2.getFilaAterrissagem1());
                }

                if (a == null || (a.getCombustivel() > 1 && pista2.getFilaAterrissagem1().getMenorCombustivel() == 1)) {
                    a = acharAeronaveMenorCombustivel(pista2.getFilaAterrissagem1());
                } else {
                    passageiroEspecial = true;
                }

                if (a != null) {
                    pista2.getFilaAterrissagem1().getFila().remove(a);
                    setQntAterrissagemEmergencial(qtdAterrissagemEmergencial + 1);
                    pista2.setQtdAterrissagensEmergenciais(pista2.getQtdAterrissagensEmergenciais() + 1);
                    pista2.getFilaAterrissagem1().setQtdAterrissagensEmergenciais(
                            pista2.getFilaAterrissagem1().getQtdAterrissagensEmergenciais() + 1);
                    somarTempoEsperaTotalTodasAeronavesSairam(a.getTempoEspera());
                    pista2.getFilaAterrissagem1().setTempoEsperaAeronavesSairam(
                            pista2.getFilaAterrissagem1().getTempoEsperaAeronavesSairam() + a.getTempoEspera());

                    System.out.print(
                            "Aeronave " + a.getId() + " aterrissando na pista 2 da fila 1, com emergencia por ");
                    if (passageiroEspecial) {
                        System.out.println("possuir passageiro com necessidades especiais.");
                    } else {
                        System.out.println("estar com combustível baixo.");
                    }
                } else {
                    somarTempoEsperaTotalTodasAeronavesSairam(
                            pista2.getFilaAterrissagem1().getFila().peek().getTempoEspera());
                    pista2.getFilaAterrissagem1()
                            .setTempoEsperaAeronavesSairam(pista2.getFilaAterrissagem1().getTempoEsperaAeronavesSairam()
                                    + pista2.getFilaAterrissagem1().getFila().peek().getTempoEspera());
                    System.out.println("Aeronave " + pista2.getFilaAterrissagem1().getFila().peek().getId()
                            + " aterrissando na pista 2 da fila 1.");
                    pista2.getFilaAterrissagem1().removerAeronave();
                }

                pista2.getFilaAterrissagem1()
                        .setQtdAeronavesSairam(pista2.getFilaAterrissagem1().getQtdAeronavesSairam() + 1);
                aterrissagem2 = true;
                qntTotalAeronavesSairam++;
                setQntAterrissaram(qntAterrissaram + 1);
            } else {
                Aeronave a = null;

                if (!clima.equals(Clima.Sol.toString())) {
                    a = acharAeronaveComPassageiroEspecial(pista2.getFilaAterrissagem2());
                }

                if (a == null || (a.getCombustivel() > 1 && pista2.getFilaAterrissagem2().getMenorCombustivel() == 1)) {
                    a = acharAeronaveMenorCombustivel(pista2.getFilaAterrissagem2());
                } else {
                    passageiroEspecial = true;
                }

                if (a != null) {
                    pista2.getFilaAterrissagem2().getFila().remove(a);
                    setQntAterrissagemEmergencial(qtdAterrissagemEmergencial + 1);
                    pista2.setQtdAterrissagensEmergenciais(pista2.getQtdAterrissagensEmergenciais() + 1);
                    pista2.getFilaAterrissagem2().setQtdAterrissagensEmergenciais(
                            pista2.getFilaAterrissagem2().getQtdAterrissagensEmergenciais() + 1);
                    somarTempoEsperaTotalTodasAeronavesSairam(a.getTempoEspera());
                    pista2.getFilaAterrissagem2().setTempoEsperaAeronavesSairam(
                            pista2.getFilaAterrissagem2().getTempoEsperaAeronavesSairam() + a.getTempoEspera());

                    System.out.print(
                            "Aeronave " + a.getId() + " aterrissando na pista 2 da fila 2, com emergencia por ");
                    if (passageiroEspecial) {
                        System.out.println("possuir passageiro com necessidades especiais.");
                    } else {
                        System.out.println("estar com combustível baixo.");
                    }
                } else {
                    somarTempoEsperaTotalTodasAeronavesSairam(
                            pista2.getFilaAterrissagem2().getFila().peek().getTempoEspera());
                    pista2.getFilaAterrissagem2()
                            .setTempoEsperaAeronavesSairam(pista2.getFilaAterrissagem2().getTempoEsperaAeronavesSairam()
                                    + pista2.getFilaAterrissagem2().getFila().peek().getTempoEspera());

                    System.out.println("Aeronave " + pista2.getFilaAterrissagem2().getFila().peek().getId()
                            + " aterrissando na pista 2 da fila 2.");
                    pista2.getFilaAterrissagem2().removerAeronave();
                }

                pista2.getFilaAterrissagem2()
                        .setQtdAeronavesSairam(pista2.getFilaAterrissagem2().getQtdAeronavesSairam() + 1);
                aterrissagem2 = true;
                qntTotalAeronavesSairam++;
                setQntAterrissaram(qntAterrissaram + 1);
            }
        }

        passageiroEspecial = false;
        if (!pista3.getFilaAterrissagem1().getFila().isEmpty()) {
            Aeronave a = null;

            if (!clima.equals(Clima.Sol.toString())) {
                a = acharAeronaveComPassageiroEspecial(pista3.getFilaAterrissagem1());
            }

            if (a == null || (a.getCombustivel() > 1 && pista3.getFilaAterrissagem1().getMenorCombustivel() == 1)) {
                a = acharAeronaveMenorCombustivel(pista3.getFilaAterrissagem1());
            } else {
                passageiroEspecial = true;
            }

            if (a != null) {
                pista3.getFilaAterrissagem1().getFila().remove(a);
                setQntAterrissagemEmergencial(qtdAterrissagemEmergencial + 1);
                pista3.setQtdAterrissagensEmergenciais(pista3.getQtdAterrissagensEmergenciais() + 1);
                pista3.getFilaAterrissagem1().setQtdAterrissagensEmergenciais(
                        pista3.getFilaAterrissagem1().getQtdAterrissagensEmergenciais() + 1);
                somarTempoEsperaTotalTodasAeronavesSairam(a.getTempoEspera());

                pista3.getFilaAterrissagem1().setTempoEsperaAeronavesSairam(
                        pista3.getFilaAterrissagem1().getTempoEsperaAeronavesSairam() + a.getTempoEspera());

                System.out.print("Aeronave " + a.getId() + " aterrissando na pista 3, com emergencia por ");
                if (passageiroEspecial) {
                    System.out.println("possuir passageiro com necessidades especiais.");
                } else {
                    System.out.println("estar com combustível baixo.");
                }
            } else {
                somarTempoEsperaTotalTodasAeronavesSairam(
                        pista3.getFilaAterrissagem1().getFila().peek().getTempoEspera());

                pista3.getFilaAterrissagem1()
                        .setTempoEsperaAeronavesSairam(pista3.getFilaAterrissagem1().getTempoEsperaAeronavesSairam()
                                + pista3.getFilaAterrissagem1().getFila().peek().getTempoEspera());

                System.out.println("Aeronave " + pista3.getFilaAterrissagem1().getFila().peek().getId()
                        + " aterrissando na pista 3.");
                pista3.getFilaAterrissagem1().removerAeronave();
            }

            pista3.getFilaAterrissagem1()
                    .setQtdAeronavesSairam(pista3.getFilaAterrissagem1().getQtdAeronavesSairam() + 1);
            aterrissagem3 = true;
            qntTotalAeronavesSairam++;
            setQntAterrissaram(qntAterrissaram + 1);
        }
    }

    public void atualizarCombustivel() {
        pista1.atualizarCombustivel();
        pista2.atualizarCombustivel();
        pista3.atualizarCombustivel();
    }

    public int calcularAeronavesEmEspera() {
        return pista1.quantidadeAeronaves() + pista2.quantidadeAeronaves() + pista3.quantidadeAeronaves();
    }

    public int calcularAeronavesEmEsperaAterrissagem() {
        return pista1.quantidadeAeronavesAterrissagem() + pista2.quantidadeAeronavesAterrissagem()
                + pista3.quantidadeAeronavesAterrissagem();
    }

    public int calcularAeronavesEmEsperaDecolagem() {
        return pista1.quantidadeAeronavesDecolagem() + pista2.quantidadeAeronavesDecolagem()
                + pista3.quantidadeAeronavesDecolagem();
    }

    public void clearConsole() {
        try {
            new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void decolagem() {
        if (!aterrissagem1) {
            if (!pista1.getFilaDecolagem().getFila().isEmpty()) {
                System.out.println(
                        "Aeronave " + pista1.getFilaDecolagem().getFila().peek().getId() + " decolando na pista 1.");
                somarTempoEsperaTotalTodasAeronavesSairam(pista1.getFilaDecolagem().getFila().peek().getTempoEspera());
                pista1.getFilaDecolagem().setQtdAeronavesSairam(pista1.getFilaDecolagem().getQtdAeronavesSairam() + 1);
                pista1.getFilaDecolagem()
                        .setTempoEsperaAeronavesSairam(pista1.getFilaDecolagem().getTempoEsperaAeronavesSairam()
                                + pista1.getFilaDecolagem().getFila().peek().getTempoEspera());
                pista1.getFilaDecolagem().removerAeronave();
                qntTotalAeronavesSairam++;
                setQntDecolaram(qntDecolaram + 1);
            }
        }

        if (!aterrissagem2) {
            if (!pista2.getFilaDecolagem().getFila().isEmpty()) {
                System.out.println(
                        "Aeronave " + pista2.getFilaDecolagem().getFila().peek().getId() + " decolando na pista 2.");
                somarTempoEsperaTotalTodasAeronavesSairam(pista2.getFilaDecolagem().getFila().peek().getTempoEspera());
                pista2.getFilaDecolagem().setQtdAeronavesSairam(pista2.getFilaDecolagem().getQtdAeronavesSairam() + 1);
                pista2.getFilaDecolagem()
                        .setTempoEsperaAeronavesSairam(pista2.getFilaDecolagem().getTempoEsperaAeronavesSairam()
                                + pista2.getFilaDecolagem().getFila().peek().getTempoEspera());
                pista2.getFilaDecolagem().removerAeronave();
                qntTotalAeronavesSairam++;
                setQntDecolaram(qntDecolaram + 1);
            }
        }

        if (!aterrissagem3) {
            if (!pista3.getFilaDecolagem().getFila().isEmpty()) {
                System.out.println(
                        "Aeronave " + pista3.getFilaDecolagem().getFila().peek().getId() + " decolando na pista 3.");
                somarTempoEsperaTotalTodasAeronavesSairam(pista3.getFilaDecolagem().getFila().peek().getTempoEspera());
                pista3.getFilaDecolagem().setQtdAeronavesSairam(pista3.getFilaDecolagem().getQtdAeronavesSairam() + 1);
                pista3.getFilaDecolagem()
                        .setTempoEsperaAeronavesSairam(pista3.getFilaDecolagem().getTempoEsperaAeronavesSairam()
                                + pista3.getFilaDecolagem().getFila().peek().getTempoEspera());
                pista3.getFilaDecolagem().removerAeronave();
                qntTotalAeronavesSairam++;
                setQntDecolaram(qntDecolaram + 1);
            }
        }

    }

    public void distribuicaoAeronaves() {
        if (pista1.quantidadeAeronavesAterrissagem() == 0) {
            if (pista3.quantidadeAeronavesAterrissagem() <= 1) {
                if (pista3.quantidadeAeronaves() > 1 && pista1.quantidadeAeronavesDecolagem() == 0) {
                    pista1.getFilaDecolagem().adicionarAeronave(pista3.getFilaDecolagem().getFila().peek());
                    pista3.getFilaDecolagem().removerAeronave();
                }
            } else {
                pista1.getFilaAterrissagem1().adicionarAeronave(pista3.getFilaAterrissagem1().getFila().peek());
                pista3.getFilaAterrissagem1().removerAeronave();
            }
        }

        if (pista2.quantidadeAeronavesAterrissagem() == 0) {
            if (pista3.quantidadeAeronavesAterrissagem() <= 1) {
                if (pista3.quantidadeAeronaves() > 1 && pista2.quantidadeAeronavesDecolagem() == 0) {
                    pista2.getFilaDecolagem().adicionarAeronave(pista3.getFilaDecolagem().getFila().peek());
                    pista3.getFilaDecolagem().removerAeronave();
                }
            } else {
                pista2.getFilaAterrissagem1().adicionarAeronave(pista3.getFilaAterrissagem1().getFila().peek());
                pista3.getFilaAterrissagem1().removerAeronave();
            }
        }
    }

    public boolean escolherFilaParaAterrissar(FilaDeEspera fila1, FilaDeEspera fila2) {
        List<Aeronave> filaPoucoCombustivel1 = new LinkedList<>();
        List<Aeronave> filaPoucoCombustivel2 = new LinkedList<>();

        for (Aeronave a : fila1.getFila()) {
            if (a.getCombustivel() < 6) {
                filaPoucoCombustivel1.add(a);
            }
        }

        for (Aeronave a : fila2.getFila()) {
            if (a.getCombustivel() < 6) {
                filaPoucoCombustivel2.add(a);
            }
        }

        if (filaPoucoCombustivel1.isEmpty() && filaPoucoCombustivel2.isEmpty()) {
            return !fila1.getFila().isEmpty();
        } else if (filaPoucoCombustivel1.isEmpty()) {
            return false;
        } else if (filaPoucoCombustivel2.isEmpty()) {
            return true;
        } else {
            filaPoucoCombustivel1.sort((a1, a2) -> a1.getCombustivel() - a2.getCombustivel());
            filaPoucoCombustivel2.sort((a1, a2) -> a1.getCombustivel() - a2.getCombustivel());

            if (filaPoucoCombustivel1.getFirst().getCombustivel() < filaPoucoCombustivel2.getFirst().getCombustivel()) {
                return true;
            } else if (filaPoucoCombustivel1.getFirst().getCombustivel() > filaPoucoCombustivel2.getFirst()
                    .getCombustivel()) {
                return false;
            } else {
                int qntPassageirosEspeciais1 = 0;
                int qntPassageirosEspeciais2 = 0;

                for (Aeronave a : filaPoucoCombustivel1) {
                    if (a.getPassageiroEspecial()) {
                        qntPassageirosEspeciais1++;
                    }
                }

                for (Aeronave a : filaPoucoCombustivel2) {
                    if (a.getPassageiroEspecial()) {
                        qntPassageirosEspeciais2++;
                    }
                }

                if (qntPassageirosEspeciais1 > qntPassageirosEspeciais2) {
                    return true;
                } else
                    return qntPassageirosEspeciais1 >= qntPassageirosEspeciais2;
            }
        }
    }

    public Pista escolherPistaAterrissagem() {
        if (pista1.quantidadeAeronavesAterrissagem() <= pista2.quantidadeAeronavesAterrissagem()) {
            return pista1;
        } else {
            return pista2;
        }
    }

    public Pista escolherPistaDecolagem() {
        if (auxContQntAeronavesFilaDecolagem < 4) {
            auxContQntAeronavesFilaDecolagem++;
            return pista3;
        } else {
            if (pista1.quantidadeAeronavesDecolagem() <= pista2.quantidadeAeronavesDecolagem()) {
                return pista1;
            } else {
                return pista2;
            }
        }
    }

    public void gerarAeronaves() {
        gerarAeronaveAterrissagem();
        gerarAeronaveDecolagem();
        System.out.println("Aeronaves geradas com sucesso!");
        System.out.println("-------------");
    }

    private void gerarAeronaveAterrissagem() {
        Random random = new Random();

        int aeronavesAterrissagem = random.nextInt(13);
        chegaramAterrissagem = aeronavesAterrissagem;

        for (int i = 0; i < aeronavesAterrissagem; i++) {
            int numPassageiros = random.nextInt(380) + 1;
            int combustivel = random.nextInt(15) + 1;

            String companhiaAerea = CompanhiaAerea.values()[random.nextInt(CompanhiaAerea.values().length)].toString();

            boolean passageiroEspecial = random.nextBoolean();

            Aeronave aeronave = new Aeronave(numPassageiros, 0, combustivel, companhiaAerea, passageiroEspecial);
            aeronave.setIdAterrissagem(idsAeronavesAterrissagem);
            idsAeronavesAterrissagem += 2;

            adicionarAeronaveFilaAterrisagem(aeronave);
        }
    }

    private void gerarAeronaveDecolagem() {
        Random random = new Random();

        int aeronavesDecolagem = random.nextInt(9);
        chegaramDecolagem = aeronavesDecolagem;

        for (int i = 0; i < aeronavesDecolagem; i++) {
            int numPassageiros = random.nextInt(380) + 1;
            int combustivel = 15;

            String companhiaAerea = CompanhiaAerea.values()[random.nextInt(CompanhiaAerea.values().length)].toString();

            boolean passageiroEspecial = random.nextBoolean();

            Aeronave aeronave = new Aeronave(numPassageiros, 0, combustivel, companhiaAerea, passageiroEspecial);
            aeronave.setIdDecolagem(idsAeronavesDecolagem);
            idsAeronavesDecolagem += 2;

            adicionarAeronaveFilaDecolagem(aeronave);
        }
    }

    public Pista getPista1() {
        return pista1;
    }

    public Pista getPista2() {
        return pista2;
    }

    public Pista getPista3() {
        return pista3;
    }

    public int getQtdAeronavesEmergencia() {
        aeronavesEmEmergencia = new ArrayList<>();

        int qtd = 0;

        for (Aeronave a : pista1.getFilaAterrissagem1().getFila()) {
            if (a.verificarCombustivelCritico()) {
                Aeroporto.aeronavesEmEmergencia.add(a);
                qtd++;
            }
        }

        for (Aeronave a : pista1.getFilaAterrissagem2().getFila()) {
            if (a.verificarCombustivelCritico()) {
                Aeroporto.aeronavesEmEmergencia.add(a);
                qtd++;
            }
        }

        for (Aeronave a : pista2.getFilaAterrissagem1().getFila()) {
            if (a.verificarCombustivelCritico()) {
                Aeroporto.aeronavesEmEmergencia.add(a);
                qtd++;
            }
        }

        for (Aeronave a : pista2.getFilaAterrissagem2().getFila()) {
            if (a.verificarCombustivelCritico()) {
                Aeroporto.aeronavesEmEmergencia.add(a);
                qtd++;
            }
        }

        for (Aeronave a : pista3.getFilaAterrissagem1().getFila()) {
            if (a.verificarCombustivelCritico()) {
                Aeroporto.aeronavesEmEmergencia.add(a);
                qtd++;
            }
        }

        return qtd;
    }

    public void imprimirAcontecimentosMinuto() {
        System.out.println("ACONTECIMENTOD DO MINUTO " + minutosSimulados + ": ");
        System.out.println("Clima: " + clima);
        System.out.println("Total de aeronaves que chegaram: " + (chegaramAterrissagem + chegaramDecolagem));
        System.out.println("Total de aeronaves que chegaram para aterrissar: " + chegaramAterrissagem);
        System.out.println("Total de aeronaves que chegaram para decolar: " + chegaramDecolagem);
        System.out.println("Total de aeronaves que aterrissaram: " + this.qntAterrissaram);
        System.out.println("Total de aeronaves que decolaram: " + this.qntDecolaram);
        System.out.println("Total de aeronaves que cairam: " + aeronavesCairam.size());
    }

    public void imprimirInformacoesAeroporto() {
        System.out.println("INFORMACOES DO AEROPORTO: ");
        System.out.println("Tempo medio de espera global: " + tempoMedioTotal());
        System.out.println(
                "Quantidade de aeronaves que realizaram aterrissagens emergenciais: " + qtdAterrissagemEmergencial);
        System.out.println("Total de aeronaves em espera: " + calcularAeronavesEmEspera());
        System.out
                .println("Total de aeronaves em espera para aterrissagem: " + calcularAeronavesEmEsperaAterrissagem());
        System.out.println("Total de aeronaves em espera para decolagem: " + calcularAeronavesEmEsperaDecolagem());
        System.out.println("Minutos simulados: " + minutosSimulados);
        System.out.println("Clima: " + clima);
    }

    public void imprimirSituacaoCombustivel() {
        System.out.println("COMBUSTIVEL CRITICO: ");
        System.out.println("Pista 1: ");
        pista1.getFilaAterrissagem1().verificarCombustivelCritico();

        System.out.println("\nPista 2: ");
        pista1.getFilaAterrissagem2().verificarCombustivelCritico();

        System.out.println("\nPista 3: ");
        pista1.getFilaDecolagem().verificarCombustivelCritico();
    }

    public void imprimirTodasAeronaves() {
        System.out.println("AERONAVES: ");

        System.out.println("- Pista 1: ");
        pista1.imprimir();
        System.out.println("-------------");

        System.out.println("- Pista 2: ");
        pista2.imprimir();
        System.out.println("-------------");

        System.out.println("- Pista 3: ");
        pista3.imprimir();
        System.out.println("-------------");
    }

    public void lerAeronave() {
        Random random = new Random();

        clearConsole();

        if (!filaAeronavesAterrissagemArquivo.isEmpty()) {
            int aeronavesAterrissagem = random.nextInt(13);
            chegaramAterrissagem = aeronavesAterrissagem;

            if (filaAeronavesAterrissagemArquivo.size() < aeronavesAterrissagem) {
                aeronavesAterrissagem = filaAeronavesAterrissagemArquivo.size();
            }

            for (int i = 0; i < aeronavesAterrissagem; i++) {
                Aeronave aeronave = filaAeronavesAterrissagemArquivo.peek();
                filaAeronavesAterrissagemArquivo.remove();
                adicionarAeronaveFilaAterrisagem(aeronave);
            }
        } else {
            chegaramAterrissagem = 0;
        }

        if (!filaAeronavesDecolagemArquivo.isEmpty()) {
            int aeronavesDecolagem = random.nextInt(9);
            chegaramDecolagem = aeronavesDecolagem;

            if (filaAeronavesDecolagemArquivo.size() < aeronavesDecolagem) {
                aeronavesDecolagem = filaAeronavesDecolagemArquivo.size();
            }

            for (int i = 0; i < aeronavesDecolagem; i++) {
                Aeronave aeronave = filaAeronavesDecolagemArquivo.peek();
                filaAeronavesDecolagemArquivo.remove();
                adicionarAeronaveFilaDecolagem(aeronave);
            }
        } else {
            chegaramDecolagem = 0;
        }

        System.out.println("Aeronaves lidas com sucesso!");
        System.out.println("-------------");
    }

    public void mudarClima() {
        Random random = new Random();
        int valor = random.nextInt(60);

        clima = (valor < 10) ? Clima.Chuva.toString()
                : (valor < 20) ? Clima.Neve.toString()
                        : (valor < 30) ? Clima.Tempestade.toString()
                                : (valor < 40) ? Clima.Nublado.toString() : Clima.Sol.toString();
    }

    public double qntTotalAeronaves() {
        return pista1.quantidadeAeronaves() + pista2.quantidadeAeronaves() + pista3.quantidadeAeronaves()
                + qntTotalAeronavesSairam;
    }

    public void setQntAterrissagemEmergencial(int qntAterrissagemEmergencial) {
        this.qtdAterrissagemEmergencial = qntAterrissagemEmergencial;
    }

    public void setQntAterrissaram(int qntAterrissaram) {
        this.qntAterrissaram = qntAterrissaram;
    }

    public void setQntDecolaram(int qntDecolaram) {
        this.qntDecolaram = qntDecolaram;
    }

    public void simularMinuto() {
        aeronavesCairam = new ArrayList<>();
        auxContQntAeronavesFilaDecolagem = 0;
        aterrissagem1 = false;
        aterrissagem2 = false;
        aterrissagem3 = false;

        System.out.println("Simulando minuto...");
        this.minutosSimulados++;
        atualizarCombustivel();

        clearConsole();
        gerarAeronaves();

        if (minutosSimulados % 10 == 0) {
            mudarClima();
        }

        distribuicaoAeronaves();

        System.out.println("ATERRISSANDO...");
        aterrissagem();
        System.out.println("-------------");
        System.out.println("DECOLANDO...");
        decolagem();
        System.out.println("-------------\n");

        somarTempoEspera();
        verificarCombustivelCritico();
        System.out.println();

        if (getQtdAeronavesEmergencia() > 4) {
            System.out.println(ANSI_RED + "AEROPORTO EM ESTADO CRÍTICO" + ANSI_RESET);
            System.out.println("Aeronaves em emergencia: ");
            for (Aeronave a : aeronavesEmEmergencia) {
                System.out.println("Aeronave " + a.getId() + " com combustivel critico. (" + a.getCombustivel() + ")");
            }
            System.out.println();
        }

        imprimirAcontecimentosMinuto();

        System.out.println("\nPressione ENTER para continuar...");
        sc.nextLine();
        clearConsole();
    }

    public void simularMinutoArquivo() {
        aeronavesCairam = new ArrayList<>();
        auxContQntAeronavesFilaDecolagem = 0;
        aterrissagem1 = false;
        aterrissagem2 = false;
        aterrissagem3 = false;

        System.out.println("Simulando minuto...");
        this.minutosSimulados++;
        atualizarCombustivel();
        lerAeronave();

        if (minutosSimulados % 10 == 0) {
            mudarClima();
        }

        distribuicaoAeronaves();

        System.out.println("ATERRISSANDO...");
        aterrissagem();
        System.out.println("-------------");
        System.out.println("DECOLANDO...");
        decolagem();
        System.out.println("-------------\n");

        somarTempoEspera();

        verificarCombustivelCritico();
        System.out.println();

        if (getQtdAeronavesEmergencia() > 4) {
            System.out.println(ANSI_RED + "AEROPORTO EM ESTADO CRÍTICO" + ANSI_RESET);
            System.out.println("Aeronaves em emergencia: ");
            for (Aeronave a : aeronavesEmEmergencia) {
                System.out.println("Aeronave " + a.getId() + " com combustivel critico. (" + a.getCombustivel() + ")");
            }
            System.out.println();
        }

        imprimirAcontecimentosMinuto();

        System.out.println("\nPressione ENTER para continuar...");
        sc.nextLine();
        clearConsole();
    }

    public void somarTempoEspera() {
        pista1.somarTempoEspera();
        pista2.somarTempoEspera();
        pista3.somarTempoEspera();
    }

    public void somarTempoEsperaTotalTodasAeronavesSairam(int tempoEspera) {
        tempoEsperaTotalTodasAeronavesSairam += tempoEspera;
    }

    public double tempoMedioTotal() {
        if (qntTotalAeronaves() == 0) {
            return 0;
        } else {
            return tempoEsperaTotalTodasAeronaves() / qntTotalAeronaves();
        }
    }

    public double tempoEsperaTotalTodasAeronavesAtuais() {
        return pista1.tempoEsperaTotalPista() + pista2.tempoEsperaTotalPista() + pista3.tempoEsperaTotalPista();
    }

    public double tempoEsperaTotalTodasAeronaves() {
        return tempoEsperaTotalTodasAeronavesAtuais() + tempoEsperaTotalTodasAeronavesSairam;
    }

    private void verificarCombustivelCritico() {
        List<Aeronave> filaCopy3 = new ArrayList<>(pista3.getFilaAterrissagem1().getFila());
        for (Aeronave aeronave : filaCopy3) {
            if (aeronave.getCombustivel() == 0) {
                System.out.println("Aeronave " + aeronave.getId() + " caiu por falta de combustivel.");
                aeronavesCairam.add(aeronave);
                pista3.getFilaAterrissagem1().getFila().remove(aeronave);
            } else if (aeronave.getCombustivel() < 4) {
                System.out.println("Aeronave " + aeronave.getId() + " esta com combustivel muito critico.");
            }
        }

        List<Aeronave> filaCopy11 = new ArrayList<>(pista1.getFilaAterrissagem1().getFila());
        for (Aeronave aeronave : filaCopy11) {
            if (aeronave.getCombustivel() == 0) {
                System.out.println("Aeronave " + aeronave.getId() + " caiu por falta de combustivel.");
                aeronavesCairam.add(aeronave);
                pista1.getFilaAterrissagem1().getFila().remove(aeronave);
            } else if (aeronave.getCombustivel() < 4) {
                System.out.println("Aeronave " + aeronave.getId() + " esta com combustivel muito critico.");
                if (aeronave.getCombustivel() > pista3.quantidadeAeronavesAterrissagem()
                        || (aeronave.getCombustivel() == 1
                                && aeronave.getCombustivel() != pista3.getFilaAterrissagem1().getMenorCombustivel())) {
                    pista3.getFilaAterrissagem1().adicionarAeronave(aeronave);
                    pista1.getFilaAterrissagem1().getFila().remove(aeronave);
                } else if (pista1.getQtdCombustivel1() > 1 && aeronave.getCombustivel() == 1
                        && pista2.getQtdCombustivel1() == 0) {
                    pista2.getFilaAterrissagem1().adicionarAeronave(aeronave);
                    pista1.getFilaAterrissagem1().getFila().remove(aeronave);
                }
            }
        }

        List<Aeronave> filaCopy12 = new ArrayList<>(pista1.getFilaAterrissagem2().getFila());
        for (Aeronave aeronave : filaCopy12) {
            if (aeronave.getCombustivel() == 0) {
                System.out.println("Aeronave " + aeronave.getId() + " caiu por falta de combustivel.");
                aeronavesCairam.add(aeronave);
                pista1.getFilaAterrissagem2().getFila().remove(aeronave);
            } else if (aeronave.getCombustivel() < 4) {
                System.out.println("Aeronave " + aeronave.getId() + " esta com combustivel muito critico.");
                if (aeronave.getCombustivel() > pista3.quantidadeAeronavesAterrissagem()
                        || (aeronave.getCombustivel() == 1
                                && aeronave.getCombustivel() != pista3.getFilaAterrissagem1().getMenorCombustivel())) {
                    pista3.getFilaAterrissagem1().adicionarAeronave(aeronave);
                    pista1.getFilaAterrissagem2().getFila().remove(aeronave);
                } else if (pista1.getQtdCombustivel1() > 1 && aeronave.getCombustivel() == 1
                        && pista2.getQtdCombustivel1() == 0) {
                    pista2.getFilaAterrissagem1().adicionarAeronave(aeronave);
                    pista1.getFilaAterrissagem2().getFila().remove(aeronave);
                }
            }
        }

        List<Aeronave> filaCopy21 = new ArrayList<>(pista2.getFilaAterrissagem1().getFila());
        for (Aeronave aeronave : filaCopy21) {
            if (aeronave.getCombustivel() == 0) {
                System.out.println("Aeronave " + aeronave.getId() + " caiu por falta de combustivel.");
                aeronavesCairam.add(aeronave);
                pista2.getFilaAterrissagem1().getFila().remove(aeronave);
            } else if (aeronave.getCombustivel() < 4) {
                System.out.println("Aeronave " + aeronave.getId() + " esta com combustivel muito critico.");
                if (aeronave.getCombustivel() > pista3.quantidadeAeronavesAterrissagem()
                        || (aeronave.getCombustivel() == 1
                                && aeronave.getCombustivel() != pista3.getFilaAterrissagem1().getMenorCombustivel())) {
                    pista3.getFilaAterrissagem1().adicionarAeronave(aeronave);
                    pista2.getFilaAterrissagem1().getFila().remove(aeronave);
                } else if (pista2.getQtdCombustivel1() > 1 && aeronave.getCombustivel() == 1
                        && pista1.getQtdCombustivel1() == 0) {
                    pista1.getFilaAterrissagem1().adicionarAeronave(aeronave);
                    pista2.getFilaAterrissagem1().getFila().remove(aeronave);
                }
            }
        }
        List<Aeronave> filaCopy22 = new ArrayList<>(pista2.getFilaAterrissagem2().getFila());
        for (Aeronave aeronave : filaCopy22) {
            if (aeronave.getCombustivel() == 0) {
                System.out.println("Aeronave " + aeronave.getId() + " caiu por falta de combustivel.");
                aeronavesCairam.add(aeronave);
                pista2.getFilaAterrissagem2().getFila().remove(aeronave);
            } else if (aeronave.getCombustivel() < 4) {
                System.out.println("Aeronave " + aeronave.getId() + " esta com combustivel muito critico.");
                if (aeronave.getCombustivel() > pista3.quantidadeAeronavesAterrissagem()
                        || (aeronave.getCombustivel() == 1
                                && aeronave.getCombustivel() != pista3.getFilaAterrissagem1().getMenorCombustivel())) {
                    pista3.getFilaAterrissagem1().adicionarAeronave(aeronave);
                    aeronave.setPista(pista3);
                    aeronave.setFila(pista3.getFilaAterrissagem1());
                    pista2.getFilaAterrissagem2().getFila().remove(aeronave);
                } else if (pista2.getQtdCombustivel1() > 1 && aeronave.getCombustivel() == 1
                        && pista1.getQtdCombustivel1() == 0) {
                    pista1.getFilaAterrissagem1().adicionarAeronave(aeronave);
                    aeronave.setPista(pista1);
                    aeronave.setFila(pista1.getFilaAterrissagem1());
                    pista2.getFilaAterrissagem2().getFila().remove(aeronave);
                }
            }
        }
    }
}