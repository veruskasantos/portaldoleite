package models;

import java.util.List;

public class OrdenaPorVoto implements Ordenavel{

	@Override
	public List<Dica> ordenaDicas(List<Dica> dicas) {
		List<Dica> dicasOrdenada = dicas;
		boolean houveTroca = true;
		Dica temp;
		while(houveTroca){
			houveTroca = false;
			for(int i = 0; i < dicasOrdenada.size() -1; i++){
				if(dicasOrdenada.get(i).getConcordancias() < (dicasOrdenada.get(i+1).getConcordancias())){
					temp = dicasOrdenada.get(i);
					dicasOrdenada.set(i,dicasOrdenada.get(i+1) );
					dicasOrdenada.set(i+1, temp);
					houveTroca = true;
				}
			}
		}
		return dicasOrdenada;
	}

	@Override
	public String getNomeTipo() {
		return "Por Voto";
	}
	
	@Override
	public Ordenavel getThis() {
		return this;
	}


}
