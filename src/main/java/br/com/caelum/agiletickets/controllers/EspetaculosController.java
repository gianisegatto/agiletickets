package br.com.caelum.agiletickets.controllers;

import static br.com.caelum.vraptor.view.Results.status;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.joda.time.LocalDate;
import org.joda.time.LocalTime;

import br.com.caelum.agiletickets.domain.Agenda;
import br.com.caelum.agiletickets.domain.DiretorioDeEstabelecimentos;
import br.com.caelum.agiletickets.domain.Promocao;
import br.com.caelum.agiletickets.models.Espetaculo;
import br.com.caelum.agiletickets.models.Periodicidade;
import br.com.caelum.agiletickets.models.Sessao;
import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.Validator;
import br.com.caelum.vraptor.validator.ValidationMessage;

import com.google.common.base.Strings;

@Resource
public class EspetaculosController {

	private final Agenda agenda;
	private final Validator validator;
	private final Result result;

	private final DiretorioDeEstabelecimentos estabelecimentos;

	public EspetaculosController(Agenda agenda,
			DiretorioDeEstabelecimentos estabelecimentos, Validator validator,
			Result result) {
		this.agenda = agenda;
		this.estabelecimentos = estabelecimentos;
		this.validator = validator;
		this.result = result;
	}

	@Get
	@Path("/espetaculos")
	public List<Espetaculo> lista() {
		
		this.incluirListaEstabelecimentos();
		
		return agenda.espetaculos();
	}

	private void incluirListaEstabelecimentos() {
		result.include("estabelecimentos", estabelecimentos.todos());
	}

	@Post
	@Path("/espetaculos")
	public void adiciona(Espetaculo espetaculo) {
		
		boolean espetaculoValido = this.validarEspetaculo(espetaculo);

		if (espetaculoValido) {
			agenda.cadastra(espetaculo);
		}
		
		result.redirectTo(this).lista();
	}

	private boolean validarEspetaculo(Espetaculo espetaculo) {
		
		boolean espetaculoValido = true;
		
		if (Strings.isNullOrEmpty(espetaculo.getNome())) {
			validator.add(new ValidationMessage("Nome do espetáculo nao pode estar em branco", ""));
			espetaculoValido = false;
		}
		
		if (Strings.isNullOrEmpty(espetaculo.getDescricao())) {
			validator.add(new ValidationMessage("Descricao do espetaculo nao pode estar em branco", ""));
			espetaculoValido = false;
		}
		validator.onErrorRedirectTo(this).lista();
		
		return espetaculoValido;
	}

	@Get
	@Path("/sessao/{id}")
	public void sessao(Long id) {
		
		Sessao sessao = agenda.sessao(id);
		if (sessao == null) {
			result.notFound();
		}

		result.include("sessao", sessao);
	}

	@Post
	@Path("/sessao/{sessaoId}/reserva")
	public void reserva(Long sessaoId, final Integer quantidade) {
		
		Sessao sessao = agenda.sessao(sessaoId);
		
		if (sessao != null) {

			boolean reservaValida = validarReserva(quantidade, sessao);
	
			if (reservaValida) {
				sessao.reserva(quantidade);
				result.include("message", "Sessao reservada com sucesso");
			}
	
			result.redirectTo(IndexController.class).index();
			
		} else {
			result.notFound();
		}
	}

	private boolean validarReserva(final Integer quantidade, Sessao sessao) {
		
		boolean reservaValida = true;
		
		if (quantidade < 1) {
			validator.add(new ValidationMessage("Voce deve escolher um lugar ou mais", ""));
			reservaValida = false;
		}

		if (!sessao.podeReservar(quantidade)) {
			validator.add(new ValidationMessage("Nao existem ingressos dispon√≠veis", ""));
			reservaValida = false;
		}

		validator.onErrorRedirectTo(this).sessao(sessao.getId());
		
		return reservaValida;
	}

	@Get
	@Path("/espetaculo/{espetaculoId}/sessoes")
	public void sessoes(Long espetaculoId) {
		
		Espetaculo espetaculo = this.carregarEspetaculo(espetaculoId);

		if (espetaculo != null) {
			result.include("espetaculo", espetaculo);
		}
	}

	@Post @Path("/espetaculo/{espetaculoId}/sessoes")
	public void cadastraSessoes(Long espetaculoId, LocalDate inicio, LocalDate fim, LocalTime horario, Periodicidade periodicidade) {

		Espetaculo espetaculo = this.carregarEspetaculo(espetaculoId);

		if (espetaculo != null) {
			List<Sessao> sessoes = espetaculo.criaSessoes(inicio, fim, horario, periodicidade);
	
			agenda.agende(sessoes);
	
			result.include("message", sessoes.size() + " sessoes criadas com sucesso");
			result.redirectTo(this).lista();
		}
	}

	@Get
	@Path("/asdsdfg")
	public void promocoesDisponiveisParaEspetaculo() {

		List<Promocao> todasPromocoes = new ArrayList<Promocao>();
		Espetaculo espetaculo = new Espetaculo();

		Map<Promocao, List<Sessao>> sessoesPromocionais = new HashMap<Promocao, List<Sessao>>();

		for (Promocao promocao : todasPromocoes) {
			
			for (Sessao sessao : espetaculo.getSessoes()) {
				
				if (promocao.isSempre()	|| sessao.getIngressosDisponiveis() <= sessao.getTotalIngressos() * 0.1) {
					
					if (sessao.dentroDoIntervalo(promocao)) {
						
						if (sessoesPromocionais.containsKey(promocao)) {
							sessoesPromocionais.get(promocao).add(sessao);
						} else {
							sessoesPromocionais.put(promocao,
									new ArrayList<Sessao>(Arrays.asList(sessao)));
						}
					}
				}
			}
		}
	}

	private Espetaculo carregarEspetaculo(Long espetaculoId) {
		
		Espetaculo espetaculo = agenda.espetaculo(espetaculoId);
		if (espetaculo == null) {
			validator.add(new ValidationMessage("Espetaculo não encontrado", ""));
		}
		validator.onErrorUse(status()).notFound();
		return espetaculo;
	}
}
