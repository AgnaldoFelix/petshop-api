package petshop_api.controller

import jakarta.validation.Valid
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
import petshop_api.dto.VacinaRequest
import petshop_api.dto.VacinaResponse
import petshop_api.service.VacinaService
import java.util.UUID

@RestController
@RequestMapping("/api/vacina")
class VacinaController (
    private val vacinaService: VacinaService
) {
    @PostMapping("/cadastrar")
    fun cadastrarVacina(
        @RequestBody request: VacinaRequest
    ): ResponseEntity<VacinaResponse> {
        val response = vacinaService.cadastrarVacina(request)

        return ResponseEntity.status(HttpStatus.CREATED).body(response)
    }

    @GetMapping("/buscar")
    fun listarVacinas(

    ): ResponseEntity<List<VacinaResponse>>{
        val response = vacinaService.listarVacinas()

        return ResponseEntity.ok(response)
    }

    @DeleteMapping("/{id}")
    fun deletarVacina(@PathVariable id: UUID): ResponseEntity<Void> {
        val response = vacinaService.deletarVacina(id)
        return ResponseEntity.noContent().build()
    }

    @PutMapping("/{id}")
    fun editarVacina(
        @PathVariable id: UUID,
        @Valid @RequestBody request: VacinaRequest
    ): ResponseEntity<VacinaResponse> {
        val response = vacinaService.editarVacina(id, request)
        return ResponseEntity.ok(response)
    }




















}