package com.example.levelupmovil.viewmodel

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class SearchViewModelTest {

    private lateinit var viewModel: SearchViewModel

    @BeforeEach
    fun setup() {
        viewModel = SearchViewModel()
    }

    @Test
    fun `initial state should be empty string`() {
        viewModel.searchQuery shouldBe ""
    }

    @Test
    fun `updateQuery should change searchQuery value`() {
        val query = "Monitor Gamer"

        viewModel.updateQuery(query)

        viewModel.searchQuery shouldBe query
    }
}