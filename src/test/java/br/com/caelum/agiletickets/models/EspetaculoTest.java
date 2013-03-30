package br.com.caelum.agiletickets.models;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.joda.time.LocalTime;
import org.junit.Before;
import org.junit.Test;

public class EspetaculoTest {

	private Espetaculo espetaculo;
	
	@Before
	public void beforeTest() {
		
		espetaculo = new Espetaculo();
		List<Sessao> sessoes = obterSessoesSemanais();
		
	}
	
	@Test
	public void deveInformarSeEhPossivelReservarAQuantidadeDeIngressosDentroDeQualquerDasSessoes() {
		Espetaculo ivete = new Espetaculo();

		ivete.getSessoes().add(sessaoComIngressosSobrando(1));
		ivete.getSessoes().add(sessaoComIngressosSobrando(3));
		ivete.getSessoes().add(sessaoComIngressosSobrando(2));

		assertTrue(ivete.Vagas(5));
	}

	@Test
	public void deveInformarSeEhPossivelReservarAQuantidadeExataDeIngressosDentroDeQualquerDasSessoes() {
		Espetaculo ivete = new Espetaculo();

		ivete.getSessoes().add(sessaoComIngressosSobrando(1));
		ivete.getSessoes().add(sessaoComIngressosSobrando(3));
		ivete.getSessoes().add(sessaoComIngressosSobrando(2));

		assertTrue(ivete.Vagas(6));
	}

	@Test
	public void DeveInformarSeNaoEhPossivelReservarAQuantidadeDeIngressosDentroDeQualquerDasSessoes() {
		Espetaculo ivete = new Espetaculo();

		ivete.getSessoes().add(sessaoComIngressosSobrando(1));
		ivete.getSessoes().add(sessaoComIngressosSobrando(3));
		ivete.getSessoes().add(sessaoComIngressosSobrando(2));

		assertFalse(ivete.Vagas(15));
	}

	@Test
	public void DeveInformarSeEhPossivelReservarAQuantidadeDeIngressosDentroDeQualquerDasSessoesComUmMinimoPorSessao() {
		Espetaculo ivete = new Espetaculo();

		ivete.getSessoes().add(sessaoComIngressosSobrando(3));
		ivete.getSessoes().add(sessaoComIngressosSobrando(3));
		ivete.getSessoes().add(sessaoComIngressosSobrando(4));

		assertTrue(ivete.Vagas(5, 3));
	}

	@Test
	public void DeveInformarSeEhPossivelReservarAQuantidadeExataDeIngressosDentroDeQualquerDasSessoesComUmMinimoPorSessao() {
		Espetaculo ivete = new Espetaculo();

		ivete.getSessoes().add(sessaoComIngressosSobrando(3));
		ivete.getSessoes().add(sessaoComIngressosSobrando(3));
		ivete.getSessoes().add(sessaoComIngressosSobrando(4));

		assertTrue(ivete.Vagas(10, 3));
	}

	@Test
	public void DeveInformarSeNaoEhPossivelReservarAQuantidadeDeIngressosDentroDeQualquerDasSessoesComUmMinimoPorSessao() {
		Espetaculo ivete = new Espetaculo();

		ivete.getSessoes().add(sessaoComIngressosSobrando(2));
		ivete.getSessoes().add(sessaoComIngressosSobrando(2));
		ivete.getSessoes().add(sessaoComIngressosSobrando(2));

		assertFalse(ivete.Vagas(5, 3));
	}

	private Sessao sessaoComIngressosSobrando(int quantidade) {
		Sessao sessao = new Sessao();
		sessao.setTotalIngressos(quantidade * 2);
		sessao.setIngressosReservados(quantidade);

		return sessao;
	}
	
	private List<Sessao> obterSessoesSemanais() {
		List<Sessao> sessoes = espetaculo.criaSessoes(new LocalDate("2013-01-09"), 
				new LocalDate("2013-01-23"), new LocalTime("17:00"), Periodicidade.SEMANAL);
		return sessoes;
	}
	
