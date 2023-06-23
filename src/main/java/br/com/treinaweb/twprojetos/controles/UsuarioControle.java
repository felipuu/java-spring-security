package br.com.treinaweb.twprojetos.controles;


import br.com.treinaweb.twprojetos.dao.AlterarSenhaDAO;
import br.com.treinaweb.twprojetos.repositorios.FuncionarioRepositorio;
import br.com.treinaweb.twprojetos.utils.SenhaUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;

@Controller
public class UsuarioControle {

    private final FuncionarioRepositorio funcionarioRepositorio;
    public UsuarioControle(FuncionarioRepositorio funcionarioRepositorio){
        this.funcionarioRepositorio = funcionarioRepositorio;
    }

    @GetMapping("/login")
    public String login(){
        return "usuario/login";
    }

    @GetMapping("/perfil")
    public ModelAndView perfil(Principal principal){
        ModelAndView modelAndView = new ModelAndView("usuario/perfil");

        var usuario = funcionarioRepositorio.findByEmail(principal.getName()).get();

        modelAndView.addObject("usuario", usuario);
        modelAndView.addObject("alterarSenhaForm", new AlterarSenhaDAO());

        return modelAndView;
    }

    @PostMapping("/alterar-senha")
    public String alterarSenha(AlterarSenhaDAO form, Principal principal){
        var usuario = funcionarioRepositorio.findByEmail(principal.getName()).get();

        if (SenhaUtils.matchs(form.getSenhaAtual(), usuario.getSenha())){
            usuario.setSenha(SenhaUtils.encode(form.getNovaSenha()));
            funcionarioRepositorio.save(usuario);
        }
        return "redirect:/perfil";
    }

    @GetMapping("senha/{senha}")
    public String gerar(@PathVariable String senha){
        var funcionario = funcionarioRepositorio.findById(1L);
        funcionario.get().setSenha(new BCryptPasswordEncoder().encode(senha));
        funcionarioRepositorio.save(funcionario.get());
        return "usuario/login";
    }
}
