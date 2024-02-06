import org.junit.jupiter.api.*
import java.lang.reflect.Method
import kotlin.math.ceil
import kotlin.math.log10
import kotlin.math.sqrt
import kotlin.test.assertEquals

class RomanVerifyTest {

    private lateinit var testClass: Class<*>
    private lateinit var testObject: Any
    private lateinit var testMethod: Method

    @BeforeEach
    @DisplayName("Initialise Variable")
    fun checkIfClassAndMethodExist() {
        try {
            testClass = Class.forName("Roman")
            testObject = testClass.getDeclaredConstructor().newInstance()
            /* Change args to match method */
            testMethod = testClass.getDeclaredMethod("romanToInt", String::class.java)
        } catch (e: ClassNotFoundException) {
            Assertions.fail("class not here")
        } catch (e: NoSuchMethodException) {
            Assertions.fail("class not here")
        }
    }

    @Nested
    @DisplayName("Roman Sample: k=I, V, VII, MCMLIV, MCMXC")
    inner class TestRomanSample {

        @TestFactory
        fun romanTests() = listOf(
            "I" to "I",
            "V" to "V",
            "VII" to "VII",
            "MCMLIV" to "MCMLIV",
            "MCMXC" to "MCMXC",
        ).map { (input, input2) ->
            DynamicTest.dynamicTest(input) {
                assertEquals(romanToIntAns(input), testMethod.invoke(testObject, input2))
            }
        }
    }

    fun romanToIntAns(romanNum: String): Int {
        var number = 0
        for (i in romanNum.indices) {
            when (romanNum[i]) {
                'I' -> number =
                    if (i != romanNum.length - 1 && (romanNum[i + 1] == 'V' || romanNum[i + 1] == 'X')) number - 1 else number + 1

                'V' -> number += 5
                'X' -> number =
                    if (i != romanNum.length - 1 && (romanNum[i + 1] == 'L' || romanNum[i + 1] == 'C')) number - 10 else number + 10

                'L' -> number += 50
                'C' -> number =
                    if (i != romanNum.length - 1 && (romanNum[i + 1] == 'D' || romanNum[i + 1] == 'M')) number - 100 else number + 100

                'D' -> number += 500
                'M' -> number += 1000
            }
        }
        return number
    }
}