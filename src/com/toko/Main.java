package com.toko;

import sun.security.krb5.internal.APOptions;

import java.io.IOException;
import java.util.Scanner;

public class Main {

    /*
    * Pada Class Main ini Manajemen Menu dilakukan
    * user yang nantinya user akan diarahkan
    * pada operasi yang akan dilakukan
    * pada Class App
    */

    private static Scanner in = new Scanner(System.in);

    public static void main(String[] args) throws IOException{
        mainMenu();
    }

    public static void mainMenu() throws IOException {

        // Header pada console
        System.out.println("===============================================");
        System.out.println("==========       CV. KALIBUNTU       ==========");
        System.out.println("===============================================\n");

        int choice;
        // Menu
        do {
            System.out.println("==============     MAIN MENU     ==============");
            System.out.println("1. DATA BARANG");
            System.out.println("2. PENJUALAN");
            System.out.println("3. ADMINISTRASI");
            System.out.println("4. INFORMASI TOKO");
            System.out.println("5. KELUAR APLIKASI");
            System.out.println("===============================================");
            do {
                System.out.print("Masukkan pilihan anda : ");
                String input = in.next();
                choice = Utility.isInteger(input);
            } while (choice < 0 || choice > 6);

            switch (choice) {
                case 1:
                    menuDataBarang();
                    break;
                case 2:
                    menuPenjualan();
                    break;
                case 3:
                    menuAdministrasi();
                    break;
                case 4:
                    System.out.println("\n===============================================");
                    System.out.println("==                   ABOUT                   ==");
                    System.out.println("===============================================");
                    menuAbout();
                    break;
                case 5:
                    boolean isExit = Utility.confirm("Apakah anda yakin ingin keluar");
                    if (isExit) {
                        System.exit(0);
                    } else {
                        break;
                    }
                default:
                    System.out.println("Input yang anda masukkan salah");
            }
        } while (choice > 0 && choice < 6);
    }

    public static void menuDataBarang() throws IOException {

        int choice;
        do {

            System.out.println("\n===============================================");
            System.out.println("==                DATA BARANG                ==");
            System.out.println("===============================================");

            App.showItems();

            System.out.println("1. Tambah Data Barang");
            System.out.println("2. Edit Data Barang");
            System.out.println("3. Hapus Data Barang");
            System.out.println("4. Main Menu");
            do {
                System.out.print("Masukkan pilihan anda : ");
                String input = in.next();
                choice = Utility.isInteger(input);
            } while (choice < 0 || choice > 4);

            switch (choice) {
                case 1:
                    System.out.println("\n========================");
                    System.out.println("=  TAMBAH BARANG BARU  =");
                    System.out.println("========================");
                    App.addBarang();
                    break;
                case 2:
                    System.out.println("\n====================");
                    System.out.println("= EDIT DATA BARANG =");
                    System.out.println("====================");
                    App.editBarang();
                    break;
                case 3:
                    System.out.println("\n=====================");
                    System.out.println("= HAPUS DATA BARANG =");
                    System.out.println("=====================");
                    App.deleteBarang();
                    break;
                case 4:
                    choice = -1;
                    break;
                default:
                    System.out.println("Input yang anda masukkan salah");
            }
        } while (choice > 0 && choice < 4);
    }

    public static void menuPenjualan() throws IOException {

        int choice;
        do {

            System.out.println("\n===============================================");
            System.out.println("==                 PENJUALAN                 ==");
            System.out.println("===============================================");

            System.out.println("1. Point of Sale");
            System.out.println("2. Riwayat penjualan");
            System.out.println("3. Main Menu");
            do {
                System.out.print("Masukkan pilihan anda : ");
                String input = in.next();
                choice = Utility.isInteger(input);
            } while (choice < 0 || choice > 3);

            switch (choice) {
                case 1:
                    App.pointOfSale();
                    break;
                case 2:
                    App.transactionHistory();
                    break;
                case 3:
                    choice = -1;
                    break;
                default:
                    System.out.println("Input yang anda masukkan salah");
            }
        } while (choice > 0 && choice < 3);
    }

    public static void menuAdministrasi() throws IOException {
        int choice;
        do {

            System.out.println("\n================================================");
            System.out.println("==                ADMINISTRASI                ==");
            System.out.println("================================================");

            System.out.println("1. Barang Masuk");
            System.out.println("2. Stok Barang");
            System.out.println("3. Rangking Barang");
            System.out.println("4. Main Menu");
            do {
                System.out.print("Masukkan pilihan anda : ");
                String input = in.next();
                choice = Utility.isInteger(input);
            } while (choice < 0 || choice > 4);

            switch (choice) {
                case 1:
                    App.barangMasuk();
                    break;
                case 2:
                    App.stokItems();
                    break;
                case 3:
                    App.totalSold();
                    break;
                case 4:
                    choice = -1;
                    break;
                default:
                    System.out.println("Input yang anda masukkan salah");
            }
        } while (choice > 0 && choice < 4);
    }

    public static void menuAbout() {
    }
}
