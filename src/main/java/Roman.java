public class Roman {
    public static int romanToInt(String romanNum) {
        int number = 0;
        for (int i = 0; i < romanNum.length(); i++) {
            char letter = romanNum.charAt(i);
            switch (letter) {
                case 'I':
                    number = (i != romanNum.length() - 1 && (romanNum.charAt(i + 1) == 'V' || romanNum.charAt(i + 1) == 'X'))
                            ? number - 1
                            : number + 1;
                    break;
                case 'V':
                    number += 5;
                    break;
                case 'X':
                    number = (i != romanNum.length() - 1 && (romanNum.charAt(i + 1) == 'L' || romanNum.charAt(i + 1) == 'C'))
                            ? number - 10
                            : number + 10;
                    break;
                case 'L':
                    number += 50;
                    break;
                case 'C':
                    number = (i != romanNum.length() - 1 && (romanNum.charAt(i + 1) == 'D' || romanNum.charAt(i + 1) == 'M'))
                            ? number - 100
                            : number + 100;
                    break;
                case 'D':
                    number += 500;
                    break;
                case 'M':
                    number += 1000;
                    break;
            }
        }

        return number;
    }

//    public static void main(String[] args) {
//        System.out.println(romanToInt("MCMXC")); //1990
//        System.out.println(romanToInt("MMVIII")); //2008
//        System.out.println(romanToInt("MDCLXVI")); //1666
//    }
}