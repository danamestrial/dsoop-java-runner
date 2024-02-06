import org.junit.jupiter.api.*
import java.lang.reflect.Method
import kotlin.random.Random
import kotlin.test.assertContentEquals
import kotlin.test.assertEquals

class HappyTest {

    private lateinit var testClass: Class<*>
    private lateinit var testObject: Any
    private lateinit var sumOfDigitsSquaredMethod: Method
    private lateinit var isHappyMethod: Method
    private lateinit var firstKMethod: Method
    private val random = Random(1234L)

    @BeforeEach
    @DisplayName("Initialise Variable")
    fun checkIfClassAndMethodExist() {
        try {
            testClass = Class.forName("Happy")
            testObject = testClass.getDeclaredConstructor().newInstance()
            /* Change args to match method */
            sumOfDigitsSquaredMethod = testClass.getDeclaredMethod("sumOfDigitsSquared", Long::class.java)
            isHappyMethod = testClass.getDeclaredMethod("isHappy", Long::class.java)
            firstKMethod = testClass.getDeclaredMethod("firstK", Int::class.java)
        } catch (e: ClassNotFoundException) {
            Assertions.fail("class not here")
        } catch (e: NoSuchMethodException) {
            Assertions.fail("class not here")
        }
    }

    @Nested
    @DisplayName("Test SumOfDigitsSquared: n=10")
    inner class TestSumOfDigitsSquared {

        private val n = 10
        private val leftLimit = 1L
        private val rightLimit = 10000000000L

        @TestFactory
        fun sumOfDigitsSquared() = List(n) {
            random.nextLong(leftLimit, rightLimit) to 1
        }.map { (first, _) -> first to first }.map { (input, input2) ->
            DynamicTest.dynamicTest("$input") {
                assertEquals(sumOfDigitsSquared(input), sumOfDigitsSquaredMethod.invoke(testObject, input2))
            }
        }
    }

    @Nested
    @DisplayName("Test isHappy: n=10")
    inner class TestIsHappy {

        private val n = 10
        private val leftLimit = 1L
        private val rightLimit = 10000000000L

        @TestFactory
        fun sumOfDigitsSquared() = List(n) {
            random.nextLong(leftLimit, rightLimit) to 1
        }.map { (first, _) -> first to first }
            .map { (input, input2) ->
            DynamicTest.dynamicTest("$input") {
                assertEquals(isHappy(input), isHappyMethod.invoke(testObject, input2))
            }
        }
    }

    @Nested
    @DisplayName("Test firstK: n=10")
    inner class TestFirstK {

        private val n = 10
        private val leftLimit = 1
        private val rightLimit = 100

        @TestFactory
        fun sumOfDigitsSquared() = List(n) {
            random.nextInt(leftLimit, rightLimit) to 1
        }.map { (first, _) -> first to first }
            .map { (input, input2) ->
                DynamicTest.dynamicTest("$input") {
                    assertContentEquals(firstK(input), firstKMethod.invoke(testObject, input2) as LongArray)
                }
            }
    }


    fun sumOfDigitsSquared(n: Long): Long {
        var copyInput = n
        var ans: Long = 0
        while (copyInput > 0) {
            ans += copyInput % 10 * (copyInput % 10)
            copyInput /= 10
        }
        return ans
    }

    fun isHappy(n: Long): Boolean {
        var n = n
        var happyOrNot = true
        while (n > 0) {
            n = sumOfDigitsSquared(n)
            if (n == 1L) {
                break
            }
            if (n == 4L) {
                happyOrNot = false
                break
            }
        }
        return happyOrNot
    }

    fun firstK(k: Int): LongArray {
        var number = 1
        var index = 0
        val ans = LongArray(k)
        while (index < k) {
            if (isHappy(number.toLong())) {
                ans[index] = number.toLong()
                index++
            }
            number++
        }
        return ans
    }
}