import java.util.*;
import java.util.regex.*;
public class pattern
{
    public static void main(String[] args) throws Exception
    {

        List<String> linput = new ArrayList<>();
        String test1 = "4096 MB";
        String test2 = "4 GB";
        String test3 = " 4GB";
        String test4 = " 4096  MB ";
        linput.add(test1);
        linput.add(test2);
        linput.add(test3);
        linput.add(test4);
        linput.add(">= 4096MB");
        linput.add(" = 4 GB ");

        pattern p = new pattern();
        for(String test: linput) {
            p.parse(test);
        }
    }


    public void parse(String input){

        String tmp = input.replace(" ","");
        System.out.println("tmp="+tmp);

        String pattern = "(\\D*)(\\d+)(\\D*)";
        Pattern r = Pattern.compile(pattern);

        Matcher m = r.matcher(tmp);

        if(m.find()){
            System.out.println("operator = " + m.group(1));
            System.out.println("value = " + m.group(2));
            System.out.println("unit = " + m.group(3));
        }


    }
}
