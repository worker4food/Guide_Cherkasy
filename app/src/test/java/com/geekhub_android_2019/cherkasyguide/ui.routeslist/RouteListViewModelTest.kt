package com.geekhub_android_2019.cherkasyguide.ui.routeslist

import androidx.navigation.NavController
import androidx.navigation.NavDirections
import com.geekhub_android_2019.cherkasyguide.R
import com.geekhub_android_2019.cherkasyguide.common.Limits
import com.geekhub_android_2019.cherkasyguide.network.NetHelper
import com.geekhub_android_2019.cherkasyguide.utils.CoroutineTestRule
import com.geekhub_android_2019.cherkasyguide.utils.mkPlace
import com.geekhub_android_2019.cherkasyguide.utils.mkPlaces
import com.geekhub_android_2019.cherkasyguide.utils.runBlockingTest
import io.mockk.*
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test

class RouteListViewModelTest {
    @get:Rule
    val testScope = CoroutineTestRule()

    private val controller = mockk<NavController> {
        every { navigate(any<NavDirections>()) } just Runs
    }

    private val netOnline = mockk<NetHelper> {
        every { isOnline } returns true
    }

    private val netOffline = mockk<NetHelper> {
        every { isOnline } returns false
    }

    @Test
    fun `createEditRoute() navigates to RouteEditFragment when network available`() {
        // GIVEN device is connected to some network
        val viewModel = RouteListViewModel(mockk(), netOnline, mockk())

        // WHEN createEditRoute called
        viewModel.createEditRoute(controller)

        //THEN NavController::navigate should be called
        verify(exactly = 1) {
            val matcher = withArg<NavDirections> {
                assertEquals(
                    "Action is action_to_routeEditFragment",
                    R.id.action_to_routeEditFragment,
                    it.actionId
                )
            }

            controller.navigate(matcher)
        }
    }

    @Test
    fun `createEditRoute() should not navigate to RouteEditFragment when no network available`() {
        // GIVEN device is not connected to any network
        val viewModel = RouteListViewModel(mockk(), netOffline, mockk())

        // WHEN createEditRoute called
        viewModel.createEditRoute(controller)

        //THEN NavController::navigate should not called
        verify(exactly = 0) {
            controller.navigate(any<NavDirections>())
        }
    }

    @Test
    fun `createEditRoute() should emit warning when no network available`() =
        testScope.runBlockingTest {
            // GIVEN device is not connected to any network
            val viewModel = RouteListViewModel(mockk(), netOffline, mockk())

            val observer = mockk<(Messages) -> Unit>(relaxed = true)
            viewModel.warn.observe(this, observer)

            // WHEN createEditRoute called
            viewModel.createEditRoute(controller)

            // THEN warning NO_NETWORK should be emitted
            verify(exactly = 1) {
                observer(Messages.NO_NETWORK)
            }

            //cleanup
            viewModel.warn.close()
        }

    @Test
    fun `viewPlace() navigates to PlaceDetailFragment when network available`() {
        // GIVEN device is connected to some network
        val viewModel = RouteListViewModel(mockk(), netOnline, mockk())

        // WHEN viewPlace called
        viewModel.viewPlace(controller, mkPlace())

        //THEN NavController::navigate should be called
        verify(exactly = 1) {
            val matcher = withArg<NavDirections> {
                assertEquals(
                    "Action is action_toPlaceDetail",
                    R.id.action_toPlaceDetail,
                    it.actionId
                )
            }

            controller.navigate(matcher)
        }
    }

    @Test
    fun `viewPlace() should not navigate to PlaceDetailFragment when no network available`() {
        // GIVEN device is not connected to any network
        val viewModel = RouteListViewModel(mockk(), netOffline, mockk())

        // WHEN viewPlace called
        viewModel.viewPlace(controller, mkPlace())

        //THEN NavController::navigate should not emitted
        verify(exactly = 0) {
            controller.navigate(any<NavDirections>())
        }
    }

    @Test
    fun `viewPlace() should emit warning when no network available`() =
        testScope.runBlockingTest {
            // GIVEN device is not connected to any network
            val viewModel = RouteListViewModel(mockk(), netOffline, mockk())

            val observer = mockk<(Messages) -> Unit>(relaxed = true)
            viewModel.warn.observe(this, observer)

            // WHEN viewPlace called
            viewModel.viewPlace(controller, mkPlace())

            // THEN warning NO_NETWORK should be emitted
            verify(exactly = 1) {
                observer(Messages.NO_NETWORK)
            }

            //cleanup
            viewModel.warn.close()
        }

