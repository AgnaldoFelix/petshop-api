import jakarta.transaction.Transactional
import org.springframework.stereotype.Service
import petshop_api.dto.PetRequest
import petshop_api.dto.PetResponse
import petshop_api.entity.Tutor
import petshop_api.mapper.PetMapper
import petshop_api.repository.PetRepository
import petshop_api.repository.TutorRepository
import java.util.ArrayList
import java.util.UUID

@Service
class PetService(
    private val petRepository: PetRepository,
    private val tutorRepository: TutorRepository
) {
    fun adicionarPet(request: PetRequest): PetResponse {
        // 1. Verifica se já existe um pet com mesmo nome para o mesmo tutor
        val petExists = petRepository.findByNomeAndTutorId(request.nome, request.tutorId)

        // 2. Se existir, lança exceção
        if (petExists != null) {
            throw Exception("Pet já cadastrado para este tutor")
        }

        // 3. Converte Request para Entity
        val pet = PetMapper.toEntity(request)

        // 4. Salva no banco
        val petSalvo = petRepository.save(pet)

        // 5. Busca o tutor para pegar o nome
        val tutor = tutorRepository.findById(request.tutorId)
            .orElseThrow { Exception("Tutor não encontrado") }

        // 6. Converte Entity para Response
        return PetMapper.toResponse(petSalvo, tutor.nome)
    }

    @Transactional
    fun deletarPet(id: UUID) {
        if (!petRepository.existsById(id)) {
            throw Exception("Pet com ID $id não encontrado")
        }
        petRepository.deleteById(id)
    }

    fun listarPets(): List<PetResponse> {
        val pets = petRepository.findAll()
        val resposta = ArrayList<PetResponse>()

        for (pet in pets) {
            val tutor = tutorRepository.findById(pet.tutor_id)
                .orElseThrow { Exception("Tutor não encontrado") }
            resposta.add(PetMapper.toResponse(pet, tutor.nome))
        }

        return resposta
    }

    fun atualizarPet(id: UUID, novosDados: PetRequest): PetResponse {
        val petExistente = petRepository.findById(id)
            .orElseThrow { Exception("Pet não encontrado") }

        petExistente.nome = novosDados.nome
        petExistente.idade = novosDados.idade.toString()
        petExistente.especie = novosDados.especie
        petExistente.data_nascimento = novosDados.dataNascimento

        val petAtualizado = petRepository.save(petExistente)

        val tutor = tutorRepository.findById(petAtualizado.tutor_id)
            .orElseThrow { Exception("Tutor não encontrado") }

        return PetMapper.toResponse(
            petAtualizado,
            tutor.nome,

        )
    }

    fun listarPetsPorTutor(tutorId: UUID): List<Pet> {

        val tutorExiste = tutorRepository.existsById(tutorId)
        if (!tutorExiste) {
            throw Exception("Tutor não encontrado")
        }

        return petRepository.findByTutorId(tutorId)
    }

    fun buscarPetsPorNome(nome: String): List<PetResponse> {
        val pets = petRepository.findByName(nome)

        if (pets.isEmpty()) {
            throw Exception("Nenhum pet encontrado com o nome: $nome")

        }

        val response = ArrayList<PetResponse>()

        for (pet in pets) {
            // Busca o tutor do pet
            val tutor = tutorRepository.findById(pet.tutor_id)
                .orElseThrow { Exception("Tutor não encontrado") }

            // Converte pet + tutor para response
            val petResponse = PetMapper.toResponse(pet, tutor.nome)

            // Adiciona na lista
            response.add(petResponse)
        }

        return response
    }

}





























