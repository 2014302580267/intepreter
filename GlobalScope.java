/**
 * Created by cxq on 17/1/2.
 */
public class GlobalScope extends BaseScope {
    public GlobalScope(Scope parentScope){
        super(parentScope);
    }
    public String getScopeName() {
        return "globals";
    }
}
