package petshop_api.controller

import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import petshop_api.dto.AtendimentoRequest
import petshop_api.dto.AtendimentoResponse
import petshop_api.service.AtendimentoService
import java.time.LocalDate
import java.util.UUID

@RestController
@RequestMapping("/api/atendimentos")
class AtendimentoController(
    private val atendimentoService: AtendimentoService
) {

    @PostMapping
    fun criarAtendimento(
        @Valid @RequestBody request: AtendimentoRequest
    ): ResponseEntity<AtendimentoResponse> {
        val response = atendimentoService.realizarAtendimento(request)
        return ResponseEntity.status(HttpStatus.CREATED).body(response)
    }

    @GetMapping
    fun listarAtendimentos(): ResponseEntity<List<AtendimentoResponse>> {
        val response = atendimentoService.listarAtendimentos()
        return ResponseEntity.ok(response)
    }

    @GetMapping("/{id}")
    fun buscarPorId(
        @PathVariable id: UUID
    ): ResponseEntity<AtendimentoResponse> {
        val response = atendimentoService.buscarAtendimentoPorId(id)
        return ResponseEntity.ok(response)
    }

    @PostMapping("/{id}/iniciar")
    fun iniciarAtendimento(
        @PathVariable id: UUID
    ): ResponseEntity<AtendimentoResponse> {
        val response = atendimentoService.iniciarAtendimento(id)
        return ResponseEntity.ok(response)
    }

    @PatchMapping("/{id}/cancelar")
    fun cancelarAtendimento(
        @PathVariable id: UUID,
        @RequestParam motivo: String
    ): ResponseEntity<AtendimentoResponse> {
        val response = atendimentoService.cancelarAtendimento(id, motivo)
        return ResponseEntity.ok(response)
    }

    @PatchMapping("/{id}/finalizar")
    fun finalizarAtendimento(
        @PathVariable id: UUID,
        @RequestParam(name = "observacaoFinal", required = false) observacaoFinal: String?
    ): ResponseEntity<AtendimentoResponse> {
        val response = atendimentoService.finalizarAtendimento(id, observacaoFinal)
        return ResponseEntity.ok(response)
    }

    @PostMapping("/{id}/emergencia")
    fun registrarEmergencia(
        @PathVariable id: UUID,
        @RequestParam descricao: String
    ): ResponseEntity<AtendimentoResponse> {
        val response = atendimentoService.registrarEmergencia(id, descricao)
        return ResponseEntity.ok(response)
    }

    @GetMapping("/historico/pet/{petId}")
    fun buscarHistoricoPorPet(
        @PathVariable petId: UUID
    ): ResponseEntity<List<AtendimentoResponse>> {
        val historico = atendimentoService.buscarHistoricoPorPet(petId)
        return ResponseEntity.ok(historico)
    }

    @GetMapping("/historico/periodo")
    fun buscarHistoricoPorPeriodo(
        @RequestParam(name = "inicio") inicio: LocalDate,
        @RequestParam(name = "fim") fim: LocalDate
    ): ResponseEntity<List<AtendimentoResponse>> {
        val historico = atendimentoService.buscarHistoricoPorPeriodo(inicio, fim)
        return ResponseEntity.ok(historico)
    }

    @GetMapping("/historico/emergencias")
    fun buscarHistoricoEmergencias(): ResponseEntity<List<AtendimentoResponse>> {
        val emergencias = atendimentoService.buscarHistoricoEmergencias()
        return ResponseEntity.ok(emergencias)
    }

    @GetMapping("/ultimos")
    fun buscarUltimosAtendimentos(
        @RequestParam(defaultValue = "10") limite: Int
    ): ResponseEntity<List<AtendimentoResponse>> {
        val ultimos = atendimentoService.buscarUltimosAtendimentos(limite)
        return ResponseEntity.ok(ultimos)
    }
}