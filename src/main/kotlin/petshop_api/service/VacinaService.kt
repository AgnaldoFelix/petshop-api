package petshop_api.service

import petshop_api.entity.Vacina
import petshop_api.repository.VacinaRepository
import java.util.UUID

class VacinaService (
    private val vacinaRepository: VacinaRepository
){
    fun adicionarVacina(vacina: Vacina): Vacina {
        val vacinaExists = vacinaRepository.findById(vacina.id)

        if (vacinaExists !== null ) {
            throw Exception("Vacina já cadastrada")
        }

        return vacinaRepository.save(vacina)
    }


    fun listarVacinas() {
        vacinaRepository.findAll()
    }

    fun deletarVacina(vacina: Vacina) {
        if (vacinaRepository.existsById(vacina.id)) {
            vacinaRepository.deleteById(vacina.id)
        }
        return throw Exception("Vacina não exencontrada")
    }


    fun atualizarVacina(id: UUID, novosDados: Vacina): Vacina {
        val vacinaExistente = vacinaRepository.findById(id)
            .orElseThrow { Exception("Vacina não encontrada") }

        vacinaExistente.nome = novosDados.nome
        vacinaExistente.descricao = novosDados.descricao

        return vacinaRepository.save(vacinaExistente)
    }

}