package com.toko;

import javafx.beans.binding.ListBinding;
import org.omg.CORBA.CharHolder;
import org.omg.CORBA.WStringSeqHelper;

import java.io.IOException;
import java.util.Scanner;
import java.util.Vector;

public class App {

    /*
     * Pada Class App ini Proses CRUD akan dijalankan
     * yang nantinya Class ini akan berhubungan
     * langsung dengan Class FileOperation
     * setalah melakukan CRUD
     */

    // Fungsi untuk mendeklarasikan Scanner pada Class App
    private static Scanner in = new Scanner(System.in);

    /*
    * Fungsi-fungsi untuk CRUD
    */
    private static Vector<String> readFile(String filePath){
        FileOperation fo = new FileOperation(filePath);
        fo.readFile();
        Vector<String> data = new Vector<>(fo.getContent());

        return data;
    }

    public static String[] getData(String filePath, int field, String keyword) {
        Vector <String> data = readFile(filePath);
        String[] output = null;

            for (int i = 0; i < data.size() ; i++) {
                String[] ele = data.elementAt(i).split(",");
                if (ele[field].equals(keyword)) {
                    output = ele;
                }
            }


        return output;
    }

    private static void saveData(String filePath, String ele) {
        FileOperation fo = new FileOperation(filePath);
        fo.readFile();
        Vector<String> data = new Vector<>(fo.getContent());

        data.add(ele);
        fo.setContent(data);
        fo.writeFile();
    }

    private static void updateData(String filePath, String key, int field, String newData) {
        FileOperation fo = new FileOperation(filePath);
        fo.readFile();
        Vector<String> data = new Vector<>(fo.getContent());

        for (int i = 0; i < data.size(); i++) {
            String[] ele = data.elementAt(i).split(",");
            if (ele[field].equalsIgnoreCase(key)) {
                data.set(i, newData);
            }
        }

        fo.setContent(data);
        fo.writeFile();
    }

    private static void deleteData(String filePath, String keyword) {
        FileOperation fo = new FileOperation(filePath);
        fo.readFile();
        Vector<String> data = new Vector<>(fo.getContent());

        for (int i = 0; i < data.size(); i++) {
            if (data.elementAt(i).contains(keyword)){
                data.remove(i);
            }
        }

        fo.setContent(data);
        fo.writeFile();
    }
    /*
    * END fungsi CRUD
    */

    /*
    * Fungsi manajemen barang
     */
    public static void showItems() {

        Vector<String> data =  readFile("data_barang.txt");

        System.out.println("--------------------------------------------------------------------------------------------------------------");
        System.out.println("| No | ID Barang | Merk Cat       | Jenis Cat    | Kode Warna | Warna Cat | Kemasan | Stok | Harga Jual");
        System.out.println("--------------------------------------------------------------------------------------------------------------");

        int no = 0;
        if (!data.isEmpty()) {
            for (int i = 0; i < data.size(); i++) {
                String[] field = data.elementAt(i).split(",");
                no++;
                System.out.printf("| %02d ", no);
                System.out.printf("| %-10s", field[0]);
                System.out.printf("| %-15s", field[1]);
                System.out.printf("| %-13s", getData("data_jenis_cat.txt", 0, field[2])[1]);
                System.out.printf("| %-11s", getData("data_warna.txt", 0, field[3])[0]);
                System.out.printf("| %-10s", getData("data_warna.txt", 0, field[3])[1]);
                System.out.printf("| %-8s", getData("data_kemasan_cat.txt", 0, field[4])[1]);
                System.out.printf("| %-5s", getData("data_stok_barang.txt", 0, field[0])[3]);
                System.out.printf("| Rp. %11s\n", Utility.indoCurrency(field[5]));
            }
        } else {
            System.out.printf(" %60s \n", "Data Belum Tersedia");
        }
        System.out.println("--------------------------------------------------------------------------------------------------------------");
    }

    private static void showJenisCat() {

        Vector<String> data =  readFile("data_jenis_cat.txt");

        System.out.println("--------------------");
        System.out.println("| Kode | Jenis Cat  ");
        System.out.println("--------------------");

        if (!data.isEmpty()) {
            for (int i = 0; i < data.size(); i++) {
                String[] field = data.elementAt(i).split(",");
                System.out.printf("| %-5s", field[0]);
                System.out.printf("| %s\n", field[1]);
            }
        } else {
            System.out.printf(" %23s\n", "Data Belum Tersedia");
        }
        System.out.println("--------------------");
    }

