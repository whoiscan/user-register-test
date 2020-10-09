package com.whoiscan.userregistertest.service;


import com.whoiscan.userregistertest.model.Result;
import com.whoiscan.userregistertest.payload.RegisterReq;

public interface UserService {
    Result saveUser(RegisterReq registerReq);
}

