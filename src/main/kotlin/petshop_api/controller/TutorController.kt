package petshop_api.controller

import TutorService
import petshop_api.entity.Tutor
import jakarta.validation.Valid
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping


class TutorController(
    private val tutorService: TutorService
) {
    @PostMapping
    fun addTutor(
        @Valid
        @RequestBody tutor: Tutor
    ): Tutor {

        return tutorService.criarTutor(tutor)

    }
}