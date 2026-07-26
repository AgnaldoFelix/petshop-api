import org.springframework.stereotype.Service
import petshop_api.entity.Tutor
import petshop_api.repository.PetRepository
import petshop_api.repository.TutorRepository
import java.util.UUID

@Service
class PetService(
    private val petRepository: PetRepository,
    private val tutorRepository: TutorRepository
) {
    fun adicionarPet(pet: Pet): Pet {
        val petExists = petRepository.findByName(pet.nome.toString())

        if (petExists == null) {
            return petRepository.save(pet)
        }
        return throw Exception("Pet já cadastrado no sistema")
    }

    fun deletarPet(id: UUID) {
        if (!petRepository.existsById(id)) {
            throw Exception("Pet não encontrado")
        }
        petRepository.deleteById(id)
    }

    fun listarPets(pet: Pet): List<Pet> {
        return petRepository.findAll()
    }

    fun atualizarPet(id: UUID, novosDados: Pet): Pet {
        val petExistente = petRepository.findById(id)
            .orElseThrow { Exception("Pet não encontrado") }

        petExistente.nome = novosDados.nome
        petExistente.idade = novosDados.idade
        petExistente.especie = novosDados.especie

        return petRepository.save(petExistente)
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