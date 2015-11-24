import java.util.ArrayList;
import java.util.List;

import models.Dica;
import models.DicaAssunto;
import models.DicaConselho;
import models.DicaDisciplina;
import models.DicaMaterial;
import models.Disciplina;
import models.Tema;
import models.User;
import models.dao.GenericDAOImpl;
import play.Application;
import play.GlobalSettings;
import play.Logger;
import play.db.jpa.JPA;
import play.db.jpa.Transactional;


public class Global extends GlobalSettings {

	private static GenericDAOImpl dao = new GenericDAOImpl();
	private List<Disciplina> disciplinas = new ArrayList<>();
	private User user1, user2, user3, user4;
	private Tema projeto, OO, lab, minitestes, javaScript, logicaProposicional;
	private Dica dicaConselho, dicaDisciplina, dicaConselhoOutro, dicaConselho3, 
	dicaAssunto, dicaMaterial;
	
	@Override
	public void onStart(Application app) {
		Logger.info("Aplicação inicializada...");

		JPA.withTransaction(new play.libs.F.Callback0() {
			@Override
			public void invoke() throws Throwable {
				if(dao.findAllByClassName(Disciplina.class.getName()).size() == 0){
					criaDisciplinaTemas();
				}
				cadastraUsuarios();
				cadastraDicas();
				cadastraVotos();
			}
		});
	}
	
	// falta remover os usuarios e dicas quando a aplicacao eh desligada
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
		OO = new Tema("Orientação a objetos");
		si1.addTema(OO);
		si1.addTema(new Tema("GRASP"));
		si1.addTema(new Tema("GoF"));
		si1.addTema(new Tema("Arquitetura"));
		si1.addTema(new Tema("Play"));
		javaScript = new Tema("JavaScript");
		si1.addTema(javaScript);
		si1.addTema(new Tema("HTML / CSS / Bootstrap"));
		si1.addTema(new Tema("Heroku"));
		si1.addTema(new Tema("Labs"));
		minitestes = new Tema("Minitestes");
		si1.addTema(minitestes);
		projeto = new Tema("Projeto");
		si1.addTema(projeto);
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
		lab = new Tema("Labroratórios");
		programacao2.addTema(lab);
		programacao2.addTema(new Tema("Projeto"));
		dao.persist(programacao2);

		Disciplina logicaMatematica = new Disciplina("Lógica Matemática");
		logicaProposicional = new Tema ("Lógica Proposicional");
		logicaMatematica.addTema(logicaProposicional);
		logicaMatematica.addTema(new Tema("Tableaux Semântico"));
		logicaMatematica.addTema(new Tema("CNF"));
		logicaMatematica.addTema(new Tema("Sat Solver"));
		logicaMatematica.addTema(new Tema("Semântica na Lógica Proposicional"));
		logicaMatematica.addTema(new Tema("Dedução na Lógica Proposicional"));
		logicaMatematica.addTema(new Tema("Lógica de Predicados"));
		logicaMatematica.addTema(new Tema("Semântica na Lógica de Predicados"));
		logicaMatematica.addTema(new Tema("Dedução na Lógica de Predicados"));
		logicaMatematica.addTema(new Tema("Lógica Temporal"));
		logicaMatematica.addTema(new Tema("Model Checker"));
		logicaMatematica.addTema(new Tema("Projeto"));
        dao.persist(logicaMatematica);

