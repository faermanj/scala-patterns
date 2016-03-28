package syntax.java;

class Nomeado {
	String nome;
	public Nomeado comNome(String nome){
		this.nome = nome;
		return this;
	}
}

class Bicho extends Nomeado {
	public void oi() {
		System.out.println(nome);
	}
	
	public static void main(String[] args) {
		new Bicho().comNome("Rex");//.oi();
	}
}
