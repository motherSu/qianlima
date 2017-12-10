package qianlima.test2;

public class Cargo {

	private String code ;
	
	private int weight;

	
	public Cargo(String code, int weight) {
		super();
		this.code = code;
		this.weight = weight;
	}

	public Cargo() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public int getWeight() {
		return weight;
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}
	
	
}
