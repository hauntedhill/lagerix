package de.hscoburg.etif.vbis.lagerix.backend.interfaces.dto;
 
import de.hscoburg.etif.vbis.lagerix.backend.interfaces.dto.base.BaseDTO;
import java.util.Map;
import javax.xml.bind.annotation.XmlRootElement;
 

/**
 * 
 * @author zuch1000
 */
@XmlRootElement 
public class JsonResponse extends BaseDTO{
     
    private static final float version = 1.0f;  
 
    private String status;
    private String errorMsg;
    private Map<String, Object> fieldErrors;
    private Object data;
 
    public JsonResponse() {
    }
     
    public JsonResponse(String status) {
        this.status = status;
    }    
     
    //@XmlElement //we don't need this thanks to Jackson
    public float getVersion() {
        return JsonResponse.version;
    }
         
    public String getStatus() {
        return status;
    }
 
    public void setStatus(String status) {
        this.status = status;
    }
     
    public String getErrorMsg() {
        return errorMsg;
    }
 
    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }
 
    public Map<String, Object> getFieldErrors() {
        return fieldErrors;
    }
 
    public void setFieldErrors(Map<String, Object> fieldErrors) {
        this.fieldErrors = fieldErrors;
    }
     
    public Object getData() {
        return data;
    }
 
    public void setData(Object data) {
        this.data = data;
    }
     
}