    private static void showWarna() {

        Vector<String> data =  readFile("data_warna.txt");

        System.out.println("--------------------");
        System.out.println("| Kode | Nama Warna ");
        System.out.println("--------------------");

        if (!data.isEmpty()) {
            for (int i = 0; i < data.size(); i++) {
                String[] field = data.elementAt(i).split(",");
                System.out.printf("| %-5s", field[0]);
                System.out.printf("| %s\n", field[1]);
            }
        } else {
            System.out.printf(" %23s\n", "Data Belum Tersedia");
        }
        System.out.println("--------------------");
    }

    private static void showKemasan() {

        Vector<String> data =  readFile("data_kemasan_cat.txt");

        System.out.println("--------------------");
        System.out.println("| Kode | Kemasan  ");
        System.out.println("--------------------");

        if (!data.isEmpty()) {
            for (int i = 0; i < data.size(); i++) {
                String[] field = data.elementAt(i).split(",");
                System.out.printf("| %-5s", field[0]);
                System.out.printf("| %s\n", field[1]);
            }
        } else {
            System.out.printf(" %23s\n", "Data Belum Tersedia");
        }
        System.out.println("--------------------");
    }

    public static void addBarang() throws IOException{
        String label, type, color, volume, price;
        boolean isAdd, check;

        System.out.print("Merk Cat       : ");
        label = in.nextLine();
        showJenisCat();
        do {
            System.out.print("Kode Jenis Cat : ");
            type = in.next();
            check = Utility.isMatch("data_jenis_cat.txt", type);
            if (!check) {
                System.out.println("Kode tidak ditemukan pada Database!\n");
            }
        } while (!check);
        showWarna();
        do {
            System.out.print("Kode Warna Cat : ");
            color = in.next();
            check = Utility.isMatch("data_warna.txt", color);
            if (!check) {
                System.out.println("Kode tidak ditemukan pada Database!\n");
            }
        } while (!check);
        showKemasan();
        do {
            System.out.print("Kode Kemasan   : ");
            volume = in.next();
            check = Utility.isMatch("data_kemasan_cat.txt", volume);
            if (!check) {
                System.out.println("Kode tidak ditemukan pada Database!\n");
            }
        } while (!check);
        System.out.print("Harga Cat      : ");
        price = in.next();

        System.out.println("\n=============================");
        System.out.println("Data yang akan ditambahkan");
        System.out.println("=============================");
        System.out.println("Merk Cat       : " + label);
        System.out.println("Kode Jenis Cat : " + type);
        System.out.println("Kode Warna Cat : " + color);
        System.out.println("Kode Kemasan   : " + volume);
        System.out.println("Harga Cat      : " + price);
        System.out.println("=============================");
        isAdd = Utility.confirm("Apakah anda ingin menambahkan data tersebut");

        if (isAdd) {
            String item = Utility.primaryKey(type, volume) + "," + label + "," + type + "," + color + "," + volume + "," + price;
            String stock = Utility.primaryKey(type, volume) + "," + "0" + "," + "0" + "," + "0";
            saveData("data_barang.txt", item);
            saveData("data_stok_barang.txt", stock);
            System.out.println("Data berhasil ditambahkan\n");
        }
    }

    private static void viewBarang(String val) {

        String[] getId = val.split(","),
                 field;

        if (getId.length == 1) {
            field = getData("data_barang.txt",0, val);
        } else {
            field = getId;
        }

        System.out.println("--------------------------------------------------------------------------------------------------------------");
        System.out.println("| No | ID Barang | Merk Cat       | Jenis Cat    | Kode Warna | Warna Cat | Kemasan | Stok | Harga Jual");
        System.out.println("--------------------------------------------------------------------------------------------------------------");
        System.out.printf("| %02d ", 1);
        System.out.printf("| %-10s", field[0]);
        System.out.printf("| %-15s", field[1]);
        System.out.printf("| %-13s", getData("data_jenis_cat.txt", 0, field[2])[1]);
        System.out.printf("| %-11s", getData("data_warna.txt", 0, field[3])[0]);
        System.out.printf("| %-10s", getData("data_warna.txt", 0, field[3])[1]);
        System.out.printf("| %-8s", getData("data_kemasan_cat.txt", 0, field[4])[1]);
        try {
            System.out.printf("| %-5s", getData("data_stok_barang.txt", 0, field[0])[1]);
        } catch (Exception e) {
            System.out.printf("| %-5s","0");
        }
        System.out.printf("| Rp. %11s\n", Utility.indoCurrency(field[5]));
        System.out.println("--------------------------------------------------------------------------------------------------------------");

    }

