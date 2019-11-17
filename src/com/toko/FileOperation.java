package com.toko;

import java.io.*;
import java.util.Vector;

public class FileOperation {

    /*
     * Class FileOperation ini akan berhubungan langsung pada Database
     * koneksi ke file, read file, write file akan dilakukan
     * pada Class ini, serta memformat data
     * yang nantinya akan diolah
     */

    // Pemberian nama alias untuk fungsi" yang akan digunakan pada Class ini
    private Vector<String> content;
    private String filePath, collect;
    private FileWriter write;
    private FileReader read;
    private BufferedWriter writer;
    private BufferedReader reader;

    // Constructor untuk operasi file dengan menambahkan argumen / parameter tempat dan nama file
    public FileOperation(String filePath) {
        setFilePath(filePath);
        try {
            read = new FileReader(filePath);
        } catch (Exception ex) {
            System.out.println("File " + filePath + " not found(404)");
            System.exit(0);
        }
    }

    // Fungsi untuk membaca file dan menginputkan element / baris ke dalam vector content
    public void readFile() {
        reader = new BufferedReader(read);
        content = new Vector<>();
        do {
            try {
                collect = reader.readLine();
                if (collect == null) {
                    break;
                }
                content.add(collect);
            } catch(IOException ex) {
                System.out.println("Error when reading the file");
            }
        } while(true);

        try {
            reader.close();
        } catch(IOException ex) {
            System.out.println("Error : " + ex.getMessage());
        }
    }

    // Fungsi untuk melakukan perubahan didalam File
    public void writeFile() {
        try {
            write = new FileWriter(filePath);
        } catch(IOException ex) {
            System.out.println("File " + filePath + " not found(404)");
        }
        writer = new BufferedWriter(write);
        for (int i = 0; i < content.size(); i++) {
            try {
                writer.write(content.elementAt(i));
                if (i < content.size() - 1) {
                    writer.newLine();
                }
            } catch(IOException ex) {
                System.out.println("Error when writing the file");
            }
        }
        try {
            writer.close();
        } catch(IOException ex) {
            System.out.println("Error : " + ex.getMessage());
        }
    }

    // Fungsi untuk mengambil isi dari Vector Content
    public Vector<String> getContent() {
        return content;
    }

    // Fungsi untuk memasukkan data ke vector content
    public void setContent(Vector<String> content) {
        this.content = content;
    }

    // Fungsi untuk memberi informasi tempat file berada
    public String getFilePath() {
        return filePath;
    }

    // fungsi untuk mendefinisikan dimana file berada dan juga apa nama filenya
    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }
}
