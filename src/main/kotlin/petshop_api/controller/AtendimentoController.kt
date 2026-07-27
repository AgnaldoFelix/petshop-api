package petshop_api.controller

import jakarta.validation.Valid
import org.apache.coyote.Response
import org.springframework.boot.context.properties.bind.Bindable.mapOf
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
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
        @Valid
        @RequestBody request: AtendimentoRequest
    ): ResponseEntity<AtendimentoResponse> {

        val response = atendimentoService.realizarAtendimento(request)

        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(response)
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

    @PostMapping("/{id}/inciar")
    fun iniciarAtendimento(
        @PathVariable id: UUID
    ): ResponseEntity<AtendimentoResponse> {

        val response = atendimentoService.iniciarAtendimento(id)

        return ResponseEntity.ok(response)
    }

    @PatchMapping("/{id}/cancelar")
    fun cancelarAtendimento(
        id: UUID, motivo: String
    ): ResponseEntity<AtendimentoResponse> {
        val response = atendimentoService.cancelarAtendimento(id, motivo)

        return ResponseEntity.ok(response)
    }

    @PatchMapping("/{id}/finalizar")
    fun finalizarAtendimento(
        id: UUID, observacaoFinal: String
    ): ResponseEntity<AtendimentoResponse> {
        val response = atendimentoService.finalizarAtendimento(id, observacaoFinal)

        return ResponseEntity.ok(response)
    }

    @PostMapping("/{id}/register/emergencia")
    fun registrarEmergencia(
        id: UUID, descricao: String
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
        @RequestParam inicio: LocalDate,
        @RequestParam fim: LocalDate
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