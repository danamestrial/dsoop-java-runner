import org.junit.jupiter.api.*
import java.lang.reflect.Method
import java.util.stream.Stream
import kotlin.random.Random
import kotlin.test.assertContentEquals
import kotlin.test.assertEquals

class HiddenTest {

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
    @DisplayName("Test isHidden")
    inner class TestIsHidden {

        @TestFactory
        fun hiddenTests(): Stream<DynamicTest> {
            val inputArr = arrayOf(
                "welcometothehotelcalifornia", "melon", "welcometothehotelcalifornia", "space", "TQ89MnQU3IC7t6", "MUIC", "VhHTdipc07", "htc",
                "VhHTdipc07", "hTc", "moo", "mow", "Ham", "Ham", "Ham", "ham", "puoiascathonelnen", "pusheen", "pushnnennenansnenneeenneeen", "pusheen",
                "Aco", "cod", "pUoiascathonelnen", "pusheen", "neenhsuppusheen", "pusheen", "Foracmaosdaoaosadd", "aodd", "pusheensocute", "uoce", "AccAb",
                "Acb", "AccAb", "Acc", "AccAb", "AcC", "AccAbTa", "AcbT", "Dst", "st", "Dst", "dt", "Dt", "dt", "DT", "dt", "DT", "DT", "abcdefg", "ae",
                "abcdefg", "abe", "mine", "e", "mine", "en", "gam12sasd4516sdfjmiomkvbcke", "game", "gam12sasd4516sdfjmiomkvbcke", "g251",
                "gam12sasd4516sdfjmiomkvbcke", "g25ibe", "gam12sasd4516sdfjmiomkvbcke", "gamck", "1123111121123123123123", "123", "1123111121123123123123",
                "123123", "1123111121123123123123", "112345", "1123111121123123123123", "111111", "111", "111", "1%!@!SDFa", "%a", "1%!@!SDFa", "%!!",
                "1%!@!SDFa", "%!!a", "1%!@!SDFa", "&", "1%!@!SDFa0", "&0a", "1%!@!SDFa0", "SdF0", "1%!@!SDFabba0", "@abba0", "oinasdad1214%1z", "oinz",
                "oinasdad1214%1z", "oinzk", "121abba1@1", "123", "121abba1@1", "1@", "pusheensocute", "pusheencute", "CasmawakxajmSodasdKae", "CoKe"
            )

            val outputArr = booleanArrayOf(
                true, false, true, false, true, false, true, false, true, true, false, false, true, true, true, true, true, false, true, true, false, false,
                false, true, true, true, true, false, true, true, true, true, true, true, false, true, true, true, true, true, false, false, false, true, true,
                false, false, true, true, true
            )

            return Stream.of(*inputArr.indices.step(2).map { index ->
                DynamicTest.dynamicTest("Test ${index / 2 + 1}") {
                    val actual = Hidden.isHidden(inputArr[index], inputArr[index + 1])
                    Assertions.assertEquals(outputArr[index / 2], actual)
                }
            }.toList().toTypedArray())
        }
    }
}