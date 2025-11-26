package com.example.levelupmovil.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import com.example.levelupmovil.rules.MainDispatcherExtension
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.api.io.TempDir
import java.io.File

@OptIn(ExperimentalCoroutinesApi::class)
@ExtendWith(MainDispatcherExtension::class)
class UserPreferencesRepositoryTest {

    private lateinit var testDataStore: DataStore<Preferences>
    private lateinit var repository: UserPreferencesRepository


    private val testDispatcher = UnconfinedTestDispatcher()
    private val testScope = TestScope(testDispatcher + Job())

    @TempDir
    lateinit var tempDir: File

    @BeforeEach
    fun setup() {
        testDataStore = PreferenceDataStoreFactory.create(
            scope = testScope,
            produceFile = { File(tempDir, "user_prefs_test.preferences_pb") }
        )
        repository = UserPreferencesRepository(testDataStore)
    }

    @Test
    fun `saveToken should update authToken flow and cachedToken`() = runTest {

        val token = "jwt_token_123"
        repository.saveToken(token)

        val savedToken = repository.authToken.first()
        savedToken shouldBe token

        repository.getTokenInstant() shouldBe token
    }

    @Test
    fun `saveAuthData should save all user details`() = runTest {

        repository.saveAuthData(
            token = "token_abc",
            name = "jose",
            email = "jose@test.com",
            address = "calle 1"
        )

        val userData = repository.userData.first()

        userData.name shouldBe "jose"
        userData.email shouldBe "jose@test.com"
        userData.address shouldBe "calle 1"
        repository.getTokenInstant() shouldBe "token_abc"
    }

    @Test
    fun `clearAuthData should remove all data`() = runTest {

        repository.saveAuthData("token", "name", "email", "addr")

        repository.clearAuthData()

        val token = repository.authToken.first()
        token shouldBe null

        val userData = repository.userData.first()
        userData.name shouldBe ""
    }

    @Test
    fun `getTokenInstant should retrieve from DataStore if cache is empty`() = runTest {

        val token = "persisted_token"
        repository.saveToken(token)

        val newRepository = UserPreferencesRepository(testDataStore)

        val retrievedToken = newRepository.getTokenInstant()

        retrievedToken shouldBe token
    }
}