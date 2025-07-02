package ramsay.health;

public sealed interface IUserClaimsService permits UserClaimsService{
    void setUserId(String userId);
    void setEmail(String email);
    void setFamilyName(String familyName);
    void setGivenName(String givenName);
    void setName(String name);

    String getUserId();
    String getEmail();
    String getFamilyName();
    String getGivenName();
    String getName();
}
