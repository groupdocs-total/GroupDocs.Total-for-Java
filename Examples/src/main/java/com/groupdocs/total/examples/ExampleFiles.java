package com.groupdocs.total.examples;

import java.io.File;

/**
 * Resolves sample inputs next to the calling example's {@code .java} file, and
 * writes results under {@code Examples/output/<library>/}.
 */
public final class ExampleFiles {

    private ExampleFiles() {
    }

    public static String path(String fileName) {
        return new File(sourceDir(), fileName).getAbsolutePath();
    }

    public static String output(String fileName) {
        File file = new File(outputDir(), fileName);
        File parent = file.getParentFile();
        if (parent != null && !parent.exists() && !parent.mkdirs()) {
            throw new IllegalStateException("Cannot create output directory: " + parent);
        }
        return file.getAbsolutePath();
    }

    public static File sourceDir() {
        Class<?> example = callingExample();
        String relative = "src" + File.separator + "main" + File.separator + "java"
                + File.separator + example.getPackage().getName().replace('.', File.separatorChar);
        File dir = new File(examplesRoot(), relative);
        if (new File(dir, example.getSimpleName() + ".java").isFile()) {
            return dir;
        }
        throw new IllegalStateException(
                "Cannot locate source directory for " + example.getName()
                        + " (looked in " + dir + ")");
    }

    public static File outputDir() {
        Class<?> example = callingExample();
        String pkg = example.getPackage().getName();
        String library = pkg.substring(pkg.lastIndexOf('.') + 1);
        File dir = new File(examplesRoot(), "output" + File.separator + library);
        if (!dir.exists() && !dir.mkdirs()) {
            throw new IllegalStateException("Cannot create output directory: " + dir);
        }
        return dir;
    }

    /**
     * Finds the Examples module whether Maven was started from {@code Examples/}
     * or from the repository root ({@code mvn -f Examples/pom.xml}).
     */
    private static File examplesRoot() {
        File cursor = new File(System.getProperty("user.dir")).getAbsoluteFile();
        for (int i = 0; i < 8 && cursor != null; i++) {
            File direct = asExamplesRoot(cursor);
            if (direct != null) {
                return direct;
            }
            File nested = asExamplesRoot(new File(cursor, "Examples"));
            if (nested != null) {
                return nested;
            }
            cursor = cursor.getParentFile();
        }
        throw new IllegalStateException(
                "Cannot locate the Examples folder from user.dir="
                        + System.getProperty("user.dir"));
    }

    private static File asExamplesRoot(File dir) {
        if (dir != null && dir.isDirectory()
                && new File(dir, "pom.xml").isFile()
                && new File(dir, "src").isDirectory()) {
            return dir;
        }
        return null;
    }

    private static Class<?> callingExample() {
        StackTraceElement[] stack = Thread.currentThread().getStackTrace();
        for (int i = 0; i < stack.length; i++) {
            String name = stack[i].getClassName();
            if (name.equals(Thread.class.getName()) || name.equals(ExampleFiles.class.getName())) {
                continue;
            }
            try {
                return Class.forName(name);
            } catch (ClassNotFoundException e) {
                throw new IllegalStateException(e);
            }
        }
        throw new IllegalStateException("Cannot determine calling example class");
    }
}
