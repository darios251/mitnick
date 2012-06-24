package org.hibernate.validator.constraints;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.CONSTRUCTOR;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import org.hibernate.validator.constraints.impl.MitnickFieldValidator;

/**
 * The string has to be a well-formed email address.
 *
 * @author Emmanuel Bernard
 * @author Hardy Ferentschik
 */
@Documented
@Constraint(validatedBy = MitnickFieldValidator.class)
@Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER })
@Retention(RUNTIME)
public @interface MitnickTextField {
	
	String message() default "";
	
	String requiredMessage() default "error.entity.message.required";
	
	String propertyName() default "";
	
	/**
	 * @return The regular expression to match.
	 */
	String regexp() default "";
	
	String regexpMessage() default "error.entity.message.regexp";
	
	String sizeMessage() default "error.entity.message.size";
	
	String fieldTypeMessage() default "error.entity.message.fieldType";
	
	boolean required() default false;
	
	FieldType fieldType() default FieldType.NONE;
	
	public static class FieldTypeValues {
		public static final int NONE_VALUE = 0;
		public static final int INTEGER_VALUE = 1;
		public static final int DOUBLE_VALUE = 2;
		public static final int LONG_VALUE = 3;
		public static final int FLOAT_VALUE = 4;
		public static final int DATE_VALUE = 5;
		public static final int CREDIT_CARD_VALUE = 6;
		public static final int EMAIL_VALUE = 7;
		public static final int URL_VALUE = 8;
		public static final int DOCUMENT_NUMBER_VALUE = 9;
		public static final int CUIT_VALUE = 10;
		public static final int PHONE_NUMBER_VALUE = 11;
		public static final int APHANUMERIC_VALUE = 12;
		public static final int NUMERIC_VALUE = 13;
		public static final int ALPHABETIC_VALUE = 14;
		public static final int NAME_VALUE = 15;
	}
	
	public static enum FieldType {
		NONE(0),
		INTEGER(FieldTypeValues.INTEGER_VALUE),
		DOUBLE(FieldTypeValues.DOUBLE_VALUE),
		LONG(FieldTypeValues.LONG_VALUE),
		FLOAT(FieldTypeValues.FLOAT_VALUE),
		DATE(FieldTypeValues.DATE_VALUE),
		CREDIT_CARD(FieldTypeValues.CREDIT_CARD_VALUE),
		EMAIL(FieldTypeValues.EMAIL_VALUE),
		URL(FieldTypeValues.URL_VALUE),
		DOCUMENT_NUMBER(FieldTypeValues.DOCUMENT_NUMBER_VALUE),
		CUIT(FieldTypeValues.CUIT_VALUE),
		PHONE_NUMBER(FieldTypeValues.PHONE_NUMBER_VALUE),
		APHANUMERIC(FieldTypeValues.APHANUMERIC_VALUE),
		NUMERIC(FieldTypeValues.NUMERIC_VALUE),
		ALPHABETIC(FieldTypeValues.ALPHABETIC_VALUE),
		NAME(FieldTypeValues.NAME_VALUE);
		
		private final int value;
		
		private FieldType(int value) {
			this.value = value;
		}

		public int getValue() {
			return value;
		}
	}
	
	/**
	 * @return Array of <code>Flag</code>s considered when resolving the regular expression.
	 */
	RegexFlag[] flags() default {};

	Class<?>[] groups() default { };

	Class<? extends Payload>[] payload() default { };
	
	/**
	 * @return size the element must be higher or equal to
	 */
	int min() default 0;

	/**
	 * @return size the element must be lower or equal to
	 */
	int max() default Integer.MAX_VALUE;
	
	/**
	 * Possible Regexp flags
	 */
	public static enum RegexFlag {

		/**
		 * Enables Unix lines mode
		 * @see java.util.regex.Pattern#UNIX_LINES
		 */
		UNIX_LINES(java.util.regex.Pattern.UNIX_LINES),

		/** 
		 * Enables case-insensitive matching
		 * @see java.util.regex.Pattern#CASE_INSENSITIVE
		 */
		CASE_INSENSITIVE(java.util.regex.Pattern.CASE_INSENSITIVE),

		/**
		 * Permits whitespace and comments in pattern
		 * @see java.util.regex.Pattern#COMMENTS
		 */
		COMMENTS(java.util.regex.Pattern.COMMENTS),

		/**
		 * Enables multiline mode
		 * @see java.util.regex.Pattern#MULTILINE
		 */
		MULTILINE(java.util.regex.Pattern.MULTILINE),

		/**
		 * Enables dotall mode
		 * @see java.util.regex.Pattern#DOTALL
		 */
		DOTALL(java.util.regex.Pattern.DOTALL),

		/**
		 * Enables Unicode-aware case folding
		 * @see java.util.regex.Pattern#UNICODE_CASE
		 */
		UNICODE_CASE(java.util.regex.Pattern.UNICODE_CASE),

		/**
		 * Enables canonical equivalence
		 * @see java.util.regex.Pattern#CANON_EQ
		 */
		CANON_EQ(java.util.regex.Pattern.CANON_EQ);

		//JDK flag value
		private final int value;

		private RegexFlag(int value) {
			this.value = value;
		}

		/**
		 * @return flag value as defined in {@link java.util.regex.Pattern}
		 */
		public int getValue() {
			return value;
		}
	}

	@Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER })
	@Retention(RUNTIME)
	@Documented
	public @interface List {
		MitnickTextField[] value();
	}
}