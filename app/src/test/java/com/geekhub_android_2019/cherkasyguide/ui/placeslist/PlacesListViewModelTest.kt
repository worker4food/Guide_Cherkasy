package com.geekhub_android_2019.cherkasyguide.ui.placeslist

import androidx.lifecycle.LiveDataScope
import com.geekhub_android_2019.cherkasyguide.data.Repository
import com.geekhub_android_2019.cherkasyguide.models.Place
import com.nhaarman.mockitokotlin2.mock
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner
import java.util.*
import androidx.lifecycle.LiveData
import com.nhaarman.mockitokotlin2.doReturn

@RunWith(MockitoJUnitRunner.Silent::class)
class PlacesListViewModelTest {

    @Mock
    lateinit var repo: Repository
    lateinit var placesListViewModel: PlacesListViewModel


    @Before
    fun setUp() {
        repo = mock()
        placesListViewModel = mock()
    }

    @Test
    fun fetchGetPlaces() {
        val place:Place = mock{
            on{ fakePlace} doReturn placesListViewModel.places
        }
    }

    companion object {
        val fakePlace = Place(
            name = "Fake Place",
            photoSmallUrl = "Fake photo"
        )
    }
}

