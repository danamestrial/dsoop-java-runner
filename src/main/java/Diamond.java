public class Diamond {
//    public static void main(String[] args) {
//        printDiamond(4);
//    }
    public static void printDiamond(int k){
        int size = k*2;
        for (int i = 1; i <= size; i += 2) {
            for (int s = size; s >= i; s -= 2) {
                System.out.print("#");
            }
            for (int t = 1;  t<= i; t++) {
                System.out.print("*");
            }
            for (int s = size; s >= i; s -= 2) {
                System.out.print("#");
            }
            System.out.println();
        }

        for (int i = 1; i < size-1; i += 2) {
            for (int s = 1; s <= i+2; s += 2) {
                System.out.print("#");
            }
            for (int t = size; t >= i+3; t--) {
                System.out.print("*");
            }
            for (int s = 1; s <= i+2; s += 2) {
                System.out.print("#");
            }
            System.out.println();
        }
    }
}
