package br.com.caelum.leilao.servico;

import static org.junit.Assert.assertEquals;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import br.com.caelum.leilao.builder.LeilaoBuilder;
import br.com.caelum.leilao.dominio.Leilao;
import br.com.caelum.leilao.dominio.Usuario;

public class LeilaoTest {
	
	@BeforeClass
	public static void testandoBeforeClass() {
	  System.out.println("before class");
	}

	@AfterClass
	public static void testandoAfterClass() {
	  System.out.println("after class");
	}
	
	@Test
	public void naoDeveAceitarDoisLancesSeguidosDoMesmoUsuario() {
		Leilao leilao = new LeilaoBuilder().para("Honda FIT").
											lance(new Usuario("Marcelo"), 1000).
											lance(new Usuario("Marcelo"), 2000).
											constroi();
		
		assertEquals(1, leilao.getLances().size());
		assertEquals(1000, leilao.getLances().get(0).getValor(), 0.0001);
	}
	
	@Test
	public void naoDeveAceitarMaisDoQue5LancesDeUmMesmoUsuario() {
		Leilao leilao = new LeilaoBuilder().para("Honda FIT").
				lance(new Usuario("Marcelo"), 1000).
				lance(new Usuario("Fernanda"), 2000).
				lance(new Usuario("Marcelo"), 3000).
				lance(new Usuario("Fernanda"), 4000).
				lance(new Usuario("Marcelo"), 5000).
				lance(new Usuario("Fernanda"), 6000).
				lance(new Usuario("Marcelo"), 7000).
				lance(new Usuario("Fernanda"), 8000).
				lance(new Usuario("Marcelo"), 9000).
				lance(new Usuario("Fernanda"), 10000).
				lance(new Usuario("Marcelo"), 11000).
				constroi();
		
		assertEquals(10, leilao.getLances().size());
		assertEquals(1000, leilao.getLances().get(0).getValor(), 0.0001);
		assertEquals(2000, leilao.getLances().get(1).getValor(), 0.0001);
	}
	
	@Test
    public void deveDobrarOUltimoLanceDado() {
		Usuario steveJobs = new Usuario("Steve Jobs");
		Usuario billGates = new Usuario("Bill Gates");
		
		Leilao leilao = new LeilaoBuilder().para("Mackbook Pro 15").
				lance(steveJobs, 2000).
				lance(billGates, 3000).
				dobra(steveJobs).
				constroi();

        assertEquals(4000, leilao.getLances().get(2).getValor(), 0.00001);
    }
	
	@Test
    public void naoDeveDobrarCasoNaoHajaLanceAnterior() {
		Leilao leilao = new LeilaoBuilder().para("Macbook Pro 15").
						dobra(new Usuario("Steve Jobs")).
						constroi();
		
        assertEquals(0, leilao.getLances().size());
    }

}