    public static void editBarang() throws IOException {

        String findId;
        boolean check, isChaged = false;

        do {
            System.out.print("Masukkan ID barang : ");
            findId = in.next();
            check = Utility.isMatch("data_barang.txt", findId);
            if (!check) {
                System.out.println("ID barang tidak ditemukan pada Database!\n");
            }
        } while (!check);

        String[] field = getData("data_barang.txt", 0, findId);
        String  id = field[0],
                label = field[1],
                type = field[2],
                color = field[3],
                volume = field[4],
                price = field[5],
                merge;

        int choice;
        do {

            if (isChaged) {
                id = Utility.primaryKey(type, volume);
                merge = id + "," + label + "," + type + "," + color + "," + volume + "," + price;
            } else {
                merge = id + "," + label + "," + type + "," + color + "," + volume + "," + price;
            }
            viewBarang(merge);
            System.out.println("1. Edit Merk");
            System.out.println("2. Edit Jenis Cat");
            System.out.println("3. Edit Warna Cat");
            System.out.println("4. Edit Kemasan Cat");
            System.out.println("5. Edit Harga Cat");
            System.out.println("6. Simpan Data");
            System.out.println("7. Cancel");
            do {
                System.out.print("Masukkan pilihan anda : ");
                String input = in.next();
                try {
                    choice = Integer.valueOf(input);
                    if (choice < 0 || choice > 7) {
                        System.out.println("Input yang anda masukkan salah!");
                    }
                } catch (Exception e) {
                    choice = -1;
                    System.out.println("Input yang anda masukkan bukan angka!");
                }
            } while (choice < 0 || choice > 7);

            switch (choice) {
                case 1:
                    System.out.println("==============================");
                    System.out.print("Merk Cat       : ");
                    label = in.next();
                    System.out.println("==============================");
                    break;
                case 2:
                    showJenisCat();
                    do {
                        System.out.println("==============================");
                        System.out.print("Kode Jenis Cat : ");
                        type = in.next();
                        System.out.println("==============================");
                        check = Utility.isMatch("data_jenis_cat.txt", type);
                        if (!check) {
                            System.out.println("Kode tidak ditemukan pada Database!\n");
                        }
                    } while (!check);
                    isChaged = true;
                    break;
                case 3:
                    showWarna();
                    do {
                        System.out.println("==============================");
                        System.out.print("Kode Warna Cat : ");
                        color = in.next();
                        System.out.println("==============================");
                        check = Utility.isMatch("data_warna.txt", color);
                        if (!check) {
                            System.out.println("Kode tidak ditemukan pada Database!\n");
                        }
                    } while (!check);
                    break;
                case 4:
                    showKemasan();
                    do {
                        System.out.println("==============================");
                        System.out.print("Kode Kemasan   : ");
                        volume = in.next();
                        System.out.println("==============================");
                        check = Utility.isMatch("data_kemasan_cat.txt", volume);
                        if (!check) {
                            System.out.println("Kode tidak ditemukan pada Database!\n");
                        }
                    } while (!check);
                    isChaged = true;
                    break;
                case 5:
                    System.out.println("==============================");
                    System.out.print("Harga Cat      : ");
                    price = in.next();
                    System.out.println("==============================");
                    break;
                case 6:
                    System.out.println("\n==============================");
                    System.out.println(" Data Lama");
                    System.out.println("==============================");
                    viewBarang(findId);
                    System.out.println("==============================");
                    System.out.println(" Data Baru");
                    System.out.println("==============================");
                    viewBarang(merge);

                    boolean isUpdate = Utility.confirm("Apakah anda ingin mengedit data ini");
                    if (isUpdate) {
                        String[] stok = getData("data_stok_barang.txt", 0, field[0]);
                        String stokMerge = id + "," + stok[1] + "," + stok[2] + "," + stok[3];
                        Vector<String> transaction = readFile("data_riwayat_penjualan.txt");

                        updateData("data_barang.txt", field[0], 0, merge);
                        if (isChaged) {
                            updateData("data_stok_barang.txt", field[0], 0, stokMerge);

                            for (int i = 0; i < transaction.size(); i++) {
                                String[] ele = transaction.elementAt(i).split(",");

                                if (ele[1].equalsIgnoreCase(field[0])) {
                                    String transactionMerge = ele[0] + "," + id + "," + field[2];
                                    updateData("data_riwayat_penjualan.txt", field[0], 1, transactionMerge);
                                }
                            }

                            for (int i = 0; i < transaction.size(); i++) {
                                String[] ele = transaction.elementAt(i).split(",");

                                if (ele[1].equalsIgnoreCase(field[0])) {
                                    String transactionMerge = ele[0] + "," + id + "," + field[2];
                                    updateData("data_barang_masuk.txt", field[0], 1, transactionMerge);
                                }
                            }
                        }
                        System.out.println("Data Berhasil diUpdate!\n");
                        choice = -1;
                    }
                    break;
                case 7:
                    choice = -1;
                    break;
                default:
                    System.out.println("Input yang anda masukkan salah");
            }

        } while (choice > 0 && choice < 7);

    }

