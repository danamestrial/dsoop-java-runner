import java.util.Arrays;

public class Happy {
    public static long sumOfDigitsSquared(long n){
        if (n < 10) return (int) Math.pow(n, 2);
        return (long)((Math.pow((n % 10), 2)) + sumOfDigitsSquared(n/10));
        }
//    public static void main(String[] args){
//        System.out.print(sumOfDigitsSquared(199));
//        System.out.print(isHappy( 111));
//        System.out.print(Arrays.toString(firstK(11)));
//    }

    public static boolean isHappy(long n){
        while(true){
            switch (Long.toString(sumOfDigitsSquared(n))){
                case "1": return true;
                case "4": return false;
                default: n = sumOfDigitsSquared(n);
            }
        }
    }
    public static long[] firstK(int k){
        long[] l = new long[k];
        int i = 0;
        for (int n = 1; l[k-1]==0; n++){
            if (isHappy(n)){
                l[i] = n;
                i++;
            }
        }
        return l;
    }
}