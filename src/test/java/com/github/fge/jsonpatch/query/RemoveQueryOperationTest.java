package com.github.fge.jsonpatch.query;

import com.github.fge.jsonpatch.JsonPatchOperationTest;
import org.testng.annotations.Ignore;

import java.io.IOException;

@Ignore
public class RemoveQueryOperationTest extends JsonPatchOperationTest {

    public RemoveQueryOperationTest() throws IOException {
        super("query/remove");
    }
}
