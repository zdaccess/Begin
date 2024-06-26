package edu.school21.orm;

@OrmEntity(table = "example_table")
public class Example {
    @OrmColumnId
    private Long id;
    @OrmColumn(name = "email", length = 255)
    private String eMail;
    @OrmColumn(name = "city", length = 255)
    private String city;
    @OrmColumn(name = "address", length = 20)
    private String address;
    @OrmColumn(name = "phoneNumber")
    private Long phoneNumber;
    @OrmColumn(name = "woman")
    private Boolean woman;
    @OrmColumn(name = "man")
    private Boolean man;

    public Long getId() {
        return id;
    }

    public String geteMail() {
        return eMail;
    }

    public String getCity() {
        return city;
    }

    public String getAddress() {
        return address;
    }

    public Long getPhoneNumber() {
        return phoneNumber;
    }

    public Boolean getWoman() {
        return woman;
    }

    public Boolean getMan() {
        return man;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void seteMail(String eMail) {
        this.eMail = eMail;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setPhoneNumber(Long phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setWoman(Boolean woman) {
        this.woman = woman;
    }

    public void setMan(Boolean man) {
        this.man = man;
    }
}