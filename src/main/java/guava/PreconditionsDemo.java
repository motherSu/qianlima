/**
 * @Title555: 
*/

package guava;

import com.google.common.base.Preconditions;

/**
 * @Title: 
 * @Description:
 * @author: 苏腾
 * @date: 2018年3月8日 下午2:55:01
*/
public class PreconditionsDemo {
   public static void main(String[] args) {
       try {
           int i = getValue(4);
           System.out.println(i);
       } catch (IndexOutOfBoundsException e){
           System.out.println(e.getMessage());
       }

       try {
           sum(4,null);
       } catch (NullPointerException e){
           System.out.println(e.getMessage());
       }

       try {
           double d = sqrt(16);
           System.out.println(d);
       } catch (IllegalArgumentException e){
           System.out.println(e.getMessage());
       }

   }

   private static double sqrt(double input){
       Preconditions.checkArgument(input>0.0,
               "Illegal Argument passed: Negative value %s.",input);
       return Math.sqrt(input);
   }

   private static int sum(Integer a,Integer b){
       a=Preconditions.checkNotNull(a,
               "Illegal Argument passed: First parameter is Null.");
       b=Preconditions.checkNotNull(b,
               "Illegal Argument passed: First parameter is Null.");
       return a+b;
   }

   private static int getValue(int input){
       int[] data={1,2,3,4,5};
       int index=Preconditions.checkElementIndex(input,data.length,
               "Illegal Argument passed: Invalid index.");
       return data[index];
   }
}