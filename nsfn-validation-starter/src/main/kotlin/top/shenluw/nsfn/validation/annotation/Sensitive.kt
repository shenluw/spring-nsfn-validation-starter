package top.shenluw.nsfn.validation.annotation

import top.shenluw.nsfn.validation.SensitiveValidatorForCharSequence
import javax.validation.Constraint
import javax.validation.Payload
import kotlin.reflect.KClass

/**
 * @author Shenluw
 * created：2019/6/6 22:44
 */
@Target(AnnotationTarget.FUNCTION, AnnotationTarget.PROPERTY_GETTER, AnnotationTarget.PROPERTY_SETTER, AnnotationTarget.FIELD, AnnotationTarget.ANNOTATION_CLASS, AnnotationTarget.CONSTRUCTOR, AnnotationTarget.VALUE_PARAMETER, AnnotationTarget.TYPE)
@Retention
@Repeatable
@MustBeDocumented
@Constraint(validatedBy = [SensitiveValidatorForCharSequence::class])
annotation class Sensitive(
		val message: String = "{top.shenluw.nsfn.validation.annotation.Sensitive.message}",
		val groups: Array<KClass<*>> = [],
		val payload: Array<KClass<out Payload>> = []) {

	@Target(AnnotationTarget.FUNCTION, AnnotationTarget.PROPERTY_GETTER, AnnotationTarget.PROPERTY_SETTER, AnnotationTarget.FIELD, AnnotationTarget.ANNOTATION_CLASS, AnnotationTarget.CONSTRUCTOR, AnnotationTarget.VALUE_PARAMETER, AnnotationTarget.TYPE)
	@Retention
	@MustBeDocumented
	annotation class List(vararg val value: Sensitive)
}