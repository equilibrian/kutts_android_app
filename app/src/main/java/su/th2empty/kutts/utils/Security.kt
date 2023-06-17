/*
 * Copyright (c) 2023 Denis Novikov
 *
 * This file is part of KUTTS.
 * KUTTS is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License version 3, as published by the Free Software Foundation.
 * KUTTS is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License version 3 for more details.
 * You should have received a copy of the GNU General Public License version 3 along with KUTTS. If not, see <https://www.gnu.org/licenses/gpl-3.0.html>.
 *
 */

package su.th2empty.kutts.utils

import java.io.InputStream
import java.security.MessageDigest

class Security {
    companion object {
        fun calculateMD5Hash(inputStream: InputStream): String {
            val digest = MessageDigest.getInstance("MD5")
            val buffer = ByteArray(8192)
            var read: Int

            while (inputStream.read(buffer).also { read = it } > 0) {
                digest.update(buffer, 0, read)
            }

            val md5Hash = digest.digest()

            val hexString = StringBuilder()
            for (byte in md5Hash) {
                hexString.append(String.format("%02x", byte))
            }

            return hexString.toString()
        }
    }
}