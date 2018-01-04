package com.crescent.alert.engine.provider.parse;

import com.crescent.alert.engine.TestEvent;
import com.crescent.alert.engine.exception.StreamQueryParseError;
import com.crescent.alert.engine.operands.NameOperand;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ParserEngineTest {

    private IParserEngine engine;

    @Before
    public void setUp() {

        engine = new ParserEngine();
    }

    @Test
    public void testParse() throws Exception {
        String sql = "select val1 from test where count(val1) > 10 FOR last 10 minute";
        RuleTemplate template = engine.parse(sql);

        Assert.assertNotNull(template);
        Assert.assertTrue(template.getStreams().contains("test"));
        Assert.assertTrue(template.getColumns().size() == 1);
        Assert.assertTrue(((NameOperand) template.getColumns().get(0)).getFieldName().equalsIgnoreCase("val1"));

        Map<String, String> raw = new HashMap<String, String>();
        raw.put("val1", "11");
        TestEvent e1 = new TestEvent("key1", raw, UUID.randomUUID(), System.currentTimeMillis(), 1L, 2L);

        Assert.assertTrue(template.getWhereClause().getValue(null, Arrays.asList(e1), null));
    }

//    @Test
//    public void notSupportInvaild() {
//        // min2 --> minimum
//        String sql = "select val1 from test where minute(val1) > 10 FOR last 10 minute";
//        RuleTemplate template = engine.parse(sql);
//        System.out.println(template.getColumns());
//        System.out.println(template.getWhereClause());
//    }

    @Test(expected = StreamQueryParseError.class)
    public void notSupportAggregateFunc() {
        // mi2 --> minimum
        String sql = "select val1 from test where min2(val1) > 10 FOR last 10 minute";

        RuleTemplate template = engine.parse(sql);
        System.out.println(template.getColumns());
        System.out.println(template.getWhereClause());
    }

    @Test(expected = StreamQueryParseError.class)
    public void withoutDurationExpr() {
        // without duration expr
        String sql = "select val1 from test where val1 > 10";

        engine.parse(sql);
    }

    @Test(expected = StreamQueryParseError.class)
    public void durationWithNotSupportedUnit() {
        // without duration expr
        String sql = "select val1 from test where val1 > 10 for last 10 hour";

        engine.parse(sql);
    }

}