package com.katiegarcia.flickrdemo.model.data.flickr.user

import com.google.common.truth.Truth.assertThat
import com.katiegarcia.flickrdemo.model.data.Person
import org.junit.Test

class PersonTest {

    @Test
    fun `farm number above 0 returns valid URL`(){
        val person = Person(iconfarm = 1, userProfileURL = "")

        assertThat(person.getURL()).contains("farm")
    }

    @Test
    fun `farm number equals 0 returns URL with buddyicon gif`(){
        val person = Person(iconfarm = 1, userProfileURL = "")

        assertThat(person.getURL()).contains("buddyicon.gif")
    }
}