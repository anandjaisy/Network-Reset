package ramsay.health;

import jakarta.inject.Singleton;

@Singleton
public final class UserClaimsService implements IUserClaimsService{
    private String userId;
    private String email;
    private String familyName;
    private String givenName;
    private String name;

    @Override public void setUserId(String userId) { this.userId = userId; }
    @Override public void setEmail(String email) { this.email = email; }
    @Override public void setFamilyName(String familyName) { this.familyName = familyName; }
    @Override public void setGivenName(String givenName) { this.givenName = givenName; }
    @Override public void setName(String name) { this.name = name; }

    @Override public String getUserId() { return userId; }
    @Override public String getEmail() { return email; }
    @Override public String getFamilyName() { return familyName; }
    @Override public String getGivenName() { return givenName; }
    @Override public String getName() { return name; }
    }