	private List<Sessao> obterSessoesDiarias() {
		List<Sessao> sessoes = espetaculo.criaSessoes(new LocalDate("2013-01-09"), 
				new LocalDate("2013-01-23"), new LocalTime("17:00"), Periodicidade.DIARIA);
		return sessoes;
	}
	
	@Test
	public void testCriarTemporadaSemanal() throws Exception {
		
		List<Sessao> sessoes = obterSessoesSemanais();
		
		assertThat(sessoes, hasSize(3));
		Assert.assertEquals(3, sessoes.size());
	}
	
	@Test
	public void testCriarTemporadaDiaria() throws Exception {
		
		List<Sessao> sessoes = obterSessoesDiarias();
		
		assertThat(sessoes, hasSize(15));
	}
	
	@Test
	public void testDiasNoPeriodoSemanal() throws Exception {
		
		List<Sessao> sessoes = obterSessoesSemanais();
		
		List<DateTime> datas = new ArrayList<DateTime>();
		
		datas.add(new DateTime(2013, 1, 9, 17, 0, 0, 0));
		datas.add(new DateTime(2013, 1, 16, 17, 0, 0, 0));
		datas.add(new DateTime(2013, 1, 23, 17, 0, 0, 0));
		
		for (int i = 0; i < datas.size(); i++) {
			DateTime dataTeste = datas.get(i);
			DateTime dataSessao = sessoes.get(i).getInicio();
			
			assertEquals(dataTeste, dataSessao);
		}
	}
	
	@Test
	public void testDiasNoPeriodoDiario() throws Exception {
		
		List<Sessao> sessoes = this.obterSessoesDiarias();
		
		List<DateTime> datas = new ArrayList<DateTime>();
		
		datas.add(new DateTime(2013, 1, 9, 17, 0, 0, 0));
		datas.add(new DateTime(2013, 1, 10, 17, 0, 0, 0));
		datas.add(new DateTime(2013, 1, 11, 17, 0, 0, 0));
		datas.add(new DateTime(2013, 1, 12, 17, 0, 0, 0));
		datas.add(new DateTime(2013, 1, 13, 17, 0, 0, 0));
		datas.add(new DateTime(2013, 1, 14, 17, 0, 0, 0));
		datas.add(new DateTime(2013, 1, 15, 17, 0, 0, 0));
		datas.add(new DateTime(2013, 1, 16, 17, 0, 0, 0));
		datas.add(new DateTime(2013, 1, 17, 17, 0, 0, 0));
		datas.add(new DateTime(2013, 1, 18, 17, 0, 0, 0));
		datas.add(new DateTime(2013, 1, 19, 17, 0, 0, 0));
		datas.add(new DateTime(2013, 1, 20, 17, 0, 0, 0));
		datas.add(new DateTime(2013, 1, 21, 17, 0, 0, 0));
		datas.add(new DateTime(2013, 1, 22, 17, 0, 0, 0));
		datas.add(new DateTime(2013, 1, 23, 17, 0, 0, 0));
		
		for (int i = 0; i < datas.size(); i++) {
			DateTime dataTeste = datas.get(i);
			DateTime dataSessao = sessoes.get(i).getInicio();
			
			assertEquals(dataTeste, dataSessao);
		}
	}
	
	@Test
	public void testConteuPeriodoSessaoSemanal() throws Exception {
		
		List<Sessao> sessoes = this.obterSessoesSemanais();
		
		List<Sessao> sessoesComparacao = new ArrayList<Sessao>();
		
		Sessao sessao = new Sessao();
		sessao.setDuracaoEmMinutos(60);
		sessao.setInicio(new DateTime(2013, 1, 9, 17, 0, 0, 0));
		sessoesComparacao.add(sessao);
		
		sessao = new Sessao();
		sessao.setDuracaoEmMinutos(60);
		sessao.setInicio(new DateTime(2013, 1, 16, 17, 0, 0, 0));
		sessoesComparacao.add(sessao);

		sessao = new Sessao();
		sessao.setDuracaoEmMinutos(60);
		sessao.setInicio(new DateTime(2013, 1, 23, 17, 0, 0, 0));
		sessoesComparacao.add(sessao);
		
		assertThat(sessoes, org.hamcrest.Matchers.contains(sessoesComparacao.toArray()));
	}
}
