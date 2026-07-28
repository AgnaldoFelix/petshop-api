package petshop_api.controller

import petshop_api.service.PetService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import petshop_api.dto.PetRequest
import petshop_api.dto.PetResponse
import java.util.UUID


@RestController
@RequestMapping("/api/pet")
class PetController (
    private val petService: PetService
) {
    @PostMapping("/adicionar")
    fun adicionarPet(
    @RequestBody request: PetRequest
    ): ResponseEntity<PetResponse> {
        val response = petService.adicionarPet(request)

        return ResponseEntity.status(HttpStatus.CREATED).body(response)
    }

    @DeleteMapping("/{id}")
    fun deletarPet(
        @PathVariable id: UUID
    ): ResponseEntity<Void> {
        petService.deletarPet(id)
        return ResponseEntity.noContent().build()
    }

    @GetMapping("/buscar")
    fun listarPets(
    ): ResponseEntity<List<PetResponse>> {
        val response = petService.listarPets()
        return ResponseEntity.ok(response)
    }

    @PutMapping("/editar/{id}")
    fun atualizarPet(
        @PathVariable id: UUID, request: PetRequest
    ): ResponseEntity<PetResponse> {
        val response = petService.atualizarPet(id, request)

        return ResponseEntity.ok(response)
    }

    @GetMapping("/buscar/{nome}")
    fun buscarPetsPorNome(
        @PathVariable nome: String
    ): ResponseEntity<List<PetResponse>> {
        val response = petService.buscarPetsPorNome(nome)

        return ResponseEntity.ok(response)
    }

}