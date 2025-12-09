package com.github.bratek20.infrastructure.sessionuser.tests

import com.github.bratek20.architecture.users.TestUserContextBuilder
import com.github.bratek20.infrastructure.sessionuser.SessionComponent
import org.junit.jupiter.api.Test

@SessionComponent
class SomeClass {

}

class SessionUserImplTest {
    @Test
    fun `test builder should work for classes annotated with SessionComponent`() {
        val c = TestUserContextBuilder(
            userBuilderOps = {
                setClass(SomeClass::class.java)
            }
        ).build()

        c.get(SomeClass::class.java)
    }
}