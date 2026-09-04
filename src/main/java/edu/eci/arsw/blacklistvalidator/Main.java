/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.blacklistvalidator;

import java.util.List;
import java.util.LinkedList;
import java.util.Collections;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

/**
 *
 * @author hcadavid
 */
public class Main {
   public static void main(String a[]) throws InterruptedException{
       AtomicInteger contador = new AtomicInteger(0);
       List<Integer> ocurrencias = Collections.synchronizedList(new LinkedList<>());
       AtomicBoolean alarma = new AtomicBoolean(false);

       BlackListSearchThread hilo = new BlackListSearchThread(0, 8000, "202.24.34.55", contador, ocurrencias, alarma, 5);

       hilo.start();
       hilo.join();

       System.out.println("The host was found in the following blacklists" + ocurrencias);
       System.out.println("Total ocurrencias: " + ocurrencias);
   }
}