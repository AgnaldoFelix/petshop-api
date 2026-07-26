package petshop_api.service

import org.springframework.stereotype.Service
import petshop_api.entity.Atendimento
import petshop_api.enums.NivelEmergencia
import petshop_api.repository.AtendimentoRepository
import petshop_api.repository.PetRepository
import java.time.LocalDate
import java.util.UUID

@Service
class AtendimentoService(
    private val atendimentoRepository: AtendimentoRepository,
    private val petRepository: PetRepository

) {

    fun realizarAtendimento(petId: UUID, atendimento: Atendimento): Atendimento {
        // 1. Garante que o Pet existe no banco de dados
        val petExistente = petRepository.findById(petId)
            .orElseThrow { Exception("Pet não encontrado") }

        // 2. Define a decisão/serviço com base no nível de emergência
        val decisaoTomada = when (atendimento.nivelEmergencia.toString()) {
            "BAIXO" -> "Banho e tosa"
            "MEDIO", "MÉDIO" -> "Tosa e acompanhamento clínico"
            "ALTO" -> "Direto para o atendimento clínico"
            else -> throw IllegalArgumentException("Nível de emergência inválido")
        }

        // 3. Cria a nova instância de Atendimento com o pet_id validado e a decisão atribuída
        val novoAtendimento = Atendimento(
            petId = petExistente.id,
            dataAtendimento = atendimento.dataAtendimento,
            nivelEmergencia = atendimento.nivelEmergencia,
            observacao = atendimento.observacao,
            decisao = decisaoTomada,
            finalizado = atendimento.finalizado
        )

        // 4. Salva no banco e retorna
        return atendimentoRepository.save(novoAtendimento)
    }

    fun listarAtendimentos(): List<Atendimento> {
        return atendimentoRepository.findAll()
    }

    fun finalizarAtendimento(atendimentoId: UUID): Atendimento {
        val atendimento = atendimentoRepository.findById(atendimentoId)
            .orElseThrow { Exception("Atendimento não encontrado") }

        atendimento.finalizado = true
        atendimento.dataFinalizacao = LocalDate.now()

        return atendimentoRepository.save(atendimento)
    }

    fun registrarEmergencia(petId: UUID, descricao: String): Atendimento {
        // 1. Valida se o pet existe
        val pet = petRepository.findById(petId)
            .orElseThrow { Exception("Pet não encontrado") }

        // 2. Instancia o Atendimento com os dados padrão de Emergência
        val atendimentoEmergencia = Atendimento(
            petId = pet.id,
            nivelEmergencia = NivelEmergencia.ALTO, // ou "ALTO" se o seu campo for String
            observacao = "EMERGÊNCIA: $descricao",
            decisao = "Direto para o atendimento clínico",
            finalizado = false,
            dataAtendimento = LocalDate.now(),
        )

        // 3. Persiste e retorna
        return atendimentoRepository.save(atendimentoEmergencia)
    }
}



