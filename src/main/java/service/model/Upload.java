package service.model;

import org.graalvm.compiler.nodes.memory.MemoryCheckpoint;
import org.springframework.web.multipart.MultipartFile;

public class Upload {

    private String extraField;

    private MultipartFile[] files;

    //getters and setters

    public Upload() { }

    public Upload(String extraField, MultipartFile[] files) {
        this.extraField = extraField;
        this.files = files;
    }

    public MultipartFile[] getFiles() {
        return files;
    }

    public String getExtraField() {
        return extraField;
    }

    public void setExtraField(String extraField) {
        this.extraField = extraField;
    }

    public void setFiles(MultipartFile[] files) {
        this.files = files;
    }
}
