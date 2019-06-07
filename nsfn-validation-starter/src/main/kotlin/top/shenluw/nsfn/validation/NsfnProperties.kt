package top.shenluw.nsfn.validation

import org.springframework.boot.context.properties.ConfigurationProperties
import java.io.File

/**
 * @author Shenluw
 * created：2019/6/6 23:35
 */
@ConfigurationProperties(prefix = "nsfn")
class NsfnProperties {
	// 敏感词目录
	var wordDir: String? = null
	// 敏感词文件
	var wordFile: File? = null
	// 敏感词
	var words: MutableSet<String>? = null
}