package br.com.treinaweb.twprojetos.config;

import br.com.treinaweb.twprojetos.repositorios.ClienteRepositorio;
import br.com.treinaweb.twprojetos.repositorios.FuncionarioRepositorio;
import br.com.treinaweb.twprojetos.validadores.ClienteValidador;
import br.com.treinaweb.twprojetos.validadores.FuncionarioValidador;
import br.com.treinaweb.twprojetos.validadores.PessoaValidador;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ValidadorConfig {
    private final ClienteRepositorio clienteRepositorio;
    private final FuncionarioRepositorio funcionarioRepositorio;

    public ValidadorConfig(ClienteRepositorio clienteRepositorio, FuncionarioRepositorio funcionarioRepositorio) {
        this.clienteRepositorio = clienteRepositorio;
        this.funcionarioRepositorio = funcionarioRepositorio;
    }

    @Bean
    public ClienteValidador clienteValidador(){
        return new ClienteValidador(clienteRepositorio);
    }
    @Bean
    public FuncionarioValidador funcionarioValidador(){
        return new FuncionarioValidador(funcionarioRepositorio);
    }

    @Bean
    public PessoaValidador pessoaValidador(){
        return new PessoaValidador();
    }

}
