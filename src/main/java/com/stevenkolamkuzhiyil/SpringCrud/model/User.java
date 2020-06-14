package com.stevenkolamkuzhiyil.SpringCrud.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Inheritance
public abstract class User {

    @Id
    @Column(name = "emp_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected long userId;

    @Email
    @Column(name = "email", nullable = false, unique = true)
    protected String email;
    @JsonIgnore
    @Column(name = "password", nullable = false)
    protected String password;
    @JsonIgnore
    @Column(name = "enabled", nullable = false)
    protected boolean enabled;
    @JsonIgnore
    @Column(name = "role", nullable = false)
    protected String roles;
    @JsonIgnore
    @Column(name = "permissions", nullable = false)
    protected String permissions;

    protected long getUserId() {
        return userId;
    }

    protected void setUserId(long userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getRoles() {
        return roles;
    }

    public void setRoles(String roles) {
        this.roles = roles;
    }

    public String getPermissions() {
        return permissions;
    }

    public void setPermissions(String permissions) {
        this.permissions = permissions;
    }

    public void setPermissions(Set<SimpleGrantedAuthority> permissions) {
        this.permissions = permissions
                .stream()
                .map(SimpleGrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));
    }

    @JsonIgnore
    public List<String> getRolesAsList() {
        return commaSeparatedStringToListAndRemoveEmptyAndNull(this.roles);
    }

    public void addRole(String role) {
        this.roles = addStringToCommaSeparatedStrings(this.roles, role);
    }

    public void removeRole(String role) {
        this.roles = removeStringFromCommaSeparatedStrings(this.roles, role);
    }

    @JsonIgnore
    public List<String> getPermissionsAsList() {
        return commaSeparatedStringToListAndRemoveEmptyAndNull(this.permissions);
    }

    public void addPermission(String permission) {
        this.permissions = addStringToCommaSeparatedStrings(this.permissions, permission);
    }

    public void removePermission(String permission) {
        this.permissions = removeStringFromCommaSeparatedStrings(this.permissions, permission);
    }

    private List<String> commaSeparatedStringToListAndRemoveEmptyAndNull(String str) {
        List<String> list = new ArrayList<>(Arrays.asList(str.split(",")));
        list.removeAll(Arrays.asList("", null));
        return list;
    }

    private String addStringToCommaSeparatedStrings(String str, String strToAdd) {
        if (!str.contains(strToAdd)) {
            return str.isEmpty() ? strToAdd : str + "," + strToAdd;
        }
        return str;
    }

    private String removeStringFromCommaSeparatedStrings(String str, String strToRemove) {
        if (str.contains(strToRemove)) {
            return str.replaceAll(strToRemove, "")
                    .replaceAll(",,", "")
                    .replaceAll("^,+", "")
                    .replaceAll(",+$", "");
        }
        return str;
    }
}
