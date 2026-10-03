package com.example;

import java.util.HashMap;
import java.util.concurrent.CompletableFuture;

public class Exercici1 {
    // Crea una classe Java amb un mètode main.
    public static void main(String[] args) {
        // Utilitza CompletableFuture per encadenar les operacions, assegurant que cada etapa processa les dades de l'etapa anterior.

        // La primera tasca (supplyAsync) ha de simular la validació de les dades d'una sol·licitud, retornant un valor inicial.
        CompletableFuture<HashMap<String, Integer>> validacioTasca = CompletableFuture.supplyAsync(() -> {
            System.out.println("Validant dades de la sol·licitud...");
            System.out.println("");

            HashMap<String, Integer> dades = new HashMap<>();
            dades.put("valor1", 100);
            dades.put("valor2", 232);
            dades.put("valor3", 596);

            System.out.println("Dades inicials de la sol·licitud: ");
            System.out.println("Valor inicial 1: " + dades.get("valor1"));
            System.out.println("Valor inicial 2: " + dades.get("valor2"));
            System.out.println("Valor inicial 3: " + dades.get("valor3"));
            System.out.println("");

            return dades;
        });

        // La segona tasca (thenApply) ha de processar aquestes dades, modificant-les per obtenir el resultat calculat.
        CompletableFuture<String> processatTasca = validacioTasca.thenApply(dades -> {
            System.out.println("Processant les dades...");
            System.out.println("");

            String codi = "PLOKMIJNUHBYGVTFCRDXESZWAQ";

            int numCodi1 = dades.get("valor1") % codi.length();
            String resultadoCodi1 = 
                String.valueOf(dades.get("valor1")) +
                String.valueOf(numCodi1) +
                codi.charAt(numCodi1);

            int numCodi2 = dades.get("valor2") % codi.length();
            String resultadoCodi2 = 
                String.valueOf(dades.get("valor2")) +
                String.valueOf(numCodi2) +
                codi.charAt(numCodi2);

            int numCodi3 = dades.get("valor3") % codi.length();
            String resultadoCodi3 = 
                String.valueOf(dades.get("valor3")) + 
                String.valueOf(numCodi3) +
                codi.charAt(numCodi3);
            
            String finalCodi = 
                resultadoCodi1 + 
                resultadoCodi2 +
                resultadoCodi3;

            return finalCodi;
        });

        // La tercera tasca (thenAccept) ha de mostrar el resultat final, simulant la resposta enviada a l'usuari.
        CompletableFuture<Void> resultatTasca = processatTasca.thenAccept(valorFinal -> {
            System.out.println("Resposta final enviada a l'usuari: " + valorFinal);
        });



        // Utilitza join() al final per esperar que totes les operacions asíncrones es completin abans de finalitzar el programa.
        resultatTasca.join();
    }
}
