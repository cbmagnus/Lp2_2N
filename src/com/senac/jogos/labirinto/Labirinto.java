package com.senac.jogos.labirinto;

import static java.lang.System.*;

import java.io.FileInputStream;
import java.util.Scanner;

public class Labirinto {
	
	private static final Scanner teclado = new Scanner(System.in);
	
	private static Sala[] salas;
	private int countSalas;
	private static int salaAtual;
	private static String direcao;
	
	private void run()
	{
		inicializaLabirinto();
		
		/*for (Sala s: salas) {
			if (s == null) break;
			out.println(s);
		}
		*/
		
		/*
		while (! isGameOver()) {
		 
			exibeStatus();
			executaComando ( teclado.next() );
		}
		*/
	}
	
	private void inicializaLabirinto()
	{
		salas = new Sala[50];
		salas[0] = new Sala();
		countSalas = 1;
		try {
			leLabirinto( new Scanner( new FileInputStream("labirinto.txt") ) );
		} catch (Exception e) {
			err.println(e.getMessage());
			exit(1);
		}
	}

	private void leLabirinto( Scanner arquivo ) throws Exception
	{
		String cmd = arquivo.next().toLowerCase();
		while (cmd.equals("room")) {
			int salaId = arquivo.nextInt();
			salas[salaId] = new Sala();
			Sala sala = salas[salaId];
			
			String direcao = arquivo.next();

			do {
				if (arquivo.hasNextInt()) {
					salaId = arquivo.nextInt();
				} else if (arquivo.next().equalsIgnoreCase("EXIT")) {
					salaId = 0;
				} else break;
			
				sala.addConexao(direcao, salaId);
			
				if (!arquivo.hasNext())
					return;
				cmd = arquivo.next().toLowerCase();	
				if (cmd.equals("trap")) {
					sala.setArmadilha(direcao);
					if (!arquivo.hasNext())
						return;
					cmd = arquivo.next();
				}
				direcao = cmd;
			} while (!cmd.equals("room"));
		}
		throw new Exception("Arquivo de descricao do labirinto invalido.");
	}
	
	public static void mover() throws Exception{
		do{
			Scanner sc = new Scanner(System.in);
			out.println(salas[salaAtual]);
		
			out.println("Para onde deseja ir: \nou digite sair");
			direcao = sc.next();
			
			if(direcao.equals("sair")){
				out.println("voce desistiu!");
				return;
			}else{
		
			Sala s = salas[salaAtual]; 
			salaAtual = s.getSaida(direcao);
		
			out.println(salas[salaAtual]);
			}
		}while(!direcao.equals("sair"));
	}
	
	public static void main(String[] args) throws Exception
	{
		(new Labirinto()).run();
		
		salaAtual = 10;
		mover();
	}
}
