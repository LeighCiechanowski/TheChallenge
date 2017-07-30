package Model;

public class FileChunk {

    public void setStart(int start) {
        this.start = start;
    }

    public void setEnd(int end) {
        this.end = end;
    }

    public void setComplete(Boolean complete) {
        this.complete = complete;
    }

    public void setProcessing(Boolean processing) {
        this.processing = processing;
    }
    private long start;
    private long end;
    private Boolean complete;
    private Boolean processing;

    public long getStart() {
        return start;
    }

    public long getEnd() {
        return end;
    }

    public Boolean getComplete() {
        return complete;
    }

    public Boolean getProcessing() {
        return processing;
    }

    public FileChunk(long start, long end, Boolean complete, Boolean processing) {
        this.start = start;
        this.end = end;
        this.complete = complete;
        this.processing = processing;
    }
    
}
