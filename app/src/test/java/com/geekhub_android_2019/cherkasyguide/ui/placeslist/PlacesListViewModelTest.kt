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
import com.geekhub_android_2019.cherkasyguide.models.Places
import com.nhaarman.mockitokotlin2.doReturn
import kotlinx.coroutines.flow.Flow
import org.junit.Rule
import org.mockito.Mockito.`when`


@RunWith(MockitoJUnitRunner.Silent::class)
class PlacesListViewModelTest {

    @Mock
    lateinit var repo: Repository
    lateinit var fakePlace: Flow<List<Place>>


    @Before
    fun setUp() {
        repo = mock()
        fakePlace = mock()
    }

    @Test
    fun fetchGetPlaces() {
        // GIVEN
        `when`(repo.getPlaces()).thenReturn(fakePlace)

        // WHEN

        // THEN
        Mockito.verify(repo).getPlaces()


    }

}

