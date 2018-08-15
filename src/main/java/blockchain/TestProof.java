/**
 * @Title555: 
*/

package blockchain;

/**
 * @Title: 
 * @Description:
 * 理解工作量证明
新的区块依赖工作量证明算法（PoW）来构造。PoW的目标是找出一个符合特定条件的数字，这个数字很难计算出来，但容易验证。这就是工作量证明的核心思想。

为了方便理解，举个例子：

假设一个整数 x 乘以另一个整数 y 的积的 Hash 值必须以 0 结尾，即 hash(x * y) = ac23dc…0。设变量 x = 5，求 y 的值？
 * @author: 苏腾
 * @date: 2018年3月30日 下午2:44:32
*/
public class TestProof {
	public static void main(String[] args) {

        int x = 5;
        int y = 0;

        while (!new Encrypt().getSHA256((x * y) + "").endsWith("0")) {
            y++;
        }

        System.out.println("y=" + y);
    }
}
