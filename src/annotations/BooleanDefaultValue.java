package annotations;

import java.lang.annotation.*;

/**
 * The {@code @BooleanDefaultValue} annotation indicates that an input or final value may be modified,
 * but is defaulted to the parameter sent.
 * <p>The default value if no other is specified is {@code false}.</p>
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface BooleanDefaultValue {
    public boolean value() default false;
}
