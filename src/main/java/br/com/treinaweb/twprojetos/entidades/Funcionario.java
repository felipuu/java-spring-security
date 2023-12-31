package br.com.treinaweb.twprojetos.entidades;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import java.time.LocalDate;
import java.util.List;

@Entity
public class Funcionario extends Pessoa {

    @NotNull
    @PastOrPresent
    @Column(name = "data_admissao", nullable = false)
    @DateTimeFormat(iso = ISO.DATE)
    private LocalDate dataAdmissao;

    @PastOrPresent
    @Column(name = "data_demissao")
    @DateTimeFormat(iso = ISO.DATE)
    private LocalDate dataDemissao;

    @Valid
    @NotNull
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "cargo_id_fk", nullable = false)
    private Cargo cargo;

    @ManyToMany(mappedBy = "equipe", fetch = FetchType.LAZY)
    private List<Projeto> projetos;

    @Column(nullable = false)
    private String senha;

    public String getSenha() {
        return senha;
    }
    public Funcionario setSenha(String senha) {
        this.senha = senha;
        return this;
    }
    public LocalDate getDataAdmissao() {
        return dataAdmissao;
    }
    public void setDataAdmissao(LocalDate dataAdmissao) {
        this.dataAdmissao = dataAdmissao;
    }
    public LocalDate getDataDemissao() {
        return dataDemissao;
    }
    public void setDataDemissao(LocalDate dataDemissao) {
        this.dataDemissao = dataDemissao;
    }

    public Cargo getCargo() {
        return cargo;
    }

    public void setCargo(Cargo cargo) {
        this.cargo = cargo;
    }

    public List<Projeto> getProjetos() {
        return projetos;
    }

    public void setProjetos(List<Projeto> projetos) {
        this.projetos = projetos;
    }
}
