import java.util.ArrayList;
import java.util.List;

import models.Disciplina;
import models.Tema;
import models.dao.GenericDAOImpl;
import play.Application;
import play.GlobalSettings;
import play.Logger;
import play.db.jpa.JPA;


public class Global extends GlobalSettings {

	private static GenericDAOImpl dao = new GenericDAOImpl();
	private List<Disciplina> disciplinas = new ArrayList<>();
	
	@Override
	public void onStart(Application app) {
		Logger.info("Aplicação inicializada...");

		JPA.withTransaction(new play.libs.F.Callback0() {
			@Override
			public void invoke() throws Throwable {
				if(dao.findAllByClassName(Disciplina.class.getName()).size() == 0){
					criaDisciplinaTemas();
				}
			}
		});
	}
	
	@Override
	public void onStop(Application app){
	    JPA.withTransaction(new play.libs.F.Callback0() {
	    @Override
	    public void invoke() throws Throwable {
	        Logger.info("Aplicação finalizando...");
	        disciplinas = dao.findAllByClassName("Disciplina");

	        for (Disciplina disciplina: disciplinas) {
	        dao.removeById(Disciplina.class, disciplina.getId());
	       } 
	    }}); 
	}
	
	private void criaDisciplinaTemas(){
		Disciplina si1 = new Disciplina("Sistemas de Informação 1");
		si1.addTema(new Tema("Análise x Design"));
		si1.addTema(new Tema("Orientação a objetos"));
		si1.addTema(new Tema("GRASP"));
		si1.addTema(new Tema("GoF"));
		si1.addTema(new Tema("Arquitetura"));
		si1.addTema(new Tema("Play"));
		si1.addTema(new Tema("JavaScript"));
		si1.addTema(new Tema("HTML / CSS / Bootstrap"));
		si1.addTema(new Tema("Heroku"));
		si1.addTema(new Tema("Labs"));
		si1.addTema(new Tema("Minitestes"));
		si1.addTema(new Tema("Projeto"));
		dao.persist(si1);

                Disciplina eda = new Disciplina("Estrutura de dados e algoritimos");
                eda.addTema(new Tema("Análise de Algoritmos Iterativos"));
                eda.addTema(new Tema("Análise de Algoritmos Recursivos"));
		eda.addTema(new Tema("TAD (vetor, pilha e fila)"));
		eda.addTema(new Tema("Listas Encadeadas (iterativo)"));
		eda.addTema(new Tema("Listas Encadeadas (recursivo)"));
		eda.addTema(new Tema("Árvore Binária de Pesquisa (BST)"));
		eda.addTema(new Tema("Tabelas Hash"));
                eda.addTema(new Tema("Heaps"));
		eda.addTema(new Tema("SkipList"));
		eda.addTema(new Tema("Árvores AVL"));
		eda.addTema(new Tema("Árvores PV"));
		eda.addTema(new Tema("Árvores Splay"));
		eda.addTema(new Tema("Árvores B"));
		dao.persist(eda);
                

                Disciplina programacao2 = new Disciplina("Programação 2");
                programacao2.addTema(new Tema("Recursividade"));
                programacao2.addTema(new Tema("Coleções em java"));
		programacao2.addTema(new Tema("Refatoramento e regras de design"));
		programacao2.addTema(new Tema("Salvando dados em arquivos"));
		programacao2.addTema(new Tema("Interfaces e polimorfismo"));
		programacao2.addTema(new Tema("Herança e polimorfismo"));
		programacao2.addTema(new Tema("Tratamento de exceções e enumerações"));
                programacao2.addTema(new Tema("Reuso, Composição e herança"));
		programacao2.addTema(new Tema("Teste de unidade"));
		programacao2.addTema(new Tema("Criação de classes e objetos"));
		programacao2.addTema(new Tema("INtrodução a java"));
		programacao2.addTema(new Tema("Labroratórios"));
		programacao2.addTema(new Tema("Projeto"));
		dao.persist(programacao2);



		dao.flush();
	}
}
