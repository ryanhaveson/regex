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
        decimalDemo();
        currencyDemo();
        ipAddressDemo();
        datesDemo();
        HTMLTagDemo();
    }


    static void HTMLTagDemo() {
        String html[] = {
                // Yes
                "<basic></basic>",
                "<basic ></basic>",
                "<basic >< /basic>",
                "< basic >< /basic>",
                "<basic >< /basic >",
                "<string>foo</string>",
                "<href a =\"fooish\">mylink</href>",
                "</onlyOneBasic>",
                "< /onlyOneBasic>",
                "< /onlyOneBasic >",
                "</onlyOneBasic >",
                "</onlyOne prop=\"fooish\">",

                // No
                "<i invalidProps></i>",
                "<invalid>",
                "<alsoInvalid></nuts>"
        };

        // "<(\\w+\\b).*>\\w*</\\1>|" +
        String htmlRegEx =
                "^" +
                    // formatted like <tag [optional props]>..</tag>

                        // Start tag
                        "<\\s*(\\w+)\\s*" +

                            // Optional properties
                            "(\\w+\\s*=\\s*\".*\")*" +

                        "\\s*>" +

                            // Value
                        ".*"    +
                        // End tag
                        "<\\s*/\\1\\s*>" +

                    // formatted like </tag [optional props]>
                    "|<\\s*/\\w*\\s*" +

                        // Optional properties
                        "(\\w+\\s*=\\s*\".*\")*" +

                    "\\s*>" +
                "$";
        tester(html, htmlRegEx);

    }

    static void datesDemo() {

        String dates[] = {
                "2000-11-15",
                "2000/11/15",
                "2000\\11\\15",
                "2000-6-9",
                "2000-06-09",
                "2000-4-30",
                "2000-3-31",
                "2000/6/9",
                "2000-0-31",          // No
                "2000-14-16" ,        // No
                "2000-12-32",         // No
                "2000-12-55",         // No
                "2000-09-32",         // No
        };

        String regex = "^" +
                // Year
                    "\\d{0,4}" +

                // Seperator
                    "[-/\\\\]" +

                // Month
                    "(1[0-2]|0?[1-9])" +

                // Seperator
                "[-/\\\\]" +

                // Day
                    "(3[01]|[0-2]?\\d)" +
                "$";
        tester(dates,regex);


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

    static void currencyDemo() {

        String[] currency = {
                "$5.1",
                "$314.123434",
                "$0.123",
                "$.389",
                "$23",
                "$23.",      // No
                "$.2.3",     // No
                "$"          // No
        };

        tester(currency, "^\\$((\\d*\\.\\d+)|(\\d+))$");
    }


    static void ipAddressDemo() {

        String[] addrs = {
                "1.2.3.4",
                "0.0.0.0",
                "67.52.159.38",
                "067.052.159.038",
                "1.3",              // No
                "300.23.3.4",       // No
                "244.888.12.0"      // No
        };
        tester(addrs, "^" +
                "(" +
                    "(" +
                        "(25[0-5]) | "    +    // 250 - 255
                        "(2[0-4][0-9]) |" +    // 200 - 249
                        "([0-1]?\\d{0,2})"  +  // 0-199
                    ")" +
                "\\.){3}" +
                    "("  +
                        "(25[0-5]) | "    +    // 250 - 255
                        "(2[0-4][0-9]) |" +    // 200 - 249
                        "([0-1]?\\d{0,2})"  +  // 0-199
                    ")"  +
                "$");

    }

    static void decimalDemo() {
        String[] decimals  = {
                "5.1",
                "314.123434",
                "0.123",
                ".389",
                "23",
                "23.",      // No
                ".2.3",     // No
                ""          // No

        };

                // Two cases:  first case with decimal, second case no decimal
        tester(decimals,"^(\\d*\\.\\d+)|(\\d+)$");
    }

    static void tester(String[] testCases, String regex) {

        System.out.println("\n\nTesting regex: " + regex);
        Pattern p = Pattern.compile(regex);

        for(String s : testCases) {
            Matcher m = p.matcher(s);
            System.out.println("testString: [" + s + "] " + (m.matches() ? "matches" : "does not match regex: [" + regex + "]"));
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

