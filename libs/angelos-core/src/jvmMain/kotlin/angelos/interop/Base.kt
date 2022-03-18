/**
 * Copyright (c) 2021 by Kristoffer Paulsson <kristoffer.paulsson@talenten.se>.
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

import java.lang.System

actual class Base {
    actual companion object {
        init {
            System.loadLibrary("jni-base") // Load underlying library via JNI.
        }

        actual fun getEndian(): Int = endian()

        actual fun getPlatform(): Int = platform()

        @JvmStatic
        private external fun endian(): Int

        @JvmStatic
        private external fun platform(): Int

        actual fun setInterrupt(sigNum: Int): Boolean {
            TODO("Not yet implemented")
        }

        private fun incomingSignal(sigNum: Int) {
            TODO("$sigNum. Time to implement signal handler on native.")
        }

        actual fun sigAbbr(sigNum: Int): String {
            TODO("Not yet implemented")
        }
    }
}