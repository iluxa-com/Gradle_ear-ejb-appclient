package com.my.client;

import javax.ejb.EJB;
import java.util.Scanner;
import com.my.remote.CacheBeanRemote;
/**
 * Enterprise Application Client main class.
 *
 */
public class Main {

    @EJB
    private static CacheBeanRemote cache;
    public static void main( String[] args ) {
        int option;
        Scanner scanner = new Scanner(System.in);
        Object object;
        String key;
        String value;
        if (cache == null) {
            System.out.println("Przed jakimkolwiek uzyciem cache = null");
        }

        do {
            System.out.println("Wybierz opcje");
            System.out.println("1. Dodaj nowy wpis.(add)");
            System.out.println("2. Usun wpis.(remove)");
            System.out.println("3. Pobierz wpis.(get)");
            System.out.println("4. Sprawdz wpis.(contains)");
            System.out.println("0. Zakoncz.");
            option = scanner.nextInt();
            scanner.nextLine();
            switch(option){
                case 1:
                    System.out.println("Wprowadz klucz");
                    key = scanner.nextLine();
                    System.out.println("Wprowadz wartosc");
                    value = scanner.nextLine();
                    cache.add(key,value);
                    System.out.println("Dodano");
                    break;
                case 2:
                    System.out.println("Wprowadz klucz");
                    key = scanner.nextLine();
                    System.out.println("Wprowadz wartosc");
                    value = scanner.nextLine();
                    object = cache.remove(new Scanner(System.in).nextLine());
                    if (object != null) System.out.println("Usunieto: " + object);
                    else System.out.println("Nie usunieto - brak wpisu.");
                    break;
                case 3:
                    System.out.println("Wprowadz klucz");
                    key = scanner.nextLine();
                    object = cache.get(key);
                    System.out.println("Pobrano: " + object);
                    break;
                case 4:
                    System.out.println("Wprowadz klucz");
                    key = scanner.nextLine();
                    boolean contain = cache.contains(key);
                    if (contain) System.out.println("Jest w cache.");
                   else System.out.println("Brak w cache.");
                    break;
            }
        } while (option != 0);
    }

}