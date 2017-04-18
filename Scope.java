
/**
 * Created by cxq on 16/12/25.
 */
public interface Scope {
    // 变量存在的范围
    public String getScopeName();

    public Scope getParentScope();

    public int define(Symbol sym);

    public Symbol getSymbol(String name);
}
