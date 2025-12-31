import com.github.bratek20.logs.context.Slf4jLogsImpl
import com.github.bratek20.spring.webapp.SpringWebApp

fun main() {
    SpringWebApp(
        modules = listOf(
            Slf4jLogsImpl()
        )
    ).run()
}