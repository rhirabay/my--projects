package rhirabay.junit.parameterizedtest

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class ParameterizedtestApplication

fun main(args: Array<String>) {
	runApplication<ParameterizedtestApplication>(*args)
}
