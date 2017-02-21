import java.util.Scanner;

/**
 * Created by Raquel on 15/02/17.
 */
public class Main {
    static Scanner teclado = new Scanner(System.in);

    public static void main(String[] args) {
        menu();
    }

    private static void menu() {
        System.out.println("1.- Cifrar\n2.- Descifrar");
        switch (teclado.nextLine().charAt(0)) {
            case '1': cifrarFichero(); break;
            case '2': descifrarFichero(); break;
            default:
                System.out.println("Debes seleccionar 1 o 2...");
                menu();
        }
    }

    private static void cifrarFichero() {
        System.out.print("Introduce la ruta del almacén de claves:");
        String pathAlmacenClaves = teclado.nextLine().trim();
        System.out.print("Introduce la contraseña del almacén: ");
        String passAlmacen = teclado.nextLine().trim();
        System.out.print("Introduce la contraseña de la clave: ");
        String passClave = teclado.nextLine().trim();
        System.out.print("Introduce el alias: ");
        String alias = teclado.nextLine().trim();

        Cifrador cifrador = new Cifrador(pathAlmacenClaves, passAlmacen, passClave, alias);

        System.out.print("Escribe una frase");
        String frase = teclado.nextLine();
        System.out.println(frase);
        String fraseCifrada = cifrador.cifrar(frase);

        // ESCRIBIRLO EN UN TXT


    }

    private static void descifrarFichero() {
        System.out.println("Cifrado: " + fraseCifrada);
        System.out.println("Descifrado: " + cifrador.descifrar(fraseCifrada));
    }


}
