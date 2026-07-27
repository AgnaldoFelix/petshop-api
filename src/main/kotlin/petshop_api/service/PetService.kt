import org.springframework.stereotype.Service
import petshop_api.dto.PetRequest
import petshop_api.dto.PetResponse
import petshop_api.entity.Tutor
import petshop_api.mapper.PetMapper
import petshop_api.repository.PetRepository
import petshop_api.repository.TutorRepository
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

    fun deletarPet(id: UUID) {
        if (!petRepository.existsById(id)) {
            throw Exception("Pet com ID $id não encontrado")
        }
        petRepository.deleteById(id)
    }

    fun listarPets(pet: Pet): List<Pet> {
        return petRepository.findAll()
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

    fun buscarPetsPorNome(nome: String): List<Pet> {
        return petRepository.findByName(nome)
    }

}