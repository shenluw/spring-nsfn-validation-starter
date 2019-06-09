package top.shenluw.nsfn.validation

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import top.shenluw.nsfn.sensitivewords.*
import top.shenluw.nsfn.sensitivewords.ahocorasick.AhoCorasickDoubleArrayTrieSensitizeProcessor
import top.shenluw.nsfn.sensitivewords.store.MemoryWordStore
import java.io.File
import java.util.function.Consumer

/**
 * @author Shenluw
 * created：2019/6/6 22:34
 */
@Configuration
@EnableConfigurationProperties(NsfnProperties::class)
class NsfnAutoConfiguration {
	@Autowired
	private lateinit var properties: NsfnProperties

	@Bean(SENSITIZE_PROCESSOR_BEAN_NAME)
	fun sensitizeProcessorBean(wordStore: WordStore, factory: DesensitizeStrategyFactory): SensitizeProcessor {
		val processor = AhoCorasickDoubleArrayTrieSensitizeProcessor(wordStore.get(), factory)
		if (properties.escape != null) {
			processor.escape = properties.escape!!
		}
		wordStore.addListener(Consumer { processor.updateWords(it.get()) })
		return processor
	}

	@Bean
	@ConditionalOnMissingBean
	fun desensitizeStrategyFactory(): DesensitizeStrategyFactory {
		return DefaultDesensitizeStrategyFactory()
	}

	@Bean
	@ConditionalOnMissingBean
	fun wordStore(properties: NsfnProperties): WordStore {

		var words: MutableSet<String>? = properties.words
		if (words.isNullOrEmpty()) {
			val wordFile = properties.wordFile
			if (wordFile != null) {
				if (wordFile.exists() && wordFile.isFile && wordFile.canRead()) {
					words = WordStore.load(wordFile)
				}
			}
		}
		if (words.isNullOrEmpty()) {
			val wordDir = properties.wordDir
			if (wordDir != null) {
				val f = File(wordDir)
				if (f.exists() && f.isDirectory) {
					words = WordStore.load(wordDir)
				}
			}
		}

		if (words.isNullOrEmpty()) {
			throw NsfnException("sensitive words not config")
		}
		return MemoryWordStore(words)
	}

}