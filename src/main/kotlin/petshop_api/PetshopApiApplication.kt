package petshop_api

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class PetshopApiApplication

fun main(args: Array<String>) {
	runApplication<PetshopApiApplication>(*args)
}
