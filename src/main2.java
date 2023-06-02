import java.io.BufferedOutputStream;
import java.io.IOException;
import java.util.Scanner;
import java.io.BufferedInputStream;
import java.net.Socket;

public class main2 {
    public static void main(String[] args) {
        try {
            Scanner stringScanner;
            stringScanner = new Scanner(System.in);
            System.out.println("Selamat datang di Web Browser, silakan pilih menu yang diinginkan:\n1.Membuka Webpage\n2.Menampilkan Link dalam Website\n3.Download File\n4.Membuka Webpage dengan Auth.\n5.Download beberapa file.");
            int userChoice = Integer.parseInt(stringScanner.nextLine());
            switch (userChoice) {
                case 1 -> {
                    System.out.println("Silakan Masukkan url!");
                    String url = stringScanner.nextLine();
                    BufferedInputStream bis = null;
                    BufferedOutputStream bos = null;
                    Socket sc = null;
                    util.httpRequestFirst(sc, bis, bos, url);
                }
                case 2 -> {
                    System.out.println("Silakan Masukkan url!");
                    String listUrl = stringScanner.nextLine();
                    util.ClickableLink(listUrl);
                }
                case 3 -> {
                    System.out.println("Silakan Masukkan url!");
                    String fileUrl = stringScanner.nextLine();
                    System.out.println("Silakan Masukkan Nama File yang Diinginkan.");
                    String fileName = stringScanner.nextLine();
                    System.out.println("Silakan Masukkan Tipe File yang Diinginkan.");
                    String fileType = stringScanner.nextLine();
                    util.downloadMethod(fileUrl, fileName, fileType);
                }
                case 4 -> {
                    System.out.println("Silakan Masukkan url!");
                    String authUrl = stringScanner.nextLine();
                    System.out.println("Silakan Masukkan Data");
                    String username = stringScanner.nextLine();
                    System.out.println("Silakan Masukkan Password!");
                    String password = stringScanner.nextLine();
                    BufferedInputStream authBis = null;
                    BufferedOutputStream authBos = null;
                    Socket authSc = null;
                    util.httpRequestWithToken(authSc, authBis, authBos, authUrl, username, password);
                }
                case 5 -> {
                    System.out.println("Silakan Tentukan Jumlah File yang Ingin Diunggah Maks. 5");
                    int totalFilesDownload = Integer.parseInt(stringScanner.nextLine());
                    if (totalFilesDownload < 1 || totalFilesDownload > 5) {
                        break;
                    }
                    String[] downloadUrls = new String[totalFilesDownload];
                    String[] fileNames = new String[totalFilesDownload];
                    String[] fileTypes = new String[totalFilesDownload];
                    downloadThread[] threads = new downloadThread[totalFilesDownload];
                    int i = 0;
                    while (i < totalFilesDownload) {
                        System.out.println("Silakan Masukkan url. No: " + i + 1);
                        downloadUrls[i] = stringScanner.nextLine();

                        System.out.println("Silakan Masukkan Nama File!");
                        fileNames[i] = stringScanner.nextLine();

                        System.out.println("Silakan Masukkan Jenis File!");
                        fileTypes[i] = stringScanner.nextLine();

                        threads[i] = new downloadThread(downloadUrls[0], fileNames[i], fileTypes[i]);
                        threads[i].start();
                        i++;
                    }
                    for (downloadThread thread : threads) {
                        thread.join();
                    }
                    System.out.println("Download Selesai!");
                }
            }


            stringScanner.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (NumberFormatException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
