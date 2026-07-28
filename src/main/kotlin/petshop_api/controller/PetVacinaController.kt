package petshop_api.controller

import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import petshop_api.dto.PetVacinaRequest
import petshop_api.dto.PetVacinaResponse
import petshop_api.service.PetVacinaService
import java.util.UUID

@RestController
@RequestMapping("/api/pet-vacinas")
class PetVacinaController(
    private val petVacinaService: PetVacinaService
) {

    @PostMapping("/aplicar")
    fun aplicarVacina(
        @Valid @RequestBody request: PetVacinaRequest
    ): ResponseEntity<PetVacinaResponse> {
        val response = petVacinaService.aplicarVacina(request)
        return ResponseEntity.status(HttpStatus.CREATED).body(response)
    }

    @GetMapping
    fun listarTodasAplicacoes(): ResponseEntity<List<PetVacinaResponse>> {
        val response = petVacinaService.listarTodasAplicacoes()
        return ResponseEntity.ok(response)
    }

    @GetMapping("/pet/{petId}")
    fun listarVacinasPorPet(
        @PathVariable petId: UUID
    ): ResponseEntity<List<PetVacinaResponse>> {
        val response = petVacinaService.listarVacinasPorPet(petId)
        return ResponseEntity.ok(response)
    }

    @GetMapping("/vacina/{vacinaId}")
    fun listarPetsPorVacina(
        @PathVariable vacinaId: UUID
    ): ResponseEntity<List<PetVacinaResponse>> {
        val response = petVacinaService.listarPetsPorVacina(vacinaId)
        return ResponseEntity.ok(response)
    }
}