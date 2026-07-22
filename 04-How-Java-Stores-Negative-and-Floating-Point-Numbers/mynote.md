How to check
```java
        float f = 31.845f;
        System.out.printf("%.20f%n", f);
        //output -> 31.84499931335449200000

        float f2 = 0.7f;
        System.out.printf("%.20f%n", f2);
        //output -> 0.69999998807907100000
        
        System.out.println(f2);
        //output -> 0.7
```

why not only take 1s complement? due to one edge case of '-0'