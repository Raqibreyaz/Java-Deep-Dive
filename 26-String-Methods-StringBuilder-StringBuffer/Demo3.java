public class Demo3 {
    public static void main(String[] args) {
        StringBuilder sb = new StringBuilder();
        sb.append("hello");
        System.out.println(sb.capacity());
        
        sb.append(" world");
        System.out.println(sb.capacity());
        
        sb.append(" fdiojaiodjoasx");
        System.out.println(sb.capacity());

        System.out.println(sb);
    }
}
