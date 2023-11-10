package io.lb.lbtasks

import dagger.hilt.android.testing.HiltAndroidTest
import junit.framework.TestCase.assertEquals
import org.junit.Test

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@HiltAndroidTest
class ExampleInstrumentedTest : LbAndroidTest() {
    @Test
    fun useAppContext() {
        assertEquals("io.lb.lbtasks", context.packageName)
    }
}
