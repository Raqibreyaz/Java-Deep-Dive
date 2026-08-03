public class Demo2 {
    public static void main(String[] args) {
        String s1 = new String("\n   \t");
        System.out.println(s1.length()); // 5
        System.out.println(s1.isEmpty()); // false
        System.out.println(s1.isBlank()); // true

        String s2 = "Raquib";

        System.out.println(s2.charAt(2));

        char[] arr = s2.toCharArray();
        System.out.println(arr);

        System.out.println(s2.equals("raquib"));
        System.out.println(s2.equalsIgnoreCase("raquib"));

        System.out.println("raquib".compareTo("Raquib"));

        System.out.println(s2.contains("aquib"));

        String s3 = " aquib  ";
        System.out.println(s3.trim());
        System.out.println(s3.strip()); // unicode friendly

        System.out.println("hi".repeat(3));

        String s4 = "Raquib, Reyaz, Imran";

        String[] tokens = s4.split(",");
        String name1 = tokens[0].trim();
        String name2 = tokens[1].trim();
        String name3 = tokens[2].trim();
        System.out.printf("%s %s %s\n", name1, name2, name3);

        System.out.println(String.join("-", "r", "a", "q"));

        String s5 = String.valueOf(10);
        System.out.println(s5);

        for(byte b: s5.getBytes())
            System.out.println(b);

        String s6 = s1.intern(); //copies the heap object to string pool
        System.out.println(s6 == s1);
    }
}
