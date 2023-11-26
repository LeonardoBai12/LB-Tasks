package io.lb.lbtasks

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.google.firebase.database.FirebaseDatabase
import dagger.hilt.android.testing.HiltAndroidRule
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Rule
import javax.inject.Inject

abstract class LbAndroidTest {
    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    protected lateinit var context: Context

    @Inject
    lateinit var database: FirebaseDatabase

    @Before
    open fun setUp() {
        context = ApplicationProvider.getApplicationContext()
        hiltRule.inject()
    }

    @After
    open fun tearDown() {
        database.goOffline()
    }
}
