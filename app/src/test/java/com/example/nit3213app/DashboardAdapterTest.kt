package com.example.nit3213app

import com.example.nit3213app.dashboard.DashboardAdapter
import com.example.nit3213app.model.Entity
import io.mockk.every
import io.mockk.spyk
import io.mockk.verify
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class DashboardAdapterTest {

    private lateinit var adapter: DashboardAdapter
    private val mockClickListener: (Entity) -> Unit = {}

    @Before
    fun setUp() {
        adapter = spyk(DashboardAdapter(onItemClick = mockClickListener))
        every { adapter.notifyDataSetChanged() } returns Unit
    }

    @Test
    fun `getItemCount returns correct size`() {
        // Given
        val dataList = listOf(
            Entity(hashMapOf("name" to "Item 1")),
            Entity(hashMapOf("name" to "Item 2")),
            Entity(hashMapOf("name" to "Item 3"))
        )
        adapter.setData(dataList)

        // When
        val itemCount = adapter.itemCount

        // Then
        assertEquals(3, itemCount)
    }

    @Test
    fun `setData updates dataList and calls notifyDataSetChanged`() {
        // Given
        val newDataList = listOf(
            Entity(hashMapOf("name" to "Item A")),
            Entity(hashMapOf("name" to "Item B"))
        )

        // When
        adapter.setData(newDataList)

        // Then
        assertEquals(2, adapter.itemCount)
        verify { adapter.notifyDataSetChanged() }
    }
}