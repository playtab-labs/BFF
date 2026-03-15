package com.playtab.bff.user.graphql;

import com.playtab.bff.user.dto.input.ConsentInputDto;
import com.playtab.bff.user.dto.input.UpdateMyProfileInput;
import com.playtab.bff.user.dto.input.UpdateMySettingsInput;
import com.playtab.bff.user.dto.input.VerifyAdultInput;
import com.playtab.bff.user.dto.output.MyAuthSummaryDto;
import com.playtab.bff.user.dto.output.SuccessResultDto;
import com.playtab.bff.user.dto.output.UserProfileDto;
import com.playtab.bff.user.dto.output.UserSettingsDto;
import com.playtab.bff.user.dto.output.VerifyAdultResultDto;
import com.playtab.bff.user.service.UserFacade;
import java.util.List;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

@Controller
public class UserGraphqlController {

    private final UserFacade userFacade;

    public UserGraphqlController(UserFacade userFacade) {
        this.userFacade = userFacade;
    }

    @QueryMapping
    public UserProfileDto me() {
        return userFacade.getMyProfile();
    }

    @QueryMapping
    public UserSettingsDto mySettings() {
        return userFacade.getMySettings();
    }

    @QueryMapping
    public MyAuthSummaryDto myAuthSummary() {
        return userFacade.getMyAuthSummary();
    }

    @MutationMapping
    public UserProfileDto updateMyProfile(@Argument UpdateMyProfileInput input) {
        return userFacade.updateMyProfile(input);
    }

    @MutationMapping
    public UserSettingsDto updateMySettings(@Argument UpdateMySettingsInput input) {
        return userFacade.updateMySettings(input);
    }

    @MutationMapping
    public SuccessResultDto updateConsents(@Argument List<ConsentInputDto> consents) {
        return userFacade.updateConsents(consents);
    }

    @MutationMapping
    public VerifyAdultResultDto verifyAdult(@Argument VerifyAdultInput input) {
        return userFacade.verifyAdult(input.isAdult());
    }
}