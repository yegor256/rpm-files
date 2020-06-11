/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2020 artipie.com
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included
 * in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NON-INFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package com.artipie.rpm.misc;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;

/**
 * File in directory.
 * @since 0.9
 * @todo #226:30min Add one more (at least) test for this class to check that
 *  IllegalArgumentException is thrown if file is not found. After that replace `Rpm#meta`
 *  and `XmlMetaJoinITCase#meta` usages with this class usages. Check that the project has no more
 *  code to replace with this class usage.
 */
public final class FileInDir {

    /**
     * Directory.
     */
    private final Path dir;

    /**
     * Ctor.
     * @param dir Directory
     */
    public FileInDir(final Path dir) {
        this.dir = dir;
    }

    /**
     * Searches for the file by subst in the directory.
     * @param substr File name substr
     * @return Path to the file
     * @throws IOException On Error
     * @throws IllegalArgumentException if not found
     */
    public Path find(final String substr) throws IOException {
        try (Stream<Path> files = Files.walk(this.dir)) {
            return files.filter(path -> path.getFileName().toString().contains(substr))
                .findFirst().orElseThrow(
                    () -> new IllegalArgumentException(
                        String.format(
                            "Metafile %s does not exists in %s", substr, this.dir.toString()
                        )
                    )
                );
        }
    }

}
