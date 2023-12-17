package jalaram.kotlin

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class KotlinExpApplication

fun main(args: Array<String>) {
	runApplication<KotlinExpApplication>(*args)
}
