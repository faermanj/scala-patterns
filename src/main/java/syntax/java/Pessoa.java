package syntax.java;

public class Pessoa {
	String nome;
	
	public Pessoa(String nome) {
		this.nome = nome;
	}
	
	public String saudacao() throws Exception {
		if(nome.length() < 10)
			return "Oi "+nome+"!";
		else
			throw new Exception("Nome muito longo");		
	}
	
	public static void main(String[] args) throws Exception{
		Pessoa p = new Pessoa("Fulano");
		System.out.println(p.saudacao());
		System.out.println(p.nome);
	}
}
