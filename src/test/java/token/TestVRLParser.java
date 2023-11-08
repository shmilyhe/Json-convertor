package token;

import io.shmilyhe.convert.Json;
import io.shmilyhe.convert.ast.parser.VRLParser;
import io.shmilyhe.convert.ast.statement.Statement;
import io.shmilyhe.convert.tools.ResourceReader;

public class TestVRLParser {
    public static void main(String[] args) {
        String commands =ResourceReader.read("testfile/callee.script");
        System.out.println("===============================");
        System.out.println("===============================");
        System.out.println("===============================");
        VRLParser vrl = new VRLParser();
        Statement stat = vrl.parse(commands);
        stat.clearParent();
        System.out.println(Json.asJsonString(stat));
    }  
}
