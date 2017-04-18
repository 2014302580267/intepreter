import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by fannie on 2017/1/2.
 */
public class Main extends Thread{
    ParseTree tree = null;
    public void setInput(ParseTree tree){
        this.tree = tree;
    }
    public Main(){}
    public void run(){

        CMMVisitor loader = new EvalVisitor();
        loader.visit(tree);
    }
    private boolean suspend = false;
    private String control = "";
    public void setSuspend(boolean suspend) {
        if (!suspend) {
            synchronized (control) {
                control.notifyAll();
            }
        }
        this.suspend = suspend;
    }
    public static void main(String[] args) throws IOException {

    }
}
