package com.github.bratek20.architecture.users

import com.github.bratek20.architecture.context.api.Context
import com.github.bratek20.architecture.context.api.ContextBuilder
import com.github.bratek20.architecture.context.someContextBuilder
import com.github.bratek20.architecture.data.context.DataInMemoryImpl
import com.github.bratek20.architecture.users.api.UserId
import com.github.bratek20.architecture.users.impl.InMemoryUser
import com.github.bratek20.architecture.users.impl.UsersInMemoryImpl
import org.springframework.beans.factory.config.BeanFactoryPostProcessor
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory
import org.springframework.context.annotation.Configuration
import org.springframework.context.support.SimpleThreadScope

@Configuration
open class TestSessionScopeConfig : BeanFactoryPostProcessor {
    override fun postProcessBeanFactory(beanFactory: ConfigurableListableBeanFactory) {
        beanFactory.registerScope("session", SimpleThreadScope())
    }
}

class TestUserContextBuilder(
    appBuilderOps: ContextBuilder.() -> Unit = {},
    val userBuilderOps: ContextBuilder.() -> Unit = {}
) {
    companion object {
        const val SEED = 42
    }

    val appContext: Context = someContextBuilder(SEED)
        .withModules(
            DataInMemoryImpl()
        )
        .apply(appBuilderOps)
        .build()

    fun build(userId: Int = 1): Context {
        val userContext = someContextBuilder(SEED)
            .withParent(appContext)
            .withModules(
                UsersInMemoryImpl()
            )
            .setClass(TestSessionScopeConfig::class.java)
            .apply(userBuilderOps)
            .build()

        userContext.get(InMemoryUser::class.java).setId(UserId(userId))
        return userContext
    }
}