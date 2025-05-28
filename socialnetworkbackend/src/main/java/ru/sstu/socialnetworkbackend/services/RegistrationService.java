package ru.sstu.socialnetworkbackend.services;

import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.sstu.socialnetworkbackend.dtos.users.UserDto;
import ru.sstu.socialnetworkbackend.entities.ConfirmationToken;
import ru.sstu.socialnetworkbackend.entities.User;

@Service
public class RegistrationService {

    private final UserService userService;
    private final ConfirmationTokenService confirmationTokenService;
    private final EmailSender emailSender;

    private static final Logger LOG = LoggerFactory.getLogger(RegistrationService.class);

    public RegistrationService(UserService userService, ConfirmationTokenService confirmationTokenService,
                               EmailSender emailSender) {
        this.userService = userService;
        this.confirmationTokenService = confirmationTokenService;
        this.emailSender = emailSender;
    }

    @Transactional
    public ConfirmationToken register(UserDto userDto) {
        User user = userService.create(userDto);
        ConfirmationToken token = confirmationTokenService.create(user);
        String link = "http://localhost:8082/confirm_registration/" + token.getToken();
        emailSender.send(user.getEmail(), buildEmail(user.getFirstName(), link));
        LOG.info("Пользователь с токеном {} зарегистрировался",
            token);
        return token;
    }

    @Transactional
    public ConfirmationToken confirm(String token) {
        ConfirmationToken confirmationToken = confirmationTokenService.update(token);
        userService.update(confirmationToken.getUser());
        LOG.info("Пользователь с токеном {} подтвердил аккаунт",
            confirmationToken);
        return confirmationToken;
    }

    private String buildEmail(String name, String link) {
        return "<div style=\"font-family:Helvetica,Arial,sans-serif;font-size:16px;margin:0;color:#0b0c0c\">\n" +
            "\n" +
            "<span style=\"display:none;font-size:1px;color:#fff;max-height:0\"></span>\n" +
            "\n" +
            "  <table role=\"presentation\" width=\"100%\" style=\"border-collapse:collapse;min-width:100%;width:" +
            "100%!important\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\">\n" +
            "    <tbody><tr>\n" +
            "      <td width=\"100%\" height=\"53\" bgcolor=\"#0b0c0c\">\n" +
            "        \n" +
            "        <table role=\"presentation\" width=\"100%\" style=\"border-collapse:collapse;" +
            "max-width:580px\" " +
            "cellpadding=\"0\" cellspacing=\"0\" border=\"0\" align=\"center\">\n" +
            "          <tbody><tr>\n" +
            "            <td width=\"70\" bgcolor=\"#0b0c0c\" valign=\"middle\">\n" +
            "                <table role=\"presentation\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" " +
            "style=\"border-collapse:collapse\">\n" +
            "                  <tbody><tr>\n" +
            "                    <td style=\"padding-left:10px\">\n" +
            "                  \n" +
            "                    </td>\n" +
            "                    <td style=\"font-size:28px;line-height:1.315789474;Margin-top:4px;" +
            "padding-left:10px\">\n" +
            "                      <span style=\"font-family:Helvetica,Arial,sans-serif;font-weight:700;" +
            "color:#ffffff;text-decoration:none;vertical-align:top;display:inline-block\">" +
            "Подтвердите вашу электронную почту</span>\n" +
            "                    </td>\n" +
            "                  </tr>\n" +
            "                </tbody></table>\n" +
            "              </a>\n" +
            "            </td>\n" +
            "          </tr>\n" +
            "        </tbody></table>\n" +
            "        \n" +
            "      </td>\n" +
            "    </tr>\n" +
            "  </tbody></table>\n" +
            "  <table role=\"presentation\" class=\"m_-6186904992287805515content\" align=\"center\" " +
            "cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse;max-width:580px;" +
            "width:100%!important\" width=\"100%\">\n" +
            "    <tbody><tr>\n" +
            "      <td width=\"10\" height=\"10\" valign=\"middle\"></td>\n" +
            "      <td>\n" +
            "        \n" +
            "                <table role=\"presentation\" width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" " +
            "border=\"0\" style=\"border-collapse:collapse\">\n" +
            "                  <tbody><tr>\n" +
            "                    <td bgcolor=\"#1D70B8\" width=\"100%\" height=\"10\"></td>\n" +
            "                  </tr>\n" +
            "                </tbody></table>\n" +
            "        \n" +
            "      </td>\n" +
            "      <td width=\"10\" valign=\"middle\" height=\"10\"></td>\n" +
            "    </tr>\n" +
            "  </tbody></table>\n" +
            "\n" +
            "\n" +
            "\n" +
            "  <table role=\"presentation\" class=\"m_-6186904992287805515content\" align=\"center\" " +
            "cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse;max-width:580px;" +
            "width:100%!important\" width=\"100%\">\n" +
            "    <tbody><tr>\n" +
            "      <td height=\"30\"><br></td>\n" +
            "    </tr>\n" +
            "    <tr>\n" +
            "      <td width=\"10\" valign=\"middle\"><br></td>\n" +
            "      <td style=\"font-family:Helvetica,Arial,sans-serif;font-size:19px;line-height:1.315789474;" +
            "max-width:560px\">\n" +
            "        \n" +
            "            <p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\">Здравствуйте, "
            + name + ".</p><p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\"> " +
            "Спасибо за регистрацию. Пожалуйста, перейдите по ссылке ниже для активации вашего аккаунта: " +
            "</p><blockquote style=\"Margin:0 0 20px 0;border-left:10px solid #b1b4b6;padding:15px 0 0.1px 15px;" +
            "font-size:19px;line-height:25px\"><p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;" +
            "color:#0b0c0c\"> <a href=\"" + link + "\">Активировать аккаунт</a> </p></blockquote>\n " +
            "Действие ссылки истекает через 15 минут. <p>До скорой встречи!</p>" +
            "        \n" +
            "      </td>\n" +
            "      <td width=\"10\" valign=\"middle\"><br></td>\n" +
            "    </tr>\n" +
            "    <tr>\n" +
            "      <td height=\"30\"><br></td>\n" +
            "    </tr>\n" +
            "  </tbody></table><div class=\"yj6qo\"></div><div class=\"adL\">\n" +
            "\n" +
            "</div></div>";
    }

}
