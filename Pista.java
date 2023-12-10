import java.util.LinkedList;
import java.util.Scanner;

public class Pista {

    private FilaDeEspera filaAterrissagem1;
    private FilaDeEspera filaAterrissagem2;
    private FilaDeEspera filaDecolagem;
    private String nome;
    private int qtdAterrissagensEmergenciais;

    public Pista() {
        this.filaAterrissagem1 = new FilaDeEspera("Fila de Aterrissagem 1");
        this.filaAterrissagem1.setFila(new LinkedList<>());

        this.filaAterrissagem2 = new FilaDeEspera("Fila de Aterrissagem 2");
        this.filaAterrissagem2.setFila(new LinkedList<>());

        this.filaDecolagem = new FilaDeEspera("Fila de Decolagem");
        this.filaDecolagem.setFila(new LinkedList<>());
    }

    public Pista(String nome, boolean aterrissagem) {
        this.nome = nome;
        this.filaAterrissagem1 = new FilaDeEspera(
                aterrissagem ? "Fila de Aterrissagem 1" : "Fila de Aterrissagem de Emergência");
        this.filaAterrissagem2 = aterrissagem ? new FilaDeEspera("Fila de Aterrissagem 2") : null;

        this.filaDecolagem = new FilaDeEspera("Fila de Decolagem");
    }

    public void atualizarCombustivel() {
        for (Aeronave a : filaAterrissagem1.getFila()) {
            a.setCombustivel(a.getCombustivel() - 1);
        }

        if (filaAterrissagem2 != null) {
            for (Aeronave a : filaAterrissagem2.getFila()) {
                a.setCombustivel(a.getCombustivel() - 1);
            }
        }
    }

    private int contarPassageirosEspeciais(FilaDeEspera fila) {
        int count = 0;
        for (Aeronave a : fila.getFila()) {
            if (a.getPassageiroEspecial()) {
                count++;
            }
        }
        return count;
    }

    public FilaDeEspera escolherFilaAterrissagem() {
        if (filaAterrissagem1.tamanho() < filaAterrissagem2.tamanho() || filaAterrissagem2 == null) {
            return filaAterrissagem1;
        } else if (filaAterrissagem1.tamanho() > filaAterrissagem2.tamanho()) {
            return filaAterrissagem2;
        } else {
            int count1 = contarPassageirosEspeciais(filaAterrissagem1);
            int count2 = contarPassageirosEspeciais(filaAterrissagem2);

            if (count1 < count2) {
                return filaAterrissagem1;
            } else {
                return filaAterrissagem2;
            }
        }
    }

    public FilaDeEspera getFilaAterrissagem1() {
        return this.filaAterrissagem1;
    }

    public FilaDeEspera getFilaAterrissagem2() {
        return this.filaAterrissagem2;
    }

    public FilaDeEspera getFilaDecolagem() {
        return this.filaDecolagem;
    }

    public String getNome() {
        return this.nome;
    }

    public int getQtdAterrissagensEmergenciais() {
        return this.qtdAterrissagensEmergenciais;
    }

    public int getQtdCombustivel1() {
        int qtd = 0;

        for (Aeronave a : filaAterrissagem1.getFila()) {
            if (a.getCombustivel() == 1) {
                qtd++;
            }
        }

        if (filaAterrissagem2 != null) {
            for (Aeronave a : filaAterrissagem2.getFila()) {
                if (a.getCombustivel() == 1) {
                    qtd++;
                }
            }
        }

        return qtd;
    }

    public void imprimir() {
        System.out.println("Fila de aterrissagem 1: ");
        filaAterrissagem1.imprimir();
        System.out.println();

        if (filaAterrissagem2 != null) {
            System.out.println("Fila de aterrissagem 2: ");
            filaAterrissagem2.imprimir();
            System.out.println();
        }

        System.out.println("Fila de decolagem: ");
        filaDecolagem.imprimir();
        System.out.println();
    }

