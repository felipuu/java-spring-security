package br.com.treinaweb.twprojetos.validadores;

import br.com.treinaweb.twprojetos.entidades.Funcionario;
import br.com.treinaweb.twprojetos.repositorios.FuncionarioRepositorio;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Optional;

public class FuncionarioValidador implements Validator {


    private final FuncionarioRepositorio funcionarioRepositorio;

    public FuncionarioValidador(FuncionarioRepositorio funcionarioRepositorio){
        this.funcionarioRepositorio = funcionarioRepositorio;
    }
    @Override
    public boolean supports(Class<?> aClass) {
        return Funcionario.class.isAssignableFrom(aClass);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Funcionario funcionario = (Funcionario) target;

        if (funcionario.getDataAdmissao() != null && funcionario.getDataDemissao() != null){
            if (funcionario.getDataDemissao().isBefore(funcionario.getDataAdmissao())){
                errors.rejectValue("dataDemissao", "validacao.funcionario.dataAdmissao.posterior.dataDemissao");
            }
        }

        Optional<Funcionario> func = funcionarioRepositorio.findByEmail(funcionario.getEmail());

        if (func.isPresent() && func.get().getEmail() == funcionario.getEmail()){
            errors.rejectValue("email", "validacao.funcionario.email.unique");
        }

        func = funcionarioRepositorio.findByCpf(funcionario.getCpf());

        if (func.isPresent() && func.get().getCpf() == funcionario.getCpf()){
            errors.rejectValue("cpf", "validacao.funcionario.cpf.unique");
        }
    }
}
