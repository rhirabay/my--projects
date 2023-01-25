package hirabay.junit5.cheatsheet

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class CheatsheetApplication

fun main(args: Array<String>) {
	runApplication<CheatsheetApplication>(*args)
}
