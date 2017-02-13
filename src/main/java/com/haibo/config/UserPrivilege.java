package com.haibo.config;

import java.util.List;

public class UserPrivilege {

    private List<String> privilegeCodes;
    private String name;

    public List<String> getPrivilegeCodes() {
        return privilegeCodes;
    }

    public void setPrivilegeCodes(List<String> privilegeCodes) {
        this.privilegeCodes = privilegeCodes;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
