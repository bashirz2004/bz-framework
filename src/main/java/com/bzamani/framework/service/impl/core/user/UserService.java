package com.bzamani.framework.service.impl.core.user;

import com.bzamani.framework.common.utility.DateUtility;
import com.bzamani.framework.common.utility.SecurityUtility;
import com.bzamani.framework.dto.selfUserRegistrationDto;
import com.bzamani.framework.model.core.organization.Organization;
import com.bzamani.framework.model.core.personel.Personel;
import com.bzamani.framework.model.core.user.User;
import com.bzamani.framework.repository.core.user.IUserRepository;
import com.bzamani.framework.service.core.action.IActionService;
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

import java.util.*;

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
    public User save(User user) {
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
            user.setActions(loadByEntityId(user.getId()).getActions());//should not change
        }
        return super.save(user);
    }

    @Override
    @Transactional
    public User selfRegister(selfUserRegistrationDto userDto) throws Exception {
        checkInputData(userDto.getUsername());

        Personel p = new Personel();
        p.setFirstname(userDto.getFirstname());
        p.setLastname(userDto.getLastname());
        p.setMale(userDto.getMale());
        p.setNationalCode(userDto.getNationalCode().trim());
        p.setEmail(userDto.getEmail().trim());
        Organization org = new Organization();
        org.setId(guestOrganizationId);
        p.setOrganization(org);
        iPersonelService.save(p);

        User newUser = new User();
        newUser.setPersonel(p);
        newUser.setPassword(userDto.getPassword().trim());
        newUser.setUsername(userDto.getUsername().trim());
        newUser.setAccountNonExpired(true);
        newUser.setCredentialsNonExpired(true);
        newUser.setAccountNonLocked(true);
        newUser.setEnabled(true);
        newUser.setUserExpireDateShamsi(DateUtility.todayShamsi(365));
        newUser.setPasswordExpireDateShamsi(DateUtility.todayShamsi(365));

        //Set<Action> actions = new HashSet<>();
        //actions.add(iActionService.loadByEntityId(3L));//patient
        //user.setActions(actions);
        return save(newUser);
    }

    public void checkInputData(String username) throws Exception {
        if (iOrganizationService.loadByEntityId(guestOrganizationId) == null)
            throw new Exception("واحد سازمانی برای کاربران پورتا تعریف نشده است.");
        List<User> usersCreatedByCurrentIP = iUserRepository.findAllByIpOrderByLastUpdateDateDesc(SecurityUtility.getRequestIp());
        if (usersCreatedByCurrentIP != null && usersCreatedByCurrentIP.size() > 0)
            if (DateUtility.getDiffSeconds(usersCreatedByCurrentIP.get(0).getLastUpdateDate(), new Date()) < 60)
                throw new Exception("کاربر گرامی حداقل فاصله زمانی بین ثبت دو کاربر، 1 دقیقه می باشد. ");
        if (findUserByUsernameEquals(username) != null)
            throw new Exception("خطا در ثبت کاربر. با این نام کاربری قبلا ثبت نام انجام شده است!");
    }

    @Override
    @Transactional
    public void sendPasswordToUserEmail(String email) throws Exception {
        checkMail(email);
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("noreply@bz-framework.com");
        message.setTo(email);
        message.setSubject("bz-framework: " + "رمز عبور جدید شما");
        message.setText("کاربر گرامی نام کاربری و رمزعبور جدید شما به این شرح می باشد: " + "\n" +
                "username: " + iUserRepository.findByEmail(email).getUsername() + "\n" +
                "password: " + updatePasswordOfUser(email, RandomStringUtils.random(10, true, true)));
        emailSender.send(message);
    }

    public void checkMail(String email) throws Exception {
        if (iPersonelService.findByEmailEquals(email) == null)
            throw new Exception("فردی تاکنون با این ایمیل ثبت نام نکرده است.");
        else if (DateUtility.getDiffSeconds(iUserRepository.findByEmail(email).getLastUpdateDate(), new Date()) < 60)
            throw new Exception("از آخرین ایمیل ارسال شده باید حداقل یک دقیقه گذشته باشد.");
    }

    @Transactional
    public String updatePasswordOfUser(String email, String newPassword) throws Exception {
        Integer result = iUserRepository.changePasswordByEmail(email, new BCryptPasswordEncoder().encode(newPassword), new Date());
        if (result > 0)
            return newPassword;
        else
            throw new Exception("برای شما هنوز حساب کاربری ایجاد نشده است.");
    }

    @Transactional
    @Override
    public boolean changePasswordByAdmin(Long userId, String newPassword) {
        Integer result = iUserRepository.changePasswordByAdmin(userId, new BCryptPasswordEncoder().encode(newPassword), new Date());
        if (result > 0)
            return true;
        else
            return false;
    }

    @Override
    public Map<String, Object> searchUser(String firstname,
                                          String lastname,
                                          String nationalCode,
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

}
