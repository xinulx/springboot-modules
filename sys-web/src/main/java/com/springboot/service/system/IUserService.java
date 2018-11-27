package com.springboot.service.system;

import com.springboot.vo.ResponseData;

public interface IUserService {
    ResponseData selectByUserAccount(String userAccount);
}
