import org.junit.jupiter.api.*
import java.lang.reflect.Method
import kotlin.test.assertContentEquals
import kotlin.test.assertEquals

class SubselVerifyTest {

    private lateinit var testClass: Class<*>
    private lateinit var testObject: Any
    private lateinit var testMethod1: Method
    private lateinit var testMethod2: Method

    @BeforeEach
    @DisplayName("Initialise Variable")
    fun checkIfClassAndMethodExist() {
        try {
            testClass = Class.forName("Subsel")
            testObject = testClass.getDeclaredConstructor().newInstance()
            /* Change args to match method */
            testMethod1 = testClass.getDeclaredMethod("takeEvery", IntArray::class.java, Int::class.java, Int::class.java)
            testMethod2 = testClass.getDeclaredMethod("takeEvery", IntArray::class.java, Int::class.java)
        } catch (e: ClassNotFoundException) {
            Assertions.fail("class not here")
        } catch (e: NoSuchMethodException) {
            Assertions.fail("class not here")
        }
    }

    @Nested
    @DisplayName("Subsel Sample")
    inner class TestSubselSample {

        @Test
        @DisplayName("takeEvery([1, 2, 3, 4], 2) == [1, 3]")
        fun sample1() {
            val res: IntArray = testMethod2.invoke(testObject, intArrayOf(1,2,3,4), 2) as IntArray
            assertContentEquals(
                res, intArrayOf(1,3)
            )
        }

        @Test
        @DisplayName("takeEvery([2, 7, 1, 8, 4, 5], 3, 2) == [1, 5]")
        fun sample2() {
            val res: IntArray = testMethod1.invoke(testObject, intArrayOf(2, 7, 1, 8, 4, 5), 3, 2) as IntArray
            assertContentEquals(
                res, intArrayOf(1,5)
            )
        }

        @Test
        @DisplayName("takeEvery([3, 1, 4, 5, 9, 2, 6, 5], -1, 5) == [2, 9, 5, 4, 1, 3]")
        fun sample3() {
            val res: IntArray = testMethod1.invoke(testObject, intArrayOf(3, 1, 4, 5, 9, 2, 6, 5), -1, 5) as IntArray
            assertContentEquals(
                res, intArrayOf(2, 9, 5, 4, 1, 3)
            )
        }
    }
}