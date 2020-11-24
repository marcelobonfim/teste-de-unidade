package br.com.caelum.leilao.servico;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import br.com.caelum.leilao.builder.LeilaoBuilder;
import br.com.caelum.leilao.dominio.Lance;
import br.com.caelum.leilao.dominio.Leilao;
import br.com.caelum.leilao.dominio.Usuario;

public class AvaliadorTest {
	
	private Avaliador avaliador;
	private Usuario marcelo;
	private Usuario fernanda;
	private Usuario ana;
	
	@BeforeClass
	public static void testandoBeforeClass() {
	  System.out.println("before class");
	}

	@AfterClass
	public static void testandoAfterClass() {
	  System.out.println("after class");
	}

	@Before
	public void setUp() {
		this.avaliador = new Avaliador();
		this.marcelo = new Usuario("Marcelo");
		this.fernanda = new Usuario("Fernanda");
		this.ana = new Usuario("Ana");
		System.out.println("inicializando teste!");
	}
	
	@After
	public void finaliza() {
	  System.out.println("fim");
	}
	
	@Test(expected=RuntimeException.class)
	public void avaliarLeilaoSemLances() {
		
		Leilao leilao = new LeilaoBuilder().para("Carro").constroi();
		
		avaliador.avalia(leilao);
	}
	
	
	@Test
	public void avaliarMaiorEMenorValorLeilao() {
		
		Leilao leilao = new LeilaoBuilder().para("Carro").
								lance(marcelo, 300).
								lance(fernanda, 500).
								lance(ana, 800).
								constroi();
		
		avaliador.avalia(leilao);
		
		assertEquals(800, avaliador.getMaiorLance(), 0.0001);
		assertEquals(300, avaliador.getMenorLance(), 0.0001);
	}
	
	@Test
	public void deveCalcularAMedia() {
		
		Leilao leilao = new LeilaoBuilder().para("Carro").
				lance(marcelo, 300).
				lance(fernanda, 400).
				lance(ana, 500).
				constroi();
		
        avaliador.avalia(leilao);

        assertEquals(400, avaliador.getMedia(), 0.0001);
	}
	
	@Test
	public void leilaoComApenasUmLance() {	
		
		Leilao leilao = new LeilaoBuilder().para("Carro").
				lance(marcelo, 200).
				constroi();

		avaliador.avalia(leilao);

        assertEquals(200, avaliador.getMaiorLance(), 0.0001);
        assertEquals(200, avaliador.getMenorLance(), 0.0001);
	}
	
	@Test
	public void avaliarMaiorEMenorValorLeilaoOrdemAleatoria() {
		
		Leilao leilao = new LeilaoBuilder().para("Carro").
				lance(marcelo, 200).
				lance(fernanda, 450).
				lance(ana, 120).
				lance(marcelo, 700).
				lance(fernanda, 630).
				lance(ana, 230).
				constroi();
		
		avaliador.avalia(leilao);
		
		assertEquals(120, avaliador.getMenorLance(), 0.0001);
		assertEquals(700, avaliador.getMaiorLance(), 0.0001);
	}
	
	@Test
	public void avaliarMaiorEMenorValorLancesOrdemDecrescente() {
		
		Leilao leilao = new LeilaoBuilder().para("Carro").
				lance(marcelo, 400).
				lance(fernanda, 300).
				lance(marcelo, 200).
				lance(fernanda, 100).
				constroi();
		
		avaliador.avalia(leilao);
		
		assertEquals(100, avaliador.getMenorLance(), 0.0001);
		assertEquals(400, avaliador.getMaiorLance(), 0.0001);
	}
	
	@Test
	public void avaliarTresMaioresOrdemAleatoria() {
		
		Leilao leilao = new LeilaoBuilder().para("Carro").
				lance(fernanda, 450).
				lance(ana, 120).
				lance(marcelo, 700).
				lance(fernanda, 630).
				lance(ana, 230).
				constroi();
		
		avaliador.avalia(leilao);
		
		List<Lance> maiores = avaliador.getTresMaiores();
		
        assertEquals(3, maiores.size());
		assertEquals(700, avaliador.getTresMaiores().get(0).getValor(), 0.0001);
		assertEquals(630, avaliador.getTresMaiores().get(1).getValor(), 0.0001);
		assertEquals(450, avaliador.getTresMaiores().get(2).getValor(), 0.0001);
	}
	
	@Test
	public void avaliarDoisMaioresOrdemAleatoria() {
		
		Leilao leilao = new LeilaoBuilder().para("Carro").
				lance(marcelo, 200).
				lance(fernanda, 450).
				constroi();
		
		avaliador.avalia(leilao);
		
		List<Lance> maiores = avaliador.getTresMaiores();
		
        assertEquals(2, maiores.size());
		assertEquals(450, avaliador.getTresMaiores().get(0).getValor(), 0.0001);
		assertEquals(200, avaliador.getTresMaiores().get(1).getValor(), 0.0001);
	}
	
	@Test
    public void deveDevolverListaVaziaCasoNaoHajaLances() {
		Leilao leilao = new LeilaoBuilder().para("Leilão de Carro").constroi();

        avaliador.avalia(leilao);

        List<Lance> maiores = avaliador.getTresMaiores();

        assertEquals(0, maiores.size());
    }
}
