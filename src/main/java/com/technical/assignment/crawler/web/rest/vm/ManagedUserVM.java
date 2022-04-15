package com.technical.assignment.crawler.web.rest.vm;

import com.technical.assignment.crawler.service.dto.AdminUserDto;

/**
 * View Model extending the AdminUserDto, which is meant to be used in the user management UI.
 */
public class ManagedUserVM extends AdminUserDto {

    public ManagedUserVM() {
        // Empty constructor needed for Jackson.
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ManagedUserVM{" + super.toString() + "} ";
    }
}
