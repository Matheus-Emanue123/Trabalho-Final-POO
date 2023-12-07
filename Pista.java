import java.util.LinkedList;

public class Pista {

    private FilaDeEspera filaAterrissagem1;
    private FilaDeEspera filaAterrissagem2;
    private FilaDeEspera filaDecolagem;
    private int qntPassagueirosEspeciais;
    private String nome;

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

    public String getNome() {
        return this.nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
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

    public FilaDeEspera escolherFilaAterrissagem() {
        if (filaAterrissagem1.tamanho() < filaAterrissagem2.tamanho() || filaAterrissagem2 == null) {
            return filaAterrissagem1;
        } else if (filaAterrissagem1.tamanho() > filaAterrissagem2.tamanho()) {
            return filaAterrissagem2;
        } else {
            int count1 = filaAterrissagem1.qntPassagueirosEspeciais();
            int count2 = filaAterrissagem2.qntPassagueirosEspeciais();

            if (count1 < count2) {
                System.out.println("tem passageiros especiais em fila 1");
                return filaAterrissagem1;
            } else {
                System.out.println("tem passageiros especiais em fila 2");
                return filaAterrissagem2;
            }
        }
    }

    public int qntPassagueirosEspeciaisAterrissagem() {
        qntPassagueirosEspeciais = filaAterrissagem1.qntPassagueirosEspeciais();
        if (filaAterrissagem2 != null) {
            qntPassagueirosEspeciais += filaAterrissagem2.qntPassagueirosEspeciais();
        }
        return qntPassagueirosEspeciais;
    }

    public FilaDeEspera escolherFilaDecolagem() {
        return filaDecolagem;
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

    public int qntAeronavesCombustivelCriticoAterrissagem() {
        int qntAeronavesCombustivelCritico = filaAterrissagem1.qntAeronavesCombustivelCritico();
        if (filaAterrissagem2 != null) {
            qntAeronavesCombustivelCritico += filaAterrissagem2.qntAeronavesCombustivelCritico();
        }
        return qntAeronavesCombustivelCritico;
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

    public double recalcularTempoMedioEspera() {
        double tempoDeEsperaFilas = 0;
        double tempoDeEsperaFila1 = filaAterrissagem1.tempoDeEsperaTotal();
        double tempoDeEsperaFila2 = 0;
        if (filaAterrissagem2 != null)
            tempoDeEsperaFila2 = filaAterrissagem2.tempoDeEsperaTotal();
        double tempoDeEsperaFila3 = filaDecolagem.tempoDeEsperaTotal();

        double qntAeronavesFilas = 0;
        double qntAeronavesFila1 = filaAterrissagem1.tamanho();
        double qntAeronavesFila2 = 0;
        if (filaAterrissagem2 != null)
            qntAeronavesFila2 = filaAterrissagem2.tamanho();
        double qntAeronavesFila3 = filaDecolagem.tamanho();

        tempoDeEsperaFilas = tempoDeEsperaFila1 + tempoDeEsperaFila2 + tempoDeEsperaFila3;
        qntAeronavesFilas = qntAeronavesFila1 + qntAeronavesFila2 + qntAeronavesFila3;

        if (qntAeronavesFilas == 0)
            return 0;

        else if (tempoDeEsperaFilas == 0)
            return 0;

        else
            return tempoDeEsperaFilas / qntAeronavesFilas;
    }

    public void imprimirTempoMedioDeEspera() {
        System.out.println("O tempo médio de espera desta " + this.nome + " é: " + recalcularTempoMedioEspera());
    }

    public void verificarCombustivelCritico() {
        filaAterrissagem1.verificarCombustivelCritico();
        if (filaAterrissagem2 != null)
            filaAterrissagem2.verificarCombustivelCritico();
        filaDecolagem.verificarCombustivelCritico();
    }

    public void imprimir() {
        System.out.println("Pista: " + this.nome);

        filaAterrissagem1.imprimirFila();
        if (filaAterrissagem2 != null)
            filaAterrissagem2.imprimirFila();
        filaDecolagem.imprimirFila();
    }
}