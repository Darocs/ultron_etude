package com.atiurin.sampleapp.tests.espresso_web

import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.web.assertion.WebViewAssertions.webMatches
import androidx.test.espresso.web.sugar.Web.onWebView
import androidx.test.espresso.web.webdriver.DriverAtoms
import androidx.test.espresso.web.webdriver.DriverAtoms.*
import androidx.test.espresso.web.webdriver.Locator
import com.atiurin.sampleapp.R
import com.atiurin.sampleapp.framework.utils.AssertUtils
import com.atiurin.sampleapp.pages.WebViewPage
import com.atiurin.sampleapp.tests.UiElementsTest
import com.atiurin.ultron.core.config.UltronConfig
import com.atiurin.ultron.core.espressoweb.*
import com.atiurin.ultron.core.espressoweb.`$`.Companion.className
import com.atiurin.ultron.core.espressoweb.`$`.Companion.id
import com.atiurin.ultron.core.espressoweb.`$`.Companion.linkText
import com.atiurin.ultron.extensions.*
import org.hamcrest.CoreMatchers.containsString
import org.junit.Assert
import org.junit.Test

class EspressoWebUiElementsTest : UiElementsTest() {
    val page = WebViewPage()

    @Test
    fun simpleWebViewTest() {
        val newTitle = "New title"
        onWebView().withElement(findElement(Locator.ID, "text_input"))
            .perform(webKeys(newTitle))
        onWebView().withElement(findElement(Locator.ID, "button1"))
            .perform(webClick())
        onWebView().withElement(findElement(Locator.ID, "title"))
            .check(webMatches(DriverAtoms.getText(), containsString("New title")))
        onWebView().perform(findElement(Locator.ID, "title")).get()
        //findMultipleElements
        onWebView().perform(findMultipleElements(Locator.CLASS_NAME, "button"))
            .get()
            .forEach {
                onWebView().withElement(it).perform(webClick())
            }
    }

    @Test
    fun multipleElementsWebViewTest() {
//        `$$`(Locator.CLASS_NAME, "button").getElements().forEach {
//            it.webClick()
//        }
        `$$`(Locator.CLASS_NAME, "link").getElements()
            .filter {
                it.isSuccess {
                    hasText("Apple", 1000)
                }
            }
            .forEach { it.webClick() }
        page.title.containsText("apple")
    }

    @Test
    fun pageMultipleElements() {
        page.buttons.getElements().forEach {
            it.webClick()
        }
    }

    @Test
    fun extWebViewTest() {
        val newTitle = "New title"
        `$`.element(Locator.ID, "text_input").webKeys(newTitle)
        `$`.element(Locator.ID, "button1").webClick()
        `$`.element(Locator.ID, "title").containsText(newTitle)
    }

    @Test
    fun extVar2WebViewTest() {
        val newTitle = "New title"
        id("text_input1").webKeys(newTitle)
        id("button1").webClick()
        id("title").containsText(newTitle)
        className("css_title").containsText(newTitle)
        linkText("Apple").containsText("Apple")
    }

    @Test
    fun jsEvaluationTest() {
        val jsTitle = "JS_TITLE"
        val jsTitleNew = "JS_TITLE_NEW"
        script("document.getElementById(\"title\").innerHTML = '$jsTitle';")
        className("css_title").containsText(jsTitle)
        onWebView(withId(R.id.webview)).script("document.getElementById(\"title\").innerHTML = '$jsTitleNew';")
//        onWebView(withId(R.id.webview)).withElement(className("css_title")).containsText(jsTitleNew)
    }

    @Test
    fun webViewFinderTest() {
        val jsTitleNew = "JS_TITLE_NEW"
        UltronConfig.Espresso.webViewFinder = { onWebView(withId(R.id.webview)) }
        script("document.getElementById(\"title\").innerHTML = '$jsTitleNew';")
        className("css_title").containsText(jsTitleNew)
    }

    @Test
    fun pageVar3WebViewTest() {
        val newTitle = "New title"
        page.textInput.webKeys(newTitle)
        page.button.webClick()
        page.title.containsText(newTitle)
        page.titleWithCss.containsText(newTitle)
        AssertUtils.assertException { page.appleLink.containsText("Apple12312") }
    }

    @Test
    fun getText2Test() {
        val newTitle = "New title"
        page.textInput.webKeys(newTitle)
        page.button.webClick()
        page.title.hasText(newTitle)
        val titleText = page.title.getText()
        Assert.assertEquals(newTitle, titleText)
    }

    @Test
    fun webInteractionLyambda() {
        Assert.assertTrue(id("button2").getText().isBlank())
        Assert.assertEquals("Apple", id("apple_link").getText())
        id("asdasdasd").getText()
    }

}