package com.james.medpay.features.authentication.data.datasource;

import com.james.medpay.features.authentication.domain.entity.Permission;
import com.james.medpay.features.authentication.domain.entity.Role;
import com.james.medpay.features.authentication.domain.repository.dataRepository.IAuthPermissionDataRepository;
import com.james.medpay.features.authentication.domain.repository.dataRepository.IAuthRoleDataRepository;
import com.james.medpay.features.users.domain.entity.UserBasicDetailsEntity;
import com.james.medpay.features.users.domain.entity.UserEntity;
import com.james.medpay.features.users.domain.entity.contract.IUserEntity;
import com.james.medpay.features.users.domain.repository.contract.IUserEntityRepository;
import com.james.medpay.features.users.domain.repository.dataRepository.IUserBasicDetailsDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.validation.constraints.Null;
import java.time.LocalDate;
import java.util.*;

import static com.james.medpay.core.constant.AuthPermissions.*;
import static com.james.medpay.core.constant.DatabaseTables.DEFAULT_SUPER_ADMIN_ROLE;
import static com.james.medpay.features.authentication.domain.entity.enums.AuthModuleEnum.*;
import static com.james.medpay.features.users.domain.entity.enums.UserPreferredNameEnum.LAST_NAME;
import static com.james.medpay.features.users.domain.entity.enums.UserTypeEnum.SUPER_USER;

@Component
public class AuthPermissionSeeder implements CommandLineRunner {
    @Autowired
    private IUserEntityRepository userDataRepository;
    @Autowired
    private IUserBasicDetailsDataRepository detailsDataRepository;
    @Autowired
    private IAuthRoleDataRepository roleRepository;
    @Autowired
    private IAuthPermissionDataRepository permissionRepository;

    @Value(value = "${app.system.username}")
    public static String SUPER_ADMIN_USERNAME;

    @Value(value = "${app.system.email}")
    public static String SUPER_ADMIN_EMAIL;

    @Value(value = "${app.system.password}")
    public static String SUPER_ADMIN_PASSWORD;

    private Set<Permission> permissions = null;
    private Role role = null;

    @Override
    public void run(String... args) throws Exception {
        this.seed();
    }

    public void seed() {
        this.seedDefaultPermissions();
        this.seedDefaultRole();
        this.seedDefaultSystemUser();
    }

    private void seedDefaultRole() {
        Optional<Role> optional = this.roleRepository.findByName(DEFAULT_SUPER_ADMIN_ROLE);
        if (optional.isEmpty()) {
            Role role = new Role();
            role.setName(DEFAULT_SUPER_ADMIN_ROLE);
            role.setDescription("Default Role");
            role.setPermissions(this.permissions);
            this.role = this.roleRepository.save(role);
            System.out.println("----- seeded role ----");
        }
    }

    private void seedDefaultSystemUser() {
        if (SUPER_ADMIN_USERNAME != null && SUPER_ADMIN_PASSWORD != null && SUPER_ADMIN_EMAIL != null) {
            Optional<IUserEntity> optional = this.userDataRepository.getByEmail(SUPER_ADMIN_USERNAME);
            if (optional.isEmpty()) {
                UserEntity user = new UserEntity();
                user.setNickName(SUPER_ADMIN_USERNAME);
                user.setEmail(SUPER_ADMIN_EMAIL);
                user.setExpiryDate(LocalDate.MAX);
                user.setPassword(SUPER_ADMIN_PASSWORD);
                user.setRoles(Set.of(this.role));
                user.setUserTypeEnum(SUPER_USER);
                this.setUserBasicDetails(user);
                this.userDataRepository.createUser(user);
                System.out.println("----- seeded user ----");
            }
        } else {
			System.out.println("No user provided in application properties");
		}
    }

    private void setUserBasicDetails(UserEntity user) {
        UserBasicDetailsEntity details = new UserBasicDetailsEntity();
        details.setFirstName("System");
        details.setLastName("User");
        details.setPreferredName(LAST_NAME);
        UserBasicDetailsEntity save = this.detailsDataRepository.save(details);
        user.setBasicDetails(save);
    }

    private void seedDefaultPermissions() {
        List<Permission> all = this.permissionRepository.findAll();
        if (all.isEmpty()) {
            Set<Permission> permissions = new HashSet<>();
            addAuthPermissions(permissions);
            addUserPermissions(permissions);
            addTransactionPermissions(permissions);
            addHospitalPermissions(permissions);
            addGlobalSettingPermissions(permissions);
            addServiceGroupPermissions(permissions);
            addRevenueHeadPermissions(permissions);
            addServicePermissions(permissions);
            List<Permission> list = this.permissionRepository.saveAll(permissions);
            this.permissions = new HashSet<>(list);
            System.out.println("------ seeded permissions  -------");
        }

    }

