package com.github.bratek20.architecture.structs.context

import com.github.bratek20.architecture.structs.api.AnyStructHelper
import com.github.bratek20.architecture.structs.impl.AnyStructHelperLogic

class StructsFactory {
    companion object {
        fun createAnyStructHelper(): AnyStructHelper {
            return AnyStructHelperLogic()
        }
    }
}