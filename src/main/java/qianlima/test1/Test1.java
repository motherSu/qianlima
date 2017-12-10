package qianlima.test1;

/**
 * 作为一位coder，首先需要掌握一门编程语言以及数学常识。我们希望你现在已经掌握了一门编程语言的基本用法。
 * 第2333个能被2或者被3整除的正整数是多少?
 * 被2或者被3整除的正整数依次是：2,3,4,6,8,9,10,12,14,15,16,18…。
 * @author suteng
 *
 */
public class Test1 {

	public static void main(String[] args) {
		System.out.println(getNum(2333));
	}
	
	private static int getNum(int i){
		int a = 1;
		int count = 0;
		while(true){
			if(zhengchu(a,2) || zhengchu(a,3)){
				count++;
			}
			if(count == i){
				return a;
			}
			a++;
		}
	}
	
	private static boolean zhengchu(int x,int y){
		return x/y > 0 && x%y == 0;
	}
}
