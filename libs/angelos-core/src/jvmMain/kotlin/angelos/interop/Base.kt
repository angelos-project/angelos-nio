/**
 * Copyright (c) 2021-2022 by Kristoffer Paulsson <kristoffer.paulsson@talenten.se>.
 *
 * This software is available under the terms of the MIT license. Parts are licensed
 * under different terms if stated. The legal terms are attached to the LICENSE file
 * and are made available on:
 *
 *      https://opensource.org/licenses/MIT
 *
 * SPDX-License-Identifier: MIT
 *
 * Contributors:
 *      Kristoffer Paulsson - initial implementation
 */
package angelos.interop

import angelos.io.file.WatcherException
import angelos.io.poll.PollAction
import angelos.io.signal.SigName
import angelos.sys.Error
import sun.misc.Signal
import java.lang.System

actual class Base {
    actual companion object {
        init {
            System.loadLibrary("jni-base") // Load underlying library via JNI.
        }

        internal actual var interrupt: (sigNum: SigName) -> Unit = {}

        actual fun getEndian(): Int = endian()

        actual fun getPlatform(): Int = platform()

        @JvmStatic
        private external fun endian(): Int

        @JvmStatic
        private external fun platform(): Int

        actual fun setInterrupt(sigName: SigName): Boolean {
            Signal.handle(Signal(sigName.sigName)) {
                incomingSignal(SigName.codeToName(it.number))
            }
            return true
        }

        internal actual fun incomingSignal(sigName: SigName) = interrupt(sigName)

        actual fun sigAbbr(sigNum: Int): String = signal_abbreviation(sigNum).uppercase()

        @JvmStatic
        private external fun signal_abbreviation(sigNum: Int): String

        actual fun getError() {
            get_error()
        }

        @JvmStatic
        private external fun get_error()

        actual fun pollAction(): PollAction {
            val action = event_poll()
            if(action == null) {
                Error.loadError()
                throw BaseError("Failed to poll due to cause: (${Error.errNum}) ${Error.errMsg}")
            }
            return action
        }

        @JvmStatic
        private external fun event_poll(): PollAction?

        actual fun pollFinalize(): Unit {} // Leave empty for compatability

        actual fun attachStream(fd: Int): Int {
            val nfd = stream_attach(fd)
            if (nfd == -1) {
                Error.loadError()
                throw WatcherException("Failed to attach stream due to cause: (${Error.errNum}) ${Error.errMsg}")
            }
            return nfd
        }

        @JvmStatic
        private external fun stream_attach(fd: Int): Int

        actual fun attachSocket(fd: Int) {
            if (socket_attach(fd) != 0) {
                Error.loadError()
                throw WatcherException("Failed to attach socket due to cause: (${Error.errNum}) ${Error.errMsg}")
            }
        }

        @JvmStatic
        private external fun socket_attach(fd: Int): Int

        actual fun isOpenStream(fd: Int): Boolean = stream_is_open(fd)

        @JvmStatic
        private external fun stream_is_open(fd: Int): Boolean

        actual fun closeStream(fd: Int): Int = stream_close(fd)

        @JvmStatic
        private external fun stream_close(fd: Int): Int
    }
}