    public static void deleteBarang(){

        String id;
        boolean check;

        do {
            System.out.print("Masukkan ID barang : ");
            id = in.next();
            check = Utility.isMatch("data_barang.txt", id);
            if (!check) {
                System.out.println("ID barang tidak ditemukan pada Database!\n");
            }
        } while (!check);

        viewBarang(id);

        boolean isDeleted = Utility.confirm("Apakah anda ingin menghapus data ini");

        if (isDeleted) {
            deleteData("data_barang.txt", id);
            deleteData("data_stok_barang.txt", id);
        }
        showItems();

    }
    /*
    * END Fungsi manajemen barang
     */

    /*
    * Fungsi manajemen penjualan
     */
    private static String totalPrice(Vector<String> data) {
        String output = "0";

        if (data.size() != 0) {
            for (int i = 0; i < data.size(); i++) {
                String[] ele = data.elementAt(i).split(",");
                String[] item = getData("data_barang.txt", 0, ele[0]);
                if (item == null) {
                    item = getData("data_barang_deleted.txt", 0, ele[0]);
                }
                output = String.valueOf(Integer.valueOf(output) + (Integer.valueOf(item[5]) * Integer.valueOf(ele[1])));
            }
        }

        return output;
    };

    private static void  viewTransaksi(String name, Vector<String> data, String id) {

        Vector<String> price = new Vector<>();

        String idTransaction;
        if (id == null) {
            idTransaction = Utility.dateNow("ddMMyy") + Utility.makeIndex("data_penjualan.txt", Utility.dateNow("ddMMyy"), 6);
        } else {
            idTransaction = id;
        }

        System.out.println("----------------------------------------------------------------------------------------------------");
        System.out.println("Nomor Transaksi : " + idTransaction);
        System.out.println("Nama Pembeli    : " + name);
        System.out.println("----------------------------------------------------------------------------------------------------");
        System.out.println("| No | Nama Barang                              | QTY | Harga           | Total                ");
        System.out.println("----------------------------------------------------------------------------------------------------");

        if (!data.isEmpty()) {
            int no = 0;
            for (int i = 0; i < data.size(); i++) {
                String[] ele = data.elementAt(i).split(",");
                String[] item = getData("data_barang.txt", 0, ele[0]);
                if (item == null) {
                    item = getData("data_barang_deleted.txt", 0, ele[0]);
                }
                String[] type = getData("data_jenis_cat.txt", 0, item[2]);
                String[] color = getData("data_warna.txt", 0, item[3]);
                String[] volume = getData("data_kemasan_cat.txt", 0, item[4]);
                String itemName = item[1] + " - " + type[1] + " " + volume[1] + " " + color[1];
                String total = String.valueOf(Integer.valueOf(ele[1]) * Integer.valueOf(item[5]));
                price.add(total);

                no++;
                System.out.printf("| %02d ", no);
                System.out.printf("| %-40s ", itemName);
                System.out.printf("| %-3s ", ele[1]);
                System.out.printf("| Rp. %11s ", Utility.indoCurrency(item[5]));
                System.out.printf("| Rp. %11s \n", Utility.indoCurrency(total));
            }

        } else {
            System.out.printf("%70s\n", "Belum ada Transaksi yang dilakukan");
        }

        System.out.println("----------------------------------------------------------------------------------------------------");
        System.out.printf("| TOTAL HARGA                                                           | Rp. %11s\n", Utility.indoCurrency(totalPrice(data)));
        System.out.println("----------------------------------------------------------------------------------------------------");
    }

    private static void payment(String cash, String totalPrice) {
        String result;
        result = String.valueOf(Integer.valueOf(cash) - Integer.valueOf(totalPrice));
        System.out.printf("| BAYAR                                                                 | Rp. %11s\n", Utility.indoCurrency(cash));
        System.out.printf("| KEMBALIAN                                                             | Rp. %11s\n", Utility.indoCurrency(result));
        System.out.println("----------------------------------------------------------------------------------------------------");

    }

