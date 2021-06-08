import java.io.IOException;
import java.util.*;

import com.datastax.oss.driver.api.core.CqlSession;

public class Main {

    static Scanner scanner;
    static TravelTableSimpleManager tableManager;
    static CqlSession session;
    static int id = 1;

    public static void main(String[] args) {

        try (CqlSession session = CqlSession.builder().build()) {
            KeyspaceManager keyspaceManager = new KeyspaceManager(session, "travel");
            keyspaceManager.dropKeyspace();
            keyspaceManager.selectKeyspaces();
            keyspaceManager.createKeyspace();
            keyspaceManager.useKeyspace();

            //CodecManager codecManager = new CodecManager(session);
            //codecManager.registerAddressCodec();

            tableManager = new TravelTableSimpleManager(session);
            tableManager.createTable();
            /*TravelMapper travelMapper = new TravelMapperBuilder(session).build();
            TravelDao dao = travelMapper.travelDao(CqlIdentifier.fromCql("travel"));*/


            scanner = new Scanner(System.in);
            while (true) {
                showAllRecords();
                pressEnter();
                showMenu();
                switch (scanner.nextInt()) {
                    case 0:
                        System.out.println("Zakonczono, dropuje baze");
                        tableManager.dropTable();
                        return;
                    case 1:
                        saveRecord();
                        break;
                    case 2:
                        updateRecord();
                        break;
                    case 3:
                        deleteRecord();
                        break;
                    case 4:
                        getRecordById();
                        break;
                    case 5:
                        gerRecordByStatement();
                        break;
                    case 6:
                        processing();
                        break;
                }
            }
        }
    }

    private static void pressEnter() {
        System.out.println("Wcisnij enter, aby kontynuowac...");
        try {
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void processing() {
        System.out.println("Zwiekszam koszt wszystkich podrozy o 100");
        tableManager.makeTravelsCostHigher();
    }

    private static void gerRecordByStatement() {
        scanner.nextLine();
        System.out.println("Podaj zapytanie, ktore chcesz wywolac:");
        String statement = scanner.nextLine();
        tableManager.executeSimpleStatement(statement);
    }

    private static void getRecordById() {
        showAllRecords();
        scanner.nextLine();
        System.out.println("Podaj id ktorego rekord chcesz pobrac:");
        int id = scanner.nextInt();
        tableManager.selectFromTable(id);
        pressEnter();
    }

    private static void deleteRecord() {
        showAllRecords();
        scanner.nextLine();
        System.out.println("Podaj id ktorego rekord chcesz usunac:");
        int id = scanner.nextInt();
        tableManager.deleteFromTable(id);
        pressEnter();
    }

    private static void updateRecord() {
        showAllRecords();
        scanner.nextLine();
        System.out.println("Podaj id ktorego cene chcesz zmienic:");
        int id = scanner.nextInt();
        System.out.println("Podaj nowy koszt podrozy:");
        int cost = scanner.nextInt();
        tableManager.updateCostWhereId(id,cost);
        pressEnter();
    }

    private static void saveRecord() {
        List<String> attractions = new ArrayList<>();
        scanner.nextLine();
        while (true) {
            System.out.println("Podaj atrakcje (lub zostaw puste aby zakonczyc):");
            String tmp = scanner.nextLine();
            if (tmp.equals(""))
                break;
            attractions.add(tmp);
        }
        System.out.println("Ile miejsc?");
        int numOfSeats = scanner.nextInt();
        System.out.println("Jaki jest koszt?");
        int cost = scanner.nextInt();
        tableManager.insertIntoTable(id++, attractions, numOfSeats, cost);
        pressEnter();
    }

    private static void showMenu() {
        System.out.print("\n2) Biuro turystyczne (Cassandra)\n\nWybierz operacje:\n" +
                "1.Zapisywanie\n2.Aktualizowanie\n3.Kasowanie\n4.Pobieranie po ID\n5.Pobieranie (po tytule)\n" +
                "6.Przetwarzanie(po kategorii)\n0.Zakoncz\n\nWpisz cyfre i zatwierdz enterem: ");
    }

    private static void showAllRecords() {
        System.out.println("Wszystkie rekordy:");
        tableManager.selectAllFromTable();
    }
}
