import java.lang.reflect.Array;
import java.util.Arrays;

public class Hidden {
    public static boolean isHidden(String s, String t) {

        char[] char_t=t.toCharArray();
        int duplicateNum=0;
        int i=0;
        while (i<char_t.length){

            char[] char_s=s.toCharArray();
            for (int j=0;j<char_s.length;j++){
                if(char_t[i]==char_s[j]){
                    s=s.substring(j+1);
                    duplicateNum++;
                    break;
                }
            }
            i++;
        }
        if (duplicateNum==t.length()) return true;
        return false;
    }

//    public static void main(String[] args){
//        System.out.println(isHidden("welcometothehotelcalifornia","melon"));
//        System.out.println(isHidden("welcometothehotelcalifornia","space"));
//        System.out.println(isHidden("TQ89MnQU3IC7t6","MUIC"));
//        System.out.println(isHidden("VhHTdipc07","htc"));
//        System.out.println(isHidden("VhHTdipc07","hTc"));
//    }
}