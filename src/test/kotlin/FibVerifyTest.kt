import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.DynamicTest
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestFactory
import java.lang.reflect.Method
import kotlin.math.ceil
import kotlin.math.log10
import kotlin.math.sqrt
import kotlin.random.Random
import kotlin.test.assertEquals

class FibVerifyTest {

    private lateinit var testClass: Class<*>
    private lateinit var testObject: Any
    private lateinit var testMethod: Method

    @BeforeEach
    @DisplayName("Initialise Variable")
    fun checkIfClassAndMethodExist() {
        try {
            testClass = Class.forName("Fib")
            testObject = testClass.getDeclaredConstructor().newInstance()
            /* Change args to match method */
            testMethod = testClass.getDeclaredMethod("firstNDigit", Int::class.java)
        } catch (e: ClassNotFoundException) {
            Assertions.fail("class not here")
        } catch (e: NoSuchMethodException) {
            Assertions.fail("class not here")
        }
    }

    @Nested
    @DisplayName("Fib Sample: k=1..3")
    inner class TestFib {

        @TestFactory
        fun fibTests() = listOf(
            1 to 1,
            2 to 2,
            3 to 3,
        ).map { (input, input2) ->
            DynamicTest.dynamicTest("$input") {
                assertEquals(firstNDigitAns(input), testMethod.invoke(testObject, input2))
            }
        }
    }

    fun firstNDigitAns(n: Int): Int {
        if (n == 1) {return 1}
        val phi = (1 + sqrt(5.0)) / 2
        return ceil((log10(10.0) * (n - 1) + log10(5.0) / 2) / log10(phi)).toInt()
    }
}