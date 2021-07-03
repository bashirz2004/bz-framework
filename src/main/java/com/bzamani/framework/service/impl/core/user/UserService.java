package com.bzamani.framework.service.impl.core.user;

import com.bzamani.framework.common.utility.DateUtility;
import com.bzamani.framework.common.utility.SecurityUtility;
import com.bzamani.framework.common.utility.Utility;
import com.bzamani.framework.dto.UserSelfRegistrationDto;
import com.bzamani.framework.model.core.group.Group;
import com.bzamani.framework.model.core.organization.Organization;
import com.bzamani.framework.model.core.personel.Personel;
import com.bzamani.framework.model.core.user.User;
import com.bzamani.framework.repository.core.user.IUserRepository;
import com.bzamani.framework.service.core.action.IActionService;
import com.bzamani.framework.service.core.group.IGroupService;
import com.bzamani.framework.service.core.organization.IOrganizationService;
import com.bzamani.framework.service.core.personel.IPersonelService;
import com.bzamani.framework.service.core.user.IUserService;
import com.bzamani.framework.service.impl.core.GenericService;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.net.URISyntaxException;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserService extends GenericService<User, Long> implements IUserService {

    @Autowired
    IUserRepository iUserRepository;

    @Autowired
    IActionService iActionService;

    @Autowired
    IPersonelService iPersonelService;

    @Autowired
    JavaMailSender emailSender;

    @Autowired
    IOrganizationService iOrganizationService;

    @Autowired
    IGroupService iGroupService;

    @Value("${bzamani.organization-id-for-guest-users}") //read from application.yml file
    Long guestOrganizationId;

    @Override
    protected JpaRepository<User, Long> getGenericRepo() {
        return iUserRepository;
    }

    @Override
    public User findUserByUsernameEquals(String username) {
        return iUserRepository.findUserByUsernameEquals(username);
    }

    @Override
    @Transactional
    public User save(User user) { //save User Only, sets will not change
        if (user.getId() == null) {
            PasswordEncoder encoder = new BCryptPasswordEncoder();
            user.setPassword(encoder.encode(user.getPassword().trim()));
            user.setAccountNonExpired(true);
            user.setCredentialsNonExpired(true);
            user.setAccountNonLocked(true);
            user.setEnabled(true);
            user.setUserExpireDateShamsi(DateUtility.todayShamsi(365));
            user.setPasswordExpireDateShamsi(DateUtility.todayShamsi(365));
        } else {
            user.setPassword(loadByEntityId(user.getId()).getPassword()); //should not change
            user.setPersonel(loadByEntityId(user.getId()).getPersonel()); //should not change
            user.setOrganizations(loadByEntityId(user.getId()).getOrganizations());//should not change
            user.setGroups(loadByEntityId(user.getId()).getGroups());//should not change
        }
        return super.save(user);
    }

    @Override
    @Transactional
    public User saveUserWithSets(User user) { //bayad khode karbar havasesh be set ha bashe
        return super.save(user);
    }

    @Override
    @Transactional
    public User selfRegister(UserSelfRegistrationDto userDto) {
        checkInputData(userDto.getMobile());

        Personel p = new Personel();
        p.setFirstname(userDto.getFirstname().trim().length() == 0 ? "کاربر" : userDto.getFirstname().trim());
        p.setLastname(userDto.getLastname().trim().length() == 0 ? userDto.getMobile().trim() : userDto.getLastname().trim());
        p.setMale(userDto.getMale());
        p.setNationalCode(userDto.getNationalCode().trim().length() == 0 ? null : userDto.getNationalCode().trim());
        p.setMobile(userDto.getMobile().trim());
        p.setEmail(userDto.getEmail().trim().length() == 0 ? null : userDto.getEmail().trim());
        Organization org = new Organization();
        org.setId(guestOrganizationId);
        p.setOrganization(org);
        iPersonelService.save(p);

        User newUser = new User();
        newUser.setPersonel(p);
        newUser.setPassword(userDto.getPassword().trim());
        newUser.setUsername(userDto.getMobile().trim());
        newUser.setAccountNonExpired(true);
        newUser.setCredentialsNonExpired(true);
        newUser.setAccountNonLocked(true);
        newUser.setEnabled(true);
        newUser.setUserExpireDateShamsi(DateUtility.todayShamsi(365));
        newUser.setPasswordExpireDateShamsi(DateUtility.todayShamsi(365));
        Set<Organization> organizations = new HashSet<>();
        organizations.add(org);
        newUser.setOrganizations(organizations);
        Set<Group> groups = new HashSet<>();
        Group group = new Group();
        group.setId(1L); //group for public users
        groups.add(group);
        newUser.setGroups(groups);
        return save(newUser);
    }

    public void checkInputData(String username) {
        if (iOrganizationService.loadByEntityId(guestOrganizationId) == null)
            throw new RuntimeException("واحد سازمانی برای کاربران پورتال تعریف نشده است.");
        List<User> usersCreatedByCurrentIP = iUserRepository.findAllByIpOrderByLastUpdateDateDesc(SecurityUtility.getRequestIp());
        if (usersCreatedByCurrentIP != null && usersCreatedByCurrentIP.size() > 0)
            if (DateUtility.getDiffSeconds(usersCreatedByCurrentIP.get(0).getLastUpdateDate(), new Date()) < 60)
                throw new RuntimeException("کاربر گرامی حداقل فاصله زمانی بین ثبت دو کاربر، 1 دقیقه می باشد. ");
        if (findUserByUsernameEquals(username) != null)
            throw new RuntimeException("خطا در ثبت کاربر. با این شماره موبایل قبلا ثبت نام انجام شده است!");
    }

    @Override
    @Transactional
    public void sendPasswordToUserEmail(String email) {
        checkMail(email);
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("noreply@bz-framework.com");
        message.setTo(email);
        message.setSubject("bz-framework: " + "رمز عبور جدید شما");
        message.setText("کاربر گرامی نام کاربری و رمزعبور جدید شما به این شرح می باشد: " + "\n" +
                "username: " + iUserRepository.findByEmail(email).getUsername() + "\n" +
                "password: " + updatePasswordOfUserByEmail(email, RandomStringUtils.random(5, true, true)) + "\n" +
                " در صورت تمایل، پس از ورود به سامانه می توانید نسبت به تغییر رمز عبور خود اقدام کنید."
        );
        emailSender.send(message);
    }

    public void checkMail(String email) {
        if (iPersonelService.findByEmailEquals(email) == null)
            throw new RuntimeException("فردی تاکنون با این ایمیل ثبت نام نکرده است.");
        else if (DateUtility.getDiffSeconds(iUserRepository.findByEmail(email).getLastUpdateDate(), new Date()) < 60)
            throw new RuntimeException("از آخرین ایمیل ارسال شده باید حداقل یک دقیقه گذشته باشد.");
    }

    @Transactional
    public String updatePasswordOfUserByEmail(String email, String newPassword) {
        Integer result = iUserRepository.changePasswordByEmail(email, new BCryptPasswordEncoder().encode(newPassword), new Date());
        if (result > 0)
            return newPassword;
        else
            throw new RuntimeException("با این ایمیل، حساب کاربری ایجاد نشده است.");
    }

    @Transactional
    @Override
    public String updatePasswordOfUserByMobile(String mobile, String newPassword) throws URISyntaxException {
        if (iPersonelService.findByMobileEquals(mobile) == null)
            throw new RuntimeException("فردی تاکنون با این شماره موبایل ثبت نام نکرده است.");
        if (DateUtility.getDiffSeconds(iUserRepository.findByMobile(mobile).getLastUpdateDate(), new Date()) < 60)
            throw new RuntimeException("از آخرین پیامک ارسال شده باید حداقل یک دقیقه گذشته باشد.");
        Integer result = iUserRepository.changePasswordByMobile(mobile, new BCryptPasswordEncoder().encode(newPassword), new Date());
        if (result > 0) {
            return Utility.sendSMS(mobile, "رمز عبور جدید شما در مدیک: " + newPassword);
        } else
            throw new RuntimeException("با این شماره موبایل، حساب کاربری ایجاد نشده است.");

    }

    @Transactional
    @Override
    public boolean changePasswordByAdmin(Long userId, String newPassword) {
        Integer result = iUserRepository.changePassword(userId, new BCryptPasswordEncoder().encode(newPassword), new Date());
        if (result > 0)
            return true;
        else
            return false;
    }

    @Transactional
    @Override
    public void changeAuthenticatedUserPassword(String oldPassword, String newPassword) {
        User authenticatedUser = findUserByUsernameEquals(SecurityUtility.getAuthenticatedUser().getUsername());
        if (!new BCryptPasswordEncoder().matches(oldPassword, authenticatedUser.getPassword()))
            throw new RuntimeException("رمز عبور قدیم صحیح نمی باشد.");
        iUserRepository.changePassword(authenticatedUser.getId(), new BCryptPasswordEncoder().encode(newPassword), new Date());
    }

    @Override
    public Map<String, Object> searchUser(String firstname,
                                          String lastname,
                                          String nationalCode,
                                          String mobile,
                                          Long organizationId,
                                          String username,
                                          Boolean accountNonExpired,
                                          Boolean accountNonLocked,
                                          Boolean credentialsNonExpired,
                                          Boolean enabled,
                                          int page, int size, String[] sort) {
        List<Sort.Order> orders = new ArrayList<Sort.Order>();
        if (sort[0].contains(",")) {
            for (String sortOrder : sort) {
                String[] _sort = sortOrder.split(",");
                orders.add(new Sort.Order(getSortDirection(_sort[1]), _sort[0]));
            }
        } else {
            orders.add(new Sort.Order(getSortDirection(sort[1]), sort[0]));
        }
        List<User> users = new ArrayList<User>();
        Pageable pagingSort = PageRequest.of(page, size, Sort.by(orders));
        Page<User> pageTuts = iUserRepository.searchUser(firstname,
                lastname,
                nationalCode,
                mobile,
                organizationId,
                username,
                accountNonExpired,
                accountNonLocked,
                credentialsNonExpired,
                enabled,
                pagingSort);
        users = pageTuts.getContent();
        Map<String, Object> response = new HashMap<>();
        response.put("entityList", users);
        response.put("currentPage", pageTuts.getNumber());
        response.put("totalRecords", pageTuts.getTotalElements());
        response.put("totalPages", pageTuts.getTotalPages());
        return response;
    }

    private Sort.Direction getSortDirection(String direction) {
        if (direction.equals("asc"))
            return Sort.Direction.ASC;
        else if (direction.equals("desc"))
            return Sort.Direction.DESC;
        return Sort.Direction.ASC;
    }

    @Override
    public Map<String, Object> searchUserOrganizations(long userId, String organizationTitle, int page, int size, String[] sort) {
        List<Sort.Order> orders = new ArrayList<Sort.Order>();
        if (sort[0].contains(",")) {
            for (String sortOrder : sort) {
                String[] _sort = sortOrder.split(",");
                orders.add(new Sort.Order(getSortDirection(_sort[1]), _sort[0]));
            }
        } else {
            orders.add(new Sort.Order(getSortDirection(sort[1]), sort[0]));
        }
        List<Organization> organizations = new ArrayList<Organization>();
        Pageable pagingSort = PageRequest.of(page, size, Sort.by(orders));
        Page<Organization> pageTuts = iUserRepository.searchUserOrganizations(userId, organizationTitle, pagingSort);
        organizations = pageTuts.getContent();
        Map<String, Object> response = new HashMap<>();
        response.put("entityList", organizations);
        response.put("currentPage", pageTuts.getNumber());
        response.put("totalRecords", pageTuts.getTotalElements());
        response.put("totalPages", pageTuts.getTotalPages());
        return response;
    }

    @Override
    @Transactional
    public boolean deleteUserOrganization(long userId, long organizationId) {
        if (!iOrganizationService.userHaveAccessToOrganization(findUserByUsernameEquals(SecurityUtility.getAuthenticatedUser().getUsername()).getId(), organizationId))
            throw new RuntimeException("خطا! شما به این واحد سازمانی دسترسی ندارید.");

        User user = loadByEntityId(userId);
        user.setOrganizations(user.getOrganizations().stream()
                .filter(e -> !e.getId().equals(organizationId))
                .collect(Collectors.toSet()));
        iUserRepository.save(user);
        return true;
    }

    @Override
    @Transactional
    public boolean addUserOrganizations(long userId, List<Long> organizationIds) {
        if (organizationIds != null) {
            long authenticatedUserId = findUserByUsernameEquals(SecurityUtility.getAuthenticatedUser().getUsername()).getId();
            User user = loadByEntityId(userId);
            Set<Organization> newSet = new HashSet<>();
            newSet.addAll(user.getOrganizations()); //copy old to new
            for (long newOrganizationId : organizationIds) {
                if (!iOrganizationService.userHaveAccessToOrganization(authenticatedUserId, newOrganizationId)) {
                    throw new RuntimeException("خطا! شما به " + iOrganizationService.loadByEntityId(newOrganizationId).getTitle() + " دسترسی ندارید.");
                }

                boolean exists = false;
                for (Organization old : newSet) {
                    if (old.getId().equals(newOrganizationId)) {
                        exists = true;
                        break;
                    }
                }
                if (!exists) {
                    Organization org = new Organization();
                    org.setId(newOrganizationId);
                    newSet.add(org);
                }
            }
            user.setOrganizations(newSet);
            iUserRepository.save(user);
        }
        return true;
    }

    @Override
    public Map<String, Object> searchUserGroups(long userId, String groupTitle, int page, int size, String[] sort) {
        List<Sort.Order> orders = new ArrayList<Sort.Order>();
        if (sort[0].contains(",")) {
            for (String sortOrder : sort) {
                String[] _sort = sortOrder.split(",");
                orders.add(new Sort.Order(getSortDirection(_sort[1]), _sort[0]));
            }
        } else {
            orders.add(new Sort.Order(getSortDirection(sort[1]), sort[0]));
        }
        List<Group> groups = new ArrayList<Group>();
        Pageable pagingSort = PageRequest.of(page, size, Sort.by(orders));
        Page<Group> pageTuts = iUserRepository.searchUserGroups(userId, groupTitle, pagingSort);
        groups = pageTuts.getContent();
        Map<String, Object> response = new HashMap<>();
        response.put("entityList", groups);
        response.put("currentPage", pageTuts.getNumber());
        response.put("totalRecords", pageTuts.getTotalElements());
        response.put("totalPages", pageTuts.getTotalPages());
        return response;
    }

    @Override
    @Transactional
    public boolean deleteUserGroup(long userId, long groupId) {
        if (!iGroupService.userHaveAccessToGroup(findUserByUsernameEquals(SecurityUtility.getAuthenticatedUser().getUsername()).getId(), groupId))
            throw new RuntimeException("خطا! شما به این گروه دسترسی ندارید.");

        User user = loadByEntityId(userId);
        user.setGroups(user.getGroups().stream()
                .filter(e -> !e.getId().equals(groupId))
                .collect(Collectors.toSet()));
        iUserRepository.save(user);
        return true;
    }

    @Override
    @Transactional
    public boolean addUserGroups(long userId, List<Long> groupIds) {
        if (groupIds != null) {
            long authenticatedUserId = findUserByUsernameEquals(SecurityUtility.getAuthenticatedUser().getUsername()).getId();
            User user = loadByEntityId(userId);
            Set<Group> newSet = new HashSet<>();
            newSet.addAll(user.getGroups()); //copy old to new
            for (long newGroupId : groupIds) {
                if (!iGroupService.userHaveAccessToGroup(authenticatedUserId, newGroupId)) {
                    throw new RuntimeException("خطا! شما به " + iGroupService.loadByEntityId(newGroupId).getTitle() + " دسترسی ندارید.");
                }

                boolean exists = false;
                for (Group old : newSet) {
                    if (old.getId().equals(newGroupId)) {
                        exists = true;
                        break;
                    }
                }
                if (!exists) {
                    Group group = new Group();
                    group.setId(newGroupId);
                    newSet.add(group);
                }
            }
            user.setGroups(newSet);
            iUserRepository.save(user);
        }
        return true;
    }

    @Transactional
    @Override
    public void resetWrongPasswordTries(Long userId) {
        iUserRepository.resetWrongPasswordTries(userId, new Date());
    }

    @Transactional
    @Override
    public void increaseWrongPasswordTries(Long userId) {
        iUserRepository.increaseWrongPasswordTries(userId, new Date());
    }

    @Transactional
    @Override
    public void lock(Long userId) {
        iUserRepository.lock(userId, new Date());
    }

}
