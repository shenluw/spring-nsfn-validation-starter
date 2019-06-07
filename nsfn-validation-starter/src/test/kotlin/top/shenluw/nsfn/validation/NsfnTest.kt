package top.shenluw.nsfn.validation

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.ImportAutoConfiguration
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultHandlers.print
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.context.WebApplicationContext
import top.shenluw.nsfn.validation.annotation.Sensitive


/**
 * @author Shenluw
 * created：2019/6/7 21:36
 */
@RestController
@SpringBootApplication
@ImportAutoConfiguration(NsfnAutoConfiguration::class)
@Validated
class TestController {
	private val log = LoggerFactory.getLogger(TestController::class.java)

	@RequestMapping("/simple")
	fun test(@Sensitive text: CharSequence?): CharSequence? {
		log.info("simple: {}", text)
		return text
	}
	/*
	@Bean
	fun customStore(): MemoryWordStore {
		return MemoryWordStore(hashSetOf("word"))
	}
	*/
}

@SpringBootTest
@ExtendWith(SpringExtension::class)
class NsfnTest {
	@Autowired
	private val context: WebApplicationContext? = null
	private lateinit var mockMvc: MockMvc

	@BeforeEach
	fun setUp() {
		mockMvc = MockMvcBuilders.webAppContextSetup(context!!).build()
	}


	@Test
	@Throws(Exception::class)
	fun test1() {
		mockMvc.perform(MockMvcRequestBuilders.get("/simple")
				.param("text", "我是敏感词测试")
				.accept(MediaType.APPLICATION_JSON_UTF8))
				.andDo(print())
	}

	@Test
	@Throws(Exception::class)
	fun test2() {
		mockMvc.perform(MockMvcRequestBuilders.get("/simple")
				.param("text", "我不是感词测试")
				.accept(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(status().isOk)
				.andExpect(MockMvcResultMatchers.content().string("我不是感词测试"))
				.andDo(print())
	}
}


