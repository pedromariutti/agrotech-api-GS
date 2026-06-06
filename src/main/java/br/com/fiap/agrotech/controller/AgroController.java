package br.com.fiap.agrotech.controller;

import br.com.fiap.agrotech.dto.PrevisaoSateliteDto;
import br.com.fiap.agrotech.dto.RegistroSoloDto;
import br.com.fiap.agrotech.model.PrevisaoSatelite;
import br.com.fiap.agrotech.model.RegistroSolo;
import br.com.fiap.agrotech.service.AgroInteligenciaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/agro")
@RequiredArgsConstructor
@Tag(name = "Agricultura de Precisão", description = "CRUD completo para telemetria de solo e dados orbitais")
public class AgroController {

    private final AgroInteligenciaService inteligenciaService;


    @PostMapping("/solo")
    @Operation(summary = "C - Cadastrar leitura de solo", description = "Recebe dados do ESP32 e avalia regras de irrigação")
    public ResponseEntity<String> criarLeituraSolo(@Valid @RequestBody RegistroSoloDto dto) {
        String resultado = inteligenciaService.salvarRegistroSolo(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(resultado);
    }

    @GetMapping("/solo")
    @Operation(summary = "R - Listar todas as leituras", description = "Retorna o histórico completo do banco Oracle")
    public ResponseEntity<List<RegistroSolo>> obterTodasLeiturasSolo() {
        return ResponseEntity.ok(inteligenciaService.listarTodosRegistrosSolo());
    }

    @GetMapping("/solo/{id}")
    @Operation(summary = "R - Buscar leitura por ID")
    public ResponseEntity<RegistroSolo> obterLeituraSoloPorId(@PathVariable Long id) {
        return inteligenciaService.buscarRegistroSoloPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/solo/{id}")
    @Operation(summary = "U - Atualizar leitura por ID")
    public ResponseEntity<RegistroSolo> modificarLeituraSolo(@PathVariable Long id, @Valid @RequestBody RegistroSoloDto dto) {
        return inteligenciaService.atualizarRegistroSolo(id, dto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/solo/{id}")
    @Operation(summary = "D - Deletar leitura por ID")
    public ResponseEntity<Void> removerLeituraSolo(@PathVariable Long id) {
        if (inteligenciaService.deletarRegistroSolo(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }


    @PostMapping("/satelite")
    @Operation(summary = "C - Cadastrar previsão de satélite", description = "Alimenta a API com dados climáticos da NASA/ESA")
    public ResponseEntity<PrevisaoSatelite> criarPrevisao(@Valid @RequestBody PrevisaoSateliteDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(inteligenciaService.salvarPrevisao(dto));
    }

    @GetMapping("/satelite")
    @Operation(summary = "R - Listar todas as previsões orbitais")
    public ResponseEntity<List<PrevisaoSatelite>> obterTodasPrevisoes() {
        return ResponseEntity.ok(inteligenciaService.listarTodasPrevisoes());
    }

    @GetMapping("/satelite/{id}")
    @Operation(summary = "R - Buscar previsão orbital por ID")
    public ResponseEntity<PrevisaoSatelite> obterPrevisaoPorId(@PathVariable Long id) {
        return inteligenciaService.buscarPrevisaoPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/satelite/{id}")
    @Operation(summary = "U - Atualizar previsão por ID")
    public ResponseEntity<PrevisaoSatelite> modificarPrevisao(@PathVariable Long id, @Valid @RequestBody PrevisaoSateliteDto dto) {
        return inteligenciaService.atualizarPrevisao(id, dto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/satelite/{id}")
    @Operation(summary = "D - Deletar previsão por ID")
    public ResponseEntity<Void> removerPrevisao(@PathVariable Long id) {
        if (inteligenciaService.deletarPrevisao(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}