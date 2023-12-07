package tema_1;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class MainApp {
    public static void main(String[] args) {
        Map<Integer, Carte> colectieCarti = citesteCartiDinJSON("src/main/resources/carti.json");
        afiseazaColectia(colectieCarti);

        System.out.println();

        stergereCarte(colectieCarti, 2);
        afiseazaColectia(colectieCarti);

        System.out.println();

        adaugareCarte(colectieCarti, 5, new Carte("Titlu nou", "Autor nou", 2023));
        adaugareCarte(colectieCarti, 7, new Carte("Titlu nou", "Autor nou", 2023));
        afiseazaColectia(colectieCarti);

        System.out.println();

        salvareFisier(colectieCarti, "src/main/resources/carti.json");

        System.out.println();


        System.out.println("Careile autorului Yual Noah Harari:");
        Set<Carte> cartiYuval = filtreazaDupaAutor(colectieCarti, "Yuval Noah Harari");
        afiseazaColectieCarti(cartiYuval);

        System.out.println();

        afisareOrdonata(cartiYuval);

        System.out.println();

        System.out.println("Cartile ordonate dupa titlu:");
        Set<Carte> ordonat=afisareOrdonataTitlu(colectieCarti);
        afiseazaOrdonataColectieCarti(ordonat);


    }
    private static Set<Carte> filtreazaDupaAutor(Map<Integer, Carte> colectieCarti, String autor) {
        return colectieCarti.values().stream()
                .filter(carte -> carte.autorul().equals(autor))
                .collect(Collectors.toSet());
    }

    private static void afiseazaColectieCarti(Set<Carte> colectieCarti) {
        colectieCarti.forEach(System.out::println);

    }
    private static void afiseazaOrdonataColectieCarti(Set<Carte> colectieCarti) {
        colectieCarti.forEach(System.out::println);

    }
    private static void afisareOrdonata(Map<Integer, Carte> colectieCarti) {
        System.out.println("Cartile ordonate după titlu:");
        colectieCarti.values().stream()
                .sorted((carte1, carte2) -> carte1.titlul().compareToIgnoreCase(carte2.titlul()))
                .forEach(System.out::println);

    }

    private static Set<Carte> afisareOrdonataTitlu(Map<Integer, Carte> colectieCarti) {
        return colectieCarti.values().stream()
                .sorted((carte1, carte2) -> carte1.titlul().compareToIgnoreCase(carte2.titlul()))
                .collect(Collectors.toSet());
    }


    private static void salvareFisier(Map<Integer, Carte> colectieCarti, String numeFisier) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(new File(numeFisier), colectieCarti);
            System.out.println("Modificările au fost salvate");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

   /*
   private static void salvareFisier(Map<Integer, Carte> colectieCarti, String numeFisier) {
        ObjectMapper objectMapper=new ObjectMapper();

        try{

objectMapper.writeValue(new File(numeFisier),colectieCarti);
            System.out.println("Modifiacarile au fost salvate");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/

    private static void adaugareCarte(Map<Integer, Carte> carti, int idCarte, Carte carte) {
        Carte carteExistenta = carti.putIfAbsent(idCarte, carte);

        if (carteExistenta == null) {
            System.out.println("CCartea cu ID-ul " + idCarte + " a fost adaugata cu succes.");
            //carti.put(idCarte,carte);
        } else {
            System.out.println("Cartea cu ID-ul " + idCarte + " exista deja in colectie. S-au actualizat informatiile");
            carti.put(idCarte,carte);
        }
    }

    private static void stergereCarte(Map<Integer, Carte> colectieCarti, int idCarte) {
        if (colectieCarti.containsKey(idCarte)) {
            colectieCarti.remove(idCarte);
            System.out.println("Cartea cu ID-ul " + idCarte + " a fost ștearsa.");
        } else {
            System.out.println("Nu exista o carte cu ID-ul " + idCarte + " in colectie.");
        }
    }

    private static void afiseazaColectia(Map<Integer, Carte> colectieCarti) {
        colectieCarti.forEach((id, carte) -> System.out.println("ID: " + id + ", Carte: " + carte));
    }
    private static Map<Integer, Carte> citesteCartiDinJSON(String numeFisier) {
        Map<Integer, Carte> colectieCarti = new HashMap<>();

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            Map<String, Map<String, Object>> map = objectMapper.readValue(new File(numeFisier), HashMap.class);

            map.forEach((id, carteData) -> {
                int idCarte = Integer.parseInt(id);
                String titlul = (String) carteData.get("titlul");
                String autorul = (String) carteData.get("autorul");
                int anul = (int) carteData.get("anul");

                Carte carte = new Carte(titlul, autorul, anul);
                colectieCarti.put(idCarte, carte);
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

        return colectieCarti;
    }
}
