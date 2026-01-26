package com.github.bratek20.utils.directory

import com.github.bratek20.utils.directory.api.FileName
import com.github.bratek20.utils.directory.api.Path
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.Test

class PathTest {

    @Test
    fun `normalized should replace backslashes with forward slashes`() {
        val path = Path("C:\\Users\\User")

        assertThat(path.value).isEqualTo("C:/Users/User")
    }

    @Test
    fun `add(path) should concatenate paths correctly`() {
        val path1 = Path("C:/Users")
        val path2 = Path("Documents")

        val result = path1.add(path2)

        assertThat(result.value).isEqualTo("C:/Users/Documents")
    }

    @Test
    fun `add(path) with empty path should return original path`() {
        val path1 = Path("C:/Users")
        val path2 = Path("")

        val result = path1.add(path2)

        assertThat(result.value).isEqualTo("C:/Users")
    }

    @Test
    fun `add(fileName) should concatenate path and filename correctly`() {
        val path = Path("C:/Users")
        val fileName = FileName("file.txt")

        val result = path.add(fileName)

        assertThat(result.value).isEqualTo("C:/Users/file.txt")
    }

    @Test
    fun `equals should return true for identical paths`() {
        val path1 = Path("C:/Users")
        val path2 = Path("C:/Users")

        assertThat(path1).isEqualTo(path2)
    }

    @Test
    fun `equals should return false for different paths`() {
        val path1 = Path("C:/Users")
        val path2 = Path("C:/Documents")

        assertThat(path1).isNotEqualTo(path2)
    }

    @Test
    fun `hashCode should return same value for identical paths`() {
        val path1 = Path("C:/Users")
        val path2 = Path("C:/Users")

        assertThat(path1.hashCode()).isEqualTo(path2.hashCode())
    }

    @Test
    fun `toString should return normalized path`() {
        val path = Path("C:\\Users\\User")

        assertThat(path.toString()).isEqualTo("C:/Users/User")
    }

    @Test
    fun `should subtract path`() {
        val path1 = Path("C:/Users")
        val path2 = Path("C:/Users/Documents")

        val result = path2.subtract(path1)

        assertThat(result.value).isEqualTo("Documents")
    }

    @Test
    fun `should throw exception when path not subtractable`() {
        val path1 = Path("C:/Users")
        val path2 = Path("C:/Documents")

        assertThatThrownBy { path2.subtract(path1) }
            .isInstanceOf(IllegalArgumentException::class.java)
            .hasMessage("Path $path2 is not subtractable from $path1")
    }
}
