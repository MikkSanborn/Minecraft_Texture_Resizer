package annotations;

import java.lang.annotation.*;

/**
 * The {@code @ShortRangeDefaultValue} annotation indicates that an input or final value may be modified,
 * but is defaulted to the parameter sent.
 * <p>{@code value} is the default value. (If no other value is specified, this defaults to {@code 0}.)</p>
 * <p>{@code min} is the minimum value. This value is included in the range if {@code inclusive} is true. (If no other value is specified, this defaults to {@code -32768}.)</p>
 * <p>{@code max} is the maximum value. This value is included in the range if {@code inclusive} is true. (If no other value is specified, this defaults to {@code 32767}.)</p>
 * <p>{@code inclusive} determines whether or not a range should include the {@code min} or {@code max} values. (If no other value is specified, this defaults to {@code true}.)</p>
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface ShortRangeDefaultValue {
    public short value() default 0;
    public short minimum() default Short.MIN_VALUE;
    public short maximum() default Short.MAX_VALUE;
    public boolean inclusive() default true;
}