		dao.flush();
	}
	
	@Transactional
	private void cadastraUsuarios(){
		
		user1 = new User("user1@hot.com", "123", "user1");
		user1.setNome("User1");
		dao.persist(user1);
		
		user2 = new User("user2@hot.com", "123", "user2");
		user2.setNome("User2");
		dao.persist(user2);
		
		user3 = new User("user3@hot.com", "123", "user3");
		user3.setNome("User3");
		dao.persist(user3);
		
		user4 = new User("user4@hot.com", "123", "user4");
		user4.setNome("User4");
		dao.persist(user4);
		
		User user5 = new User("user5@hot.com", "123", "user5");
		user5.setNome("User5");
		dao.persist(user5);
		
		User user6 = new User("user6@hot.com", "123", "user6");
		user6.setNome("User6");
		dao.persist(user6);
		
		User user7 = new User("user7@hot.com", "123", "user7");
		user7.setNome("User7");
		dao.persist(user7);
		
		User user8 = new User("user8@hot.com", "123", "user8");
		user8.setNome("User8");
		dao.persist(user8);
		
		User user9 = new User("user9@hot.com", "123", "user9");
		user9.setNome("User9");
		dao.persist(user9);
		
		User user10 = new User("user10@hot.com", "123", "user10");
		user10.setNome("User10");
		dao.persist(user10);
		
	}
	
	private void cadastraDicas(){
		
		dicaConselho = new DicaConselho("Fazer todos os minitestes é uma "
				+ "excelente forma de praticar os assuntos e conhecer as ferramentas");
		dicaConselho.setUser(user1.getNome());
		dicaConselho.setTema(projeto);
		dicaConselho.setData(2015,1, 10, 20, 30);
		projeto.addDica(dicaConselho);
		dao.persist(dicaConselho);
		dao.persist(projeto);
		
		
		dicaDisciplina = new DicaDisciplina("Programação II", "Nesta disiciplina"
				+ " aprende-se o que é OO e pratica-se a mesma através da "
				+ "programação em Java.");
		dicaDisciplina.setUser(user1.getNome());
		dicaDisciplina.setTema(OO);
		dicaDisciplina.setData(2015,3, 10, 20, 30);
		OO.addDica(dicaDisciplina);
		dao.persist(dicaDisciplina);
		dao.persist(OO);
		
		
		dicaConselhoOutro = new DicaConselho("Fazer todos os laboratórios "
				+ "garante uma boa experiência para desenvolver o projeto mais "
				+ "tranquilo.");
		dicaConselhoOutro.setUser(user1.getNome());
		dicaConselhoOutro.setTema(lab);
		dicaConselhoOutro.setData(2015,2, 10, 20, 30);
		lab.addDica(dicaConselhoOutro);
		dao.persist(dicaConselhoOutro);
		dao.persist(lab);
		
		
		dicaConselho3 = new DicaConselho("Responder os leites de períodos "
				+ "anteriores é uma boa forma de praticar os assuntos teóricos "
				+ "e a programação no papel.");
		dicaConselho3.setUser(user1.getNome());
		dicaConselho3.setTema(minitestes);
		dicaConselho3.setData(2015,5, 10, 20, 30);
		minitestes.addDica(dicaConselho3);
		dao.persist(dicaConselho3);
		dao.persist(minitestes);
		
		
		dicaAssunto = new DicaAssunto("Saber programar em Java facilita "
				+ "rapidamente a compreensão da linguagem JavaScript");
		dicaAssunto.setUser(user1.getNome());
		dicaAssunto.setTema(javaScript);
		dicaAssunto.setData(2015,4, 10, 20, 30);
		javaScript.addDica(dicaAssunto);
		dao.persist(dicaAssunto);
		dao.persist(javaScript);
		
		dicaMaterial = new DicaMaterial("https://www.google.com.br/"
				+ "url?sa=t&rct=j&q=&esrc=s&source=web&cd=3&cad=rja&uact=8&ved="
				+ "0ahUKEwjMxceHzaLJAhXMmpAKHaCKB_gQFggkMAI&url=https%3A%2F%2Fpt."
				+ "wikipedia.org%2Fwiki%2FL%25C3%25B3gica_matem%25C3%25A1tica&usg="
				+ "AFQjCNHZFO7AY7tcmwdt6HsePdjUKHbmFw");
		dicaMaterial.setUser(user1.getNome());
		dicaMaterial.setTema(logicaProposicional);
		dicaMaterial.setData(2015,6, 10, 20, 30);
		logicaProposicional.addDica(dicaMaterial);
		dao.persist(dicaMaterial);
		dao.persist(logicaProposicional);
		
	}
	
	private void cadastraVotos(){
		
		dicaConselho.incrementaConcordancias();
		dicaConselho.addUsuarioQueVotou(user2.getNome());
		
		dicaConselho.incrementaConcordancias();
		dicaConselho.addUsuarioQueVotou(user3.getNome());
		
		dicaConselho.incrementaDiscordancias();
		dicaConselho.addUserCommentary(user4.getLogin(), "Discordo. "
				+ "Os minitestes focam mais nos padrões que nas ferramentas "
				+ "propriamente ditas.");
		dicaConselho.addUsuarioQueVotou(user4.getNome());
		dao.persist(dicaConselho);
		
		dicaDisciplina.incrementaDiscordancias();
		dicaDisciplina.addUserCommentary(user2.getLogin(), "Minha opinião: "
				+ "Esperem para pagar com Gustavo.");
		
		dicaDisciplina.incrementaDiscordancias();
		dicaDisciplina.addUserCommentary(user3.getLogin(), "Quem dera leda fosse "
				+ "p2...");
		dao.persist(dicaDisciplina);
		
		dicaAssunto.incrementaConcordancias();
		dicaAssunto.addUsuarioQueVotou(user2.getNome());
		
		dicaAssunto.incrementaConcordancias();
		dicaAssunto.addUsuarioQueVotou(user3.getNome());
		
		dicaAssunto.incrementaConcordancias();
		dicaAssunto.addUsuarioQueVotou(user4.getNome());
		dao.persist(dicaAssunto);
		
		dicaMaterial.incrementaConcordancias();
		dicaMaterial.addUsuarioQueVotou(user2.getNome());
		
		dicaMaterial.incrementaDiscordancias();
		dicaMaterial.addUserCommentary(user3.getLogin(), "Já eu, prefiro os slides "
				+ "do professor.");
		dicaMaterial.addUsuarioQueVotou(user3.getNome());

	}
	
}
