import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

public class Main {
    static Aeroporto aeroporto = new Aeroporto();
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) throws FileNotFoundException, IOException {
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
        }
    }

    public static void menuInicial() {
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

    public static void linhaAeronave(Scanner arqScanner) {
        String[] aviaoArrayString = arqScanner.nextLine().split(":");

        int tipo = Integer.parseInt(aviaoArrayString[0]);
        int numPassageiros = Integer.parseInt(aviaoArrayString[1]);
        int combustivel = Integer.parseInt(aviaoArrayString[2]);
        String companhiaAerea = aviaoArrayString[3];
        boolean passageiroEspecial = Integer.parseInt(aviaoArrayString[4]) == 1;

        Aeronave aeronave = new Aeronave(numPassageiros, 0, combustivel, companhiaAerea, passageiroEspecial);

        if (tipo == 0) {
            aeronave.setIdAterrissagem(Aeroporto.idsAeronavesAterrissagem);
            Aeroporto.idsAeronavesAterrissagem += 2;

            Aeroporto.filaAeronavesAterrissagemArquivo.add(aeronave);
        } else {
            aeronave.setIdDecolagem(Aeroporto.idsAeronavesDecolagem);
            Aeroporto.idsAeronavesDecolagem += 2;

            Aeroporto.filaAeronavesDecolagemArquivo.add(aeronave);
        }
    }

    public static void leituraArquivoAeronaves() throws FileNotFoundException {
        System.out.println("Lendo arquivo de aeronaves.");
        try {
            Scanner arqScanner = new Scanner(new File("aeronaves.txt"));

            while (arqScanner.hasNextLine()) {
                linhaAeronave(arqScanner);
            }

            System.out.println("Arquivo lido com sucesso.");

            arqScanner.close();

            while (true) {
                System.out.println("Tem no total " + (Aeroporto.filaAeronavesAterrissagemArquivo.size())
                        + " aeronaves para aterrisar no arquivo.");
                System.out.println("Tem no total " + (Aeroporto.filaAeronavesDecolagemArquivo.size())
                        + " aeronaves para decolar no arquivo.");
                System.out.println("Tem no total " + (Aeroporto.filaAeronavesAterrissagemArquivo.size()
                        + Aeroporto.filaAeronavesDecolagemArquivo.size()) + " aeronaves no arquivo.");

                System.out.println("Aperte enter para simular um minuto. Digite 0 para sair.");
                String enter = scanner.nextLine();

                if (Aeroporto.filaAeronavesAterrissagemArquivo.isEmpty()
                        && Aeroporto.filaAeronavesDecolagemArquivo.isEmpty()) {
                    System.out.println("Não há mais aeronaves para simular.");
                    return;
                } else {
                    if (enter.equals("0")) {
                        System.out.println("Saindo da função arquivo...");
                        return;
                    } else {
                        aeroporto.simularMinutoArquivo();
                    }
                }
            }

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