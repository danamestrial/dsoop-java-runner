import org.junit.jupiter.api.*
import java.lang.reflect.Method
import java.time.Duration
import java.util.concurrent.TimeUnit
import kotlin.math.ceil
import kotlin.math.log10
import kotlin.math.sqrt
import kotlin.random.Random
import kotlin.test.assertEquals

class FibTest {

    private lateinit var testClass: Class<*>
    private lateinit var testObject: Any
    private lateinit var testMethod: Method
    private val random = Random(1234L)

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
    @Nested
    @DisplayName("Fib Exhaustive 1-1000, n=10")
    inner class TestFibExhaustive1 {

        private val n = 10
        private val leftLimit = 1
        private val rightLimit = 1000

        @TestFactory
        @Timeout(value = 10, unit = TimeUnit.SECONDS)
        fun fibTestExhaustive() = List(n) {
            random.nextInt(leftLimit, rightLimit) to 1
        }.map { (first, _) -> first to first }.map { (input, input2) ->
            DynamicTest.dynamicTest("$input") {
                assertEquals(
                    firstNDigitAns(input),
                    testMethod.invoke(testObject, input2)
                )
            }
        }
    }

    @Nested
    @DisplayName("Fib Exhaustive 8360, n=1")
    inner class TestFibExhaustive2 {

        @Test
        @DisplayName("Fib Exhaustive 8360")
        fun fibExhaustiveTest() {
            Assertions.assertTimeoutPreemptively(
                Duration.ofSeconds(30), // Adjust the duration as per your requirement
                {
                    Assertions.assertEquals(
                        firstNDigitAns(8360),
                        testMethod.invoke(testObject, 8360)
                    )
                },
                "Execution exceeded timeout"
            )
        }

    }


    // SUS alert from chat-gpt (Bhum is sus-ing this)
    fun firstNDigitAns(n: Int): Int {
        if (n == 1) {return 1}
        val phi = (1 + sqrt(5.0)) / 2
        return ceil((log10(10.0) * (n - 1) + log10(5.0) / 2) / log10(phi)).toInt()
    }
}