package com.toko;

import java.sql.SQLOutput;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;
import java.util.Vector;

public class Utility {

    /*
     * Class Utility ini adalah Class untuk membantu pemformatan data
     * yang akan ditampilkan ataupun yang akan diinput ke Database
     * serta fungsi yang berguna untuk proses CRUD nantinya
     */

    // Fungsi untuk mendeklarasikan Scanner pada Class App
    private static Scanner in = new Scanner(System.in);

    // Fungsi untuk menampilkan pesan
    public static boolean confirm(String message) {
        System.out.print(message + " (y/n)? ");
        String userInput = in.next();

        // Cek apabila input user bukan y atau n
        while (!userInput.equalsIgnoreCase("y") && !userInput.equalsIgnoreCase("n")) {
            System.out.println("Pilihan anda bukan y atau n");
            System.out.print("\n" + message + " (y/n)? ");
            userInput = in.next();
        }

        return userInput.equalsIgnoreCase("y");
    }

    // Membuat auto increment pada ID barang apabila ada jenis dan kemasannya sama
    public static String makeIndex(String filePath, String keyword, int readString) {
        FileOperation fo = new FileOperation(filePath);
        fo.readFile();
        Vector<String> data =  new Vector<>(fo.getContent());
        String output = null;
        int total = 0;

        for (int i = 0; i < data.size(); i++) {

            String[] ele = data.elementAt(i).split(",");

            if (ele[0].substring(0,readString).equalsIgnoreCase(keyword)){
                total++;
            }
        }

        output = String.valueOf(total);

        if (output.length() == 1) {

            if (output.equals("0")) {
                output = "001";
            } else {
                output = "00" + String.valueOf(total + 1);
            }

        } else if (output.length() == 2) {
            output = "0" + String.valueOf(total + 1);
        }

        return output;
    }

    // Membentuk PrimaryKey pada ID barang agar mudah untuk mencarinya
    public static String primaryKey(String type, String volume){
        String  output = null,
                left, mid, right;
        String[] firstKey = App.getData("data_jenis_cat.txt", 0, type)[1].split("\\s");

        left = firstKey[0].substring(0, 1) + firstKey[1].substring(0, 1);
        mid = volume;
        String keyword = left + mid;
        right = makeIndex("data_barang.txt", keyword, 3);

        output = left + mid + right;

        return output;
    }

    // Mencari data pada database
    public static boolean isMatch(String filePath, String keyword) {
        FileOperation fo = new FileOperation(filePath);
        fo.readFile();
        Vector<String> data =  new Vector<>(fo.getContent());
        boolean match = false;

        // Cek apakah ada data diDatabase
        for (int i = 0; i < data.size() ; i++) {
            String[] ele = data.elementAt(i).split(",");
            if (ele[0].equals(keyword)) {
                match = true;
            }
        }

        return match;
    }

    public static int isInteger(String check){
        int output = 0;

        try {
            output = Integer.valueOf(check);
            if (output < 0) {
                System.out.println("Inputan anda tidak valid!\n");
            }
        } catch (Exception e) {
            output = -1;
            System.out.println("Inputan anda bukan angka!\n");
        }

        return output;
    }

    // Fungsi tampilan mata uang Rupiah pada console
    public static String indoCurrency(String value) {
        int currency = Integer.valueOf(value);
        // Membuat format Currency INDONESIA dipemrograman Java
        DecimalFormat indo = (DecimalFormat) DecimalFormat.getCurrencyInstance();
        DecimalFormatSymbols rupiahs = new DecimalFormatSymbols();

        rupiahs.setCurrencySymbol("");
        rupiahs.setMonetaryDecimalSeparator(',');
        rupiahs.setGroupingSeparator('.');
        indo.setDecimalFormatSymbols(rupiahs);
        // end Currency INDONESIA

        return indo.format(currency);
    }

    // Fungsi untuk membuat tanggal dan waktu yang akan dimasukkan ke database
    public static String dateNow(String format) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern(format);
        LocalDateTime now = LocalDateTime.now();
        return dtf.format(now);
    }

}
