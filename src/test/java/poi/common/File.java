package poi.common;

public class File {

    public File(int num, String fileName) {
        this.num = num;
        this.fileName = fileName;
    }

    private int num;
    private String fileName;

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}