    public static void pointOfSale() {

        System.out.println("\n=========================");
        System.out.println("=  TRANSAKSI PENJUALAN  =");
        System.out.println("=========================");

        Vector<String> items = new Vector<>();
        String name = null,
                cash = null,
                result = null;
        int choice;
        do {

            viewTransaksi(name, items, null);

            if (cash != null && items != null) {
                payment(cash, totalPrice(items));
            }

            System.out.println("1. Nama pembeli");
            System.out.println("2. Tambah Barang");
            System.out.println("3. Pembayaran");
            System.out.println("4. Simpan Data");
            System.out.println("5. Cancel");
            do {
                System.out.print("Masukkan pilihan anda : ");
                String input = in.next();
                try {
                    choice = Integer.valueOf(input);
                    if (choice < 0 || choice > 5) {
                        System.out.println("Input yang anda masukkan salah!");
                    }
                } catch (Exception e) {
                    choice = -1;
                    System.out.println("Input yang anda masukkan bukan angka!");
                }
            } while (choice < 0 || choice > 5);

            switch (choice) {
                case 1:
                    System.out.print("Masukkan Nama pembeli : ");
                    name = in.next();
                    break;
                case 2:
                    showItems();
                    boolean check;
                    String id, total;
                    do {
                        System.out.print("Masukkan ID barang : ");
                        id = in.next();
                        check = Utility.isMatch("data_barang.txt", id);
                        if (!check) {
                            System.out.println("ID barang tidak ditemukan pada Database!\n");
                        }
                    } while (!check);
                    System.out.print("Jumlah             : ");
                    total = in.next();

                    items.add(id + "," + total);

                    break;
                case 3:
                    System.out.print("Masukkan Uang : ");
                    cash = in.next();
                    break;
                case 4:
                    boolean isSave = Utility.confirm("Apakah anda ingin menyelesaikan transaksi");

                    if(isSave) {
                        String primaryKey = Utility.dateNow("ddMMyy") + Utility.makeIndex("data_penjualan.txt", Utility.dateNow("ddMMyy"), 6);
                        String dataPenjualan = primaryKey + "," + name + "," + totalPrice(items) + "," + cash;
                        saveData("data_penjualan.txt", dataPenjualan);

                        for (int i = 0; i < items.size(); i++) {
                            String[] field = items.elementAt(i).split(",");
                            String dataRiwayat = primaryKey + "," + field[0] + "," + field[1];

                            String[] stok = getData("data_stok_barang.txt", 0, field[0]);
                            String itemStok = String.valueOf(Integer.valueOf(stok[1]) - Integer.valueOf(field[1]));
                            String stokTotal = String.valueOf(Integer.valueOf(itemStok) - Integer.valueOf(stok[2]));
                            String newStok = stok[0] + "," + itemStok + "," + stok[2] + "," + stokTotal;

                            saveData("data_riwayat_penjualan.txt", dataRiwayat);
                            updateData("data_stok_barang.txt", field[0], 0, newStok);
                        }

                        choice = -1;
                    }
                    break;
                case 5:
                    choice = -1;
                    break;
                default:
                    System.out.println("Input yang anda masukkan salah");
            }

        } while (choice > 0 && choice < 5);
    }

    private static void viewHistory() {

        Vector<String> data = readFile("data_penjualan.txt");

        System.out.println("--------------------------------------------------");
        System.out.println("| NO | Nomor Transaksi | Nama Pembeli ");
        System.out.println("--------------------------------------------------");

        if (data.size() == 0) {
            System.out.printf(" %-35s", "Belum ada transaksi");
        } else {
            int no = 0;
            for (int i = 0; i < data.size(); i++) {

                String[] ele = data.elementAt(i).split(",");

                no++;
                System.out.printf("| %02d ", no);
                System.out.printf("| %-15s ", ele[0]);
                System.out.printf("| %s\n", ele[1]);
            }
        }

        System.out.println("--------------------------------------------------");

    }

    public static void transactionHistory() {

        int choice;
        do {

            System.out.println("\n=====================");
            System.out.println("= RIWAYAT PENJUALAN =");
            System.out.println("=====================");

            viewHistory();
            System.out.println("1. Detail transaksi");
            System.out.println("2. Kembali");
            do {
                System.out.print("Masukkan pilihan anda : ");
                String input = in.next();
                choice = Utility.isInteger(input);
            } while (choice < 0 || choice > 2);

            switch (choice) {
                case 1:
                    String id;
                    boolean check;
                    Vector<String> items = new Vector<>();

                    do {
                        System.out.print("Masukkan Nomor Transaksi : ");
                        id = in.next();
                        check = Utility.isMatch("data_penjualan.txt", id);
                        if (!check) {
                            System.out.println("Nomor Transaksi tidak ditemukan pada Database!\n");
                        }
                    } while (!check);

                    Vector<String> dataHistory = readFile("data_riwayat_penjualan.txt");
                    String[] sale = getData("data_penjualan.txt", 0, id);

                    for (int i = 0; i < dataHistory.size(); i++) {
                        String[] field = dataHistory.elementAt(i).split(",");
                        if (field[0].equalsIgnoreCase(id)) {
                            items.add(field[1] + "," + field[2]);
                        }
                    }
                    viewTransaksi(sale[1], items, id);
                    payment(sale[3], sale[2]);

                    boolean isContinue = Utility.confirm("Apakah anda ingin melanjutkan");
                    if (!isContinue) {
                        choice = -1;
                    }

                    break;
                case 2:
                    choice = -1;
                    break;
                default:
                    System.out.println("Input yang anda masukkan salah");
            }
        } while (choice > 0 && choice < 2);
    }

