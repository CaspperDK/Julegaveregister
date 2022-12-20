import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    public static void menu(){
        int valg;

        //Login:
        String loginNavn;
        String loginKode;

        //Opret:
        String nyNavn;
        String nyKode;

        DbSql db = new DbSql();
        Scanner input = new Scanner(System.in);
        System.out.println("1. Opret Konto");
        System.out.println("2. Login");
        System.out.println( );

        System.out.println("Indtast dit valg: ");
        valg = input.nextInt();
        switch (valg){
            case 1:
                System.out.println("Indtast Brugernavn: ");
                nyNavn = input.next();
                System.out.println("Navn: " + nyNavn + ", indtast kodeordet til denne konto.");
                nyKode = input.next();
                System.out.println("Navn: " + nyNavn + ", " + nyKode + ": Dette er dine loginoplysninger.");
                User b = new User(nyNavn, nyKode);
                db.opretBruger(b);
            case 2:
                System.out.println("Indtast login oplysninger (Brugernavn + Kode): ");
                loginNavn = input.next();
                loginKode = input.next();
                b = db.getUser(loginNavn, loginKode);
                String navn = b.getUserName();
                if (navn == null)
                    System.out.println("Disse login oplysninger findes ikke i vores system.");
                else if(navn.equals(loginNavn)) {
                    System.out.println("Velkommen " + b + ", til Ditønske.dk");
                    homepage(b.getUserID());
                }
                break;
        }
    }

    public static void homepage(int userID) {
        // Main options
        int valg;
        int ID = userID;
        DbSql db = new DbSql();
        Scanner input = new Scanner(System.in);

        System.out.println("1. Tilføj ønske til din ønskeliste."); //
        System.out.println("2. Se din ønskeliste og del med anden bruger."); // Deler din ønskeliste med en anden.
        System.out.println("3. Fjern ønske fra din ønskeliste."); // Fjerner et ønske fra din ønskeliste.
        System.out.println("4. Følg anden brugers ønskeliste."); // Viser anden brugers ønskeliste på din APP.
        System.out.println("5. Marker ønske som købt."); // Markere ønsket som købt.
        System.out.println(" ");
        System.out.println("6. Luk programmet."); // Lukker programmet.
        System.out.println(" ");
        System.out.println("Indtast dit valg: ");
        valg = input.nextInt();
        switch(valg) {

            case 1: // Tilføj ønske
                String gaveNavn;
                String gaveModtager;
                String gaveGiver;
                String gavePris;
                String bought;
                User u = new User();
                u = db.getUserFromID(ID);
                System.out.println("Indtast gave navn: ");
                gaveNavn = input.next();
                System.out.println("Indtast gave modtager: ");
                gaveModtager = input.next();
                gaveGiver = u.getUserName();
                System.out.println("Indtast gave prisen: ");
                gavePris = input.next();
                System.out.println("Indtast om gaven er købt: ");
                bought = input.next();
                Gave g = new Gave(gaveNavn, gaveModtager, gaveGiver, gavePris, bought);
                db.addAWish(u, g);

                System.out.println("Henter oplysninger..");
                db.getGavelisteIDDB(u, g);
                System.out.println("Tilføjer gaven til databasen..");
                System.out.println("Connecting User and Present..");
                db.connectUserAndGave(u, g);

                homepage(ID);
                break;

            case 2: // Del ønskeliste
                System.out.println("Henter oplysninger om alle ønsker du har oprettet.");
                ArrayList<Gave> liste;
                User u1 = new User();
                u1 = db.getUserFromID(ID);
                liste = db.shareAWish(u1);
                for(int i = 0; i<liste.size(); i++) {
                    System.out.println(liste.get(i));
                }
                System.out.println(" ");
                String link;
                link = "http://xn--ditnske-s1a.dk/delonskeliste/" + u1.getUserName() + "" + u1.getUserID();
                System.out.println(link);

                homepage(ID);
                break;

            case 3: // Fjern ønske
                String gavenavn;
                System.out.println("Indtast navnet på gaven der skal fjernes: ");
                gavenavn = input.next();
                User u2 = new User();
                u2 = db.getUserFromID(ID);
                Gave g1 = new Gave();
                g1 = db.getGave(gavenavn);
                db.removeConnectionUserWish(u2, g1);
                System.out.println("Fjerner connection mellem User og Gave");
                System.out.println("Fjerner gaven...");
                db.removeAWish(g1.getGave(), g1.getGavelisteID());

                homepage(ID);

            case 4: // Følg med i andens ønskeliste
                System.out.println("Indtast navnet på persons ønskeliste du vil se: ");
                String wishlist = input.next();
                System.out.println("Henter oplysninger om " + wishlist + "'s ønskeliste.");
                ArrayList<Gave> liste1;
                liste1 = db.getAWishlist(wishlist);
                for(int i = 0; i<liste1.size(); i++) {
                    System.out.println(liste1.get(i));
                }
                System.out.println(" ");

                homepage(ID);
                break;

            case 5: // Reserver/Marker ønske fra anden brugers ønskeliste som reserveret/købt.
                System.out.println("Indtast navnet på gave giveren: ");
                gaveGiver = input.next();
                System.out.println("Indtast navnet på gaven der skal markeres som købt: ");
                gaveNavn = input.next();
                User u3 = new User();
                Gave g2 = new Gave();
                u3 = db.getUserFromName(gaveGiver);
                g2 = db.getGave(gaveNavn);

                db.getGavelisteIDDB(u3, g2);
                db.markWish(g2, u3);

                homepage(ID);
                break;

            case 6: // Lukker programmet.
                System.out.println("Lukker progammet.");
                break;

        }
    }

    public static void main(String[] args) {
        menu();
    }
}