package com.fatec.scc.servico;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;
import com.fatec.scc.model.Cliente;
import com.fatec.scc.model.ClienteRepository;
import com.fatec.scc.model.Endereco;
@Service
public class ClienteServicoI implements ClienteServico{
	Logger logger = LogManager.getLogger(ClienteServicoI.class);
	@Autowired
	private ClienteRepository repository;
	
	public Iterable<Cliente> findAll() {
		return repository.findAll();
	}
	public Cliente findByCpf(String cpf) {
		return repository.findByCpf(cpf);
	}
	public void deleteById(Long id) {
		repository.deleteById(id);
		logger.info(">>>>>> 2. comando exclusao executado para o id => " + id);
	}
	public Cliente findById(Long id) {
		return repository.findById(id).get();
	}
	public ModelAndView saveOrUpdate (Cliente cliente) {
		ModelAndView modelAndView = new ModelAndView("consultarCliente");
		try {
			String endereco = obtemEndereco(cliente.getCep());
			if (endereco != null) {
				cliente.setDataCadastro(new DateTime());
				cliente.setEndereco(endereco);
				repository.save(cliente);
				logger.info(">>>>>> 6. endereço ok comando save executado ");
				modelAndView.addObject("clientes", repository.findAll());
			}else {
				logger.info(">>>>>> 6. comando save executado com erro endereço inválido. ");
				modelAndView.setViewName("cadastrarCliente");
				modelAndView.addObject("message", "Endereço não localizado.");
			}

		} catch (Exception e) { // captura validacoes na camada de persistencia
			modelAndView.setViewName("cadastrarCliente");
			if (e.getMessage().contains("could not execute statement")) {
				modelAndView.addObject("message", "Dados invalidos - cliente já cadastrado.");
				logger.info(">>>>>> 6. cliente ja cadastrado ==> " + e.getMessage());
			} else {
				modelAndView.addObject("message", "Erro não esperado - contate o administrador");
				logger.error(">>>>>> 6. erro nao esperado ==> " + e.getMessage());
			}
		}
		return modelAndView;
}

	public String obtemEndereco(String cep) {
		RestTemplate template = new RestTemplate();
		String url = "https://viacep.com.br/ws/{cep}/json/";
		Endereco endereco = template.getForObject(url, Endereco.class, cep);
		logger.info(">>>>>> 5. clienteservico obtem endereco ==> " + endereco.toString());
		return endereco.getLogradouro();
	}
}	
	
	
	
