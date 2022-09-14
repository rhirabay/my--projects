package rhirabay.kotlin.plain.reflect.copy

import org.junit.jupiter.api.Test
import kotlin.reflect.full.memberProperties

class SampleTest {
    @Test
    fun test() {
        val base = SampleDataClass().also {
            it.strValue = "sample"
            it.intValue = 1
        }

        val obj = SampleDataClass.clone(base)
        println("${obj.strValue} ${obj.intValue} ${obj.greet}")
    }

    class SampleDataClass {
        var strValue: String = ""
        var intValue: Int = 1
        var greet: Greet = Greet("hello")

        companion object {
            fun clone(other: SampleDataClass): SampleDataClass {
                val map = SampleDataClass::class.memberProperties.associateBy({ it.name }, { it.get(other) })

                val obj = SampleDataClass()
                map.keys.forEach {
                    val field = SampleDataClass::class.java.getDeclaredField(it)
                    field.isAccessible = true
                    field.set(obj, map[it])
                }

                return obj
            }
        }
    }

    data class Greet(
        val message: String
    )
}