package de.hscoburg.etif.vbis.lagerix.backend.service.dto;



import de.hscoburg.etif.vbis.lagerix.backend.interfaces.dto.base.BaseDTO;
import java.util.Map;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Respone DTO for JSON
 *
 * @author zuch1000
 */
@XmlRootElement
public class JsonResponseDTO extends BaseDTO {

    private static final float version = 1.0f;

    private String status;
    private String errorMsg;
    private Map<String, Object> fieldErrors;
    private Object data;

    public JsonResponseDTO() {
    }

    public JsonResponseDTO(String status) {
        this.status = status;
    }

    //@XmlElement //we don't need this thanks to Jackson
    public float getVersion() {
        return JsonResponseDTO.version;
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

    @Override
    public String toString() {
        return "JsonResponseDTO{" + "status=" + status + ", errorMsg=" + errorMsg + ", fieldErrors=" + fieldErrors + ", data=" + data + '}';
    }

}
