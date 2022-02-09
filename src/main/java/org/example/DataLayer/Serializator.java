package org.example.DataLayer;

import org.example.BussinessLayer.DeliveryService;

import java.io.*;
/**
 *
 * @author Grama Malina Bianca
 * @since June 03, 2021
 * Class that serializes and deserializes the main class that contains all of the necessary data structures, namely DeliveryService
 *
 */
public class Serializator {

    public static void serializeDeliveryService(DeliveryService deliveryService) {
        try {
            FileOutputStream fileOut = new FileOutputStream("deliveryService.ser");
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(deliveryService);
            out.close();
            fileOut.close();
            System.out.println("Serialized data is saved in deliveryService.ser");
        } catch (IOException i) {
            i.printStackTrace();
        }
    }

    public static DeliveryService deserializeDeliveryService() throws IOException {
        DeliveryService deliveryService;
        try {
            FileInputStream fileIn = new FileInputStream("deliveryService.ser");
            ObjectInputStream in = new ObjectInputStream(fileIn);
            deliveryService = (DeliveryService) in.readObject();
            in.close();
            fileIn.close();
            System.out.println(deliveryService);
            return deliveryService;
        } catch (IOException i) {
            System.out.println(i);
            deliveryService = new DeliveryService();
            serializeDeliveryService(deliveryService);
            return deliveryService;
        } catch (ClassNotFoundException c) {
            System.out.println("DeliveryService class not found");
            c.printStackTrace();
            return new DeliveryService();
        }
    }
}
