// DO NOT EDIT! Autogenerated by HLA tool

package com.github.bratek20.infrastructure.userauthserver.fixtures

import org.assertj.core.api.Assertions.assertThat

import com.github.bratek20.infrastructure.userauthserver.api.*

fun assertAuthId(given: AuthId, expected: String) {
    val diff = diffAuthId(given, expected)
    assertThat(diff).withFailMessage(diff).isEqualTo("")
}


fun assertUserId(given: UserId, expected: String) {
    val diff = diffUserId(given, expected)
    assertThat(diff).withFailMessage(diff).isEqualTo("")
}

fun assertUserMapping(given: UserMapping, expectedInit: ExpectedUserMapping.() -> Unit) {
    val diff = diffUserMapping(given, expectedInit)
    assertThat(diff).withFailMessage(diff).isEqualTo("")
}