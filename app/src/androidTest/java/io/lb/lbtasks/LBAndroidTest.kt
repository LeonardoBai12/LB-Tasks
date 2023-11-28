package io.lb.lbtasks

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import dagger.hilt.android.testing.HiltAndroidRule
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.tasks.await
import org.junit.After
import org.junit.Before
import org.junit.Rule
import javax.inject.Inject

abstract class LBAndroidTest {
    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    protected lateinit var context: Context

    @Inject
    lateinit var database: FirebaseDatabase

    @Inject
    lateinit var auth: FirebaseAuth

    @Before
    open fun setUp() {
        context = ApplicationProvider.getApplicationContext()
        hiltRule.inject()
    }

    @After
    open fun tearDown() = runBlocking {
        if (auth.currentUser == null) {
            try {
                auth.signInWithEmailAndPassword(
                    "jetpack@compose.com",
                    "jetpackPassword"
                ).await()
            } catch (_: Exception) {

            }
        }

        auth.currentUser?.let { user ->
            val credential = EmailAuthProvider.getCredential(
                "jetpack@compose.com",
                "jetpackPassword"
            )

            user.reauthenticate(credential)
                .addOnCompleteListener {
                    user.delete()
                }.await()

            database.reference.child(user.uid).removeValue().await()
        }
        database.goOffline()
    }
}
