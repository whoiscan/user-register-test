package com.whoiscan.userregistertest.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserReq {
//    @Size(min = 3, message = "Full name must be minimum 3 characters.")
    @NotNull
    private String fullName;
    private boolean roleAdmin;
    private boolean roleModer;
    private boolean roleUser;
    private boolean accountNonLocked;
 }
