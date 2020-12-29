package com.bzamani.framework.common.config.security;

import com.bzamani.framework.common.config.security.recaptcha.RecaptchaService;
import com.bzamani.framework.common.utility.DateUtility;
import com.bzamani.framework.controller.core.BaseController;
import com.bzamani.framework.model.core.user.User;
import com.bzamani.framework.service.core.user.IUserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@RestController
public class AuthenticationController extends BaseController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Autowired
    private IUserService iUserService;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private RecaptchaService recaptchaService;

    @Value("${google.recaptcha.enabled}")
    boolean isCaptchaEnabled;

    @PostMapping("/authenticate")
    @ResponseBody
    public ResponseEntity createAuthenticationToken(@RequestBody Map<String, String> loginInfo, HttpServletRequest request) throws Exception {
        if (isCaptchaEnabled) {
            String ip = request.getRemoteAddr();
            String captchaVerifyMessage = recaptchaService.verifyRecaptcha(ip, loginInfo.get("g-recaptcha-response"));

            if (StringUtils.isNotEmpty(captchaVerifyMessage)) {
                Map<String, Object> response = new HashMap<>();
                response.put("message", captchaVerifyMessage);
                return ResponseEntity.badRequest().body(response);
            }
        }

        try {
            checkUser(loginInfo.get("username"));
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginInfo.get("username"), loginInfo.get("password")));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }

        UserDetails userdetails = userDetailsService.loadUserByUsername(loginInfo.get("username"));
        User user = iUserService.findUserByUsernameEquals(loginInfo.get("username"));
        user.setToken(jwtUtil.generateToken(userdetails));
        return ResponseEntity.ok().body(user);
    }

    public void checkUser(String username) throws Exception {
        User user = iUserService.findUserByUsernameEquals(username);
        if (!user.isEnabled())
            throw new Exception("کاربر محترم، حساب کاربری شما غیر فعال شده است.");
        if (!user.isAccountNonLocked())
            throw new Exception("کاربر محترم، در اثر چندین بار ورود رمز اشتباه، حساب کاربری شما مسدود شده است.");
        if (user.getUserExpireDateShamsi().compareTo(DateUtility.todayShamsi()) <= 0)
            throw new Exception("کاربر محترم، اعتبار شما به پایان رسیده است.");
        if (user.getPasswordExpireDateShamsi().compareTo(DateUtility.todayShamsi()) <= 0)
            throw new Exception("کاربر محترم، اعتبار رمزعبور شما تمام شده است.");
    }

}