    @Test
    fun `viewRouteMap() navigates to RouteMapFragment when network available`() {
        // GIVEN device is connected to some network
        val viewModel = RouteListViewModel(mockk(), netOnline, mockk())

        // WHEN viewRouteMap called
        viewModel.viewRouteMap(controller, mkPlaces(5), "THE TEST")

        //THEN NavController::navigate should be called
        verify(exactly = 1) {
            val matcher = withArg<NavDirections> {
                assertEquals(
                    "Action is action_toRouteMap",
                    R.id.action_toRouteMap,
                    it.actionId
                )
            }

            controller.navigate(matcher)
        }
    }

    @Test
    fun `viewRouteMap() should not navigate to RouteMapFragment when no network available`() {
        // GIVEN device is not connected to any network
        val viewModel = RouteListViewModel(mockk(), netOffline, mockk())

        // WHEN viewRouteMap called
        viewModel.viewRouteMap(controller, mkPlaces(5), "THE TEST")

        //THEN NavController::navigate should not called
        verify(exactly = 0) {
            controller.navigate(any<NavDirections>())
        }
    }

    @Test
    fun `viewRouteMap() should emit warning when no network available`() =
        testScope.runBlockingTest {
            // GIVEN device is not connected to any network
            val viewModel = RouteListViewModel(mockk(), netOffline, mockk())

            val observer = mockk<(Messages) -> Unit>(relaxed = true)
            viewModel.warn.observe(this, observer)

            // WHEN viewRouteMap called
            viewModel.viewRouteMap(controller, mkPlaces(5), "THE TEST")

            // THEN warning NO_NETWORK should be emitted
            verify(exactly = 1) {
                observer(Messages.NO_NETWORK)
            }

            //cleanup
            viewModel.warn.close()
        }

    @Test
    fun `viewRouteMap() should emit warning when route contain less than two places`() =
        testScope.runBlockingTest {
            // GIVEN device is not connected to any network
            val viewModel = RouteListViewModel(mockk(), netOnline, mockk())

            val observer = mockk<(Messages) -> Unit>(relaxed = true)
            viewModel.warn.observe(this, observer)

            // WHEN viewRouteMap called
            viewModel.viewRouteMap(controller, mkPlaces(1), "THE TEST")

            // THEN warning ROUTE_TO_SHORT should be emitted
            verify(exactly = 1) {
                observer(Messages.ROUTE_TO_SHORT)
            }

            //cleanup
            viewModel.warn.close()
        }

    @Test
    fun `viewRouteMap() should not navigate to RouteMapFragment when contain less than two places`() {
        // GIVEN device is not connected to any network
        val viewModel = RouteListViewModel(mockk(), netOffline, mockk())

        // WHEN viewRouteMap called
        viewModel.viewRouteMap(controller, mkPlaces(5), "THE TEST")

        //THEN NavController::navigate should not called
        verify(exactly = 0) {
            controller.navigate(any<NavDirections>())
        }
    }

    @Test
    fun `viewRouteMap() should emit warning when route contain more than 10 places`() =
        testScope.runBlockingTest {
            // GIVEN device is not connected to any network
            val viewModel = RouteListViewModel(mockk(), netOnline, mockk())

            val observer = mockk<(Messages) -> Unit>(relaxed = true)
            viewModel.warn.observe(this, observer)

            // WHEN viewRouteMap called
            viewModel.viewRouteMap(controller, mkPlaces(Limits.MAX_PLACES + 1), "THE TEST")

            // THEN warning ROUTE_TO_LONG should be emitted
            verify(exactly = 1) {
                observer(Messages.ROUTE_TO_LONG)
            }

            //cleanup
            viewModel.warn.close()
        }

    @Test
    fun `viewRouteMap() should not navigate to RouteMapFragment when contain more than 10 places`() {
        // GIVEN device is not connected to any network
        val viewModel = RouteListViewModel(mockk(), netOffline, mockk())

        // WHEN viewRouteMap called
        viewModel.viewRouteMap(controller, mkPlaces(Limits.MAX_PLACES + 1), "THE TEST")

        //THEN NavController::navigate should not called
        verify(exactly = 0) {
            controller.navigate(any<NavDirections>())
        }
    }
}
