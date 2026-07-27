package petshop_api.controller

import StatusAtendimento
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import petshop_api.dto.AtendimentoHistoricoResponse
import petshop_api.entity.Atendimento
import petshop_api.repository.PetRepository
import java.time.LocalDateTime
import java.util.UUID

@RestController
@RequestMapping("/api/historico")
class HistoricoController(
    private val atendimentoService: `AtendimentoService.kt`,
    private val petRepository: PetRepository
) {

    @GetMapping("/pet/{petId}")
    fun buscarHistoricoPet(@PathVariable petId: UUID): ResponseEntity<List<AtendimentoHistoricoResponse>> {
        val historico = atendimentoService.buscarHistoricoPet(petId)
        val response = historico.map { toResponse(it) }
        return ResponseEntity.ok(response)
    }

    @GetMapping("/periodo")
    fun buscarHistoricoPeriodo(
        @RequestParam dataInicio: LocalDateTime,
        @RequestParam dataFim: LocalDateTime
    ): ResponseEntity<List<AtendimentoHistoricoResponse>> {
        val historico = atendimentoService.buscarHistoricoPorPeriodo(dataInicio, dataFim)
        val response = historico.map { toResponse(it) }
        return ResponseEntity.ok(response)
    }

    @GetMapping("/status/{status}")
    fun buscarHistoricoStatus(
        @PathVariable status: StatusAtendimento
    ): ResponseEntity<List<AtendimentoHistoricoResponse>> {
        val historico = atendimentoService.buscarHistoricoPorStatus(status)
        val response = historico.map { toResponse(it) }
        return ResponseEntity.ok(response)
    }

    @GetMapping("/emergencias")
    fun buscarHistoricoEmergencias(): ResponseEntity<List<AtendimentoHistoricoResponse>> {
        val historico = atendimentoService.buscarHistoricoEmergencias()
        val response = historico.map { toResponse(it) }
        return ResponseEntity.ok(response)
    }

    @GetMapping("/resumo/{petId}")
    fun buscarResumoHistorico(@PathVariable petId: UUID): ResponseEntity<Map<String, Any>> {
        val total = atendimentoService.contarAtendimentosPorPet(petId)
        val finalizados = atendimentoService.buscarHistoricoPet(petId)
            .count { it.status == StatusAtendimento.FINALIZADO }

        return ResponseEntity.ok(mapOf(
            "petId" to petId,
            "totalAtendimentos" to total,
            "finalizados" to finalizados,
            "cancelados" to (total - finalizados)
        ))
    }

    // 🔧 Método auxiliar para converter
    private fun toResponse(atendimento: Atendimento): AtendimentoHistoricoResponse {
        val pet = petRepository.findById(atendimento.petId).orElse(null)

        val duracao = if (atendimento.dataFinalizacao != null && atendimento.dataAtendimento != null) {
            val diff = java.time.Duration.between(atendimento.dataAtendimento, atendimento.dataFinalizacao)
            "${diff.toHours()}h ${diff.toMinutes() % 60}min"
        } else null

        return AtendimentoHistoricoResponse(
            id = atendimento.id,
            petId = atendimento.petId,
            petNome = pet?.nome ?: "Pet não encontrado",
            dataAtendimento = atendimento.dataAtendimento,
            nivelEmergencia = atendimento.nivelEmergencia?.toString(),
            observacao = atendimento.observacao,
            decisao = atendimento.decisao,
            status = atendimento.status.toString(),
            dataFinalizacao = atendimento.dataFinalizacao,
            dataCancelamento = atendimento.dataCancelamento,
            motivoCancelamento = atendimento.motivoCancelamento,
            dataCriacao = atendimento.createdAt,
            duracaoAtendimento = duracao
        )
    }
}