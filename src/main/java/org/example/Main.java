package org.example;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;

public class Main {
    public static void main(String[] args) {

        System.out.println("Début du programme !!!");

        try(var executor = Executors.newVirtualThreadPerTaskExecutor()){
            executor.execute(() -> {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            });
        }

        CompletableFuture<Void> step1 = CompletableFuture.runAsync(() -> {
            System.out.println("Etape 1 => Lecture de données...");
        });

        try(var executor = Executors.newVirtualThreadPerTaskExecutor()){
            executor.execute(() -> {
                try{
                    Thread.sleep(2000);
                }catch (InterruptedException e){
                    Thread.currentThread().interrupt();
                }
            });
        }

        try(var executor = Executors.newVirtualThreadPerTaskExecutor()) {
            executor.execute(() -> {
                System.out.println("### Deuxième séquence ####");
                try {
                    Thread.sleep(1500);
                }catch (InterruptedException e){
                    Thread.currentThread().interrupt();
                }
            });
        }

        CompletableFuture<Void> step2 = step1.thenRunAsync(() -> {
            System.out.println("Etape 2 => Traitement des données...");
        });

        try(var executor = Executors.newVirtualThreadPerTaskExecutor()){
            executor.execute(() -> {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            });
        }

        step2.join();

        try(var executor = Executors.newVirtualThreadPerTaskExecutor()) {
            executor.execute(() -> {
                System.out.println("### Troisième et dernière séquence ####");
                try{
                    Thread.sleep(3000);
                }catch (InterruptedException e){
                    Thread.currentThread().interrupt();
                }
            });
        }

        CompletableFuture<Void> step3 = step2.thenRunAsync(() -> {
            System.out.println("Stockage des résultats !!");
        });

        step3.join();

        System.out.println("#### Fin de programme ####");


    }
}