    public void imprimirInformacoesPista(int n, Scanner sc) {
        System.out.println("INFORMACOES DA PISTA " + n + ": ");
        System.out.println("Tempo medio de espera: " + String.format("%.2f", recalcularTempoMedioEspera()));
        System.out.println(
                "Quantidade de aeronaves que realizaram aterrissagens emergenciais: "
                        + this.qtdAterrissagensEmergenciais);
        System.out.println("Total de aeronaves em espera: " + quantidadeAeronaves());
        System.out
                .println("Total de aeronaves em espera para aterrissagem: " + quantidadeAeronavesAterrissagem());
        System.out.println("Total de aeronaves em espera para decolagem: " + quantidadeAeronavesDecolagem());

        do {
            System.out.println("Deseja ver as filas de espera? (S/N)");
            String escolha = sc.nextLine();

            if (escolha.equals("S") || escolha.equals("s")) {
                System.out.println();
                imprimir();
            } else if (escolha.equals("N") || escolha.equals("n")) {
                return;
            } else {
                System.out.println("Opcao invalida!");
            }
        } while (true);
    }

    public int quantidadeAeronaves() {
        return quantidadeAeronavesAterrissagem() + quantidadeAeronavesDecolagem();
    }

    public int quantidadeAeronavesAterrissagem() {
        if (filaAterrissagem2 == null)
            return filaAterrissagem1.tamanho();
        else
            return filaAterrissagem1.tamanho() + filaAterrissagem2.tamanho();
    }

    public int quantidadeAeronavesDecolagem() {
        return filaDecolagem.tamanho();
    }

    public double recalcularTempoMedioEspera() {
        double tempoDeEsperaFilas;
        double tempoDeEsperaFila1 = filaAterrissagem1.tempoDeEsperaTotal()
                + filaAterrissagem1.getTempoEsperaAeronavesSairam();
        double tempoDeEsperaFila2 = 0;
        if (filaAterrissagem2 != null)
            tempoDeEsperaFila2 = filaAterrissagem2.tempoDeEsperaTotal()
                    + filaAterrissagem2.getTempoEsperaAeronavesSairam();
        double tempoDeEsperaFila3 = filaDecolagem.tempoDeEsperaTotal() + filaDecolagem.getTempoEsperaAeronavesSairam();

        double qntAeronavesFilas;
        double qntAeronavesFila1 = filaAterrissagem1.tamanho() + filaAterrissagem1.getQtdAeronavesSairam();
        double qntAeronavesFila2 = 0;
        if (filaAterrissagem2 != null)
            qntAeronavesFila2 = filaAterrissagem2.tamanho() + filaAterrissagem2.getQtdAeronavesSairam();
        double qntAeronavesFila3 = filaDecolagem.tamanho() + filaDecolagem.getQtdAeronavesSairam();

        tempoDeEsperaFilas = tempoDeEsperaFila1 + tempoDeEsperaFila2 + tempoDeEsperaFila3;
        qntAeronavesFilas = qntAeronavesFila1 + qntAeronavesFila2 + qntAeronavesFila3;

        if (qntAeronavesFilas == 0)
            return 0;

        else if (tempoDeEsperaFilas == 0)
            return 0;

        else
            return tempoDeEsperaFilas / qntAeronavesFilas;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setQtdAterrissagensEmergenciais(int qtd) {
        this.qtdAterrissagensEmergenciais = qtd;
    }

    public void somarTempoEspera() {
        for (Aeronave a : filaAterrissagem1.getFila()) {
            a.setTempoEspera(a.getTempoEspera() + 1);
        }

        if (filaAterrissagem2 != null)
            for (Aeronave a : filaAterrissagem2.getFila()) {
                a.setTempoEspera(a.getTempoEspera() + 1);
            }

        for (Aeronave a : filaDecolagem.getFila()) {
            a.setTempoEspera(a.getTempoEspera() + 1);
        }
    }

    public double tempoEsperaTotalPista() {
        double tempoFila1 = filaAterrissagem1.tempoDeEsperaTotal();
        double tempoFila2;
        if (filaAterrissagem2 == null) {
            tempoFila2 = 0;
        } else {
            tempoFila2 = filaAterrissagem2.tempoDeEsperaTotal();
        }
        double tempoFila3 = filaDecolagem.tempoDeEsperaTotal();

        return tempoFila1 + tempoFila2 + tempoFila3;
    }

}