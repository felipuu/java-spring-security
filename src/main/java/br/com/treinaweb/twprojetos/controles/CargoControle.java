package br.com.treinaweb.twprojetos.controles;

import br.com.treinaweb.twprojetos.dao.AlertDAO;
import br.com.treinaweb.twprojetos.entidades.Cargo;
import br.com.treinaweb.twprojetos.excecoes.CargoPossuiFuncionarioException;
import br.com.treinaweb.twprojetos.servicos.CargoService;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping("/cargos")
public class CargoControle {

    private final CargoService cargoService;

    public CargoControle(CargoService cargoService) {
        this.cargoService = cargoService;
    }

    @GetMapping
    public ModelAndView home() {
        ModelAndView modelAndView = new ModelAndView("cargo/home");

        modelAndView.addObject("cargos", cargoService.buscarTodos());

        return modelAndView;
    }

    @GetMapping("/cadastrar")
    public ModelAndView cadastrar() {
        ModelAndView modelAndView = new ModelAndView("cargo/formulario");

        modelAndView.addObject("cargo", new Cargo());

        return modelAndView;
    }

    @GetMapping("/{id}/editar")
    public ModelAndView editar(@PathVariable Long id) {
        ModelAndView modelAndView = new ModelAndView("cargo/formulario");

        modelAndView.addObject("cargo", cargoService.buscarPorId(id));

        return modelAndView;
    }

    @PostMapping({"/cadastrar", "/{id}/editar"})
    public String salvar(@Valid Cargo cargo,
                         BindingResult resultado,
                         RedirectAttributes attr,
                         @PathVariable(required = false) Long id) {

        if (resultado.hasErrors()){
            return "cargo/formulario";
        }

        if (cargo.getId() == null){
            cargoService.cadastrar(cargo);
            attr.addFlashAttribute("alert", new AlertDAO("Cargo cadastrado com sucesso", "alert-success"));

        }else{
            cargoService.atualizar(cargo,cargo.getId());
            attr.addFlashAttribute("alert", new AlertDAO("Cargo editado com sucesso", "alert-success"));
        }
        return "redirect:/cargos";
    }

    @GetMapping("/{id}/excluir")
    public String excluir(@PathVariable Long id, RedirectAttributes attr) {

        try {
            cargoService.excluirPorId(id);
            attr.addFlashAttribute("alert", new AlertDAO("Cargo excluido com sucesso", "alert-success"));
        }catch (CargoPossuiFuncionarioException e){
            attr.addFlashAttribute("alert", new AlertDAO("Cargo nao pode ser excluido", "alert-danger"));
        }
        return "redirect:/cargos";
    }
    
}
