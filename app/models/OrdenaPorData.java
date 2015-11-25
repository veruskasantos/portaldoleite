package models;

import java.util.List;

public class OrdenaPorData implements Ordenavel{

	@Override
	public List<Dica> ordenaDicas(List<Dica> dicas) {
		List<Dica> dicasOrdenada = dicas;
		boolean houveTroca = true;
		Dica temp;
		while(houveTroca){
			houveTroca = false;
			for(int i = 0; i < dicasOrdenada.size() -1; i++){
				if(dicasOrdenada.get(i).getData().after(dicasOrdenada.get(i+1).getData())){
					temp = dicasOrdenada.get(i);
					dicasOrdenada.set(i,dicasOrdenada.get(i+1) );
					dicasOrdenada.set(i+1, temp);
					houveTroca = true;
				}
			}
		}
		
		return null;
	}

	@Override
	public String getNomeTipo() {
		return "Por Data";
	}

	@Override
	public Ordenavel getThis() {
		return this;
	}

}
