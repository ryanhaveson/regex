import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by rhaveson on 7/17/2017.
 */
public class regex {

    public static void main(final String args[]) {

        maskingDemo();
        summonerDemo();
        urlDemo();

    }

    static void maskingDemo() {
        String input =
                "User clientId=23421. Some more text clientId=33432. This clientNum=100";

        Pattern pat = Pattern.compile("(clientId=)(\\d+)");
        Matcher mat = pat.matcher(input);

        StringBuffer result = new StringBuffer();
        while (mat.find()) {
            System.out.println("Masking: " + mat.group(2));
            mat.appendReplacement(result, mat.group(1) + "***masked***");
        }
        mat.appendTail(result);
        System.out.println(result);

    }


    static void summonerDemo() {

        System.out.println("This is working");
        String[] summoners = {"hatmandew", "percy", "123ABC", "&^^foo", "0xdee.adbeef", "d_e_a_d"};
        Pattern p = Pattern.compile("^[0-9\\p{L} _\\.]+$");

        for(String summoner : summoners) {

            Matcher m = p.matcher(summoner);
            System.out.println("Summoner name: [" + summoner + "] is " + (m.matches() ? "" : "not ") + "valid");

        }
    }


    static void urlDemo() {

        String[] urls = {
                "http://www.nowhere.com",
                "http://nowhere.com",
                "http://blog.nowhere.com",
                "https://www.nowhere.com",
                "http://www.nowhere.com/product_page.html",
                "http://www.nowhere.com/images/image.gif",
                "http://www.nowhere.com/product/",
                "http://www.nowhere.com/product/3456",
                "http://www.nowhere.com/product_page.php?product=28",
                "http://www.nowhere.com?product=28&color=blue",
                "http://www.nowhere.com#details",
                "http://255.255.255.255"};

        Pattern urlPattern = Pattern.compile("^(http|https)://" + // Protocol
                "([\\w+.]+)" +  // hostname
                "([/?#]?.*)$");

        for(String url : urls) {
            System.out.println("In the loop");
            Matcher m = urlPattern.matcher(url);

            if(m.find()) {
                System.out.println("there are matches");
                System.out.println("Protocol is: " + m.group(1));
                System.out.println("Hostname is: " + m.group(2));
                System.out.println("Page is: " + m.group(3));
            } else {
                System.out.println("no matches");
            }

        }
    }

}

