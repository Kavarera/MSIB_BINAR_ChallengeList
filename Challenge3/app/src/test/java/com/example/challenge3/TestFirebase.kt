package com.example.challenge3
import org.junit.Test

import com.google.common.truth.Truth.assertThat
class TestFirebase {
    @Test
    fun checkEmailValid() {
        val email = "yourmail@gmail.com"
        val result = VerificationForAuth.cekEmail(email)
        assertThat(result).isTrue()
    }
    @Test
    fun checkEmailEmptyTest() {
        val email = ""
        val result = VerificationForAuth.cekEmail(email);

        assertThat(result).isFalse()
    }

    @Test
    fun checkEmailNotValidTest() {
        val email = "someMail.gmail.com"
        val result = VerificationForAuth.cekEmail(email)

        assertThat(result).isFalse()
    }

    @Test
    fun emailTitikGandaTest() {
        val email = "youremail@gmail..com"
        val result = VerificationForAuth.cekEmail(email)

        assertThat(result).isFalse()
    }

    @Test
    fun emailNotIncludeGlobalDomainTest() {
        val email = "youremail@gmail"
        val result = VerificationForAuth.cekEmail(email)

        assertThat(result).isFalse()
    }


    @Test
    fun checkPasswordValidTest() {
        val password = "password123"
        val result = VerificationForAuth.cekPassword(password)

        assertThat(result).isTrue()
    }

    @Test
    fun passwordKosongTest() {
        val password = ""
        val result = VerificationForAuth.cekPassword(password)

        assertThat(result).isFalse()
    }

    @Test
    fun passwordLengthIsLessTest() {
        val password = "lei"
        val result = VerificationForAuth.cekPassword(password)

        assertThat(result).isFalse()
    }

}