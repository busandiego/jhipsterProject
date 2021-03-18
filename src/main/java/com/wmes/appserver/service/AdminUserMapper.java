package com.wmes.appserver.service;

import com.wmes.appserver.domain.AdminUser;
import com.wmes.appserver.dto.AdminUserResponseDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {})
public interface AdminUserMapper extends EntityMapper<AdminUserResponseDto, AdminUser> {


    default AdminUser fromId(Long id) {
        if (id == null) {
            return null;
        }
        AdminUser adminUser = new AdminUser();
        adminUser.setId(id);
        return adminUser;
    }

}
