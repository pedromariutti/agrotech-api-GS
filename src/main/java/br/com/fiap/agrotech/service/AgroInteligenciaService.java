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

    // CREATE (Solo)
    public String salvarRegistroSolo(RegistroSoloDto dto) {
        RegistroSolo registro = new RegistroSolo(
                null,
                dto.umidade(),
                dto.temperatura(),
                LocalDateTime.now(),
                dto.dispositivoId()
        );
        soloRepository.save(registro);

        Optional<PrevisaoSatelite> previsaoOpt = sateliteRepository.findFirstByRegiaoOrderByDataPrevisaoDesc("Setor_A_Principal");
        if (previsaoOpt.isPresent() && previsaoOpt.get().getChuvaIminente() && dto.umidade() < 40.0) {
            return "REGA BLOQUEADA AUTOMATICAMENTE. Chuva iminente detectada por satelite.";
        }
        return "Leitura registrada. Condicoes normais para o cultivo.";
    }

    // READ ALL (Solo)
    public List<RegistroSolo> listarTodosRegistrosSolo() {
        return soloRepository.findAll();
    }

    // READ BY ID (Solo)
    public Optional<RegistroSolo> buscarRegistroSoloPorId(Long id) {
        return soloRepository.findById(id);
    }

    // UPDATE (Solo)
    public Optional<RegistroSolo> atualizarRegistroSolo(Long id, RegistroSoloDto dto) {
        return soloRepository.findById(id).map(registroExistente -> {
            registroExistente.setUmidade(dto.umidade());
            registroExistente.setTemperatura(dto.temperatura());
            registroExistente.setDispositivoId(dto.dispositivoId());
            return soloRepository.save(registroExistente);
        });
    }

    // DELETE (Solo)
    public boolean deletarRegistroSolo(Long id) {
        if (soloRepository.existsById(id)) {
            soloRepository.deleteById(id);
            return true;
        }
        return false;
    }

    // CREATE (Satelite)
    public PrevisaoSatelite salvarPrevisao(PrevisaoSateliteDto dto) {
        PrevisaoSatelite novaPrevisao = new PrevisaoSatelite(
                null,
                dto.regiao(),
                dto.chuvaIminente(),
                LocalDate.now()
        );
        return sateliteRepository.save(novaPrevisao);
    }

    // READ ALL (Satelite)
    public List<PrevisaoSatelite> listarTodasPrevisoes() {
        return sateliteRepository.findAll();
    }

    // READ BY ID (Satelite)
    public Optional<PrevisaoSatelite> buscarPrevisaoPorId(Long id) {
        return sateliteRepository.findById(id);
    }

    // UPDATE (Satelite)
    public Optional<PrevisaoSatelite> atualizarPrevisao(Long id, PrevisaoSateliteDto dto) {
        return sateliteRepository.findById(id).map(previsaoExistente -> {
            previsaoExistente.setRegiao(dto.regiao());
            previsaoExistente.setChuvaIminente(dto.chuvaIminente());
            return sateliteRepository.save(previsaoExistente);
        });
    }

    // DELETE (Satelite)
    public boolean deletarPrevisao(Long id) {
        if (sateliteRepository.existsById(id)) {
            sateliteRepository.deleteById(id);
            return true;
        }
        return false;
    }
}