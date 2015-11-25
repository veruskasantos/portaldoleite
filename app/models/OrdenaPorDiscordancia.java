package models;

import java.util.List;

public class OrdenaPorDiscordancia implements Ordenavel{

	@Override
	public List<Dica> ordenaDicas(List<Dica> dicas) {
		List<Dica> dicasOrdenada = dicas;
		boolean houveTroca = true;
		Dica temp;
		while(houveTroca){
			houveTroca = false;
			for(int i = 0; i < dicasOrdenada.size() -1; i++){
				if(dicasOrdenada.get(i).getDiscordancias() < (dicasOrdenada.get(i+1).getDiscordancias())){
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
		
		return "Por Discordância";
	}
	
	@Override
	public Ordenavel getThis() {
		return this;
	}


}
