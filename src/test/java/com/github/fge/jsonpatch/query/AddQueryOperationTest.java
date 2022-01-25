package com.github.fge.jsonpatch.query;

import com.github.fge.jsonpatch.JsonPatchOperationTest;
import org.testng.annotations.Ignore;

import java.io.IOException;

@Ignore
public class AddQueryOperationTest extends JsonPatchOperationTest {

    public AddQueryOperationTest() throws IOException {
        super("query/add");
    }
}
