package com.github.fge.jsonpatch;

import org.testng.annotations.Test;

import static org.testng.Assert.*;

public class JsonPathParserTest {

    @Test
    public void shouldConvertQueryToJsonPath() {
        String jsonPointerWithQuery = "/productPrice/prodPriceAlteration?productPrice.name=Regular Price";
        String expected = "$.productPrice[?(@.name=='Regular Price')].prodPriceAlteration";
        String result = JsonPathParser.tmfStringToJsonPath(jsonPointerWithQuery);
        assertEquals(result, expected);
    }

    @Test
    public void shouldConvertArrayPathToJsonPath() {
        String jsonPointerWithQuery = "/2/1/-";
        String expected = "$.[2].[1].-";
        String result = JsonPathParser.tmfStringToJsonPath(jsonPointerWithQuery);
        assertEquals(result, expected);
    }

    @Test
    public void shouldConvertManyConditions() {
        String jsonPointerWithQuery = "/orderItem/quantity?orderItem.productOffering.id=1513&orderItem.product.relatedParty.role=customer&orderItem.product.relatedParty.name=Mary";
        String expected = "$.orderItem[?(@.productOffering.id=='1513' && @.product.relatedParty.role=='customer' && @.product.relatedParty.name=='Mary')].quantity";
        String result = JsonPathParser.tmfStringToJsonPath(jsonPointerWithQuery);
        assertEquals(result, expected);
    }

}