    /*
    * END Fungsi menejemen penjualan
     */

    /*
     * Fungsi pada menu Administrasi
     */
    private static void viewBarangMasuk() {

        Vector<String> data = readFile("data_barang_masuk.txt");

        System.out.println("------------------------------------------------------------------------------------------");
        System.out.println("| No | ID Barang | Nama Barang                              | QTY | Tanggal Masuk ");
        System.out.println("------------------------------------------------------------------------------------------");
        int no = 0;
        for (int i = 0; i < data.size(); i++) {
            String[] field = data.elementAt(i).split(",");
            String[] item = getData("data_barang.txt", 0, field[0]);
            if (item == null) {
                item = getData("data_barang_deleted.txt", 0, field[0]);
            }
            String[] type = getData("data_jenis_cat.txt", 0, item[2]);
            String[] color = getData("data_warna.txt", 0, item[3]);
            String[] volume = getData("data_kemasan_cat.txt", 0, item[4]);
            String itemName = item[1] + " - " + type[1] + " " + volume[1] + " " + color[1];

            no++;
            System.out.printf("| %02d ", no);
            System.out.printf("| %-9s ", field[0]);
            System.out.printf("| %-40s ", itemName);
            System.out.printf("| %-3s ", field[1]);
            System.out.printf("| %s \n", field[2]);
        }
        System.out.println("------------------------------------------------------------------------------------------");
    }

    public static void barangMasuk() {

        int choice;
        do {

            System.out.println("\n========================");
            System.out.println("= RIWAYAT BARANG MASUK =");
            System.out.println("========================");

            viewBarangMasuk();

            System.out.println("1. Data Barang Masuk");
            System.out.println("2. Kembali");
            do {
                System.out.print("Masukkan pilihan anda : ");
                String input = in.next();
                choice = Utility.isInteger(input);
            } while (choice < 0 || choice > 2);

            switch (choice) {
                case 1:
                    boolean isContinue;
                    do {

                        showItems();

                        String id;
                        int amount;
                        boolean check;

                        do {
                            System.out.print("Masukkan ID barang masuk : ");
                            id = in.next();
                            check = Utility.isMatch("data_barang.txt", id);
                            if (!check) {
                                System.out.println("Barang tidak ditemukan pada Database!\n");
                            }
                        } while (!check);

                        do {
                            System.out.print("Jumlah barang masuk      : ");
                            String input = in.next();
                            amount = Utility.isInteger(input);
                        } while (amount < 0);

                        boolean isAdd = Utility.confirm("Simpan data");
                        if (isAdd) {

                            String[] dataStok = getData("data_stok_barang.txt", 0, id);
                            String addStok = String.valueOf(Integer.valueOf(dataStok[1]) + Integer.valueOf(amount));
                            String totalStok = String.valueOf(Integer.valueOf(addStok) - Integer.valueOf(dataStok[2]));

                            String dataStokFormat = dataStok[0] + "," + addStok + "," + dataStok[2] + "," + totalStok;
                            String historyStokFormat = dataStok[0] + "," + Integer.valueOf(amount) + "," + Utility.dateNow("dd/MM/YYYY - HH:mm:ss");

                            updateData("data_stok_barang.txt", id, 0, dataStokFormat);
                            saveData("data_barang_masuk.txt", historyStokFormat);
                        }

                        isContinue = Utility.confirm("Apakah ingin memasukkan barang lagi");

                    } while (isContinue);
                    break;
                case 2:
                    choice = -1;
                    break;
                default:
                    System.out.println("Input yang anda masukkan salah");
            }
        } while (choice > 0 && choice < 2);

    }

