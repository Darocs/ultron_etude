import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.runComposeUiTest
import kotlin.test.Test

class AppTest {
    @OptIn(ExperimentalTestApi::class)
    @Test
    fun test() = runComposeUiTest {
        setContent {
            App()
        }
        onNode(hasText("Click me!123")).assertExists()
    }
}