package com.geekhub_android_2019.cherkasyguide.ui.placeslist

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleRegistry
import androidx.lifecycle.Observer
import com.geekhub_android_2019.cherkasyguide.data.Repository
import com.geekhub_android_2019.cherkasyguide.models.Place
import junit.framework.Assert.assertNotNull
import junit.framework.Assert.assertTrue
import kotlinx.coroutines.flow.flowOf
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

    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    @Mock
    lateinit var repo: Repository
    lateinit var placesListViewModel: PlacesListViewModel

    @Mock
    lateinit var lifecycleOwner: LifecycleOwner
    var lifecycle: Lifecycle? = null
    lateinit var observer: Observer<List<Place>>

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        lifecycle = LifecycleRegistry(lifecycleOwner)
        placesListViewModel = PlacesListViewModel(repo)
        placesListViewModel.places.observeForever(observer)
    }

    @Test
    fun fetchPlacesNull() {
        `when`(repo.getPlaces())
            .thenReturn(null)
        assertNotNull(placesListViewModel.places)
        assertTrue(placesListViewModel.places.hasObservers())
    }

    @Test
    fun testFetchPlaces() {
        `when`(repo.getPlaces())
            .thenReturn(flowOf(List()))
        placesListViewModel.places
        verify(observer).onChanged(placesListViewModel.places.value)
    }


    /* @Mock
     lateinit var repo: Repository
     lateinit var placesListViewModel: PlacesListViewModel

     //    lateinit var lifecycleOwner: LifecycleOwner
 //    var lifecycle: Lifecycle? = null
 //    private val observer: Observer<List<Place>> = mock()

     @Before
     fun setUp() {
         repo = mock()
         placesListViewModel = mock()
         placesListViewModel = PlacesListViewModel(repo)
 //        placesListViewModel.places.observeForever(observer)

     }


     @Test
     fun moveToTopOfList() {
         val placesListViewModel = PlacesListViewModel(Repository())
         val lifecycle = LifecycleRegistry(mockk()).apply {
             handleLifecycleEvent(Lifecycle.Event.ON_RESUME)
         }

         // given
         val observe = mockk<(List<Place>) -> Unit>()
         every { observe.invoke(Places()) } returns Unit
         placesListViewModel.places.observe({ lifecycle }, observe)

         // when
         placesListViewModel.places

         // then
         verify { observe.invoke(Places()) }
     }
 */

//@Test
//fun testsGetPlaces(){
//    val fakePlaces = Place()
//    placesListViewModel.places
//
//    val captor = ArgumentCaptor.forClass(Places::class.java)
//    captor.run {
//        verify(observer).onChanged(capture())
//        assertEquals(fakePlaces, value)
//    }
//}

    /*  @Test
      fun fetchPlacesNull() {
          `when`(repo.getPlaces())
              .thenReturn(null)
          assertNotNull(placesListViewModel.places)
          assertTrue(placesListViewModel.places.hasObservers())
      }

      @Test
      fun testFetchPlaces() {
          `when`(repo.getPlaces())
              .thenReturn(List<Place>)
          placesListViewModel.places
          verify(observer).onChanged(placesListViewModel.places.value)
      }*/
}

