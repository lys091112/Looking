package com.crescent.alert.engine.provider.parse;

import com.crescent.alert.engine.TestEvent;
import com.crescent.alert.engine.exception.StreamQueryParseError;
import com.crescent.alert.engine.operands.NameOperand;
import com.crescent.alert.engine.provider.ProcessingContext;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ParserEngineTest {

    private IParserEngine engine;

    @Before
    public void setUp() {
        engine = ParserEngineFactory.getParseEngine();
    }

    @Test
    public void testParse() throws Exception {
        String sql = "select val1 from test where val1 > 10 FOR last ?periodTime minute";
        RuleTemplate template = engine.parse(sql);

        Assert.assertNotNull(template);
        Assert.assertTrue(template.getStreams().contains("test"));
        Assert.assertTrue(template.getColumns().size() == 1);
        Assert.assertTrue(((NameOperand) template.getColumns().get(0)).getFieldName().equalsIgnoreCase("val1"));

        Map<String, String> raw = new HashMap<String, String>();
        raw.put("val1", "11");
        raw.put("periodTime", "10");
        TestEvent e1 = new TestEvent("key1", raw, System.currentTimeMillis());

        Assert.assertTrue(template.getWhereClause().getValue(new ProcessingContext(e1, Arrays.asList(e1), null)));
        Assert.assertTrue(template.getResult(e1, Arrays.asList(e1), null).isSuccess());
        System.out.println(template.getResult(e1, Arrays.asList(e1), null).getContent());
    }

//    @Test
//    public void notSupportInvaild() {
//        // min2 --> minimum
//        String sql = "select val1 from test where minute(val1) > 10 FOR last 10 minute";
//        RuleTemplate template = engine.parse(sql);
//        System.out.println(template.getColumns());
//        System.out.println(template.getWhereClause());
//    }

//    @Test(expected = StreamQueryParseError.class)
    @Test
    public void notSupportAggregateFunc() {
        // mi2 --> minimum
        String sql = "select val1 from test where min(val1) > 10 FOR last 10 min";

        RuleTemplate template = engine.parse(sql);
        System.out.println(template.getColumns());
        System.out.println(template.getWhereClause());
    }

    @Test
    public void withoutDurationExpr() {
        // without duration expr
        String sql = "select val1 from test where val1 > 10";

        engine.parse(sql);
    }

    @Test(expected = StreamQueryParseError.class)
    public void durationWithNotSupportedUnit() {
        // without duration expr
//        String sql = "select val1 from test where val1 > 10 for last 10 hour";
        String sql = "select val1 from test where val1 > 10 for last 10 hour";

        engine.parse(sql);
    }

    @Test
    public void durationNormal() {
        String sql = "select val1 from test where val1 > 10 for last ?duration min";

        engine.parse(sql);
    }

}