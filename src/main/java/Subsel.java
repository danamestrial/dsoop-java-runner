import java.util.Arrays;
import java.util.*;

public class Subsel {

    public static int[] takeEvery(int[] a, int stride, int beginWith){

        int mem_array=0;
        for(int i=0; i<a.length;i++){
            if (((beginWith+(i*stride)) < a.length) && (beginWith+(i*stride))>=0){
                mem_array++;
            }
            else break;
        }
        int[] ans=new int[mem_array];
        int j=0;
        while (j<ans.length){
            if ((beginWith+(j*stride))<a.length){
                ans[j]=a[beginWith+(j*stride)];
                j++;
            }
            else break;
        }
        return ans;
    }

    public static int[] takeEvery(int[] a, int stride) {
        int b=0;
        return takeEvery(a,stride,b);
    }

//    public static void main(String[] args){
//        int[] dd={2,7,1,8,4,5};
//        takeEvery(dd,3,2);
//        System.out.println(Arrays.toString(takeEvery(dd,3,2)));
//    }

}