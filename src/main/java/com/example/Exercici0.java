package com.example;

import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Exercici0 {
    // Crea una classe Java amb un mètode main.
    public static void main(String[] args) {

        // Defineix una estructura de dades concurrent
        // (com ConcurrentHashMap) per compartir informació entre les tasques.
        ConcurrentHashMap<String, Double> dades = new ConcurrentHashMap<>();



        // Defineix tres tasques:

        // Una tasca (Runnable) que introdueixi les dades inicials, 
        // simulant la recepció d'una operació bancària.
        Runnable recepcioOperacio = () -> {
            System.out.println("Operacio bancaria rebuda, actualitzant dades...");
            System.out.println("");

            dades.put("saldo", 1000.0);
            dades.put("comissio", 25.0);
            dades.put("interessos", 10.0);

            System.out.println("Saldo inicial: " + dades.get("saldo"));
            System.out.println("Comissio: -"     + dades.get("comissio"));
            System.out.println("Interessos: +"   + dades.get("interessos"));
            System.out.println("");

            System.out.println("Dades bancaries actualitzades...");
            System.out.println("");
        };

        // Una altra tasca (Runnable) que modifiqui aquestes dades, 
        // simulant una operació de càlcul d'interessos o comissions.
        Runnable calculOperacio = () -> {     
            System.out.println("Calculant saldo final...");
            System.out.println("");

            double resultat = dades.get("saldo") - dades.get("comissio") + dades.get("interessos");
            dades.put("saldo", resultat);
            dades.put("comissio", 0.0);
            dades.put("interessos", 0.0);
        };

        // Una tercera tasca (Callable) que llegeixi les dades modificades
        // i retorni un resultat final, com ara el saldo actualitzat.
        Callable<Double> resultatFinal = () -> {
            double saldoActualitzat = dades.get("saldo");
            return saldoActualitzat;
        };



        // Utilitza un ExecutorService amb un pool de 3 fils (newFixedThreadPool(3)).
        ExecutorService executor = Executors.newFixedThreadPool(3);

        // Fil 1: Operacio de recepcio
        Future<?> recepcioFuture = executor.submit(recepcioOperacio);
        try {
            recepcioFuture.get();
        } catch (InterruptedException e) {
            System.out.println("[recepcioFuture] El fil que esperava ha estat interromput.");
        } catch (ExecutionException e) {
            System.out.println("[recepcioFuture] La tasca que estavem esperant ha fallat.");
        }

        // Fil 2: Operacio de calcul
        Future<?> operacioFuture = executor.submit(calculOperacio);
        try {
            operacioFuture.get();
        } catch (InterruptedException e) {
            System.out.println("[operacioFuture] El fil que esperava ha estat interromput.");
        } catch (ExecutionException e) {
            System.out.println("[operacioFuture] La tasca que estavem esperant ha fallat.");
        }

        // Fil 3: Operacio de retornar el calcul final
        Future<Double> resultatFuture = executor.submit(resultatFinal);
        try {
            double saldoFinal = resultatFuture.get();
            System.out.println("SALDO FINAL: " + saldoFinal);
        } catch (InterruptedException e) {
            System.out.println("[resultatFuture] El fil que esperava ha estat interromput.");
        } catch (ExecutionException e) {
            System.out.println("[resultatFuture] La tasca que estavem esperant ha fallat.");
        }



        // Tanca l'executor per alliberar els recursos.
        executor.shutdown();
    }
}