    private void addAuthPermissions(Set<Permission> permissions) {
        for (String s : Arrays.asList(ACCESS_AUTH_PAGE_USER, ACCESS_AUTH_PAGE_ROLE, ACCESS_AUTH_PAGE_PERMISSION,
                ACCESS_AUTH_ACTION_USER_ASSIGN_ROLE, ACCESS_AUTH_ACTION_ROLE_CREATE, ACCESS_AUTH_ACTION_ROLE_UPDATE,
                ACCESS_AUTH_ACTION_ROLE_STATUS, ACCESS_AUTH_ACTION_PERMISSION_VIEW)) {
            permissions.add(new Permission(s, AUTHENTICATION));
        }
    }

    private void addUserPermissions(Set<Permission> permissions) {
        for (String s : Arrays.asList(ACCESS_USER_PAGE_VIEW, ACCESS_USER_PAGE_CREATE, ACCESS_USER_PAGE_REPORT,
                ACCESS_USER_ACTION_VIEW, ACCESS_USER_ACTION_CREATE, ACCESS_USER_ACTION_UPDATE,
                ACCESS_USER_ACTION_DOWNLOAD_REPORT)) {
            permissions.add(new Permission(s, USER));
        }
    }

    private void addTransactionPermissions(Set<Permission> permissions) {
        for (String s : Arrays.asList(
                ACCESS_TRANSACTION_PAGE_VIEW, ACCESS_TRANSACTION_PAGE_PAYMENT_STATUS,
                ACCESS_TRANSACTION_PAGE_PAYMENT_DETAILS, ACCESS_TRANSACTION_PAGE_RECONCILE_PAYMENT,
                ACCESS_TRANSACTION_PAGE_REPRINT, ACCESS_TRANSACTION_PAGE_REPORT,
                ACCESS_TRANSACTION_ACTION_VIEW, ACCESS_TRANSACTION_ACTION_CREATE,
                ACCESS_TRANSACTION_ACTION_UPDATE, ACCESS_TRANSACTION_ACTION_DOWNLOAD_REPORT)) {
            permissions.add(new Permission(s, TRANSACTION));
        }
    }

    private void addHospitalPermissions(Set<Permission> permissions) {
        for (String s : Arrays.asList(
                ACCESS_HOSPITAL_PAGE_VIEW, ACCESS_HOSPITAL_PAGE_CREATE,
                ACCESS_HOSPITAL_PAGE_CONFIGURE, ACCESS_HOSPITAL_ACTION_VIEW, ACCESS_HOSPITAL_ACTION_CREATE,
                ACCESS_HOSPITAL_ACTION_UPDATE, ACCESS_HOSPITAL_ACTION_DOWNLOAD_REPORT)) {
            permissions.add(new Permission(s, HOSPITAL));
        }
    }

    private void addGlobalSettingPermissions(Set<Permission> permissions) {
        for (String s : Arrays.asList(
                ACCESS_GLOBAL_SETTING_PAGE_HOSPITAL, ACCESS_GLOBAL_SETTING_PAGE_USER,
                ACCESS_GLOBAL_SETTING_ROLE_PERMISSION, ACCESS_GLOBAL_SETTING_TRANSACTION)) {
            permissions.add(new Permission(s, GLOBAL_SETTING));
        }
    }

    private void addServiceGroupPermissions(Set<Permission> permissions) {
        for (String s : Arrays.asList(ACCESS_SERVICE_GROUP_PAGE_CREATE, ACCESS_SERVICE_GROUP_PAGE_MANAGE)) {
            permissions.add(new Permission(s, SERVICE_GROUP));
        }
    }

    private void addRevenueHeadPermissions(Set<Permission> permissions) {
        for (String s : Arrays.asList(ACCESS_REVENUE_HEAD_PAGE_CREATE, ACCESS_REVENUE_HEAD_PAGE_MANAGE)) {
            permissions.add(new Permission(s, REVENUE_HEAD));
        }
    }

    private void addServicePermissions(Set<Permission> permissions) {
        for (String s : Arrays.asList(ACCESS_SERVICE_PAGE_CREATE, ACCESS_SERVICE_PAGE_MANAGE)) {
            permissions.add(new Permission(s, SERVICE));
        }
    }

}
