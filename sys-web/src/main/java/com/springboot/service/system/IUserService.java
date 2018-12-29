package com.springboot.service.system;

import com.springboot.entity.vo.ResponseData;

public interface IUserService {
    ResponseData selectByUserAccount(String userAccount);
}
