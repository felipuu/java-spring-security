package br.com.treinaweb.twprojetos.excecoes;

public class CargoPossuiFuncionarioException  extends RuntimeException{
    public CargoPossuiFuncionarioException(Long id) {
        super(String.format("Cargo ID %s possui funcionarios cadastrados", id));
    }
}
