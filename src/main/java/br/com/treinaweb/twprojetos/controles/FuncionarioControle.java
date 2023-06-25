package br.com.treinaweb.twprojetos.controles;

import br.com.treinaweb.twprojetos.entidades.Funcionario;
import br.com.treinaweb.twprojetos.repositorios.CargoRepositorio;
import br.com.treinaweb.twprojetos.repositorios.FuncionarioRepositorio;
import br.com.treinaweb.twprojetos.utils.SenhaUtils;
import br.com.treinaweb.twprojetos.validadores.FuncionarioValidador;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Controller
@RequestMapping("/funcionarios")
public class FuncionarioControle {

    @Autowired
    private FuncionarioRepositorio funcionarioRepositorio;

    @Autowired
    private CargoRepositorio cargoRepositorio;

    @InitBinder
    public void initBinder(WebDataBinder binder){
        binder.addValidators(new FuncionarioValidador(funcionarioRepositorio));
    }

    @GetMapping
    public ModelAndView home() {
        ModelAndView modelAndView = new ModelAndView("funcionario/home");

        modelAndView.addObject("funcionarios", funcionarioRepositorio.findAll());

        return modelAndView;
    }

    @GetMapping("/{id}")
    public ModelAndView detalhes(@PathVariable Long id) {
        ModelAndView modelAndView = new ModelAndView("funcionario/detalhes");

        modelAndView.addObject("funcionario", funcionarioRepositorio.getOne(id));

        return modelAndView;
    }

    @GetMapping("/cadastrar")
    public ModelAndView cadastrar() {
        ModelAndView modelAndView = new ModelAndView("funcionario/formulario");

        modelAndView.addObject("funcionario", new Funcionario());
        modelAndView.addObject("cargos", cargoRepositorio.findAll());

        return modelAndView;
    }

    @GetMapping("/{id}/editar")
    public ModelAndView editar(@PathVariable Long id) {
        ModelAndView modelAndView = new ModelAndView("funcionario/formulario");

        modelAndView.addObject("funcionario", funcionarioRepositorio.getOne(id));
        modelAndView.addObject("cargos", cargoRepositorio.findAll());

        return modelAndView;
    }

    @PostMapping("/cadastrar")
    public String cadastrar(@Valid Funcionario funcionario, BindingResult resultado, ModelMap model) {

        if (resultado.hasErrors()) {
            model.addAttribute("cargos", cargoRepositorio.findAll());
            return "funcionario/formulario";
        }

        funcionario.setSenha(SenhaUtils.encode(funcionario.getSenha()));
        funcionarioRepositorio.save(funcionario);

        return "redirect:/funcionarios";
    }

    @PostMapping("/{id}/editar")
    public String editar(@Valid Funcionario funcionario, BindingResult result, ModelMap model){
        if (result.hasErrors()){
            model.addAttribute("cargos", cargoRepositorio.findAll());
            return "funcionario/formulario";
        }

        funcionario.setSenha(SenhaUtils.encode(funcionarioRepositorio.getOne(funcionario.getId()).getSenha()));
        funcionarioRepositorio.save(funcionario);

        return "redirect:/funcionarios";
    }

    @GetMapping("/{id}/excluir")
    public String excluir(@PathVariable Long id) {
        funcionarioRepositorio.deleteById(id);

        return "redirect:/funcionarios";
    }
    
}
