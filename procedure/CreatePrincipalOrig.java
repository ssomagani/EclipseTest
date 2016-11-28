/* This file is part of VoltDB.
 * Copyright (C) 2008-2016 VoltDB Inc.
 *
 * Permission is hereby granted, free of charge, to any person obtaining
 * a copy of this software and associated documentation files (the
 * "Software"), to deal in the Software without restriction, including
 * without limitation the rights to use, copy, modify, merge, publish,
 * distribute, sublicense, and/or sell copies of the Software, and to
 * permit persons to whom the Software is furnished to do so, subject to
 * the following conditions:
 *
 * The above copyright notice and this permission notice shall be
 * included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT.
 * IN NO EVENT SHALL THE AUTHORS BE LIABLE FOR ANY CLAIM, DAMAGES OR
 * OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE,
 * ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR
 * OTHER DEALINGS IN THE SOFTWARE.
 */


/**
 * Identity service POC. This is a VoltDB procedure to create new principals.
 */


import org.voltdb.SQLStmt;
import org.voltdb.VoltProcedure;

public class CreatePrincipalOrig extends VoltProcedure { 

    // potential return codes (syncd with client app)
    static final long SUCCESS = 0;
    static final long ERR_FOOBAR = 1;

    // insert a new principal
    public final SQLStmt insertStmt = new SQLStmt("INSERT INTO principals (id, kind, note) VALUES (?, ?, ?);");

    /*
     * Insert the specified principal into volt
     */
    public long run(String id, String kind, String note) {
        // queue insert
        voltQueueSQL(insertStmt, EXPECT_SCALAR_MATCH(1), id, kind, note);
        // and execute
        voltExecuteSQL(true);

        // ##handle errors?
        // return success
        return SUCCESS;
    }
}
