package qianlima.test2;

import java.util.ArrayList;
import java.util.List;

/**
 * 顺丰速运，全货机专机运输，提供高效的便捷服务，更快更安全!

首先，是快捷的时效服务。自有专机和400余条航线的强大航空资源以及庞大的地面运输网络，保障客户的快递在各环节最快发运。　

其次，是安全的运输服务。顺丰速运自营的运输网络，给消费者提供标准、高质、安全的服务。

由此，顺丰速运在消费者的心中留下了完美的形象，从而提高了企业的业绩，也奠定了其在整个快递领域中的基础。

顺丰快递每天能收到成千上万的物流单，每个物流单的重量不一。 现在顺丰快递的货车司机隔壁老王开着顺丰的标配货车（限载5吨，含5吨，不考虑限高）,想要一次性拿走尽可能重的货物，这些货有红木沙发，有钢材等等。

以下是货物清单：

货物编号   货物重量(单位:kg)
1         509
2         838
3         924
4         650
5         604
6         793
7         564
8         651
9         697
10        649
11        747
12        787
13        701
14        605
15        644
请在答题框下输入你想装运的货物的编号，用-分隔。

比如1-2-3代表同时装入编号为1,2,3的货物，此时货物总重为509（1号货物）+838（2号货物）+924（3号货物）=2271kg,远小于限载额——5吨，隔壁老王会被吐槽的。

比如1-5-8-9-11-12-14代表同时装入编号为1,5,8,9,11,12,14的货物，此时货物总重为509（1号货物）+604（5号货物）+651（8号货物）+697（9号货物）747（11号货物）+787（12号货物）+605（14号货物）=4600kg，这时与限载额5吨就比较接近了，隔壁老王会很高兴……
 * @author suteng
 *
 */
public class Test2 {
	
	public static void main(String[] args) {
		List<Cargo> cargos = new ArrayList<Cargo>();
		cargos.add(new Cargo("1",509));
		cargos.add(new Cargo("2",838));
		cargos.add(new Cargo("3",924));
		cargos.add(new Cargo("4",650));
		cargos.add(new Cargo("5",604));
		cargos.add(new Cargo("6",793));
		cargos.add(new Cargo("7",564));
		cargos.add(new Cargo("8",651));
		cargos.add(new Cargo("9",697));
		cargos.add(new Cargo("10",649));
		cargos.add(new Cargo("11",747));
		cargos.add(new Cargo("12",787));
		cargos.add(new Cargo("13",701));
		cargos.add(new Cargo("14",605));
		cargos.add(new Cargo("15",644));
		getBestAnswer(cargos);
	}
	
	private static String getBestAnswer(List<Cargo> cargos){
		List<Answer> answers = new ArrayList<Answer>();
		List<Cargo> cg = new ArrayList<Cargo>();
		getAnswers(cargos, cg, answers);
		for (Answer answer : answers) {
			System.out.println("answer:"+answer.getCode()+","+answer.getTotalWeight());
		}
		
		return "";
	}
	
	private static void getAnswers(List<Cargo> cargos, List<Cargo> cg, List<Answer> answers){
		for (int i = 0;i < cargos.size(); i++) {
			//List<Cargo> cg = new ArrayList<Cargo>();
			if(cg.size() == i){
				for (Cargo cargo : cargos.subList(1, cargos.size())) {
					cg.add(i,cargo);
					System.out.println(cg.size());
					answers.add(new Answer(cg));
				}
				continue;
			}else{
				
				getAnswers(cargos.subList(1, cargos.size()),cg, answers);
			}
		}
	}
	
}
