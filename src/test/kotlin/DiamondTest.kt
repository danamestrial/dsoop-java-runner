import org.junit.jupiter.api.*
import java.io.ByteArrayOutputStream
import java.io.PrintStream
import java.lang.reflect.Method
import org.junit.jupiter.api.Assertions.assertEquals

internal class DiamondTest {

    private lateinit var testClass: Class<*>
    private lateinit var testObject: Any
    private lateinit var testMethod: Method

    @BeforeEach
    @DisplayName("Initialise Variable")
    fun checkIfClassAndMethodExist() {
        try {
            testClass = Class.forName("Diamond")
            testObject = testClass.getDeclaredConstructor().newInstance()
            /* Change args to match method */
            testMethod = testClass.getDeclaredMethod("printDiamond", Int::class.java)
        } catch (e: ClassNotFoundException) {
            Assertions.fail("class not here")
        }   catch (e: NoSuchMethodException) {
            Assertions.fail("class not here")
        }
    }

    @Nested
    @DisplayName("Test Start")
    inner class StartTest {

        private val outContent: ByteArrayOutputStream = ByteArrayOutputStream()
        private val errContent: ByteArrayOutputStream = ByteArrayOutputStream()
        private val originalOut = System.out
        private val originalErr = System.err

        /**
         * Redirecting system io to capture stdout/stderr (if exists)
         **/

        @BeforeEach
        fun setUpStreams() {
            System.setOut(PrintStream(outContent))
            System.setErr(PrintStream(errContent))
        }

        @AfterEach
        fun restoreStreams() {
            System.setOut(originalOut)
            System.setErr(originalErr)
            outContent.reset()
            errContent.reset()
        }


        /**
         * This is where Tests begin
         **/

        @Nested
        @DisplayName("Test Diamond: Sample case k = 2..4")
        inner class TestSample {
            @Test
            @DisplayName("k=2")
            fun k2() {
                testMethod.invoke(testObject, 2) // printDiamond 2

                assertEquals(
                    "##*##\n" +
                            "#***#\n" +
                            "##*##",
                    outContent.toString().trim())
            }

            @Test
            @DisplayName("k=3")
            fun k3() {
                testMethod.invoke(testObject, 3) // printDiamond 3

                assertEquals(
                    "###*###\n" +
                            "##***##\n" +
                            "#*****#\n" +
                            "##***##\n" +
                            "###*###",
                    outContent.toString().trim())
            }

            @Test
            @DisplayName("k=4")
            fun k4() {
                testMethod.invoke(testObject, 4) // printDiamond 4

                assertEquals(
                    "####*####\n" +
                            "###***###\n" +
                            "##*****##\n" +
                            "#*******#\n" +
                            "##*****##\n" +
                            "###***###\n" +
                            "####*####",
                    outContent.toString().trim())
            }
        }

        @Nested
        @DisplayName("Test Exhaustive")
        inner class ExhaustiveTest {

            @Test
            fun ex1() {
                testMethod.invoke(testObject, 1) // printDiamond 10

                assertEquals(printDiamondAns(1).trim(), outContent.toString().trim())
            }

            @Test
            fun ex2() {
                testMethod.invoke(testObject, 0) // printDiamond 20

                assertEquals(printDiamondAns(0).trim(), outContent.toString().trim())
            }

            @Test
            fun ex3() {
                testMethod.invoke(testObject, 69) // printDiamond 20

                assertEquals(printDiamondAns(69).trim(), outContent.toString().trim())
            }
        }
    }

    fun printDiamondAns(k: Int): String {
        val result = StringBuilder()
        val size = k * 2

        for (i in 1..size step 2) {
            for (s in size downTo i step 2) {
                result.append("#")
            }
            for (t in 1..i) {
                result.append("*")
            }
            for (s in size downTo i step 2) {
                result.append("#")
            }
            result.append("\n")
        }

        for (i in 1 until size - 1 step 2) {
            for (s in 1..i + 2 step 2) {
                result.append("#")
            }
            for (t in size downTo i + 3) {
                result.append("*")
            }
            for (s in 1..i + 2 step 2) {
                result.append("#")
            }
            result.append("\n")
        }

        return result.toString()
    }
}