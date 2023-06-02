/**
 * tester
 */
public class tester {

    public static void main(String[] args) {
        testParseCode();
        testHostParser();
    }

    public static void testParseCode(){
        String testHeaderFirst = "HTTP/1.1 200 OK\r\nDate: Tue, 21 Mar 2023 13:28:33 GMT\r\nExpires: -1\r\n\r\n";
        int statusCodeFirst = util.getStatusCode(testHeaderFirst);
        System.out.println(statusCodeFirst);

        // String testHeaderSecond = new String();
        // statusCodeSecond = util.getStatusCode(testHeaderSecond);
        // System.out.println(statusCodeSecond);

    }

    public static void testHostParser(){
        String test = "https://www.google.com/";
        String[] result = util.getHostAndUrl(test);
        for (String string : result) {
            System.out.println(string);
        }
    }   
}