    private static void viewStok() {
        Vector<String> data = readFile("data_stok_barang.txt");

        System.out.println("----------------------------------------------------------------------------------------------------");
        System.out.println("| No | ID Barang | Nama Barang                              | Stok Awal  | Rusak      | Stok Akhir ");
        System.out.println("----------------------------------------------------------------------------------------------------");
        int no = 0;
        for (int i = 0; i < data.size(); i++) {
            String[] field = data.elementAt(i).split(",");
            String[] item = getData("data_barang.txt", 0, field[0]);
            if (item == null) {
                continue;
            }
            String[] type = getData("data_jenis_cat.txt", 0, item[2]);
            String[] color = getData("data_warna.txt", 0, item[3]);
            String[] volume = getData("data_kemasan_cat.txt", 0, item[4]);
            String itemName = item[1] + " - " + type[1] + " " + volume[1] + " " + color[1];

            no++;
            System.out.printf("| %02d ", no);
            System.out.printf("| %-9s ", field[0]);
            System.out.printf("| %-40s ", itemName);
            System.out.printf("| %-10s ", field[1]);
            System.out.printf("| %-10s ", field[2]);
            System.out.printf("| %s \n", field[3]);
        }
        System.out.println("----------------------------------------------------------------------------------------------------");
    }

    private static void viewItemStok(String id) {
        String[] field = getData("data_stok_barang.txt", 0, id);

        System.out.println("----------------------------------------------------------------------------------------------------");
        System.out.println("| No | ID Barang | Nama Barang                              | Stok Awal  | Rusak      | Stok Akhir ");
        System.out.println("----------------------------------------------------------------------------------------------------");

        String[] item = getData("data_barang.txt", 0, field[0]);
        String[] type = getData("data_jenis_cat.txt", 0, item[2]);
        String[] color = getData("data_warna.txt", 0, item[3]);
        String[] volume = getData("data_kemasan_cat.txt", 0, item[4]);
        String itemName = item[1] + " - " + type[1] + " " + volume[1] + " " + color[1];

        System.out.printf("| %02d ", 1);
        System.out.printf("| %-9s ", field[0]);
        System.out.printf("| %-40s ", itemName);
        System.out.printf("| %-10s ", field[1]);
        System.out.printf("| %-10s ", field[2]);
        System.out.printf("| %s \n", field[3]);
        System.out.println("----------------------------------------------------------------------------------------------------");
    }

    private static void viewReportHistory() {
        Vector<String> data = readFile("data_laporan_barang.txt");

        System.out.println("----------------------------------------------------------------------------------------------------");
        System.out.println("| No | ID Barang | Nama Barang                              | Kerusakan | Tanggal Pelaporan ");
        System.out.println("----------------------------------------------------------------------------------------------------");
        int no = 0;
        for (int i = 0; i < data.size(); i++) {
            String[] field = data.elementAt(i).split(",");
            String[] item = getData("data_barang.txt", 0, field[0]);
            if (item == null) {
                continue;
            }
            String[] type = getData("data_jenis_cat.txt", 0, item[2]);
            String[] color = getData("data_warna.txt", 0, item[3]);
            String[] volume = getData("data_kemasan_cat.txt", 0, item[4]);
            String itemName = item[1] + " - " + type[1] + " " + volume[1] + " " + color[1];

            no++;
            System.out.printf("| %02d ", no);
            System.out.printf("| %-9s ", field[0]);
            System.out.printf("| %-40s ", itemName);
            System.out.printf("| %-9s ", field[1]);
            System.out.printf("| %s \n", field[2]);
        }
        System.out.println("----------------------------------------------------------------------------------------------------");

    }

    public static void stokItems() {

        int choice;
        do {

            System.out.println("\n===============");
            System.out.println("= STOK BARANG =");
            System.out.println("===============");

            viewStok();

            System.out.println("1. Laporkan Kerusakan Barang");
            System.out.println("2. Riwayat Pelaporan Barang");
            System.out.println("3. Kembali");
            do {
                System.out.print("Masukkan pilihan anda : ");
                String input = in.next();
                choice = Utility.isInteger(input);
            } while (choice < 0);

            switch (choice) {
                case 1:
                    String id;
                    int damaged;
                    boolean check;
                    Vector<String> items = new Vector<>();

                    do {
                        System.out.print("Masukkan ID Barang : ");
                        id = in.next();
                        check = Utility.isMatch("data_stok_barang.txt", id);
                        if (!check) {
                            System.out.println("Barang tidak ditemukan pada Database!\n");
                        }
                    } while (!check);

                    viewItemStok(id);

                    do {
                        System.out.print("Jumlah kerusakan : ");
                        String input = in.next();
                        damaged = Utility.isInteger(input);
                    } while (damaged < 0);

                    boolean isSave = Utility.confirm("Laporkan keadaan barang");

                    if (isSave) {
                        String[] stok = getData("data_stok_barang.txt", 0, id);
                        String report = id + "," + damaged + "," + Utility.dateNow("dd/MM/yyyy - HH:mm:ss");
                        String result = String.valueOf(Integer.valueOf(stok[1]) - damaged);
                        String newStok = stok[0] + "," + stok[1] + "," + damaged + "," + result;

                        updateData("data_stok_barang.txt", id, 0, newStok);
                        saveData("data_laporan_barang.txt", report);
                    }
                    break;
                case 2:

                    viewReportHistory();

                    boolean isBack = Utility.confirm("Kembali ke Menu Stok Barang");
                    if (!isBack) {
                        choice = -1;
                    }
                    break;
                case 3:
                    choice = -1;
                    break;
                default:
                    System.out.println("Input yang anda masukkan salah");
            }
        } while (choice > 0 && choice < 3);

    }

