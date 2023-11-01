/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package cn.sliew.sakura.common.exception;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Callable;
import java.util.function.Function;

import static org.apache.flink.shaded.guava31.com.google.common.base.Preconditions.checkNotNull;


/**
 * Rethrowing checked exceptions as unchecked ones. Eh, it is sometimes useful...
 */
@Slf4j
public enum Rethrower {
    ;

    /**
     * Rethrow the supplied {@link Throwable exception} if it is
     * <em>unrecoverable</em>.
     *
     * <p>If the supplied {@code exception} is not <em>unrecoverable</em>, this
     * method does nothing.
     */
    public static void rethrowIfUnrecoverable(Throwable exception) {
        if (exception instanceof OutOfMemoryError) {
            throwAsUncheckedException(exception);
        }
    }

    /**
     * Throw the supplied {@link Throwable}, <em>masked</em> as an
     * unchecked exception.
     *
     * <p>The supplied {@code Throwable} will not be wrapped. Rather, it
     * will be thrown <em>as is</em> using an exploit of the Java language
     * that relies on a combination of generics and type erasure to trick
     * the Java compiler into believing that the thrown exception is an
     * unchecked exception even if it is a checked exception.
     *
     * <h3>Warning</h3>
     *
     * <p>This method should be used sparingly.
     *
     * @param t the {@code Throwable} to throw as an unchecked exception;
     *          never {@code null}
     * @return this method always throws an exception and therefore never
     * returns anything; the return type is merely present to allow this
     * method to be supplied as the operand in a {@code throw} statement
     */
    public static RuntimeException throwAsUncheckedException(Throwable t) {
        checkNotNull(t, "Throwable must not be null");

        throwAs(t);
        // Appeasing the compiler: the following line will never be executed.
        return null;
    }

    /**
     * Rethrows <code>t</code> (identical object).
     */
    @SuppressWarnings("unchecked")
    public static <T extends Throwable> void throwAs(Throwable t) throws T {
        throw (T) t;
    }

    /**
     * Catch a checked exception and rethrow as a {@link RuntimeException}
     *
     * @param callable function that throws a checked exception.
     * @param <T>      return type of the function.
     * @return object that the function returns.
     */
    public static <T> T toRuntime(final Callable<T> callable) {
        try {
            return callable.call();
        } catch (final RuntimeException e) {
            throw e;
        } catch (final Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Catch a checked exception and rethrow as a {@link RuntimeException}.
     *
     * @param voidCallable function that throws a checked exception.
     */
    public static void toRuntime(final Procedure voidCallable) {
        castCheckedToRuntime(voidCallable, RuntimeException::new);
    }

    public static void toIllegalState(final Procedure voidCallable) {
        castCheckedToRuntime(voidCallable, IllegalStateException::new);
    }

    public static void toIllegalArgument(final Procedure voidCallable) {
        castCheckedToRuntime(voidCallable, IllegalArgumentException::new);
    }

    private static void castCheckedToRuntime(final Procedure voidCallable, final Function<Exception, RuntimeException> exceptionFactory) {
        try {
            voidCallable.call();
        } catch (final RuntimeException e) {
            throw e;
        } catch (final Exception e) {
            throw exceptionFactory.apply(e);
        }
    }

    public static void swallow(final Procedure procedure) {
        try {
            procedure.call();
        } catch (final Exception e) {
            log.error("Swallowed error.", e);
        }
    }

    public interface Procedure {

        void call() throws Exception;
    }
}
