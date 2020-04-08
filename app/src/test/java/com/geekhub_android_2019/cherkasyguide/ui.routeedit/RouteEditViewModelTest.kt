package com.geekhub_android_2019.cherkasyguide.ui.routeedit

import androidx.lifecycle.LiveData
import com.geekhub_android_2019.cherkasyguide.common.Limits
import com.geekhub_android_2019.cherkasyguide.data.Repository
import com.geekhub_android_2019.cherkasyguide.models.UserRoute
import com.geekhub_android_2019.cherkasyguide.utils.asAppDispatchers
import com.geekhub_android_2019.cherkasyguide.utils.mkPlaces
import io.mockk.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Test

class RouteEditViewModelTest {

    private val testDispatcher = TestCoroutineDispatcher()
    private val testScope = TestCoroutineScope(testDispatcher)
    private val testAppDispatchers = testDispatcher.asAppDispatchers()

    private val repo = mockk<Repository> {
        coEvery { updateUserRoute(any()) } just Runs
    }

    @Test
    fun `toggleCheck() should add place to null user route`() = testScope.runBlockingTest {
        // GIVEN
        // available places: [place1, place2]
        // user route: null
        val places2 = mkPlaces(2)
        val (place1, _) = places2

        val viewState = ViewState(
            places = places2,
            userRoute = null
        )

        val viewModel = spyk(RouteEditViewModel(repo, testAppDispatchers)) {
            every { state } returns mkStateLiveData(viewState)
        }

        // WHEN togglePlaces called with place1
        viewModel.toggleCheck(place1)

        // THEN
        // user route: [place1]
        coVerify(exactly = 1) {
            repo.updateUserRoute(withArg { userRoute ->
                assertNotNull(userRoute)

                val (_, newPlaces) = userRoute
                assertEquals("In new route only one place", 1, newPlaces.size)
                assertEquals("New route is [place1]", listOf(place1), newPlaces)
            })
        }

    }

    @Test
    fun `toggleCheck() should add place to user route`() = testScope.runBlockingTest {
        // GIVEN
        // available places: [place1, place2]
        // user route places: [place2]
        val places2 = mkPlaces(2)
        val (place1, place2) = places2

        val viewState = ViewState(
            places = places2,
            userRoute = UserRoute(places = listOf(place2))
        )

        val viewModel = mkViewModel(viewState)

        // WHEN togglePlaces called with place1
        viewModel.toggleCheck(place1)

        // THEN place1 should be appended to user route
        // user route: [place2, place1]
        coVerify(exactly = 1) {
            repo.updateUserRoute(withArg { (_, newPLaces) ->
                assertEquals("In new route two places", 2, newPLaces.size)
                assertEquals(
                    "New route is [place2, place1]",
                    listOf(place2, place1),
                    newPLaces
                )
            })
        }
    }

    @Test
    fun `toggleCheck() should remove place from user route`() = testScope.runBlockingTest {
        // GIVEN
        // available places: [place1, place2]
        // user route places: [place1, place2]
        val places2 = mkPlaces(2)
        val (place1, place2) = places2

        val viewState = ViewState(
            places = places2,
            userRoute = UserRoute(places = places2)
        )

        val viewModel = mkViewModel(viewState)

        // WHEN togglePlaces called with place1
        viewModel.toggleCheck(place1)

        // THEN place1 should be removed from user route
        // user route: [place2]
        coVerify(exactly = 1) {
            repo.updateUserRoute(withArg { (_, newPLaces) ->
                assertEquals("In new route only one place", 1, newPLaces.size)
                assertEquals("New route is [place2]", listOf(place2), newPLaces)
            })
        }
    }

    @Test
    fun `toggleCheck() should push warning about to large route`() = testScope.runBlockingTest {
        // GIVEN
        // available places: [place1, place2, .., place10, place11]
        // user route: [place1, place2, .., place10]
        val places = mkPlaces(Limits.MAX_PLACES + 1)
        val viewState = ViewState(
            places = places,
            userRoute = UserRoute(places = places.take(Limits.MAX_PLACES))
        )

        val viewModel = mkViewModel(viewState)
        val place11 = places.last()
        val chan = viewModel.warn.openSubscription()

        // WHEN toggleCheck() called with place11
        viewModel.toggleCheck(place11)
        val msg = chan.receive()

        assertEquals("Message is ROUTE_TO_LARGE", Messages.ROUTE_TO_LONG, msg)
    }

    private fun mkStateLiveData(state: ViewState) =
        mockk<LiveData<ViewState>> {
            every { value } returns state
        }

    private fun mkViewModel(viewState: ViewState): RouteEditViewModel {
        val stateLiveData = mkStateLiveData(viewState)

        return spyk(RouteEditViewModel(repo, testAppDispatchers)) {
            every { state } returns stateLiveData
        }
    }

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun cleanUp() {
        testScope.cleanupTestCoroutines()
        Dispatchers.resetMain()
    }
}
