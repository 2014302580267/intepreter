import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

/**
 * Created by fannie on 2016/11/15.
 */
public class Test {
    public static void main(String[] args) throws Exception {
        File inputFile = new File("F:\\�μ�\\������\\�����������ĵ�\\2014302580315-������-��������ҵ\\file\\error4.cmm");
//
       // if(args[0]!=null){
        //    inputFile=new File(args[0]);
       // }

        InputStream is = new FileInputStream(inputFile);
        ANTLRInputStream input = new ANTLRInputStream(is);

        CMMLexer lexer = new CMMLexer(input);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        CMMParser parser=new CMMParser(tokens);
        ParseTree tree=parser.program();

        System.out.println(tree.toStringTree(parser));


    }
}
