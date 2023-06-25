package br.com.treinaweb.twprojetos.validadores;

import br.com.treinaweb.twprojetos.entidades.Cliente;
import br.com.treinaweb.twprojetos.repositorios.ClienteRepositorio;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Optional;

public class ClienteValidador implements Validator {

    private final ClienteRepositorio clienteRepositorio;

    public ClienteValidador(ClienteRepositorio clienteRepositorio) {
        this.clienteRepositorio = clienteRepositorio;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return Cliente.class.isAssignableFrom(aClass);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Cliente cliente = (Cliente) target;

        Optional<Cliente> cli = clienteRepositorio.findByEmail(cliente.getEmail());

        if (cli.isPresent() && cli.get().getEmail() == cliente.getEmail()){
            errors.rejectValue("email", "validacao.cliente.email.unique");
        }

        cli = clienteRepositorio.findByCpf(cliente.getCpf());

        if (cli.isPresent() && cli.get().getCpf() == cliente.getCpf()){
            errors.rejectValue("cpf", "validacao.cliente.cpf.unique");
        }

    }
}
