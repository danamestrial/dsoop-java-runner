import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.fail
import org.junit.jupiter.api.Test
import java.lang.reflect.Method

internal class SampleTest {

    @Test
    fun sum() {
        /**
        * Safe Guard:
        * If students didn't submit and class doesn't exist
        */
        try {
            val testClass: Class<*> = Class.forName("Sample")
            val testObject: Any = testClass.getDeclaredConstructor().newInstance()
            /* Change args to match method */
            val testMethod: Method = testClass.getDeclaredMethod("sum", Int::class.java, Int::class.java)

            /* Test goes below here */
            val expected = 42
            assertEquals(expected, testMethod.invoke(testObject, 40, 2))
        } catch (e: ClassNotFoundException) {
            fail("class not here")
        }   catch (e: NoSuchMethodException) {
            fail("class not here")
        }
    }

    @Test // will always fail
    fun sum2() {
        /*
        * Safe Guard:
        * If students didn't submit and class doesn't exist
        */
        try {
            val testClass: Class<*> = Class.forName("Sample2")
            val testObject: Any = testClass.getDeclaredConstructor().newInstance()
            /* Change args to match method */
            val testMethod: Method = testClass.getDeclaredMethod("sum2", Int::class.java, Int::class.java)

            /* Test goes below here */
            val expected = 42
            assertEquals(expected, testMethod.invoke(testObject, 40, 2))
        } catch (e: ClassNotFoundException) {
            fail("class not here")
        }   catch (e: NoSuchMethodException) {
            fail("class not here")
        }
    }
}