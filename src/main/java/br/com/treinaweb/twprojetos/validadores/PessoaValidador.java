package br.com.treinaweb.twprojetos.validadores;

import br.com.treinaweb.twprojetos.entidades.Pessoa;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.time.LocalDate;
import java.time.Period;

public class PessoaValidador implements Validator {
    @Override
    public boolean supports(Class<?> aClass) {
        return Pessoa.class.isAssignableFrom(aClass);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Pessoa pessoa = (Pessoa) target;

        if (pessoa.getDataNascimento() != null){
            int idade = Period.between(pessoa.getDataNascimento(), LocalDate.now()).getYears();

            if (idade < 18 || idade >= 100){
                errors.rejectValue("dataNascimento", "validacao.pessoa.idade");
            }
        }
    }
}
