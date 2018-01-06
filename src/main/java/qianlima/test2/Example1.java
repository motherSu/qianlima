package qianlima.test2;

    public class Example1 {
    
        static int good[] = { 509, 838, 924, 650, 604, 793, 564, 651, 697, 649, 747, 787, 701, 605, 644 };
        static int max = 5000;
        // this is a hint
        static int[][] mark = new int[max][good.length];
    
        public static void main(String[] args) {
            int result = make(good.length - 1, max);
            System.out.println(result);
        }
    
        /**
         * 动态规划 - 两种方案，选择装的多的
         * 
         * @param i 第几件货物
         * @param remain 剩余空间
         * @return 最多能装下多少货物
         */
        private static int make(int i, int remain) {
            if (i == 0) {
                // 如果只剩下一件货物，同样是有2种选择
                // 如果不装，那么结果就是0。
                // 如果装，并且装的下，结果就是最后一件货物本身的重量，装不下就是0
                // 那么其实就可以忽略不装的情况，因为最大值可以在只选择装的情况下判断出来。
                return remain > good[i] ? good[i] : 0;
            }
    
            // 对于一件货物只有2种选择，装，或者不装
            // 方案A(装)：如果剩下的空间能够装下本件货物，则计算装下本件货物后剩下的空间能装多少货
            int planA = 0;// 如果装不下，PlanA就是0
            if (remain > good[i])
                planA = make(i - 1, remain - good[i]) + good[i];
    
            // 方案B(不装)：如果不装本件货物（因为不装，所以不需要考虑能不能装下），则计算不装本件货物时剩下的空间最多能装多少货物
            int planB = make(i - 1, remain);
    
            // 结果，方案A和方案B哪个装的多就要哪个
            return planA > planB ? planA : planB;
        }
    }