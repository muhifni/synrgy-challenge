import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        List<String> makananYangDipilih = new ArrayList<>();

        System.out.println("==========================================");
        System.out.println("Selamat Datang di Nasi Goreng Mamang Hifni");
        System.out.println("==========================================\n");

        String[] daftarMenu = {
                "Nasi/mie Goreng Biasa   | 10.000 | ",
                "Nasi/mie Goreng Ampela  | 13.000 | ",
                "Nasi/mie Goreng Bakso   | 13.000 | ",
                "Nasi/mie Goreng Ayam    | 15.000 | ",
                "Nasi/mie Goreng Seafood | 17.000 | ",
                "Nasi/mie Goreng Komplit | 25.000 | ",
                "Air putih               | 1.000  | ",
                "Teh manis (es/panas)    | 3.000  | ",
                "Jeruk manis (es/panas)  | 5.000  | ",
        };

        int pilihMenu;

        do {
            tampilkanMenu(daftarMenu);

            System.out.println("99. Pesan & Bayar");
            System.out.println("0. Keluar Aplikasi\n");
            System.out.print("=> ");
            pilihMenu = input.nextInt();

            if (pilihMenu >= 1 && pilihMenu <= 9) {
                pesanMenu(pilihMenu, daftarMenu, makananYangDipilih);
            } else if (pilihMenu == 99) {
                pesanDanBayar(makananYangDipilih);
            } else {
                System.out.println("======================");
                System.out.println("Pilihan tidak valid!!!".toUpperCase());
                System.out.println("======================");
            }

        } while (pilihMenu != 0);

        input.close();
    }

    static void tampilkanMenu(String[] daftarMenu) {
        System.out.println("Silakan pilih makanan anda :");
        System.out.println("|  Makanan                 | Harga  | Qty |");

        for (int i = 0; i < daftarMenu.length; i++) {
            System.out.println((i + 1) + ". " + daftarMenu[i]);
        }
    }

    static void pesanMenu(int pilihMenu, String[] daftarMenu, List<String> makananYangDipilih) {
        Scanner input = new Scanner(System.in);

        System.out.println("===================");
        System.out.println("Berapa pesanan anda");
        System.out.println("===================");

        System.out.println(daftarMenu[pilihMenu - 1]);
        System.out.println("Input 0 untuk kembali\n");

        int jumlahPesanan;
        do {
            System.out.print("qty => ");
            jumlahPesanan = input.nextInt();

            if (jumlahPesanan < 0) {
                System.out.println("Jumlah pesanan tidak valid. Silakan masukkan jumlah pesanan yang benar.");
            }
        } while (jumlahPesanan < 0);

        for (int i = 0; i < jumlahPesanan; i++) {
            makananYangDipilih.add(daftarMenu[pilihMenu - 1]);
        }
    }

    static void pesanDanBayar(List<String> makananYangDipilih) {
        Scanner input = new Scanner(System.in);
        int sumQty = 0; // Inisialisasi total jumlah pesanan

        System.out.println("=======================");
        System.out.println("Konfirmasi & Pembayaran");
        System.out.println("=======================\n");

        Map<String, Long> jumlahPerMakanan = makananYangDipilih.stream()
                .collect(Collectors.groupingBy(e -> e, Collectors.counting()));

        System.out.println("|  Makanan              | Harga  | Qty |");

        for (Map.Entry<String, Long> entry : jumlahPerMakanan.entrySet()) {
            String makanan = entry.getKey();
            long qty = entry.getValue();
            int idx = makanan.lastIndexOf("|");
            String namaMakanan = makanan.substring(0, idx).trim();
            String hargaStr = makanan.substring(idx + 1).trim();
            int harga = 0; // Inisialisasi dengan nilai default

            if (!hargaStr.isEmpty()) {
                // Hapus karakter non-digit dan spasi, kemudian konversi ke integer
                hargaStr = hargaStr.replaceAll("[^0-9]", "");
                harga = Integer.parseInt(hargaStr);
            }

            int totalQty = (int) qty;
            sumQty += totalQty;

            System.out.println(namaMakanan + " | " + hargaStr + " | " + totalQty);
        }

        int totalHarga = hitungTotalHarga(jumlahPerMakanan); // Menghitung total harga

        // Menampilkan struk
        System.out.println("================================");
        System.out.println("Nasi Goreng Mamang Hifni");
        System.out.println("================================");
        System.out.println("Terima kasih\n");
        System.out.println("Di bawah ini pesanan anda :");
        System.out.println("|  Makanan              | Harga  | Qty |");

        for (Map.Entry<String, Long> entry : jumlahPerMakanan.entrySet()) {
            String makanan = entry.getKey();
            long qty = entry.getValue();
            int idx = makanan.lastIndexOf("|");
            String namaMakanan = makanan.substring(0, idx).trim();
            String hargaStr = makanan.substring(idx + 1).trim();
            int harga = 0;

            if (!hargaStr.isEmpty()) {
                hargaStr = hargaStr.replaceAll("[^0-9]", "");
                harga = Integer.parseInt(hargaStr);
            }

            int totalQty = (int) qty;
            System.out.println(namaMakanan + " | " + hargaStr + " | " + totalQty);
        }

        System.out.println("-----------------------------------");
        System.out.println("|  Total                | " + totalHarga + " | " + sumQty + " |\n");

        System.out.println("1. Konfirmasi dan Bayar");
        System.out.println("2. Kembali ke menu utama");
        System.out.println("0. Keluar Aplikasi\n");

        System.out.print("=> ");
        int pilihan = input.nextInt();

        if (pilihan == 1) {
            simpanStruk(totalHarga, sumQty, jumlahPerMakanan);
        } else if (pilihan == 0) {
            input.close();
        }
    }

    static int hitungTotalHarga(Map<String, Long> jumlahPerMakanan) {
        int totalHarga = 0;

        for (Map.Entry<String, Long> entry : jumlahPerMakanan.entrySet()) {
            String makanan = entry.getKey();
            long qty = entry.getValue();
            int idx1 = makanan.lastIndexOf("|");
            int idx2 = makanan.lastIndexOf("|", idx1 - 1);
            String hargaStr = makanan.substring(idx2 + 1, idx1).trim().replaceAll("[^0-9]", "");

            if (!hargaStr.isEmpty()) {
                try {
                    int harga = Integer.parseInt(hargaStr);
                    totalHarga += (harga * qty);
                } catch (NumberFormatException e) {
                    System.err.println("Kesalahan dalam mengonversi harga: " + e.getMessage());
                }
            }
        }

        return totalHarga;
    }

    static void simpanStruk(int totalHarga, int sumQty, Map<String, Long> jumlahPerMakanan) {
        try {
            Scanner input = new Scanner(System.in);
            // mendapatkan waktu sekarang
            Date currentTime = new Date();

            // membuat format waktu menggunakan simpledateFormat
            SimpleDateFormat timeFormat = new SimpleDateFormat("yyyyMMdd_HHmmss");
            SimpleDateFormat timeFormatId = new SimpleDateFormat("EEEE, dd MMMM yyyy || HH:mm:ss", new Locale("id", "ID"));

            // melakukan formatting waktu sekarang dengan format yang diinginkan
            String formattingTime = timeFormat.format(currentTime);
            String formattingTimeId = timeFormatId.format(currentTime);

            String strukName = "struk_" + formattingTime;

            File createStruk = new File("E:\\Course\\Synrgy Batch 6\\Tugas\\challenge_1\\src\\" + strukName);
            if (createStruk.createNewFile()){
                System.out.println("Struk berhasil di cetak : " + createStruk.getName());


            } else {
                System.out.println("File already exist!!");
            }

            FileWriter writeStruk = new FileWriter("E:\\Course\\Synrgy Batch 6\\Tugas\\challenge_1\\src\\" + strukName);
            writeStruk.write("========================\n");
            writeStruk.write("Nasi Goreng Mamang Hifni\n");
            writeStruk.write("========================\n");
            writeStruk.write(formattingTimeId + "\n");
            writeStruk.write("======================================================\n");
            writeStruk.write("Terima kasih sudah memesan di Nasi Goreng Mamang Hifni\n");
            writeStruk.write("======================================================\n");
            writeStruk.write("Di bawah ini pesanan anda :\n");
            writeStruk.write("|  Makanan              | Harga  | Qty |\n");

            for (Map.Entry<String, Long> entry : jumlahPerMakanan.entrySet()) {
                String makanan = entry.getKey();
                long qty = entry.getValue();
                int idx = makanan.lastIndexOf("|");
                String namaMakanan = makanan.substring(0, idx).trim();
                String hargaStr = makanan.substring(idx + 1).trim().replaceAll("[^0-9]", "");
                int harga = 0;

                if (!hargaStr.isEmpty()) {
                    harga = Integer.parseInt(hargaStr);
                }

                int totalQty = (int) qty;
                writeStruk.write(namaMakanan + " | " + hargaStr + " | " + totalQty + "\n");
            }

            writeStruk.write("-----------------------------------\n");
            writeStruk.write("|  Total                | " + totalHarga + " | " + sumQty + " |\n\n");
            writeStruk.write("Pembayaran : Cash\n");
            writeStruk.write("===============================\n");
            writeStruk.write("Simpan struk sebagai bukti pembayaran\n");
            writeStruk.write("===============================\n");
            writeStruk.close();
            input.close();
        } catch (IOException e) {
            System.err.println("Error saat menyimpan struk: " + e.getMessage());
        }
    }
}
