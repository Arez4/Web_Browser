import java.io.BufferedInputStream;
import java.net.Socket;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Base64;
import java.io.FileOutputStream;
import java.io.BufferedReader;
import java.io.PrintWriter;
import java.io.InputStreamReader;

public class util {

    private static int bufferSize = 8192;

    public static boolean checkUrl() {

        return true;
    }

    public static void httpRequestFirst(Socket sc, BufferedInputStream bis, BufferedOutputStream bos, String url)
            throws Exception {
        String[] hostAndUrl = getHostAndUrl(url);
        sc = new Socket("" + hostAndUrl[0], 80);
        bis = new BufferedInputStream(sc.getInputStream());
        bos = new BufferedOutputStream(sc.getOutputStream());
        boolean isRedirect = false;
        String redirectAddress = null;

        // exchange msg here
        String messageHeader = null;
        if (hostAndUrl.length > 1) {
            messageHeader = "GET /" + hostAndUrl[1] + " HTTP/1.1\r\n";
        } else {
            messageHeader = "GET / HTTP/1.1\r\n";
        }
        String host = "Host: " + hostAndUrl[0];
        // System.out.print(messageHeader);
        // System.out.print(host + "\n");
        bos.write(messageHeader.getBytes(), 0, messageHeader.length());
        bos.write(host.getBytes(), 0, host.length());
        bos.write("\r\n\r\n".getBytes(), 0, "\r\n\r\n".length());
        bos.flush();
        int counter = 0;
        String tempResponse = null;
        int statusCode = -1;
        do {
            tempResponse = new String(bis.readNBytes(bufferSize));
            if (tempResponse.isEmpty()) {
                break;
            }
            if (counter == 0) {
                statusCode = getStatusCode(tempResponse);
                statusCodeHandler(statusCode);
                counter++;
            }
            String[] responseArr = tempResponse.split("\r\n");
            for (String string : responseArr) {
                if (string.contains("url=")) {
                    String[] stringSplit = string.split(";");
                    for (String stringTwo : stringSplit) {
                        if (stringTwo.contains("url=")) {
                            redirectAddress = stringTwo.replaceAll("url=", "");
                            isRedirect = true;
                        }
                    }
                }
                if(string.contains("<")){
                    System.out.println(string);
                }
            }
            if (statusCode >= 300 && statusCode < 400) {
                redirectAddress = getRedirectAddress(responseArr);
                isRedirect = true;
                // System.out.print(redirectAddress);
            }

        } while (tempResponse.length() < bufferSize);
        if (isRedirect) {
            System.out.println("Redirecting to " + redirectAddress);
            httpRequestFirst(sc, bis, bos, redirectAddress);
        }
        // String[] response = ;
        // String tempResponse = null;
        // do{
        // tempResponse = new String(bis.readNBytes(4096));
        // // response[counter] = tempResponse;

        // System.out.println(tempResponse);
        // }
        // while(!tempResponse.isEmpty() || tempResponse.length() < bufferSize);
        // String responses = new String(bis.readNBytes(bufferSize));

        sc.close();

        // System.out.println(responses);

    }

    public static void httpRequestWithToken(Socket sc, BufferedInputStream bis, BufferedOutputStream bos, String url, String username, String password) throws Exception {
        String[] hostAndUrl = getHostAndUrl(url);
        sc = new Socket("" + hostAndUrl[0], 80);
        bis = new BufferedInputStream(sc.getInputStream());
        bos = new BufferedOutputStream(sc.getOutputStream());
        boolean isRedirect = false;
        String redirectAddress = null;

        // exchange msg here
        String messageHeader = null;
        if (hostAndUrl.length > 1) {
            messageHeader = "GET /" + hostAndUrl[1] + " HTTP/1.1\r\n";
        } else {
            messageHeader = "GET / HTTP/1.1\r\n";
        }
        String host = "Host: " + hostAndUrl[0];
        String usernameAndPassword = username+ ":" + password;
        System.out.print(usernameAndPassword);
        String base64EncodedToken = Base64.getEncoder().encodeToString(usernameAndPassword.getBytes());
        String authHeader = "Authorization: Basic " + base64EncodedToken + "\r\n";
        System.out.print(messageHeader);
        System.out.print(host + "\n");
        System.out.print(authHeader + "\n");
        String request = messageHeader + host + "\r\n" + authHeader + "\r\n\r\n";
        System.out.print(request);
        bos.write(request.getBytes(), 0, request.length());
        bos.flush();
        int counter = 0;
        String tempResponse = null;
        int statusCode = -1;
        byte[] buffer = new byte[bufferSize];
        do {
            tempResponse = new String(bis.readNBytes(bufferSize));
            if (tempResponse.isEmpty()) {
                break;
            }
            if (counter == 0) {
                statusCode = getStatusCode(tempResponse);
                // statusCodeHandler(statusCode);
                counter++;
            }
            String[] responseArr = tempResponse.split("\r\n");
            for (String string : responseArr) {
                System.out.println(string);
                if (string.contains("url=")) {
                    String[] stringSplit = string.split(";");
                    for (String stringTwo : stringSplit) {
                        if (stringTwo.contains("url=")) {
                            redirectAddress = stringTwo.replaceAll("url=", "");
                            isRedirect = true;
                        }
                    }
                }
            }
            if (statusCode >= 300 && statusCode < 400) {
                redirectAddress = getRedirectAddress(responseArr);
                isRedirect = true;
                // System.out.print(redirectAddress);
            }

        } while (bis.read(buffer) != -1 );
        if (isRedirect) {
            System.out.println("Redirecting to " + redirectAddress);
            httpRequestFirst(sc, bis, bos, redirectAddress);
            // String[] response = ;
            // String tempResponse = null;
            // do{
            // tempResponse = new String(bis.readNBytes(4096));
            // // response[counter] = tempResponse;

            // System.out.println(tempResponse);
            // }
            // while(!tempResponse.isEmpty() || tempResponse.length() < bufferSize);
            // String responses = new String(bis.readNBytes(bufferSize));
        }
        System.out.println(statusCode);
        sc.close();

        // System.out.println(responses);

    }

