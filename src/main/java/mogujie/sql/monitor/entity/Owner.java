/**
 * 
 */
package mogujie.sql.monitor.entity;

/**
 * @author zhaoxi LinZuxiong
 * @email linzuxiong1988@gmail.com
 *
 */
public class Owner implements java.io.Serializable {
    private static final long serialVersionUID = -5669823790182163227L;
    private String name;
    /**
     * nick name in the company
     */
    private String nick;
    /**
     * alarm phone N.O.
     */
    private String phone;

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name
     *            the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the nick
     */
    public String getNick() {
        return nick;
    }

    /**
     * @param nick
     *            the nick to set
     */
    public void setNick(String nick) {
        this.nick = nick;
    }

    /**
     * @return the phone
     */
    public String getPhone() {
        return phone;
    }

    /**
     * @param phone
     *            the phone to set
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

}
