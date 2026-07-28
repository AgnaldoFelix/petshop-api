package petshop_api.controller

import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import petshop_api.dto.AtendimentoServicoRequest
import petshop_api.dto.AtendimentoServicoResponse
import petshop_api.service.AtendimentoServicoService
import java.util.UUID

@RestController
@RequestMapping("/api/atendimento-servicos")
class AtendimentoServicoController(
    private val atendimentoServicoService: AtendimentoServicoService
) {

    @PostMapping("/vincular")
    fun vincularServico(
        @Valid @RequestBody request: AtendimentoServicoRequest
    ): ResponseEntity<AtendimentoServicoResponse> {
        val response = atendimentoServicoService.vincularServico(request)
        return ResponseEntity.status(HttpStatus.CREATED).body(response)
    }

    @GetMapping("/atendimento/{atendimentoId}")
    fun listarPorAtendimento(
        @PathVariable atendimentoId: UUID
    ): ResponseEntity<List<AtendimentoServicoResponse>> {
        val response = atendimentoServicoService.listarPorAtendimento(atendimentoId)
        return ResponseEntity.ok(response)
    }

    @DeleteMapping("/{id}")
    fun removerServicoAtendimento(
        @PathVariable id: UUID
    ): ResponseEntity<Void> {
        atendimentoServicoService.removerServicoAtendimento(id)
        return ResponseEntity.noContent().build()
    }
}