package mogujie.sql.monitor.entity.product;

public class StringProduct implements Product {

    private static final long serialVersionUID = 1268938557200134012L;
    private String line;

    public StringProduct(String line) {
        this.line = line;
    }

    public String getLine() {
        return line;
    }

    public void setLine(String line) {
        this.line = line;
    }

}
