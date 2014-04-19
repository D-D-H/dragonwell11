/*
 * Copyright (c) 2012, 2014, Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This code is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License version 2 only, as
 * published by the Free Software Foundation.
 *
 * This code is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License
 * version 2 for more details (a copy is included in the LICENSE file that
 * accompanied this code).
 *
 * You should have received a copy of the GNU General Public License version
 * 2 along with this work; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 * Please contact Oracle, 500 Oracle Parkway, Redwood Shores, CA 94065 USA
 * or visit www.oracle.com if you need additional information or have any
 * questions.
 */

/*
 * @test
 * @bug 8000418 8024288
 * @summary Verify that files use a common Generated By string
 * @library ../lib/
 * @build JavadocTester TestGeneratedBy
 * @run main TestGeneratedBy
 */

public class TestGeneratedBy extends JavadocTester {

    private static final String OUTPUT_DIR = "tmp";

    private static final String[] FILES = {
        "pkg/MyClass.html",
        "pkg/package-summary.html",
        "pkg/package-frame.html",
        "pkg/package-tree.html",
        "allclasses-noframe.html",
        "constant-values.html",
        "allclasses-frame.html",
        "overview-tree.html",
        "deprecated-list.html",
        "serialized-form.html",
        "help-doc.html",
        "index-all.html",
        "index.html"
    };

    private static final String[] STD_ARGS =
        new String[] {
            "-d", OUTPUT_DIR,
            "-sourcepath", SRC_DIR,
            "pkg"
        };

    private static final String[] NO_TIMESTAMP_ARGS =
        new String[] {
            "-notimestamp",
            "-d", OUTPUT_DIR,
            "-sourcepath", SRC_DIR,
            "pkg"
        };

    private static final String BUG_ID = "8000418-8024288";

    private static String[][] getTests(boolean timestamp) {
        String version = System.getProperty("java.version");
        String[][] tests = new String[FILES.length][];
        for (int i = 0; i < FILES.length; i++) {
            String genBy = "Generated by javadoc";
            if (timestamp) genBy += " (" + version + ") on ";
            tests[i] = new String[] {
                OUTPUT_DIR + "/" + FILES[i], genBy
            };
        }
        return tests;
    }

    private static String[][] getNegatedTests(boolean timestamp) {
        String[][] tests = new String[FILES.length][];
        for (int i = 0; i < FILES.length; i++) {
            tests[i] = new String[] {
                OUTPUT_DIR + "/" + FILES[i],
                (timestamp
                    ? "Generated by javadoc (version"
                    : "Generated by javadoc ("),
                "Generated by javadoc on"
            };
        }
        return tests;
    }

    /**
     * The entry point of the test.
     * @param args the array of command line arguments.
     */
    public static void main(String[] args) {
        TestGeneratedBy tester = new TestGeneratedBy();
        int ec1 = tester.run(STD_ARGS, getTests(true), getNegatedTests(true));
        int ec2 = tester.run(NO_TIMESTAMP_ARGS, getTests(false), getNegatedTests(false));
        tester.printSummary();
        if (ec1 != 0 || ec2 != 0) {
            throw new Error("Error found while executing Javadoc");
        }
    }

    /**
     * {@inheritDoc}
     */
    public String getBugId() {
        return BUG_ID;
    }

    /**
     * {@inheritDoc}
     */
    public String getBugName() {
        return getClass().getName();
    }
}

