package sjnenterprises.household.sjnhomego.data.repository

import sjnenterprises.household.sjnhomego.data.datastore.MBWRKOnboardingPrefs
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class MBWRKOnboardingRepo(
    private val mbwrkOnboardingStoreManager: MBWRKOnboardingPrefs,
    private val coroutineDispatcher: CoroutineDispatcher,
) {

    fun observeOnboardingState(): Flow<Boolean?> {
        return mbwrkOnboardingStoreManager.onboardedStateFlow
    }

    suspend fun setOnboardingState(state: Boolean) {
        withContext(coroutineDispatcher) {
            mbwrkOnboardingStoreManager.setOnboardedState(state)
        }
    }
}