    public static int getStatusCode(String response) throws NumberFormatException {
        String[] responses = response.split("\r\n");

        String[] firstLine = responses[0].split(" ");

        int statusCode = Integer.parseInt(firstLine[1]);
        return statusCode;
    }

    public static void statusCodeHandler(int statusCode) throws Exception {
        if (statusCode >= 100 && statusCode < 200) {
            throw new Exception("Error: Status code " + statusCode);
        }
        if (statusCode >= 200 && statusCode < 300) {
            return;
        }

        if (statusCode >= 300 && statusCode < 400) {
                // throw new Exception("Error: Status code " + statusCode);
            return;
        }
        if (statusCode >= 400 && statusCode < 600) {
            if (statusCode == 401){
                return;
            }
            throw new Exception("Error: Status code " + statusCode);
        }
    }

    public static void downloadMethod(String FILE_URL, String FILE_NAME, String FILE_TYPE) throws IOException {
        URL url = new URL(FILE_URL);

        BufferedInputStream bis = new BufferedInputStream(url.openStream());
        FileOutputStream fis = new FileOutputStream("./" + FILE_NAME + "." + FILE_TYPE);

        byte[] buffer = new byte[bufferSize];
        int count = 0;

        while ((count = bis.read(buffer, 0, bufferSize)) != -1) {
            fis.write(buffer, 0, count);
        }

        fis.close();
        bis.close();

    }

    public static String getRedirectAddress(String[] stringArr) {
        for (String string : stringArr) {
            if (string.contains("Location")) {
                String[] redirectHeader = string.split(":", 2);
                // for(String string2: redirectHeader){
                // System.out.println(string2);
                // }
                return redirectHeader[1].replaceAll(" ", "");
            }
        }
        return null;
    }

    public static void ClickableLink(String url) throws IOException {
        String newUrl;
        if (url.contains("https://")) {
            String[] arrOfStr = url.split("https://", 2);
            newUrl = arrOfStr[1];
        } else {
            newUrl = url;
        }

        String[] hostAndUrl = getHostAndUrl(newUrl);
        Socket socket = new Socket("" + hostAndUrl[0], 80);

        PrintWriter wtr = new PrintWriter(socket.getOutputStream());
        if (hostAndUrl.length > 1) {
            System.out.println(hostAndUrl[0]);
            System.out.println(hostAndUrl[1]);
            wtr.println("GET /" + hostAndUrl[1] + " HTTP/1.1");
            wtr.println("Host: " + hostAndUrl[0]);
        } else {
            wtr.println("GET / HTTP/1.1");
            wtr.println("Host: " + hostAndUrl[0]);
        }
        wtr.println("");
        wtr.flush();

        BufferedReader bufRead = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        String outStr;

        boolean foundLinks = false;
        int count = 1;

        while ((outStr = bufRead.readLine()) != null) {
            if (outStr.contains("<a href=")) {

                int startIndex = outStr.indexOf("href=\"") + 6;
                int endIndex = outStr.indexOf("\"", startIndex);
                if (startIndex == -1 || endIndex == -1) {
                    break;
                }
                String link = outStr.substring(startIndex, endIndex);

                System.out.println(count++ + "." + link);

                foundLinks = true;
            }
        }

        if (!foundLinks) {
            System.out.println("No hyperlinks found in response.");
        }

        bufRead.close();
        wtr.close();
        socket.close();
    }

    public static String[] getHostAndUrl(String input) {
        String inputWithoutProtocol = input.replaceAll("https://", "").replaceAll("http://", "");
        String[] hostAndUrl = inputWithoutProtocol.split("/", 2);
        // for (String string : hostAndUrl) {
        // // System.out.println(string);
        // }

        return hostAndUrl;
    }
}
