package com.geekhub_android_2019.cherkasyguide.ui.placeslist

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleRegistry
import androidx.lifecycle.Observer
import com.geekhub_android_2019.cherkasyguide.TestCoroutineRule
import com.geekhub_android_2019.cherkasyguide.data.Repository
import com.geekhub_android_2019.cherkasyguide.models.Place
import junit.framework.Assert.assertNotNull
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner


@RunWith(MockitoJUnitRunner.Silent::class)
class PlacesListViewModelTest {

    lateinit var placesListViewModel: PlacesListViewModel

    @Mock
    lateinit var repo: Repository

    @Mock
    lateinit var lifecycleOwner: LifecycleOwner
    var lifecycle: Lifecycle? = null

    @Mock
    lateinit var observer: Observer<List<Place>>

    @ExperimentalCoroutinesApi
    @get:Rule
    var testCoroutineRule = TestCoroutineRule()

    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        lifecycle = LifecycleRegistry(lifecycleOwner)
        (lifecycle as LifecycleRegistry).handleLifecycleEvent(Lifecycle.Event.ON_CREATE)
        `when`(lifecycleOwner.lifecycle).thenReturn(lifecycle)
    }

    @Test
    fun fetchPlacesNull() {
        val place = listOf<Place>(
            Place("id", "name", null, "desc", "small photo")
        )
        `when`(repo.getPlaces())
            .thenReturn(flowOf(place))
        placesListViewModel = PlacesListViewModel(repo)
        assertNotNull(flowOf(placesListViewModel.places))
    }

    @Test
    fun testFetchGetPlaces() {
        testCoroutineRule.runBlockingTest {
            val place = listOf<Place>(
                Place("id", "name", null, "desc", "small photo")
            )
            `when`(repo.getPlaces())
                .thenReturn(flowOf(place))
            placesListViewModel = PlacesListViewModel(repo)
            val result = placesListViewModel.places
            placesListViewModel.places.observeForever(observer)
            verify(observer).onChanged(result.value)
        }
    }
}
