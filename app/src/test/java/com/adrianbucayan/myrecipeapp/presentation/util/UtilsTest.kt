package com.adrianbucayan.myrecipeapp.presentation.util

import com.google.common.truth.Truth.assertThat
import org.junit.Test

class UtilsTest {

    @Test
    fun `invalid email returns false`() {
        val result = Utils.isEmailValid("adrianbucayan@.com")
        assertThat(result).isEqualTo(false)
    }

    @Test
    fun `valid email returns true`() {
        val result = Utils.isEmailValid("adrianbucayan@gmail.com")
        assertThat(result).isEqualTo(true)
    }

   /* @Test
    fun `removed html string returns true`() {
        val withHtmlString = "<p><b>Welcome to My Recipe</b></p>"
        val result = Utils.stripHtml(withHtmlString)
        assertThat(result.equals("Welcome to My Recipe", ignoreCase = true)).isTrue()
    }*/

    @Test
    fun `valid searched text only returns true`() {
        val stringSearch = "cake"
        val result = Utils.isLetters(stringSearch)
        assertThat(result).isEqualTo(true)
    }


}