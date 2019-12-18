package annotations;

import java.lang.annotation.*;

/**
 * The {@code @ShortDefaultValue} annotation indicates that an input or final value may be modified,
 * but is defaulted to the parameter sent.
 * <p>The default value if no other is specified is {@code 0}.</p>
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface ShortDefaultValue {
    public short value() default 0;
}