    public static void totalSold() {

        Vector<String> salesHistory = readFile("data_riwayat_penjualan.txt");
        Vector<String> items = readFile("data_barang.txt");
        Vector<String> itemSold = new Vector<>();

        for (int i = 0; i < items.size(); i++) {
            String[] itemField = items.elementAt(i).split(",");
            String sum = "0";

            for (int j = 0; j < salesHistory.size(); j++) {
                String[] sale = salesHistory.elementAt(j).split(",");

                if (itemField[0].equalsIgnoreCase(sale[1])) {
                    sum = String.valueOf(Integer.valueOf(sum) + Integer.valueOf(sale[2]));
                }
            }

            itemSold.add(itemField[0] +  "," + sum);
        }

        viewSales(itemSold);

        int choice = 0;
        do {
            System.out.println("1. Urutkan barang paling banyak terjual");
            System.out.println("2. Urutkan barang paling sedikit terjual");
            System.out.println("3. kembali");
            do {
                System.out.print("Masukkan pilihan anda : ");
                String input = in.next();
                choice = Utility.isInteger(input);
            } while (choice > 3);

            switch (choice) {
                case 1:
                    selectionSorting(itemSold, false);
                    viewSales(itemSold);
                    break;
                case 2:
                    selectionSorting(itemSold, true);
                    viewSales(itemSold);
                    break;
                case 3:
                    choice = -1;
                default:
                    System.out.println("Inputan yang anda masukkan salah!");
            }
        } while (choice > 0 && choice < 3);


    }

    private static Vector<String> selectionSorting(Vector<String> items, boolean asc) {

        for (int i = 0; i < items.size() - 1; i++) {
            int index = i;
            for (int j = i + 1; j < items.size(); j++) {
                int sale = Integer.valueOf(items.elementAt(j).split(",")[1]);

                if (asc) {
                    if (sale < Integer.valueOf(items.elementAt(index).split(",")[1])) {
                        index = j;
                    }
                } else {
                    if (sale > Integer.valueOf(items.elementAt(index).split(",")[1])) {
                        index = j;
                    }
                }
            }

            String sortedData = items.elementAt(i);
            items.set(i, items.elementAt(index));
            items.set(index, sortedData);

        }

        return items;
    }

    private static void viewSales(Vector<String> items) {
        System.out.println("--------------------------------------------------------------------------------");
        System.out.println("                                 Data Penjualan                                 ");
        System.out.println("--------------------------------------------------------------------------------");
        System.out.println("| No | ID Barang | Nama Barang                              | Terjual");
        System.out.println("--------------------------------------------------------------------------------");

        int no = 0;
        for (int i = 0; i < items.size(); i++) {
            String[] field = items.elementAt(i).split(",");
            String[] item = getData("data_barang.txt", 0, field[0]);
            if (item == null) {
                continue;
            }
            String[] type = getData("data_jenis_cat.txt", 0, item[2]);
            String[] color = getData("data_warna.txt", 0, item[3]);
            String[] volume = getData("data_kemasan_cat.txt", 0, item[4]);
            String itemName = item[1] + " - " + type[1] + " " + volume[1] + " " + color[1];

            no++;
            System.out.printf("| %02d ", no);
            System.out.printf("| %-9s ", field[0]);
            System.out.printf("| %-40s ", itemName);
            System.out.printf("| %s\n", field[1]);
        }


        System.out.println("--------------------------------------------------------------------------------");
        System.out.println("Update : " + Utility.dateNow("dd/MM/yyyy - HH:mm:ss"));
        System.out.println("--------------------------------------------------------------------------------");

    }
    /*
     * End Fungsi menu Administrasi
     */
}
