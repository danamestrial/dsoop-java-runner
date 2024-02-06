import org.junit.jupiter.api.*
import java.lang.reflect.Method
import java.util.stream.Stream
import kotlin.random.Random
import kotlin.test.assertContentEquals
import kotlin.test.assertEquals

class HiddenVerifyTest {

    private lateinit var testClass: Class<*>
    private lateinit var testObject: Any
    private lateinit var isHiddenMethod: Method

    @BeforeEach
    @DisplayName("Initialise Variable")
    fun checkIfClassAndMethodExist() {
        try {
            testClass = Class.forName("Hidden")
            testObject = testClass.getDeclaredConstructor().newInstance()
            /* Change args to match method */
            isHiddenMethod = testClass.getDeclaredMethod("isHidden", String::class.java, String::class.java)
        } catch (e: ClassNotFoundException) {
            Assertions.fail("class not here")
        } catch (e: NoSuchMethodException) {
            Assertions.fail("class not here")
        }
    }

    @Nested
    @DisplayName("Test isHidden Sample")
    inner class TestIsHidden {

        @TestFactory
        fun hiddenTests(): Stream<DynamicTest> {
            val inputArr = arrayOf(
                "welcometothehotelcalifornia", "melon", "welcometothehotelcalifornia", "space", "TQ89MnQU3IC7t6", "MUIC", "VhHTdipc07", "htc",
                "VhHTdipc07", "hTc"
            )

            val outputArr = booleanArrayOf(
                true, false, true, false, true
            )

            return Stream.of(*inputArr.indices.step(2).map { index ->
                DynamicTest.dynamicTest("Test ${inputArr[index]} - ${inputArr[index + 1]}") {
                    val actual = Hidden.isHidden(inputArr[index], inputArr[index + 1])
                    Assertions.assertEquals(outputArr[index / 2], actual)
                }
            }.toList().toTypedArray())
        }
    }
}