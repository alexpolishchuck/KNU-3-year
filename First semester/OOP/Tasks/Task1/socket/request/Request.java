package socket.socket.request;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class Request implements Serializable {

    public Request(TypeOfRequest type)
    {
        this.type = type;
    }
    public Request (TypeOfRequest type, String arg1, String arg2)
    {
        this.type = type;
        args = new ArrayList<>();
        args.add(arg1);
        args.add(arg2);
    }
    public Request (TypeOfRequest type, ArrayList<String> args)
    {
        this.type = type;
        this.args = args;
    }
    public ArrayList<String> getArgs() {
        return args;
    }

    public TypeOfRequest getType() {
        return type;
    }
    public enum TypeOfRequest
    {
        DEL("DEL"), ADD("ADD"), EDIT("EDIT"), GET("GET");

        public String getValue() {
            return value;
        }
        public TypeOfRequest fromString(String val)
        {
            if(val.compareTo(DEL.value)==0)
                return DEL;
            else if(val.compareTo(ADD.value)==0)
                return ADD;
            else if(val.compareTo(EDIT.value)==0)
                return EDIT;
            else if(val.compareTo(GET.value)==0)
                return GET;
            else return null;
        }
        private String value ;
        private TypeOfRequest(String i) {
            value  =i;
        }
    }
    private TypeOfRequest type;
    private ArrayList<String> args;

}
