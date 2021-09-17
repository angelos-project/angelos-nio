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
package angelos.io

class RealPath internal constructor(root: String, path: List<String>, separator: PathSeparator) :
    Path(root, path, separator) {

    companion object {
        internal fun wrap(path: String, separator: PathSeparator): RealPath {
            val elements = getElements(path, separator)
            return RealPath(elements.first, elements.second, elements.third)
        }

        internal inline fun getItem(path: RealPath, type: FileObject.Type): FileObject = when (type) {
            FileObject.Type.DIR -> Dir(path)
            FileObject.Type.LINK -> Link(path)
            FileObject.Type.FILE -> File(path)
            else -> throw UnsupportedOperationException()
        }

        internal inline fun getType(type: Int): FileObject.Type = when (type) {
            1 -> FileObject.Type.LINK
            2 -> FileObject.Type.DIR
            3 -> FileObject.Type.FILE
            else -> FileObject.Type.UNKNOWN
        }
    }

    private fun getType(): FileObject.Type = getType(getFileType(this.toString()))

    fun getItem(): FileObject = getItem(this, getType())
    fun exists(): Boolean = checkExists(this.toString())
    fun isLink(): Boolean = getType() == FileObject.Type.LINK
    fun isFile(): Boolean = getType() == FileObject.Type.FILE
    fun isDir(): Boolean = getType() == FileObject.Type.DIR

    override fun join(vararg elements: String): RealPath = RealPath(root, path + elements, separator)
    override fun join(path: String): RealPath = RealPath(root, this.path + splitString(path, separator), separator)
}