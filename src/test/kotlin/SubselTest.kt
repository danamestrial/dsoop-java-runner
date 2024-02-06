import org.junit.jupiter.api.*
import java.lang.reflect.Method
import kotlin.test.assertContentEquals
import kotlin.test.assertEquals

class SubselTest {

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

    @Nested
    @DisplayName("Test Subsel Exhaustive 2 Params")
    inner class TestSubselExhaustive2Params {

        @TestFactory
        fun subsel2Params() = listOf(
            intArrayOf(1, 3) to intArrayOf(1, 2, 3, 4) to 2,
            intArrayOf(2, 8) to intArrayOf(2, 7, 1, 8, 4, 5) to 3,
            intArrayOf(3) to intArrayOf(3, 1, 4, 5, 9, 2, 6, 5) to -1,
            intArrayOf(2, 4) to intArrayOf(2, 7, 9, 5, 1, 4, 3, 6, 8) to 5,
            intArrayOf(3, 5, 4) to intArrayOf(3, 5, 4) to 1,
            intArrayOf(5) to intArrayOf(5) to 1,
            intArrayOf(2) to intArrayOf(2, 3, 8, 9) to 7,
            intArrayOf(5, 7) to intArrayOf(5, 4, 9, 6, 7) to 4,
            intArrayOf(1) to intArrayOf(1, 3, 5, 7, 9) to -2,
            intArrayOf(2, 6) to intArrayOf(2, 4, 6, 8) to 2,
            intArrayOf(7) to intArrayOf(7, 8, 4) to -2,
            intArrayOf(6, 3) to intArrayOf(6, 1, 2, 3, 4, 5) to 3,
            intArrayOf(4, 5, 7) to intArrayOf(4, 5, 7) to 1,
            intArrayOf(1) to intArrayOf(1, 3, 4, 5, 6, 11, 13, 14, 16, 8, 9, 20, 21, 10, 22, 23, 24, 25, 100, 99, 94, 82) to -10,
            intArrayOf(1) to intArrayOf(1, 2) to 2
        ).map { (oneParamsAnsArrays, strides) ->
            val (oneParamsAns, arrays) = oneParamsAnsArrays
            DynamicTest.dynamicTest("2 Params : [" + oneParamsAns.joinToString() + "]") {
                assertContentEquals(oneParamsAns, testMethod2.invoke(testObject, arrays, strides) as IntArray)
            }
        }
    }

    @Nested
    @DisplayName("Test Subsel Exhaustive 3 Params")
    inner class TestSubselExhaustive3Params {

        @TestFactory
        fun subsel3Params() = listOf(
            intArrayOf(1, 3) to intArrayOf(1, 2, 3, 4) to 2 to 0,
            intArrayOf(1, 5) to intArrayOf(2, 7, 1, 8, 4, 5) to 3 to 2,
            intArrayOf(2, 9, 5, 4, 1, 3) to intArrayOf(3, 1, 4, 5, 9, 2, 6, 5) to -1 to 5,
            intArrayOf(9, 6) to intArrayOf(2, 7, 9, 5, 1, 4, 3, 6, 8) to 5 to 2,
            intArrayOf(4) to intArrayOf(3, 5, 4) to 1 to 2,
            intArrayOf() to intArrayOf(5) to 1 to 1,
            intArrayOf(9) to intArrayOf(2, 3, 8, 9) to 7 to 3,
            intArrayOf(5, 7) to intArrayOf(5, 4, 9, 6, 7) to 4 to 0,
            intArrayOf(9, 5, 1) to intArrayOf(1, 3, 5, 7, 9) to -2 to 4,
            intArrayOf(4, 8) to intArrayOf(2, 4, 6, 8) to 2 to 1,
            intArrayOf(4, 7) to intArrayOf(7, 8, 4) to -2 to 2,
            intArrayOf(1, 4) to intArrayOf(6, 1, 2, 3, 4, 5) to 3 to 1,
            intArrayOf(7) to intArrayOf(4, 5, 7) to 1 to 2,
            intArrayOf(82, 20, 3) to intArrayOf(1, 3, 4, 5, 6, 11, 13, 14, 16, 8, 9, 20, 21, 10, 22, 23, 24, 25, 100, 99, 94, 82) to -10 to 21,
            intArrayOf(2) to intArrayOf(1, 2) to 2 to 1
        ).map { (twoParamsAnsArrays, beginWiths) ->
            val (more, strides) = twoParamsAnsArrays
            val (twoParamsAns, arrays) = more
            DynamicTest.dynamicTest("3 Params : [" + twoParamsAns.joinToString() + "]") {
                assertContentEquals(twoParamsAns, testMethod1.invoke(testObject, arrays, strides, beginWiths) as IntArray)
            }
        }
    }
}