/**
 * Created by wzq on 1/1/17.
 */
public class Identifier extends Symbol{
    private String varType = null;//int|double|char|bool
    private String dType = null;//const|array|variable
    private String id = null;
    private String value = "0";

    public Identifier(){}
    //dType
    public Identifier(String type){
        this.dType = type;
    }
    //varType
    public Identifier(String varType, String type) {
        this.varType = varType;
        this.dType = type;
    }
    public Identifier(String varType, String type, String id) {
        this.varType = varType;
        this.dType = type;
        this.id = id;
        this.name = id;
    }
    public String getVarType() {
        return varType;
    }

    public void setVarType(String varType) {
        this.varType = varType;
    }

    public String getType() {
        return dType;
    }

    public void setType(String type) {
        this.dType = type;
    }



    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
        this.name = id;
    }



    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }


}
