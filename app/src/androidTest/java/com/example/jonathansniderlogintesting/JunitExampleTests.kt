package com.example.jonathansniderlogintesting

import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import org.junit.Test

class JunitExampleTests {
    @Test
    fun testAddition() {
        val result = 2 + 2
        assertEquals(4, result)
        assertEquals(3, result)

    }

    @Test
    fun testEmptyString() {
        val str = ""
        assertTrue(str.isEmpty())
    }
}

//other examples include:
//assertNull / assertNotNull
//assertEqual
//(uses .equal)
//assertSame / assertNotSame
// (uses ==)
//assertFalse
