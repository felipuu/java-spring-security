package br.com.treinaweb.twprojetos.servicos;

import br.com.treinaweb.twprojetos.entidades.Cargo;
import br.com.treinaweb.twprojetos.excecoes.CargoNaoEncontradoException;
import br.com.treinaweb.twprojetos.excecoes.CargoPossuiFuncionarioException;
import br.com.treinaweb.twprojetos.repositorios.CargoRepositorio;
import br.com.treinaweb.twprojetos.repositorios.FuncionarioRepositorio;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CargoService {

    private final CargoRepositorio cargoRepositorio;
    private final FuncionarioRepositorio funcionarioRepositorio;

    public CargoService(CargoRepositorio cargoRepositorio, FuncionarioRepositorio funcionarioRepositorio) {
        this.cargoRepositorio = cargoRepositorio;
        this.funcionarioRepositorio = funcionarioRepositorio;
    }

    public List<Cargo> buscarTodos(){
        return cargoRepositorio.findAll();
    }

    public Cargo buscarPorId(Long id){

        return cargoRepositorio.findById(id)
                .orElseThrow(() -> new CargoNaoEncontradoException(id));
    }

    public Cargo cadastrar(Cargo cargo){
        return cargoRepositorio.save(cargo);
    }

    public Cargo atualizar(Cargo cargo, Long id){
        buscarPorId(id);

        return cargoRepositorio.save(cargo);
    }

    public void excluirPorId(Long id){
        Cargo cargo = buscarPorId(id);

        if (funcionarioRepositorio.findByCargo(cargo).isEmpty()){
            cargoRepositorio.delete(cargo);
        }else {
            throw new CargoPossuiFuncionarioException(id);
        }
    }
}
