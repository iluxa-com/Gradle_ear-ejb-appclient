package com.my.client;

import javax.ejb.EJB;
import java.util.List;
import java.util.Scanner;

import com.my.model.Users;
import com.my.remote.CacheBeanRemote;
import com.my.remote.DbOperatorRemote;

/**
 * Enterprise Application Client main class.
 *
 */
public class Main {

    @EJB
    private static DbOperatorRemote dbOperatorRemote;

    public static void main( String[] args ) {
        int option;
        String name;
        String lastName;
        Scanner scanner = new Scanner(System.in);

        do {
            System.out.println("Wybierz opcje");
            System.out.println("1. Dodaj uzytkownika.(add)");
            System.out.println("2. Usun uzytkownika.(remove)");
            System.out.println("3. Pobierz po lastName.(get)");
            System.out.println("4. Zaktualizuj.");
            System.out.println("0. Zakoncz.");
            option = scanner.nextInt();
            scanner.nextLine();
            switch(option){
                case 1:
                    System.out.println("Wprowadz imie");
                    name = scanner.nextLine();
                    System.out.println("Wprowadz nazwiskoc");
                    lastName = scanner.nextLine();
                    Users user = new Users();
                    user.setFirstName(name);
                    user.setLastName(lastName);
                    try {
                        dbOperatorRemote.addUser(user);
                    }catch (Exception e) {

                    }
                    System.out.println("Dodano");
                    break;
                case 2:
                    System.out.println("Wprowadz nazwisko");
                    lastName = scanner.nextLine();
                    try {
                        List<Users> usersList = dbOperatorRemote.findByLastName(lastName);
                        for (Users u : usersList) {
                            dbOperatorRemote.deleteUser(u);
                        }
                    }catch (Exception e) {

                    }
                    break;
                case 3:
                    System.out.println("Wprowadz nazwisko");
                    lastName = scanner.nextLine();
                    try {
                        List<Users> usersList = dbOperatorRemote.findByLastName(lastName);
                        for (Users u : usersList) {
                            System.out.println("Pobrano: " + u.getFirstName() + " " + u.getLastName());
                        }
                    }
                    catch (Exception e) {}
                    break;
                case 4:
                    break;
            }
        } while (option != 0);
    }

}