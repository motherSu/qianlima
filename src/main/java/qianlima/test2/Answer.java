package qianlima.test2;

import java.util.List;

public class Answer {

	private String code;
	
	private int totalWeight;
	
	private List<Cargo> cargos;

	public Answer(List<Cargo> cargos) {
		super();
		this.cargos = cargos;
		StringBuffer sb = new StringBuffer("");
		int tw = 0;
		for (Cargo cargo : cargos) {
			sb.append(cargo.getCode()+"-");
			tw += cargo.getWeight(); 
		}
		this.code = sb.toString();
		this.totalWeight = tw;
	}

	public Answer() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public int getTotalWeight() {
		return totalWeight;
	}

	public void setTotalWeight(int totalWeight) {
		this.totalWeight = totalWeight;
	}

	public List<Cargo> getCargos() {
		return cargos;
	}

	public void setCargos(List<Cargo> cargos) {
		this.cargos = cargos;
	}
}
