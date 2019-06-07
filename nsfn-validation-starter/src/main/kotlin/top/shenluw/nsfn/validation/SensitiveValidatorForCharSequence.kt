package top.shenluw.nsfn.validation

import org.springframework.context.ApplicationContext
import org.springframework.context.ApplicationContextAware
import top.shenluw.nsfn.sensitivewords.SensitizeProcessor
import top.shenluw.nsfn.validation.annotation.Sensitive
import java.util.*
import javax.validation.ConstraintValidator
import javax.validation.ConstraintValidatorContext

/**
 * @author Shenluw
 * created：2019/6/6 22:49
 */

const val SENSITIZE_PROCESSOR_BEAN_NAME = "sensitizeValidatorProcessorBean"

class SensitiveValidatorForCharSequence : ConstraintValidator<Sensitive, CharSequence>, ApplicationContextAware {

	private var context: ApplicationContext? = null

	private var sensitizeProcessor: SensitizeProcessor? = null

	override fun setApplicationContext(applicationContext: ApplicationContext) {
		this.context = applicationContext
	}

	override fun initialize(constraintAnnotation: Sensitive?) {
		sensitizeProcessor = context?.getBean(SENSITIZE_PROCESSOR_BEAN_NAME, SensitizeProcessor::class.java)
		Objects.requireNonNull(sensitizeProcessor, SENSITIZE_PROCESSOR_BEAN_NAME + "bean not found")
	}

	override fun isValid(value: CharSequence?, context: ConstraintValidatorContext?): Boolean {
		if (value.isNullOrBlank()) {
			return true
		}
		return !sensitizeProcessor!!.containsMatch(value)
	}
}