import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class Main {
    static Queue<Aeronave> filaAeronavesDecolagemArquivo = new LinkedList<Aeronave>();
    static Queue<Aeronave> filaAeronavesAterrissagemArquivo = new LinkedList<Aeronave>();

    static Aeroporto aeroporto = new Aeroporto();
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) throws FileNotFoundException, IOException {
        Aeroporto aeroporto = new Aeroporto();

        int escolha = 0;

        while (escolha != 3) {

            menuInicial();
            escolha = scanner.nextInt();
            scanner.nextLine();
            aeroporto.clearConsole();

            switch (escolha) {
                case 1:
                    iniciar(false);
                    break;
                case 2:
                    iniciar(true);
                    break;
                case 3:
                    System.out.println("Saindo do programa...");
                    break;
                default:
                    System.out.println("Opcao invalida");
                    break;
            }

            filaAeronavesAterrissagemArquivo = new LinkedList<Aeronave>();
            filaAeronavesDecolagemArquivo = new LinkedList<Aeronave>();
        }
    }

    public static void menuInicial() {
        aeroporto.clearConsole();
        System.out.println("Escolha uma opcao:");
        System.out.println("1 - Geração aleatória de aeronaves");
        System.out.println("2 - Leitura de arquivo de aeronaves");
        System.out.println("3 - Sair");
    }

    public static void iniciar(boolean arquivo) throws FileNotFoundException {
        if (arquivo) {
            leituraArquivoAeronaves();
        } else {
            aeronavesAleatorias();
        }
    }

    public static Aeronave linhaAeronave(Scanner arqScanner) {
        String[] aviaoArrayString = arqScanner.nextLine().split(":");

        int numPassageiros = Integer.parseInt(aviaoArrayString[0]);
        int combustivel = Integer.parseInt(aviaoArrayString[1]);
        String companhiaAerea = aviaoArrayString[2];
        boolean passageiroEspecial = Boolean.parseBoolean(aviaoArrayString[3]);

        return new Aeronave(numPassageiros, 0, combustivel, companhiaAerea, passageiroEspecial);
    }

    public static void leituraArquivoAeronaves() throws FileNotFoundException {
        System.out.println("Lendo arquivo de aeronaves.");
        try {
            Scanner arqScanner = new Scanner(new File("aeronavesAterrissagem.txt"));

            while (arqScanner.hasNextLine()) {
                Aeronave aeronave = linhaAeronave(arqScanner);
                aeronave.setIdAterrissagem(Aeroporto.idsAeronavesAterrissagem);
                Aeroporto.idsAeronavesAterrissagem += 2;

                filaAeronavesAterrissagemArquivo.add(aeronave);
            }

            arqScanner = new Scanner(new File("aeronavesDecolagem.txt"));

            while (arqScanner.hasNextLine()) {
                Aeronave aeronave = linhaAeronave(arqScanner);
                aeronave.setIdDecolagem(Aeroporto.idsAeronavesDecolagem);
                Aeroporto.idsAeronavesDecolagem += 2;

                filaAeronavesDecolagemArquivo.add(aeronave);
            }

            arqScanner.close();
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void aeronavesAleatorias() {
        while (true) {
            System.out.println("Aperte enter para simular um minuto. Digite 0 para sair.");
            String enter = scanner.nextLine();

            if (enter.equals("0")) {
                System.out.println("Saindo da função aleatória...");
                return;
            } else {
                aeroporto.simularMinuto();
            }
        }
    }
}