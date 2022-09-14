package rhirabay.kotlin.plain

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class PlainApplication

fun main(args: Array<String>) {
	runApplication<PlainApplication>(*args)
}
