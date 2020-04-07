package com.geekhub_android_2019.cherkasyguide.ui.routeedit

import androidx.lifecycle.LiveData
import com.geekhub_android_2019.cherkasyguide.data.Repository
import com.geekhub_android_2019.cherkasyguide.models.UserRoute
import com.geekhub_android_2019.cherkasyguide.testData.placesGen
import io.mockk.*
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Test

class RouteEditViewModelTest {

    private val places = placesGen(2)

    private val repo = mockk<Repository> {
        coEvery { updateUserRoute(any()) } just Runs
    }

    @Test
    fun `toggleCheck() should add place to null user route`() {
        // GIVEN
        // available places: [place1, place2]
        // user route: null

        val (place1, _) = places

        val viewState = ViewState(
            places = places,
            userRoute = null
        )

        val viewModel = spyk(RouteEditViewModel(repo)) {
            every { state } returns mkStateLiveData(viewState)
        }

        // WHEN togglePlaces called with place1
        runBlockingTest {
            viewModel.toggleCheck(place1)

            advanceUntilIdle()
        }

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
    fun `toggleCheck() should add place to user route`() {
        // GIVEN
        // available places: [place1, place2]
        // user route places: [place2]

        val (place1, place2) = places

        val viewState = ViewState(
            places = places,
            userRoute = UserRoute(places = listOf(place2))
        )

        val viewModel = spyk(RouteEditViewModel(repo)) {
            every { state } returns mkStateLiveData(viewState)
        }

        // WHEN togglePlaces called with place1
        runBlockingTest {
            viewModel.toggleCheck(place1)

            advanceUntilIdle()
        }

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
    fun `toggleCheck() should remove place from user route`() {
        // GIVEN
        // available places: [place1, place2]
        // user route places: [place1, place2]

        val (place1, place2) = places

        val viewState = ViewState(
            places = places,
            userRoute = UserRoute(places = places)
        )

        val viewModel = spyk(RouteEditViewModel(repo)) {
            every { state } returns mkStateLiveData(viewState)
        }

        // WHEN togglePlaces called with place1
        runBlockingTest {
            viewModel.toggleCheck(place1)

            advanceUntilIdle()
        }

        // THEN place1 should be removed from user route
        // user route: [place2]
        coVerify(exactly = 1) {
            repo.updateUserRoute(withArg { (_, newPLaces) ->
                assertEquals("In new route only one place", 1, newPLaces.size)
                assertEquals("New route is [place2]", listOf(place2), newPLaces)
            })
        }
    }

    private fun mkStateLiveData(state: ViewState) =
        mockk<LiveData<ViewState>> {
            every { value } returns state
        }
}
