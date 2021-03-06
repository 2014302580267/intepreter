/**
 * Created by cxq on 2016/12/30.
 */
public class Symbol {
    public static enum Type {INVALID, DOUBLE, INT, BOOL, CHAR,
        DOUBLE_LIST, INT_LIST, BOOL_LIST, CHAR_LIST};

    String name;
    Type type;

    Scope scope;     // all symbols have an attribute called scope
    public Symbol(){}
    public Symbol(String name) { this.name = name; }
    public Symbol(String name, Type type) { this(name); this.type = type; }
    public String getName() { return name; }

    public String toString(){
        if(type!=Type.INVALID) return '<'+getName()+":"+type+'>';
        return getName();
    }
}
