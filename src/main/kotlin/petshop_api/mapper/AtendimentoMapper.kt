package petshop_api.mapper

import petshop_api.dto.AtendimentoHistoricoResponse
import petshop_api.dto.AtendimentoRequest
import petshop_api.dto.AtendimentoResponse
import petshop_api.entity.Atendimento
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit

object AtendimentoMapper {

    fun toEntity(request: AtendimentoRequest): Atendimento {
        return Atendimento(
            petId = request.petId,
            dataAtendimento = request.dataAtendimento ?: LocalDate.now(),
            nivelEmergencia = request.nivelEmergencia,
            observacao = request.observacao,
            decisao = "",
            status = StatusAtendimento.AGUARDANDO.toString(),
            finalizado = false,
            dataFinalizacao = null,
            dataCancelamento = null,
            motivoCancelamento = null,
            createdAt = LocalDateTime.now(),
            updatedAt = LocalDateTime.now()
        )
    }

    fun toResponse(atendimento: Atendimento, petNome: String): AtendimentoResponse {
        return AtendimentoResponse(
            id = atendimento.id,
            petId = atendimento.petId,
            petNome = petNome,
            dataAtendimento = atendimento.dataAtendimento,
            nivelEmergencia = atendimento.nivelEmergencia?.toString(),
            observacao = atendimento.observacao,
            decisao = atendimento.decisao,
            status = atendimento.status.toString(),
            finalizado = atendimento.finalizado,
            dataFinalizacao = atendimento.dataFinalizacao,
            createdAt = atendimento.createdAt
        )
    }

    fun toHistoricoResponse(atendimento: Atendimento, petNome: String): AtendimentoHistoricoResponse {
        return AtendimentoHistoricoResponse(
            id = atendimento.id,
            petNome = petNome,
            dataAtendimento = atendimento.dataAtendimento,
            nivelEmergencia = atendimento.nivelEmergencia?.toString(),
            decisao = atendimento.decisao,
            status = atendimento.status.toString(),
            dataFinalizacao = atendimento.dataFinalizacao,
            duracao = calcularDuracao(atendimento)
        )
    }

    private fun calcularDuracao(atendimento: Atendimento): String? {
        if (!atendimento.finalizado || atendimento.dataFinalizacao == null) {
            return null
        }

        val inicio = atendimento.dataAtendimento.atStartOfDay()
        val fim = atendimento.dataFinalizacao?.atStartOfDay()

        val dias = ChronoUnit.DAYS.between(inicio, fim)

        return when {
            dias == 0L -> "Menos de 1 dia"
            dias == 1L -> "1 dia"
            else -> "$dias dias"
        }
    }
}