/**
 * Created by cxq on 16/1/2.
 */

import java.util.LinkedHashMap;
import java.util.Map;
public abstract class BaseScope implements Scope{
    Scope parentScope;
    Map<String,Symbol> symbols =new LinkedHashMap<String,Symbol>();

    public BaseScope(Scope parentScope){
        this.parentScope=parentScope;
    }

    public Symbol getSymbol(String name){
        Symbol s=symbols.get(name);
        if(s!=null) return s;
        if(parentScope!=null) return parentScope.getSymbol(name);
        return null;
    }

    public Symbol findInScope(String name){
        Symbol s=symbols.get(name);
        if(s!=null) return s;
        return null;
    }
    
    public int define(Symbol sym){
        if(findInScope(sym.getName())==null){
            symbols.put(sym.name,sym);
            sym.scope=this;
            return 0;
        }
        else{
            return -1;
        }
    }

    public Scope getParentScope(){return parentScope;}
    public String toString(){
        return getScopeName()+":"+symbols.keySet().toString();
    }

}
