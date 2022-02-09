package org.example.DataLayer;

import org.example.BussinessLayer.BaseProduct;
import org.example.BussinessLayer.MenuItem;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
/**
 *
 * @author Grama Malina Bianca
 * @since June 03, 2021
 * Class that helps with the reading of products from the CSV file, using streams
 *
 */
public class FileWriter {

    private static <T> Predicate<T> distinctByKey(Function<? super T, ?> keyExtractor) {

        Map<Object, Boolean> seen = new ConcurrentHashMap<>();
        return t -> seen.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
    }

    /**
     * Function that splits a line and returns a BaseProduct with the specified parameters
     */
    private static final Function<String, MenuItem> mapToItem = (line) -> {

        String[] p = line.split(",");// a CSV has comma separated lines

        String name = p[0];
        double rating = Double.parseDouble(p[1]);
        int calories = Integer.parseInt(p[2]);
        int protein = Integer.parseInt(p[3]);
        int fat = Integer.parseInt(p[4]);
        int sodium = Integer.parseInt(p[5]);
        int price = Integer.parseInt(p[6]);

        return (MenuItem) new BaseProduct(name, rating, calories, protein, fat, sodium, price);
    };

    /**
     * Reads the CSV file using streams and returns a List of MenuItems
     * @return an ArrayList that holds all of the fields read from the CSV file
     */
    public static List<MenuItem> readProd() {
        List<MenuItem> inputList = new ArrayList<>();
        List<MenuItem> menu;

        try{
            File inputF = new File("products.csv");
            InputStream inputFS = new FileInputStream(inputF);
            BufferedReader br = new BufferedReader(new InputStreamReader(inputFS));

            // skip the header of the csv
            inputList = br.lines().skip(1).map(mapToItem).collect(Collectors.toList());
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        menu = inputList.stream().filter(distinctByKey(MenuItem::getName))
                .collect(Collectors.toCollection(ArrayList::new));

        return menu;
    }
}
