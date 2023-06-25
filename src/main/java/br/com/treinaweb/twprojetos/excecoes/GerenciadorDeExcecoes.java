package br.com.treinaweb.twprojetos.excecoes;

import org.springframework.boot.autoconfigure.web.servlet.error.ErrorViewResolver;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@ControllerAdvice
public class GerenciadorDeExcecoes implements ErrorViewResolver {
    @Override
    public ModelAndView resolveErrorView(HttpServletRequest request, HttpStatus status, Map<String, Object> model) {
        ModelAndView modelAndView = new ModelAndView("problema");
        modelAndView.addObject("status", status.value());

        switch (status.value()){
            case 404:
                modelAndView.addObject("titulo", "Pagina nao encontrada");
                modelAndView.addObject("mensagem", "a pagina que voce procura nao foi encontrada ");
                modelAndView.addObject("causa", "URL" + model.get("path") + "nao foi encontrada");
                modelAndView.addObject("cssClass", "text-warning");
                break;
            case 500:
                modelAndView.addObject("titulo", "Erro interno");
                modelAndView.addObject("mensagem", "Opss! algo deu errado");
                modelAndView.addObject("causa", "Ocorreu um erro inesperado");
                modelAndView.addObject("cssClass", "text-danger");
                break;
        }

        return modelAndView;
    }
}
