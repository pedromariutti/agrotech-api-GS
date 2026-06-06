package br.com.fiap.agrotech.service;

import br.com.fiap.agrotech.dto.PrevisaoSateliteDto;
import br.com.fiap.agrotech.dto.RegistroSoloDto;
import br.com.fiap.agrotech.model.PrevisaoSatelite;
import br.com.fiap.agrotech.model.RegistroSolo;
import br.com.fiap.agrotech.repository.PrevisaoSateliteRepository;
import br.com.fiap.agrotech.repository.RegistroSoloRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AgroInteligenciaService {

    private final RegistroSoloRepository soloRepository;
    private final PrevisaoSateliteRepository sateliteRepository;

    public String salvarRegistroSolo(RegistroSoloDto dto) {
        RegistroSolo registro = new RegistroSolo(
                null,
                dto.getUmidade(),
                dto.getTemperatura(),
                LocalDateTime.now(),
                dto.getDispositivoId()
        );
        soloRepository.save(registro);

        Optional<PrevisaoSatelite> previsaoOpt = sateliteRepository.findFirstByRegiaoOrderByDataPrevisaoDesc("Setor_A_Principal");
        if (previsaoOpt.isPresent() && dto.getUmidade() < 40.0 && previsaoOpt.get().getChuvaIminente()) {
            return "REGA BLOQUEADA AUTOMATICAMENTE. Chuva iminente detectada por satélite.";
        }
        return dto.getUmidade() < 40.0 ? "SISTEMA DE IRRIGAÇÃO ATIVADO." : "SISTEMA EM ESPERA.";
    }

    // READ (Todos)
    public List<RegistroSolo> listarTodosRegistrosSolo() {
        return soloRepository.findAll();
    }

    // READ (Por ID)
    public Optional<RegistroSolo> buscarRegistroSoloPorId(Long id) {
        return soloRepository.findById(id);
    }

    // UPDATE
    public Optional<RegistroSolo> atualizarRegistroSolo(Long id, RegistroSoloDto dto) {
        return soloRepository.findById(id).map(registroExistente -> {
            registroExistente.setUmidade(dto.getUmidade());
            registroExistente.setTemperatura(dto.getTemperatura());
            registroExistente.setDispositivoId(dto.getDispositivoId());
            return soloRepository.save(registroExistente);
        });
    }

    // DELETE
    public boolean deletarRegistroSolo(Long id) {
        if (soloRepository.existsById(id)) {
            soloRepository.deleteById(id);
            return true;
        }
        return false;
    }


    // CREATE
    public PrevisaoSatelite salvarPrevisao(PrevisaoSateliteDto dto) {
        PrevisaoSatelite novaPrevisao = new PrevisaoSatelite(
                null,
                dto.getRegiao(),
                dto.getChuvaIminente(),
                LocalDate.now()
        );
        return sateliteRepository.save(novaPrevisao);
    }

    // READ (Todos)
    public List<PrevisaoSatelite> listarTodasPrevisoes() {
        return sateliteRepository.findAll();
    }

    // READ (Por ID)
    public Optional<PrevisaoSatelite> buscarPrevisaoPorId(Long id) {
        return sateliteRepository.findById(id);
    }

    // UPDATE
    public Optional<PrevisaoSatelite> atualizarPrevisao(Long id, PrevisaoSateliteDto dto) {
        return sateliteRepository.findById(id).map(previsaoExistente -> {
            previsaoExistente.setRegiao(dto.getRegiao());
            previsaoExistente.setChuvaIminente(dto.getChuvaIminente());
            return sateliteRepository.save(previsaoExistente);
        });
    }

    // DELETE
    public boolean deletarPrevisao(Long id) {
        if (sateliteRepository.existsById(id)) {
            sateliteRepository.deleteById(id);
            return true;
        }
        return false;
    }
}