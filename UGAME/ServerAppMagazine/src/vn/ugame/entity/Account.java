/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package vn.ugame.entity;

/**
 *
 * @author TruongDV
 */
@Entity
public class Account {

    @Column(name = "AccountId")
    private int accountId;
    @Column(name = "PhoneNumber")
    private String phoneNumber;
    @Column(name = "ActiveCode")
    private int activeCode;
    @Column(name = "Status")
    private int status;

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public int getActiveCode() {
        return activeCode;
    }

    public void setActiveCode(int activeCode) {
        this.activeCode = activeCode;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Account() {
    }
}
