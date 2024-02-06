import java.math.BigInteger;

public class Fib {
//    public static void main(String[] args) {
//        System.out.println(firstNDigit(1)==1);
//        System.out.println(firstNDigit(2)==7);
//        System.out.println(firstNDigit(3)==12);
////        System.out.println(firstNDigit(100));
//    }
    public static int firstNDigit(int n){
        if (n==1) return 1;
        BigInteger n1 = BigInteger.valueOf(1);
        BigInteger n2 = BigInteger.valueOf(1);
        BigInteger n3;
        int i;
        for (i = 2; n2.toString().length() != n; i++) {
            n3 = n1.add(n2);
            n1 = n2;
            n2 = n3;
        }
        return i;
    }
}
