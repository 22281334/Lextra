package comp5216.sydney.edu.au.lextra.uos;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Scanner;

public class UosParser {

    public static ArrayList<UoS> readUoSFromCSV(File f) throws FileNotFoundException {

        Scanner sc = new Scanner(f);
        int i = 0;
        ArrayList<UoS> unitsOfStudy = new ArrayList<>();
        while (sc.hasNextLine()) {
            String line = sc.nextLine();
            if (i != 0) {
                String[] fields = line.split("\t");
                UoS thisUnitOfStudy = new UoS(Integer.parseInt(fields[0]), fields[1], fields[2], fields[3], fields[4], fields[5], fields[6]);
                unitsOfStudy.add(thisUnitOfStudy);
            }
            i++;
        }
        return unitsOfStudy;
    }

    /**
     *
     * change input type InputStream for map read
     * Louis
     *
     */
    public static ArrayList<UoS> readUoSFromCSV(InputStream f) throws FileNotFoundException {

        Scanner sc = new Scanner(f);
        int i = 0;
        ArrayList<UoS> unitsOfStudy = new ArrayList<>();
        while (sc.hasNextLine()) {
            String line = sc.nextLine();
            if (i != 0) {
                String[] fields = line.split("\t");
                UoS thisUnitOfStudy = new UoS(Integer.parseInt(fields[0]), fields[1], fields[2], fields[3], fields[4], fields[5], fields[6]);
                unitsOfStudy.add(thisUnitOfStudy);
            }
            i++;
        }
        return unitsOfStudy;
    }

}
