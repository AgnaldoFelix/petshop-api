package petshop_api.controller

import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import petshop_api.dto.PetVacinaRequest
import petshop_api.dto.PetVacinaResponse
import petshop_api.service.PetVacinaService
import java.util.UUID

@RestController
@RequestMapping("/api/pet-vacinas")
class PetVacinaController(
    private val petVacinaService: PetVacinaService
) {

    @PostMapping
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

    @GetMapping("/api/pets/{petId}/vacinas")
    fun listarVacinasPorPet(
        @PathVariable petId: UUID
    ): ResponseEntity<List<PetVacinaResponse>> {
        val response = petVacinaService.listarVacinasPorPet(petId)
        return ResponseEntity.ok(response)
    }

    @GetMapping("/api/vacinas/{vacinaId}/pets")
    fun listarPetsPorVacina(
        @PathVariable vacinaId: UUID
    ): ResponseEntity<List<PetVacinaResponse>> {
        val response = petVacinaService.listarPetsPorVacina(vacinaId)
        return ResponseEntity.ok(response)
    }
}