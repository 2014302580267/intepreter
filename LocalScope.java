/**
 * Created by fannie on 2016/12/31.
 */
public class LocalScope extends BaseScope {
    public LocalScope(Scope parent) {
        super(parent);
    }

    public String getScopeName() {
        return "locals";
    }
}
