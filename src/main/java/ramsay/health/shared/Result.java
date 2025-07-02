package ramsay.health.shared;

import io.micronaut.serde.annotation.Serdeable;

import java.util.function.Function;
import java.util.function.Supplier;

/**
 * A generic Result class representing the outcome of an operation that can either succeed with a value or fail with an exception.
 *
 * @param <T> The type of the result value.
 */
@Serdeable
public class Result<T> {
    public final Exception exception;
    public final T value;
    private final ResultState state;

    /**
     * Constructor for a successful result.
     *
     * @param value The value of the result.
     */
    public Result(T value) {
        this.exception = null;
        this.value = value;
        state = ResultState.SUCCESS;
    }

    /**
     * Constructor for a failed result.
     *
     * @param e The exception representing the failure.
     */

    public Result(Exception e) {
        this.exception = e;
        this.value = null;
        state = ResultState.FAULTED;
    }

    public static <T> Result<T> of(T value) {
        return new Result<>(value);
    }

    public static <T> Result<T> of(Exception e) {
        return new Result<>(e);
    }

    /**
     * A Supplier indicating whether the result is in a faulted state.
     */
    public boolean isFaulted() {
        return state == ResultState.FAULTED;
    }

    /**
     * A Supplier indicating whether the result is in a success state.
     */
    public boolean isSuccess() {
        return state == ResultState.SUCCESS;
    }

    /**
     * Matches the result and executes the corresponding callable.
     *
     * @param successFn The callable to execute in case of success.
     * @param fail      The callable to execute in case of failure.
     * @return The result of the executed callable.
     * @throws RuntimeException if an exception occurs during the callable execution.
     */
    public <R> R match(Function<T, R> successFn, Function<Exception, R> fail) {
        try {
            if (state == ResultState.FAULTED)
                return fail.apply(this.exception);
            else
                return successFn.apply(this.value);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    // Overloaded match method for cases where no arguments are needed
    public <R> R match(Supplier<R> successFn, Supplier<R> fail) {
        try {
            if (state == ResultState.FAULTED)
                return fail.get();
            else
                return successFn.get();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    // Overloaded match method for cases where one argument is needed
    public <R> R match(Function<T, R> successFn, Supplier<R> fail) {
        try {
            if (state == ResultState.FAULTED)
                return fail.get();
            else
                return successFn.apply(this.value);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    // Overloaded match method for cases where one argument is needed
    public <R> R match(Supplier<R> successFn, Function<Exception, R>  fail) {
        try {
            if (state == ResultState.FAULTED)
                return fail.apply(this.exception);
            else
                return successFn.get();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}