public class StringBufferDemo {
    public static void main(String[] args) {
        String palindrome = "Dot saw I was Tod";
         
        StringBuffer sb = new StringBuffer(palindrome);
        
        sb.reverse();  // reverse it
        
        System.out.println(sb);
    }
}
